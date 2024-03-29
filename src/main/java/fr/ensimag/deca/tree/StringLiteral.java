package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * String literal
 *
 * @author gl11
 * @date 01/01/2023
 */
public class StringLiteral extends AbstractStringLiteral {

    @Override
    public String getValue() {
        return value;
    }

    private String value;

    /**
     * Declares the value of the string literal after verifying that it is not null
     * @param value
     */
    public StringLiteral(String value) {
        Validate.notNull(value);
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        // throw new UnsupportedOperationException("not yet implemented");
        this.setType(compiler.environmentType.STRING);
        return this.getType();
    }

    @Override
    protected int[] codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new WSTR(new ImmediateString(value)));
        int[] res = {0, 0};
        return res;
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        throw new UnsupportedOperationException("Should not end up here");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("not yet implemented");
        s.print("\""+value+"\"");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }
    
    @Override
    String prettyPrintNode() {
        return "StringLiteral (" + value + ")";
    }

}
