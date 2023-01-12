package fr.ensimag.deca.tree;

import java.io.PrintStream;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclMethod extends AbstractDeclMethod {
    private AbstractIdentifier type;
    private AbstractIdentifier ident;
    private ListParam listeparametre;
    private AbstractMethodBody methodBody;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier ident, ListParam listeparametre,
            AbstractMethodBody methodBody) {
        this.type = type;
        this.ident = ident;
        this.listeparametre = listeparametre;
        this.methodBody = methodBody;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, false);
        
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }
    
    public void verifyMethodMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
            Type t = type.verifyType(compiler);
            ExpDefinition sDef = superEnv.get(ident.getName());
            if (sDef != null && !sDef.isMethod()) {
                // ERROR MSG
                throw new ContextualError("??? : rule 2.7", getLocation());
            }
            Signature sig = new Signature();
            listeparametre.verifyListParam(compiler, sig);
            MethodDefinition mDef = (MethodDefinition) sDef;
            if (!mDef.getSignature().equals(sig)) {
                // ERROR MSG
                throw new ContextualError(" : rule 2.7", getLocation());
            }

            if (!t.sameType(mDef.getType())) {
                // ERROR MSG
                throw new ContextualError(" : rule 2.7", getLocation());
            }
            // ERROR MSG
            ClassType cType = t.asClassType(" : rule 2.7", getLocation());
            // ERROR MSG
            ClassType superType = mDef.getType().asClassType(" : rule 2.7", getLocation());
            if (!cType.isSubClassOf(superType)) {
                // ERROR MSG
                throw new ContextualError(" : rule 2.7", getLocation());
            }
            try {
                localEnv.declare(ident.getName(), new MethodDefinition(t, getLocation(), sig, currentClass.getNumberOfMethods()));
            } catch (DoubleDefException e) {
                // ERROR MSG
                throw new ContextualError(" rule ?.??", getLocation());
            }
            currentClass.incNumberOfMethods();

    }

    public void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        // TODO
    }
}
