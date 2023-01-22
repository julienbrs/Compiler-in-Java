package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.InlinePortion;

/**
 * Method Asm Body
 * 
 * @author gl11
 * @date 01/01/2023
 */
public class MethodAsmBody extends AbstractMethodBody {

    private StringLiteral asm; 

    /**
     * Sets the asm value
     * @param asm
     */
    public MethodAsmBody(StringLiteral asm) {
        this.asm = asm;
    }
    
    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, EnvironmentExp paramEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        asm.verifyExpr(compiler, localEnv, currentClass);
    }

    @Override
    public void codeGenBody(DecacCompiler compiler, ClassDefinition currentClass, AbstractIdentifier ident) {
        compiler.add(new InlinePortion(asm.getValue().replace("\"", "")));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("asm(");
        asm.decompile(s);
        s.print(");");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        asm.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        asm.iter(f);        
    }
    
}
