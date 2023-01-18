package fr.ensimag.deca.tree;

import java.util.Iterator;

import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl11
 * @date 01/01/2023
 */
public class ListExpr extends TreeList<AbstractExpr> {

    @Override
    public void decompile(IndentPrintStream s) {
        // throw new UnsupportedOperationException("Not yet implemented");

        // boolean first = true;
        // for (AbstractExpr i : getList()) {
        // if (!first) {
        // s.print(', ');
        // }
        // i.decompileInst(s);
        // }

        Iterator<AbstractExpr> ite = getList().iterator();
        AbstractExpr current;
        if (ite.hasNext()) {
            current = ite.next();
            current.decompile(s);
        }
        while (ite.hasNext()) {
            current = ite.next();
            s.print(", ");
            current.decompile(s);
        }
    }
}
