package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Field declaration.
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractDeclField extends Tree  {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the fields are OK.
     * @param compiler
     * @param superEnv
     * @param localEnv
     * @param currentClass
     * @throws ContextualError
     */
    protected abstract void verifyFieldMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError;
    
    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that instructions and expressions contained are OK.
     * @param compiler
     * @param localEnv
     * @param currentClass
     * @throws ContextualError
     */
    protected abstract void verifyFieldBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError;

    /**
     * Initializes fields to 0.
     * @param compiler
     */
    protected abstract void codeGenDeclFieldNull(DecacCompiler compiler);

    /**
     * Initializes fields to their values.
     * @param compiler
     * @return // TODO
     */
    protected abstract int[] codeGenDeclField(DecacCompiler compiler);

}
