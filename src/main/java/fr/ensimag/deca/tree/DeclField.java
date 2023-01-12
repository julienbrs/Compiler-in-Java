package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclField extends AbstractDeclClass{
    private Visibility visibility;
    private AbstractIdentifier type;
    private AbstractIdentifier varName;
    AbstractInitialization initialization;

    public DeclField(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization, Visibility visibility) {
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
        this.visibility = visibility;
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
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, false);

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
                // TODO
            }
            initialization.verifyInitialization(compiler, t, localEnv, currentClass);
            try {
                localEnv.declare(varName.getName(), new VariableDefinition(t, getLocation()));   
            } catch (DoubleDefException e) {
                // TODO : a vérifier
                // ERROR MSG
                throw new ContextualError("The variable \""+varName+"\" is already declared : rule 2.5", getLocation());
            }
            varName.verifyExpr(compiler, localEnv, currentClass);
        }
}
