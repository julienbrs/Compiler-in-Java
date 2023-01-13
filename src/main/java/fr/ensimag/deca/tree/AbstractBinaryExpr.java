package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
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

    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    public AbstractExpr getRightOperand() {
        return rightOperand;
    }

    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    public AbstractBinaryExpr(AbstractExpr leftOperand,
            AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    protected int codeGenLeftOperande(DecacCompiler compiler, int offset) {
        return leftOperand.codeGenExpr(compiler, offset);
    }

    protected int codeGenRightOperande(DecacCompiler compiler, int offset) {
        return rightOperand.codeGenExpr(compiler, offset);
    }

    protected int[] codeGenOperande(DecacCompiler compiler, int offset) {
        int[] res = {0, 0};
        int nbLeftPush = codeGenLeftOperande(compiler, offset);
        if (offset == compiler.getCompilerOptions().getRmax()) {
            compiler.addInstruction(new PUSH(GPRegister.getR(offset)));
            int nbRightPush = codeGenRightOperande(compiler, offset);
            compiler.addInstruction(new LOAD(GPRegister.getR(offset), GPRegister.R0));
            compiler.addInstruction(new POP(GPRegister.getR(offset)));
            res[1] = Math.max(nbLeftPush, nbRightPush + 1);
            return res;
        } else {
            int nbRightPush = codeGenRightOperande(compiler, offset + 1);
            res[0] = offset + 1;
            res[1] = Math.max(nbLeftPush, nbRightPush);
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
