package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class Param extends AbstractParam {
    private AbstractIdentifier type;
    private AbstractIdentifier name;
    
    public Param(AbstractIdentifier type, AbstractIdentifier name) {
        this.type = type;
        this.name = name;
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
    
    public void verifyParam(DecacCompiler compiler) throws ContextualError {
        Type t = type.verifyType(compiler);
        if (t.sameType(compiler.environmentType.VOID)) {
            // ERROR MSG
            throw new ContextualError("Type can't be void : rule 2.9", getLocation());
        }
    }
}
