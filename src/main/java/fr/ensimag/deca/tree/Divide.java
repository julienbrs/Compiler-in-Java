package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.QUO;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        int[] res = codeGenOperande(compiler, offset);
        assert(getType().isInt() || getType().isFloat());
        if (getType().isInt()) {
            compiler.addInstruction(new QUO(GPRegister.getR(res[0]), GPRegister.getR(offset)));
            if (!compiler.getCompilerOptions().getNoCheck()) {
                compiler.addInstruction(new BOV(new Label("division_par_0")));
            }
        } else {
            compiler.addInstruction(new DIV(GPRegister.getR(res[0]), GPRegister.getR(offset)));
            if (!compiler.getCompilerOptions().getNoCheck()) {
                compiler.addInstruction(new BOV(new Label("debordement_arithmetique")));
            }
        }
        return res[1];
    }
}
