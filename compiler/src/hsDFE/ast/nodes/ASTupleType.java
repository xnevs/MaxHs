package hsDFE.ast.nodes;

import java.util.List;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASTupleType extends ASAType {

    public List<ASType> elements;

    public ASTupleType(Position position, List<ASType> elements) {
        super(position);
        this.elements = elements;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
