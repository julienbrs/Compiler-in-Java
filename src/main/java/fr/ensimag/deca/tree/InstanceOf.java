package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Instruction
 *
 * @author gl11
 * @date 16/01/2023
 */
public class InstanceOf extends AbstractExpr {

    private AbstractExpr expr;
    private AbstractIdentifier type;

    public InstanceOf(AbstractExpr expr, AbstractIdentifier type) {
        this.expr = expr;
        this.type = type;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type tExpr = expr.verifyExpr(compiler, localEnv, currentClass);
        Type tType = type.verifyType(compiler);
        if (!tExpr.isClassOrNull() || !tType.isClass()) {
            // ERROR MSG
            throw new ContextualError(" : rule 3.41", getLocation());
        }
        setType(compiler.environmentType.BOOLEAN);
        return getType();
    }

    @Override
    protected int codeGenExpr(DecacCompiler compiler, int offset) {
        int labelNumber = compiler.getLabelNumber();
        Label vrai = new Label("is_true."+labelNumber);
        Label end = new Label("end."+labelNumber);
        compiler.incrLabelNumber();
        compiler.incrLabelNumber();
        int nbPush = codeGenBool(compiler, true, vrai);
        compiler.addInstruction(new LOAD(0, GPRegister.getR(offset)));
        compiler.addInstruction(new BRA(end));
        compiler.addLabel(vrai);
        compiler.addInstruction(new LOAD(1, GPRegister.getR(offset)));
        compiler.addLabel(end);
        return nbPush;
    }

    @Override
    protected int codeGenBool(DecacCompiler compiler, boolean aim, Label dest) {
        
        int i = compiler.getLabelNumber();
        compiler.incrLabelNumber();
        Label instLabel = new Label("instanceof.boucle." + i);
        Label finLabel = new Label("instanceof.end." + i);
        Label l1;
        Label l2;

        if (aim) {
            l1 = dest;
            l2 = finLabel;
        } else {
            l1 = finLabel;
            l2 = dest;
        }

        int nbPush = expr.codeGenExpr(compiler, 2);
        compiler.addInstruction(new CMP(new NullOperand(), GPRegister.getR(2)));
        compiler.addInstruction(new BEQ(l1));
        compiler.addInstruction(new LOAD(new RegisterOffset(0, GPRegister.getR(2)), GPRegister.getR(2)));
        compiler.addInstruction(new LEA(type.getClassDefinition().getOperand(), GPRegister.getR(3)));
        compiler.addLabel(instLabel);
        compiler.addInstruction(new CMP(GPRegister.getR(2), GPRegister.getR(3)));
        compiler.addInstruction(new BEQ(l1));
        compiler.addInstruction(new CMP(new NullOperand(), GPRegister.getR(2)));
        compiler.addInstruction(new BEQ(l2));
        compiler.addInstruction(new LOAD(new RegisterOffset(0, GPRegister.getR(2)), GPRegister.getR(2)));
        compiler.addInstruction(new BRA(instLabel));
        compiler.addLabel(finLabel);

        return nbPush;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        expr.decompile(s);
        s.print(" instanceof ");
        type.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        type.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        type.iter(f);
    }

    
}