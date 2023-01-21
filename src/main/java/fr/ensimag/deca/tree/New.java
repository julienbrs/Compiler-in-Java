package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.NEW;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Instruction
 *
 * @author gl11
 * @date 16/01/2023
 */
public class New extends AbstractExpr {

    public New(AbstractIdentifier type) {
        this.type = type;
    }

    private AbstractIdentifier type;

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type t = type.verifyType(compiler);
        if (!t.isClass()) {
            // ERROR MSG
            throw new ContextualError("Can't do \"new\" on \"" + t + "\" : rule 3.42", getLocation());
        }
        setType(t);
        return getType();
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        compiler.addInstruction(new NEW(type.getClassDefinition().getNumberOfFields() + 1, GPRegister.getR(offset)));
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(new Label("tas_plein")));
        }
        compiler.addInstruction(new LEA(type.getClassDefinition().getOperand(), GPRegister.R0));
        compiler.addInstruction(new STORE(GPRegister.R0, new RegisterOffset(0, GPRegister.getR(offset))));
        compiler.addInstruction(new PUSH(GPRegister.getR(offset)));
        compiler.addInstruction(new BSR(new Label("init." + type.getName())));
        compiler.addInstruction(new POP(GPRegister.getR(offset)));
        int[] res = {offset, 3};
        return res;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        type.decompile(s);
        s.print("()");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, true);        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
    }

    
}