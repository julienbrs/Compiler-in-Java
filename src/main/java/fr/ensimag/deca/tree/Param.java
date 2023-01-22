package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.RegisterOffset;

/**
 * Parameters
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class Param extends AbstractParam {

    private AbstractIdentifier type;
    private AbstractIdentifier name;
    
    /**
     * Sets the type and name of parameters
     * @param type
     * @param name
     */
    public Param(AbstractIdentifier type, AbstractIdentifier name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        name.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);        
    }
    
    /**
     * Passe 2 of contextual syntax for the parameters members
     * @param compiler
     * @param sig
     * @throws ContextualError
     */
    public void verifyParamMembers(DecacCompiler compiler, Signature sig) throws ContextualError {
        Type t = type.verifyType(compiler);
        if (t.sameType(compiler.environmentType.VOID)) {
            // ERROR MSG
            throw new ContextualError("Type can't be void : rule 2.9", getLocation());
        }
        sig.add(t);
    }

    /**
     * Passe 3 of contextual syntax for the parameters body
     * @param compiler
     * @param localEnv
     * @throws ContextualError
     */
    public void verifyParamBody(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError {
        Type t = type.verifyType(compiler);
        try {
            name.setDefinition(new ParamDefinition(t, getLocation()));
            localEnv.declare(name.getName(), name.getExpDefinition());
        } catch (DoubleDefException e) {
            // ERROR MSG
            throw new ContextualError("The parameter \"" + name.getName() + "\" is already used : rule 3.12", getLocation());
        }
    }

    /**
     * Generates code for the parameters
     * @param compiler
     * @param offset
     */
    public void codeGenParam(DecacCompiler compiler, int offset) {
        name.getExpDefinition().setOperand(new RegisterOffset(-offset, GPRegister.LB));
    }
}