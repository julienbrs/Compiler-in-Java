package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Initialization (of variable, field, ...)
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractInitialization extends Tree {
    
    /**
     * Sets the type at initialization
     * @param t
     */
    protected void setType(Type t) {
        type = t;
    }

    /**
     * Gets the type
     * @return the type
     */
    protected Type getType() {
        return type;
    }

    private Type type = null;

    /**
     * Implements non-terminal "initialization" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains "env_types" attribute
     * @param t corresponds to the "type" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass 
     *          corresponds to the "class" attribute (null in the main bloc).
     */
    protected abstract void verifyInitialization(DecacCompiler compiler,
            Type t, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * // TODO
     * @param compiler
     * @param offset
     * @return // TODO
     */
    protected abstract int[] codeGenInitialization(DecacCompiler compiler, int offset);
}
