package fr.ensimag.deca.tree;

public class DeclField extends DeclVar{
    private Visibility visibility;
    public DeclField(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization, Visibility visibility) {
        super(type, varName, initialization);
        this.visibility=visibility;
    }
    
}
