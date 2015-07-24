package hsDFE.seman.types;

public class SemFunType extends SemType {

    public SemType arg;

    public SemType  result;

    public SemFunType(SemType arg, SemType result) {
        this.arg = arg;
        this.result = result;
    }

    @Override
    public boolean equals(SemType other) {
        if (other instanceof SemFunType) {
            SemFunType otherFun = (SemFunType) other;
            return otherFun.arg.equals(this.arg)
                    && otherFun.result.equals(this.result);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (arg instanceof SemFunType) {
            return "(" + arg.toString() + ") -> " + result.toString();
        } else {
            return arg.toString() + " -> " + result.toString();
        }
    }

}
