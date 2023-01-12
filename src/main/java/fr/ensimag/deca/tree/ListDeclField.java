package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import net.bytebuddy.implementation.bind.annotation.Super;

public class ListDeclField extends TreeList<DeclField> {

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }
    
    // Passe 2
    public void verifyClassMembers(DecacCompiler compiler, EnvironmentExp localEnv,
        ClassDefinition currentClass) throws ContextualError {
            for (DeclField DeclField : this.getList()) {
                DeclField.verifyClassMembers(compiler, localEnv, currentClass);
            }
    }
}
