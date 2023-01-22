package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

public class ArrayType extends Type {
    ArrayDefinition def;
    Location location;

    public ArrayType(Symbol name, Type localType, Location location, int level) {
        super(name);
        this.location= location;
        this.def = new ArrayDefinition(this, localType, location, level);
    }

    public Definition getDefinition() {
        return def;
    }

    public Type getEltType() {
        return def.getLocalType();
    }

    public void setEltType(Type t) {
        def.setLocalType(t);
    }

    public int getLevel() {
        return def.getLevel();
    }

    @Override
    public boolean isArray() {
        return true;
    }

    public Type subType() {
        if (getLevel() > 1) {
            SymbolTable sTable = new SymbolTable();
            Symbol subType = sTable.create(getName().getName().substring(0, getName().getName().length() - 2));
            return new ArrayType(subType, getEltType(), location, getLevel() - 1);
        } else {
            return getEltType();
        }
    }

    @Override
    public boolean sameType(Type otherType){
        if (!otherType.isArray()) {
            return false;
        }
        ArrayType otherArrayType;
        try {
            otherArrayType = otherType.asArrayType(null, null);
        } catch (ContextualError e) {
            // pas d'exeption a priori
            otherArrayType = null;
        }
        if (!getEltType().sameType(otherArrayType.getEltType())) {
            return false;
        }
        if (!(getLevel() == otherArrayType.getLevel())) {
            return false;
        }
        return true;
    }
    
    public ArrayType asArrayType(String errorMessage, Location l)
            throws ContextualError {
        return this;
    }

}
