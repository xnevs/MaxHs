package hsDFE.ast;

public class ASVarDecl extends ASDecl {

    public ASVarExp var;

    public ASExp    exp;

    public ASVarDecl(ASVarExp var, ASExp exp) {
        this.var = var;
        this.exp = exp;
    }

}
