package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.antlr.v4.runtime.misc.Triple;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffOffset;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BLE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

public class ArraySel extends AbstractSelection {
    private AbstractExpr selExpr;
    private AbstractExpr indexExpr;
    public ArraySel(AbstractExpr selexpr, AbstractExpr indexExpr) {
        this.selExpr = selexpr;
        this.indexExpr = indexExpr;
    }

    @Override
    public Type verifyLValue(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        return verifyExpr(compiler, localEnv, currentClass);
    }

    @Override
    public Triple<int[], Integer, DAddr> codeGenLValue(DecacCompiler compiler, int offset) {
        int currentOffset = offset;
        int[] resSel = selExpr.codeGenExpr(compiler, currentOffset);
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new CMP(new NullOperand(), GPRegister.getR(currentOffset)));
            compiler.addInstruction(new BEQ(new Label("dereferencement_null")));
        }
        if (offset + 1 == compiler.getCompilerOptions().getRmax()) {
            compiler.addInstruction(new PUSH(GPRegister.getR(currentOffset)));
        } else {
            currentOffset++;
        }
        int[] resInd = indexExpr.codeGenExpr(compiler, currentOffset);
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new LOAD(new RegisterOffset(0, GPRegister.getR(currentOffset - 1)), GPRegister.R1));
            compiler.addInstruction(new CMP(GPRegister.getR(currentOffset), GPRegister.R1));
            compiler.addInstruction(new BLE(new Label("index_hors_range")));
            compiler.addInstruction(new CMP(new ImmediateInteger(0), GPRegister.getR(currentOffset)));
            compiler.addInstruction(new BLT(new Label("index_hors_range")));
        }
        int[] max = {Math.max(resSel[0], resInd[0]), Math.max(resSel[1], resInd[1])};
        DAddr addr;
        if (offset + 1 == compiler.getCompilerOptions().getRmax()) {
            addr = new RegisterOffOffset(1, GPRegister.R0, GPRegister.getR(currentOffset));
            max[1]++;
        } else {
            addr = new RegisterOffOffset(1, GPRegister.getR(offset), GPRegister.getR(currentOffset));
        }
        Triple<int[], Integer, DAddr> res = new Triple<>(max, offset + 2, addr);
        return res;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type selType = selExpr.verifyExpr(compiler, localEnv, currentClass);
        if (!selType.isArray()) {
            // ERROR MSG
            throw new ContextualError("Can't use \"[]\" on \"" + selType + "\" : rule extension", getLocation());
        }
        Type exprType = indexExpr.verifyExpr(compiler, localEnv, currentClass);
        if (!exprType.isInt()) {
            // ERROR MSG
            throw new ContextualError("Can't select from an array with type \"" + exprType + "\" : rule extension", getLocation());
        }
        setType(selType.asArrayType(null, getLocation()).subType());
        return getType();
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        int[] resSel = selExpr.codeGenExpr(compiler, offset);
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new CMP(new NullOperand(), GPRegister.getR(offset)));
            compiler.addInstruction(new BEQ(new Label("dereferencement_null")));
        }
        int currOffset = offset;
        int nextOffset = offset + 1;
        if (offset + 1 == compiler.getCompilerOptions().getRmax()) {
            compiler.addInstruction(new PUSH(GPRegister.getR(offset)));
            currOffset = 0;
            nextOffset = offset;
        }

        int[] resInd = indexExpr.codeGenExpr(compiler, nextOffset);

        if (offset + 1 == compiler.getCompilerOptions().getRmax()) {
            compiler.addInstruction(new POP(GPRegister.R0));
            resInd[1] += 1;
        }
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new LOAD(new RegisterOffset(0, GPRegister.getR(currOffset)), GPRegister.R1));
            compiler.addInstruction(new CMP(GPRegister.getR(nextOffset), GPRegister.R1));
            compiler.addInstruction(new BLE(new Label("index_hors_range")));
            compiler.addInstruction(new CMP(new ImmediateInteger(0), GPRegister.getR(nextOffset)));
            compiler.addInstruction(new BLT(new Label("index_hors_range")));
        }
        compiler.addInstruction(new LOAD(new RegisterOffOffset(1, GPRegister.getR(currOffset), GPRegister.getR(nextOffset)), GPRegister.getR(offset)));
        int[] res = {Math.max(resSel[0], resInd[0]), Math.max(resSel[1], resInd[1])};
        return res;
    }

    @Override
    public int[] codeGenBool(DecacCompiler compiler, boolean aim, Label dest, int offset) {
        int[] res = codeGenExpr(compiler, offset);
        compiler.addInstruction(new CMP(0, GPRegister.getR(offset)));
        if (aim) {
            compiler.addInstruction(new BNE(dest));
        } else {
            compiler.addInstruction(new BEQ(dest));
        }
        return res;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        selExpr.decompile(s);
        s.print("[");
        indexExpr.decompile(s);
        s.print("]");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        selExpr.prettyPrint(s, prefix, false);
        indexExpr.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        selExpr.iter(f);
        indexExpr.iter(f);
        
    }
    
}
