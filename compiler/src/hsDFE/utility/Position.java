package hsDFE.utility;

public class Position {
    /** Vrstica zacetka dela besedila. */
    private final int begRow;
    /** Stolpec zacetka dela besedila. */
    private final int begColumn;

    /** Vrstica konca dela besedila. */
    private final int endRow;
    /** Stolpec konca dela besedila. */
    private final int endColumn;

    public Position(int begRow, int begColumn, int endRow, int endColumn) {
        this.begRow = begRow;
        this.begColumn = begColumn;
        this.endRow = endRow;
        this.endColumn = endColumn;
    }
    
    public Position(Position begPos, Position endPos) {
        this.begRow = begPos.begRow;
        this.begColumn = begPos.begColumn;
        this.endRow = endPos.endRow;
        this.endColumn = endPos.endColumn;
    }
    
    @Override
    public String toString() {
        return "[" + begRow + ":" + begColumn + "-" + endRow + ":" + endColumn + "]";
    }
}
