package hsDFE;

import java.io.*;

import hsDFE.lexer.Lexer;

public class Main {

    private static String sourceFileName;

    private static void parseArgs(String[] args) {
        sourceFileName = args[0];
    }

    public static void main(String[] args) {
		parseArgs(args);
		
		Reader sourceFile = null;
		try {
			sourceFile = new BufferedReader(new FileReader(sourceFileName));
		} catch (FileNotFoundException e) {
			System.exit(255); //TODO
		}
		
		Lexer lexer = new Lexer(sourceFile);
		SynAn synan = new SynAn(lexer);
	}

}
