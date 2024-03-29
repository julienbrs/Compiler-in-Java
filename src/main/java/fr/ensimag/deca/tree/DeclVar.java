package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Declaration of a variable
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    /**
     * Verifies that the characteristics of the variable are not null and sets their values
     * @param type
     * @param varName
     * @param initialization
     */
    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
                Type t = type.verifyType(compiler);
                if (t.sameType(compiler.environmentType.VOID)) {
                    // ERROR MSG
                    throw new ContextualError("Type can't be void : rule 3.17", getLocation());
                }
                initialization.verifyInitialization(compiler, t, localEnv, currentClass);
                try {
                    varName.setDefinition(new VariableDefinition(t, getLocation()));
                    localEnv.declare(varName.getName(), varName.getExpDefinition());   
                } catch (DoubleDefException e) {
                    // ERROR MSG
                    throw new ContextualError("The variable \""+varName.getName()+"\" is already declared : rule 3.17", getLocation());
                }
                varName.verifyExpr(compiler, localEnv, currentClass);
    }

    @Override
    protected int[] codeGenDeclVar(DecacCompiler compiler, int offsetFromSP, Register reg) {
        /* Initialization */
        varName.getExpDefinition().setOperand(new RegisterOffset(offsetFromSP, reg));
        int[] res = initialization.codeGenInitialization(compiler, 2);
        compiler.addInstruction(new STORE(GPRegister.getR(2), varName.getExpDefinition().getOperand()));
        return res; // {maxReg, maxPush}
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("not yet implemented");
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
        s.println(";");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
