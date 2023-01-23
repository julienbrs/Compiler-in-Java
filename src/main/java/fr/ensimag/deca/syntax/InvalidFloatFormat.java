package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.ParserRuleContext;

public class InvalidFloatFormat extends DecaRecognitionException{

    public InvalidFloatFormat(DecaParser recognizer, ParserRuleContext ctx) {
        super(recognizer, ctx);
    }
    public InvalidFloatFormat(DecaParserExtension recognizer, ParserRuleContext ctx) {
        super(recognizer, ctx);
    }
    @Override
    public String getMessage() {
        return "Float cannot be infinite";
    }
    
}
