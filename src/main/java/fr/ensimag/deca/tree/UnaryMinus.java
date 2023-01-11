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
            throw new ContextualError("Can't apply UnaryMinus on \""+t+"\" type : rule ?.??", getLocation());
        }
        setType(t);
        return this.getType();
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int offset) {
        codeGenOperande(compiler, offset);
        compiler.addInstruction(new OPP(GPRegister.getR(offset), GPRegister.getR(offset)));
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

}
