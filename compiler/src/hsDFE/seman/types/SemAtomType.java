package hsDFE.seman.types;

import hsDFE.utility.Report;

public class SemAtomType extends SemType {

    public static enum Type {
        BOOLEAN, INT, DOUBLE, UNDEFINED
    } // UNDEFINED je z polimorfne funkcije

    public Type type;

    public SemAtomType(Type type) {
        super();
        this.type = type;
    }

    @Override
    public boolean equals(SemType other) {
        
        /*
         * Pazi!!!!!
         * Pri tej primerjavi se zgodi tudi unifikacija UNDEFINED tipov.
         */
        
        if (other instanceof SemAtomType) {
            SemAtomType atomOther = (SemAtomType) other;
            if (this.type == Type.UNDEFINED && atomOther.type == Type.UNDEFINED) {
                Report.error("Internal error: unifiying two undefined types.");
            } else if (this.type == Type.UNDEFINED) {
                this.type = atomOther.type;
            } else if (atomOther.type == Type.UNDEFINED) {
                atomOther.type = this.type;
            }
            return atomOther.type == this.type;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        switch(type) {
        case BOOLEAN:
            return "Bool";
        case DOUBLE:
            return "Double";
        case INT:
            return "Int";
        case UNDEFINED:
            return "UNDEFINED";
        default:
            return "null";
        }
    }
    
}
