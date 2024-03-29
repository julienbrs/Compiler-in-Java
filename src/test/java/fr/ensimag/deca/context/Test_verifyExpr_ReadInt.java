package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.Plus;

/**
 * 
 */

public class Test_verifyExpr_ReadInt {
	final Type INT = new IntType(null);
	final Type FLOAT = new FloatType(null);

	@Mock
	AbstractExpr lOpInt;
	@Mock
	AbstractExpr rOpInt;
	@Mock
	AbstractExpr lOpFloat;
	@Mock
	AbstractExpr rOpFloat;

	DecacCompiler compiler;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() throws ContextualError {
		MockitoAnnotations.initMocks(this);
		compiler = new DecacCompiler(null, null);
		when(lOpInt.verifyExpr(compiler, null, null)).thenReturn(INT);
		when(rOpInt.verifyExpr(compiler, null, null)).thenReturn(INT);
		when(lOpFloat.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
		when(rOpFloat.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
	}

	@Test
	public void testIntInt() throws ContextualError {
		Plus p = new Plus(lOpInt, rOpInt);
		assertTrue(p.verifyExpr(compiler, null, null).isInt());
		verify(lOpInt).verifyExpr(compiler, null, null);
		verify(rOpInt).verifyExpr(compiler, null, null);
		verify(lOpInt, times(1)).verifyExpr(compiler, null, null);
		verify(rOpInt, times(1)).verifyExpr(compiler, null, null);
	}
}