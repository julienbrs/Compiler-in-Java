package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SGE;
/**
 * Operator "x >= y"
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class GreaterOrEqual extends AbstractOpIneq {

    /**
     * Sets the operands for greater or equal inequality
     * @param leftOperand
     * @param rightOperand
     */
    public GreaterOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return ">=";
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        int[] resOp = codeGenOperande(compiler, offset); // {offset, maxReg, maxPush}
        compiler.addInstruction(new CMP(GPRegister.getR(resOp[0]), GPRegister.getR(offset)));
        compiler.addInstruction(new SGE(GPRegister.getR(offset)));
        int[] res = {resOp[1], resOp[2]};
        return res;
    }

    @Override
    protected int[] codeGenBool(DecacCompiler compiler, boolean aim, Label dest, int offset) {
        int[] resOp = codeGenOperande(compiler, offset); // {offset, maxReg, maxPush}
        compiler.addInstruction(new CMP(GPRegister.getR(resOp[0]), GPRegister.getR(offset)));
        if (aim) {
            compiler.addInstruction(new BGE(dest));
        } else {
            compiler.addInstruction(new BLT(dest));
        }
        int[] res = {resOp[1], resOp[2]};
        return res;
    }
}
