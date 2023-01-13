package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Print statement (print, println, ...).
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractPrint extends AbstractInst {

    private boolean printHex;
    private ListExpr arguments = new ListExpr();
    
    abstract String getSuffix();

    public AbstractPrint(boolean printHex, ListExpr arguments) {
        Validate.notNull(arguments);
        this.arguments = arguments;
        this.printHex = printHex;
    }

    public ListExpr getArguments() {
        return arguments;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        for (AbstractExpr abstractExpr : arguments.getList()) {
            Type t = abstractExpr.verifyExpr(compiler, localEnv, currentClass);
            if (!(t.sameType(compiler.environmentType.INT) || t.sameType(compiler.environmentType.FLOAT) || t.sameType(compiler.environmentType.STRING))) {
                // ERROR MSG
                throw new ContextualError("Can't print other than int, float or string : rule 3.31", getLocation());
            }
        }
    }

    @Override
    protected int codeGenInst(DecacCompiler compiler) {
        int maxPush = 0;
        for (AbstractExpr a : getArguments().getList()) {
            int nbPush = a.codeGenPrint(compiler, getPrintHex());
            if (nbPush > maxPush) {
                maxPush = nbPush;
            }
        }
        return maxPush;
    }

    private boolean getPrintHex() {
        return printHex;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("not yet implemented");
        s.print("print" + getSuffix());
        if (getPrintHex()) {
            s.print("x");
        }
        s.print("(");
        arguments.decompile(s);
        s.print(");");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        arguments.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        arguments.prettyPrint(s, prefix, true);
    }

}
