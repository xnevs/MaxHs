package hsDFE.seman;

import hsDFE.ast.nodes.*;
import hsDFE.seman.types.*;

import java.util.*;

public class SymbDesc {

    private Map<ASDecl, Integer> scope;

    private Map<ASNode, ASDecl>  nameDecl;

    private Map<ASNode, SemType> type;

    private Map<ASNode, ASType>  typeDecl;

    public SymbDesc() {
        this.scope = new HashMap<ASDecl, Integer>();
        this.nameDecl = new HashMap<ASNode, ASDecl>();
        this.type = new HashMap<ASNode, SemType>();
        this.typeDecl = new HashMap<ASNode, ASType>();
    }

    public void setScope(ASDecl decl, Integer scope) {
        this.scope.put(decl, scope);
    }

    public Integer getScope(ASDecl decl) {
        return this.scope.get(decl);
    }

    public void setNameDecl(ASNode node, ASDecl decl) {
        nameDecl.put(node, decl);
    }

    public ASDecl getNameDecl(ASNode node) {
        return nameDecl.get(node);
    }

    public void setType(ASNode node, SemType typ) {
        type.put(node, typ);
    }

    public SemType getType(ASNode node) {
        return type.get(node);
    }
    
    public void setTypeDecl(ASNode node, ASType typ) {
        typeDecl.put(node, typ);
    }
    
    public ASType getTypeDecl(ASNode node) {
        return typeDecl.get(node);
    }
}
