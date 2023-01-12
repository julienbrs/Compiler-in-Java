package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListDeclMethod extends TreeList<DeclMethod> {

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListMethodMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv) throws ContextualError {
            for (DeclMethod DeclMethod : this.getList()) {
                DeclMethod.verifyMethodMembers(compiler, superEnv, localEnv);
            }
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        for (DeclMethod DeclMethod : this.getList()) {
            DeclMethod.verifyMethodBody(compiler, localEnv, currentClass);
        }
    }
}
