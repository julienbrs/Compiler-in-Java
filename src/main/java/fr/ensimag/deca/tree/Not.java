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
            throw new ContextualError("Can't negate an object of type \""+t+"\" : rule 3.63", getLocation());
        }
        setType(t);
        return this.getType();
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        int labelNumber = compiler.getLabelNumber();
        Label vrai = new Label("is_true."+labelNumber);
        Label end = new Label("end."+labelNumber);
        compiler.incrLabelNumber();
        compiler.incrLabelNumber();
        int[] res = codeGenBool(compiler, true, vrai, offset);
        compiler.addInstruction(new LOAD(0, GPRegister.getR(offset)));
        compiler.addInstruction(new BRA(end));
        compiler.addLabel(vrai);
        compiler.addInstruction(new LOAD(1, GPRegister.getR(offset)));
        compiler.addLabel(end);
        return res;
    }

    @Override
    protected int[] codeGenBool(DecacCompiler compiler, boolean aim, Label dest, int offset) {
        return getOperand().codeGenBool(compiler, !aim, dest, offset);
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }
}
