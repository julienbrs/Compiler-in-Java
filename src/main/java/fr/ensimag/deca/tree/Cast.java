package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Instruction
 *
 * @author gl11
 * @date 16/01/2023
 */
public class Cast extends AbstractExpr {

    private AbstractIdentifier type;
    private AbstractExpr expr;

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type t = type.verifyType(compiler);
        Type e = expr.verifyExpr(compiler, localEnv, currentClass);
        if (t.isVoid()) {
            // ERROR MSG
            throw new ContextualError("t1 non void : rule 3.??", getLocation());
        }
        
        if (t.sameType(e)) {
            setType(t);
            return getType();
        } else if (t.isInt() && e.isFloat()) {
            setType(t);
            return getType();
        } else if ((t.isFloat() && e.isInt())) {
            setType(t);
            return getType();
        } else if (t.isNull() && e.isClass()) {
            setType(t);
            return getType();
        } else if (t.isClass() && e.isNull()) {
            setType(t);
            return getType();
        } else {
            // ERROR MSG
            ClassType tClass = t.asClassType(" rule 3.??", getLocation());
            // ERROR MSG
            ClassType eClass = e.asClassType(" rule 3.??", getLocation());
            if (tClass.isSubClassOf(eClass)) {
                setType(t);
                return getType();
            } else if (eClass.isSubClassOf(tClass)) {
                setType(t);
                return getType();
            }
        }
        // ERROR MSG
        throw new ContextualError(" rule 3.??", getLocation());
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }

    
}