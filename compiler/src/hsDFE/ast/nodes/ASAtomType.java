package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASAtomType extends ASAType {
    
    public static enum Type {
        INT,
        DOUBLE,
        BOOLEAN
    }
    
    public Type type;
    
    public ASAtomType(Position position, Type type) {
        super(position);
        this.type = type;
    }
    
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
