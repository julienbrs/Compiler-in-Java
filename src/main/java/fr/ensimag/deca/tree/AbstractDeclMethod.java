package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

public abstract class AbstractDeclMethod extends Tree{
    
    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the method members (fields and
     * methods) are OK, without looking at method body and field initialization.
     */
    protected abstract void verifyMethodMembers(DecacCompiler compiler, EnvironmentExp localEnv)
            throws ContextualError;

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that instructions and expressions
     * contained in the class are OK.
     */
    protected abstract void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv)
            throws ContextualError;
}
