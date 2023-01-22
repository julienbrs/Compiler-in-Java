package fr.ensimag.deca.tree;

import java.io.Serializable;

/**
 * Location in a file (File, line, positionInLine).
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Location implements Serializable {
    /*
     * Location implements Serializable because it appears as a field
     * of LocationException, which is serializable.
     */
    private static final long serialVersionUID = -2906437663480660298L;

    public static final String NO_SOURCE_NAME = "<no source file>";
    public static final Location BUILTIN = new Location(-1, -1, NO_SOURCE_NAME);

    /**
     * Display the (line, positionInLine) as a String. The file is not
     * displayed.
     */
    @Override
    public String toString() {
        if (this == BUILTIN) {
            return "[builtin]";
        } else {
            return "[" + line + ", " + positionInLine + "]";
        }
    }

    /**
     * Gets the line in a file
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * Gets the position in line in the file
     * @return the position
     */
    public int getPositionInLine() {
        return positionInLine;
    }

    /**
     * Gets the filename (default name if the file is not named)
     * @return the filename
     */
    public String getFilename() {
        if (filename != null) {
            return filename;
        } else {
            // we're probably reading from stdin
            return NO_SOURCE_NAME;
        }
    }

    private final int line;
    private final int positionInLine;
    private final String filename;

    /**
     * Sets the location of a file (line, position in line and filename)
     * @param line
     * @param positionInLine
     * @param filename
     */
    public Location(int line, int positionInLine, String filename) {
        super();
        this.line = line;
        this.positionInLine = positionInLine;
        this.filename = filename;
    }

}
