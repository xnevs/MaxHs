package hsDFE;

import java.io.*;

import hsDFE.lexer.Lexer;
import hsDFE.synan.SynAn;

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

        // SynAn synan = new SynAn(lexer);
    }

    private static void parseArgs(String[] args) {
        sourceFileName = args[0];
    }
}
