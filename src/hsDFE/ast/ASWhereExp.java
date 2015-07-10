package hsDFE.ast;

public class ASWhereExp extends ASExp {

    public ASDecls decls;

    public ASExp   exp;

    public ASWhereExp(ASDecls decls, ASExp exp) {
        super();
        this.decls = decls;
        this.exp = exp;
    }

}
