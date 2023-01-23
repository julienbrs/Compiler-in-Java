package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;

/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class ListDeclVar extends TreeList<AbstractDeclVar> {

    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("Not yet implemented");
        for (AbstractDeclVar abstractDeclVar : this.getList()) {
            abstractDeclVar.decompile(s);
        }
    }

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the "env_exp_r" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    void verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                for (AbstractDeclVar abstractDeclVar : this.getList()) {
                    abstractDeclVar.verifyDeclVar(compiler, localEnv, currentClass);
                }
    }

    /**
     * Generates code for the declaration of variables
     * @param compiler
     * @param offsetSP
     * @param reg
     * @return index, maximum register used, maximum push used
     */
    public int[] codeGenListDeclVar(DecacCompiler compiler, int offsetSP, Register reg) {
        int[] res = {0, 0, 0};
        int j = 0;
        for (AbstractDeclVar i : getList()) {
            int[] resDecl = i.codeGenDeclVar(compiler, j + offsetSP, reg); // {maxReg, maxPush}
            if (resDecl[0] > res[1]) {
                res[1] = resDecl[0];
            }
            if (resDecl[1] > res[2]) {
                res[2] = resDecl[1];
            }
            j++;
        }
        res[0] = j; // {nbVar, maxReg, maxPush}
        return res;
    }
}
