package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 * @author gl11
 * @date 01/01/2023
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        int[] resOp = codeGenOperande(compiler, offset); // {offset, maxReg, maxPush}
        compiler.addInstruction(new MUL(GPRegister.getR(resOp[0]), GPRegister.getR(offset)));
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(new Label("debordement_arithmetique")));
        }
        int[] res = {resOp[1], resOp[2]};
        return res;
    }
}
