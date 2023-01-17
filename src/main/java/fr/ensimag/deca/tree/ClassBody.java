package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.tools.IndentPrintStream;

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

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
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
