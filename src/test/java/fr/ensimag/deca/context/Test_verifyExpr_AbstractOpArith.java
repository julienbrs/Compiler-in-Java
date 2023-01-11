package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.ConvFloat;
import fr.ensimag.deca.tree.Plus;
import fr.ensimag.deca.tree.Minus;
import fr.ensimag.deca.tree.Multiply;
import fr.ensimag.deca.tree.Divide;
import fr.ensimag.deca.tree.Modulo;
import fr.ensimag.deca.context.Type;

/**
 * 
 */

public class Test_verifyExpr_AbstractOpArith {
	private DecacCompiler compiler = new DecacCompiler(null, null);

	final Type INT = compiler.environmentType.INT;
	final Type FLOAT = compiler.environmentType.FLOAT;
	final Type BOOL = compiler.environmentType.BOOLEAN;
        
	@Mock
	AbstractExpr lOpBool;
	@Mock
	AbstractExpr rOpBool;
	@Mock
	AbstractExpr lOpInt;
	@Mock
	AbstractExpr rOpInt;
	@Mock
	AbstractExpr lOpFloat;
	@Mock
	AbstractExpr rOpFloat;

	@BeforeEach
        public void setup() throws ContextualError {
            MockitoAnnotations.initMocks(this);
            when(lOpBool.verifyExpr(compiler, null, null)).thenReturn(BOOL);
            when(rOpBool.verifyExpr(compiler, null, null)).thenReturn(BOOL);
            when(lOpInt.verifyExpr(compiler, null, null)).thenReturn(INT);
            when(rOpInt.verifyExpr(compiler, null, null)).thenReturn(INT);
            when(lOpFloat.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
            when(rOpFloat.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
	}

	// Plus

	@Test
	public void verifyExprPlusBoolBool() throws ContextualError {
		Plus op = new Plus(lOpBool, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"+"+"\" between \""+"boolean"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprPlusBoolInt() throws ContextualError {
		Plus op = new Plus(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"+"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprPlusBoolFloat() throws ContextualError {
		Plus op = new Plus(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"+"+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprPlusIntBool() throws ContextualError {
		Plus op = new Plus(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"+"+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprPlusIntInt() throws ContextualError {
		Plus op = new Plus(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.INT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprPlusIntFloat() throws ContextualError {
		Plus op = new Plus(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprPlusFloatBool() throws ContextualError {
		Plus op = new Plus(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"+"+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprPlusFloatInt() throws ContextualError {
		Plus op = new Plus(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprPlusFloatFloat() throws ContextualError {
		Plus op = new Plus(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	// Minus

	@Test
	public void verifyExprMinusBoolBool() throws ContextualError {
		Minus op = new Minus(lOpBool, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"-"+"\" between \""+"boolean"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMinusBoolInt() throws ContextualError {
		Minus op = new Minus(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"-"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMinusBoolFloat() throws ContextualError {
		Minus op = new Minus(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"-"+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMinusIntBool() throws ContextualError {
		Minus op = new Minus(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"-"+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMinusIntInt() throws ContextualError {
		Minus op = new Minus(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.INT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprMinusIntFloat() throws ContextualError {
		Minus op = new Minus(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprMinusFloatBool() throws ContextualError {
		Minus op = new Minus(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"-"+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMinusFloatInt() throws ContextualError {
		Minus op = new Minus(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprMinusFloatFloat() throws ContextualError {
		Minus op = new Minus(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	// Multiply

	@Test
	public void verifyExprMultiplyBoolBool() throws ContextualError {
		Multiply op = new Multiply(lOpBool, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"*"+"\" between \""+"boolean"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMultiplyBoolInt() throws ContextualError {
		Multiply op = new Multiply(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"*"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMultiplyBoolFloat() throws ContextualError {
		Multiply op = new Multiply(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"*"+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMultiplyIntBool() throws ContextualError {
		Multiply op = new Multiply(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"*"+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMultiplyIntInt() throws ContextualError {
		Multiply op = new Multiply(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.INT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprMultiplyIntFloat() throws ContextualError {
		Multiply op = new Multiply(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprMultiplyFloatBool() throws ContextualError {
		Multiply op = new Multiply(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"*"+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprMultiplyFloatInt() throws ContextualError {
		Multiply op = new Multiply(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprMultiplyFloatFloat() throws ContextualError {
		Multiply op = new Multiply(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	// Divide

	@Test
	public void verifyExprDivideBoolBool() throws ContextualError {
		Divide op = new Divide(lOpBool, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"/"+"\" between \""+"boolean"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprDivideBoolInt() throws ContextualError {
		Divide op = new Divide(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"/"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprDivideBoolFloat() throws ContextualError {
		Divide op = new Divide(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"/"+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprDivideIntBool() throws ContextualError {
		Divide op = new Divide(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"/"+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprDivideIntInt() throws ContextualError {
		Divide op = new Divide(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.INT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprDivideIntFloat() throws ContextualError {
		Divide op = new Divide(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprDivideFloatBool() throws ContextualError {
		Divide op = new Divide(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"/"+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprDivideFloatInt() throws ContextualError {
		Divide op = new Divide(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprDivideFloatFloat() throws ContextualError {
		Divide op = new Divide(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.FLOAT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}


	// Modulo

	@Test
	public void verifyExprModuloBoolBool() throws ContextualError {
		Modulo op = new Modulo(lOpBool, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"%"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprModuloBoolInt() throws ContextualError {
		Modulo op = new Modulo(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"%"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprModuloBoolFloat() throws ContextualError {
		Modulo op = new Modulo(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"%"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprModuloIntBool() throws ContextualError {
		Modulo op = new Modulo(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"%"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprModuloIntInt() throws ContextualError {
		Modulo op = new Modulo(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.INT, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprModuloIntFloat() throws ContextualError {
		Modulo op = new Modulo(lOpInt, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"%"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprModuloFloatBool() throws ContextualError {
		Modulo op = new Modulo(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"%"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprModuloFloatInt() throws ContextualError {
		Modulo op = new Modulo(lOpFloat, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"%"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprModuloFloatFloat() throws ContextualError {
		Modulo op = new Modulo(lOpFloat, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"%"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}
}