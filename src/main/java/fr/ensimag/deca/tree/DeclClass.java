package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Line;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class DeclClass extends AbstractDeclClass {
    AbstractIdentifier name;
    AbstractIdentifier extension;
    private ClassBody bodyclass;

    public DeclClass(AbstractIdentifier name, AbstractIdentifier extension, ClassBody body) {
        this.name = name;
        this.extension = extension;
        this.bodyclass = body;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        name.decompile(s);
        s.print(" extends ");
        extension.decompile(s);
        s.println(" {");
        s.indent();
        bodyclass.decompile(s);
        s.unindent();
        s.println("}");
    }

    // passe 1
    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        ClassType t;
        boolean b = true;
        extension.verifyType(compiler);
        TypeDefinition tDef = compiler.environmentType.defOfType(extension.getName());
        if (!tDef.isClass()) {
            // ERROR MSG : match msg d'erreur avec doc
            throw new ContextualError("No super class named : \"" + extension.getName() + "\" : rule 1.3",
                    getLocation());
        }
        ClassDefinition supClass = (ClassDefinition) tDef;
        t = new ClassType(name.getName(), getLocation(), supClass);
        name.setDefinition(t.getDefinition());
        b = compiler.environmentType.put(name.getName(), name.getClassDefinition());
        if (!b) {
            // ERROR MSG : match msg d'erreur avec doc
            throw new ContextualError("The class \"" + name.getName() + "\" is already declared : rule 1.3",
                    getLocation());
        }
    }

    // passe 2
    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        // ClassDefinition supClassDef = (ClassDefinition)
        // compiler.environmentType.defOfType(extension.getName());
        ClassDefinition supClassDef = extension.getClassDefinition();
        EnvironmentExp envExpSuper = supClassDef.getMembers();
        // EnvironmentExp envExp = ((ClassDefinition)
        // compiler.environmentType.defOfType(name.getName())).getMembers();
        EnvironmentExp envExp = name.getClassDefinition().getMembers();
        name.getClassDefinition().setNumberOfFields(supClassDef.getNumberOfFields());
        name.getClassDefinition().setNumberOfMethods(supClassDef.getNumberOfMethods());
        bodyclass.getListDeclField().verifyListFieldMembers(compiler, envExpSuper, envExp, name.getClassDefinition());
        bodyclass.getListDeclMethod().verifyListMethodMembers(compiler, envExpSuper, envExp, name.getClassDefinition());
    }

    // passe 3
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        EnvironmentExp localEnv = ((ClassDefinition) compiler.environmentType.defOfType(name.getName())).getMembers();
        bodyclass.getListDeclField().verifyListFieldBody(compiler, localEnv, name.getClassDefinition());
        bodyclass.getListDeclMethod().verifyListMethodBody(compiler, localEnv, name.getClassDefinition());
    }

    public int codeGenVTable(DecacCompiler compiler, int offset) {
        DAddr addr = new RegisterOffset(offset, GPRegister.GB);
        name.getClassDefinition().setOperand(addr);
        DAddr extAddr;
        if (!extension.getType().equals(compiler.environmentType.OBJECT)) {
            extAddr = extension.getClassDefinition().getOperand();
        } else {
            extAddr = new RegisterOffset(1, GPRegister.GB);
        }
        compiler.addComment("Table methodes " + name.getName());
        compiler.addInstruction(new LEA(extAddr, GPRegister.getR(2)));
        compiler.addInstruction(new STORE(GPRegister.getR(2), addr));
        for (int i = 1; i <= extension.getClassDefinition().getNumberOfMethods(); i++) {
            if (name.getClassDefinition().getMethodDefinition(i) == null) {
                MethodDefinition mDef = extension.getClassDefinition().getMethodDefinition(i);
                name.getClassDefinition().put(i, mDef);
                DAddr destMethodAddr = new RegisterOffset(offset + i, ((RegisterOffset) addr).getRegister());
                compiler.addInstruction(new LOAD(new LabelOperand(mDef.getLabel()), GPRegister.getR(2)));
                compiler.addInstruction(new STORE(GPRegister.getR(2), destMethodAddr));
            }
        }
        bodyclass.codeGenVTable(compiler, name, offset);
        return name.getClassDefinition().getNumberOfMethods() + 1;
    }

    public void codeGenBody(DecacCompiler compiler) {
        compiler.addComment("Corps des methodes de la classe " + name.getName());
        compiler.addLabel(new Label("init." + name.getName()));

        Line lTSTO = new Line("");
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.add(lTSTO);
            compiler.addInstruction(new BOV(new Label("pile_pleine")));
        }

        Line lADDSP = new Line("");
        compiler.add(lADDSP);

        for (int i = 2; i < compiler.getCompilerOptions().getRmax(); i++) {
            compiler.addInstruction(new PUSH(GPRegister.getR(i)));
        }

        compiler.addInstruction(new LOAD(new RegisterOffset(-2, GPRegister.LB), GPRegister.getR(2)));
        for (AbstractDeclField declField : bodyclass.getListDeclField().getList()) {
            declField.codeGenDeclFieldNull(compiler);
        }
        int maxPush = 0;
        if (!extension.getName().equals(compiler.environmentType.OBJECT.getName())) {
            compiler.addInstruction(new PUSH(GPRegister.getR(2)));
            compiler.addInstruction(new BSR(new Label("init." + extension.getName())), "init super class");
            compiler.addInstruction(new SUBSP(new ImmediateInteger(1)));
            maxPush = 3;
        }

        int[] max = { 1, maxPush }; // max reg / max push
        for (AbstractDeclField declField : bodyclass.getListDeclField().getList()) {
            int[] res = declField.codeGenDeclField(compiler);
            if (res[0] > max[0]) {
                max[0] = res[0];
            }
            if (res[1] > max[1]) {
                max[1] = res[1];
            }
        }

        for (int i = compiler.getCompilerOptions().getRmax() - 1; i > 1; i--) {
            compiler.addInstruction(new POP(GPRegister.getR(i)));
        }

        lADDSP.setInstruction(new ADDSP(new ImmediateInteger(compiler.getCompilerOptions().getRmax() - 2)));
        lTSTO.setInstruction(new TSTO(new ImmediateInteger(max[1] + compiler.getCompilerOptions().getRmax() - 2)));

        compiler.addInstruction(new RTS());

        for (AbstractDeclMethod declMethod : bodyclass.getListDeclMethod().getList()) {
            declMethod.codeGenBody(compiler, name.getClassDefinition());
        }

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {

        name.prettyPrint(s, prefix, false);
        if (extension != null) {
            extension.prettyPrint(s, prefix, false);
        }
        bodyclass.prettyPrintChildren(s, prefix);
        // throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // throw new UnsupportedOperationException("Not yet supported");
        name.iter(f);
        extension.iter(f);
        bodyclass.iterChildren(f);
    }

}
