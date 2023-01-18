package fr.ensimag.deca.tree;

import java.io.PrintStream;

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

public class Array extends AbstractDeclVar {

    public Array() {
    }

    private AbstractExpr nbElements;
    private AbstractIdentifier type;
    private AbstractIdentifier name;
    private Array ArrayFather;
    private Array ArraySon;
    private int Level;

    public Array(AbstractExpr nbElements, AbstractIdentifier type, AbstractIdentifier name,int level) {
        this.nbElements = nbElements;
        this.type = type;
        this.name = name;
        this.Level=level;
    }
    public void setArrayFather(Array type){
        this.ArrayFather = type;
    }
    public void setArraySon(Array type){
        this.ArraySon = type;
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
    String prettyPrintNode() {
        return "Array ( level :" +Level + ")";
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
            Array a = new Array();
        Type t = type.verifyType(compiler);
        if (t.sameType(compiler.environmentType.VOID)) {
            // ERROR MSG
            throw new ContextualError("Type can't be void : rule 3.17", getLocation());
        }
        try {
            a=this;
            while(a.ArraySon !=null){
                a=a.ArraySon;
            }
            System.out.println(a.Level);
            name.setDefinition(new ArrayDefinition(t, getLocation(),a.Level));
            // nbElements.
            localEnv.declare(name.getName(), name.getExpDefinition());
        } catch (DoubleDefException e) {
            // ERROR MSG
            throw new ContextualError("The variable \""+name.getName()+"\" is already declared : rule 3.17", getLocation());
        }
        name.verifyExpr(compiler, localEnv, currentClass);
        nbElements.verifyExpr(compiler, localEnv, currentClass);
    }

    @Override
    protected void codeGenDeclVar(DecacCompiler compiler, int offsetFromSP) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if(ArrayFather==null){
            
            type.decompile(s);
            s.print(" ");
            name.decompile(s);
            s.print("[");
            nbElements.decompile(s);
            s.print("]");
    
        }
        else {
            s.print("[");
            nbElements.decompile(s);
            s.print("]");
        }
        if(ArraySon!=null){
            ArraySon.decompile(s);
        }
        else {
            s.println(";"); 
        }
        
    }
    
}
