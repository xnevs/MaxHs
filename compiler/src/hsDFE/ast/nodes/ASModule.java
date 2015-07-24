package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASModule extends ASNode {

    public String    modid;

    public ASExports exports;

    public ASBody    body;

    public ASModule(Position position, String modid, ASExports exports, ASBody body) {
        super(position);
        this.modid = modid;
        this.exports = exports;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
