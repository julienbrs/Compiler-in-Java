package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;

/**
 * And
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class And extends AbstractOpBool {

    /**
     * Sets the operands to the and operation
     * @param leftOperand
     * @param rightOperand
     */
    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }
    
    @Override
    protected int[] codeGenBool(DecacCompiler compiler, boolean aim, Label dest, int offset) {
        if (aim) {
            int labelNumber = compiler.getLabelNumber();
            compiler.incrLabelNumber();
            Label andEnd = new Label("And_end."+labelNumber);
            int[] resLeft = getLeftOperand().codeGenBool(compiler, false, andEnd, offset);
            int[] resRight = getRightOperand().codeGenBool(compiler, true, dest, offset);
            compiler.addLabel(andEnd);
            int[] res = {Math.max(resLeft[0], resRight[0]), Math.max(resLeft[1], resRight[1])};
            return res;
        } else {
            int[] resLeft = getLeftOperand().codeGenBool(compiler, false, dest, offset);
            int[] resRight = getRightOperand().codeGenBool(compiler, false, dest, offset);
            int[] res = {Math.max(resLeft[0], resRight[0]), Math.max(resLeft[1], resRight[1])};
            return res;
        }
    }

}
