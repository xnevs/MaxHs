package hsDFE.ast;


public abstract class ASExp extends ASTree {

    public ASType typeDecl;
    
    public ASExp() {
        super();
        typeDecl = null;
    }
    
}
