package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

public class MethodAsmBody extends AbstractDeclMethod {
    private StringLiteral asm; 

    public MethodAsmBody(StringLiteral asm) {
        this.asm = asm;
    }

    @Override
    protected void verifyMethodMembers(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError {
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
