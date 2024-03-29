package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.BooleanLiteral;

/**
 * 
 */

public class Test_verifyExpr_BooleanLiteral {
	private DecacCompiler compiler = new DecacCompiler(null, null);

	final Type INT = compiler.environmentType.INT;
	final Type FLOAT = compiler.environmentType.FLOAT;
	final Type BOOL = compiler.environmentType.BOOLEAN;

	@Test
	public void verifyExpr_ShouldReturnBoolean_WhenLiteralIsFalse() throws ContextualError {
		BooleanLiteral b = new BooleanLiteral(false);
		Type t = b.verifyExpr(compiler, null, null);
		assertTrue(t.isBoolean());
	}

	@Test
	public void verifyExpr_ShouldReturnBoolean_WhenLiteralIsTrue() throws ContextualError {
		BooleanLiteral b = new BooleanLiteral(true);
		Type t = b.verifyExpr(compiler, null, null);
		assertTrue(t.isBoolean());
	}
}