package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Main block of a Deca program.
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractMain extends Tree {

    /**
     * Generates code for the main
     * @param compiler
     * @param offsetGP
     * @return maximum register used, maximum push used
     */
    protected abstract int[] codeGenMain(DecacCompiler compiler, int offsetGP);

    /**
     * Implements non-terminal "main" of [SyntaxeContextuelle] in pass 3 
     * @param compiler
     * @throws ContextualError
     */
    protected abstract void verifyMain(DecacCompiler compiler) throws ContextualError;
}
