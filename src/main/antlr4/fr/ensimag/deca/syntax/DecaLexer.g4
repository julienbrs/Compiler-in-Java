lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

// Deca lexer rules.
// DUMMY_TOKEN: .; // A FAIRE : Règle bidon qui reconnait tous les caractères.
                // A FAIRE : Il faut la supprimer et la remplacer par les vraies règles.

// Liste token de base
OBRACE : '{';
CBRACE : '}';
COLON : ':';
COMMA : ',';
EQUALS : '=';
PRINT : 'print';
OPARENT : '(';
CPARENT : ')';
PRINTLN : 'println';
PRINTX : 'printx';
PRINTLNX : 'printlnx';
WHILE : 'while';
RETURN : 'return';
IF : 'if';
ELSE : 'else';
OR : '||';
AND : '&&';
EQEQ : '==';
NEQ : '!=';
LEQ : '<=';
GEQ : '>=';
GT : '>';
LT : '<';
INSTANCEOF : 'instanceof';
PLUS : '+';
MINUS : '-';
TIMES : '*';
SLASH : '/';
PERCENT : '%';
EXCLAM : '!';
DOT : '.';
READINT : 'readInt';
READFLOAT : 'readFloat';
NEW : 'new';
INT : '0' | POSITIVE_DIGIT DIGIT*;
FLOAT : FLOATDEC | FLOATHEX;
STRING : '"' (STRING_CAR | '\\"' | '\\\\')* '"';
TRUE : 'true';
FALSE : 'false';
THIS : 'this';
NULL : 'null';
CLASS : 'class';
IDENT : (LETTER | '$' | '_') (LETTER | DIGIT | '$' | '_')*;
EXTENDS : 'extends';
PROTECTED : 'protected';
ASM : 'asm';
MULTI_LINE_STRING : '"' (STRING_CAR | '\\"' | '\\\\' | '\n')* '"';

// Espace
SPACE : ' ' { skip(); };

// Spe char
LF : '\n'{ skip(); };
CR : '\r';
TAB : '\t';
BACKSLASH : '\\';
QUOTE : '\'';
fragment DQUOTE : '"';
SEMI : ';';
UNDERSCORE : '_';

// Chiffre et mots
fragment LETTER : ('a'..'z'|'A'..'Z');
fragment DIGIT : '0'..'9';
fragment POSITIVE_DIGIT : '1'..'9';
WORD : LETTER+;
NUMBER : DIGIT+;

fragment STRING_CAR : ~('\n' | '"' | '\\');

fragment SIGN : PLUS | MINUS | ;
fragment EXP : ('E' | 'e') SIGN NUMBER;
fragment DEC : NUMBER '.' NUMBER;
fragment FLOATDEC : (DEC | DEC EXP)('F' | 'f' | );
fragment DIGITHEX : DIGIT | 'A'..'F' | 'a'..'f';
fragment NUMHEX : DIGITHEX+;
fragment FLOATHEX : ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUMBER ('F' | 'f' | );

// Comment
COMMENT : ('/*' .*? '*/' | '//' .*? '\n') { skip(); };

//include 
FILENAME : (LETTER|DIGIT|'.'|'-'|'_')+;
INCLUDE  : '#include' SPACE*'"'FILENAME'"'{doInclude(FILENAME);};
