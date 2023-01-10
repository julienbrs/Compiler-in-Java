package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int offset) {
        // codeGenBool(compiler, , );
        // SEQ;
    }

    @Override
    protected void codeGenBool(DecacCompiler compiler, boolean aim, Label dest) {
        if (aim) {
            int labelNumber = compiler.getLabelNumber();
            compiler.incrLabelNumber();
            Label orEnd = new Label("Or_end."+labelNumber);
            getLeftOperand().codeGenBool(compiler, true, orEnd);
            getRightOperand().codeGenBool(compiler, false, dest);
            compiler.addLabel(orEnd);
        } else {
            getLeftOperand().codeGenBool(compiler, true, dest);
            getRightOperand().codeGenBool(compiler, true, dest);
        }
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }


}
