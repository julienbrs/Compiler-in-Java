package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.WNL;

/**
 * @author gl11
 * @date 01/01/2023
 */
public class Println extends AbstractPrint {

    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printlnx)
     */
    public Println(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    protected int codeGenInst(DecacCompiler compiler) {
        int nbPush = super.codeGenInst(compiler);
        compiler.addInstruction(new WNL());
        return nbPush;
    }

    @Override
    String getSuffix() {
        return "ln";
    }
}
