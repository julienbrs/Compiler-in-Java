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
        if (expr == null) {
            def = currentClass.getMembers().get(methodIdent.getName());
        } else {
            // ERROR MSG
            ClassType e = expr.verifyExpr(compiler, localEnv, currentClass).asClassType("", getLocation());
            def = e.getDefinition().getMembers().get(methodIdent.getName());
        }
        // ERROR MSG
        if (def == null) {
            // ERROR MSG
            throw new ContextualError("", getLocation());
        }
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
        // TODO Auto-generated method stub
        return 0;
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