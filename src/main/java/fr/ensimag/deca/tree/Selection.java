package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;

public class Selection extends AbstractSelection {
    private AbstractExpr expr;
    private AbstractIdentifier ident;

    public Selection(AbstractExpr expr, AbstractIdentifier ident) {
        this.expr = expr;
        this.ident = ident;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        ident.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // ERROR MSG
        ClassType t = expr.verifyExpr(compiler, localEnv, currentClass).asClassType("", getLocation());
        ident.verifyExpr(compiler, t.getDefinition().getMembers(), currentClass);
        FieldDefinition def = ident.getFieldDefinition();
        if (def.getVisibility().equals(Visibility.PROTECTED)) {
            // ERROR MSG
            if (!t.isSubClassOf(currentClass.getType()) || !currentClass.getType().isSubClassOf(def.getType().asClassType("", getLocation()))) {
                // ERROR MSG
                throw new ContextualError("", getLocation());
            }
        }
        setType(def.getType());
        return getType();
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Type verifyLValue(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        return verifyExpr(compiler, localEnv, currentClass);
    }
    
}
