package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

public class ArrayType extends Type {
    Definition def;
    Location location;
    public ArrayType(Symbol name, Location location) {
        super(name);
        this.location= location;
    }

    @Override
    public boolean sameType(Type otherType) {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
