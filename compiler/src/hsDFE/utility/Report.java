package hsDFE.utility;

public class Report {
    
    public static void warning(String message) {
        System.err.print("warning: ");
        System.err.println(message);
    }
    
    public static void warning(Position position, String message) {
        System.err.print("warning: ");
        System.err.print(position + " ");
        System.err.println(message);
    }
    
    public static void error(String message) {
        System.err.print("error: ");
        System.err.println(message);
        System.exit(1);
    }
    
    public static void error(Position position, String message) {
        System.err.print("error: ");
        System.err.print(position + " ");
        System.err.println(message);
        System.exit(1);
    }
}
