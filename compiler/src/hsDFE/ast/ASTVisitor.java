package hsDFE.ast;

import hsDFE.ast.nodes.*;

public interface ASTVisitor {

    public void visit(ASModule module);
    public void visit(ASExports exports);
    public void visit(ASBody body);
    public void visit(ASDecls decls);
    public void visit(ASTypeDecl decl);
    public void visit(ASAtomType type);
    public void visit(ASFunType type);
    public void visit(ASTupleType type);
    public void visit(ASListType type);
    public void visit(ASStreamType type);
    public void visit(ASVarDecl decl);
    public void visit(ASFunDecl decl);
    public void visit(ASStdLibDecl decl);
    public void visit(ASArgDecl decl);
    public void visit(ASExpWithType exp);
    public void visit(ASWhereExp exp);
    public void visit(ASLetExp exp);
    public void visit(ASIfExp exp);
    public void visit(ASBinExp exp);
    public void visit(ASFunExp exp);
    public void visit(ASVarExp exp);
    public void visit(ASLiteralExp exp);
    public void visit(ASTupleExp exp);
    public void visit(ASListExp exp);
    
}
