package hsDFE.ast.nodes;

import java.util.List;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASFunDecl extends ASADecl {

    public List<ASArgDecl> args;

    public ASExp           exp;

    public ASFunDecl(Position position, String varid, List<ASArgDecl> args, ASExp exp) {
        super(position, varid);
        this.args = args;
        this.exp = exp;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
