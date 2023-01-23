package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Declaration of fields
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class DeclField extends AbstractDeclField{
    private Visibility visibility;
    private AbstractIdentifier type;
    private AbstractIdentifier varName;
    AbstractInitialization initialization;

    /**
     * Sets characteristics of a declared field
     * @param type
     * @param varName
     * @param initialization
     * @param visibility
     */
    public DeclField(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization, Visibility visibility) {
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
        this.visibility = visibility;
    }

    /**
     * Gets the name of a variable
     * @return the name
     */
    public Symbol getName() {
        return varName.getName();
    }


    // Passe 2
    @Override
    protected void verifyFieldMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type t = type.verifyType(compiler);
        if (t.isVoid()) {
            // ERROR MSG
            throw new ContextualError("Field can't be of type \"void\" : rule 2.5", getLocation());
        }
        ExpDefinition sDef = superEnv.get(varName.getName());
        if (sDef != null && !sDef.isField()) {
            // ERROR MSG
            throw new ContextualError(getName() + " already declare as method in super class : rule 2.5", getLocation());
        }
        try {
            currentClass.incNumberOfFields();
            varName.setDefinition(new FieldDefinition(t, getLocation(), visibility, currentClass, currentClass.getNumberOfFields()));
            varName.setType(varName.getDefinition().getType());
            localEnv.declare(varName.getName(), varName.getExpDefinition()); 
        } catch (DoubleDefException e) {
            // ERROR MSG
            throw new ContextualError("The field \""+varName.getName()+"\" is already declared : rule 2.4", getLocation());
        }
    }

    //Passe 3
    @Override
    protected void verifyFieldBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type t = type.verifyType(compiler);
        initialization.verifyInitialization(compiler, t, localEnv, currentClass);   
    }

    @Override
    public void codeGenDeclFieldNull(DecacCompiler compiler) {
        if (varName.getType().isInt() || varName.getType().isBoolean()) {
            compiler.addInstruction(new LOAD(new ImmediateInteger(0), GPRegister.getR(3)));
        } else if (varName.getType().isFloat()) {
            compiler.addInstruction(new LOAD(new ImmediateFloat(0), GPRegister.getR(3)));
        } else {
            compiler.addInstruction(new LOAD(new NullOperand(), GPRegister.getR(3)));
        }
        compiler.addInstruction(new STORE(GPRegister.getR(3), new RegisterOffset(varName.getFieldDefinition().getIndex(), GPRegister.getR(2))));
    }

    @Override
    public int[] codeGenDeclField(DecacCompiler compiler) {
        int[] res = initialization.codeGenInitialization(compiler, 3); // res = {max registre, max push}
        compiler.addInstruction(new STORE(GPRegister.getR(3), new RegisterOffset(varName.getFieldDefinition().getIndex(), GPRegister.getR(2))));
        return res;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (visibility.equals(Visibility.PROTECTED)) {
            s.print("protected ");
        }
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
        s.print(";");
    }

    @Override
    protected String prettyPrintNode() {
        return "[visibility = "+this.visibility.toString()+"]  "+this.getClass().getSimpleName();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, false);

        
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
}
