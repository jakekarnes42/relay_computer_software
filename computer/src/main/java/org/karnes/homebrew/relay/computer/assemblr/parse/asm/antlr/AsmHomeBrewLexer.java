// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/asm/antlr/AsmHomeBrew.g4 by ANTLR 4.7
package org.karnes.homebrew.relay.computer.assemblr.parse.asm.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AsmHomeBrewLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, MOV=3, CLR=4, ADD=5, INC=6, DEC=7, AND=8, OR=9, XOR=10, 
		NOT=11, CMP=12, SUB=13, LOAD=14, FETCH=15, STORE=16, PUSH=17, POP=18, 
		RET=19, CALL=20, WRDIN=21, WRDOUT=22, JMP=23, JZ=24, JNZ=25, JS=26, JNS=27, 
		JC=28, JNC=29, JO=30, JNO=31, NOP=32, HALT=33, TIN=34, TOUT=35, ORG=36, 
		DW=37, DS=38, AX=39, BX=40, CX=41, DX=42, EX=43, SP=44, RP=45, PC=46, 
		NAME=47, MACRO_NAME=48, NUMBER=49, DECIMAL=50, HEX=51, COMMENT=52, STRING=53, 
		PAREN_STRING=54, JAVASCRIPT=55, EOL=56, WS=57;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", 
		"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", 
		"Z", "MOV", "CLR", "ADD", "INC", "DEC", "AND", "OR", "XOR", "NOT", "CMP", 
		"SUB", "LOAD", "FETCH", "STORE", "PUSH", "POP", "RET", "CALL", "WRDIN", 
		"WRDOUT", "JMP", "JZ", "JNZ", "JS", "JNS", "JC", "JNC", "JO", "JNO", "NOP", 
		"HALT", "TIN", "TOUT", "ORG", "DW", "DS", "AX", "BX", "CX", "DX", "EX", 
		"SP", "RP", "PC", "NAME", "MACRO_NAME", "NUMBER", "DECIMAL", "HEX", "COMMENT", 
		"STRING", "PAREN_STRING", "JAVASCRIPT", "EOL", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "MOV", "CLR", "ADD", "INC", "DEC", "AND", "OR", "XOR", 
		"NOT", "CMP", "SUB", "LOAD", "FETCH", "STORE", "PUSH", "POP", "RET", "CALL", 
		"WRDIN", "WRDOUT", "JMP", "JZ", "JNZ", "JS", "JNS", "JC", "JNC", "JO", 
		"JNO", "NOP", "HALT", "TIN", "TOUT", "ORG", "DW", "DS", "AX", "BX", "CX", 
		"DX", "EX", "SP", "RP", "PC", "NAME", "MACRO_NAME", "NUMBER", "DECIMAL", 
		"HEX", "COMMENT", "STRING", "PAREN_STRING", "JAVASCRIPT", "EOL", "WS"
	};
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


	public AsmHomeBrewLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "AsmHomeBrew.g4"; }

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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2;\u01e0\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3"+
		"\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3"+
		"\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\36\3"+
		"\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3\"\3\"\3#\3#\3#\3"+
		"#\3$\3$\3$\3%\3%\3%\3%\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3)\3)\3"+
		")\3)\3)\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3-\3-\3-\3"+
		"-\3.\3.\3.\3.\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3"+
		"\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3"+
		"\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\67\3\67\3\67\38\38\38\3"+
		"8\39\39\39\3:\3:\3:\3:\3;\3;\3;\3;\3<\3<\3<\3<\3<\3=\3=\3=\3=\3>\3>\3"+
		">\3>\3>\3?\3?\3?\3?\3@\3@\3@\3A\3A\3A\3B\3B\3B\3C\3C\3C\3D\3D\3D\3E\3"+
		"E\3E\3F\3F\3F\3G\3G\3G\3H\3H\3H\3I\3I\3I\3J\3J\7J\u0193\nJ\fJ\16J\u0196"+
		"\13J\3K\3K\6K\u019a\nK\rK\16K\u019b\3L\3L\5L\u01a0\nL\3M\5M\u01a3\nM\3"+
		"M\6M\u01a6\nM\rM\16M\u01a7\3N\5N\u01ab\nN\3N\3N\3N\6N\u01b0\nN\rN\16N"+
		"\u01b1\3O\3O\7O\u01b6\nO\fO\16O\u01b9\13O\3O\3O\3P\3P\7P\u01bf\nP\fP\16"+
		"P\u01c2\13P\3P\3P\3Q\3Q\7Q\u01c8\nQ\fQ\16Q\u01cb\13Q\3Q\3Q\3R\3R\7R\u01d1"+
		"\nR\fR\16R\u01d4\13R\3R\3R\3S\5S\u01d9\nS\3S\3S\3T\3T\3T\3T\3\u01d2\2"+
		"U\3\3\5\4\7\2\t\2\13\2\r\2\17\2\21\2\23\2\25\2\27\2\31\2\33\2\35\2\37"+
		"\2!\2#\2%\2\'\2)\2+\2-\2/\2\61\2\63\2\65\2\67\29\2;\5=\6?\7A\bC\tE\nG"+
		"\13I\fK\rM\16O\17Q\20S\21U\22W\23Y\24[\25]\26_\27a\30c\31e\32g\33i\34"+
		"k\35m\36o\37q s!u\"w#y${%}&\177\'\u0081(\u0083)\u0085*\u0087+\u0089,\u008b"+
		"-\u008d.\u008f/\u0091\60\u0093\61\u0095\62\u0097\63\u0099\64\u009b\65"+
		"\u009d\66\u009f\67\u00a18\u00a39\u00a5:\u00a7;\3\2#\4\2CCcc\4\2DDdd\4"+
		"\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMm"+
		"m\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2"+
		"VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\4\2C\\c|\6\2\62"+
		";C\\aac|\3\2\62;\3\2\62\62\5\2\62;CHch\4\2\f\f\17\17\4\2\13\13\"\"\2\u01d1"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3"+
		"\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2"+
		"\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2"+
		"i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3"+
		"\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081"+
		"\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2"+
		"\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093"+
		"\3\2\2\2\2\u0095\3\2\2\2\2\u0097\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2"+
		"\2\2\u009d\3\2\2\2\2\u009f\3\2\2\2\2\u00a1\3\2\2\2\2\u00a3\3\2\2\2\2\u00a5"+
		"\3\2\2\2\2\u00a7\3\2\2\2\3\u00a9\3\2\2\2\5\u00ab\3\2\2\2\7\u00ad\3\2\2"+
		"\2\t\u00af\3\2\2\2\13\u00b1\3\2\2\2\r\u00b3\3\2\2\2\17\u00b5\3\2\2\2\21"+
		"\u00b7\3\2\2\2\23\u00b9\3\2\2\2\25\u00bb\3\2\2\2\27\u00bd\3\2\2\2\31\u00bf"+
		"\3\2\2\2\33\u00c1\3\2\2\2\35\u00c3\3\2\2\2\37\u00c5\3\2\2\2!\u00c7\3\2"+
		"\2\2#\u00c9\3\2\2\2%\u00cb\3\2\2\2\'\u00cd\3\2\2\2)\u00cf\3\2\2\2+\u00d1"+
		"\3\2\2\2-\u00d3\3\2\2\2/\u00d5\3\2\2\2\61\u00d7\3\2\2\2\63\u00d9\3\2\2"+
		"\2\65\u00db\3\2\2\2\67\u00dd\3\2\2\29\u00df\3\2\2\2;\u00e1\3\2\2\2=\u00e5"+
		"\3\2\2\2?\u00e9\3\2\2\2A\u00ed\3\2\2\2C\u00f1\3\2\2\2E\u00f5\3\2\2\2G"+
		"\u00f9\3\2\2\2I\u00fc\3\2\2\2K\u0100\3\2\2\2M\u0104\3\2\2\2O\u0108\3\2"+
		"\2\2Q\u010c\3\2\2\2S\u0111\3\2\2\2U\u0117\3\2\2\2W\u011d\3\2\2\2Y\u0122"+
		"\3\2\2\2[\u0126\3\2\2\2]\u012a\3\2\2\2_\u012f\3\2\2\2a\u0135\3\2\2\2c"+
		"\u013c\3\2\2\2e\u0140\3\2\2\2g\u0143\3\2\2\2i\u0147\3\2\2\2k\u014a\3\2"+
		"\2\2m\u014e\3\2\2\2o\u0151\3\2\2\2q\u0155\3\2\2\2s\u0158\3\2\2\2u\u015c"+
		"\3\2\2\2w\u0160\3\2\2\2y\u0165\3\2\2\2{\u0169\3\2\2\2}\u016e\3\2\2\2\177"+
		"\u0172\3\2\2\2\u0081\u0175\3\2\2\2\u0083\u0178\3\2\2\2\u0085\u017b\3\2"+
		"\2\2\u0087\u017e\3\2\2\2\u0089\u0181\3\2\2\2\u008b\u0184\3\2\2\2\u008d"+
		"\u0187\3\2\2\2\u008f\u018a\3\2\2\2\u0091\u018d\3\2\2\2\u0093\u0190\3\2"+
		"\2\2\u0095\u0197\3\2\2\2\u0097\u019f\3\2\2\2\u0099\u01a2\3\2\2\2\u009b"+
		"\u01aa\3\2\2\2\u009d\u01b3\3\2\2\2\u009f\u01bc\3\2\2\2\u00a1\u01c5\3\2"+
		"\2\2\u00a3\u01ce\3\2\2\2\u00a5\u01d8\3\2\2\2\u00a7\u01dc\3\2\2\2\u00a9"+
		"\u00aa\7<\2\2\u00aa\4\3\2\2\2\u00ab\u00ac\7.\2\2\u00ac\6\3\2\2\2\u00ad"+
		"\u00ae\t\2\2\2\u00ae\b\3\2\2\2\u00af\u00b0\t\3\2\2\u00b0\n\3\2\2\2\u00b1"+
		"\u00b2\t\4\2\2\u00b2\f\3\2\2\2\u00b3\u00b4\t\5\2\2\u00b4\16\3\2\2\2\u00b5"+
		"\u00b6\t\6\2\2\u00b6\20\3\2\2\2\u00b7\u00b8\t\7\2\2\u00b8\22\3\2\2\2\u00b9"+
		"\u00ba\t\b\2\2\u00ba\24\3\2\2\2\u00bb\u00bc\t\t\2\2\u00bc\26\3\2\2\2\u00bd"+
		"\u00be\t\n\2\2\u00be\30\3\2\2\2\u00bf\u00c0\t\13\2\2\u00c0\32\3\2\2\2"+
		"\u00c1\u00c2\t\f\2\2\u00c2\34\3\2\2\2\u00c3\u00c4\t\r\2\2\u00c4\36\3\2"+
		"\2\2\u00c5\u00c6\t\16\2\2\u00c6 \3\2\2\2\u00c7\u00c8\t\17\2\2\u00c8\""+
		"\3\2\2\2\u00c9\u00ca\t\20\2\2\u00ca$\3\2\2\2\u00cb\u00cc\t\21\2\2\u00cc"+
		"&\3\2\2\2\u00cd\u00ce\t\22\2\2\u00ce(\3\2\2\2\u00cf\u00d0\t\23\2\2\u00d0"+
		"*\3\2\2\2\u00d1\u00d2\t\24\2\2\u00d2,\3\2\2\2\u00d3\u00d4\t\25\2\2\u00d4"+
		".\3\2\2\2\u00d5\u00d6\t\26\2\2\u00d6\60\3\2\2\2\u00d7\u00d8\t\27\2\2\u00d8"+
		"\62\3\2\2\2\u00d9\u00da\t\30\2\2\u00da\64\3\2\2\2\u00db\u00dc\t\31\2\2"+
		"\u00dc\66\3\2\2\2\u00dd\u00de\t\32\2\2\u00de8\3\2\2\2\u00df\u00e0\t\33"+
		"\2\2\u00e0:\3\2\2\2\u00e1\u00e2\5\37\20\2\u00e2\u00e3\5#\22\2\u00e3\u00e4"+
		"\5\61\31\2\u00e4<\3\2\2\2\u00e5\u00e6\5\13\6\2\u00e6\u00e7\5\35\17\2\u00e7"+
		"\u00e8\5)\25\2\u00e8>\3\2\2\2\u00e9\u00ea\5\7\4\2\u00ea\u00eb\5\r\7\2"+
		"\u00eb\u00ec\5\r\7\2\u00ec@\3\2\2\2\u00ed\u00ee\5\27\f\2\u00ee\u00ef\5"+
		"!\21\2\u00ef\u00f0\5\13\6\2\u00f0B\3\2\2\2\u00f1\u00f2\5\r\7\2\u00f2\u00f3"+
		"\5\17\b\2\u00f3\u00f4\5\13\6\2\u00f4D\3\2\2\2\u00f5\u00f6\5\7\4\2\u00f6"+
		"\u00f7\5!\21\2\u00f7\u00f8\5\r\7\2\u00f8F\3\2\2\2\u00f9\u00fa\5#\22\2"+
		"\u00fa\u00fb\5)\25\2\u00fbH\3\2\2\2\u00fc\u00fd\5\65\33\2\u00fd\u00fe"+
		"\5#\22\2\u00fe\u00ff\5)\25\2\u00ffJ\3\2\2\2\u0100\u0101\5!\21\2\u0101"+
		"\u0102\5#\22\2\u0102\u0103\5-\27\2\u0103L\3\2\2\2\u0104\u0105\5\13\6\2"+
		"\u0105\u0106\5\37\20\2\u0106\u0107\5%\23\2\u0107N\3\2\2\2\u0108\u0109"+
		"\5+\26\2\u0109\u010a\5/\30\2\u010a\u010b\5\t\5\2\u010bP\3\2\2\2\u010c"+
		"\u010d\5\35\17\2\u010d\u010e\5#\22\2\u010e\u010f\5\7\4\2\u010f\u0110\5"+
		"\r\7\2\u0110R\3\2\2\2\u0111\u0112\5\21\t\2\u0112\u0113\5\17\b\2\u0113"+
		"\u0114\5-\27\2\u0114\u0115\5\13\6\2\u0115\u0116\5\25\13\2\u0116T\3\2\2"+
		"\2\u0117\u0118\5+\26\2\u0118\u0119\5-\27\2\u0119\u011a\5#\22\2\u011a\u011b"+
		"\5)\25\2\u011b\u011c\5\17\b\2\u011cV\3\2\2\2\u011d\u011e\5%\23\2\u011e"+
		"\u011f\5/\30\2\u011f\u0120\5+\26\2\u0120\u0121\5\25\13\2\u0121X\3\2\2"+
		"\2\u0122\u0123\5%\23\2\u0123\u0124\5#\22\2\u0124\u0125\5%\23\2\u0125Z"+
		"\3\2\2\2\u0126\u0127\5)\25\2\u0127\u0128\5\17\b\2\u0128\u0129\5-\27\2"+
		"\u0129\\\3\2\2\2\u012a\u012b\5\13\6\2\u012b\u012c\5\7\4\2\u012c\u012d"+
		"\5\35\17\2\u012d\u012e\5\35\17\2\u012e^\3\2\2\2\u012f\u0130\5\63\32\2"+
		"\u0130\u0131\5)\25\2\u0131\u0132\5\r\7\2\u0132\u0133\5\27\f\2\u0133\u0134"+
		"\5!\21\2\u0134`\3\2\2\2\u0135\u0136\5\63\32\2\u0136\u0137\5)\25\2\u0137"+
		"\u0138\5\r\7\2\u0138\u0139\5#\22\2\u0139\u013a\5/\30\2\u013a\u013b\5-"+
		"\27\2\u013bb\3\2\2\2\u013c\u013d\5\31\r\2\u013d\u013e\5\37\20\2\u013e"+
		"\u013f\5%\23\2\u013fd\3\2\2\2\u0140\u0141\5\31\r\2\u0141\u0142\59\35\2"+
		"\u0142f\3\2\2\2\u0143\u0144\5\31\r\2\u0144\u0145\5!\21\2\u0145\u0146\5"+
		"9\35\2\u0146h\3\2\2\2\u0147\u0148\5\31\r\2\u0148\u0149\5+\26\2\u0149j"+
		"\3\2\2\2\u014a\u014b\5\31\r\2\u014b\u014c\5!\21\2\u014c\u014d\5+\26\2"+
		"\u014dl\3\2\2\2\u014e\u014f\5\31\r\2\u014f\u0150\5\13\6\2\u0150n\3\2\2"+
		"\2\u0151\u0152\5\31\r\2\u0152\u0153\5!\21\2\u0153\u0154\5\13\6\2\u0154"+
		"p\3\2\2\2\u0155\u0156\5\31\r\2\u0156\u0157\5#\22\2\u0157r\3\2\2\2\u0158"+
		"\u0159\5\31\r\2\u0159\u015a\5!\21\2\u015a\u015b\5#\22\2\u015bt\3\2\2\2"+
		"\u015c\u015d\5!\21\2\u015d\u015e\5#\22\2\u015e\u015f\5%\23\2\u015fv\3"+
		"\2\2\2\u0160\u0161\5\25\13\2\u0161\u0162\5\7\4\2\u0162\u0163\5\35\17\2"+
		"\u0163\u0164\5-\27\2\u0164x\3\2\2\2\u0165\u0166\5-\27\2\u0166\u0167\5"+
		"\27\f\2\u0167\u0168\5!\21\2\u0168z\3\2\2\2\u0169\u016a\5-\27\2\u016a\u016b"+
		"\5#\22\2\u016b\u016c\5/\30\2\u016c\u016d\5-\27\2\u016d|\3\2\2\2\u016e"+
		"\u016f\5#\22\2\u016f\u0170\5)\25\2\u0170\u0171\5\23\n\2\u0171~\3\2\2\2"+
		"\u0172\u0173\5\r\7\2\u0173\u0174\5\63\32\2\u0174\u0080\3\2\2\2\u0175\u0176"+
		"\5\r\7\2\u0176\u0177\5+\26\2\u0177\u0082\3\2\2\2\u0178\u0179\5\7\4\2\u0179"+
		"\u017a\5\65\33\2\u017a\u0084\3\2\2\2\u017b\u017c\5\t\5\2\u017c\u017d\5"+
		"\65\33\2\u017d\u0086\3\2\2\2\u017e\u017f\5\13\6\2\u017f\u0180\5\65\33"+
		"\2\u0180\u0088\3\2\2\2\u0181\u0182\5\r\7\2\u0182\u0183\5\65\33\2\u0183"+
		"\u008a\3\2\2\2\u0184\u0185\5\17\b\2\u0185\u0186\5\65\33\2\u0186\u008c"+
		"\3\2\2\2\u0187\u0188\5+\26\2\u0188\u0189\5%\23\2\u0189\u008e\3\2\2\2\u018a"+
		"\u018b\5)\25\2\u018b\u018c\5%\23\2\u018c\u0090\3\2\2\2\u018d\u018e\5%"+
		"\23\2\u018e\u018f\5\13\6\2\u018f\u0092\3\2\2\2\u0190\u0194\t\34\2\2\u0191"+
		"\u0193\t\35\2\2\u0192\u0191\3\2\2\2\u0193\u0196\3\2\2\2\u0194\u0192\3"+
		"\2\2\2\u0194\u0195\3\2\2\2\u0195\u0094\3\2\2\2\u0196\u0194\3\2\2\2\u0197"+
		"\u0199\7&\2\2\u0198\u019a\t\35\2\2\u0199\u0198\3\2\2\2\u019a\u019b\3\2"+
		"\2\2\u019b\u0199\3\2\2\2\u019b\u019c\3\2\2\2\u019c\u0096\3\2\2\2\u019d"+
		"\u01a0\5\u0099M\2\u019e\u01a0\5\u009bN\2\u019f\u019d\3\2\2\2\u019f\u019e"+
		"\3\2\2\2\u01a0\u0098\3\2\2\2\u01a1\u01a3\7/\2\2\u01a2\u01a1\3\2\2\2\u01a2"+
		"\u01a3\3\2\2\2\u01a3\u01a5\3\2\2\2\u01a4\u01a6\t\36\2\2\u01a5\u01a4\3"+
		"\2\2\2\u01a6\u01a7\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a7\u01a8\3\2\2\2\u01a8"+
		"\u009a\3\2\2\2\u01a9\u01ab\7/\2\2\u01aa\u01a9\3\2\2\2\u01aa\u01ab\3\2"+
		"\2\2\u01ab\u01ac\3\2\2\2\u01ac\u01ad\t\37\2\2\u01ad\u01af\5\65\33\2\u01ae"+
		"\u01b0\t \2\2\u01af\u01ae\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1\u01af\3\2"+
		"\2\2\u01b1\u01b2\3\2\2\2\u01b2\u009c\3\2\2\2\u01b3\u01b7\7=\2\2\u01b4"+
		"\u01b6\n!\2\2\u01b5\u01b4\3\2\2\2\u01b6\u01b9\3\2\2\2\u01b7\u01b5\3\2"+
		"\2\2\u01b7\u01b8\3\2\2\2\u01b8\u01ba\3\2\2\2\u01b9\u01b7\3\2\2\2\u01ba"+
		"\u01bb\bO\2\2\u01bb\u009e\3\2\2\2\u01bc\u01c0\7$\2\2\u01bd\u01bf\n!\2"+
		"\2\u01be\u01bd\3\2\2\2\u01bf\u01c2\3\2\2\2\u01c0\u01be\3\2\2\2\u01c0\u01c1"+
		"\3\2\2\2\u01c1\u01c3\3\2\2\2\u01c2\u01c0\3\2\2\2\u01c3\u01c4\7$\2\2\u01c4"+
		"\u00a0\3\2\2\2\u01c5\u01c9\7*\2\2\u01c6\u01c8\n!\2\2\u01c7\u01c6\3\2\2"+
		"\2\u01c8\u01cb\3\2\2\2\u01c9\u01c7\3\2\2\2\u01c9\u01ca\3\2\2\2\u01ca\u01cc"+
		"\3\2\2\2\u01cb\u01c9\3\2\2\2\u01cc\u01cd\7+\2\2\u01cd\u00a2\3\2\2\2\u01ce"+
		"\u01d2\7}\2\2\u01cf\u01d1\13\2\2\2\u01d0\u01cf\3\2\2\2\u01d1\u01d4\3\2"+
		"\2\2\u01d2\u01d3\3\2\2\2\u01d2\u01d0\3\2\2\2\u01d3\u01d5\3\2\2\2\u01d4"+
		"\u01d2\3\2\2\2\u01d5\u01d6\7\177\2\2\u01d6\u00a4\3\2\2\2\u01d7\u01d9\7"+
		"\17\2\2\u01d8\u01d7\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9\u01da\3\2\2\2\u01da"+
		"\u01db\7\f\2\2\u01db\u00a6\3\2\2\2\u01dc\u01dd\t\"\2\2\u01dd\u01de\3\2"+
		"\2\2\u01de\u01df\bT\2\2\u01df\u00a8\3\2\2\2\17\2\u0194\u019b\u019f\u01a2"+
		"\u01a7\u01aa\u01b1\u01b7\u01c0\u01c9\u01d2\u01d8\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}