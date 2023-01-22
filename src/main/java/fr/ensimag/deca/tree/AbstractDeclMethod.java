package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

/**
 * Method declaration.
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractDeclMethod extends Tree{
    
    /**
     * Gets the name of the attribut (Symbol type)
     * @return the name
     */
    public abstract Symbol getName();

    /**
     * Gets the identifier of the attribut.
     * @return the identifier
     */
    public abstract AbstractIdentifier getIdent();

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the method members (fields and
     * methods) are OK, without looking at method body and field initialization.
     * @param compiler
     * @param superEnv
     * @param localEnv
     * @param currentClass
     * @throws ContextualError
     */
    protected abstract void verifyMethodMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that instructions and expressions
     * contained in the class are OK.
     * @param compiler
     * @param localEnv
     * @param currentClass
     * @throws ContextualError
     */
    protected abstract void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Describes the body of a method.
     * @param compiler
     * @param currentClass
     */
    public abstract void codeGenBody(DecacCompiler compiler, ClassDefinition currentClass);
}
