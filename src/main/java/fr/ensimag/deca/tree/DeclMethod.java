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
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

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

    public AbstractIdentifier getIdent() {
        return ident;
    }

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
        methodBody.prettyPrint(s,prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        ident.iter(f);
        listeparametre.iter(f);
        methodBody.iter(f);        
    }
    
    public void verifyMethodMembers(DecacCompiler compiler, EnvironmentExp superEnv, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
            Type t = type.verifyType(compiler);
            ExpDefinition sDef = superEnv.get(ident.getName());
            if (sDef == null) {
                Signature sig = new Signature();
                listeparametre.verifyListParamMembers(compiler, sig);
                try {
                    currentClass.incNumberOfMethods();
                    ident.setDefinition(new MethodDefinition(t, getLocation(), sig, currentClass.getNumberOfMethods()));
                    currentClass.put(currentClass.getNumberOfMethods(), ident.getMethodDefinition());
                    localEnv.declare(ident.getName(), ident.getExpDefinition());
                } catch (DoubleDefException e) {
                    // ERROR MSG
                    throw new ContextualError(" rule ?.??", getLocation());
                }
                return;
            }
            if (!sDef.isMethod()) {
                // ERROR MSG
                throw new ContextualError("??? : rule 2.7", getLocation());
            }
            Signature sig = new Signature();
            listeparametre.verifyListParamMembers(compiler, sig);
            MethodDefinition mDef = (MethodDefinition) sDef;
            if (mDef != null) {
                if (!mDef.getSignature().equals(sig)) {
                    // ERROR MSG
                    throw new ContextualError(" : rule 2.7", getLocation());
                }
                if (!t.sameType(mDef.getType())) {
                    // ERROR MSG
                    ClassType cType = t.asClassType(" : rule 2.7", getLocation());
                    // ERROR MSG
                    ClassType superType = mDef.getType().asClassType(" : rule 2.7", getLocation());
                    if (!cType.isSubClassOf(superType)) {
                        // ERROR MSG
                        throw new ContextualError(" : rule 2.7", getLocation());
                    }
                }
                
            }
            try {
                ident.setDefinition(new MethodDefinition(t, getLocation(), sig, mDef.getIndex()));
                currentClass.put(mDef.getIndex(), mDef);
                localEnv.declare(ident.getName(), ident.getExpDefinition());
            } catch (DoubleDefException e) {
                // ERROR MSG
                throw new ContextualError(" rule ?.??", getLocation());
            }

    }

    public void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type t = type.verifyType(compiler);
        EnvironmentExp paramEnv = new EnvironmentExp(null);
        listeparametre.verifyListParamBody(compiler, paramEnv);
        methodBody.verifyMethodBody(compiler, localEnv, paramEnv, currentClass, t);
    }

    public void codeGenBody(DecacCompiler compiler, ClassDefinition currentClass) {
        compiler.addLabel(ident.getMethodDefinition().getLabel());

        Line lTSTO = new Line("");
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.add(lTSTO);
            compiler.addInstruction(new BOV(new Label("pile_pleine")));
        }
        Label returnLabel = new Label("end." + currentClass.getType().getName() + "." + ident.getName());
        // Label oldReturnLabel = compiler.getReturnLabel();
        // compiler.setReturnLabel(returnLabel);

        // TODO : empilage registre

        // int nbPush = methodBody.codeGenBody(compiler);

        compiler.addLabel(returnLabel);
        // TODO : depilage registre

        // compiler.setReturnLabel(oldReturnLabel);
        compiler.addInstruction(new RTS());

        // lTSTO.setInstruction(new TSTO(new ImmediateInteger(nbPush)));
    }
}
