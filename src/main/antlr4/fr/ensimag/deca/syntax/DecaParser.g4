parser grammar DecaParser;

options {
    // Default language but name it anyway
    //
    language  = Java;

    // Use a superclass to implement all helper
    // methods, instance variables and overrides
    // of ANTLR default methods, such as error
    // handling.
    //
    superClass = AbstractDecaParser;

    // Use the vocabulary generated by the accompanying
    // lexer. Maven knows how to work out the relationship
    // between the lexer and parser and will build the
    // lexer before the parser. It will also rebuild the
    // parser if the lexer changes.
    //
    tokenVocab = DecaLexer;

}

// which packages should be imported?
@header {
    import fr.ensimag.deca.tree.*;
    import java.io.PrintStream;
    import fr.ensimag.deca.tools.*;
}

@members {
    @Override
    protected AbstractProgram parseProgram() {
        return prog().tree;
    }
}

prog returns[AbstractProgram tree]
    : list_classes main EOF {
          //  assert($list_classes.tree != null);
            assert($main.tree != null);
            $tree = new Program($list_classes.tree, $main.tree);
            setLocation($tree, $list_classes.start);
        }
    ;

main returns[AbstractMain tree]
    : /* epsilon */ {
            $tree = new EmptyMain();
        }
    | block {
            assert($block.decls != null);
            assert($block.insts != null);
            $tree = new Main($block.decls, $block.insts);
            setLocation($tree, $block.start);
        }
    ;

block returns[ListDeclVar decls, ListInst insts]
    : OBRACE list_decl list_inst CBRACE {
            assert($list_decl.tree != null);
            assert($list_inst.tree != null);
            $decls = $list_decl.tree;
            $insts = $list_inst.tree;
        }
    ;

list_decl returns[ListDeclVar tree]
@init   {
            $tree = new ListDeclVar();
        }
    : decl_var_set[$tree]*
    ;

decl_var_set[ListDeclVar l]
    : type list_decl_var[$l,$type.tree] SEMI
    ;

list_decl_var[ListDeclVar l, AbstractIdentifier t]
    : dv1=decl_var[$t] {
        $l.add($dv1.tree);
        } (COMMA dv2=decl_var[$t] {
        }
      )*
    ;

decl_var[AbstractIdentifier t] returns[AbstractDeclVar tree]
@init   {
        
        }
    : i=ident {
        }
      (EQUALS e=expr {
        Initialization a = new Initialization ($e.tree);
    $tree =   new DeclVar(t, $i.tree,a ) ;
        }
      )? {
        
         $tree =   new DeclVar(t, $i.tree,new  NoInitialization() ) ;
         setLocation($tree, $i.start);
        }
    ;

list_inst returns[ListInst tree]
@init {
    $tree=new ListInst();
}
    : (inst {
        $tree.add($inst.tree);
        }
      )*
    ;

inst returns[AbstractInst tree]
    : e1=expr SEMI {
            $tree=$e1.tree;
            setLocation($tree, $expr.start);
            assert($e1.tree != null);
        }
    | SEMI {

        }
    | PRINT OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
        }
    | PRINTLN OPARENT list_expr CPARENT SEMI {
               try{
       $tree=new Println(true, $list_expr.tree);
        } catch(DecaRecognitionException e) { $tree=null; }
        
            assert($list_expr.tree != null);
        }
    | PRINTX OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
        }
    | PRINTLNX OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
        }
    | if_then_else {
            assert($if_then_else.tree != null);
        }
    | WHILE OPARENT condition=expr CPARENT OBRACE body=list_inst CBRACE {
            assert($condition.tree != null);
            assert($body.tree != null);
        }
    | RETURN expr SEMI {
            assert($expr.tree != null);
        }
    ;

if_then_else returns[IfThenElse tree]
@init {
}
    : if1=IF OPARENT condition=expr CPARENT OBRACE li_if=list_inst CBRACE {
        }
      (ELSE elsif=IF OPARENT elsif_cond=expr CPARENT OBRACE elsif_li=list_inst CBRACE {
        }
      )*
      (ELSE OBRACE li_else=list_inst CBRACE {
        }
      )?
    ;

list_expr returns[ListExpr tree]
@init   {
    $tree =new ListExpr(); 
        }
    : (e1=expr {
        $tree.add($e1.tree);
        }
       (COMMA e2=expr {
        $tree.add($e2.tree);
        }
       )* )?
    ;

expr returns[AbstractExpr tree]
    : assign_expr {
        $tree=$assign_expr.tree;
         assert($assign_expr.tree != null);
        }
    ;

assign_expr returns[AbstractExpr tree]
    : e=or_expr (
        
        /* condition: expression e must be a "LVALUE" */ {

            if (! ($e.tree instanceof AbstractLValue)) {
                throw new InvalidLValue(this, $ctx);
            }
        }
        EQUALS e2=assign_expr {
            $tree = new Assign((AbstractLValue)$e.tree,$e2.tree);
            assert($e.tree != null);
            assert($e2.tree != null);
        }
      | /* epsilon */ {
        $tree=$e.tree;
            assert($e.tree != null);
        }
      )
    ;

or_expr returns[AbstractExpr tree]
    : e=and_expr {
        $tree=$e.tree;
        setLocation($tree, $and_expr.start);
            //assert($e.tree != null);
        }
    | e1=or_expr OR e2=and_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
       }
    ;

and_expr returns[AbstractExpr tree]
    : e=eq_neq_expr {
        $tree=$e.tree;
            //assert($e.tree != null);
        }
    |  e1=and_expr AND e2=eq_neq_expr {
            assert($e1.tree != null);                         
            assert($e2.tree != null);
        }
    ;

eq_neq_expr returns[AbstractExpr tree]
    : e=inequality_expr {
         $tree=$e.tree;
          //  assert($e.tree != null);
        }
    | e1=eq_neq_expr EQEQ e2=inequality_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
        }
    | e1=eq_neq_expr NEQ e2=inequality_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
        }
    ;

inequality_expr returns[AbstractExpr tree]
    : e=sum_expr {
        $tree=$e.tree;
         //   assert($e.tree != null);
        }
    | e1=inequality_expr LEQ e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
        }
    | e1=inequality_expr GEQ e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
        }
    | e1=inequality_expr GT e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
        }
    | e1=inequality_expr LT e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
        }
    | e1=inequality_expr INSTANCEOF type {
            assert($e1.tree != null);
            assert($type.tree != null);
        }
    ;


sum_expr returns[AbstractExpr tree]
    : e=mult_expr {
        $tree=$e.tree;
          //  assert($e.tree != null);
        }
    | e1=sum_expr PLUS e2=mult_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
        }
    | e1=sum_expr MINUS e2=mult_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
        }
    ;

mult_expr returns[AbstractExpr tree]
    : e=unary_expr {
        setLocation($tree, $unary_expr.start);
        $tree=$e.tree;
        assert($e.tree != null);
        }
    | e1=mult_expr TIMES e2=unary_expr {
        setLocation($tree, $mult_expr.start);

            assert($e1.tree != null);                                         
            assert($e2.tree != null);
            $tree=new Multiply($e1.tree,$e2.tree);
        }
    | e1=mult_expr SLASH e2=unary_expr {
            assert($e1.tree != null);                                         
            assert($e2.tree != null);
        }
    | e1=mult_expr PERCENT e2=unary_expr {
            assert($e1.tree != null);                                                                          
            assert($e2.tree != null);
        }
    ;

unary_expr returns[AbstractExpr tree]
    : op=MINUS e=unary_expr {
        $tree=$e.tree;
        assert($e.tree != null);
        }
    | op=EXCLAM e=unary_expr {
            assert($e.tree != null);
        }
    | select_expr {
        $tree=$select_expr.tree;
            assert($select_expr.tree != null);
        }
    ;

select_expr returns[AbstractExpr tree]
    : e=primary_expr {
            $tree= $e.tree;
            assert($e.tree != null);
        }
    | e1=select_expr DOT i=ident {
            assert($e1.tree != null);
            assert($i.tree != null);
        }
        (o=OPARENT args=list_expr CPARENT {
            // we matched "e1.i(args)"
            assert($args.tree != null);
        }
        | /* epsilon */ {
            // we matched "e.i"
        }
        )
    ;

primary_expr returns[AbstractExpr tree]
    : ident {
        $tree=$ident.tree;
            assert($ident.tree != null);
        }
    | m=ident OPARENT args=list_expr CPARENT {
            assert($args.tree != null);
            assert($m.tree != null);
        }
    | OPARENT expr CPARENT {
            $tree = $expr.tree;
            assert($expr.tree != null);
        }
    | READINT OPARENT CPARENT {
        $tree = new ReadInt();
        }
    | READFLOAT OPARENT CPARENT {
        }
    | NEW ident OPARENT CPARENT {
            assert($ident.tree != null);
        }
    | cast=OPARENT type CPARENT OPARENT expr CPARENT {
            assert($type.tree != null);
            assert($expr.tree != null);
        }
    | literal {
        $tree=$literal.tree;
           assert($literal.tree != null);
        }
    ;

type returns[AbstractIdentifier tree]
    : ident {

            assert($ident.tree != null);
            $tree=$ident.tree;
        }
    ;

literal returns[AbstractExpr tree]
    : INT {
        try{
       $tree= new IntLiteral(Integer.parseInt($INT.text));
        } catch(DecaRecognitionException e) { $tree=null; }
        }
    | fd=FLOAT {
                try{
       $tree= new FloatLiteral(Float.parseFloat($fd.text));
        } catch(DecaRecognitionException e) { $tree=null; }
        }
    | STRING {
         try{
       $tree= new StringLiteral($STRING.text);
        } catch(DecaRecognitionException e) { $tree=null; }
        }
    | TRUE {
        }
    | FALSE {
        }
    | THIS {
        }
    | NULL {
        }
    ;

ident returns[AbstractIdentifier tree]
    @init{ 
        SymbolTable sym = new SymbolTable();
     }
    : IDENT {
                 try{

        
       $tree= new Identifier(sym.create($IDENT.text));
        } catch(DecaRecognitionException e) { $tree=null; }
        }
    ;

/****     Class related rules     ****/

list_classes returns[ListDeclClass tree]
    :
      (c1=class_decl {
        }
      )*
    ;

class_decl
    : CLASS name=ident superclass=class_extension OBRACE class_body CBRACE {
        }
    ;

class_extension returns[AbstractIdentifier tree]
    : EXTENDS ident {
        }
    | /* epsilon */ {
        }
    ;

class_body
    : (m=decl_method {
        }
      | decl_field_set
      )*
    ;

decl_field_set
    : v=visibility t=type list_decl_field
      SEMI
    ;

visibility
    : /* epsilon */ {
        }
    | PROTECTED {
        }
    ;

list_decl_field
    : dv1=decl_field
        (COMMA dv2=decl_field
      )*
    ;

decl_field
    : i=ident {
        }
      (EQUALS e=expr {
        }
      )? {
        }
    ;

decl_method
@init {
}
    : type ident OPARENT params=list_params CPARENT (block {
        }
      | ASM OPARENT code=multi_line_string CPARENT SEMI {
        }
      ) {
        }
    ;

list_params
    : (p1=param {
        } (COMMA p2=param {
        }
      )*)?
    ;
    
multi_line_string returns[String text, Location location]
    : s=STRING {
            $text = $s.text;
            $location = tokenLocation($s);
        }
    | s=MULTI_LINE_STRING {
            $text = $s.text;
            $location = tokenLocation($s);
        }
    ;

param
    : type ident {
        }
    ;
