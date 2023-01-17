package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class DeclClass extends AbstractDeclClass {
    AbstractIdentifier name;
    AbstractIdentifier extension;
    private ClassBody bodyclass;
     
    public DeclClass(AbstractIdentifier name, AbstractIdentifier extension, ClassBody body) {
        this.name = name;
        this.extension = extension;
        this.bodyclass = body;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class { ... A FAIRE ... }");
    }

    // passe 1
    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        ClassType t;
        boolean b = true;
        TypeDefinition tDef = compiler.environmentType.defOfType(extension.getName());
        if (!tDef.isClass()) {
            // ERROR MSG : match msg d'erreur avec doc
            throw new ContextualError("No super class named : \""+extension.getName()+"\" : rule 1.3", getLocation());
        }
        ClassDefinition supClass = (ClassDefinition) tDef;
        t = new ClassType(name.getName(), getLocation(), supClass);
        name.setDefinition(new ClassDefinition(t, getLocation(), supClass));
        b = compiler.environmentType.put(name.getName(), name.getClassDefinition());
        if (!b) {
            // ERROR MSG : match msg d'erreur avec doc
            throw new ContextualError("The class \""+name+"\" is already declared : rule 1.3", getLocation());
        }
    }

    // passe 2
    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        ClassDefinition supClassDef = (ClassDefinition) compiler.environmentType.defOfType(extension.getName());
        EnvironmentExp envExpSuper = supClassDef.getMembers();
        EnvironmentExp envExp = ((ClassDefinition) compiler.environmentType.defOfType(name.getName())).getMembers();
        bodyclass.getListDeclField().verifyListFieldMembers(compiler, envExpSuper, envExp, name.getClassDefinition());
        bodyclass.getListDeclMethod().verifyListMethodMembers(compiler, envExpSuper, envExp, name.getClassDefinition());


    }
    
    // passe 3
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        extension.verifyType(compiler);
        EnvironmentExp localEnv = ((ClassDefinition) compiler.environmentType.defOfType(name.getName())).getMembers();
        bodyclass.getListDeclField().verifyListFieldBody(compiler, localEnv, name.getClassDefinition());
        bodyclass.getListDeclMethod().verifyListMethodBody(compiler, localEnv, name.getClassDefinition());
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        
        name.prettyPrint(s,prefix,false);
        if (extension != null) {
            extension.prettyPrint(s, prefix, false);
        }
        bodyclass.prettyPrint(s,prefix,true);
       // throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

}
