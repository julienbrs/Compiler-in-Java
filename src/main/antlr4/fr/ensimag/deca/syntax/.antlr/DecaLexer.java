// Generated from /home/ensimag/2A/gl11/src/main/antlr4/fr/ensimag/deca/syntax/DecaLexer.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DecaLexer extends AbstractDecaLexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OBRACE=1, CBRACE=2, COLON=3, COMMA=4, EQUALS=5, PRINT=6, OPARENT=7, CPARENT=8, 
		PRINTLN=9, PRINTX=10, PRINTLNX=11, WHILE=12, RETURN=13, IF=14, ELSE=15, 
		OR=16, AND=17, EQEQ=18, NEQ=19, LEQ=20, GEQ=21, GT=22, LT=23, INSTANCEOF=24, 
		PLUS=25, MINUS=26, TIMES=27, SLASH=28, PERCENT=29, EXCLAM=30, DOT=31, 
		READINT=32, READFLOAT=33, NEW=34, INT=35, FLOAT=36, STRING=37, TRUE=38, 
		FALSE=39, THIS=40, NULL=41, CLASS=42, EXTENDS=43, PROTECTED=44, ASM=45, 
		MULTI_LINE_STRING=46, IDENT=47, SPACE=48, LF=49, CR=50, TAB=51, BACKSLASH=52, 
		QUOTE=53, SEMI=54, UNDERSCORE=55, WORD=56, NUMBER=57, COMMENT=58;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"OBRACE", "CBRACE", "COLON", "COMMA", "EQUALS", "PRINT", "OPARENT", "CPARENT", 
			"PRINTLN", "PRINTX", "PRINTLNX", "WHILE", "RETURN", "IF", "ELSE", "OR", 
			"AND", "EQEQ", "NEQ", "LEQ", "GEQ", "GT", "LT", "INSTANCEOF", "PLUS", 
			"MINUS", "TIMES", "SLASH", "PERCENT", "EXCLAM", "DOT", "READINT", "READFLOAT", 
			"NEW", "INT", "FLOAT", "STRING", "TRUE", "FALSE", "THIS", "NULL", "CLASS", 
			"EXTENDS", "PROTECTED", "ASM", "MULTI_LINE_STRING", "IDENT", "SPACE", 
			"LF", "CR", "TAB", "BACKSLASH", "QUOTE", "DQUOTE", "SEMI", "UNDERSCORE", 
			"LETTER", "DIGIT", "POSITIVE_DIGIT", "WORD", "NUMBER", "STRING_CAR", 
			"SIGN", "EXP", "DEC", "FLOATDEC", "DIGITHEX", "NUMHEX", "FLOATHEX", "COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "':'", "','", "'='", "'print'", "'('", "')'", "'println'", 
			"'printx'", "'printlnx'", "'while'", "'return'", "'if'", "'else'", "'||'", 
			"'&&'", "'=='", "'!='", "'<='", "'>='", "'>'", "'<'", "'instanceof'", 
			"'+'", "'-'", "'*'", "'/'", "'%'", "'!'", "'.'", "'readInt'", "'readFloat'", 
			"'new'", null, null, null, "'true'", "'false'", "'this'", "'null'", "'class'", 
			"'extends'", "'protected'", "'asm'", null, null, "' '", "'\n'", "'\r'", 
			"'\t'", "'\\'", "'''", "';'", "'_'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "OBRACE", "CBRACE", "COLON", "COMMA", "EQUALS", "PRINT", "OPARENT", 
			"CPARENT", "PRINTLN", "PRINTX", "PRINTLNX", "WHILE", "RETURN", "IF", 
			"ELSE", "OR", "AND", "EQEQ", "NEQ", "LEQ", "GEQ", "GT", "LT", "INSTANCEOF", 
			"PLUS", "MINUS", "TIMES", "SLASH", "PERCENT", "EXCLAM", "DOT", "READINT", 
			"READFLOAT", "NEW", "INT", "FLOAT", "STRING", "TRUE", "FALSE", "THIS", 
			"NULL", "CLASS", "EXTENDS", "PROTECTED", "ASM", "MULTI_LINE_STRING", 
			"IDENT", "SPACE", "LF", "CR", "TAB", "BACKSLASH", "QUOTE", "SEMI", "UNDERSCORE", 
			"WORD", "NUMBER", "COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}




	public DecaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DecaLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 47:
			SPACE_action((RuleContext)_localctx, actionIndex);
			break;
		case 48:
			LF_action((RuleContext)_localctx, actionIndex);
			break;
		case 49:
			CR_action((RuleContext)_localctx, actionIndex);
			break;
		case 50:
			TAB_action((RuleContext)_localctx, actionIndex);
			break;
		case 69:
			COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void SPACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 skip(); 
			break;
		}
	}
	private void LF_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			 skip(); 
			break;
		}
	}
	private void CR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 skip(); 
			break;
		}
	}
	private void TAB_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			 skip(); 
			break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			 skip(); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2<\u01ec\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3"+
		"\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3"+
		"\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3"+
		"\37\3\37\3 \3 \3!\3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3#\3#\3#\3#\3$\3$\3$\7$\u0119\n$\f$\16$\u011c\13$\5$\u011e\n"+
		"$\3%\3%\5%\u0122\n%\3&\3&\3&\3&\3&\3&\7&\u012a\n&\f&\16&\u012d\13&\3&"+
		"\3&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3*\3*\3*\3*\3"+
		"*\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-\3-\3"+
		"-\3-\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\7/\u0169\n/\f/\16/\u016c\13/\3/"+
		"\3/\3\60\3\60\5\60\u0172\n\60\3\60\3\60\3\60\7\60\u0177\n\60\f\60\16\60"+
		"\u017a\13\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3"+
		"\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3=\6="+
		"\u0199\n=\r=\16=\u019a\3>\6>\u019e\n>\r>\16>\u019f\3?\3?\3@\3@\3@\5@\u01a7"+
		"\n@\3A\3A\3A\3A\3B\3B\3B\3B\3C\3C\3C\3C\5C\u01b5\nC\3C\3C\5C\u01b9\nC"+
		"\3D\3D\5D\u01bd\nD\3E\6E\u01c0\nE\rE\16E\u01c1\3F\3F\3F\3F\5F\u01c8\n"+
		"F\3F\3F\3F\3F\3F\3F\3F\3F\5F\u01d2\nF\3G\3G\3G\3G\7G\u01d8\nG\fG\16G\u01db"+
		"\13G\3G\3G\3G\3G\3G\3G\7G\u01e3\nG\fG\16G\u01e6\13G\3G\5G\u01e9\nG\3G"+
		"\3G\4\u01d9\u01e4\2H\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63"+
		"e\64g\65i\66k\67m\2o8q9s\2u\2w\2y:{;}\2\177\2\u0081\2\u0083\2\u0085\2"+
		"\u0087\2\u0089\2\u008b\2\u008d<\3\2\t\4\2&&aa\4\2C\\c|\5\2\f\f$$^^\4\2"+
		"GGgg\4\2HHhh\4\2CHch\4\2RRrr\2\u01fa\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"+
		"\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3"+
		"\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2"+
		"\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2"+
		"\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2["+
		"\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2"+
		"\2\2\2i\3\2\2\2\2k\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2"+
		"\2\u008d\3\2\2\2\3\u008f\3\2\2\2\5\u0091\3\2\2\2\7\u0093\3\2\2\2\t\u0095"+
		"\3\2\2\2\13\u0097\3\2\2\2\r\u0099\3\2\2\2\17\u009f\3\2\2\2\21\u00a1\3"+
		"\2\2\2\23\u00a3\3\2\2\2\25\u00ab\3\2\2\2\27\u00b2\3\2\2\2\31\u00bb\3\2"+
		"\2\2\33\u00c1\3\2\2\2\35\u00c8\3\2\2\2\37\u00cb\3\2\2\2!\u00d0\3\2\2\2"+
		"#\u00d3\3\2\2\2%\u00d6\3\2\2\2\'\u00d9\3\2\2\2)\u00dc\3\2\2\2+\u00df\3"+
		"\2\2\2-\u00e2\3\2\2\2/\u00e4\3\2\2\2\61\u00e6\3\2\2\2\63\u00f1\3\2\2\2"+
		"\65\u00f3\3\2\2\2\67\u00f5\3\2\2\29\u00f7\3\2\2\2;\u00f9\3\2\2\2=\u00fb"+
		"\3\2\2\2?\u00fd\3\2\2\2A\u00ff\3\2\2\2C\u0107\3\2\2\2E\u0111\3\2\2\2G"+
		"\u011d\3\2\2\2I\u0121\3\2\2\2K\u0123\3\2\2\2M\u0130\3\2\2\2O\u0135\3\2"+
		"\2\2Q\u013b\3\2\2\2S\u0140\3\2\2\2U\u0145\3\2\2\2W\u014b\3\2\2\2Y\u0153"+
		"\3\2\2\2[\u015d\3\2\2\2]\u0161\3\2\2\2_\u0171\3\2\2\2a\u017b\3\2\2\2c"+
		"\u017e\3\2\2\2e\u0181\3\2\2\2g\u0184\3\2\2\2i\u0187\3\2\2\2k\u0189\3\2"+
		"\2\2m\u018b\3\2\2\2o\u018d\3\2\2\2q\u018f\3\2\2\2s\u0191\3\2\2\2u\u0193"+
		"\3\2\2\2w\u0195\3\2\2\2y\u0198\3\2\2\2{\u019d\3\2\2\2}\u01a1\3\2\2\2\177"+
		"\u01a6\3\2\2\2\u0081\u01a8\3\2\2\2\u0083\u01ac\3\2\2\2\u0085\u01b4\3\2"+
		"\2\2\u0087\u01bc\3\2\2\2\u0089\u01bf\3\2\2\2\u008b\u01c7\3\2\2\2\u008d"+
		"\u01e8\3\2\2\2\u008f\u0090\7}\2\2\u0090\4\3\2\2\2\u0091\u0092\7\177\2"+
		"\2\u0092\6\3\2\2\2\u0093\u0094\7<\2\2\u0094\b\3\2\2\2\u0095\u0096\7.\2"+
		"\2\u0096\n\3\2\2\2\u0097\u0098\7?\2\2\u0098\f\3\2\2\2\u0099\u009a\7r\2"+
		"\2\u009a\u009b\7t\2\2\u009b\u009c\7k\2\2\u009c\u009d\7p\2\2\u009d\u009e"+
		"\7v\2\2\u009e\16\3\2\2\2\u009f\u00a0\7*\2\2\u00a0\20\3\2\2\2\u00a1\u00a2"+
		"\7+\2\2\u00a2\22\3\2\2\2\u00a3\u00a4\7r\2\2\u00a4\u00a5\7t\2\2\u00a5\u00a6"+
		"\7k\2\2\u00a6\u00a7\7p\2\2\u00a7\u00a8\7v\2\2\u00a8\u00a9\7n\2\2\u00a9"+
		"\u00aa\7p\2\2\u00aa\24\3\2\2\2\u00ab\u00ac\7r\2\2\u00ac\u00ad\7t\2\2\u00ad"+
		"\u00ae\7k\2\2\u00ae\u00af\7p\2\2\u00af\u00b0\7v\2\2\u00b0\u00b1\7z\2\2"+
		"\u00b1\26\3\2\2\2\u00b2\u00b3\7r\2\2\u00b3\u00b4\7t\2\2\u00b4\u00b5\7"+
		"k\2\2\u00b5\u00b6\7p\2\2\u00b6\u00b7\7v\2\2\u00b7\u00b8\7n\2\2\u00b8\u00b9"+
		"\7p\2\2\u00b9\u00ba\7z\2\2\u00ba\30\3\2\2\2\u00bb\u00bc\7y\2\2\u00bc\u00bd"+
		"\7j\2\2\u00bd\u00be\7k\2\2\u00be\u00bf\7n\2\2\u00bf\u00c0\7g\2\2\u00c0"+
		"\32\3\2\2\2\u00c1\u00c2\7t\2\2\u00c2\u00c3\7g\2\2\u00c3\u00c4\7v\2\2\u00c4"+
		"\u00c5\7w\2\2\u00c5\u00c6\7t\2\2\u00c6\u00c7\7p\2\2\u00c7\34\3\2\2\2\u00c8"+
		"\u00c9\7k\2\2\u00c9\u00ca\7h\2\2\u00ca\36\3\2\2\2\u00cb\u00cc\7g\2\2\u00cc"+
		"\u00cd\7n\2\2\u00cd\u00ce\7u\2\2\u00ce\u00cf\7g\2\2\u00cf \3\2\2\2\u00d0"+
		"\u00d1\7~\2\2\u00d1\u00d2\7~\2\2\u00d2\"\3\2\2\2\u00d3\u00d4\7(\2\2\u00d4"+
		"\u00d5\7(\2\2\u00d5$\3\2\2\2\u00d6\u00d7\7?\2\2\u00d7\u00d8\7?\2\2\u00d8"+
		"&\3\2\2\2\u00d9\u00da\7#\2\2\u00da\u00db\7?\2\2\u00db(\3\2\2\2\u00dc\u00dd"+
		"\7>\2\2\u00dd\u00de\7?\2\2\u00de*\3\2\2\2\u00df\u00e0\7@\2\2\u00e0\u00e1"+
		"\7?\2\2\u00e1,\3\2\2\2\u00e2\u00e3\7@\2\2\u00e3.\3\2\2\2\u00e4\u00e5\7"+
		">\2\2\u00e5\60\3\2\2\2\u00e6\u00e7\7k\2\2\u00e7\u00e8\7p\2\2\u00e8\u00e9"+
		"\7u\2\2\u00e9\u00ea\7v\2\2\u00ea\u00eb\7c\2\2\u00eb\u00ec\7p\2\2\u00ec"+
		"\u00ed\7e\2\2\u00ed\u00ee\7g\2\2\u00ee\u00ef\7q\2\2\u00ef\u00f0\7h\2\2"+
		"\u00f0\62\3\2\2\2\u00f1\u00f2\7-\2\2\u00f2\64\3\2\2\2\u00f3\u00f4\7/\2"+
		"\2\u00f4\66\3\2\2\2\u00f5\u00f6\7,\2\2\u00f68\3\2\2\2\u00f7\u00f8\7\61"+
		"\2\2\u00f8:\3\2\2\2\u00f9\u00fa\7\'\2\2\u00fa<\3\2\2\2\u00fb\u00fc\7#"+
		"\2\2\u00fc>\3\2\2\2\u00fd\u00fe\7\60\2\2\u00fe@\3\2\2\2\u00ff\u0100\7"+
		"t\2\2\u0100\u0101\7g\2\2\u0101\u0102\7c\2\2\u0102\u0103\7f\2\2\u0103\u0104"+
		"\7K\2\2\u0104\u0105\7p\2\2\u0105\u0106\7v\2\2\u0106B\3\2\2\2\u0107\u0108"+
		"\7t\2\2\u0108\u0109\7g\2\2\u0109\u010a\7c\2\2\u010a\u010b\7f\2\2\u010b"+
		"\u010c\7H\2\2\u010c\u010d\7n\2\2\u010d\u010e\7q\2\2\u010e\u010f\7c\2\2"+
		"\u010f\u0110\7v\2\2\u0110D\3\2\2\2\u0111\u0112\7p\2\2\u0112\u0113\7g\2"+
		"\2\u0113\u0114\7y\2\2\u0114F\3\2\2\2\u0115\u011e\7\62\2\2\u0116\u011a"+
		"\5w<\2\u0117\u0119\5u;\2\u0118\u0117\3\2\2\2\u0119\u011c\3\2\2\2\u011a"+
		"\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011e\3\2\2\2\u011c\u011a\3\2"+
		"\2\2\u011d\u0115\3\2\2\2\u011d\u0116\3\2\2\2\u011eH\3\2\2\2\u011f\u0122"+
		"\5\u0085C\2\u0120\u0122\5\u008bF\2\u0121\u011f\3\2\2\2\u0121\u0120\3\2"+
		"\2\2\u0122J\3\2\2\2\u0123\u012b\7$\2\2\u0124\u012a\5}?\2\u0125\u0126\7"+
		"^\2\2\u0126\u012a\7$\2\2\u0127\u0128\7^\2\2\u0128\u012a\7^\2\2\u0129\u0124"+
		"\3\2\2\2\u0129\u0125\3\2\2\2\u0129\u0127\3\2\2\2\u012a\u012d\3\2\2\2\u012b"+
		"\u0129\3\2\2\2\u012b\u012c\3\2\2\2\u012c\u012e\3\2\2\2\u012d\u012b\3\2"+
		"\2\2\u012e\u012f\7$\2\2\u012fL\3\2\2\2\u0130\u0131\7v\2\2\u0131\u0132"+
		"\7t\2\2\u0132\u0133\7w\2\2\u0133\u0134\7g\2\2\u0134N\3\2\2\2\u0135\u0136"+
		"\7h\2\2\u0136\u0137\7c\2\2\u0137\u0138\7n\2\2\u0138\u0139\7u\2\2\u0139"+
		"\u013a\7g\2\2\u013aP\3\2\2\2\u013b\u013c\7v\2\2\u013c\u013d\7j\2\2\u013d"+
		"\u013e\7k\2\2\u013e\u013f\7u\2\2\u013fR\3\2\2\2\u0140\u0141\7p\2\2\u0141"+
		"\u0142\7w\2\2\u0142\u0143\7n\2\2\u0143\u0144\7n\2\2\u0144T\3\2\2\2\u0145"+
		"\u0146\7e\2\2\u0146\u0147\7n\2\2\u0147\u0148\7c\2\2\u0148\u0149\7u\2\2"+
		"\u0149\u014a\7u\2\2\u014aV\3\2\2\2\u014b\u014c\7g\2\2\u014c\u014d\7z\2"+
		"\2\u014d\u014e\7v\2\2\u014e\u014f\7g\2\2\u014f\u0150\7p\2\2\u0150\u0151"+
		"\7f\2\2\u0151\u0152\7u\2\2\u0152X\3\2\2\2\u0153\u0154\7r\2\2\u0154\u0155"+
		"\7t\2\2\u0155\u0156\7q\2\2\u0156\u0157\7v\2\2\u0157\u0158\7g\2\2\u0158"+
		"\u0159\7e\2\2\u0159\u015a\7v\2\2\u015a\u015b\7g\2\2\u015b\u015c\7f\2\2"+
		"\u015cZ\3\2\2\2\u015d\u015e\7c\2\2\u015e\u015f\7u\2\2\u015f\u0160\7o\2"+
		"\2\u0160\\\3\2\2\2\u0161\u016a\7$\2\2\u0162\u0169\5}?\2\u0163\u0164\7"+
		"^\2\2\u0164\u0169\7$\2\2\u0165\u0166\7^\2\2\u0166\u0169\7^\2\2\u0167\u0169"+
		"\7\f\2\2\u0168\u0162\3\2\2\2\u0168\u0163\3\2\2\2\u0168\u0165\3\2\2\2\u0168"+
		"\u0167\3\2\2\2\u0169\u016c\3\2\2\2\u016a\u0168\3\2\2\2\u016a\u016b\3\2"+
		"\2\2\u016b\u016d\3\2\2\2\u016c\u016a\3\2\2\2\u016d\u016e\7$\2\2\u016e"+
		"^\3\2\2\2\u016f\u0172\5s:\2\u0170\u0172\t\2\2\2\u0171\u016f\3\2\2\2\u0171"+
		"\u0170\3\2\2\2\u0172\u0178\3\2\2\2\u0173\u0177\5s:\2\u0174\u0177\5u;\2"+
		"\u0175\u0177\t\2\2\2\u0176\u0173\3\2\2\2\u0176\u0174\3\2\2\2\u0176\u0175"+
		"\3\2\2\2\u0177\u017a\3\2\2\2\u0178\u0176\3\2\2\2\u0178\u0179\3\2\2\2\u0179"+
		"`\3\2\2\2\u017a\u0178\3\2\2\2\u017b\u017c\7\"\2\2\u017c\u017d\b\61\2\2"+
		"\u017db\3\2\2\2\u017e\u017f\7\f\2\2\u017f\u0180\b\62\3\2\u0180d\3\2\2"+
		"\2\u0181\u0182\7\17\2\2\u0182\u0183\b\63\4\2\u0183f\3\2\2\2\u0184\u0185"+
		"\7\13\2\2\u0185\u0186\b\64\5\2\u0186h\3\2\2\2\u0187\u0188\7^\2\2\u0188"+
		"j\3\2\2\2\u0189\u018a\7)\2\2\u018al\3\2\2\2\u018b\u018c\7$\2\2\u018cn"+
		"\3\2\2\2\u018d\u018e\7=\2\2\u018ep\3\2\2\2\u018f\u0190\7a\2\2\u0190r\3"+
		"\2\2\2\u0191\u0192\t\3\2\2\u0192t\3\2\2\2\u0193\u0194\4\62;\2\u0194v\3"+
		"\2\2\2\u0195\u0196\4\63;\2\u0196x\3\2\2\2\u0197\u0199\5s:\2\u0198\u0197"+
		"\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u0198\3\2\2\2\u019a\u019b\3\2\2\2\u019b"+
		"z\3\2\2\2\u019c\u019e\5u;\2\u019d\u019c\3\2\2\2\u019e\u019f\3\2\2\2\u019f"+
		"\u019d\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0|\3\2\2\2\u01a1\u01a2\n\4\2\2"+
		"\u01a2~\3\2\2\2\u01a3\u01a7\5\63\32\2\u01a4\u01a7\5\65\33\2\u01a5\u01a7"+
		"\3\2\2\2\u01a6\u01a3\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a6\u01a5\3\2\2\2\u01a7"+
		"\u0080\3\2\2\2\u01a8\u01a9\t\5\2\2\u01a9\u01aa\5\177@\2\u01aa\u01ab\5"+
		"{>\2\u01ab\u0082\3\2\2\2\u01ac\u01ad\5{>\2\u01ad\u01ae\7\60\2\2\u01ae"+
		"\u01af\5{>\2\u01af\u0084\3\2\2\2\u01b0\u01b5\5\u0083B\2\u01b1\u01b2\5"+
		"\u0083B\2\u01b2\u01b3\5\u0081A\2\u01b3\u01b5\3\2\2\2\u01b4\u01b0\3\2\2"+
		"\2\u01b4\u01b1\3\2\2\2\u01b5\u01b8\3\2\2\2\u01b6\u01b9\t\6\2\2\u01b7\u01b9"+
		"\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b8\u01b7\3\2\2\2\u01b9\u0086\3\2\2\2\u01ba"+
		"\u01bd\5u;\2\u01bb\u01bd\t\7\2\2\u01bc\u01ba\3\2\2\2\u01bc\u01bb\3\2\2"+
		"\2\u01bd\u0088\3\2\2\2\u01be\u01c0\5\u0087D\2\u01bf\u01be\3\2\2\2\u01c0"+
		"\u01c1\3\2\2\2\u01c1\u01bf\3\2\2\2\u01c1\u01c2\3\2\2\2\u01c2\u008a\3\2"+
		"\2\2\u01c3\u01c4\7\62\2\2\u01c4\u01c8\7z\2\2\u01c5\u01c6\7\62\2\2\u01c6"+
		"\u01c8\7Z\2\2\u01c7\u01c3\3\2\2\2\u01c7\u01c5\3\2\2\2\u01c8\u01c9\3\2"+
		"\2\2\u01c9\u01ca\5\u0089E\2\u01ca\u01cb\7\60\2\2\u01cb\u01cc\5\u0089E"+
		"\2\u01cc\u01cd\t\b\2\2\u01cd\u01ce\5\177@\2\u01ce\u01d1\5{>\2\u01cf\u01d2"+
		"\t\6\2\2\u01d0\u01d2\3\2\2\2\u01d1\u01cf\3\2\2\2\u01d1\u01d0\3\2\2\2\u01d2"+
		"\u008c\3\2\2\2\u01d3\u01d4\7\61\2\2\u01d4\u01d5\7,\2\2\u01d5\u01d9\3\2"+
		"\2\2\u01d6\u01d8\13\2\2\2\u01d7\u01d6\3\2\2\2\u01d8\u01db\3\2\2\2\u01d9"+
		"\u01da\3\2\2\2\u01d9\u01d7\3\2\2\2\u01da\u01dc\3\2\2\2\u01db\u01d9\3\2"+
		"\2\2\u01dc\u01dd\7,\2\2\u01dd\u01e9\7\61\2\2\u01de\u01df\7\61\2\2\u01df"+
		"\u01e0\7\61\2\2\u01e0\u01e4\3\2\2\2\u01e1\u01e3\13\2\2\2\u01e2\u01e1\3"+
		"\2\2\2\u01e3\u01e6\3\2\2\2\u01e4\u01e5\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e5"+
		"\u01e7\3\2\2\2\u01e6\u01e4\3\2\2\2\u01e7\u01e9\7\f\2\2\u01e8\u01d3\3\2"+
		"\2\2\u01e8\u01de\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01eb\bG\6\2\u01eb"+
		"\u008e\3\2\2\2\31\2\u011a\u011d\u0121\u0129\u012b\u0168\u016a\u0171\u0176"+
		"\u0178\u019a\u019f\u01a6\u01b4\u01b8\u01bc\u01c1\u01c7\u01d1\u01d9\u01e4"+
		"\u01e8\7\3\61\2\3\62\3\3\63\4\3\64\5\3G\6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}