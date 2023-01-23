package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListDeclField extends TreeList<DeclField> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclField c : getList()) {
            c.decompile(s);
            s.println();
        }
    }
    
    // Passe 2
    public void verifyListFieldMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv,
        ClassDefinition currentClass) throws ContextualError {
            for (DeclField declField : this.getList()) {
                declField.verifyFieldMembers(compiler, superEnv, localEnv, currentClass);
            }
    }

    // Passe 3
    public void verifyListFieldBody(DecacCompiler compiler, EnvironmentExp localEnv,
        ClassDefinition currentClass) throws ContextualError {
            for (DeclField declField : this.getList()) {
                declField.verifyFieldBody(compiler, localEnv, currentClass);
            }
    }
}

