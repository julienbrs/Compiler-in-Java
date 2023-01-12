package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.SNE;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        Type t = getOperand().verifyExpr(compiler, localEnv, currentClass);
        if (t != compiler.environmentType.BOOLEAN) {
            // ERROR MSG
            throw new ContextualError("Can't negate an object of type \""+t+"\" : rule ?.??", getLocation());
        }
        setType(t);
        return this.getType();
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int offset) {
        getOperand().codeGenExpr(compiler, offset);
        compiler.addInstruction(new SNE(GPRegister.getR(offset)));
    }

    @Override
    protected void codeGenBool(DecacCompiler compiler, boolean aim, Label dest) {
        getOperand().codeGenBool(compiler, !aim, dest);
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }
}
