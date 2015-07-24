package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASLiteralExp extends ASAExp {

    public static enum Type {
        INT, REAL, BOOLEAN
    }

    public Type   type;

    public String literal;

    public ASLiteralExp(Position position, Type type, String literal) {
        super(position);
        this.type = type;
        this.literal = literal;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
