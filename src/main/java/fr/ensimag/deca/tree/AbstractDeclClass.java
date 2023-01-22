package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Class declaration.
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractDeclClass extends Tree {

    /**
     * Pass 1 of [Contextual syntax]. Verify that the class declaration is OK
     * without looking at its content.
     * @param compiler
     * @throws ContextualError
     */
    protected abstract void verifyClass(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 2 of [Contextual syntax]. Verify that the class members (fields and
     * methods) are OK, without looking at method body and field initialization.
     * @param compiler
     * @throws ContextualError
     */
    protected abstract void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 3 of [Contextual syntax]. Verify that instructions and expressions
     * contained in the class are OK.
     * @param compiler
     * @throws ContextualError
     */
    protected abstract void verifyClassBody(DecacCompiler compiler)
            throws ContextualError;


    /**
     * Construction of the table of method labels;
     * Generation of code to build the table of methods.
     * @param compiler
     * @param offset
     * @return // TODO
     */    
    protected abstract int codeGenVTable(DecacCompiler compiler, int offset);

    /**
     * Codage of the fields of each class;
     * Coding of the methods of each class;
     * Coding of the main program.
     * @param compiler
     */
    protected abstract void codeGenBody(DecacCompiler compiler);
}
