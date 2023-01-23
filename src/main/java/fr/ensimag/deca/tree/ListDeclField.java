package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * ListDeclField
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class ListDeclField extends TreeList<DeclField> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclField c : getList()) {
            c.decompile(s);
            s.println();
        }
    }
    
    /**
     * Passe 2 of contextual syntax for field members
     * @param compiler
     * @param superEnv
     * @param localEnv
     * @param currentClass
     * @throws ContextualError
     */
    public void verifyListFieldMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv,
        ClassDefinition currentClass) throws ContextualError {
            for (DeclField declField : this.getList()) {
                declField.verifyFieldMembers(compiler, superEnv, localEnv, currentClass);
            }
    }

    /**
     * Passe 3 of contextual syntax for field body
     * @param compiler
     * @param localEnv
     * @param currentClass
     * @throws ContextualError
     */
    public void verifyListFieldBody(DecacCompiler compiler, EnvironmentExp localEnv,
        ClassDefinition currentClass) throws ContextualError {
            for (DeclField declField : this.getList()) {
                declField.verifyFieldBody(compiler, localEnv, currentClass);
            }
    }
}

