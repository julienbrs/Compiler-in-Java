package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.antlr.v4.runtime.misc.Triple;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;

public class ArraySel extends AbstractSelection {
    private AbstractExpr selExpr;
    private AbstractExpr indexExpr;
    public ArraySel(AbstractExpr selexpr, AbstractExpr indexExpr) {
        this.selExpr = selexpr;
        this.indexExpr = indexExpr;
    }

    @Override
    public Type verifyLValue(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Triple<int[], Integer, DAddr> codeGenLValue(DecacCompiler compiler, int offset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        selExpr.prettyPrint(s, prefix, false);
        indexExpr.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }
    
}
