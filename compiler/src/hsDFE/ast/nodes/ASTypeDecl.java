package hsDFE.ast.nodes;

import java.util.List;

import hsDFE.utility.Position;
import hsDFE.ast.ASTVisitor;

public class ASTypeDecl extends ASDecl {

    public List<String> vars;

    public ASType       type;

    public ASTypeDecl(Position position, List<String> vars, ASType type) {
        super(position);
        this.vars = vars;
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
