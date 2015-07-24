package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public abstract class ASNode {
    
    public final Position position;
    
    public ASNode(Position position) {
        this.position = position;
    }
    
    public abstract void accept(ASTVisitor visitor);
    
}
