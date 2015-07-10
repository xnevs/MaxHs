package hsDFE.ast;


public abstract class ASExp extends ASTree {

    public ASType type;
    
    public ASExp() {
        super();
        type = null;
    }
    
}
