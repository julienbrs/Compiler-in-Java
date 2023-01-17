package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;

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
            varName.setDefinition(new FieldDefinition(t, getLocation(), visibility, currentClass, currentClass.getNumberOfFields()));
            localEnv.declare(varName.getName(), varName.getExpDefinition()); 
        } catch (DoubleDefException e) {
            // ERROR MSG
            throw new ContextualError("The field \""+varName.getName()+"\" is already declared : rule 2.4", getLocation());
        }
        currentClass.incNumberOfFields();
    }

    //Passe 3
    @Override
    protected void verifyFieldBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type t = type.verifyType(compiler);
        // varName.verifyType(compiler);
        initialization.verifyInitialization(compiler, t, localEnv, currentClass);   
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
