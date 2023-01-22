package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Program extends AbstractProgram {

    private static final Logger LOG = Logger.getLogger(Program.class);
    
    /**
     * Verifies that classes and main are not null before setting values
     * @param classes
     * @param main
     */
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }

    /**
     * Gets classes from the program
     * @return
     */
    public ListDeclClass getClasses() {
        return classes;
    }

    /**
     * Gets main of the program
     * @return
     */
    public AbstractMain getMain() {
        return main;
    }

    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify program: start");
        // throw new UnsupportedOperationException("not yet implemented");

        // 1ere passe
        classes.verifyListClass(compiler);
        
        // 2eme passe
        classes.verifyListClassMembers(compiler);

        // 3eme passe
        classes.verifyListClassBody(compiler);
        main.verifyMain(compiler);

        LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        // A FAIRE: compléter ce squelette très rudimentaire de code
        Line lTSTO = new Line("");
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.add(lTSTO);
            compiler.addInstruction(new BOV(new Label("pile_pleine")));
        }
        
        Line lADDSP = new Line("");
        compiler.add(lADDSP);

        compiler.add(new Line(""));
        compiler.addComment("Generation table des methodes");
        int nbMethod = classes.codeGenVTable(compiler);

        compiler.add(new Line(""));
        compiler.addComment("Main program");
        int[] res = main.codeGenMain(compiler, nbMethod); // {maxReg, nbDecl, nbDecl + nbPush}
        
        lADDSP.setInstruction(new ADDSP(new ImmediateInteger(res[1])));
        lTSTO.setInstruction(new TSTO(res[2]));
        compiler.addInstruction(new HALT()); 

        compiler.add(new Line(""));
        compiler.addComment("Methodes de classe");

        compiler.addLabel(new Label("code.Object.equals"));
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new TSTO(new ImmediateInteger(2)));
            compiler.addInstruction(new BOV(new Label("pile_pleine")));
        }    
        compiler.addInstruction(new PUSH(GPRegister.getR(2)));
        compiler.addInstruction(new PUSH(GPRegister.getR(3)));
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, GPRegister.LB), GPRegister.getR(2)));
        compiler.addInstruction(new LOAD(new RegisterOffset(-3, GPRegister.LB), GPRegister.getR(3)));
        compiler.addInstruction(new CMP(GPRegister.getR(2), GPRegister.getR(3)));
        compiler.addInstruction(new SEQ(GPRegister.R0));
        compiler.addInstruction(new POP(GPRegister.getR(3)));
        compiler.addInstruction(new POP(GPRegister.getR(2)));
        compiler.addInstruction(new RTS());
        
        classes.codeGenBody(compiler);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
