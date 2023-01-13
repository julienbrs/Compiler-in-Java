package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractOpIneq extends AbstractOpCmp {

    public AbstractOpIneq(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        Type lt = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rt = getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        /* Si au moins l'un des deux n'est ni un entier ni un flottant
         * alors l'opération Ineq n'est pas faisable */
        if ((!lt.isInt() && !lt.isFloat()) || (!rt.isInt() && !rt.isFloat())) {
            // ERROR MSG
            throw new ContextualError("Can't do \""+getOperatorName()+"\" between \""+lt+"\" and \""+rt+"\": rule 3.33", getLocation());
        }
        /* Si l'un des deux est un entier alors que l'autre est un flottant
         * alors il faut faire un cast */
        if (lt.isInt() && rt.isFloat()) {
            /* On cast lt en Float */
            Location loc = getLeftOperand().getLocation();
            setLeftOperand(new ConvFloat(getLeftOperand()));
            getLeftOperand().setLocation(loc);
            getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        }
        if (lt.isFloat() && rt.isInt()) {
            /* On cast lt en Float */
            Location loc = getLeftOperand().getLocation();
            setRightOperand(new ConvFloat(getRightOperand()));
            getLeftOperand().setLocation(loc);
            getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        }

        setType(compiler.environmentType.BOOLEAN);
        return this.getType();
    }
}
