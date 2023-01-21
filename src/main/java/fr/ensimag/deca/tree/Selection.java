package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.antlr.v4.runtime.misc.Triple;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

public class Selection extends AbstractSelection {
    private AbstractExpr expr;
    private AbstractIdentifier ident;

    public Selection(AbstractExpr expr, AbstractIdentifier ident) {
        this.expr = expr;
        this.ident = ident;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        ident.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        ident.iter(f);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // ERROR MSG
        ClassType t = expr.verifyExpr(compiler, localEnv, currentClass).asClassType("Can't select field from \"" + expr.getType() + "\" : rule 3.65", getLocation());
        ident.verifyExpr(compiler, t.getDefinition().getMembers(), currentClass);
        FieldDefinition def = ident.getFieldDefinition();
        if (def.getVisibility().equals(Visibility.PROTECTED)) {
            if (currentClass == null) {
                // ERROR MSG
                throw new ContextualError("Can't acces a protected field in main : rule 3.66", getLocation());
            }
            if (!t.isSubClassOf(currentClass.getType()) || !currentClass.getType().isSubClassOf(def.getContainingClass().getType())) {
                // ERROR MSG
                throw new ContextualError("Can't acces a protected field from a foreign class : rule 3.66", getLocation());
            }
        }
        setType(def.getType());
        return getType();
    }

    @Override
    protected int[] codeGenExpr(DecacCompiler compiler, int offset) {
        int[] res = expr.codeGenExpr(compiler, offset);
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new CMP(new NullOperand(), GPRegister.getR(offset)));
            compiler.addInstruction(new BEQ(new Label("dereferencement_null")));
        }
        compiler.addInstruction(new LOAD(new RegisterOffset(ident.getFieldDefinition().getIndex(), GPRegister.getR(offset)), GPRegister.getR(offset)));
        return res;
    }

    public Triple<int[], Integer, DAddr> codeGenLValue(DecacCompiler compiler, int offset) {
        int[] resExpr = expr.codeGenExpr(compiler, offset);
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new CMP(new NullOperand(), GPRegister.getR(offset)));
            compiler.addInstruction(new BEQ(new Label("dereferencement_null")));
        }
        Triple<int[], Integer, DAddr> res = new Triple<>(resExpr, offset + 1, new RegisterOffset(ident.getFieldDefinition().getIndex(), GPRegister.getR(offset)));
        return res;
    }

    @Override
    protected int[] codeGenBool(DecacCompiler compiler, boolean aim, Label dest, int offset) {
        int[] res = expr.codeGenExpr(compiler, offset);
        compiler.addInstruction(new LOAD(new RegisterOffset(ident.getFieldDefinition().getIndex(), GPRegister.getR(offset)), GPRegister.R0));
        compiler.addInstruction(new CMP(0, GPRegister.R0));
        if (aim) {
            compiler.addInstruction(new BNE(dest));
        } else {
            compiler.addInstruction(new BEQ(dest));
        }
        return res;
    }

    @Override
    public Type verifyLValue(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        return verifyExpr(compiler, localEnv, currentClass);
    }
    
}
