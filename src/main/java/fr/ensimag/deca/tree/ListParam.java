package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListParam extends TreeList<Param> {

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }
    
    public void verifyListParam(DecacCompiler compiler) throws ContextualError {
        for (Param param : this.getList()) {
            param.verifyParam(compiler);
        }
    }
}
