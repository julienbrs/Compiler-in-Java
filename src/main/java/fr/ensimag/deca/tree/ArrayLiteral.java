package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ArrayLiteral extends AbstractExpr {
    private ListExpr elementab;
    
    public ArrayLiteral(AbstractExpr elementab) {
        this.elementab = new ListExpr();
        this.elementab.add(elementab);
    }
    public void addExpr(AbstractExpr elementab){
        this.elementab.add(elementab);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        elementab.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }
    
}
