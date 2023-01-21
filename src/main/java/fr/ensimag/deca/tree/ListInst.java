package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class ListInst extends TreeList<AbstractInst> {

    /**
     * Implements non-terminal "list_inst" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains "env_types" attribute
     * @param localEnv corresponds to "env_exp" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     * @param returnType
     *          corresponds to "return" attribute (void in the main bloc).
     */    
    public void verifyListInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        for (AbstractInst abstractInst : this.getList()) {
            abstractInst.verifyInst(compiler, localEnv, currentClass, returnType);
        }
    }

    public int[] codeGenListInst(DecacCompiler compiler) {
        int[] res = {0, 0};
        for (AbstractInst i : getList()) {
            int[] resInst = i.codeGenInst(compiler);
            if (resInst[0] > res[0]) {
                res[0] = resInst[0];
            }
            if (resInst[1] > res[1]) {
                res[1] = resInst[1];
            }
        }
        return res;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractInst i : getList()) {
            i.decompileInst(s);
            s.println();
        }
    }
}
