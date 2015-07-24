package hsDFE.ast.nodes;

import java.util.List;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASTupleExp extends ASAExp {

    public List<ASExp> elements;
    
    public ASTupleExp(Position position, List<ASExp> elements) {
        super(position);
        this.elements = elements;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
