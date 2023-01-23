package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

public class ArrayDefinition extends VariableDefinition {
    private int level;
    private Type localType;
    
    public ArrayDefinition(Type type, Type localType, Location location,int level) {
        super(type, location);
        this.level = level;
        this.localType = localType;
    }
    @Override
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public Type getLocalType() {
        return localType;
    }

    public void setLocalType(Type t) {
        localType = t;
    }

    @Override
    public String getNature() {
        return "array";
    }

    @Override
    public boolean isExpression() {
        return true;
    }
    
}
