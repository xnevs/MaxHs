package hsDFE.ast;

import java.util.List;

public class ASDecls extends ASTree {
   
    public List<ASDecl> decls;
    
    public ASDecls(List<ASDecl> decls) {
        super();
        this.decls = decls;
    }
}
