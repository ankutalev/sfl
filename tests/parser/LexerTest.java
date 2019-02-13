package parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.exceptions.LexerUnknownLexemeException;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    private Lexer lexer = new Lexer(new StringReader("123()*a2"));
    @Test
    void getNextLexeme() {
        try {
            assertEquals(lexer.getNextLexeme().data,"123");
            assertEquals(lexer.getNextLexeme().type,LexemeType.OPEN_BRACKET);
            assertEquals(lexer.getNextLexeme().type,LexemeType.CLOSE_BRACKET);
            assertEquals(lexer.getNextLexeme().data,"*");
            assertThrows(LexerUnknownLexemeException.class,()->lexer.getNextLexeme());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}