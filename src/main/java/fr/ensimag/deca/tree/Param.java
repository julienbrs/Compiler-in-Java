package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
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
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }
    
    public void verifyParamMembers(DecacCompiler compiler, Signature sig) throws ContextualError {
        Type t = type.verifyType(compiler);
        if (t.sameType(compiler.environmentType.VOID)) {
            // ERROR MSG
            throw new ContextualError("Type can't be void : rule 2.9", getLocation());
        }
        sig.add(t);
    }

    public void verifyParamBody(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError {
        Type t = type.verifyType(compiler);
        try {
            name.setDefinition(new ParamDefinition(t, getLocation()));
            localEnv.declare(name.getName(), name.getExpDefinition());
        } catch (DoubleDefException e) {
            // ERROR MSG
            throw new ContextualError("??? : rule 3.12", getLocation());
        }
        // name.verifyType(compiler);
    }
}