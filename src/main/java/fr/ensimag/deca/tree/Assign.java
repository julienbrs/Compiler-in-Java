package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import org.antlr.v4.runtime.misc.Triple;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        Type lt = getLeftOperand().verifyLValue(compiler, localEnv, currentClass);
        setRightOperand(getRightOperand().verifyRValue(compiler, localEnv, currentClass, lt));
        setType(lt);
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        Triple<Integer, Integer, DAddr> res = getLeftOperand().codeGenLValue(compiler, offset);
        int nbPush = res.a;
        offset = res.b;
        Register reg;
        if (offset == compiler.getCompilerOptions().getRmax()) {
            offset--;
            compiler.addInstruction(new PUSH(GPRegister.getR(offset)));
            int nbRightPush = codeGenRightOperande(compiler, offset);
            nbPush = Math.max(nbPush, nbRightPush);
            compiler.addInstruction(new LOAD(GPRegister.getR(offset), GPRegister.R0));
            reg = GPRegister.R0;
            compiler.addInstruction(new POP(GPRegister.getR(offset)));
        } else {
            nbPush = codeGenRightOperande(compiler, offset);
            reg = GPRegister.getR(offset);
        }
        compiler.addInstruction(new STORE(reg, res.c));
        return nbPush;
    }

}
