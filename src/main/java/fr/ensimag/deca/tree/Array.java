package fr.ensimag.deca.tree;

import java.beans.Expression;
import java.io.PrintStream;
import java.util.Iterator;

import org.antlr.v4.runtime.misc.Triple;
import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ArrayDefinition;
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
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;

public class Array extends AbstractIdentifier {



    private Symbol name;
    private     AbstractIdentifier nametype;
    private ListExpr profondeur;
    private int Level;

    public Array(Symbol name,int Level,AbstractIdentifier nametype ) {
        Validate.notNull(name);
        this.name = name;
        this.Level= Level;
        this.nametype = nametype;
    }
    public void addProfondeur(AbstractExpr expr){
        this.profondeur.add(expr);
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
        return "Array ("+ this.getName()+" "+ Level   +" )";
    }

    @Override
    protected void iterChildren(TreeFunction f) {
       this.nametype.iter(f);
       this.profondeur.iter(f);
        
    }
    public void setLevel(int level){
        this.Level = level;
    }
    public int getLevel(){
        return this.Level;
    }

   String Profondeur(){
    Iterator<AbstractExpr> it  = profondeur.iterator();
    String a = new String();
    while(it.hasNext()){
        a = a +" "+it.next().decompile();
    }
    return a ;
   }
   private Definition definition;

 
    @Override
    public ClassDefinition getClassDefinition() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Definition getDefinition() {

        return definition;
    }
    @Override
    public FieldDefinition getFieldDefinition() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public MethodDefinition getMethodDefinition() {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public VariableDefinition getVariableDefinition() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void setDefinition(Definition definition) {
        this.definition=definition;
        
    }
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
          // throw new UnsupportedOperationException("not yet implemented");
          TypeDefinition def = compiler.environmentType.defOfType(nametype.getName());
          if (def == null) {
              // ERROR MSG
              throw new ContextualError("", getLocation());
          }
          setDefinition(def);
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
        // TODO Auto-generated method stub
        int[] res = {0, 0};
        return res;
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
        // TODO Auto-generated method stub
        return null;
    }
 
    
}
