package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BGT;
import fr.ensimag.ima.pseudocode.instructions.BLE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SLE;
/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class LowerOrEqual extends AbstractOpIneq {
    public LowerOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<=";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int offset) {
        Register r = codeGenOperande(compiler, offset);
        compiler.addInstruction(new CMP(r, GPRegister.getR(offset)));
        compiler.addInstruction(new SLE(GPRegister.getR(offset)));
    }

    @Override
    protected void codeGenBool(DecacCompiler compiler, boolean aim, Label dest) {
        Register r = codeGenOperande(compiler, 2);
        compiler.addInstruction(new CMP(r, GPRegister.getR(2)));
        if (aim) {
            compiler.addInstruction(new BLE(dest));
        } else {
            compiler.addInstruction(new BGT(dest));
        }
    }
}
