package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

/**
 * Equals
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class Equals extends AbstractOpExactCmp {

    /**
     * Sets the operands for equals
     * @param leftOperand
     * @param rightOperand
     */
    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "==";
    }    

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        int[] resOp = codeGenOperande(compiler, offset); // {offset, maxReg, maxPush}
        compiler.addInstruction(new CMP(GPRegister.getR(resOp[0]), GPRegister.getR(offset)));
        compiler.addInstruction(new SEQ(GPRegister.getR(offset)));
        int[] res = {resOp[1], resOp[2]};
        return res;
    }

    @Override
    protected int[] codeGenBool(DecacCompiler compiler, boolean aim, Label dest, int offset) {
        int[] resOp = codeGenOperande(compiler, offset); // {offset, maxReg, maxPush}
        compiler.addInstruction(new CMP(GPRegister.getR(resOp[0]), GPRegister.getR(offset)));
        if (aim) {
            compiler.addInstruction(new BEQ(dest));
        } else {
            compiler.addInstruction(new BNE(dest));
        }
        int[] res = {resOp[1], resOp[2]};
        return res;
    }
    
}
