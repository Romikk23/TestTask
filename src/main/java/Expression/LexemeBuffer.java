package Expression;

import java.util.List;

public class LexemeBuffer {
    private int p;
    public List<Lexeme> lexemes;

    public LexemeBuffer(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public Lexeme next() {
        return lexemes.get(p++);
    }

    public void back() {
        p--;
    }

    public int getP() {
        return p;
    }
}
