package parser;

import parser.exceptions.LexerUnknownLexemeException;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
    int last;
    final int NOT_YET_WORKED = -2;
    private Reader reader;
    private Map<Character, LexemeType> stringToTypeTransformer = new HashMap<>();

    public Lexer(Reader reader) {
        this.reader = reader;
        stringToTypeTransformer.put('(', LexemeType.OPEN_BRACKET);
        stringToTypeTransformer.put(')', LexemeType.CLOSE_BRACKET);
        stringToTypeTransformer.put('+', LexemeType.PLUS);
        stringToTypeTransformer.put('-', LexemeType.MINUS);
        stringToTypeTransformer.put('*', LexemeType.MULTIPLY);
        stringToTypeTransformer.put('/', LexemeType.DIVIDE);
        stringToTypeTransformer.put('^', LexemeType.POWER);
        last = NOT_YET_WORKED;
    }


    public Lexeme getNextLexeme() throws IOException {
        if (last == NOT_YET_WORKED)
            last = reader.read();
        while (last == ' ')
            last = reader.read();
        if (last == '\n' || last == '\r')
            return new Lexeme("eof",LexemeType.EOF);
        if (stringToTypeTransformer.containsKey((char) last)) {
            char tmp = (char) last;
            last = reader.read();
            return new Lexeme(Character.toString(tmp), stringToTypeTransformer.get(tmp));
        }
        if (last == -1) {
            last = reader.read();
            return new Lexeme("eof", LexemeType.EOF);
        }
        if (Character.isDigit(last))
            return getNumberLexeme();
        throw new LexerUnknownLexemeException();
    }

    private Lexeme getNumberLexeme() throws IOException {
        StringBuilder value = new StringBuilder();
        while (Character.isDigit(last)) {
            value.append((char) last);
            last = reader.read();
        }
        return new Lexeme(value.toString(), LexemeType.VALUE);
    }
}
