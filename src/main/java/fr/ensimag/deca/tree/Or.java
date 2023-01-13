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
    protected int codeGenBool(DecacCompiler compiler, boolean aim, Label dest) {
        int nbLeftPush;
        int nbRightPush;
        if (aim) {
            int labelNumber = compiler.getLabelNumber();
            compiler.incrLabelNumber();
            Label orEnd = new Label("Or_end."+labelNumber);
            nbLeftPush = getLeftOperand().codeGenBool(compiler, true, orEnd);
            nbRightPush = getRightOperand().codeGenBool(compiler, false, dest);
            compiler.addLabel(orEnd);
        } else {
            nbLeftPush = getLeftOperand().codeGenBool(compiler, true, dest);
            nbRightPush = getRightOperand().codeGenBool(compiler, true, dest);
        }
        return Math.max(nbLeftPush, nbRightPush);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }


}
