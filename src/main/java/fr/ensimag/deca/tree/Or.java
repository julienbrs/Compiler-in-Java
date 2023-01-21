package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;

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
    protected int[] codeGenBool(DecacCompiler compiler, boolean aim, Label dest, int offset) {
        int[] resLeft;
        int[] resRight;
        if (aim) {    
            resLeft = getLeftOperand().codeGenBool(compiler, true, dest, offset);
            resRight = getRightOperand().codeGenBool(compiler, true, dest, offset);
        } else {
            int labelNumber = compiler.getLabelNumber();
            compiler.incrLabelNumber();
            Label orEnd = new Label("Or_end."+labelNumber);
            resLeft = getLeftOperand().codeGenBool(compiler, true, orEnd, offset);
            resRight = getRightOperand().codeGenBool(compiler, false, dest, offset);
            compiler.addLabel(orEnd);
        }
        int[] res = {Math.max(resLeft[0], resRight[0]), Math.max(resLeft[1], resRight[1])};
        return res;
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }


}
