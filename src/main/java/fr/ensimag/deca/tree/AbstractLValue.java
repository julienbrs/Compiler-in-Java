package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DAddr;

import org.antlr.v4.runtime.misc.Triple;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Left-hand side value of an assignment.
 * 
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractLValue extends AbstractExpr {

    /**
     * Contextual Syntax of LValue
     * @param compiler
     * @param localEnv
     * @param currentClass
     * @return type
     * @throws ContextualError
     */
    public abstract Type verifyLValue(DecacCompiler compiler, EnvironmentExp localEnv,
                                         ClassDefinition currentClass) throws ContextualError;

    public abstract Triple<int[], Integer, DAddr> codeGenLValue(DecacCompiler compiler, int offset);
    // {{maxReg, maxPush}, nextOffset, Addr}
}
