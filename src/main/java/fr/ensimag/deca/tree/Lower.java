package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SLT;
/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<";
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        int[] res = codeGenOperande(compiler, offset);
        compiler.addInstruction(new CMP(GPRegister.getR(res[0]), GPRegister.getR(offset)));
        compiler.addInstruction(new SLT(GPRegister.getR(offset)));
        return res[1];
    }

    @Override
    protected int codeGenBool(DecacCompiler compiler, boolean aim, Label dest) {
        int[] res = codeGenOperande(compiler, 2);
        compiler.addInstruction(new CMP(GPRegister.getR(res[0]), GPRegister.getR(2)));
        if (aim) {
            compiler.addInstruction(new BLT(dest));
        } else {
            compiler.addInstruction(new BGE(dest));
        }
        return res[1];
    }
}
