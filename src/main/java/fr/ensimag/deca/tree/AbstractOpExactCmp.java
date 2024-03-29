package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Comparing operations (==, !=)
 * 
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractOpExactCmp extends AbstractOpCmp {

    /**
     * Gets left and right operand
     * @param leftOperand
     * @param rightOperand
     */
    public AbstractOpExactCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        Type lt = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rt = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        if (lt.isBoolean() && rt.isBoolean()) {
            setType(compiler.environmentType.BOOLEAN);
            return this.getType();
        }
        if (lt.isInt() && rt.isArray() || lt.isArray() && rt.isNull()) {
            setType(compiler.environmentType.BOOLEAN);
            return this.getType();
        }
        if ((!lt.isInt() && !lt.isFloat()) || (!rt.isInt() && !rt.isFloat())) {
            // ERROR MSG
            throw new ContextualError("Can't do \""+getOperatorName()+"\" between \""+lt+"\" and \""+rt+"\": rule 3.33", getLocation());
        } 
        if (lt.isInt() && rt.isFloat()) {
            Location loc = getLeftOperand().getLocation();
            setLeftOperand(new ConvFloat(getLeftOperand()));
            getLeftOperand().setLocation(loc);
            getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        }
        if (lt.isFloat() && rt.isInt()) {
            Location loc = getLeftOperand().getLocation();
            setRightOperand(new ConvFloat(getRightOperand()));
            getLeftOperand().setLocation(loc);
            getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        }
        setType(compiler.environmentType.BOOLEAN);
        return this.getType();
    }
}
