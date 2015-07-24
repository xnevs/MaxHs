package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASVarDecl extends ASADecl {

    public ASExp exp;

    public ASVarDecl(Position position, String varid, ASExp exp) {
        super(position, varid);
        this.exp = exp;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
