package hsDFE.ast;

public abstract class ASTVisitor {
    protected abstract void visit(ASModule module);

    protected abstract void visit(ASExports exports);

    protected abstract void visit(ASBody body);

    protected abstract void visit(ASDecls decls);

    protected void visit(ASDecl decl) {
        if (decl instanceof ASTypeDecl) {
            visit((ASTypeDecl) decl);
        } else if (decl instanceof ASVarDecl) {
            visit((ASVarDecl) decl);
        } else if (decl instanceof ASFunDecl) {
            visit((ASFunDecl) decl);
        }
    }

    protected void visit(ASFExp exp) {
        if (exp instanceof ASAExp) {
            visit((ASAExp) exp);
        } else if (exp instanceof ASFunExp) {
            visit((ASFunExp) exp);
        } else {
            // error TODO
        }
    }

    protected void visit(ASAExp exp) {
        if (exp instanceof ASVarExp) {
            visit((ASVarExp) exp);
        } else if (exp instanceof ASLiteralExp) {
            visit((ASLiteralExp) exp);
        } else if (exp instanceof ASTupleExp) {
            visit((ASTupleExp) exp);
        } else if (exp instanceof ASListExp) {
            visit((ASListExp) exp);
        } else {
            // error TODO
        }
    }

    protected abstract void visit(ASTypeDecl decl);

    protected void visit(ASType type) {
        if(type instanceof ASAType) {
            visit((ASAType)type);
        } else if (type instanceof ASFunType) {
            visit((ASFunType)type);
        } else {
            //error TODO
        }
    }
    
    protected void visit(ASAType type) {
        if(type instanceof ASAtomType) {
            visit((ASAtomType) type);
        } else if (type instanceof ASTupleType) {
            visit((ASTupleType)type);
        } else if (type instanceof ASListType) {
            visit((ASListType)type);
        }
    }

    protected abstract void visit(ASAtomType type);

    protected abstract void visit(ASFunType type);

    protected abstract void visit(ASTupleType type);

    protected abstract void visit(ASListType type);

    protected abstract void visit(ASVarDecl decl);

    protected abstract void visit(ASFunDecl decl);

    protected void visit(ASExp exp) {
        if (exp instanceof ASFExp) {
            visit((ASFExp) exp);
        } else if (exp instanceof ASWhereExp) {
            visit((ASWhereExp) exp);
        } else if (exp instanceof ASLetExp) {
            visit((ASLetExp) exp);
        } else if (exp instanceof ASBinExp) {
            visit((ASBinExp) exp);
        } else {
            System.out.println("BBBERRRRRRRORRRRRRR!!!"); // TODO
        }
    }

    protected abstract void visit(ASWhereExp exp);

    protected abstract void visit(ASLetExp exp);

    protected abstract void visit(ASBinExp exp);

    protected abstract void visit(ASFunExp exp);

    protected abstract void visit(ASVarExp exp);

    protected abstract void visit(ASLiteralExp exp);

    protected abstract void visit(ASTupleExp exp);

    protected abstract void visit(ASListExp exp);
}
