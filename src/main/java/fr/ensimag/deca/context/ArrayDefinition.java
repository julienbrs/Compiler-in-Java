package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

public class ArrayDefinition extends VariableDefinition {
    private int level;
    public ArrayDefinition(Type type, Location location,int level) {
        super(type, location);
        this.level = level;
        //TODO Auto-generated constructor stub
    }
    @Override
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
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
