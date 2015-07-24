package hsDFE.lexer;

import hsDFE.utility.Position;
import hsDFE.utility.Report;

import java.io.Reader;
import java.io.IOException;

public class Lexer {

    private Reader        sourceFile;

    private int           row;
    private int           column;

    private int           character;

    private StringBuilder lexeme;
    private int           begRow;
    private int           begColumn;
    private int           endRow;
    private int           endColumn;

    private Token         token;

    public Lexer(Reader sourceFile) {
        this.sourceFile = sourceFile;

        this.row = 1;
        this.column = 0;
        this.character = -1;
        readCharacter();
    }

    public Symbol nextSymbol() {
        token = Token.IGNORE;
        lexeme = new StringBuilder();
        begRow = begColumn = endRow = endColumn = -1;

        while (token == Token.IGNORE) {
            switch (character) {
            case -1:
                token = Token.EOF;
                break;

            case ' ':
            case '\t':
            case '\n':
            case '\r':
                readCharacter();
                token = Token.IGNORE;
                break;

            case '+':
                firstCharacter();
                token = Token.ADD;
                break;
            case '-':
                readMinus();
                break;
            case '*':
                firstCharacter();
                token = Token.MUL;
                break;
            case '/':
                firstCharacter();
                token = Token.DIV;
                break;
            case '&':
                firstCharacter();
                token = Token.AND;
                break;
            case '^':
                firstCharacter();
                token = Token.XOR;
                break;
            case '|':
                firstCharacter();
                token = Token.OR;
                break;

            case '(':
                firstCharacter();
                token = Token.LPARENT;
                break;
            case ')':
                firstCharacter();
                token = Token.RPARENT;
                break;
            case '[':
                firstCharacter();
                token = Token.LBRACKET;
                break;
            case ']':
                firstCharacter();
                token = Token.RBRACKET;
                break;
            case '{':
                firstCharacter();
                token = Token.LBRACE;
                break;
            case '}':
                firstCharacter();
                token = Token.RBRACE;
                break;
            case ';':
                firstCharacter();
                token = Token.SCOLON;
                break;
            case ',':
                firstCharacter();
                token = Token.COMMA;
                break;

            case ':':
                readDCOLON();
                break;

            case '!':
                readNEQUAL3();
                break;
            case '=':
                readEqual();
                break;
            case '<':
                readLess();
                break;
            case '>':
                readGreater();
                break;

            default:
                if (Character.isLowerCase(character) || character == '_') {
                    readLowerCase();
                } else if (Character.isUpperCase(character)) {
                    readUpperCase();
                } else if (Character.isDigit(character)) {
                    readNumber();
                } else {
                    firstCharacter();
                    token = Token.UNKNOWN;
                }
            }
        }

        return new Symbol(token, lexeme.toString(), new Position(begRow,
                begColumn, endRow, endColumn));
    }

    private void readMinus() {
        // tu velja characte == '-'

        firstCharacter();
        if (character == '>') {
            addCharacter();
            token = Token.ARROW;
        } else {
            token = Token.SUB;
        }
    }

    private void readDCOLON() {
        // tu velja character == ':'

        firstCharacter();
        if (character == ':') {
            token = Token.DCOLON;
            addCharacter();
        } else {
            token = Token.UNKNOWN;
        }
    }

    private void readNEQUAL3() {
        // tu velja character == '!'

        firstCharacter();
        if (character == '=') {
            addCharacter();
            if (character == '=') {
                addCharacter();
                token = Token.NEQUAL3;
            } else {
                token = Token.UNKNOWN;
            }
        } else {
            token = Token.UNKNOWN;
        }
    }

    private void readEqual() {
        // tu velja character == '='

        firstCharacter();
        if (character == '=') {
            addCharacter();
            if (character == '=') {
                addCharacter();
                token = Token.EQUAL3;
            } else {
                token = Token.UNKNOWN;
            }
        } else {
            token = Token.EQUAL;
        }
    }

    private void readLess() {
        // tu velja character == '<'

        firstCharacter();

        if (character == '=') {
            addCharacter();
            token = Token.LEQ;
        } else if (character == '<') {
            addCharacter();
            token = Token.SHIFTL;
        } else {
            token = Token.LTH;
        }
    }

    private void readGreater() {
        // tu velja character == '>'

        firstCharacter();

        if (character == '=') {
            addCharacter();
            token = Token.GEQ;
        } else if (character == '>') {
            addCharacter();
            token = Token.SHIFTR;
        } else {
            token = Token.GTH;
        }
    }

    private void readLowerCase() {
        // tu velja Character.isLowerCase(character)

        firstCharacter();

        while (Character.isLowerCase(character)
                || Character.isUpperCase(character) || character == '_'
                || Character.isDigit(character)) {
            addCharacter();
        }

        switch (lexeme.toString()) {
        case "module":
            token = Token.MODULE;
            break;
        case "where":
            token = Token.WHERE;
            break;
        case "let":
            token = Token.LET;
            break;
        case "in":
            token = Token.IN;
            break;
        case "if":
            token = Token.IF;
            break;
        case "then":
            token = Token.THEN;
            break;
        case "else":
            token = Token.ELSE;
            break;
        default:
            token = Token.VARID;
        }
    }

    private void readUpperCase() {
        // tu velja Character.isUpperCase(character)

        firstCharacter();

        while (Character.isLowerCase(character)
                || Character.isUpperCase(character) || character == '_'
                || Character.isDigit(character)) {
            addCharacter();
        }

        token = Token.CONID;
    }

    private void readNumber() {
        // tu velja Character.isDigit(character)

        firstCharacter();

        while (Character.isDigit(character))
            addCharacter();

        if (character == '.') {
            addCharacter();
            while (Character.isDigit(character)) {
                addCharacter();
            }
            token = Token.REAL_CONST;
        } else {
            token = Token.INT_CONST;
        }
    }

    private void readCharacter() {
        int prev = character;

        try {
            character = sourceFile.read();
        } catch (IOException e) {
            Report.error("Input/Output error.");
        }

        // column++;
        if (prev == '\n') {
            row++;
            column = 1;
        } else
            column++;
    }

    private void firstCharacter() {
        lexeme.append((char) character);

        begRow = row;
        begColumn = column;

        endRow = row;
        endColumn = column;

        readCharacter();
    }

    private void addCharacter() {
        lexeme.append((char) character);

        endRow = row;
        endColumn = column;

        readCharacter();
    }
}
