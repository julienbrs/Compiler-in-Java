package fr.ensimag.deca.syntax.non_terminaux;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fr.ensimag.deca.tree.*;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tree.IntLiteral;
import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.FloatType;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Plus;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

public class TestAbstractExprRValue{

    // Check that verifyRValue returns expected object when
    // ConvFloat isn't needed, and that verifyExpr is called
    // before returning the objet result
    
    @Test
    // Vérifie que si le type de l'AbstractExpr correspond au type
    // attendu
    public void testRValue() throws ContextualError {
        DecacCompiler compiler = new DecacCompiler(null, null);
        SymbolTable symbolTable = new SymbolTable();
        Symbol symbInt = symbolTable.create("int");
        IntType intType = new IntType(symbInt);

        // Instantiate the concrete objects
        IntLiteral operand = new IntLiteral(10);
        
        // Invoke the method under test
        AbstractExpr result = operand.verifyRValue(compiler, null, null, intType);

        // Assert that the returned value is of the expected type
        assertEquals(operand, result);
    }
    
    @Test
    // On est un int et on attend un float, donc on vérifie qu'on ajoute
    // bien un convfloat
    public void testRValueConvFloat() throws ContextualError {
        DecacCompiler compiler = new DecacCompiler(null, null);
        SymbolTable symbolTable = new SymbolTable();
        Symbol symbFloat = symbolTable.create("float");
        FloatType floatType = new FloatType(symbFloat);

        IntLiteral operand = new IntLiteral(10);
        
        // Invoke the method under test
        AbstractExpr result =  operand.verifyRValue(compiler, null, null, floatType);

        assertTrue(result instanceof ConvFloat);
        assertEquals(operand, ((ConvFloat) result).getOperand());
    }
    
    @Test
    // On est pas de type expected ni castable to expected
    public void testRValueError() {
        DecacCompiler compiler = new DecacCompiler(null, null);
        SymbolTable symbolTable = new SymbolTable();
        Symbol symbBool = symbolTable.create("boolean");
        BooleanType boolType = new BooleanType(symbBool);

        IntLiteral operand = new IntLiteral(10);

        try {
        
            // Invoke the method under test
            operand.verifyRValue(compiler, null, null, boolType);
            fail();
        }
        catch (ContextualError e) {
            assertEquals("Can't assign type : \"int\" to type :\"boolean\" : rule 3.28", e.getMessage());
        }
    }
}