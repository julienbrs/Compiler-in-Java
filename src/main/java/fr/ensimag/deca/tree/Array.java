package fr.ensimag.deca.tree;

import java.io.PrintStream;
import java.util.Iterator;

import org.antlr.v4.runtime.misc.Triple;
import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ArrayDefinition;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Type;
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
    private ListExpr profondeur;
    private int Level;

    public Array(Symbol name,AbstractExpr expr) {
        Validate.notNull(name);
        this.name = name;
        this.profondeur = new ListExpr();
        this.profondeur.add(expr);
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
        return "Array ("+ this.getName()+Profondeur()   +" )";
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }

   String Profondeur(){
    Iterator<AbstractExpr> it  = profondeur.iterator();
    String a = new String();
    while(it.hasNext()){
        a = a +" "+it.next().decompile();
    }
    return a ;
   }


 
    @Override
    public ClassDefinition getClassDefinition() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Definition getDefinition() {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        
    }
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Type verifyLValue(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // TODO Auto-generated method stub
        return null;
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
    public Triple<Integer, Integer, DAddr> codeGenLValue(DecacCompiler compiler, int offset) {
        // TODO Auto-generated method stub
        return null;
    }
 
    
}
