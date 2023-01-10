package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;

public class DeclField extends DeclVar{
    private Visibility visibility;

    public DeclField(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization, Visibility visibility) {
        super(type, varName, initialization);
        this.visibility = visibility;
    }
    
    public void verifyField(DecacCompiler compiler, EnvironmentExp localEnv,
        ClassDefinition currentClass) throws ContextualError {
            Type t = type.verifyType(compiler);
            if (t.sameType(compiler.environmentType.VOID)) {
                throw new ContextualError("Type can't be void : rule 3.7", getLocation());
            }
            initialization.verifyInitialization(compiler, t, localEnv, currentClass);
            try {
                localEnv.declare(varName.getName(), new VariableDefinition(t, getLocation()));   
            } catch (DoubleDefException e) {
                // TODO : a vérifier
                throw new ContextualError("The variable \""+varName+"\" is already declared : rule ?.??", getLocation());
            }
            varName.verifyExpr(compiler, localEnv, currentClass);
        }
}
