package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import fr.ensimag.deca.tree.*;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.tree.IntLiteral;
import fr.ensimag.deca.tree.FloatLiteral;
import fr.ensimag.deca.tree.And;

import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.FloatType;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.DecacCompiler;
import org.junit.jupiter.api.BeforeEach;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.context.ContextualError;

/**
 * Terminaux d'AbstractOpBool: AND et OR
 */

public class Test_verifyExpr_AbstractOpBool {
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
            // compiler = new DecacCompiler(null, null);
            when(lOpBool.verifyExpr(compiler, null, null)).thenReturn(BOOL);
            when(rOpBool.verifyExpr(compiler, null, null)).thenReturn(BOOL);
            when(lOpInt.verifyExpr(compiler, null, null)).thenReturn(INT);
            when(rOpInt.verifyExpr(compiler, null, null)).thenReturn(INT);
            when(lOpFloat.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
            when(rOpFloat.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
	}

	// And

	@Test
	public void verifyExprAndBoolBool() throws ContextualError {
		And op = new And(lOpBool, rOpBool) ;
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
	}
        
	@Test
	public void verifyExprAndBoolInt() throws ContextualError {       
            And op = new And(lOpBool, rOpInt) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"=="+"\" between \""+"boolean"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprAndBoolFloat() throws ContextualError {       
            And op = new And(lOpBool, rOpFloat) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"&&"+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprAndIntBool() throws ContextualError {       
            And op = new And(lOpInt, rOpBool) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"&&"+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprAndIntInt() throws ContextualError {       
            And op = new And(lOpInt, rOpInt) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"&&"+"\" between \""+"int"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprAndIntFloat() throws ContextualError {       
            And op = new And(lOpInt, rOpFloat) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"&&"+"\" between \""+"int"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprAndFloatBool() throws ContextualError {       
            And op = new And(lOpFloat, rOpBool) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"&&"+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprAndFloatInt() throws ContextualError {       
            And op = new And(lOpFloat, rOpInt) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"&&"+"\" between \""+"float"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprAndFloatFloat() throws ContextualError {       
            And op = new And(lOpFloat, rOpFloat) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"&&"+"\" between \""+"float"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
            }
            
	}

	// Or

	@Test
	public void verifyExprOrBoolBool() throws ContextualError {
		Or op = new Or(lOpBool, rOpBool) ;
		Type result = op.verifyExpr(compiler, null, null);
		assertEquals(compiler.environmentType.BOOLEAN, result );
	}
        
	@Test
	public void verifyExprOrBoolInt() throws ContextualError {       
            Or op = new Or(lOpBool, rOpInt) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"||"+"\" between \""+"boolean"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprOrBoolFloat() throws ContextualError {       
            Or op = new Or(lOpBool, rOpFloat) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"||"+"\" between \""+"boolean"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprOrIntBool() throws ContextualError {       
            Or op = new Or(lOpInt, rOpBool) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"||"+"\" between \""+"int"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprOrIntInt() throws ContextualError {       
            Or op = new Or(lOpInt, rOpInt) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"||"+"\" between \""+"int"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprOrIntFloat() throws ContextualError {       
            Or op = new Or(lOpInt, rOpFloat) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"||"+"\" between \""+"int"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprOrFloatBool() throws ContextualError {       
            Or op = new Or(lOpFloat, rOpBool) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"||"+"\" between \""+"float"+"\" and \""+"boolean"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprOrFloatInt() throws ContextualError {       
            Or op = new Or(lOpFloat, rOpInt) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"||"+"\" between \""+"float"+"\" and \""+"int"+"\": rule 3.33", e.getMessage());
            }
            
	}
        
	@Test
	public void verifyExprOrFloatFloat() throws ContextualError {       
            Or op = new Or(lOpFloat, rOpFloat) ;
            try {
				op.verifyExpr(compiler, null, null);
                fail("Expected a ContextualError to be thrown.");
            }
            catch (ContextualError e) {
                assertEquals("Can't do \""+"||"+"\" between \""+"float"+"\" and \""+"float"+"\": rule 3.33", e.getMessage());
            }
            
	}
}