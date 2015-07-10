package hsDFE.ast;

import java.util.List;

public class ASExports extends ASTree {

    public List<ASVarExp> exports;

    public ASExports(List<ASVarExp> exports) {
        super();
        this.exports = exports;
    }
    
}
