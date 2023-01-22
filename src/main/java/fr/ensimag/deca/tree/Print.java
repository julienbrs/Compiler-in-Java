package fr.ensimag.deca.tree;

/**
 * Print
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class Print extends AbstractPrint {

    /**
     * Sets the arguments and the boolean printHex for print
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printx)
     */
    public Print(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    String getSuffix() {
        return "";
    }
}
