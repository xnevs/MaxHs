package hsDFE.ast;

import hsDFE.ast.nodes.*;

public class ASTWriter implements ASTVisitor {

    public int            indent;
    private StringBuilder sb;

    public ASTWriter() {
        this.indent = 0;
        this.sb = null;
    }
    
    public String write(ASNode ast) {
        indent = 0;
        sb = new StringBuilder();
        ast.accept(this);
        return sb.toString();
    }

    @Override
    public void visit(ASModule module) {
        append("Module: " + module.position);
        indent += 1;
        append("ModId: " + module.modid);
        module.exports.accept(this);
        module.body.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASExports exports) {
        append("Exports: " + exports.position);
        indent += 1;
        for (ASVarExp var : exports.exports) {
            var.accept(this);
        }
        indent -= 1;
    }

    @Override
    public void visit(ASBody body) {
        append("Body: " + body.position);
        indent += 1;
        body.decls.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASDecls decls) {
        append("Decls: " + decls.position);
        indent += 1;
        for (ASDecl decl : decls.decls) {
            decl.accept(this);
        }
        indent -= 1;
    }

    @Override
    public void visit(ASTypeDecl decl) {
        append("TypeDecl: " + decl.position);
        indent += 1;
        for (String varid : decl.vars) {
            append(varid);
        }
        decl.type.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASAtomType type) {
        append("AtomType: " + type.position);
        indent += 1;
        append(type.type.toString());
        indent -= 1;
    }

    @Override
    public void visit(ASFunType type) {
        append("FunType: " + type.position);
        indent += 1;
        type.arg.accept(this);
        type.result.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASTupleType type) {
        append("TupleType: " + type.position);
        indent += 1;
        for (ASType el : type.elements) {
            el.accept(this);
        }
        indent -= 1;
    }

    @Override
    public void visit(ASListType type) {
        append("ListType: " + type.position);
        indent += 1;
        type.type.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASStreamType type) {
        append("StreamType: " + type.position);
        indent += 1;
        type.type.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASVarDecl decl) {
        append("VarDecl: " + decl.position);
        indent += 1;
        append(decl.varid);
        decl.exp.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASFunDecl decl) {
        append("FunDecl: " + decl.position);
        indent += 1;
        append(decl.varid);
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
        indent -= 1;
    }

    @Override
    public void visit(ASArgDecl decl) {
        append("ArgDecl: " + decl.position);
        indent += 1;
        append(decl.varid);
        indent -= 1;
    }

    @Override
    public void visit(ASExpWithType exp) {
        append("ExpWithType: " + exp.position);
        indent += 1;
        exp.exp.accept(this);
        exp.type.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASWhereExp exp) {
        append("WhereExp: " + exp.position);
        indent += 1;
        exp.decls.accept(this);
        exp.exp.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASLetExp exp) {
        append("LetExp: " + exp.position);
        indent += 1;
        exp.decls.accept(this);
        exp.exp.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASIfExp exp) {
        append("IfExp: " + exp.position);
        indent += 1;
        exp.condExp.accept(this);
        exp.thenExp.accept(this);
        exp.elseExp.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASBinExp exp) {
        append("BinExp: " + exp.position);
        indent += 1;
        append(exp.operator.toString());
        exp.exp1.accept(this);
        exp.exp2.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASFunExp exp) {
        append("FunExp: " + exp.position);
        indent += 1;
        exp.fun.accept(this);
        exp.arg.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(ASVarExp exp) {
        append("VarExp: " + exp.position);
        indent += 1;
        append(exp.varid);
        indent -= 1;
    }

    @Override
    public void visit(ASLiteralExp exp) {
        append("LiteralExp: " + exp.position);
        indent += 1;
        append(exp.literal);
        append(exp.type.toString());
        indent -= 1;
    }

    @Override
    public void visit(ASTupleExp exp) {
        append("TupleExp: " + exp.position);
        indent += 1;
        for (ASExp el : exp.elements) {
            el.accept(this);
        }
        indent -= 1;
    }

    @Override
    public void visit(ASListExp exp) {
        append("ListExp: " + exp.position);
        indent += 1;
        for (ASExp el : exp.elements) {
            el.accept(this);
        }
        indent -= 1;
    }

    protected void append(String str) {
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        sb.append(str);
        sb.append("\n");
    }
}
