package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class MethodBody extends AbstractMethodBody{
    private ListDeclVar declvar;
    private ListInst listInst;

    public MethodBody(ListDeclVar declvar, ListInst listInst) {
        this.declvar = declvar;
        this.listInst = listInst;
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, EnvironmentExp paramEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        localEnv.setParent(paramEnv);
        declvar.verifyListDeclVariable(compiler, localEnv, currentClass);
        listInst.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        
        declvar.prettyPrint(s,prefix,false);
        listInst.prettyPrint(s,prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declvar.iter(f);
        listInst.iter(f);
    }
    

}