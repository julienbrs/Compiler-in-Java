package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Unary expression.
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractUnaryExpr extends AbstractExpr {

    /**
     * Gets the operand
     * @return the operand
     */
    public AbstractExpr getOperand() {
        return operand;
    }

    private AbstractExpr operand;

    /**
     * Verifies the operand is not null and sets it value
     * @param operand
     */
    public AbstractUnaryExpr(AbstractExpr operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }

    /**
     * Gets operator name
     * @return operator name
     */
    protected abstract String getOperatorName();
  
    /**
     * // TODO
     * @param compiler
     * @param offset
     * @return // TODO
     */
    protected int[] codeGenOperande(DecacCompiler compiler, int offset) {
        return operand.codeGenExpr(compiler, offset);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("not yet implemented");
        s.print("(");
        s.print(getOperatorName());
        operand.decompile(s);
        s.print(")");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        operand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        operand.prettyPrint(s, prefix, true);
    }

}
