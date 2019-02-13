package parser;

import parser.exceptions.ParserIncorrectInputException;

import java.io.IOException;

public class Parser {
    private Lexer lexer;
    private Lexeme lastLexeme = null;
    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }
    private void clearState() {
        lexer.last= lexer.NOT_YET_WORKED;
        lastLexeme = null;
    }
    public int calculateExpression() {
        System.out.println("Input expression!");
        int answer = 0;
        try {
             answer = parseExpression();
        }
        catch (ParserIncorrectInputException exc) {
            clearState();
            System.out.println("Invalid input");
            return   calculateExpression();
        }
        catch (IOException exc) {
            System.out.println("Error during IO happened! Try again!");
            clearState();
            return calculateExpression();
        }
        catch (ArithmeticException exc) {
            System.out.println("division by zero");
            return calculateExpression();
        }
        if (lastLexeme.type!=LexemeType.EOF) {
            System.out.println("Incorrect input! Try again!");
            clearState();
            return calculateExpression();
        }
        clearState();
        return answer;
    }

    private int parseExpression() throws IOException {
        if (lastLexeme == null)
            lastLexeme = lexer.getNextLexeme();
        int tmp = parseTerm();
        while (lastLexeme.type ==LexemeType.MINUS || lastLexeme.type == LexemeType.PLUS) {
            if (lastLexeme.type == LexemeType.MINUS) {
                lastLexeme = lexer.getNextLexeme();
                tmp -= parseTerm();
            }
            if (lastLexeme.type == LexemeType.PLUS) {
                lastLexeme = lexer.getNextLexeme();
                tmp += parseTerm();
            }
        }
        return tmp;
    }

    private int parseTerm() throws IOException {
        var factor = parseFactor();
        while (lastLexeme.type == LexemeType.DIVIDE || lastLexeme.type == LexemeType.MULTIPLY) {
            if (lastLexeme.type == LexemeType.DIVIDE) {
                lastLexeme = lexer.getNextLexeme();
                factor /= parseFactor();
            }
            if (lastLexeme.type == LexemeType.MULTIPLY) {
                lastLexeme = lexer.getNextLexeme();
                factor *= parseFactor();
            }
        }
        return factor;
    }

    private int parsePower() throws IOException {
        if (lastLexeme.type == LexemeType.MINUS){
            lastLexeme = lexer.getNextLexeme();
            return -parseAtom();
        }
        else return parseAtom();
    }

    private int parseFactor() throws IOException {
        var power = parsePower();
        if (lastLexeme.type==LexemeType.POWER){
            lastLexeme = lexer.getNextLexeme();
            return (int)Math.pow(power,parseFactor());
        }else return power;
    }

    private int parseAtom() throws IOException {
        if (lastLexeme.type == LexemeType.VALUE) {
            var tmpLexeme = lastLexeme;
            lastLexeme = lexer.getNextLexeme();
            return Integer.valueOf(tmpLexeme.data);
        }
        if (lastLexeme.type == LexemeType.OPEN_BRACKET) {
            lastLexeme = lexer.getNextLexeme();
            var tmp = parseExpression();
            if (lastLexeme.type == LexemeType.CLOSE_BRACKET) {
                lastLexeme = lexer.getNextLexeme();
                return tmp;
            }
        }
        throw new ParserIncorrectInputException();
    }
}
