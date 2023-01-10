package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
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
    
}
