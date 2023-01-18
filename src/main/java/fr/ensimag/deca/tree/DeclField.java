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
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

public class DeclField extends AbstractDeclField{
    private Visibility visibility;
    private AbstractIdentifier type;
    private AbstractIdentifier varName;
    AbstractInitialization initialization;

    public DeclField(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization, Visibility visibility) {
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
        this.visibility = visibility;
    }

    // Passe 2
    @Override
    protected void verifyFieldMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type t = type.verifyType(compiler);
        if (t.sameType(compiler.environmentType.VOID)) {
            // ERROR MSG
            throw new ContextualError(" : rule 2.5", getLocation());
        }
        ExpDefinition sDef = superEnv.get(varName.getName());
        if (sDef != null && !sDef.isField()) {
            throw new ContextualError("??? : rule 2.5", getLocation());
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

    public void codeGenDeclFieldNull(DecacCompiler compiler) {
        if (varName.getType().isInt() || varName.getType().isBoolean()) {
            compiler.addInstruction(new LOAD(new ImmediateInteger(0), GPRegister.getR(2)));
        } else if (varName.getType().isFloat()) {
            compiler.addInstruction(new LOAD(new ImmediateFloat(0), GPRegister.getR(2)));
        } else {
            compiler.addInstruction(new LOAD(new NullOperand(), GPRegister.getR(2)));
        }
        compiler.addInstruction(new STORE(GPRegister.getR(2), new RegisterOffset(varName.getFieldDefinition().getIndex(), GPRegister.getR(2))));
    }

    public void codeGenDeclField(DecacCompiler compiler) {
        initialization.codeGenInitialization(compiler, 3);
        compiler.addInstruction(new STORE(GPRegister.getR(3), new RegisterOffset(varName.getFieldDefinition().getIndex(), GPRegister.getR(2))));
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
