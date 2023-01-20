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
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.InlinePortion;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

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

        // for (int i = 2; i < compiler.getCompilerOptions().getRmax(); i++) {
        //     compiler.addInstruction(new PUSH(GPRegister.getR(i)));
        // }
        InlinePortion pushLine = new InlinePortion("");
        compiler.add(pushLine);

        Label returnLabel = new Label("end." + currentClass.getType().getName() + "." + ident.getName());
        Label oldReturnLabel = compiler.getReturnLabel();
        compiler.setReturnLabel(returnLabel);

        compiler.addComment("Variables declarations");
        int[] resDecl = declVar.codeGenListDeclVar(compiler, 1, GPRegister.LB); // {nbVar, maxReg, maxPush}

        compiler.addComment("Beginning of instructions");
        int[] resInst = listInst.codeGenListInst(compiler); // {maxReg, maxPush}

        if (!compiler.getCompilerOptions().getNoCheck() && !ident.getType().isVoid()) {
            compiler.addInstruction(new WSTR(new ImmediateString("pas de return dans une methode sans void")));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
            compiler.addLabel(returnLabel);
        }

        int[] max = {Math.max(resDecl[1], resInst[0]), Math.max(resDecl[2], resInst[1])};

        String asmPush = "";
        for (int i = 2; i <= max[0]; i++) {
            asmPush += "\tPUSH R" + i + "\n";
        }
        pushLine.setAsm(asmPush.substring(0, asmPush.length()-1));
        for (int i = max[0]; i >= 2; i--) {
            compiler.addInstruction(new POP(GPRegister.getR(i)));
        }

        compiler.addInstruction(new RTS());

        compiler.setReturnLabel(oldReturnLabel);
        
        lADDSP.setInstruction(new ADDSP(new ImmediateInteger(resDecl[0] + max[0])));

        lTSTO.setInstruction(new TSTO(new ImmediateInteger(resDecl[0] + max[1] + max[0])));
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