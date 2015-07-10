package hsDFE.ast;

public class ASTWriter extends ASTVisitor {

    protected int           indent;
    protected StringBuilder sb;

    public ASTWriter() {
        this.indent = 0;
        this.sb = null;
    }

    public String write(ASTree ast) {
        indent = 0;
        sb = new StringBuilder();

        visit((ASModule) ast);

        return sb.toString();
    }

    protected void visit(ASModule module) {
        append("Module:");
        indent += 1;
        append("ModId: " + module.modid);
        visit(module.exports);
        visit(module.body);
        indent -= 1;
    }

    protected void visit(ASExports exports) {
        append("Exports:");
        indent += 1;
        for (ASVarExp var : exports.exports) {
            visit(var);
        }
        indent -= 1;
    }

    protected void visit(ASBody body) {
        append("Body:");
        indent += 1;
        visit(body.decls);
        indent -= 1;
    }

    protected void visit(ASDecls decls) {
        append("Decls:");
        indent += 1;
        for (ASDecl decl : decls.decls) {
            visit(decl);
        }
        indent -= 1;
    }

    protected void visit(ASTypeDecl decl) {
        append("TypeDecl:");
        indent += 1;
        for (ASVarExp var : decl.vars) {
            visit(var);
        }
        visit(decl.type);
        indent -= 1;
    }

    protected void visit(ASAtomType type) {
        append("AtomType:");
        indent += 1;
        append(type.type.toString());
        indent -= 1;
    }

    protected void visit(ASFunType type) {
        append("FunType:");
        indent += 1;
        visit(type.arg);
        visit(type.result);
        indent -= 1;
    }

    protected void visit(ASTupleType type) {
        append("TupleType:");
        indent += 1;
        for (ASType el : type.elements) {
            visit(el);
        }
        indent -= 1;
    }

    protected void visit(ASListType type) {
        append("ListType:");
        indent += 1;
        visit(type.type);
        indent -= 1;
    }

    protected void visit(ASVarDecl decl) {
        append("VarDecl:");
        indent += 1;
        visit(decl.var);
        visit(decl.exp);
        indent -= 1;
    }

    protected void visit(ASFunDecl decl) {
        append("FunDecl:");
        indent += 1;
        visit(decl.var);
        for (ASVarExp arg : decl.args) {
            visit(arg);
        }
        visit(decl.exp);
        indent -= 1;
    }

    protected void visit(ASWhereExp exp) {
        append("WhereExp:");
        indent += 1;
        visit(exp.decls);
        visit(exp.exp);
        indent -= 1;
    }

    protected void visit(ASLetExp exp) {
        append("LetExp:");
        indent += 1;
        visit(exp.decls);
        visit(exp.exp);
        indent -= 1;
    }

    protected void visit(ASBinExp exp) {
        append("BinExp:");
        indent += 1;
        append(exp.operator.toString());
        if (exp.typeDecl != null) visit(exp.typeDecl);
        visit(exp.exp1);
        visit(exp.exp2);
        indent -= 1;
    }

    protected void visit(ASFunExp exp) {
        append("FunExp:");
        indent += 1;
        if (exp.typeDecl != null) visit(exp.typeDecl);
        visit(exp.fun);
        visit(exp.arg);
        indent -= 1;
    }

    protected void visit(ASVarExp exp) {
        append("VarExp:");
        indent += 1;
        append(exp.varid);
        if (exp.typeDecl != null) visit(exp.typeDecl);
        indent -= 1;
    }

    protected void visit(ASLiteralExp exp) {
        append("LiteralExp:");
        indent += 1;
        append(exp.literal);
        append(exp.type.toString());
        indent -= 1;
    }
    
    protected void visit(ASTupleExp exp) {
        append("TupleExp:");
        indent += 1;
        if(exp.typeDecl != null) visit(exp.typeDecl);
        for(ASExp el : exp.elements) {
            visit(el);
        }
        indent -= 1;
    }
    
    protected void visit(ASListExp exp) {
        append("ListExp:");
        indent += 1;
        if(exp.typeDecl != null) visit(exp.typeDecl);
        for(ASExp el : exp.elements) {
            visit(el);
        }
        indent -= 1;
    }

    private void append(String str) {
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        sb.append(str);
        sb.append("\n");
    }
}
