package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * While
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class While extends AbstractInst {

    private AbstractExpr condition;
    private ListInst body;

    /**
     * Gets condition on while
     * @return the while condition
     */
    public AbstractExpr getCondition() {
        return condition;
    }

    /**
     * Gets body of the instructions in while
     * @return body of the instructions in while
     */
    public ListInst getBody() {
        return body;
    }

    /**
     * Verifies that the condition and the body are not null and declare them
     * @param condition
     * @param body
     */
    public While(AbstractExpr condition, ListInst body) {
        Validate.notNull(condition);
        Validate.notNull(body);
        this.condition = condition;
        this.body = body;
    }
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
    ClassDefinition currentClass, Type returnType)
    throws ContextualError {
        condition.verifyCondition(compiler, localEnv, currentClass);
        body.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    protected int[] codeGenInst(DecacCompiler compiler) {
        // throw new UnsupportedOperationException("not yet implemented");
        int labelNumber = compiler.getLabelNumber();
        compiler.incrLabelNumber();
        Label whileDeb = new Label("while_start."+labelNumber);
        Label whileCond = new Label("while_cond."+labelNumber);
        compiler.addInstruction(new BRA(whileCond));
        compiler.addLabel(whileDeb);
        int[] resBody = body.codeGenListInst(compiler);
        compiler.addLabel(whileCond);
        int[] resCond = condition.codeGenBool(compiler, true, whileDeb, 2);
        int[] res = {Math.max(resBody[0], resCond[0]), Math.max(resBody[1], resCond[1])};
        return res;
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("while (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getBody().decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

}
