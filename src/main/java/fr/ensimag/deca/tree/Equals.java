package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "==";
    }    

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int offset) {
        Register r = codeGenOperande(compiler, offset);
        compiler.addInstruction(new CMP(r, GPRegister.getR(offset)));
        compiler.addInstruction(new SEQ(GPRegister.getR(offset)));
    }
    
}
