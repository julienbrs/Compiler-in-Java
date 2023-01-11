package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        Type lt = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rt = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        // TODO
        // ERROR MSG : test des type autre que int et float
        // "Can't do \""+getOperandeName()+"\" between \""+lt+"\" and \""+rt+"\": rule 3.53"
        if (lt.isInt() && rt.isInt()) {
            setType(compiler.environmentType.INT);
        } else {
            setType(compiler.environmentType.FLOAT);
            if (lt.isInt()) {
                Location loc = getLeftOperand().getLocation();
                setLeftOperand(new ConvFloat(getLeftOperand()));
                getLeftOperand().setLocation(loc);
                getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
            }
            if (rt.isInt()) {
                Location loc = getLeftOperand().getLocation();
                setRightOperand(new ConvFloat(getRightOperand()));
                getLeftOperand().setLocation(loc);
                getRightOperand().verifyExpr(compiler, localEnv, currentClass);
            }
        }
        return this.getType();
    }
}
