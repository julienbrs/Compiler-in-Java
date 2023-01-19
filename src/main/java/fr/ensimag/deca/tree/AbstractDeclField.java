package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

public abstract class AbstractDeclField extends Tree  {

    protected abstract void verifyFieldMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError;
    
    protected abstract void verifyFieldBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError;

    protected abstract void codeGenDeclFieldNull(DecacCompiler compiler);

    protected abstract int[] codeGenDeclField(DecacCompiler compiler);

}
