package hsDFE.ast;

import java.util.List;

public class ASFunExp extends ASFExp {

    public ASFExp fun;

    public ASAExp arg;

    public ASFunExp(ASFExp fun, ASAExp arg) {
        super();
        this.fun = fun;
        this.arg = arg;
    }
}
