package fr.ensimag.deca.tree;

import java.io.PrintStream;
import java.util.Iterator;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ArrayType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.NEW;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Array Literal
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class ArrayLiteral extends AbstractExpr {

    private ListExpr elementab;
    
    /**
     * Constructor by default
     */
    public ArrayLiteral() {
        this.elementab = new ListExpr();
    }

    /**
     * Sets the expression in the array
     * @param elementab
     */
    public ArrayLiteral(AbstractExpr elementab) {
        this.elementab = new ListExpr();
        this.elementab.add(elementab);
    }

    /**
     * Adds an expression to an expression in array
     * @param elementab
     */
    public void addExpr(AbstractExpr elementab){
        this.elementab.add(elementab);
    }

    /**
     * Adds recursively ConvFloat nodes on the array values
     * @throws ContextualError
     */
    public void addConvFloat() throws ContextualError{
        ListExpr newList = new ListExpr();
        for (AbstractExpr expr : elementab.getList()) {
            if (expr.getType().isArray() && expr.getType().asArrayType(null, getLocation()).getEltType().isFloat()) {
                newList.add(expr);
            } else if (expr instanceof ArrayLiteral && expr.getType().asArrayType(null, getLocation()).getEltType().isInt()) {
                ((ArrayLiteral) expr).addConvFloat();
                newList.add(expr);
            } else {
                if (expr.getType().isFloat()) {
                    newList.add(expr);
                } else if (expr.getType().isInt()) {
                    ConvFloat convExpr = new ConvFloat(expr);
                    convExpr.setLocation(expr.getLocation());
                    newList.add(convExpr);
                } else {
                    // ERROR MSG
                    throw new ContextualError("All element of an array must be of same type : rule extension", getLocation());
                }
            }
        }
        elementab = newList;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type t = null;
        Type eltType = null;
        Iterator<AbstractExpr> ite = elementab.getList().iterator();
        AbstractExpr current = null;
        boolean needConv = false;
        if (ite.hasNext()) {
            current = ite.next();
            t = current.verifyExpr(compiler, localEnv, currentClass);
            if (t.isArray()) {
                eltType = t.asArrayType(null, getLocation()).getEltType();
            } else {
                eltType = t;
            }
        } else {
            return new ArrayType(null, t, getLocation(), 0);
        }
        while (ite.hasNext()) {
            current = ite.next();
            Type tNext = current.verifyExpr(compiler, localEnv, currentClass);
            if (tNext.isArray()) {
                if (tNext.asArrayType(null, getLocation()).getLevel() != t.asArrayType(null, getLocation()).getLevel()) {
                    // ERROR MSG
                    throw new ContextualError("Array must be of same dimension : rule extension", getLocation());
                }
                Type currEltType = tNext.asArrayType(null, getLocation()).getEltType();
                if (!currEltType.sameType(eltType) && !(currEltType.isInt() && eltType.isFloat()) && !(currEltType.isFloat() && eltType.isInt())) {
                    // ERROR MSG
                    throw new ContextualError("Array must contains element of same type : rule extension", getLocation());
                }
            } else {
                if (!tNext.sameType(eltType) && !(tNext.isInt() && eltType.isFloat()) && !(tNext.isFloat() && eltType.isInt())) {
                    // ERROR MSG
                    throw new ContextualError("Array must contains element of same type : rule extension", getLocation());
                }
            }
        }
        if (needConv) {
            addConvFloat();
            eltType = compiler.environmentType.FLOAT;
        }
        ArrayType arrType;
        if (current.getType().isArray()) {
            Symbol sym = compiler.createSymbol(current.getType().getName().getName() + "[]");
            arrType = new ArrayType(sym, eltType, getLocation(), current.getType().asArrayType(null, getLocation()).getLevel() + 1);
        } else {
            Symbol sym = compiler.createSymbol(current.getType().getName().getName() + "[]");
            arrType = new ArrayType(sym, eltType, getLocation(), 1);
        }
        setType(arrType);
        return getType();
    }

    @Override
    public AbstractExpr verifyRValue(DecacCompiler compiler,
                EnvironmentExp localEnv, ClassDefinition currentClass, 
                Type expectedType) throws ContextualError{
        Type t = verifyExpr(compiler, localEnv, currentClass);
        if (!t.sameType(expectedType)) {
            if (t.asArrayType(null, getLocation()).getEltType().isInt() && expectedType.asArrayType(null, getLocation()).getEltType().isFloat()) {
                addConvFloat();
                t.asArrayType(null, getLocation()).setEltType(compiler.environmentType.FLOAT);
                Symbol sym = compiler.createSymbol(t.getName().getName() + "[]");
                setType(new ArrayType(sym, compiler.environmentType.FLOAT, getLocation(), t.asArrayType(null, getLocation()).getLevel() + 1));
            } else {
                // ERROR MSG
                throw new ContextualError("Can't assign \"" + t + "\" to \"" + expectedType + "\" : rule extension", getLocation());
            }
        }
        for (AbstractExpr abstractExpr : elementab.getList()) {
            abstractExpr.verifyRValue(compiler, localEnv, currentClass, expectedType.asArrayType(null, getLocation()).subType());
        }
        this.setType(expectedType);
        return this;
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        int len = elementab.size();
        int localOffset;
        if (offset + 1 == compiler.getCompilerOptions().getRmax()) {
            localOffset = offset - 1;
            compiler.addInstruction(new PUSH(GPRegister.getR(localOffset)));
        } else {
            localOffset = offset;
        }
        int[] res = {localOffset, 0};
        compiler.addInstruction(new NEW(len + 1, GPRegister.getR(localOffset)));
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(new Label("tas_plein")));
        }
        compiler.addInstruction(new LOAD(new ImmediateInteger(len),GPRegister.R0));
        compiler.addInstruction(new STORE(GPRegister.R0, new RegisterOffset(0, GPRegister.getR(localOffset))));
        
        AbstractExpr expr;
        Iterator<AbstractExpr> ite = elementab.getList().iterator();
        for (int i = 1; i <= len; i++) {
            expr = ite.next();
            int[] resExpr = expr.codeGenExpr(compiler, localOffset + 1);
            if (resExpr[0] > res[0]) {
                res[0] = resExpr[0];
            }
            if (resExpr[1] > res[1]) {
                res[1] = resExpr[1];
            }
            compiler.addInstruction(new STORE(GPRegister.getR(localOffset + 1), new RegisterOffset(i, GPRegister.getR(offset))));
        }
        if (offset + 1 == compiler.getCompilerOptions().getRmax()) {
            compiler.addInstruction(new LOAD(GPRegister.getR(localOffset), GPRegister.getR(offset)));
            compiler.addInstruction(new POP(GPRegister.getR(localOffset)));
            res[1] += 1;
        }
        return res;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("{");
        Iterator<AbstractExpr> it = elementab.iterator();
        while(it.hasNext()){
            it.next().decompile(s);;
            if(it.hasNext()){
                s.print(",");
            }
        }
        s.print("}");
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        elementab.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        elementab.iter(f);
        
        
    }
    
}
