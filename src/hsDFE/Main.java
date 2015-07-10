package hsDFE;

import java.io.*;

import hsDFE.lexer.*;
import hsDFE.synan.SynAn;
import hsDFE.ast.ASTree;

public class Main {

    private static String sourceFileName;

    public static void main(String[] args) {
        parseArgs(args);

        Reader sourceFile = null;
        try {
            sourceFile = new BufferedReader(new FileReader(sourceFileName));
        } catch (FileNotFoundException e) {
            System.exit(255); // TODO
        }

        Lexer lexer = new Lexer(sourceFile);

        Symbol symbol;
        
        while((symbol = lexer.nextSymbol()).token != Token.EOF) {
           // if(symbol.token == null)
                System.out.println(symbol.lexeme + " " + symbol.token);
        }
        
        SynAn synan = new SynAn(lexer);
        ASTree ast = synan.parse();
    }

    private static void parseArgs(String[] args) {
        sourceFileName = args[0];
    }
}
