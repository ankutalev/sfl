package parser;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Parser parser = new Parser(new Lexer(new StringReader("2^(3*3+(2+2)- 6/3)")));
    @Test
    void executorTest1() {
        assertEquals(parser.calculateExpression(),2048);
    }
    @Test
    void executorTest2() {
        parser = new Parser(new Lexer(new StringReader("(1+(1)+(1)*(5*6) + (2/2 + (3-4)))")));
        assertEquals(parser.calculateExpression(),32);
    }
    @Test
    void executorTest3() {
        parser = new Parser(new Lexer(new StringReader("100/((1+(1)+(1)*(5*6) + (2/2 + (3-4))) - 3*10 -2)")));
        assertThrows(StackOverflowError.class,()-> parser.calculateExpression());
    }
    @Test
    void executorTest4() {
        parser = new Parser(new Lexer(new StringReader("100/(dsa(1+(1)+(1)*(5*6) + (2/2 + (3-4))) - 3*10 -2)")));
        assertThrows(StackOverflowError.class,()-> parser.calculateExpression());
    }
    @Test
    void executorTest5() {
        parser = new Parser(new Lexer(new StringReader("(((((((")));
        assertThrows(StackOverflowError.class,()-> parser.calculateExpression());
    }
    @Test
    void executorTest6() {
        parser = new Parser(new Lexer(new StringReader("")));
        assertThrows(StackOverflowError.class,()-> parser.calculateExpression());
    }
    @Test
    void executorTest7() {
        parser = new Parser(new Lexer(new StringReader("((1+(1)+(1)*(5*6) + (2/2 + (3-4))) - 3*10 -2)")));
        assertEquals(0,parser.calculateExpression());
    }
    @Test
    void executorTest8() {
        parser = new Parser(new Lexer(new StringReader("(3")));
        assertThrows(StackOverflowError.class,()-> parser.calculateExpression());
    }
    @Test
    void executorTest9() {
        parser = new Parser(new Lexer(new StringReader("3)")));
        assertThrows(StackOverflowError.class,()-> parser.calculateExpression());
    }
    @Test
    void executorTest10() {
        parser = new Parser(new Lexer(new StringReader("(-3) + (-5)")));
        assertEquals(-8,parser.calculateExpression());
    }








}