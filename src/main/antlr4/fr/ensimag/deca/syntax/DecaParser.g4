parser grammar DecaParser;

options {
    // Default language but name it anyway
    //
    language  = Java;

    // Use a superclass to implement all helper
    // methods, instance variables and overrides
    // of ANTLR default methods, such as erro
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
            assert($list_classes.tree != null);
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
        try {
            assert($list_decl.tree != null);
            assert($list_inst.tree != null);
            $decls = $list_decl.tree;
            $insts = $list_inst.tree;
        }
        catch(DecaRecognitionException e) { $decls=null; } 
        }
    ;

list_decl returns[ListDeclVar tree]
@init   {
            $tree = new ListDeclVar();
        }
    : decl_var_set[$tree]*
    ;

decl_var_set[ListDeclVar l]
    : type list_decl_var[$l,$type.tree] SEMI{
    }

    ;

list_decl_var[ListDeclVar l, AbstractIdentifier t]
    : (dv1 = decl_var[$t]  {
            $l.add($dv1.tree);
        }
        )
         (COMMA dv2 = decl_var[$t] {
            $l.add($dv2.tree);
        }
        

      )*
    ;
  


decl_var[AbstractIdentifier t] returns[AbstractDeclVar tree]
@init   {
        
        }
    : i=ident {
            $tree = new DeclVar(t, $i.tree,new  NoInitialization() ) ;
            setLocation($tree, $i.start);
        }
      (EQUALS e=expr {
            Initialization a = new Initialization($e.tree);
            $tree = new DeclVar(t, $i.tree, (AbstractInitialization)a);
            setLocation(a, $EQUALS);
            setLocation($tree, $i.start);
        }
      )?
    ;

list_inst returns[ListInst tree]
@init {
    $tree=new ListInst();
}
    : (inst {
        if($inst.tree != null){
        $tree.add($inst.tree);
       
        }
    }
      )*
    ;

inst returns[AbstractInst tree]
    : e1=expr SEMI {
            $tree=$e1.tree;
            assert($e1.tree != null);
        }
    | SEMI {
       $tree=new NoOperation();
       setLocation($tree, $SEMI);
        }
    | PRINT OPARENT list_expr CPARENT SEMI {
                     try{
       $tree=new Print(false, $list_expr.tree);
       setLocation($tree, $PRINT);
        } catch(DecaRecognitionException e) { $tree=null; }
        
            assert($list_expr.tree != null);
        }
    | PRINTLN OPARENT list_expr CPARENT SEMI {
               try{
       $tree=new Println(false, $list_expr.tree);
       setLocation($tree, $PRINTLN);
        } catch(DecaRecognitionException e) { $tree=null; }
        
            assert($list_expr.tree != null);
        }
    | PRINTX OPARENT list_expr CPARENT SEMI {
                     try{
       $tree=new Print(true, $list_expr.tree);
       setLocation($tree, $PRINTX);
        } catch(DecaRecognitionException e) { $tree=null; }
        
            assert($list_expr.tree != null);
        }
    | PRINTLNX OPARENT list_expr CPARENT SEMI {
                     try{
       $tree=new Println(true, $list_expr.tree);
       setLocation($tree, $PRINTLNX);
        } catch(DecaRecognitionException e) { $tree=null; }
        
            assert($list_expr.tree != null);
        }
    | if_then_else {
                     try{
       $tree=$if_then_else.tree;
        } catch(DecaRecognitionException e) { $tree=null; }
        
            assert($if_then_else.tree != null);
        }
    | WHILE OPARENT condition=expr CPARENT OBRACE body=list_inst CBRACE {

            assert($condition.tree != null);
            assert($body.tree != null);
            $tree = new While($condition.tree,$body.tree);
            setLocation($tree, $WHILE);
        }
    | RETURN expr SEMI {

            assert($expr.tree != null);
            $tree = new Return($expr.tree);
            setLocation($tree, $RETURN);
        }
    ;

if_then_else returns[IfThenElse tree]
@init {
    ListInst listelse = new ListInst();
    ListInst previous = listelse;
}
    : if1=IF OPARENT condition=expr CPARENT OBRACE li_if=list_inst CBRACE {
        assert($condition.tree != null);
        assert($li_if.tree != null);
        $tree = new IfThenElse($condition.tree, $li_if.tree, listelse);
        setLocation($tree, $if1);
        }
      (ELSE elsif=IF OPARENT elsif_cond=expr CPARENT OBRACE elsif_li=list_inst CBRACE {
        ListInst current = new ListInst();
        IfThenElse ifThenElse = new IfThenElse($elsif_cond.tree,$elsif_li.tree, current);
        previous.add(ifThenElse);
        previous = current;
        setLocation(ifThenElse, $ELSE);
        }
      )*
      (ELSE OBRACE li_else=list_inst CBRACE {
        for (AbstractInst i : $li_else.tree.getList()) {
            previous.add(i);
        }
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
        assert($assign_expr.tree != null);
        $tree=$assign_expr.tree;
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
            setLocation($tree,$EQUALS);
            assert($e.tree != null);
            assert($e2.tree != null);
        }
      | /* epsilon */ {
        assert($e.tree != null);  
        $tree=$e.tree;
        
        }
      )
    ;

or_expr returns[AbstractExpr tree]
    : e=and_expr {
            assert($e.tree != null);
            $tree=$e.tree;
        }
    | e1=or_expr OR e2=and_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Or($e1.tree,$e2.tree);
            setLocation($tree, $OR);
       }
    ;

and_expr returns[AbstractExpr tree]
    : e=eq_neq_expr {
            $tree=$e.tree;
            assert($e.tree != null);
        }
    |  e1=and_expr AND e2=eq_neq_expr {
            assert($e1.tree != null);                         
            assert($e2.tree != null);
            $tree = new And($e1.tree, $e2.tree);
            setLocation($tree, $AND);
        }
    ;

eq_neq_expr returns[AbstractExpr tree]
    : e=inequality_expr {
        assert($e.tree != null);
        $tree=$e.tree;
        }
    | e1=eq_neq_expr EQEQ e2=inequality_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Equals($e1.tree,$e2.tree);
            setLocation($tree, $EQEQ);
        }
    | e1=eq_neq_expr NEQ e2=inequality_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new NotEquals($e1.tree,$e2.tree);
            setLocation($tree, $NEQ);
        }
    ;

inequality_expr returns[AbstractExpr tree]
    : e=sum_expr {
            assert($e.tree != null);
            $tree=$e.tree;
        }
    | e1=inequality_expr LEQ e2=sum_expr {

            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new LowerOrEqual($e1.tree,$e2.tree);
            setLocation($tree, $LEQ);
        }
    | e1=inequality_expr GEQ e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new GreaterOrEqual($e1.tree,$e2.tree);
            setLocation($tree, $GEQ);
        }
    | e1=inequality_expr GT e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Greater($e1.tree,$e2.tree);
            setLocation($tree, $GT);
        }
    | e1=inequality_expr LT e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Lower($e1.tree,$e2.tree);
            setLocation($tree, $LT);
        }
    | e1=inequality_expr INSTANCEOF type {
            assert($e1.tree != null);
            assert($type.tree != null);
            $tree  = new InstanceOf($e1.tree,$type.tree);
            setLocation($tree, $INSTANCEOF);
        }
    ;


sum_expr returns[AbstractExpr tree]
    : e=mult_expr {
        try{
        assert($e.tree != null);
        $tree=$e.tree;
          } catch(DecaRecognitionException e) { $tree=null; }
        }
    | e1=sum_expr PLUS e2=mult_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Plus($e1.tree, $e2.tree);
            setLocation($tree, $PLUS);
        }
    | e1=sum_expr MINUS e2=mult_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Minus($e1.tree,$e2.tree);
            setLocation($tree, $MINUS);
        }
    ;

mult_expr returns[AbstractExpr tree]
    : e=unary_expr {
        try{
        assert($e.tree != null);
        $tree=$e.tree;
          } catch(DecaRecognitionException e) { $tree=null; }
        }
    | e1=mult_expr TIMES e2=unary_expr {
            try{
            assert($e1.tree != null);                                         
            assert($e2.tree != null);
            $tree=new Multiply($e1.tree,$e2.tree);
            setLocation($tree, $TIMES);
            } catch(DecaRecognitionException e) { $tree=null; }
        }
    | e1=mult_expr SLASH e2=unary_expr {
            assert($e1.tree != null);                                         
            assert($e2.tree != null);
            $tree = new Divide($e1.tree , $e2.tree);
            setLocation($tree, $SLASH);
        }
    | e1=mult_expr PERCENT e2=unary_expr {
            assert($e1.tree != null);                                                                          
            assert($e2.tree != null);
            $tree = new Modulo($e1.tree, $e2.tree);
            setLocation($tree, $PERCENT);
        }
    ;

unary_expr returns[AbstractExpr tree]
    : op=MINUS e=unary_expr {
         try{
            assert($e.tree != null);
            $tree= new UnaryMinus($e.tree);
            setLocation($tree, $op);
        } catch(DecaRecognitionException e) { $tree=null; }
        }
    | op=EXCLAM e=unary_expr {
        try{
            assert($e.tree != null);
            $tree = new Not($e.tree);
            setLocation($tree, $op);
             } catch(DecaRecognitionException e) { $tree=null; }
        }
    | select_expr {
         try{
            assert($select_expr.tree != null);
            $tree=$select_expr.tree;
         } catch(DecaRecognitionException e) { $tree=null; }
        }
    ;

select_expr returns[AbstractExpr tree]
    : e=primary_expr {
            
         try {  
            $tree= $e.tree;
             assert($e.tree != null);
            } catch(DecaRecognitionException e) { $tree=null; }
        }
    | e1=select_expr DOT i=ident {
            assert($e1.tree != null);
            assert($i.tree != null);

        }
        (o=OPARENT args=list_expr CPARENT {

            // we matched "e1.i(args)"
            assert($args.tree != null);
            $tree = new MethodCall($e1.tree, $i.tree, $args.tree);
            setLocation($tree, $DOT);
        }
        | /* epsilon */ {
            $tree = new Selection($e1.tree, $i.tree);
            setLocation($tree, $DOT);
            // we matched "e.i"
        }
        )
    ;

primary_expr returns[AbstractExpr tree]
    : ident {
            assert($ident.tree != null);
            $tree=$ident.tree;
        }
     | tabident{ 
        assert($tabident.tree != null);
        $tree=$tabident.tree;
    }
    | m=ident OPARENT args=list_expr CPARENT {
            assert($args.tree != null);
            assert($m.tree != null);
            $tree = new MethodCall(new This(true), $m.tree, $args.tree);
            setLocation($tree, $OPARENT);
        }
    | OPARENT expr CPARENT {
            $tree = $expr.tree;
            setLocation($tree, $OPARENT);
            assert($expr.tree != null);
        }
    | READINT OPARENT CPARENT {
        $tree = new ReadInt();
        setLocation($tree, $READINT);
        }
    | READFLOAT OPARENT CPARENT {
        $tree = new ReadFloat();
        setLocation($tree, $READFLOAT);
        }
    | NEW ident OPARENT CPARENT {
            assert($ident.tree != null);
            $tree=new New($ident.tree);
            setLocation($tree, $NEW);
        }
    | cast=OPARENT type CPARENT OPARENT expr CPARENT {
            assert($type.tree != null);
            assert($expr.tree != null);
            $tree = new Cast($type.tree, $expr.tree);
            setLocation($tree, $cast);
            
        }
    | literal {
           
           $tree=$literal.tree;
           assert($literal.tree != null);
        }
    ;

type returns[AbstractIdentifier tree]
 @init{ 
        SymbolTable sym = new SymbolTable();
         Array a ;
     }
    : ident {
         try{
            assert($ident.tree != null);
            $tree=$ident.tree;
             } catch(DecaRecognitionException e) { $tree=null; }
        }
         
   |  IDENT OBRACKET
        (expr {  
           
       a =new Array(sym.create(($IDENT.text+"[]")),new IntLiteral(1));
        $tree = a;
        setLocation($tree, $IDENT);
        })?{  


         a = new Array(sym.create(($IDENT.text +"[]")),$expr.tree);
 $tree = a;
 setLocation($tree, $IDENT);
        }
        CBRACKET
       (OBRACKET
       (expr {
         assert($expr.tree != null);
             a.setName(sym.create(a.getName().toString()+"[]"));
       a.addProfondeur(new IntLiteral(1));
       })? {   
 assert($expr.tree != null);
        a.setName(sym.create(a.getName().toString()+"[]"));
        a.addProfondeur( $expr.tree);
       }CBRACKET)*  
       
    ;

literal returns[AbstractExpr tree]
    : INT {
        try{
       $tree= new IntLiteral(Integer.parseInt($INT.text));
       setLocation($tree, $INT);
        } catch(NumberFormatException e) {throw new InvalidIntFormat(this, $ctx); }
        }
    | fd=FLOAT {
                try{
       $tree= new FloatLiteral(Float.parseFloat($fd.text));
       setLocation($tree, $fd);
        } catch(IllegalArgumentException e) { throw new InvalidFloatFormat(this, $ctx); }
        }
    | STRING {
         try{

       $tree= new StringLiteral($STRING.text.substring(1, $STRING.text.length()-1));
       setLocation($tree, $STRING);
        } catch(DecaRecognitionException e) { $tree=null; }
        }
    | TRUE {
        try{
       $tree= new BooleanLiteral(true);
       setLocation($tree, $TRUE);
        } catch(DecaRecognitionException e) { $tree=null; }

        }
    | FALSE {
         try{
       $tree= new BooleanLiteral(false);
       setLocation($tree, $FALSE);
        } catch(DecaRecognitionException e) { $tree=null; }
        }
    | THIS {
        $tree = new This(false);
        setLocation($tree, $THIS);

        }
    | NULL {
        $tree = new NullLiteral();
        setLocation($tree, $NULL);
        }
    ;

ident returns[AbstractIdentifier tree]
 @init{ 
        SymbolTable sym = new SymbolTable();
         Array a ;
     }
    :
    IDENT {
        try{
            $tree= new Identifier(sym.create($IDENT.text));
            setLocation($tree, $IDENT);
        } catch(DecaRecognitionException e) {
            $tree=null;
        }
    }
    ;
tabident  returns[AbstractIdentifier tree]
    @init{ 
        SymbolTable sym = new SymbolTable();
        ListExpr listp = new ListExpr();
     }
:
    IDENT OBRACKET r =expr CBRACKET {  
        listp.add($r.tree);
        $tree = new TabIdentifier(sym.create($IDENT.text),listp);
    setLocation($tree,$IDENT);

        }(OBRACKET expr CBRACKET{
        listp.add($expr.tree);
        }
    )*
;
/****     Class related rules     ****/

list_classes returns[ListDeclClass tree]
@init {
   $tree = new ListDeclClass();
}
    :
      (c1=class_decl {
        $tree.add($c1.tree);
        }
      )*
    ;

class_decl returns [DeclClass tree]
    : CLASS name=ident superclass=class_extension OBRACE class_body CBRACE {
           $tree = new DeclClass($name.tree,$superclass.tree,$class_body.tree);
           setLocation($tree, $CLASS);
        }
    ;

class_extension returns[AbstractIdentifier tree]
    @init{ 
        SymbolTable sym = new SymbolTable();
     }
    : EXTENDS ident {
        $tree = $ident.tree;
        setLocation($tree, $EXTENDS);
        }
    | /* epsilon */ {
        
        $tree= new Identifier(sym.create("Object"));
        $tree.setLocation(Location.BUILTIN);
        }
    ;

class_body returns[ClassBody tree]
@init{
 $tree = new ClassBody();
}
    : (m=decl_method {
        $tree.addDeclMethod($m.tree);
        }
      | decl_field_set{ 
        assert($decl_field_set.tree!=null);
        $tree.addDeclField($decl_field_set.tree);
       }
      )*
    ;

decl_field_set returns [ListDeclField tree]
@init { 
    $tree = new ListDeclField();
 }
    : v=visibility t=type list_decl_field[$tree,$t.tree,$v.tree] 
      SEMI
    ;

visibility returns[Visibility tree]
    : /* epsilon */ {
        $tree = Visibility.PUBLIC;
        }
    | PROTECTED {
        $tree = Visibility.PROTECTED;

        }
    ;

list_decl_field[ListDeclField l, AbstractIdentifier t, Visibility v]
    : dv1=decl_field[$t,$v]{
        $l.add($dv1.tree);}
        (COMMA dv2=decl_field[$t,$v]{
        $l.add($dv2.tree);
        }
      )*
    ;

decl_field[AbstractIdentifier t, Visibility v] returns[DeclField tree]
        : i=ident {
            $tree = new DeclField(t, $i.tree,new  NoInitialization() ,v) ;
            setLocation($tree, $i.start);
        }
      (EQUALS e=expr {
            Initialization a = new Initialization($e.tree);
            $tree = new DeclField(t, $i.tree, (AbstractInitialization)a, v);
            setLocation(a, $EQUALS);
            setLocation($tree, $i.start);
        }
      )? {
        }
    ;

decl_method returns [ DeclMethod tree]
@init {
}
    : type ident OPARENT params=list_params CPARENT (block {
       MethodBody body =  new MethodBody($block.decls,$block.insts);
       setLocation(body, $block.start);
        $tree = new DeclMethod($type.tree,$ident.tree,$params.tree,body);
        setLocation($tree, $type.start);
        }
      | ASM OPARENT code=multi_line_string CPARENT SEMI {
        
        $tree = new DeclMethod($type.tree,$ident.tree,$params.tree,new MethodAsmBody(new StringLiteral($code.text)));
         setLocation($tree, $ASM);
        }
      ) {
        }
    ;

list_params returns[ListParam tree]
@init{  
    $tree = new ListParam();
 }
    : (p1=param {
        $tree.add($p1.tree);
        } (COMMA p2=param {
            $tree.add($p2.tree);
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

param returns[Param tree]
    : type ident {
        $tree = new Param($type.tree,$ident.tree);
        setLocation($tree, $type.start);
        }
    ;
