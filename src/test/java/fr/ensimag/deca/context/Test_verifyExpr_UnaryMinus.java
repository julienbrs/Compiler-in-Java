package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.UnaryMinus;

/**
 * 
 */

public class Test_verifyExpr_UnaryMinus {
	private DecacCompiler compiler = new DecacCompiler(null, null);

	final Type INT = compiler.environmentType.INT;
	final Type FLOAT = compiler.environmentType.FLOAT;
	final Type BOOL = compiler.environmentType.BOOLEAN;

	@Mock
	AbstractExpr opInt;
	@Mock
	AbstractExpr opFloat;
	@Mock
	AbstractExpr opBoolean;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() throws ContextualError {
		MockitoAnnotations.initMocks(this);
		when(opInt.verifyExpr(compiler, null, null)).thenReturn(INT);
		when(opFloat.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
		when(opBoolean.verifyExpr(compiler, null, null)).thenReturn(BOOL);
	}

	@Test
	public void verifyExpr_ShouldReturnInt_WhenOperandIsInt() throws ContextualError {
		UnaryMinus u = new UnaryMinus(opInt);
		assertTrue(u.verifyExpr(compiler, null, null).isInt());
	}

	@Test
	public void verifyExpr_ShouldReturnFloat_WhenOperandIsFloat() throws ContextualError {
		UnaryMinus u = new UnaryMinus(opFloat);
		assertTrue(u.verifyExpr(compiler, null, null).isFloat());
	}
	
	@Test
	public void verifyExpr_ShouldReturnBoolean_WhenOperandIsBoolean() throws ContextualError {
		UnaryMinus u = new UnaryMinus(opBoolean);
		try  {
			u.verifyExpr(compiler, null, null);
			fail("Expected a ContextualError to be thrown.");
		} catch (ContextualError e) {
			assertEquals("Can't apply UnaryMinus on \""+"boolean"+"\" type : rule 3.62", e.getMessage());
		}
	}
}