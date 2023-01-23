package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

/**
 * Method Body
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractMethodBody extends Tree {

    /**
     * Pass 3 of [Contextual syntax]. Verify that instructions and expressions
     * contained in the class are OK.
     * @param compiler
     * @param localEnv
     * @param paramEnv
     * @param currentClass
     * @param returnType
     * @throws ContextualError
     */
    protected abstract void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, EnvironmentExp paramEnv, ClassDefinition currentClass, Type returnType) throws ContextualError;

    /**
     * Codage of the fields of each class;
     * Coding of the methods of each class;
     * Coding of the main program.
     * @param compiler
     * @param currentClass
     * @param ident
     */
    public abstract void codeGenBody(DecacCompiler compiler, ClassDefinition currentClass, AbstractIdentifier ident);
}