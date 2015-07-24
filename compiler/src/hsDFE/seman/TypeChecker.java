package hsDFE.seman;

import java.util.*;

import hsDFE.ast.ASTVisitor;
import hsDFE.ast.nodes.*;
import hsDFE.seman.types.*;
import hsDFE.utility.Report;
import hsDFE.stdlib.StandardLibrary;

public class TypeChecker implements ASTVisitor {

    private SymbDesc      symbDesc;

    private final SemType booleanType = new SemAtomType(
                                              SemAtomType.Type.BOOLEAN);
    private final SemType intType     = new SemAtomType(SemAtomType.Type.INT);
    private final SemType doubleType  = new SemAtomType(SemAtomType.Type.DOUBLE);

    public TypeChecker(SymbDesc symbDesc) {
        this.symbDesc = symbDesc;
    }

    @Override
    public void visit(ASModule module) {
        module.body.accept(this);
        module.exports.accept(this);
    }

    @Override
    public void visit(ASExports exports) {
        for (ASVarExp var : exports.exports) {
            var.accept(this);
        }
    }

    @Override
    public void visit(ASBody body) {
        body.decls.accept(this);
    }

    @Override
    public void visit(ASDecls decls) {
        for (ASDecl decl : decls.decls) {
            if(decl instanceof ASTypeDecl) {
                decl.accept(this);
            }
        }
        
        for(ASDecl decl : decls.decls) {
            if(decl instanceof ASADecl) {
                SemType declaredType = getDeclaredType(decl);
                symbDesc.setType(decl, declaredType);
            }
        }
        for(ASDecl decl : decls.decls) {
            if(decl instanceof ASADecl) {
                decl.accept(this);
            }
        }
    }

    @Override
    public void visit(ASTypeDecl decl) {
        decl.type.accept(this);
    }

    @Override
    public void visit(ASAtomType type) {
        switch (type.type) {
        case BOOLEAN:
            symbDesc.setType(type, booleanType);
            break;
        case DOUBLE:
            symbDesc.setType(type, doubleType);
            break;
        case INT:
            symbDesc.setType(type, intType);
            break;
        default:
            break;
        }
    }

    @Override
    public void visit(ASFunType type) {
        type.arg.accept(this);
        SemType arg = symbDesc.getType(type.arg);
        type.result.accept(this);
        SemType result = symbDesc.getType(type.result);

        symbDesc.setType(type, new SemFunType(arg, result));
    }

    @Override
    public void visit(ASTupleType type) {
        List<SemType> elements = new LinkedList<SemType>();
        for (ASType el : type.elements) {
            el.accept(this);
            elements.add(symbDesc.getType(el));
        }
        symbDesc.setType(type, new SemTupleType(elements));
    }

    @Override
    public void visit(ASListType type) {
        type.type.accept(this);
        SemType element = symbDesc.getType(type.type);
        symbDesc.setType(type, new SemListType(element));
    }

    @Override
    public void visit(ASStreamType type) {
        type.type.accept(this);
        SemAtomType element = null;
        try {
            element = (SemAtomType) symbDesc.getType(type.type);
        } catch (ClassCastException _) {
            Report.error(type.type.position, "A stream must have atomic elements.");
        }
        symbDesc.setType(type, new SemStreamType(element));
    }

    private SemType getDeclaredType(ASNode node) {
        ASType typeDecl = symbDesc.getTypeDecl(node);
        if (typeDecl != null) {
            return symbDesc.getType(typeDecl);
        } else {
            return null;
        }
    }

    @Override
    public void visit(ASVarDecl decl) {
        SemType declaredType = symbDesc.getType(decl);
        
        decl.exp.accept(this);
        
        SemType actualType = symbDesc.getType(decl.exp);

        if (!actualType.equals(declaredType)) {
            Report.error(decl.position, "Declared and actual type do not match.");
        }
    }

    @Override
    public void visit(ASFunDecl decl) {
        SemType declaredType = getDeclaredType(decl);

        SemType type = declaredType;

        for (ASArgDecl arg : decl.args) {
            SemFunType funType = null;
            try {
                funType = (SemFunType) type;
            } catch (ClassCastException _) {
                Report.error(decl.position, "Incorrect number of elements in function definition.");
            }
            symbDesc.setType(arg, funType.arg);
            type = funType.result;
        }
        
        decl.exp.accept(this);
        SemType expType = symbDesc.getType(decl.exp);
        if (expType.equals(type)) {
            symbDesc.setType(decl, declaredType);
        } else {
            Report.error(decl.position, "Declared and actual type do not match.");
        }

    }
    
    @Override
    public void visit(ASStdLibDecl decl) {
        //void
    }

    @Override
    public void visit(ASArgDecl decl) {
        // obdelam v visit(ASFunDecl)

    }

    @Override
    public void visit(ASExpWithType exp) {
        exp.type.accept(this);
        SemType declaredType = symbDesc.getType(exp.type);
        exp.exp.accept(this);
        SemType actualType = symbDesc.getType(exp.exp);
        if (actualType.equals(declaredType)) {
            symbDesc.setType(exp, actualType);
        } else {
            Report.error(exp.position, "Declared and actual type do not match.");
        }
    }

    @Override
    public void visit(ASWhereExp exp) {
        exp.decls.accept(this);
        exp.exp.accept(this);
        symbDesc.setType(exp, symbDesc.getType(exp.exp));
    }

    @Override
    public void visit(ASLetExp exp) {
        exp.decls.accept(this);
        exp.exp.accept(this);
        symbDesc.setType(exp, symbDesc.getType(exp.exp));
    }

    @Override
    public void visit(ASIfExp exp) { //TODO if atomarni ali ne
        exp.condExp.accept(this);
        SemType condType = symbDesc.getType(exp.condExp);
        
        if(condType.equals(booleanType)) {
            exp.thenExp.accept(this);
            SemType thenType = symbDesc.getType(exp.thenExp);
            exp.elseExp.accept(this);
            SemType elseType = symbDesc.getType(exp.elseExp);
            
            if(thenType.equals(elseType)) {
                symbDesc.setType(exp, thenType);
            } else {
                Report.error(exp.position, "Then and else types do not match.");
            }
        } else {
            Report.error(exp.condExp.position, "Condition must be boolean.");
        }
    }

    @Override
    public void visit(ASBinExp exp) {
        exp.exp1.accept(this);
        SemType type1 = symbDesc.getType(exp.exp1);
        exp.exp2.accept(this);
        SemType type2 = symbDesc.getType(exp.exp2);

        switch (exp.operator) {
        case ADD:
        case SUB:
        case DIV:
        case MUL:
            if ((type1.equals(intType) || type1.equals(doubleType))
                    && (type2.equals(intType) || type2.equals(doubleType))) {
                if (type1.equals(doubleType) || type2.equals(doubleType)) {
                    symbDesc.setType(exp, doubleType);
                } else {
                    symbDesc.setType(exp, intType);
                }
            } else {
                Report.error(exp.position, "Operands must be integer or double.");
            }
            break;

        case EQU:
        case NEQ:
        case GEQ:
        case GTH:
        case LEQ:
        case LTH:
            if ((type1 instanceof SemAtomType) && type2.equals(type1)) {
                symbDesc.setType(exp, booleanType);
            } else {
                Report.error(exp.position, "Operands must be of the same atomic type.");
            }
            break;

        case AND:
        case OR:
        case XOR:
        case SHIFTL:
        case SHIFTR:
            if (type1.equals(intType) && type2.equals(type1)) {
                symbDesc.setType(exp, intType);
            } else {
                Report.error(exp.position, "Operands must be integers.");
            }
            break;

        default:
            break;

        }
    }

    @Override
    public void visit(ASFunExp exp) {
        exp.fun.accept(this);
        
        SemType type = symbDesc.getType(exp.fun);
        if (type instanceof SemFunType) {
            SemFunType funType = (SemFunType) type;
            exp.arg.accept(this);
            SemType argType = symbDesc.getType(exp.arg);
            if (argType.equals(funType.arg)) {
                symbDesc.setType(exp, funType.result);
            } else {
                Report.error(exp.arg.position, "Incorrect argument type.");
            }
        }
    }

    @Override
    public void visit(ASVarExp exp) { //TODO polymorphism, se kdaj razmisli
        ASDecl decl = symbDesc.getNameDecl(exp);
        
        SemType type;
        if(decl instanceof ASStdLibDecl) {
            type = StandardLibrary.newType(((ASStdLibDecl) decl).varid);
        } else {
            type = symbDesc.getType(decl);
        }
        
        symbDesc.setType(exp, type);
    }

    @Override
    public void visit(ASLiteralExp exp) {
        switch (exp.type) {
        case BOOLEAN:
            symbDesc.setType(exp, booleanType);
            break;
        case INT:
            symbDesc.setType(exp, intType);
            break;
        case REAL:
            symbDesc.setType(exp, doubleType);
            break;
        default:
            break;

        }
    }

    @Override
    public void visit(ASTupleExp exp) {
        List<SemType> elements = new LinkedList<SemType>();
        for (ASExp el : exp.elements) {
            el.accept(this);
            elements.add(symbDesc.getType(el));
        }
        symbDesc.setType(exp, new SemTupleType(elements));
    }

    @Override
    public void visit(ASListExp exp) {
        SemType element = null;
        for (ASExp el : exp.elements) {
            el.accept(this);
            SemType type = symbDesc.getType(el);
            if (element == null || element.equals(type)) {
                element = type;
            } else {
                Report.error(exp.position, "Elements in a list must be of the same type.");
            }
        }
        if (element != null) {
            symbDesc.setType(exp, new SemListType(element));
        } else {
            Report.error(exp.position, "Zaenkrat se ne znam praznih seznamov."); // TODO []
        }
    }

}
