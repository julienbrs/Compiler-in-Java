package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListParam extends TreeList<Param> {

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }
    
    public void verifyListParamMembers(DecacCompiler compiler, Signature sig) throws ContextualError {
        for (Param param : this.getList()) {
            param.verifyParamMembers(compiler, sig);
        }
    }

    public void verifyListParamBody(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError{
        for (Param param : this.getList()) {
            param.verifyParamBody(compiler, localEnv);
        }
    }
}
