package hsDFE.seman;

import hsDFE.ast.*;
import hsDFE.ast.nodes.*;

public class SemanASTWriter extends ASTWriter {

    private SymbDesc symbDesc;

    public SemanASTWriter(SymbDesc symbDesc) {
        this.symbDesc = symbDesc;
    }
    

    @Override
    public void visit(ASAtomType type) {
        append("AtomType: " + type.position);
        indent += 1;
        append(type.type.toString());
        append("typed as: " + symbDesc.getType(type));
        indent -= 1;
    }

    @Override
    public void visit(ASFunType type) {
        append("FunType: " + type.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(type));
        type.arg.accept(this);
        type.result.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASTupleType type) {
        append("TupleType: " + type.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(type));
        for (ASType el : type.elements) {
            el.accept(this);
        }
        indent -= 1;
    }

    @Override
    public void visit(ASListType type) {
        append("ListType: " + type.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(type));
        type.type.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASStreamType type) {
        append("StreamType: " + type.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(type));
        type.type.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASVarDecl decl) {
        append("VarDecl: " + decl.position);
        indent += 1;
        append(decl.varid);
        append("typed as: " + symbDesc.getType(decl));
        decl.exp.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASFunDecl decl) {
        append("FunDecl: " + decl.position);
        indent += 1;
        append(decl.varid);
        append("typed as: " + symbDesc.getType(decl));
        for (ASArgDecl arg : decl.args) {
            arg.accept(this);
        }
        decl.exp.accept(this);
        indent -= 1;
    }
    
    @Override
    public void visit(ASStdLibDecl decl) {
        append("StdLibDecl: " + decl.position);
        indent += 1;
        append(decl.varid);
        append("typed as: " + symbDesc.getType(decl));
        indent -= 1;
    }

    @Override
    public void visit(ASArgDecl decl) {
        append("ArgDecl: " + decl.position);
        indent += 1;
        append(decl.varid);
        append("typed as: " + symbDesc.getType(decl));
        indent -= 1;
    }

    @Override
    public void visit(ASExpWithType exp) {
        append("ExpWithType: " + exp.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(exp));
        exp.exp.accept(this);
        exp.type.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASWhereExp exp) {
        append("WhereExp: " + exp.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(exp));
        exp.decls.accept(this);
        exp.exp.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASLetExp exp) {
        append("LetExp: " + exp.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(exp));
        exp.decls.accept(this);
        exp.exp.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASIfExp exp) {
        append("IfExp: " + exp.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(exp));
        exp.condExp.accept(this);
        exp.thenExp.accept(this);
        exp.elseExp.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASBinExp exp) {
        append("BinExp: " + exp.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(exp));
        append(exp.operator.toString());
        exp.exp1.accept(this);
        exp.exp2.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASFunExp exp) {
        append("FunExp: " + exp.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(exp));
        exp.fun.accept(this);
        exp.arg.accept(this);
        indent -= 1;
    }
    
    @Override
    public void visit(ASVarExp exp) {
        append("VarExp: " + exp.position);
        indent += 1;
        append(exp.varid);
        append("defed at: " + symbDesc.getNameDecl(exp).position);
        append("typed as: " + symbDesc.getType(exp));
        indent -= 1;

    }

    @Override
    public void visit(ASLiteralExp exp) {
        append("LiteralExp: " + exp.position);
        indent += 1;
        append(exp.literal);
        append(exp.type.toString());
        append("typed as: " + symbDesc.getType(exp));
        indent -= 1;
    }

    @Override
    public void visit(ASTupleExp exp) {
        append("TupleExp: " + exp.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(exp));
        for (ASExp el : exp.elements) {
            el.accept(this);
        }
        indent -= 1;
    }

    @Override
    public void visit(ASListExp exp) {
        append("ListExp: " + exp.position);
        indent += 1;
        append("typed as: " + symbDesc.getType(exp));
        for (ASExp el : exp.elements) {
            el.accept(this);
        }
        indent -= 1;
    }

}
