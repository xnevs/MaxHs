package hsDFE.synan;

import hsDFE.lexer.*;
import hsDFE.ast.*;

import java.util.List;
import java.util.LinkedList;

public class SynAn {

    private Lexer  lexer;

    private Symbol symbol;

    public SynAn(Lexer lexer) {
        this.lexer = lexer;
        nextSymbol();
    }

    public ASTree parse() {
        return parseModule();
    }

    private ASModule parseModule() {
        checkToken(Token.MODULE);
        String modid = parseConId();
        ASExports exports = parseExports();
        checkToken(Token.WHERE);
        ASBody body = parseBody();

        return new ASModule(modid, exports, body);
    }

    private ASExports parseExports() {
        List<ASVarExp> exports = new LinkedList<ASVarExp>();

        if (symbol.token == Token.LPARENT) {
            checkToken(Token.LPARENT);

            exports.add(parseVar());
            parseExports_(exports);

            checkToken(Token.RPARENT);
        } else {
            // void
        }

        return new ASExports(exports);
    }

    private void parseExports_(List<ASVarExp> exports) {
        if (symbol.token == Token.COMMA) {
            checkToken(Token.COMMA);

            exports.add(parseVar());

            parseExports_(exports);
        } else {
            // void
        }
    }

    private ASBody parseBody() {
        checkToken(Token.LBRACE);

        ASDecls decls = parseDecls();

        checkToken(Token.RBRACE);

        return new ASBody(decls);
    }

    private ASDecls parseDecls() {
        List<ASDecl> decls = new LinkedList<ASDecl>();
        
        if (symbol.token == Token.VARID) { // TODO
            decls.add(parseDecl());
            parseDecls_(decls);
        } else {
            // void
        }

        return new ASDecls(decls);
    }

    private void parseDecls_(List<ASDecl> decls) {
        if (symbol.token == Token.SCOLON) {
            checkToken(Token.SCOLON);
            decls.add(parseDecl());
            parseDecls_(decls);
        } else {
            // void
        }
    }

    private ASDecl parseDecl() {
        ASDecl decl = null;

        if (symbol.token == Token.VARID) {
            ASVarExp var = parseVar();
            if (symbol.token == Token.DCOLON || symbol.token == Token.COMMA) {
                decl = parseGenDecl_(var);
            } else if (symbol.token == Token.VARID) {
                decl = parseFun_(var); // TODO
            } else if (symbol.token == Token.EQUAL) {
                ASExp exp = parseRhs();
                decl = new ASVarDecl(var, exp);
            } else {
                // error TODO
            }
        } else {
            // error TODO
        }
        return decl;
    }

    private ASTypeDecl parseGenDecl_(ASVarExp var) {
        List<ASVarExp> vars = new LinkedList<ASVarExp>();

        vars.add(var);
        parseVars_(vars);

        checkToken(Token.DCOLON);

        ASType type = parseType();

        return new ASTypeDecl(vars, type);
    }

    private void parseVars_(List<ASVarExp> vars) {
        if (symbol.token == Token.COMMA) {
            checkToken(Token.COMMA);

            vars.add(parseVar());
            parseVars_(vars);
        } else {
            // void
        }
    }

    private ASType parseType() {
        ASAType aType = parseAType();
        return parseType_(aType);
    }

    private ASType parseType_(ASAType aType) {
        if (symbol.token == Token.ARROW) {
            checkToken(Token.ARROW);
            ASAType aType1 = parseAType();
            return new ASFunType(aType, parseType_(aType1));
        } else {
            return aType;
        }
    }

    private ASAType parseAType() {
        ASAType aType = null;

        if (symbol.token == Token.CONID) {
            aType = parseAtomType();
        } else if (symbol.token == Token.LPARENT) {
            aType = parseTupleType();
        } else if (symbol.token == Token.LBRACKET) {
            aType = parseListType();
        } else {
            // error TODO
        }

        return aType;
    }

    private ASAtomType parseAtomType() {
        // tu velja symbol.token == Token.CONID

        ASAtomType atomType = null;

        if (symbol.lexeme.equals("Int")) {
            atomType = new ASAtomType(ASAtomType.Type.INT);
        } else if (symbol.lexeme.equals("Double")) {
            atomType = new ASAtomType(ASAtomType.Type.DOUBLE);
        } else if (symbol.lexeme.equals("Bool")) {
            atomType = new ASAtomType(ASAtomType.Type.BOOLEAN);
        } else {
            // error TODO
        }
        checkToken(Token.CONID);
        return atomType;
    }

    private ASTupleType parseTupleType() {
        checkToken(Token.LPARENT);
        List<ASType> types = parseTypes();
        checkToken(Token.RPARENT);

        return new ASTupleType(types);
    }

    private ASListType parseListType() {
        checkToken(Token.LBRACKET);
        ASType type = parseAType();
        checkToken(Token.RBRACKET);

        return new ASListType(type);
    }

    private List<ASType> parseTypes() {
        List<ASType> types = new LinkedList<ASType>();

        types.add(parseType());
        parseTypes_(types);

        return types;
    }

    private void parseTypes_(List<ASType> types) {
        if (symbol.token == Token.COMMA) {
            checkToken(Token.COMMA);
            types.add(parseType());
            parseTypes_(types);
        } else {
            // void
        }
    }

    private ASFunDecl parseFun_(ASVarExp var) {
        List<ASVarExp> args = parseArgs();
        ASExp exp = parseRhs();

        return new ASFunDecl(var, args, exp);
    }

    private List<ASVarExp> parseArgs() {
        List<ASVarExp> args = new LinkedList<ASVarExp>();
        args.add(parseVar());
        parseArgs_(args);
        return args;
    }

    private void parseArgs_(List<ASVarExp> args) {
        if (symbol.token == Token.VARID) {
            args.add(parseVar());
            parseArgs_(args);
        } else {
            // void
        }
    }

    private ASExp parseRhs() {
        checkToken(Token.EQUAL);
        ASExp exp = parseExp();

        if (symbol.token == Token.WHERE) {
            checkToken(Token.WHERE);
            checkToken(Token.LBRACE);
            ASDecls decls = parseDecls();
            checkToken(Token.RBRACE);

            return new ASWhereExp(decls, exp);
        } else {
            return exp;
        }
    }

    private ASExp parseExp() {
        ASExp bitOrExp = parseBitOrExp();

        if (symbol.token == Token.DCOLON) {
            checkToken(Token.DCOLON);
            bitOrExp.typeDecl = parseType();
        } else {
            // void
        }
        return bitOrExp;
    }

    private ASExp parseBitOrExp() {
        ASExp bitXorExp = parseBitXorExp();
        return parseBitOrExp_(bitXorExp);
    }

    private ASExp parseBitOrExp_(ASExp exp1) {
        if (symbol.token == Token.OR) {
            checkToken(Token.OR);
            ASExp exp2 = parseBitXorExp();
            return parseBitOrExp_(new ASBinExp(ASBinExp.Operator.OR, exp1, exp2));
        } else {
            return exp1;
        }
    }

    private ASExp parseBitXorExp() {
        ASExp bitAndExp = parseBitAndExp();
        return parseBitXorExp_(bitAndExp);
    }

    private ASExp parseBitXorExp_(ASExp exp1) {
        if (symbol.token == Token.XOR) {
            checkToken(Token.XOR);
            ASExp exp2 = parseBitAndExp();
            return parseBitXorExp_(new ASBinExp(ASBinExp.Operator.XOR, exp1,
                    exp2));
        } else {
            return exp1;
        }
    }

    private ASExp parseBitAndExp() {
        ASExp compExp = parseCompExp();
        return parseBitAndExp_(compExp);
    }

    private ASExp parseBitAndExp_(ASExp exp1) {
        if (symbol.token == Token.AND) {
            checkToken(Token.AND);
            ASExp exp2 = parseCompExp();
            return parseBitAndExp_(new ASBinExp(ASBinExp.Operator.AND, exp1,
                    exp2));
        } else {
            return exp1;
        }
    }

    private ASExp parseCompExp() {
        ASExp addExp = parseAddExp();
        return parseCompExp_(addExp);
    }

    private ASExp parseCompExp_(ASExp exp1) {
        ASBinExp.Operator operator;
        if (symbol.token == Token.EQUAL3) {
            operator = ASBinExp.Operator.EQU;
        } else if (symbol.token == Token.NEQUAL3) {
            operator = ASBinExp.Operator.NEQ;
        } else if (symbol.token == Token.LTH) {
            operator = ASBinExp.Operator.LTH;
        } else if (symbol.token == Token.GTH) {
            operator = ASBinExp.Operator.GTH;
        } else if (symbol.token == Token.LEQ) {
            operator = ASBinExp.Operator.GEQ;
        } else if (symbol.token == Token.GEQ) {
            operator = ASBinExp.Operator.GEQ;
        } else {
            return exp1;
        }

        ASExp exp2 = parseAddExp();

        return new ASBinExp(operator, exp1, exp2);
    }

    private ASExp parseAddExp() {
        ASExp mulExp = parseMulExp();
        return parseAddExp_(mulExp);
    }

    private ASExp parseAddExp_(ASExp exp1) {
        if (symbol.token == Token.ADD) {
            checkToken(Token.ADD);
            ASExp exp2 = parseMulExp();
            return parseAddExp_(new ASBinExp(ASBinExp.Operator.ADD, exp1, exp2));
        } else if (symbol.token == Token.SUB) {
            checkToken(Token.SUB);
            ASExp exp2 = parseMulExp();
            return parseAddExp_(new ASBinExp(ASBinExp.Operator.SUB, exp1, exp2));
        } else {
            return exp1;
        }
    }

    private ASExp parseMulExp() {
        ASExp preExp = parsePreExp();
        return parseMulExp_(preExp);
    }

    private ASExp parseMulExp_(ASExp exp1) {
        if (symbol.token == Token.MUL) {
            checkToken(Token.MUL);
            ASExp exp2 = parsePreExp();
            return parseMulExp_(new ASBinExp(ASBinExp.Operator.MUL, exp1, exp2));
        } else if (symbol.token == Token.DIV) {
            checkToken(Token.DIV);
            ASExp exp2 = parsePreExp();
            return parseMulExp_(new ASBinExp(ASBinExp.Operator.DIV, exp1, exp2));
        } else {
            return exp1;
        }
    }

    private ASExp parsePreExp() {
        if (symbol.token == Token.SUB) {
            // TODO
            return null;
        } else {
            return parseExp10();
        }
    }

    private ASExp parseExp10() {
        if (symbol.token == Token.LET) {
            checkToken(Token.LET);
            checkToken(Token.LBRACE);
            ASDecls decls = parseDecls();
            checkToken(Token.RBRACE);
            ASExp exp = parseExp();

            return new ASLetExp(decls, exp);
        } else {
            return parseFExp();
        }
    }

    private ASFExp parseFExp() {
        ASAExp aExp = parseAExp();
        return parseFExp_(aExp);
    }

    private ASFExp parseFExp_(ASFExp fExp) {
        if (symbol.token == Token.VARID || symbol.token == Token.INT_CONST
                || symbol.token == Token.REAL_CONST
                || symbol.token == Token.LPARENT
                || symbol.token == Token.LBRACKET) {
            ASAExp aExp = parseAExp();
            return parseFExp_(new ASFunExp(fExp, aExp));
        } else {
            return fExp;
        }
    }

    private ASAExp parseAExp() {
        ASAExp aExp = null;
        if (symbol.token == Token.VARID) {
            aExp = new ASVarExp(symbol.lexeme);
            checkToken(Token.VARID);
        } else if (symbol.token == Token.INT_CONST) {
            aExp = new ASLiteralExp(ASLiteralExp.Type.INT, symbol.lexeme);
            checkToken(Token.INT_CONST);
        } else if (symbol.token == Token.REAL_CONST) {
            aExp = new ASLiteralExp(ASLiteralExp.Type.REAL, symbol.lexeme);
            checkToken(Token.REAL_CONST);
        } else if (symbol.token == Token.LPARENT) {
            checkToken(Token.LPARENT);
            aExp = new ASTupleExp(parseExps());
            checkToken(Token.RPARENT);
        } else if (symbol.token == Token.LBRACKET) {
            checkToken(Token.LPARENT);
            aExp = new ASListExp(parseExps());
            checkToken(Token.RBRACKET);
        } else {
            //error TODO
        }
        return aExp;
    }

    private List<ASExp> parseExps() {
        List<ASExp> exps = new LinkedList<ASExp>();

        exps.add(parseExp());
        parseExps_(exps);
        return exps;
    }

    private void parseExps_(List<ASExp> exps) {
        if (symbol.token == Token.COMMA) {
            checkToken(Token.COMMA);
            exps.add(parseExp());
            parseExps_(exps);
        } else {
            // void
        }
    }

    private ASVarExp parseVar() {
        ASVarExp var = new ASVarExp(symbol.lexeme);
        checkToken(Token.VARID);
        return var;
    }
    
    private String parseConId() {
        if (symbol.token == Token.CONID) {
            String conid = symbol.lexeme;
            checkToken(Token.CONID);
            return conid;
        } else {
            // TODO error
            return null;
        }
    }

    private void checkToken(Token token) {
        if (symbol.token == token) {
            nextSymbol();
        } else {
            // warning
        }
    }

    private void nextSymbol() {
        symbol = lexer.nextSymbol();
    }

}
