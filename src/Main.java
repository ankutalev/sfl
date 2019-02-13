import parser.Lexer;
import parser.Parser;

import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser(new Lexer(new InputStreamReader(System.in)));
        while (true) {
            System.out.println("I CALC "+parser.calculateExpression());
        }
    }
}

