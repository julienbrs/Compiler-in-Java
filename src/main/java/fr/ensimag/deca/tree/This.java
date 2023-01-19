package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Instruction
 *
 * @author gl11
 * @date 16/01/2023
 */
public class This extends AbstractExpr {

    private boolean isImplicit;
    private ClassDefinition parentClass;

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
        Type t = currentClass.getType();
        if (currentClass.equals(null)) {
            // ERROR MSG
            throw new ContextualError("Can't call \"this\" in the main program : rule 3.43", getLocation());
        }
        parentClass = currentClass;
        setType(t);
        return getType();
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        compiler.addInstruction(new LOAD(parentClass.getOperand(), GPRegister.getR(offset)));
        return 0;
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