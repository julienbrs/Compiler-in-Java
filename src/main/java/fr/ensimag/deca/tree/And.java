package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BNE;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }
    
    @Override
    protected int codeGenBool(DecacCompiler compiler, boolean aim, Label dest) {
        if (aim) {
            int labelNumber = compiler.getLabelNumber();
            compiler.incrLabelNumber();
            Label andEnd = new Label("And_end."+labelNumber);
            int nbLeftPush = getLeftOperand().codeGenBool(compiler, false, andEnd);
            int nbRightPush = getRightOperand().codeGenBool(compiler, true, dest);
            compiler.addLabel(andEnd);
            return Math.max(nbLeftPush, nbRightPush);
        } else {
            int nbLeftPush = getLeftOperand().codeGenBool(compiler, false, dest);
            int nbRightPush = getRightOperand().codeGenBool(compiler, false, dest);
            return Math.max(nbLeftPush, nbRightPush);
        }
    }

}
