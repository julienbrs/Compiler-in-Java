package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BNE;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int offset) {
        codeGenLeftOperande(compiler, offset);
        Label endAnd = new Label("And_end");
        // TODO : si faux saut a la fin
        compiler.addInstruction(new BNE(endAnd)); // verif instruction Branchement conditionnel
        codeGenRightOperande(compiler, offset);
        compiler.addLabel(endAnd); // Etiquette a revoir
    }

}
