package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.AbstractLValue;
import fr.ensimag.deca.tree.Assign;

/**
 * 
 */

public class Test_verifyExpr_Assign {
	private DecacCompiler compiler = new DecacCompiler(null, null);

	final Type INT = compiler.environmentType.INT;
	final Type FLOAT = compiler.environmentType.FLOAT;
	final Type BOOL = compiler.environmentType.BOOLEAN;

	@Mock
	AbstractLValue lvalueInt;
	@Mock
	AbstractExpr exprInt;
	@Mock
	AbstractLValue lvalueFloat;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() throws ContextualError {
		MockitoAnnotations.initMocks(this);
		when(lvalueInt.verifyLValue(compiler, null, null)).thenReturn(INT);
		when(exprInt.verifyRValue(compiler, null, null, INT)).thenReturn(exprInt);
	}

	@Test
	public void verifyExpr_ShouldReturnInt_WhenLeftOpIsIntAndRightOpIsInt() throws ContextualError {
		Assign a = new Assign(lvalueInt, exprInt);
		Type t = a.verifyExpr(compiler, null, null);
		assertTrue(t.isInt());
	}

	// TESTTODO : do more tests ?
}