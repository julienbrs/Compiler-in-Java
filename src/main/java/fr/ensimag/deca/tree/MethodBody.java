package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class MethodBody extends AbstractMethodBody{
    private ListDeclVar declVar;
    private ListInst listInst;

    public MethodBody(ListDeclVar declVar, ListInst listInst) {
        this.declVar = declVar;
        this.listInst = listInst;
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, EnvironmentExp paramEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        localEnv.setParent(paramEnv);
        declVar.verifyListDeclVariable(compiler, localEnv, currentClass);
        listInst.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("{");
        declVar.decompile(s);
        listInst.decompile(s);
        s.print("}");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        
        declVar.prettyPrint(s,prefix,false);
        listInst.prettyPrint(s,prefix,false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVar.iter(f);
        listInst.iter(f);
    }
    

}