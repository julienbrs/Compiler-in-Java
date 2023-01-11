package fr.ensimag.deca.tree;

import java.io.PrintStream;
import fr.ensimag.deca.context.Type;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclMethod extends AbstractDeclMethod {
    private AbstractIdentifier type;
    private AbstractIdentifier ident;
    private ListParam listeparametre;
    

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
    
    public void verifyMethodMembers(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError {
            Type t = type.verifyType(compiler);
            if () {
                // TODO
            }
            listeparametre.verifyListParam(compiler);
            // TODO : method_body
            
            /*try {
                localEnv.declare(varName.getName(), new VariableDefinition(t, getLocation()));   
            } catch (DoubleDefException e) {
                // TODO : a vérifier
                throw new ContextualError("The variable \""+varName+"\" is already declared : rule ?.??", getLocation());
            }
            varName.verifyExpr(compiler, localEnv, currentClass);*/
    }

    public void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        // TODO
    }
}
