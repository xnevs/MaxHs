package hsDFE.ast.nodes;

import java.util.List;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASExports extends ASNode {

    public List<ASVarExp> exports;

    public ASExports(Position position, List<ASVarExp> exports) {
        super(position);
        this.exports = exports;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
