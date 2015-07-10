package hsDFE.ast;

import java.util.List;

public class ASTupleType extends ASAType {

    public List<ASType> elements;

    public ASTupleType(List<ASType> elements) {
        super();
        this.elements = elements;
    }
    
}
