package fr.ensimag.deca.tree;

import java.io.PrintStream;
import java.util.Iterator;

import org.antlr.v4.runtime.misc.Triple;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ArrayType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffOffset;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.NEW;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUB;


public class TabIdentifier extends AbstractIdentifier{
    private Symbol name;
    private Symbol localType;
    private int level;
    private ListExpr listeposs;
    private Definition definition;

    public TabIdentifier(Symbol name, Symbol localType, ListExpr listeposs, int level) {
        this.name = name;
        this.localType = localType;
        this.listeposs = listeposs;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    @Override
    public Symbol getName() {
       return name;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
        TypeDefinition def = compiler.environmentType.defOfType(localType);
        if (def == null) {
            // ERROR MSG
            throw new ContextualError("", getLocation());
        }
        ArrayType arrType = new ArrayType(name, def.getType(), getLocation(), level);
        setDefinition(arrType.getDefinition());
        setType(definition.getType());
        return getType();
    }

    @Override
    public Type verifyLValue(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        throw new ContextualError("\"" + getType().getName() + "\" can't be a LValue : rule extension", getLocation());
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        for (AbstractExpr abstractExpr : listeposs.getList()) {
            Type t = abstractExpr.verifyExpr(compiler, localEnv, currentClass);
            if (!t.isInt()) {
                throw new ContextualError("Expression inside \"[]\" must be of type \"int\" : rule extension", getLocation());
            }
        }
        return getType();
    }

    private int[] codeGenTab(DecacCompiler compiler, int offset, ListExpr dim, int level) {
        
        if (level - 1 == dim.getList().size()) {
            Type t = compiler.environmentType.defOfType(localType).getType();
            if (this.level != level - 1) {
                compiler.addInstruction(new LOAD(new NullOperand(), GPRegister.getR(offset)));
            } else if (t.isInt() || t.isBoolean()) {
                compiler.addInstruction(new LOAD(new ImmediateInteger(0), GPRegister.getR(offset)));
            } else if (t.isFloat()) {
                compiler.addInstruction(new LOAD(new ImmediateFloat(0), GPRegister.getR(offset)));
            } else {
                compiler.addInstruction(new LOAD(new NullOperand(), GPRegister.getR(offset)));
            }
            int[] res = {offset, 0};
            return res;
        }

        int currOffset = offset;
        if (offset + 2 == compiler.getCompilerOptions().getRmax()) {
            currOffset = offset - 1;
            compiler.addInstruction(new PUSH(GPRegister.getR(currOffset)));
        } else if (offset + 1 == compiler.getCompilerOptions().getRmax()) {
            currOffset = offset - 2;
            compiler.addInstruction(new PUSH(GPRegister.getR(currOffset)));
            compiler.addInstruction(new PUSH(GPRegister.getR(currOffset + 1)));
        }

        dim.getList().get(level - 1).codeGenExpr(compiler, currOffset);
        compiler.addInstruction(new ADD(new ImmediateInteger(1), GPRegister.getR(currOffset)));
        compiler.addInstruction(new NEW(GPRegister.getR(currOffset), GPRegister.getR(currOffset + 1)));
        compiler.addInstruction(new SUB(new ImmediateInteger(1), GPRegister.getR(currOffset)));
        compiler.addInstruction(new STORE(GPRegister.getR(currOffset), new RegisterOffset(0, GPRegister.getR(currOffset + 1))));
        
        int labelNumber = compiler.getLabelNumber();
        compiler.incrLabelNumber();
        Label debLabel = new Label("init.tab.boucle."+ labelNumber);
        Label condLabel = new Label("init.tab.condition."+ labelNumber);

        compiler.addInstruction(new BRA(condLabel));
        compiler.addLabel(debLabel);
        
        int[] res = codeGenTab(compiler, currOffset + 2, dim, level + 1);
        compiler.addInstruction(new STORE(GPRegister.getR(currOffset + 2), new RegisterOffOffset(1, GPRegister.getR(offset + 1), GPRegister.getR(offset))));

        compiler.addLabel(condLabel);
        compiler.addInstruction(new SUB(new ImmediateInteger(1), GPRegister.getR(currOffset)));
        compiler.addInstruction(new CMP(new ImmediateInteger(-1), GPRegister.getR(currOffset)));
        compiler.addInstruction(new BNE(debLabel));

        if (offset + 2 == compiler.getCompilerOptions().getRmax()) {
            compiler.addInstruction(new POP(GPRegister.getR(currOffset)));
            res[1]++;
        } else if (offset + 1 == compiler.getCompilerOptions().getRmax()) {
            compiler.addInstruction(new POP(GPRegister.getR(currOffset + 1)));
            compiler.addInstruction(new POP(GPRegister.getR(currOffset)));
            res[1] += 2;
        }
        compiler.addInstruction(new LOAD(GPRegister.getR(currOffset + 1), GPRegister.getR(offset)));
        return res;
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        return codeGenTab(compiler, offset, listeposs, 1);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(localType.toString());
        Iterator<AbstractExpr> ite = listeposs.iterator();
        AbstractExpr current;
        if (ite.hasNext()) {
            current = ite.next();
            s.print("[");
            current.decompile(s);
            s.print("]");
        }
        while (ite.hasNext()) {
            current = ite.next();
            s.print("[");
            current.decompile(s);
            s.print("]");
        }
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        listeposs.prettyPrint(s, prefix, true);
    }

    @Override
    String prettyPrintNode() {
        return "TAB Identifier (" +this.getName() + ")";
    }
    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }
    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void setName(Symbol a) {
        this.name = a;        
    }

    @Override
    public Triple<int[], Integer, DAddr> codeGenLValue(DecacCompiler compiler, int offset) {
        // Pas une LValue
        throw new UnsupportedOperationException();
    }
    
}
