package hsDFE.ast;

import java.util.List;

public class ASTupleExp extends ASAExp {

    List<ASExp> elements;
    
    public ASTupleExp(List<ASExp> elements) {
        super();
        this.elements = elements;
    }
    
}
