package hsDFE;

import java.io.*;

import hsDFE.utility.Report;
import hsDFE.lexer.Lexer;
import hsDFE.synan.SynAn;
import hsDFE.ast.nodes.ASModule;
import hsDFE.seman.*;
import hsDFE.stdlib.StandardLibrary;

public class Main {

    private static String sourceFileName;

    public static void main(String[] args) {
        parseArgs(args);

        Reader sourceFile = null;
        try {
            sourceFile = new BufferedReader(new FileReader(sourceFileName));
        } catch (FileNotFoundException e) {
            Report.error("File not found.");
        }

        Lexer lexer = new Lexer(sourceFile);

        SynAn synan = new SynAn(lexer);
        ASModule ast = synan.parse();
        
        SymbDesc symbDesc = new SymbDesc();
        SymbTable symbTable = new SymbTable(symbDesc);
        
        StandardLibrary.populate(symbTable, symbDesc);
        
        NameChecker nameChecker = new NameChecker(symbDesc, symbTable);
        ast.accept(nameChecker);
        
        TypeChecker typeChecker = new TypeChecker(symbDesc);
        ast.accept(typeChecker);
        
        SemanASTWriter semanAstWriter = new SemanASTWriter(symbDesc);
        System.out.print(semanAstWriter.write(ast));
    }

    private static void parseArgs(String[] args) {
        sourceFileName = args[0];
    }
    
}
