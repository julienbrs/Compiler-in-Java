package fr.ensimag.deca.tree;

import java.io.PrintStream;
import java.util.Iterator;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;

/**
 * Instruction
 *
 * @author gl11
 * @date 16/01/2023
 */
public class MethodCall extends AbstractExpr {

    private AbstractExpr expr;
    private AbstractIdentifier methodIdent;
    private ListExpr rValStar;

    public MethodCall(AbstractExpr expr, AbstractIdentifier methodIdent, ListExpr rValStar) {
        this.expr = expr;
        this.methodIdent = methodIdent;
        this.rValStar = rValStar;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        ExpDefinition def;
        EnvironmentExp envExp2;
        if (expr == null) {
            envExp2 = currentClass.getMembers();
            def = envExp2.get(methodIdent.getName());
        } else {
            // ERROR MSG
            ClassType e = expr.verifyExpr(compiler, localEnv, currentClass).asClassType("", getLocation());
            envExp2 = e.getDefinition().getMembers();
            def = envExp2.get(methodIdent.getName());
        }
        // ERROR MSG
        if (def == null) {
            // ERROR MSG
            throw new ContextualError("", getLocation());
        }
        methodIdent.verifyExpr(compiler, envExp2, currentClass);
        MethodDefinition mDef = def.asMethodDefinition("", getLocation());
        Type t = mDef.getType();
        Signature sig = mDef.getSignature();
        if (sig.size() != rValStar.size()) {
            // ERROR MSG
            throw new ContextualError("", getLocation());
        }
        Iterator<Type> ite = sig.iterator();
        for (AbstractExpr absExpr : rValStar.getList()) {
            Type expType = ite.next();
            absExpr.verifyRValue(compiler, localEnv, currentClass, expType);
        }
        setType(t);
        return getType();
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        compiler.addInstruction(new ADDSP(new ImmediateInteger(rValStar.size() + 1)));
        expr.codeGenExpr(compiler, offset);
        compiler.addInstruction(new STORE(GPRegister.getR(offset), new RegisterOffset(0, GPRegister.SP)));
        int index = 1;
        for (AbstractExpr abstractExpr : rValStar.getList()) {
            abstractExpr.codeGenExpr(compiler, offset);
            compiler.addInstruction(new STORE(GPRegister.getR(offset), new RegisterOffset(-index, GPRegister.SP)));
            index++;
        }
        compiler.addInstruction(new LOAD(new RegisterOffset(0, GPRegister.SP), GPRegister.getR(offset)));
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new CMP(new NullOperand(), GPRegister.getR(offset)));
            compiler.addInstruction(new BEQ(new Label("dereferencement_null")));
        }
        compiler.addInstruction(new LOAD(new RegisterOffset(0, GPRegister.getR(offset)), GPRegister.getR(offset)));
        compiler.addInstruction(new BSR(new RegisterOffset(methodIdent.getMethodDefinition().getIndex(), GPRegister.getR(offset))));
        compiler.addInstruction(new SUBSP(new ImmediateInteger(rValStar.size() + 1)));
        return rValStar.size() + 3;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (!expr.isImplicit()) {
            expr.decompile(s);
            s.print(".");
        }
        methodIdent.decompile(s);
        s.print("(");
        rValStar.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        methodIdent.prettyPrint(s, prefix, false);
        rValStar.prettyPrint(s, prefix, true);

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        methodIdent.iter(f);
        rValStar.iter(f);
    }

}