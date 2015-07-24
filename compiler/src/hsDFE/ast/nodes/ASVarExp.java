package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASVarExp extends ASAExp {

    public String varid;
    
    public ASVarExp(Position position, String varid) {
        super(position);
        this.varid = varid;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
