package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class ConvFloat extends AbstractUnaryExpr {

    /**
     * Sets the operand from expression to convert to float
     * @param operand
     */
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
        // throw new UnsupportedOperationException("not yet implemented");
        setType(compiler.environmentType.FLOAT);
        return this.getType();
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        int[] res = getOperand().codeGenExpr(compiler, offset);
        compiler.addInstruction(new FLOAT(GPRegister.getR(offset), GPRegister.getR(offset)));
        return res;
    }

    @Override
    protected String getOperatorName() {
        /* Code inaccessible à partir d'un .deca */
        return "/* conv float */";
    }

}
