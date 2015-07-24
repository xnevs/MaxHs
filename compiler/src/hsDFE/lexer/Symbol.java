package hsDFE.lexer;

import hsDFE.utility.Position;

public class Symbol {
    
    /** Vrsta simbola. */
    public final Token token;

    /** Znakovna predstavitev simbola. */
    public final String lexeme;
    
    /** Polozaj simbola v izvorni datoteki. */
    public final Position position;

    public Symbol(Token token, String lexeme, Position position) {
        this.token = token;
        this.lexeme = lexeme;
        this.position = position;
    }

}
