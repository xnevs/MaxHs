package hsDFE.ast;

public class ASLiteralExp extends ASAExp {

    public static enum Type {
        INT, REAL
    }

    public Type   type;

    public String literal;

    public ASLiteralExp(Type type, String literal) {
        super();
        this.type = type;
        this.literal = literal;
    }

}
