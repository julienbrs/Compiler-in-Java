package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclField extends AbstractDeclClass{
    private Visibility visibility;
    private AbstractIdentifier type;
    private AbstractIdentifier varName;
    AbstractInitialization initialization;
    public DeclField(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization, Visibility visibility) {
        this.type=type;
        this.varName=varName;
        this.initialization=initialization;
        this.visibility=visibility;
    }
    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        // TODO Auto-generated method stub
        
    }
    @Override
    protected void verifyClassMembers(DecacCompiler compiler) throws ContextualError {
        // TODO Auto-generated method stub
        
    }
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub
        
    }
    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
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