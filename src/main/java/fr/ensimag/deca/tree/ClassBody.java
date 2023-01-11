package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.tools.IndentPrintStream;

public class ClassBody extends Tree{
    private ListDeclMethod declMethod;
    private ListDeclField declField;

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
        declMethod.prettyPrint(s, prefix, false);
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }

    
}
