package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * ListDeclMethod
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class ListDeclMethod extends TreeList<DeclMethod> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclMethod c : getList()) {
            c.decompile(s);
            s.println();
        }
    }
    
    /**
     * Passe 2 of contextual syntax for method members
     */
    public void verifyListMethodMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv, ClassDefinition curentClass) throws ContextualError {
            for (DeclMethod DeclMethod : this.getList()) {
                DeclMethod.verifyMethodMembers(compiler, superEnv, localEnv, curentClass);
            }
    }

    /**
     * Passe 3 of contextual syntax for method body
     */
    public void verifyListMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        for (DeclMethod DeclMethod : this.getList()) {
            DeclMethod.verifyMethodBody(compiler, localEnv, currentClass);
        }
    }
}
