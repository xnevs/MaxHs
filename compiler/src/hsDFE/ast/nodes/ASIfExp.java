package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASIfExp extends ASExp {
    
    public ASExp condExp;
    
    public ASExp thenExp;
    
    public ASExp elseExp;
    
    public ASIfExp(Position position, ASExp condExp, ASExp thenExp, ASExp elseExp) {
        super(position);
        this.condExp = condExp;
        this.thenExp = thenExp;
        this.elseExp = elseExp;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
