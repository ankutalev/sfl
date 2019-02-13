package parser;

public class Lexeme {
    public Lexeme(String data, LexemeType type) {
        this.data = data;
        this.type = type;
    }
    String data;
    LexemeType type;
}
