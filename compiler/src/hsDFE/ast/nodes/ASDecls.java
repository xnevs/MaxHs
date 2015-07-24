package hsDFE.ast.nodes;

import java.util.List;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASDecls extends ASNode {
   
    public List<ASDecl> decls;
    
    public ASDecls(Position position, List<ASDecl> decls) {
        super(position);
        this.decls = decls;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
