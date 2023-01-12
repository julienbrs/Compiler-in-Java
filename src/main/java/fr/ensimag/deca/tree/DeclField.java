package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
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
    protected void verifyClassMembers(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type t = type.verifyType(compiler);
        if (t.sameType(compiler.environmentType.VOID)) {
            // ERROR MSG
            throw new ContextualError(" : rule 2.5", getLocation());
        }
        try {
            localEnv.declare(varName.getName(), new FieldDefinition(t, getLocation(), visibility, currentClass, 0));   
        } catch (DoubleDefException e) {
            // TODO : a vérifier
            // ERROR MSG
            throw new ContextualError("The field \""+varName+"\" is already declared : rule 2.4", getLocation());
        }
    }

    //Passe 3
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, false);

        // TODO Auto-generated method stub
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }

}
