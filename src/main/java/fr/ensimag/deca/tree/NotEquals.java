package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SNE;
/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "!=";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int offset) {
        Register r = codeGenOperande(compiler, offset);
        compiler.addInstruction(new CMP(r, GPRegister.getR(offset)));
        compiler.addInstruction(new SNE(GPRegister.getR(offset)));
    }

    @Override
    protected void codeGenBool(DecacCompiler compiler, boolean aim, Label dest) {
        Register r = codeGenOperande(compiler, 2);
        compiler.addInstruction(new CMP(r, GPRegister.getR(2)));
        compiler.addInstruction(new BNE(dest));
    }
}
