package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public void add(Type t) {
        args.add(t);
    }
    
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    public int size() {
        return args.size();
    }

    public Iterator<Type> iterator() {
        return args.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Signature && ((Signature) other).size() == args.size()) {
            Signature sig = (Signature) other;
            int i = 0;
            for (Type t : args) {
                if (!t.sameType(sig.paramNumber(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
