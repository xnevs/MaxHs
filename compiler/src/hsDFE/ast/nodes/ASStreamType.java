package hsDFE.ast.nodes;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASStreamType extends ASAType {

    public ASType type;

    public ASStreamType(Position position, ASType type) {
        super(position);
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
