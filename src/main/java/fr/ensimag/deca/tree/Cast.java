package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.INT;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Cast
 *
 * @author gl11
 * @date 16/01/2023
 */
public class Cast extends AbstractExpr {

    private AbstractIdentifier type;
    private AbstractExpr expr;

    /**
     * Sets the type and the expression to cast
     * @param type
     * @param expr
     */
    public Cast(AbstractIdentifier type, AbstractExpr expr) {
        this.type = type;
        this.expr = expr;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type t = type.verifyType(compiler);
        Type e = expr.verifyExpr(compiler, localEnv, currentClass);
        if (e.isVoid()) {
            // ERROR MSG
            throw new ContextualError("Can't cast void : rule 3.39", getLocation());
        }
        
        if (t.sameType(e)) {
            setType(t);
            return getType();
        } else if (t.isInt() && e.isFloat()) {
            setType(t);
            return getType();
        } else if ((t.isFloat() && e.isInt())) {
            setType(t);
            return getType();
        } else if (t.isNull() && e.isClass()) {
            setType(t);
            return getType();
        } else if (t.isClass() && e.isNull()) {
            setType(t);
            return getType();
        } else {
            // ERROR MSG
            ClassType tClass = t.asClassType("Can't cast \"" + t + "\" to \"" + e + "\" : rule 3.39", getLocation());
            // ERROR MSG
            ClassType eClass = e.asClassType("Can't cast \"" + t + "\" to \"" + e + "\" : rule 3.39", getLocation());
            if (tClass.isSubClassOf(eClass)) {
                setType(t);
                return getType();
            } else if (eClass.isSubClassOf(tClass)) {
                setType(t);
                return getType();
            }
        }
        // ERROR MSG
        throw new ContextualError("Can't cast \"" + t + "\" to \"" + e + "\" : rule 3.39", getLocation());
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        int[] resExpr = expr.codeGenExpr(compiler, offset);
        if ((type.getType().isInt() || type.getType().isFloat() || type.getType().isBoolean()) && type.getType().sameType(expr.getType())) {
            return resExpr;
        }
        if (type.getType().isFloat() && expr.getType().isInt()) {
            compiler.addInstruction(new FLOAT(GPRegister.getR(offset), GPRegister.getR(offset)));
        } else if (type.getType().isInt() && expr.getType().isFloat()) {
            compiler.addInstruction(new INT(GPRegister.getR(offset), GPRegister.getR(offset)));
            if (!compiler.getCompilerOptions().getNoCheck()) {
                compiler.addInstruction(new BOV(new Label("debordement_arithmetique")));
            }
        } else {
            int i = compiler.getLabelNumber();
            compiler.incrLabelNumber();
            Label finLabel = new Label("cast.fin." + i);
            Label castLabel = new Label("cast.boucle." + i);

            compiler.addInstruction(new CMP(new NullOperand(), GPRegister.getR(offset)));
            compiler.addInstruction(new BEQ(finLabel));
            compiler.addInstruction(new LOAD(new RegisterOffset(0, GPRegister.getR(offset)), GPRegister.R1));
            compiler.addInstruction(new LEA(type.getClassDefinition().getOperand(), GPRegister.R0));
            compiler.addLabel(castLabel);
            compiler.addInstruction(new CMP(GPRegister.R1, GPRegister.R0));
            compiler.addInstruction(new BEQ(finLabel));
            compiler.addInstruction(new CMP(new NullOperand(), GPRegister.R1));
            compiler.addInstruction(new BEQ(new Label("cast_impossible")));
            compiler.addInstruction(new LOAD(new RegisterOffset(0, GPRegister.R1), GPRegister.R1));
            compiler.addInstruction(new BRA(castLabel));
            compiler.addLabel(finLabel);
        }
        return resExpr;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        type.decompile(s);
        s.print(") (");
        expr.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        expr.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        expr.iter(f);
    }

    
}