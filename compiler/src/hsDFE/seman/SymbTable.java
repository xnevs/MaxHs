package hsDFE.seman;

import hsDFE.ast.nodes.*;
import hsDFE.utility.Report;

import java.util.*;

public class SymbTable {

    private Map<String, LinkedList<ASDecl>> mapping;

    private int                             scope;

    private SymbDesc                        symbDesc;

    public SymbTable(SymbDesc symbDesc) {
        this.mapping = new HashMap<String, LinkedList<ASDecl>>();
        this.scope = 0;
        this.symbDesc = symbDesc;
    }
    
    public int getScope() {
        return scope;
    }
    
    public void newScope() {
        scope++;
    }
    
    public void oldScope() {
        List<String> allNames = new LinkedList<String>();
        allNames.addAll(mapping.keySet());
        
        for(String name : allNames) {
            LinkedList<ASDecl> decls = mapping.get(name);
            ASDecl decl = decls.getFirst();
            if(symbDesc.getScope(decl) == scope) {
                decls.removeFirst();
                if(decls.size() == 0) {
                    mapping.remove(name);
                }
            }
        }
        
        scope--;
    }

    public void insert(String name, ASDecl decl) {
        LinkedList<ASDecl> decls = mapping.get(name);
        if (decls == null) {
            decls = new LinkedList<ASDecl>();
        }
        if(decls.size() > 0 && symbDesc.getScope(decls.getFirst()) == scope) {
            Report.error(decl.position, "Duplicate definitions");
        }
        decls.addFirst(decl);
        symbDesc.setScope(decl, scope);
        mapping.put(name, decls);
    }
    
    public ASDecl find(String name) {
        ASDecl decl = null;
        LinkedList<ASDecl> decls = mapping.get(name);
        if(decls != null && decls.size() > 0) {
            decl =  decls.getFirst();
        } else {
            Report.error("Definition for " + name + " not found");
        }
        return decl;
    }
    
}
