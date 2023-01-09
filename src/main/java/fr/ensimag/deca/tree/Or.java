package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;

/**
 *
 * @author gl11
 * @date 01/01/2023
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void codeGenExpr(DecacCompiler compiler, int offset) {
        codeGenLeftOperande(compiler, offset);
        // TODO : si vrai saut a la fin
        // compiler.addInstruction(new Bcc()); verif instruction Branchement conditionnel
        codeGenRightOperande(compiler, offset);
        compiler.addLabel(new Label("E_fin")); // Etiquette a revoir
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }


}
