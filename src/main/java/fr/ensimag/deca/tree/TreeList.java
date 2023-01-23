package fr.ensimag.deca.tree;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.Validate;

/**
 * Tree List
 * 
 * @author gl11
 * @date 01/01/2023
 */
public abstract class TreeList<TreeType extends Tree> extends Tree {
    /*
     * We could allow external iterators by adding
     * "implements Iterable<AbstractInst>" but it's cleaner to provide our own
     * iterators, to make sure all callers iterate the same way (Main,
     * IfThenElse, While, ...). If external iteration is needed, use getList().
     */

    private List<TreeType> list = new ArrayList<TreeType>();

    /**
     * Adds a tree type to the tree if its not null
     * @param i
     */
    public void add(TreeType i) {
        Validate.notNull(i);
        list.add(i);
    }

    /**
     * @return the list contained in the class, read-only. Use getModifiableList()
     *         if you need to change elements of the list.
     */
    public List<TreeType> getList() {
        return Collections.unmodifiableList(list);
    }

    /**
     * Sets the index and element of a tree type
     * @param index
     * @param element
     * @return
     */
    public TreeType set(int index, TreeType element) {
        return list.set(index, element);
    }

    /**
     * Check if the tree is empty or not
     * @return a boolean
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Adds a tree type to the list
     * @return an iterator of tree type
     */
    public Iterator<TreeType> iterator() {
        return list.iterator();
    }

    /**
     * The size of the tree
     * @return the size
     */
    public int size() {
        return list.size();
    }

    /**
     * Do not check anything about the location.
     * 
     * It is possible to use setLocation() on a list, but it is also OK not to
     * set it.
     */
    @Override
    protected void checkLocation() {
        // nothing
    }

    @Override
    protected String prettyPrintNode() {
        return super.prettyPrintNode() +
                " [List with " + getList().size() + " elements]";
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        int count = getList().size();
        for (TreeType i : getList()) {
            i.prettyPrint(s, prefix, count == 1, true);
            count--;
        }
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        for (TreeType i : getList()) {
            i.iter(f);
        }
    }

}
