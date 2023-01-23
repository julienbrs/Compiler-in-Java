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
 * Full if/else if/else statement.
 *
 * @author gl11
 * @date 01/01/2023
 */
public class IfThenElse extends AbstractInst {
    
    private final AbstractExpr condition; 
    private final ListInst thenBranch;
    private ListInst elseBranch;

    /**
     * Verifies the instructions and conditions and sets their values
     * @param condition
     * @param thenBranch
     * @param elseBranch
     */
    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
                condition.verifyCondition(compiler, localEnv, currentClass);
                thenBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
                elseBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    protected int[] codeGenInst(DecacCompiler compiler) {
        // throw new UnsupportedOperationException("not yet implemented");
        int numLabel = compiler.getLabelNumber();
        compiler.incrLabelNumber();
        Label elseLabel = new Label("else."+numLabel);
        Label endLabel = new Label("end."+numLabel);
        int[] resCond = condition.codeGenBool(compiler, false, elseLabel, 2);
        int[] resThen = thenBranch.codeGenListInst(compiler);
        compiler.addInstruction(new BRA(endLabel));
        compiler.addLabel(elseLabel);
        int[] resElse = elseBranch.codeGenListInst(compiler);
        compiler.addLabel(endLabel);
        int[] res = {Math.max(resCond[0], Math.max(resThen[0], resElse[0])), Math.max(resCond[1], Math.max(resThen[1], resElse[1]))};
        return res;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // throw new UnsupportedOperationException("not yet implemented");
        s.print("if (");
        condition.decompile(s);
        s.println(") {");
        s.indent();
        thenBranch.decompile(s);
        s.unindent();
        s.println("} else {");
        s.indent();
        elseBranch.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
