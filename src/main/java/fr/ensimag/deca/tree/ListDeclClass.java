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
     * Pass 1 of [SyntaxeContextuelle]
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
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        for (AbstractDeclClass abstractDeclClass : this.getList()) {
            abstractDeclClass.verifyClassMembers(compiler);
        }
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        for (AbstractDeclClass abstractDeclClass : this.getList()) {
            abstractDeclClass.verifyClassBody(compiler);
        }
    }

    public int codeGenVTable(DecacCompiler compiler) {
        int offset = 3;
        // Ajout de "null"
        compiler.addInstruction(new LOAD(new NullOperand(), GPRegister.getR(2)));
        compiler.addInstruction(new STORE(GPRegister.getR(2), new RegisterOffset(1, GPRegister.GB)));
        // Ajout de "equals"
        compiler.addInstruction(new LOAD(new LabelOperand(new Label("code.Object.equals")), GPRegister.getR(2)));
        compiler.addInstruction(new STORE(GPRegister.getR(2), new RegisterOffset(2, GPRegister.GB)));
        for (AbstractDeclClass abstractDeclClass : this.getList()) {
            offset += abstractDeclClass.codeGenVTable(compiler, offset);
        }
        return offset + 1;
    }

    public void codeGenBody(DecacCompiler compiler) {
        for (AbstractDeclClass abstractDeclClass : getList()) {
            abstractDeclClass.codeGenBody(compiler);
        }
    }

}
