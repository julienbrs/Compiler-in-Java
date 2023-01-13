package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractOpBool extends AbstractBinaryExpr {

    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        Type lt = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rt = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!lt.isBoolean() || !rt.isBoolean()) {
            // ERROR MSG
            throw new ContextualError("Can't do \""+getOperatorName()+"\" between \""+lt+"\" and \""+rt+"\": rule 3.33", getLocation());
        }
        setType(compiler.environmentType.BOOLEAN);
        return this.getType();
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        int labelNumber = compiler.getLabelNumber();
        Label vrai = new Label("is_true."+labelNumber);
        Label end = new Label("end."+labelNumber);
        compiler.incrLabelNumber();
        compiler.incrLabelNumber();
        int nbPush = codeGenBool(compiler, true, vrai);
        compiler.addInstruction(new LOAD(0, GPRegister.getR(offset)));
        compiler.addInstruction(new BRA(end));
        compiler.addLabel(vrai);
        compiler.addInstruction(new LOAD(1, GPRegister.getR(offset)));
        compiler.addLabel(end);
        return nbPush;
    }

}
