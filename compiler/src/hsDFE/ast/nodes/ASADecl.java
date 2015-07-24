package hsDFE.ast.nodes;

import hsDFE.utility.Position;

public abstract class ASADecl extends ASDecl {
    
    public String varid;
    
    public ASADecl(Position position, String varid) {
        super(position);
        this.varid = varid;
    }
    
}
