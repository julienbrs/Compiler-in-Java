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

/**
 * Class Body
 *
 * @author gl11
 * @date 16/01/2023
 */
public class ClassBody extends Tree{

    private ListDeclMethod declMethod;
    private ListDeclField declField;

    /**
     * Gets the method to declare
     * @return the metehod to declare
     */
    public ListDeclMethod getListDeclMethod() {
        return declMethod;
    }

    /**
     * Gets the field to declare
     * @return the field to declare
     */
    public ListDeclField getListDeclField() {
        return declField;
    }

    /**
     * Sets the method and the field to declare
     * @param declMethod
     * @param declField
     */
    public ClassBody(ListDeclMethod declMethod, ListDeclField declField) {
        this.declMethod = declMethod;
        this.declField = declField;
    }

    /**
     * Initializes the method and the field to declare
     */
    public ClassBody() {
        this.declMethod = new ListDeclMethod();
        this.declField = new ListDeclField();
    }

    /**
     * Adds a method to a declared method
     * @param a
     */
    public void addDeclMethod(DeclMethod a){
        declMethod.add(a);
    }

    /**
     * Adds a field to declared fields
     * @param a
     */
    public void addDeclField(ListDeclField a){
        for (DeclField i : a.getList()) {
            assert(i!=null);
            this.declField.add(i);
        }
        
    }

    /**
     * Construction of the table of method labels;
     * Generation of code to build the table of methods.
     * @param compiler
     * @param currentClass
     * @param offset
     */
    public void codeGenVTable(DecacCompiler compiler, AbstractIdentifier currentClass, int offset) {
        for (AbstractDeclMethod aDeclMethod : declMethod.getList()) {
            Label label = new Label("code." + currentClass.getName() + "." + aDeclMethod.getName());
            aDeclMethod.getIdent().getMethodDefinition().setLabel(label);
            int index = aDeclMethod.getIdent().getMethodDefinition().getIndex();
            compiler.addInstruction(new LOAD(new LabelOperand(label), GPRegister.getR(2)));
            compiler.addInstruction(new STORE(GPRegister.getR(2), new RegisterOffset(offset + index, GPRegister.GB)));
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
