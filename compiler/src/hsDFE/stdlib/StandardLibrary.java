package hsDFE.stdlib;

import hsDFE.ast.nodes.*;
import hsDFE.seman.SymbTable;
import hsDFE.seman.SymbDesc;
import hsDFE.seman.types.*;
import hsDFE.utility.Report;

public class StandardLibrary {

    public static void populate(SymbTable symbTable, SymbDesc symbDesc) {

        ASDecl map = new ASStdLibDecl("map");
        symbTable.insert("map", map);
        
        ASDecl zipWith = new ASStdLibDecl("zipWith");
        symbTable.insert("zipWith", zipWith);

        ASDecl offset = new ASStdLibDecl("offset");
        symbTable.insert("offset", offset);

        ASDecl mux = new ASStdLibDecl("mux");
        symbTable.insert("mux", mux);

        ASDecl counter = new ASStdLibDecl("counter");
        symbTable.insert("counter", counter);

    }

    public static SemType newType(String function) {
        SemType type = null;
        
        switch(function) {
        case "map": {
            SemAtomType a = new SemAtomType(SemAtomType.Type.UNDEFINED);
            SemAtomType b = new SemAtomType(SemAtomType.Type.UNDEFINED);
            type = new SemFunType(
                     new SemFunType(a, b),
                     new SemFunType(
                       new SemStreamType(a),
                       new SemStreamType(b)
                     )
                   );
            break;
        }
        case "zipWith": {
            SemAtomType a = new SemAtomType(SemAtomType.Type.UNDEFINED);
            SemAtomType b = new SemAtomType(SemAtomType.Type.UNDEFINED);
            SemAtomType c = new SemAtomType(SemAtomType.Type.UNDEFINED);
            type = new SemFunType(
                    new SemFunType(a, new SemFunType(b, c)),
                    new SemFunType(
                      new SemStreamType(a),
                      new SemFunType(
                        new SemStreamType(b),
                        new SemStreamType(c)
                      )
                    )
                  );
            break;
        }
        case "offset": {
            SemAtomType a = new SemAtomType(SemAtomType.Type.UNDEFINED);
            type = new SemFunType(
                     new SemAtomType(SemAtomType.Type.INT),
                     new SemFunType(
                       new SemStreamType(a),
                       new SemStreamType(a)
                     )
                   );
            break;
        }
        case "mux": {
            SemAtomType a = new SemAtomType(SemAtomType.Type.UNDEFINED);
            type = new SemFunType(
                     new SemStreamType(new SemAtomType(SemAtomType.Type.INT)),
                     new SemFunType(
                       new SemListType(new SemStreamType(a)),
                       new SemStreamType(a)
                     )
                   );
            break;
        }
        case "counter": {
            type = new SemFunType(
                     new SemAtomType(SemAtomType.Type.INT),
                     new SemFunType(
                       new SemAtomType(SemAtomType.Type.INT),
                       new SemStreamType(new SemAtomType(SemAtomType.Type.INT))
                     )
                   );
            break;
        }
        default: {
            Report.error("Internal error: " + function + " is not a stdlib function.");
        }
        }
        
        return type;
    }
    
}
