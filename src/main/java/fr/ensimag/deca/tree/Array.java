package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public class Array extends AbstractDeclVar {

    public Array() {
    }

    private AbstractExpr nbElements;
    private AbstractIdentifier type;
    private AbstractIdentifier name;
    private Array ArrayFather;
    private Array ArraySon;

    private Definition definition;

    public Array(AbstractExpr nbElements, AbstractIdentifier type, AbstractIdentifier name) {
        this.nbElements = nbElements;
        this.type = type;
        this.name = name;
    }
    public void setArrayFather(Array type){
        this.ArrayFather=type;
    }
    public void setArraySon(Array type){
        this.ArraySon=type;
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
    protected void prettyPrintChildren(PrintStream s, String prefix) {
       
        type.prettyPrint(s, prefix, false);
        nbElements.prettyPrint(s,prefix,false);
        name.prettyPrint(s, prefix, true);
        if(ArraySon != null){
        ArraySon.prettyPrint(s, prefix, true);
        }
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void codeGenDeclVar(DecacCompiler compiler, int offsetFromSP) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }
    
}
