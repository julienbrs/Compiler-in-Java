package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import org.apache.log4j.Logger;

/**
 * ListDeclClass
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {

    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Passe 1 of Contextual syntax for class
     * @param compiler
     * @throws ContextualError
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");
        // throw new UnsupportedOperationException("not yet implemented");
        for (AbstractDeclClass abstractDeclClass : this.getList()) {
            abstractDeclClass.verifyClass(compiler);
        }
        LOG.debug("verify listClass: end");
    }

    /**
     * Passe 2 of Contextual syntax for class members
     * @param compiler
     * @throws ContextualError
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        for (AbstractDeclClass abstractDeclClass : this.getList()) {
            abstractDeclClass.verifyClassMembers(compiler);
        }
    }
    
    /**
     * Passe 3 of Contextual syntax for class body
     * @param compiler
     * @throws ContextualError
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        for (AbstractDeclClass abstractDeclClass : this.getList()) {
            abstractDeclClass.verifyClassBody(compiler);
        }
    }

    /**
     * Construction of the table of method labels;
     * Generation of code to build the table of methods.
     * @param compiler
     * @return offset
     */
    public int codeGenVTable(DecacCompiler compiler) {
        int offset = 3;
        compiler.addComment("Table methodes Object");
        // Ajout de "null"
        compiler.addInstruction(new LOAD(new NullOperand(), GPRegister.getR(2)));
        compiler.addInstruction(new STORE(GPRegister.getR(2), new RegisterOffset(1, GPRegister.GB)));
        // Ajout de "equals"
        compiler.addInstruction(new LOAD(new LabelOperand(new Label("code.Object.equals")), GPRegister.getR(2)));
        compiler.addInstruction(new STORE(GPRegister.getR(2), new RegisterOffset(2, GPRegister.GB)));
        for (AbstractDeclClass abstractDeclClass : this.getList()) {
            offset += abstractDeclClass.codeGenVTable(compiler, offset);
        }
        return offset;
    }

    /**
     * Generates code to the list of declared class
     * @param compiler
     */
    public void codeGenBody(DecacCompiler compiler) {
        for (AbstractDeclClass abstractDeclClass : getList()) {
            abstractDeclClass.codeGenBody(compiler);
        }
    }

}
