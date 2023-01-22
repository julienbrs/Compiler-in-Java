package fr.ensimag.deca.tree;

import java.util.Iterator;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Parameters
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class ListParam extends TreeList<Param> {

    @Override
    public void decompile(IndentPrintStream s) {
        Iterator<Param> ite = getList().iterator();
        Param current;
        if (ite.hasNext()) {
            current = ite.next();
            current.decompile(s);
        }
        while (ite.hasNext()) {
            current = ite.next();
            s.print(", ");
            current.decompile(s);
        }
    }
    
    /**
     * Passe 1 of contextual syntax for parameters members
     * @param compiler
     * @param sig
     * @throws ContextualError
     */
    public void verifyListParamMembers(DecacCompiler compiler, Signature sig) throws ContextualError {
        for (Param param : this.getList()) {
            param.verifyParamMembers(compiler, sig);
        }
    }

    /**
     * Passe 2 of contextual syntax for parameters body
     * @param compiler
     * @param localEnv
     * @throws ContextualError
     */
    public void verifyListParamBody(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError{
        for (Param param : this.getList()) {
            param.verifyParamBody(compiler, localEnv);
        }
    }

    /**
     * Generates code to parameters
     * @param compiler
     */
    public void codeGenParam(DecacCompiler compiler) {
        int index = 3;
        for (Param p : getList()) {
            p.codeGenParam(compiler, index);
            index++;
        }
    }
}
