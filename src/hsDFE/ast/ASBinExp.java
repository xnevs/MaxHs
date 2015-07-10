package hsDFE.ast;

public class ASBinExp extends ASExp {

    public static enum Operator {
        AND, XOR, OR, ADD, SUB, MUL, DIV, SHIFTL, SHIFTR, EQU, NEQ, LTH, LEQ, GTH, GEQ,
    }

    public Operator operator;

    public ASExp    exp1;

    public ASExp    exp2;

    public ASBinExp(Operator operator, ASExp exp1, ASExp exp2) {
        super();
        this.operator = operator;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

}
