package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.REM;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type lt = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rt = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!lt.isInt() || !rt.isInt()) {
            // ERROR MSG
            throw new ContextualError("Can't do \""+getOperatorName()+"\" between \""+lt+"\" and \""+rt+"\": rule 3.33", getLocation());
        }
        setType(compiler.environmentType.INT);
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        int[] res = codeGenOperande(compiler, offset);
        compiler.addInstruction(new REM(GPRegister.getR(res[0]), GPRegister.getR(offset)));
        compiler.addInstruction(new BOV(new Label("division_par_0")));
        return res[1];
    }
}
