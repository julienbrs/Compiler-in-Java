package fr.ensimag.deca.context;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.syntax.AbstractDecaLexer;
import fr.ensimag.deca.syntax.DecaParserExtension;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.LocationException;
import java.io.IOException;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Driver to test the contextual analysis (together with lexer/parser)
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class ManualTestContextExtension {
    public static void main(String[] args) throws IOException {
        // Logger.getRootLogger().setLevel(Level.DEBUG);
        fr.ensimag.deca.syntax.DecaLexerExtension lex = AbstractDecaLexer.createLexerFromArgsExt(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParserExtension parser = new DecaParserExtension(tokens);
        DecacCompiler compiler = new DecacCompiler(new CompilerOptions(), null);
        parser.setDecacCompiler(compiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
            return; // Unreachable, but silents a warning.
        }
        try {
            prog.verifyProgram(compiler);
        } catch (LocationException e) {
            e.display(System.err);
            System.exit(1);
        }
        prog.prettyPrint(System.out);
    }
}
