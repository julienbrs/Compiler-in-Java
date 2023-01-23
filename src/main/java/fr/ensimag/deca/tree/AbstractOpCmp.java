package fr.ensimag.deca.tree;

/**
 * Comparing operation (exact and inexact)
 * 
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    /**
     * Gets left and right operand
     * @param leftOperand
     * @param rightOperand
     */
    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
}
