package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Main
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    private ListDeclVar declVariables;
    private ListInst insts;
    
    /**
     * Verifies that variables and instructions are not null. Declares those two parameters.
     * @param declVariables
     * @param insts
     */
    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify Main: start");

        EnvironmentExp localEnv = new EnvironmentExp(null);
        declVariables.verifyListDeclVariable(compiler, localEnv, null);
        insts.verifyListInst(compiler, localEnv, null, compiler.environmentType.VOID);
        
        LOG.debug("verify Main: end");
        
        // throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected int[] codeGenMain(DecacCompiler compiler, int offsetGP) {
                
        compiler.addComment("Variables declarations:");
        int[] resDecl = declVariables.codeGenListDeclVar(compiler, offsetGP, GPRegister.GB);
        offsetGP += resDecl[0];
        
        compiler.addComment("Beginning of main instructions:");
        int[] resInst = insts.codeGenListInst(compiler); // {maxReg, maxPush}
        
        int[] res = {Math.max(resInst[0], resDecl[1]), offsetGP - 1, offsetGP - 1 + Math.max(resInst[1], resDecl[2])}; // {maxReg, nbDecl, nbDecl + nbPush}
        return res;
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
