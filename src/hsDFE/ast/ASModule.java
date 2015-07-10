package hsDFE.ast;

public class ASModule extends ASTree {

    public String    modid;

    public ASExports exports;

    public ASBody    body;

    public ASModule(String modid, ASExports exports, ASBody body) {
        super();
        this.modid = modid;
        this.exports = exports;
        this.body = body;
    }
}
