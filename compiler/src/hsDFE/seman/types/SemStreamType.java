package hsDFE.seman.types;

public class SemStreamType extends SemType {

    public SemAtomType type;

    public SemStreamType(SemAtomType type) {
        super();
        this.type = type;
    }
    
    @Override
    public boolean equals(SemType other) {
        if (other instanceof SemStreamType) {
            SemStreamType otherStream = (SemStreamType) other;
            return otherStream.type.equals(this.type);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Stream " + type.toString();
    }
}
