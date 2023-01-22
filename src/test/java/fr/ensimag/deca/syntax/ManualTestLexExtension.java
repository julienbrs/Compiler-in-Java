package fr.ensimag.deca.syntax;

import java.io.IOException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @author GL11
 * @date 22/01/2023
 */
public class ManualTestLexExtension {
    // This is a launcher for tests of extension

    // This is a test class, we do not try to give user-friendly error messages
    // but just throw exception to the user if something goes wrong (=> throws
    // IOException)
    public static void main(String[] args) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexerExtension lex = AbstractDecaLexer.createLexerFromArgsExt(args);
        System.exit(lex.debugTokenStream() ? 1 : 0);
    }
}
