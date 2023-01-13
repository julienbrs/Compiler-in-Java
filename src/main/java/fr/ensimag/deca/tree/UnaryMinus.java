package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * @author gl11
 * @date 01/01/2023
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        Type t = getOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!t.isInt() && !t.isFloat()) {
            // ERROR MSG
            throw new ContextualError("Can't apply UnaryMinus on \""+t+"\" type : rule 3.62", getLocation());
        }
        setType(t);
        return this.getType();
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        int nbPush = codeGenOperande(compiler, offset);
        compiler.addInstruction(new OPP(GPRegister.getR(offset), GPRegister.getR(offset)));
        return nbPush;
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

}
