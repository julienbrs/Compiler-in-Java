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
import fr.ensimag.deca.context.Type;

/**
 * 
 */

public class Test_verifyExpr_UnaryMinus {
	final Type INT = new IntType(null);
	final Type FLOAT = new FloatType(null);
	final Type BOOL = new BooleanType(null);

	@Mock
	AbstractExpr opInt;
	@Mock
	AbstractExpr opFloat;
	@Mock
	AbstractExpr opBoolean;

	DecacCompiler compiler;

	@BeforeEach
	public void setup() throws ContextualError {
		MockitoAnnotations.initMocks(this);
		compiler = new DecacCompiler(null, null);
		when(opInt.verifyExpr(compiler, null, null)).thenReturn(INT);
		when(opFloat.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
		when(opBoolean.verifyExpr(compiler, null, null)).thenReturn(BOOL);
	}

	@Test
	public void verifyExpr_ShouldReturnInt_WhenOperandIsInt() throws ContextualError {
		UnaryMinus u = new UnaryMinus(opInt);
		assertTrue(u.verifyExpr(compiler, null, null).isInt());
		verify(opInt).verifyExpr(compiler, null, null);
		verify(opInt, times(1)).verifyExpr(compiler, null, null);
	}

	@Test
	public void verifyExpr_ShouldReturnFloat_WhenOperandIsFloat() throws ContextualError {
		UnaryMinus u = new UnaryMinus(opFloat);
		assertTrue(u.verifyExpr(compiler, null, null).isFloat());
		verify(opFloat).verifyExpr(compiler, null, null);
		verify(opFloat, times(1)).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void verifyExpr_ShouldReturnBoolean_WhenOperandIsBoolean() throws ContextualError {
		UnaryMinus u = new UnaryMinus(opBoolean);
		assertTrue(u.verifyExpr(compiler, null, null).isBoolean());
		verify(opBoolean).verifyExpr(compiler, null, null);
		verify(opBoolean, times(1)).verifyExpr(compiler, null, null);
	}
}