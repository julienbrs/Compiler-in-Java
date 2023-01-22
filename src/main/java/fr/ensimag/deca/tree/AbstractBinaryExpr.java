package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Binary expressions.
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {
    
    /**
     * Gets left operand of an equation.
     * @return left operand of an equation
     */
    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    /**
     * Gets right operand of an equation.
     * @return right operand of an equation
     */
    public AbstractExpr getRightOperand() {
        return rightOperand;
    }

    /**
     * Sets left operand of an equation.
     * @param leftOperand
     */
    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    /**
     * Sets right operand of an equation.
     * @param rightOperand
     */
    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    /**
     * Verifies values of operands and sets them.
     * @param leftOperand
     * @param rightOperand
     */
    public AbstractBinaryExpr(AbstractExpr leftOperand,
            AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    /**
     * Generates the assembly code to compute left operand.
     * @param compiler
     * @param offset
     * @return left operand
     */
    protected int[] codeGenLeftOperande(DecacCompiler compiler, int offset) {
        return leftOperand.codeGenExpr(compiler, offset);
    }

    /**
     * Generates the assembly code to compute right operand.
     * @param compiler
     * @param offset
     * @return right operand
     */
    protected int[] codeGenRightOperande(DecacCompiler compiler, int offset) {
        return rightOperand.codeGenExpr(compiler, offset);
    }

    /**
     * // TODO
     * @param compiler
     * @param offset
     * @return // TODO
     */
    protected int[] codeGenOperande(DecacCompiler compiler, int offset) {
        int[] res = {0, 0, 0}; // {offset, maxReg ,maxPush}
        int[] resLeft = codeGenLeftOperande(compiler, offset); // {maxReg, maxPush}
        if (offset == compiler.getCompilerOptions().getRmax() - 1) {
            compiler.addInstruction(new PUSH(GPRegister.getR(offset)));
            int[] resRight = codeGenRightOperande(compiler, offset);
            compiler.addInstruction(new LOAD(GPRegister.getR(offset), GPRegister.R0));
            compiler.addInstruction(new POP(GPRegister.getR(offset)));
            res[1] = Math.max(resLeft[0], resRight[0]);
            res[2] = Math.max(resLeft[1], resRight[1] + 1);
            return res;
        } else {
            int[] resRight = codeGenRightOperande(compiler, offset + 1);
            res[0] = offset + 1;
            res[1] = Math.max(resLeft[0], resRight[0]);
            res[2] = Math.max(resLeft[1], resRight[1]);
            return res;
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        getLeftOperand().decompile(s);
        s.print(" " + getOperatorName() + " ");
        getRightOperand().decompile(s);
        s.print(")");
    }

    /**
     * Gets the name of the operator
     * @return the name of the operator
     */
    abstract protected String getOperatorName();

    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iter(f);
        rightOperand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        rightOperand.prettyPrint(s, prefix, true);
    }

}
