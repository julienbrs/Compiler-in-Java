package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Instruction
 *
 * @author gl11
 * @date 16/01/2023
 */
public class Return extends AbstractInst {

    private AbstractExpr returnExpr;

    public Return(AbstractExpr returnExpr) {
        this.returnExpr = returnExpr;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass,
            Type returnType) throws ContextualError {
        returnExpr = returnExpr.verifyRValue(compiler, localEnv, currentClass, returnType);
        Type t = returnExpr.getType();
        if (t.sameType(compiler.environmentType.VOID)) {
            // ERROR MSG
            throw new ContextualError("Return can't be void : rule 3.24", getLocation());
        }
    }

    @Override
    protected int[] codeGenInst(DecacCompiler compiler) {
        int[] res = returnExpr.codeGenExpr(compiler, 2);
        compiler.addInstruction(new LOAD(GPRegister.getR(2), GPRegister.getR(0)));
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BRA(compiler.getReturnLabel()));
        }
        return res;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        returnExpr.decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnExpr.prettyPrint(s,prefix,true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnExpr.iter(f);
    }
}