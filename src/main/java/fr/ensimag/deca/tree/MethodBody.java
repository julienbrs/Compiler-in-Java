package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

public class MethodBody extends AbstractMethodBody{
    private ListDeclVar declVar;
    private ListInst listInst;

    public MethodBody(ListDeclVar declVar, ListInst listInst) {
        this.declVar = declVar;
        this.listInst = listInst;
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, EnvironmentExp paramEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        declVar.verifyListDeclVariable(compiler, paramEnv, currentClass);
        listInst.verifyListInst(compiler, paramEnv, currentClass, returnType);
    }

    public void codeGenBody(DecacCompiler compiler, ClassDefinition currentClass, AbstractIdentifier ident) {
        

        Line lTSTO = new Line("");
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.add(lTSTO);
            compiler.addInstruction(new BOV(new Label("pile_pleine")));
        }

        Line lADDSP = new Line("");
        compiler.add(lADDSP);

        Label returnLabel = new Label("end." + currentClass.getType().getName() + "." + ident.getName());
        Label oldReturnLabel = compiler.getReturnLabel();
        compiler.setReturnLabel(returnLabel);

        compiler.addComment("Variables declarations:");
        int nbDecl = declVar.codeGenListDeclVar(compiler, 0, GPRegister.LB);

        compiler.addComment("Beginning of main instructions:");
        int maxPush = listInst.codeGenListInst(compiler);

        compiler.setReturnLabel(oldReturnLabel);

        lADDSP.setInstruction(new ADDSP(new ImmediateInteger(nbDecl)));

        lTSTO.setInstruction(new TSTO(new ImmediateInteger(nbDecl + maxPush)));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVar.decompile(s);
        listInst.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        
        declVar.prettyPrint(s,prefix,false);
        listInst.prettyPrint(s,prefix,false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVar.iter(f);
        listInst.iter(f);
    }
    

}