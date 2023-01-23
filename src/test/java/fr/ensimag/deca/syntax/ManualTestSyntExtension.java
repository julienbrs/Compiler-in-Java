package fr.ensimag.deca.syntax;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractProgram;
import java.io.File;
import java.io.IOException;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Driver to test the Parser (and lexer).
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class ManualTestSyntExtension {
    public static void main(String[] args) throws IOException {
        // Uncomment the following line to activate debug traces
        // unconditionally for test_synt
        // Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexerExtension lex = AbstractDecaLexer.createLexerFromArgsExt(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParserExtension parser = new DecaParserExtension(tokens);
        File file = null;
        if (lex.getSourceName() != null) {
            file = new File(lex.getSourceName());
        }
        final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(decacCompiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
        } else {
            prog.prettyPrint(System.out);
        }
    }
}
