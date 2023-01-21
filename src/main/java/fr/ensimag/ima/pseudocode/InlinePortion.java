package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;

/**
 * Portion of IMA assembly code to be dumped verbatim into the
 * generated code.
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class InlinePortion extends AbstractLine {
    private String asmCode;
    
    public InlinePortion(String asmCode) {
        super();
        this.asmCode = asmCode;
    }

    public void setAsm(String asmCode) {
        this.asmCode = asmCode;
    }
    
    @Override
    void display(PrintStream s) {
        s.println(asmCode);
    }

}
