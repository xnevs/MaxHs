package hsDFE.seman.types;

public class SemListType extends SemType {

    public SemType type;

    public SemListType(SemType type) {
        super();
        this.type = type;
    }

    @Override
    public boolean equals(SemType other) {
        if (other instanceof SemListType) {
            SemListType otherList = (SemListType) other;
            return otherList.type.equals(this.type);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[" + type.toString() + "]";
    }
}
