package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructions.WINT;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Expression, i.e. anything that has a value.
 *
 * @author gl11
 * @date 01/01/2023
 */
public abstract class AbstractExpr extends AbstractInst {
    /**
     * @return true if the expression does not correspond to any concrete token
     * in the source code (and should be decompiled to the empty string).
     */
    boolean isImplicit() {
        return false;
    }

    /**
     * @return the type decoration associated to this expression (i.e. the type computed by contextual verification).
     */
    public Type getType() {
        return type;
    }

    /**
     * Set the type.
     * 
     * @param type
     */
    protected void setType(Type type) {
        Validate.notNull(type);
        this.type = type;
    }

    private Type type;

    @Override
    protected void checkDecoration() {
        if (getType() == null) {
            throw new DecacInternalError("Expression " + decompile() + " has no Type decoration");
        }
    }

    /**
     * Verify the expression for contextual error.
     * 
     * implements non-terminals "expr" and "lvalue" 
     *    of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @throws ContextualError
     * @return the Type of the expression
     *            (corresponds to the "type" attribute)
     */
    public abstract Type verifyExpr(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Verify the expression in right hand-side of (implicit) assignments 
     * 
     * implements non-terminal "rvalue" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass corresponds to the "class" attribute
     * @param expectedType corresponds to the "type1" attribute     
     * @throws ContextualError       
     * @return this with an additional ConvFloat if needed...
     */
    public AbstractExpr verifyRValue(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass, 
            Type expectedType)
            throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        Type t = verifyExpr(compiler, localEnv, currentClass);
        if (t.sameType(expectedType)) {
            return this;
        } else if (t.isInt() && expectedType.isFloat()) {
            Location loc = getLocation();
            AbstractExpr newThis= new ConvFloat(this);
            newThis.setLocation(loc);
            newThis.verifyExpr(compiler, localEnv, currentClass);
            return newThis;
        } else if (t.isNull() && expectedType.isArray()) {
            return this;
        } else if (t.isNull() && expectedType.isClass()) {
            return this;
        } else {
            // ERROR MSG
            ClassType cType = t.asClassType("Can't assign type : \""+t+"\" to type :\""+expectedType+"\" : rule 3.28", getLocation());
            // ERROR MSG
            ClassType expType = expectedType.asClassType("Can't assign type : \""+t+"\" to type :\""+expectedType+"\" : rule 3.28", getLocation());
            if (cType.isSubClassOf(expType)) {
                return this;
            }
        }
        // ERROR MSG
        throw new ContextualError("Can't assign type : \""+t+"\" to type :\""+expectedType+"\" : rule 3.28", getLocation());
    }
    
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        verifyExpr(compiler, localEnv, currentClass);
        
    }

    /**
     * Verify the expression as a condition, i.e. check that the type is
     * boolean.
     *
     * @param localEnv
     *            Environment in which the condition should be checked.
     * @param currentClass
     *            Definition of the class containing the expression, or null in
     *            the main program.
     */
    void verifyCondition(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        Type t = verifyExpr(compiler, localEnv, currentClass);
        if (!t.isBoolean()) {
            // ERROR MSG
            throw new ContextualError("The condition must be 'boolean' but is of type \""+t+"\" : rule 3.29", getLocation());
        }
    }

    /**
     * Generate code to print the expression
     * @param compiler
     * @param printHex
     * @return {0, 0}
     * 
     */
    protected int[] codeGenPrint(DecacCompiler compiler, boolean printHex) {
        // throw new UnsupportedOperationException("not yet implemented");
        int[] res = codeGenExpr(compiler, 2);
        compiler.addInstruction(new LOAD(GPRegister.getR(2), GPRegister.R1));
        Type t = getType();
        if (t.isInt()) {
            compiler.addInstruction(new WINT());
        } else if (t.isFloat()) {
            if (printHex) {
                compiler.addInstruction(new WFLOATX());
            } else {
                compiler.addInstruction(new WFLOAT());
            }
        }
        return res;
    }

    @Override
    protected int[] codeGenInst(DecacCompiler compiler) {
        // throw new UnsupportedOperationException("not yet implemented");
        return codeGenExpr(compiler, 2);
    }

    /**
     * Generates code for the expression
     * @param compiler
     * @param offset
     * @return maximum register used, maximum push used
     * 
     */
    protected abstract int[] codeGenExpr(DecacCompiler compiler, int offset);
    
    /**
     * Generates code for the boolean type
     * @param compiler
     * @param aim
     * @param dest
     * @param offset
     * @return maximum register used, maximum push used
     * 
     */
    protected int[] codeGenBool(DecacCompiler compiler, boolean aim, Label dest, int offset) {
        throw new UnsupportedOperationException("Should not end up here");
    }

    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }
}
