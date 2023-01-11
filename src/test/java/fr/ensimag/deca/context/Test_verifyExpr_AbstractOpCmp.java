package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.Equals;
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
	// NotEquals
	// Greater
	// GreaterOrEqual
	// Lower
.
1*MFBD QXW	e23Ù	// LowerOrEquals

	@Test
	public void testIntInt() throws ContextualError {
		Plus p = new Plus(lOpInt, rOpInt);
		assertTrue(p.verifyExpr(compiler, null, null).isInt());
	}

	@Test
	public void test() {
		
		+60
555553.-6
0.5	}
}-4
*dvw >qa²&SZ 