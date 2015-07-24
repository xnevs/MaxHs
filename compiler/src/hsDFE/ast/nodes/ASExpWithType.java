package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASExpWithType extends ASExp {
    
    public ASExp  exp;
    public ASType type;

    public ASExpWithType(Position position, ASExp exp, ASType type) {
        super(position);
        this.exp = exp;
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
