package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Entry point for contextual verifications and code generation from outside the package.
 * 
 * @author gl11
 * @date 01/01/2023
 *
 */
public abstract class AbstractProgram extends Tree {
    /**
     * Contextual syntax, verifies class, class members and class body.
     * @param compiler
     * @throws ContextualError
     */
    public abstract void verifyProgram(DecacCompiler compiler) throws ContextualError;

    /**
     * Generates code for the program
     * @param compiler
     */
    public abstract void codeGenProgram(DecacCompiler compiler) ;

}
