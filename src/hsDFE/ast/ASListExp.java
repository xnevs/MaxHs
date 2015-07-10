package hsDFE.ast;

import java.util.List;

public class ASListExp extends ASAExp {

    List<ASExp> elements;

    public ASListExp(List<ASExp> elements) {
        super();
        this.elements = elements;
    }

}
