package hsDFE.seman.types;

import java.util.*;

public class SemTupleType extends SemType {

    public List<SemType> elements;
    
    public SemTupleType(List<SemType> elements) {
        this.elements = elements;
    }
    
    @Override
    public boolean equals(SemType other) {
        if(other instanceof SemTupleType) {
            SemTupleType otherTuple = (SemTupleType) other;
            if(otherTuple.elements.size() == this.elements.size()) {
                boolean equal = true;
                ListIterator<SemType> it1 = this.elements.listIterator();
                ListIterator<SemType> it2 = otherTuple.elements.listIterator();
                while(it1.hasNext()) {
                    SemType type1 = it1.next();
                    SemType type2 = it2.next();
                    if(!type1.equals(type2)) {
                        equal = false;
                    }
                }
                return equal;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        ListIterator<SemType> it = elements.listIterator();
        while(it.hasNext()) {
            sb.append(it.next().toString());
            if(it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

}
