package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

public abstract class AbstractMethodBody extends Tree {

    protected abstract void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, EnvironmentExp paramEnv, ClassDefinition currentClass, Type returnType) throws ContextualError;
}