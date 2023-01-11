package fr.ensimag.deca.syntax.non_terminaux;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import fr.ensimag.deca.tree.*;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tree.IntLiteral;
import fr.ensimag.deca.tree.FloatLiteral;
import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.FloatType;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.context.ContextualError;

public class test_verifyExpr_AbstractOpArith {

    
    private SymbolTable symbolTable;
    private DecacCompiler compiler;

    @BeforeEach
    public void initEach(){
        DecacCompiler compiler = new DecacCompiler(null, null);
        SymbolTable symbolTable = new SymbolTable();
    }

    @Test
    public void testVerifyExpr() throws ContextualError {
        Symbol symbInt = symbolTable.create("int");
        IntType intType = new IntType(symbInt);

        IntLiteral leftOperand = new IntLiteral(10);
        IntLiteral rightOperand = new IntLiteral(20);

        //Invoke the method under test
        Type result = operand.verifyExpr(compiler, mockEnv, mockClassDef); 


    }
}