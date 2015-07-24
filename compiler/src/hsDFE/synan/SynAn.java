package hsDFE.synan;

import hsDFE.utility.*;
import hsDFE.lexer.*;
import hsDFE.ast.nodes.*;

import java.util.List;
import java.util.LinkedList;

public class SynAn {

    private Lexer  lexer;

    private Symbol symbol;
    
    private Position endPosition;

    public SynAn(Lexer lexer) {
        this.lexer = lexer;
        this.symbol = this.lexer.nextSymbol();
        this.endPosition = null;
    }

    public ASModule parse() {
        return parseModule();
    }

    private ASModule parseModule() {
        Position begPosition = symbol.position;
        
        checkToken(Token.MODULE);
        String modid = symbol.lexeme;
        checkToken(Token.CONID);
        ASExports exports = parseExports();
        checkToken(Token.WHERE);
        ASBody body = parseBody();

        return new ASModule(new Position(begPosition, endPosition), modid, exports, body);
    }

    private ASExports parseExports() {
        Position begPosition = symbol.position;
        
        List<ASVarExp> exports = new LinkedList<ASVarExp>();

        if (symbol.token == Token.LPARENT) {
            checkToken(Token.LPARENT);

            exports.add(parseVar());
            parseExports_(exports);

            checkToken(Token.RPARENT);
        } else {
            // void
        }

        return new ASExports(new Position(begPosition, endPosition), exports);
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
        Position begPosition = symbol.position;
        
        checkToken(Token.LBRACE);

        ASDecls decls = parseDecls();

        checkToken(Token.RBRACE);

        return new ASBody(new Position(begPosition, endPosition), decls);
    }

    private ASDecls parseDecls() {
        Position begPosition = symbol.position;
        
        List<ASDecl> decls = new LinkedList<ASDecl>();

        if (symbol.token == Token.VARID) {
            decls.add(parseDecl());
            parseDecls_(decls);
        } else {
            // void
        }

        return new ASDecls(new Position(begPosition, endPosition), decls);
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
        Position begPosition = symbol.position;
        
        ASDecl decl = null;

        if (symbol.token == Token.VARID) {
            String varid = symbol.lexeme;
            checkToken(Token.VARID);
            if (symbol.token == Token.DCOLON || symbol.token == Token.COMMA) {
                decl = parseGenDecl_(begPosition, varid);
            } else if (symbol.token == Token.VARID) {
                decl = parseFun_(varid);
            } else if (symbol.token == Token.EQUAL) {
                ASExp exp = parseRhs();
                decl = new ASVarDecl(new Position(begPosition, endPosition), varid, exp);
            } else {
                Report.error(symbol.position, "Incomplete declaration.");
            }
        } else {
            Report.error(symbol.position, "varid expected");
        }
        return decl;
    }

    private ASTypeDecl parseGenDecl_(Position begPosition, String varid) {
        List<String> vars = new LinkedList<String>();

        vars.add(varid);
        parseVars_(vars);

        checkToken(Token.DCOLON);

        ASType type = parseType();

        return new ASTypeDecl(new Position(begPosition, endPosition), vars, type);
    }

    private void parseVars_(List<String> vars) {
        if (symbol.token == Token.COMMA) {
            checkToken(Token.COMMA);

            vars.add(symbol.lexeme);
            checkToken(Token.VARID);
            parseVars_(vars);
        } else {
            // void
        }
    }

    private ASType parseType() {
        ASType aType = parseAType();
        return parseType_(aType);
    }

    private ASType parseType_(ASType aType) {
        if (symbol.token == Token.ARROW) {
            checkToken(Token.ARROW);
            ASType aType1 = parseAType();
            ASType resultType = parseType_(aType1);
            return new ASFunType(new Position(aType.position, endPosition), aType, resultType);
        } else {
            return aType;
        }
    }

    private ASType parseAType() {
        ASType aType = null;

        if (symbol.token == Token.CONID && symbol.lexeme.equals("Stream")) { // TODO Stream
            aType = parseStreamType();
        } else if (symbol.token == Token.CONID) {
            aType = parseAtomType();
        } else if (symbol.token == Token.LPARENT) {
            aType = parseTupleType();
        } else if (symbol.token == Token.LBRACKET) {
            aType = parseListType();
        } else {
            Report.error(symbol.position, "type expected");
        }

        return aType;
    }

    private ASAtomType parseAtomType() {
        // tu velja symbol.token == Token.CONID
        Position begPosition = symbol.position;
        
        ASAtomType atomType = null;

        if (symbol.lexeme.equals("Int")) {
            checkToken(Token.CONID);
            atomType = new ASAtomType(new Position(begPosition, endPosition), ASAtomType.Type.INT);
        } else if (symbol.lexeme.equals("Double")) {
            checkToken(Token.CONID);
            atomType = new ASAtomType(new Position(begPosition, endPosition), ASAtomType.Type.DOUBLE);
        } else if (symbol.lexeme.equals("Bool")) {
            checkToken(Token.CONID);
            atomType = new ASAtomType(new Position(begPosition, endPosition), ASAtomType.Type.BOOLEAN);
        } else {
            Report.error(symbol.position, "atomic type expected");
        }
        return atomType;
    }

    private ASType parseTupleType() {
        Position begPosition = symbol.position;
        
        checkToken(Token.LPARENT);
        List<ASType> types = parseTypes();
        checkToken(Token.RPARENT);

        if(types.size() == 1) {
            return types.get(0);
        } else {
            return new ASTupleType(new Position(begPosition, endPosition), types);
        }
    }

    private ASListType parseListType() {
        Position begPosition = symbol.position;
        
        checkToken(Token.LBRACKET);
        ASType type = parseAType();
        checkToken(Token.RBRACKET);

        return new ASListType(new Position(begPosition, endPosition), type);
    }
    private ASStreamType parseStreamType() {
        Position begPosition = symbol.position;
        
        checkToken(Token.CONID);
        ASType type = parseAType();

        return new ASStreamType(new Position(begPosition, endPosition), type);
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

    private ASFunDecl parseFun_(String varid) {
        Position begPosition = symbol.position;
        
        List<ASArgDecl> args = parseArgs();
        ASExp exp = parseRhs();

        return new ASFunDecl(new Position(begPosition, endPosition), varid, args, exp);
    }

    private List<ASArgDecl> parseArgs() {
        List<ASArgDecl> args = new LinkedList<ASArgDecl>();
        args.add(parseArg());
        parseArgs_(args);
        return args;
    }

    private void parseArgs_(List<ASArgDecl> args) {
        if (symbol.token == Token.VARID) {
            args.add(parseArg());
            parseArgs_(args);
        } else {
            // void
        }
    }
    
    private ASArgDecl parseArg() {
        Position begPosition = symbol.position;
        
        String varid = symbol.lexeme;
        checkToken(Token.VARID);
        
        return new ASArgDecl(new Position(begPosition, endPosition), varid);
    }

    private ASExp parseRhs() {
        checkToken(Token.EQUAL);

        Position begPosition = symbol.position; //TODO EQUAL ne dam v pozicijo
        
        ASExp exp = parseExp();

        if (symbol.token == Token.WHERE) {
            checkToken(Token.WHERE);
            checkToken(Token.LBRACE);
            ASDecls decls = parseDecls();
            checkToken(Token.RBRACE);

            return new ASWhereExp(new Position(begPosition, endPosition), decls, exp);
        } else {
            return exp;
        }
    }

    private ASExp parseExp() {
        Position begPosition = symbol.position;
        
        ASExp exp = parseBitOrExp();

        if (symbol.token == Token.DCOLON) {
            checkToken(Token.DCOLON);
            exp = new ASExpWithType(new Position(begPosition, endPosition), exp, parseType());
        } else {
            // void
        }
        return exp;
    }

    private ASExp parseBitOrExp() {
        ASExp bitXorExp = parseBitXorExp();
        return parseBitOrExp_(bitXorExp);
    }

    private ASExp parseBitOrExp_(ASExp exp1) {
        if (symbol.token == Token.OR) {
            checkToken(Token.OR);
            ASExp exp2 = parseBitXorExp();
            return parseBitOrExp_(new ASBinExp(new Position(exp1.position, exp2.position), ASBinExp.Operator.OR, exp1, exp2));
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
            return parseBitXorExp_(new ASBinExp(new Position(exp1.position, exp2.position), ASBinExp.Operator.XOR, exp1,
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
            return parseBitAndExp_(new ASBinExp(new Position(exp1.position, exp2.position), ASBinExp.Operator.AND, exp1,
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
            operator = ASBinExp.Operator.LEQ;
        } else if (symbol.token == Token.GEQ) {
            operator = ASBinExp.Operator.GEQ;
        } else {
            return exp1;
        }

        ASExp exp2 = parseAddExp();

        return new ASBinExp(new Position(exp1.position, exp2.position), operator, exp1, exp2);
    }

    private ASExp parseAddExp() {
        ASExp mulExp = parseMulExp();
        return parseAddExp_(mulExp);
    }

    private ASExp parseAddExp_(ASExp exp1) {
        if (symbol.token == Token.ADD) {
            checkToken(Token.ADD);
            ASExp exp2 = parseMulExp();
            return parseAddExp_(new ASBinExp(new Position(exp1.position, exp2.position), ASBinExp.Operator.ADD, exp1, exp2));
        } else if (symbol.token == Token.SUB) {
            checkToken(Token.SUB);
            ASExp exp2 = parseMulExp();
            return parseAddExp_(new ASBinExp(new Position(exp1.position, exp2.position), ASBinExp.Operator.SUB, exp1, exp2));
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
            return parseMulExp_(new ASBinExp(new Position(exp1.position, exp2.position), ASBinExp.Operator.MUL, exp1, exp2));
        } else if (symbol.token == Token.DIV) {
            checkToken(Token.DIV);
            ASExp exp2 = parsePreExp();
            return parseMulExp_(new ASBinExp(new Position(exp1.position, exp2.position), ASBinExp.Operator.DIV, exp1, exp2));
        } else {
            return exp1;
        }
    }

    private ASExp parsePreExp() {
        if (symbol.token == Token.SUB) {
            // TODO negate
            return null;
        } else {
            return parseExp10();
        }
    }

    private ASExp parseExp10() {
        Position begPosition = symbol.position;
        
        if (symbol.token == Token.LET) {
            checkToken(Token.LET);
            checkToken(Token.LBRACE);
            ASDecls decls = parseDecls();
            checkToken(Token.RBRACE);
            checkToken(Token.IN);
            ASExp exp = parseExp();
            return new ASLetExp(new Position(begPosition, endPosition), decls, exp);
        } else if(symbol.token == Token.IF) {
            checkToken(Token.IF);
            ASExp condExp = parseExp();
            checkToken(Token.THEN);
            ASExp thenExp = parseExp();
            checkToken(Token.ELSE);
            ASExp elseExp = parseExp();
            return new ASIfExp(new Position(begPosition, endPosition), condExp, thenExp, elseExp);
        } else {
            return parseFExp();
        }
    }

    private ASExp parseFExp() {
        ASExp aExp = parseAExp();
        return parseFExp_(aExp);
    }

    private ASExp parseFExp_(ASExp fExp) {
        if (symbol.token == Token.VARID || symbol.token == Token.INT_CONST
                || symbol.token == Token.REAL_CONST
                || symbol.token == Token.LPARENT
                || symbol.token == Token.LBRACKET) {
            ASExp aExp = parseAExp();
            return parseFExp_(new ASFunExp(new Position(fExp.position, aExp.position), fExp, aExp));
        } else {
            return fExp;
        }
    }

    private ASExp parseAExp() {
        Position begPosition = symbol.position;
        
        ASExp aExp = null;
        if (symbol.token == Token.VARID) {
            String varid = symbol.lexeme;
            checkToken(Token.VARID);
            aExp = new ASVarExp(new Position(begPosition, endPosition), varid);
        } else if (symbol.token == Token.INT_CONST) {
            String literal = symbol.lexeme;
            checkToken(Token.INT_CONST);
            aExp = new ASLiteralExp(new Position(begPosition, endPosition), ASLiteralExp.Type.INT, literal);
        } else if (symbol.token == Token.REAL_CONST) {
            String literal = symbol.lexeme;
            checkToken(Token.REAL_CONST);
            aExp = new ASLiteralExp(new Position(begPosition, endPosition), ASLiteralExp.Type.REAL, literal);
        } else if (symbol.token == Token.CONID
                && (symbol.lexeme.equals("True") || symbol.lexeme.equals("False"))) { // TODO True False
            String literal = symbol.lexeme;
            checkToken(Token.CONID);
            aExp = new ASLiteralExp(new Position(begPosition, endPosition), ASLiteralExp.Type.BOOLEAN, literal);
        } else if (symbol.token == Token.LPARENT) {
            checkToken(Token.LPARENT);
            List<ASExp> exps = parseExps();
            checkToken(Token.RPARENT);
            if(exps.size() == 1) {
                aExp = exps.get(0);
            } else {
                aExp = new ASTupleExp(new Position(begPosition, endPosition), exps);
            }
        } else if (symbol.token == Token.LBRACKET) {
            checkToken(Token.LPARENT);
            List<ASExp> exps = parseExps();
            checkToken(Token.RBRACKET);
            aExp = new ASListExp(new Position(begPosition, endPosition), exps);
        } else {
            Report.error(symbol.position, "aexp expected");
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
        Position begPosition = symbol.position;
        String varid = symbol.lexeme;
        checkToken(Token.VARID);
        return new ASVarExp(new Position(begPosition, endPosition), varid);
    }

    private void checkToken(Token token) {
        if (symbol.token == token) {
            nextSymbol();
        } else {
            // warning
            switch (token) {
            case VARID:
            case CONID:
            case INT_CONST:
            case REAL_CONST:
                Report.error(symbol.position, token.toString() + " expected");
                break;
            default:
                Report.warning(symbol.position, token.toString() + " expected");
                break;
            }
        }
    }

    private void nextSymbol() {
        endPosition = symbol.position;
        symbol = lexer.nextSymbol();
    }

}
