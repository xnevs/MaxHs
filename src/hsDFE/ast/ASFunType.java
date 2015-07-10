package hsDFE.ast;

public class ASFunType extends ASType {
    
    public ASAType arg;
    
    public ASType result;
    
    public ASFunType(ASAType arg, ASType result) {
        super();
        this.arg = arg;
        this.result = result;
    }
    
}
