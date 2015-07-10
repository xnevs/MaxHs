package hsDFE;

import java.io.*;

import hsDFE.lexer.*;
import hsDFE.synan.SynAn;
import hsDFE.ast.ASTree;
import hsDFE.ast.ASTWriter;

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
        
        SynAn synan = new SynAn(lexer);
        ASTree ast = synan.parse();
        
        ASTWriter astWriter = new ASTWriter();
        System.out.print(astWriter.write(ast));
    }

    private static void parseArgs(String[] args) {
        sourceFileName = args[0];
    }
}
