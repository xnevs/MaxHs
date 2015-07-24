package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASFunExp extends ASFExp {

    public ASExp fun;

    public ASExp arg;

    public ASFunExp(Position position, ASExp fun, ASExp arg) {
        super(position);
        this.fun = fun;
        this.arg = arg;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
