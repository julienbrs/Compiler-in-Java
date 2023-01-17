package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.ParserRuleContext;

public class InvalidIntFormat extends  DecaRecognitionException {

    public InvalidIntFormat(DecaParser recognizer, ParserRuleContext ctx) {
        super(recognizer, ctx);
        
    }
    @Override
    public String getMessage() {
        return "INT is too big";
    }
    
}
