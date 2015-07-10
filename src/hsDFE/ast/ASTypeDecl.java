package hsDFE.ast;

import java.util.List;

public class ASTypeDecl extends ASDecl {

    public List<ASVarExp> vars;

    public ASType         type;

    public ASTypeDecl(List<ASVarExp> vars, ASType type) {
        super();
        this.vars = vars;
        this.type = type;
    }
}
