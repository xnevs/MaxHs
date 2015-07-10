package hsDFE.ast;

public class ASAtomType extends ASAType {
    
    public static enum Type {
        INT,
        DOUBLE,
        BOOLEAN
    }
    
    Type type;
    
    public ASAtomType(Type type) {
        super();
        this.type = type;
    }
    
}
