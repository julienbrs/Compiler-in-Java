package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;

import java.io.PrintStream;

/**
 * Integer literal
 *
 * @author gl11
 * @date 01/01/2023
 */
public class IntLiteral extends AbstractExpr {

    /**
     * Gets the value of the integer literal
     * @return
     */
    public int getValue() {
        return value;
    }

    private int value;

    /**
     * Sets value of the integer literal
     * @param value
     */
    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        this.setType(compiler.environmentType.INT);
        return this.getType();
    }

    @Override
    protected int[] codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new LOAD(new ImmediateInteger(value), GPRegister.R1));
        compiler.addInstruction(new WINT());
        int[] res = {0, 0};
        return res;
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        compiler.addInstruction(new LOAD(new ImmediateInteger(value), GPRegister.getR(offset)));
        int[] res = {offset, 0};
        return res;
    }

    @Override
    String prettyPrintNode() {
        return "Int (" + getValue() + ")";
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Integer.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

}
