package hsDFE.seman;

import hsDFE.ast.ASTVisitor;
import hsDFE.ast.nodes.*;
import hsDFE.utility.Report;

public class NameChecker implements ASTVisitor {

    private SymbDesc  symbDesc;
    private SymbTable symbTable;

    public NameChecker(SymbDesc symbDesc, SymbTable symbTable) {
        this.symbDesc = symbDesc;
        this.symbTable = symbTable;
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
            if (decl instanceof ASADecl) {
                symbTable.insert(((ASADecl) decl).varid, decl);
            }
        }
        for (ASDecl decl : decls.decls) {
            decl.accept(this);
        }
    }

    @Override
    public void visit(ASTypeDecl decl) {
        // TODO paziii, tukaj naredim povezavo med definicijo in deklaracijo
        // tipa
        for (String varid : decl.vars) {
            ASDecl varDecl = symbTable.find(varid);
            if (varDecl != null) {
                int varScope = symbDesc.getScope(varDecl);
                if (varScope == symbTable.getScope()) {
                    symbDesc.setTypeDecl(varDecl, decl.type);
                } else {
                    Report.error(decl.position, "Type declaration for " + varid + " without definition.");
                }
            } else {
                Report.error(decl.position, "Type declaration for " + varid + " without definition.");
            }
        }
    }

    @Override
    public void visit(ASAtomType type) {
        // void
    }

    @Override
    public void visit(ASFunType type) {
        // void
    }

    @Override
    public void visit(ASTupleType type) {
        // void
    }

    @Override
    public void visit(ASListType type) {
        // void
    }

    @Override
    public void visit(ASStreamType type) {
        // void
    }

    @Override
    public void visit(ASVarDecl decl) {
        // je ze v symbTable
        decl.exp.accept(this);
    }

    @Override
    public void visit(ASFunDecl decl) {
        // je ze v symbTable
        symbTable.newScope();
        for (ASArgDecl arg : decl.args) {
            symbTable.insert(arg.varid, arg);
            visit(arg); // odvec
        }
        decl.exp.accept(this);
        symbTable.oldScope();
    }
    
    @Override
    public void visit(ASStdLibDecl decl) {
        //void
    }

    @Override
    public void visit(ASArgDecl decl) {
        // je ze v symbTable
    }

    @Override
    public void visit(ASExpWithType exp) {
        exp.type.accept(this); // odvec
        symbDesc.setTypeDecl(exp.exp, exp.type);
        exp.exp.accept(this);
    }

    @Override
    public void visit(ASWhereExp exp) {
        symbTable.newScope();
        exp.decls.accept(this);
        exp.exp.accept(this);
        symbTable.oldScope();
    }

    @Override
    public void visit(ASLetExp exp) {
        symbTable.newScope();
        exp.decls.accept(this);
        exp.exp.accept(this);
        symbTable.oldScope();
    }

    @Override
    public void visit(ASIfExp exp) {
        exp.condExp.accept(this);
        exp.thenExp.accept(this);
        exp.elseExp.accept(this);
    }

    @Override
    public void visit(ASBinExp exp) {
        exp.exp1.accept(this);
        exp.exp2.accept(this);
    }

    @Override
    public void visit(ASFunExp exp) {
        exp.fun.accept(this);
        exp.arg.accept(this);
    }

    @Override
    public void visit(ASVarExp exp) {
        ASDecl decl = symbTable.find(exp.varid);
        symbDesc.setNameDecl(exp, decl);
    }

    @Override
    public void visit(ASLiteralExp exp) {
        // void
    }

    @Override
    public void visit(ASTupleExp exp) {
        for (ASExp el : exp.elements) {
            el.accept(this);
        }
    }

    @Override
    public void visit(ASListExp exp) {
        for (ASExp el : exp.elements) {
            el.accept(this);
        }
    }
}
