package fr.ensimag.deca.tree;

import java.io.PrintStream;

/**
 * Exception corresponding to an error at a particular location in a file.
 *
 * @author gl11
 * @date 01/01/2023
 */
public class LocationException extends Exception {
    
    /**
     * Gets the location of an exception
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Displays the file name, the line, column and the message
     * @param s
     */
    public void display(PrintStream s) {
        Location loc = getLocation();
        String line;
        String column;
        if (loc == null) {
            line = "<unknown>";
            column = "";
        } else {
            line = Integer.toString(loc.getLine());
            column = ":" + loc.getPositionInLine();
        }
        s.println(location.getFilename() + ":" + line + column + ": " + getMessage());
    }

    private static final long serialVersionUID = 7628400022855935597L;
    protected Location location;

    /**
     * Verifies that the location is not null and that it has a filename. Declares the location and the message
     * @param message
     * @param location
     */
    public LocationException(String message, Location location) {
        super(message);
        assert(location == null || location.getFilename() != null);
        this.location = location;
    }

}
