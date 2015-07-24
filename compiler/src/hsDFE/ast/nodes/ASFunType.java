package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASFunType extends ASType {
    
    public ASType arg;
    
    public ASType result;
    
    public ASFunType(Position position, ASType arg, ASType result) {
        super(position);
        this.arg = arg;
        this.result = result;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
