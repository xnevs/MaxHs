package hsDFE.ast.nodes;

import hsDFE.ast.ASTVisitor;

public class ASStdLibDecl extends ASADecl {
    
    public ASStdLibDecl(String varid) {
        super(null, varid);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
