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
import fr.ensimag.deca.tree.Equals;
import fr.ensimag.deca.tree.NotEquals;
import fr.ensimag.deca.tree.Greater;
import fr.ensimag.deca.tree.GreaterOrEqual;
import fr.ensimag.deca.tree.Lower;
import fr.ensimag.deca.tree.LowerOrEqual;
import fr.ensimag.deca.context.Type;

/**
 * 
 */

public class Test_verifyExpr_AbstractOpCmp {
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

	// Equals

	@Test
	public void verifyExprEqualsBoolBool() throws ContextualError {
		Equals op = new Equals(lOpBool, rOpBool);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprEqualsBoolInt() throws ContextualError {
		Equals op = new Equals(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"=="+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprEqualsBoolFloat() throws ContextualError {
		Equals op = new Equals(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"=="+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprEqualsIntBool() throws ContextualError {
		Equals op = new Equals(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"=="+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprEqualsIntInt() throws ContextualError {
		Equals op = new Equals(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprEqualsIntFloat() throws ContextualError {
		Equals op = new Equals(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprEqualsFloatBool() throws ContextualError {
		Equals op = new Equals(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"=="+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprEqualsFloatInt() throws ContextualError {
		Equals op = new Equals(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprEqualsFloatFloat() throws ContextualError {
		Equals op = new Equals(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	// NotEquals

	@Test
	public void verifyExprNotEqualsBoolBool() throws ContextualError {
		NotEquals op = new NotEquals(lOpBool, rOpBool);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprNotEqualsBoolInt() throws ContextualError {
		NotEquals op = new NotEquals(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"!="+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprNotEqualsBoolFloat() throws ContextualError {
		NotEquals op = new NotEquals(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"!="+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprNotEqualsIntBool() throws ContextualError {
		NotEquals op = new NotEquals(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"!="+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprNotEqualsIntInt() throws ContextualError {
		NotEquals op = new NotEquals(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprNotEqualsIntFloat() throws ContextualError {
		NotEquals op = new NotEquals(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprNotEqualsFloatBool() throws ContextualError {
		NotEquals op = new NotEquals(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"!="+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprNotEqualsFloatInt() throws ContextualError {
		NotEquals op = new NotEquals(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprNotEqualsFloatFloat() throws ContextualError {
		NotEquals op = new NotEquals(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	// Greater

	@Test
	public void verifyExprGreaterBoolBool() throws ContextualError {
		Greater op = new Greater(lOpBool, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">"+"\" between \""+"boolean"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterBoolInt() throws ContextualError {
		Greater op = new Greater(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterBoolFloat() throws ContextualError {
		Greater op = new Greater(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">"+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterIntBool() throws ContextualError {
		Greater op = new Greater(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">"+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterIntInt() throws ContextualError {
		Greater op = new Greater(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprGreaterIntFloat() throws ContextualError {
		Greater op = new Greater(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprGreaterFloatBool() throws ContextualError {
		Greater op = new Greater(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">"+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterFloatInt() throws ContextualError {
		Greater op = new Greater(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprGreaterFloatFloat() throws ContextualError {
		Greater op = new Greater(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	// GreaterOrEqual

	@Test
	public void verifyExprGreaterOrEqualBoolBool() throws ContextualError {
		GreaterOrEqual op = new GreaterOrEqual(lOpBool, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">="+"\" between \""+"boolean"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterOrEqualBoolInt() throws ContextualError {
		GreaterOrEqual op = new GreaterOrEqual(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">="+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterOrEqualBoolFloat() throws ContextualError {
		GreaterOrEqual op = new GreaterOrEqual(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">="+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterOrEqualIntBool() throws ContextualError {
		GreaterOrEqual op = new GreaterOrEqual(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">="+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterOrEqualIntInt() throws ContextualError {
		GreaterOrEqual op = new GreaterOrEqual(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprGreaterOrEqualIntFloat() throws ContextualError {
		GreaterOrEqual op = new GreaterOrEqual(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprGreaterOrEqualFloatBool() throws ContextualError {
		GreaterOrEqual op = new GreaterOrEqual(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+">="+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprGreaterOrEqualFloatInt() throws ContextualError {
		GreaterOrEqual op = new GreaterOrEqual(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprGreaterOrEqualFloatFloat() throws ContextualError {
		GreaterOrEqual op = new GreaterOrEqual(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	// Lower

	@Test
	public void verifyExprLowerBoolBool() throws ContextualError {
		Lower op = new Lower(lOpBool, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<"+"\" between \""+"boolean"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerBoolInt() throws ContextualError {
		Lower op = new Lower(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerBoolFloat() throws ContextualError {
		Lower op = new Lower(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<"+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerIntBool() throws ContextualError {
		Lower op = new Lower(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<"+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerIntInt() throws ContextualError {
		Lower op = new Lower(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprLowerIntFloat() throws ContextualError {
		Lower op = new Lower(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprLowerFloatBool() throws ContextualError {
		Lower op = new Lower(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<"+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerFloatInt() throws ContextualError {
		Lower op = new Lower(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprLowerFloatFloat() throws ContextualError {
		Lower op = new Lower(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	// LowerOrEqual

	@Test
	public void verifyExprLowerOrEqualBoolBool() throws ContextualError {
		LowerOrEqual op = new LowerOrEqual(lOpBool, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<="+"\" between \""+"boolean"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerOrEqualBoolInt() throws ContextualError {
		LowerOrEqual op = new LowerOrEqual(lOpBool, rOpInt);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<="+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerOrEqualBoolFloat() throws ContextualError {
		LowerOrEqual op = new LowerOrEqual(lOpBool, rOpFloat);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<="+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerOrEqualIntBool() throws ContextualError {
		LowerOrEqual op = new LowerOrEqual(lOpInt, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<="+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerOrEqualIntInt() throws ContextualError {
		LowerOrEqual op = new LowerOrEqual(lOpInt, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprLowerOrEqualIntFloat() throws ContextualError {
		LowerOrEqual op = new LowerOrEqual(lOpInt, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertTrue(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprLowerOrEqualFloatBool() throws ContextualError {
		LowerOrEqual op = new LowerOrEqual(lOpFloat, rOpBool);
		try  {
			op.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't do \""+"<="+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
		}
	}

	@Test
	public void verifyExprLowerOrEqualFloatInt() throws ContextualError {
		LowerOrEqual op = new LowerOrEqual(lOpFloat, rOpInt);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertTrue(op.getRightOperand() instanceof ConvFloat);
	}

	@Test
	public void verifyExprLowerOrEqualFloatFloat() throws ContextualError {
		LowerOrEqual op = new LowerOrEqual(lOpFloat, rOpFloat);
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
		assertFalse(op.getLeftOperand() instanceof ConvFloat);
		assertFalse(op.getRightOperand() instanceof ConvFloat);
	}
	
}