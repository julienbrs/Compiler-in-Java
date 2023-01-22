package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.antlr.v4.runtime.misc.Triple;
import org.apache.commons.lang.Validate;

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

/**
 * Array
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class Array extends AbstractIdentifier {

    private Symbol name;
    private AbstractIdentifier nametype;
    private int level;

    /**
     * Sets the arguments for an array
     * @param name
     * @param level
     * @param nametype
     */
    public Array(Symbol name, int level,AbstractIdentifier nametype ) {
        Validate.notNull(name);
        this.name = name;
        this.level= level;
        this.nametype = nametype;
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
    String prettyPrintNode() {
        return "Array ("+ this.getName()+" "+ level   +" )";
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    //    this.nametype.iter(f);
    }

    /**
     * Sets the level of an array
     * @param level
     */
    public void setLevel(int level){
        this.level = level;
    }

    /**
     * Gets the level of an array
     * @return the leve of the array
     */
    public int getLevel(){
        return this.level;
    }

    private Definition definition;

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
    public void setName(Symbol a ) {
        this.name = a;
    }
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
        this.definition=definition;
        
    }
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
          nametype.verifyType(compiler);
          TypeDefinition def = compiler.environmentType.defOfType(nametype.getName());
          if (def == null) {
              // ERROR MSG
              throw new ContextualError("The type \"" + nametype + "\" doesn't exist : 0.1", getLocation());
          }
          ArrayType arrType = new ArrayType(name, nametype.getType(), getLocation(), level);
          setDefinition(arrType.getDefinition());
          setType(definition.getType());
          return this.getType();
    }

    @Override
    public Type verifyLValue(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        return verifyExpr(compiler, localEnv, currentClass);
    }
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        ExpDefinition def = localEnv.get(nametype.getName());
        if (def == null) {
            // ERROR MSG
            throw new ContextualError("La variable \""+name+"\" n'a pas été déclaré : rule 0.1", getLocation());
        }
        setDefinition(def);
        setType(definition.getType());
        return this.getType();
    }
    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        // int[] res = {0, 0};
        // return res;
        throw new UnsupportedOperationException("Should not end up here");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public Triple<int[], Integer, DAddr> codeGenLValue(DecacCompiler compiler, int offset) {
        // return null;
        throw new UnsupportedOperationException("Should not end up here");
    }
 
    
}
