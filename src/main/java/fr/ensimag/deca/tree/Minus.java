package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl11
 * @date 01/01/2023
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }
    
    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        int[] res = codeGenOperande(compiler, offset);
        compiler.addInstruction(new SUB(GPRegister.getR(res[0]), GPRegister.getR(offset)));
        if (getType().isFloat() && !compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(new Label("debordement_arithmetique")));
        }
        return res[1];
    }
}
