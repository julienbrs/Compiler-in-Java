package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Initialization
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class Initialization extends AbstractInitialization {

    /**
     * Gets expression to initialize
     * @return expresssion
     */
    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    /**
     * Verifies that the value of expression is not null and sets its value
     * @param expression
     */
    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    /**
     * Declares an expression and verifies that its value is not null
     * @param expression
     */
    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        setExpression(getExpression().verifyRValue(compiler, localEnv, currentClass, t));
        setType(t);
    }

    @Override
    protected int[] codeGenInitialization(DecacCompiler compiler, int offset) {
        return expression.codeGenExpr(compiler, offset); // {maxReg, maxPush}
    }

    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("Not yet implemented");
        s.print(" = ");
        expression.decompile(s);
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }
}
