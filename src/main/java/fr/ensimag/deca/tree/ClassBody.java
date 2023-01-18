package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

public class ClassBody extends Tree{
    private ListDeclMethod declMethod;
    private ListDeclField declField;

    public ListDeclMethod getListDeclMethod() {
        return declMethod;
    }

    public ListDeclField getListDeclField() {
        return declField;
    }

    public ClassBody(ListDeclMethod declMethod, ListDeclField declField) {
        this.declMethod = declMethod;
        this.declField = declField;
    }
    public ClassBody() {
        this.declMethod = new ListDeclMethod();
        this.declField = new ListDeclField();
    }
    public void addDeclMethod(DeclMethod a){
        declMethod.add(a);
    }
    public void addDeclField(ListDeclField a){
        for (DeclField i : a.getList()) {
            assert(i!=null);
            this.declField.add(i);
        }
        
    }

    public void codeGenVTable(DecacCompiler compiler, AbstractIdentifier currentClass, int offset) {
        for (AbstractDeclMethod aDeclMethod : declMethod.getList()) {
            Label label = new Label("code." + currentClass.getName() + "." + aDeclMethod.getName());
            aDeclMethod.getIdent().getMethodDefinition().setLabel(label);
            int index = aDeclMethod.getIdent().getMethodDefinition().getIndex();
            compiler.addInstruction(new LOAD(new LabelOperand(label), GPRegister.getR(2)));
            compiler.addInstruction(new STORE(GPRegister.getR(2), new RegisterOffset(offset + index, GPRegister.GB)));
            offset++;
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        declField.decompile(s);
        declMethod.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declField.prettyPrint(s, prefix, false);
        declMethod.prettyPrint(s, prefix, true);        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declField.iter(f);
        declMethod.iter(f);
    }

    
}
