package hsDFE.ast;


public class ASLetExp extends ASExp {

    public ASDecls decls;

    public ASExp   exp;

    public ASLetExp(ASDecls decls, ASExp exp) {
        super();
        this.decls = decls;
        this.exp = exp;
    }

}
