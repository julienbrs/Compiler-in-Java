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
import fr.ensimag.deca.tools.SymbolTable.Symbol;

/**
 * Declaration of a method
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class DeclMethod extends AbstractDeclMethod {
    private AbstractIdentifier type;
    private AbstractIdentifier ident;
    private ListParam listeparametre;
    private AbstractMethodBody methodBody;

    /**
     * Sets the characteristics for a method
     * 
     * @param type
     * @param ident
     * @param listeparametre
     * @param methodBody
     */
    public DeclMethod(AbstractIdentifier type, AbstractIdentifier ident, ListParam listeparametre,
            AbstractMethodBody methodBody) {
        this.type = type;
        this.ident = ident;
        this.listeparametre = listeparametre;
        this.methodBody = methodBody;
    }

    @Override
    public AbstractIdentifier getIdent() {
        return ident;
    }

    @Override
    public Symbol getName() {
        return ident.getName();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        ident.decompile(s);
        s.print("(");
        listeparametre.decompile(s);
        s.print(") ");
        methodBody.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, false);
        listeparametre.prettyPrint(s, prefix, false);
        methodBody.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        ident.iter(f);
        listeparametre.iter(f);
        methodBody.iter(f);
    }

    public void verifyMethodMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t = type.verifyType(compiler);
        ExpDefinition sDef = superEnv.get(ident.getName());
        if (sDef == null) {
            Signature sig = new Signature();
            listeparametre.verifyListParamMembers(compiler, sig);
            try {
                currentClass.incNumberOfMethods();
                ident.setDefinition(
                        new MethodDefinition(t, ident.getLocation(), sig, currentClass.getNumberOfMethods()));
                ident.setType(ident.getDefinition().getType());
                currentClass.put(currentClass.getNumberOfMethods(), ident.getMethodDefinition());
                localEnv.declare(ident.getName(), ident.getExpDefinition());
            } catch (DoubleDefException e) {
                // ERROR MSG
                throw new ContextualError("The method \"" + ident.getName() + "\" is already declared : rule 2.6",
                        getLocation());
            }
            return;
        }
        if (!sDef.isMethod()) {
            // ERROR MSG
            throw new ContextualError(ident.getName() + " already declare as field in super class : rule 2.7",
                    getLocation());
        }
        Signature sig = new Signature();
        listeparametre.verifyListParamMembers(compiler, sig);
        MethodDefinition mDef = (MethodDefinition) sDef;
        if (mDef != null) {
            if (!mDef.getSignature().equals(sig)) {
                // ERROR MSG
                throw new ContextualError(
                        "The method \"" + ident.getName()
                                + "\" is already define in super class with a different signature : rule 2.7",
                        getLocation());
            }
            if (!t.sameType(mDef.getType())) {
                // ERROR MSG
                ClassType cType = t.asClassType(
                        "The method \"" + ident.getName()
                                + "\" is already define in super class with a different signature : rule 2.7",
                        getLocation());
                // ERROR MSG
                ClassType superType = mDef.getType().asClassType(
                        "The method \"" + ident.getName()
                                + "\" is already define in super class with a different signature : rule 2.7",
                        getLocation());
                if (!cType.isSubClassOf(superType)) {
                    // ERROR MSG
                    throw new ContextualError(
                            "The method \"" + ident.getName()
                                    + "\" is already define in super class with a different signature : rule 2.7",
                            getLocation());
                }
            }

        }
        try {
            ident.setDefinition(new MethodDefinition(t, ident.getLocation(), sig, mDef.getIndex()));
            ident.setType(ident.getDefinition().getType());
            currentClass.put(mDef.getIndex(), mDef);
            localEnv.declare(ident.getName(), ident.getExpDefinition());
        } catch (DoubleDefException e) {
            // ERROR MSG
            throw new ContextualError("The method \"" + ident.getName() + "\" is already declared : rule 2.6",
                    getLocation());
        }

    }

    public void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type t = type.verifyType(compiler);
        EnvironmentExp paramEnv = new EnvironmentExp(localEnv);
        listeparametre.verifyListParamBody(compiler, paramEnv);
        methodBody.verifyMethodBody(compiler, localEnv, paramEnv, currentClass, t);
    }

    @Override
    public void codeGenBody(DecacCompiler compiler, ClassDefinition currentClass) {
        compiler.addLabel(ident.getMethodDefinition().getLabel());
        listeparametre.codeGenParam(compiler);
        methodBody.codeGenBody(compiler, currentClass, ident);
    }
}
