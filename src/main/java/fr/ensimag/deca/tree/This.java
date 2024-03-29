package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * This
 *
 * @author gl11
 * @date 16/01/2023
 */
public class This extends AbstractExpr {

    private boolean isImplicit;

    /**
     * Declares the boolean for this
     * @param isImplicit
     */
    public This(boolean isImplicit) {
        this.isImplicit = isImplicit;
    }

    @Override
    public boolean isImplicit() {
        return isImplicit;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        if (currentClass == null) {
            // ERROR MSG
            throw new ContextualError("Can't call \"this\" in the main program : rule 3.43", getLocation());
        }
        Type t = currentClass.getType();
        setType(t);
        return getType();
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, GPRegister.LB), GPRegister.getR(offset)));
        int[] res = {offset, 0};
        return res;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (!isImplicit) {
            s.print("this");
        }
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // no children
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // no children
    }

}