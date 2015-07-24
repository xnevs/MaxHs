package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASBody extends ASNode {

    public ASDecls decls;

    public ASBody(Position position, ASDecls decls) {
        super(position);
        this.decls = decls;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
