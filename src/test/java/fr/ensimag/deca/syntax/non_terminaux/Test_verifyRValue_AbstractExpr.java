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


public class TestAbstractExprRValue{

    // Check that verifyRValue returns expected object when
    // ConvFloat isn't needed, and that verifyExpr is called
    // before returning the objet result
    
    private SymbolTable symbolTable;
    private DecacCompiler compiler;
    
    @BeforeEach
    public void initEach(){
        compiler = new DecacCompiler(null, null);
        symbolTable = new SymbolTable();
    }
    

    @Test
    /*  Vérifie que la méthode verifyRValue de la classe IntLiteral ne modifie
        pas l'expression en entrée si le type de cette expression correspond
         au type attendu */
    public void testSameType() throws ContextualError {
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
    /*  Vérifie que la conversion de type de int à float est correctement effectuée par la
        méthode verifyRValue de la classe IntLiteral. Il s'assure que cette méthode ajoute
        bien un objet de type ConvFloat avec l'opérande initialement utilisé. */
    public void testConvFloat() throws ContextualError {
        Symbol symbFloat = symbolTable.create("float");
        FloatType floatType = new FloatType(symbFloat);

        IntLiteral operand = new IntLiteral(10);
        
        // Invoke the method under test
        AbstractExpr result =  operand.verifyRValue(compiler, null, null, floatType);

        assertTrue(result instanceof ConvFloat);
        assertEquals(operand, ((ConvFloat) result).getOperand());
    }
    

    /* Prochain cas de test vérifient que la méthode verifyRValue de la classe
        IntLiteral lève une exception de type ContextualError lorsque l'expression
        en entrée n'est pas du type attendu ou ne peut pas être convertie en ce type. */

    // Cas où type = float, expectedType = boolean
    @Test
    public void testElseCase1() {
        Symbol symbBool = symbolTable.create("boolean");
        BooleanType boolType = new BooleanType(symbBool);
        FloatLiteral operand = new FloatLiteral(10.5f);
        try {
            // Invoke the method under test
            operand.verifyRValue(compiler, null, null, boolType);
            fail();
        }
        catch (ContextualError e) {
            assertEquals("Can't assign type : \"float\" to type :\"boolean\" : rule 3.28", e.getMessage());
        }
    }


    // Cas où type = int, expectedType = boolean
    @Test
    public void testElseCase2() {
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

    // Cas où type = boolean, expectedType = float
    @Test
    public void testElseCase3() {
        Symbol symbBool = symbolTable.create("float");
        FloatType floatType = new FloatType(symbBool);
        BooleanLiteral operand = new BooleanLiteral(true);
        try {
            // Invoke the method under test
            operand.verifyRValue(compiler, null, null, floatType);
            fail();
        }
        catch (ContextualError e) {
            assertEquals("Can't assign type : \"boolean\" to type :\"float\" : rule 3.28", e.getMessage());
        }
    }

    // Cas où type = boolean, expectedType = int
    @Test
    public void testElseCase4() {
        Symbol symbBool = symbolTable.create("int");
        IntType intType = new IntType(symbBool);
        BooleanLiteral operand = new BooleanLiteral(true);
        try {
            // Invoke the method under test
            operand.verifyRValue(compiler, null, null, intType);
            fail();
        }
        catch (ContextualError e) {
            assertEquals("Can't assign type : \"boolean\" to type :\"int\" : rule 3.28", e.getMessage());
        }
    }

    // Cas où type = float, expectedType = int
    // Todo: doit-on aussi faire un convInt plus tard ?
    @Test
    public void testElseCase5() {
        Symbol symbBool = symbolTable.create("int");
        IntType intType = new IntType(symbBool);
        FloatLiteral operand = new FloatLiteral(10.5f);
        try {
            // Invoke the method under test
            operand.verifyRValue(compiler, null, null, intType);
            fail();
        }
        catch (ContextualError e) {
            assertEquals("Can't assign type : \"float\" to type :\"int\" : rule 3.28", e.getMessage());
        }
    }


}