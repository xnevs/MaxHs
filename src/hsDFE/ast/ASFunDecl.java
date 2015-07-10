package hsDFE.ast;

import java.util.List;

public class ASFunDecl extends ASDecl {

    public ASVarExp       var;

    public List<ASVarExp> args;

    public ASExp          exp;

    public ASFunDecl(ASVarExp var, List<ASVarExp> args, ASExp exp) {
        this.var = var;
        this.args = args;
        this.exp = exp;
    }

}
