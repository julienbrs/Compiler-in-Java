package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

/**
 * Boolean Literal
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    /**
     * Sets the boolean value for the literal expression 
     * 
     * @param value
     */
    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        setType(compiler.environmentType.BOOLEAN);
        return this.getType();
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        if (value) {
            compiler.addInstruction(new LOAD(new ImmediateInteger(1), GPRegister.getR(offset)));
        } else {
            compiler.addInstruction(new LOAD(new ImmediateInteger(0), GPRegister.getR(offset)));
        }
        int[] res = {offset, 0};
        return res;
    }

    @Override
    protected int[] codeGenBool(DecacCompiler compiler, boolean aim, Label dest, int offset) {
        if ((value && aim) || (!value && !aim)) {
            compiler.addInstruction(new BRA(dest));
        }
        int[] res = {0, 0};
        return res;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

}
