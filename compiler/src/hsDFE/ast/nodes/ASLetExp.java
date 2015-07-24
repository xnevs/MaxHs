package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASLetExp extends ASExp {

    public ASDecls decls;

    public ASExp   exp;

    public ASLetExp(Position position, ASDecls decls, ASExp exp) {
        super(position);
        this.decls = decls;
        this.exp = exp;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
