// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/AsmHomeBrew.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse;
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
		T__0=1, T__1=2, MOV=3, ADD=4, INC=5, AND=6, OR=7, XOR=8, NOT=9, ROL=10, 
		LOAD=11, STORE=12, PUSH=13, POP=14, CALL=15, BYIN=16, BYOUT=17, JMP=18, 
		JZ=19, JNZ=20, JNEG=21, JNNEG=22, JC=23, JNC=24, JO=25, JNO=26, NOP=27, 
		HALT=28, ORG=29, AX=30, BX=31, CX=32, DX=33, IP=34, SP=35, RP=36, PC=37, 
		DB=38, DW=39, NAME=40, NUMBER=41, DECIMAL=42, HEX=43, COMMENT=44, STRING=45, 
		JAVASCRIPT=46, EOL=47, WS=48;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", 
		"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", 
		"Z", "MOV", "ADD", "INC", "AND", "OR", "XOR", "NOT", "ROL", "LOAD", "STORE", 
		"PUSH", "POP", "CALL", "BYIN", "BYOUT", "JMP", "JZ", "JNZ", "JNEG", "JNNEG", 
		"JC", "JNC", "JO", "JNO", "NOP", "HALT", "ORG", "AX", "BX", "CX", "DX", 
		"IP", "SP", "RP", "PC", "DB", "DW", "NAME", "NUMBER", "DECIMAL", "HEX", 
		"COMMENT", "STRING", "JAVASCRIPT", "EOL", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "MOV", "ADD", "INC", "AND", "OR", "XOR", "NOT", "ROL", 
		"LOAD", "STORE", "PUSH", "POP", "CALL", "BYIN", "BYOUT", "JMP", "JZ", 
		"JNZ", "JNEG", "JNNEG", "JC", "JNC", "JO", "JNO", "NOP", "HALT", "ORG", 
		"AX", "BX", "CX", "DX", "IP", "SP", "RP", "PC", "DB", "DW", "NAME", "NUMBER", 
		"DECIMAL", "HEX", "COMMENT", "STRING", "JAVASCRIPT", "EOL", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\62\u01a2\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4"+
		"I\tI\4J\tJ\4K\tK\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20"+
		"\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27"+
		"\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36"+
		"\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3"+
		"\"\3#\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\3%\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3"+
		",\3,\3,\3,\3,\3-\3-\3-\3-\3.\3.\3.\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\64"+
		"\3\64\3\64\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67"+
		"\3\67\38\38\38\38\39\39\39\3:\3:\3:\3;\3;\3;\3<\3<\3<\3=\3=\3=\3>\3>\3"+
		">\3?\3?\3?\3@\3@\3@\3A\3A\3A\3B\3B\3B\3C\3C\7C\u0164\nC\fC\16C\u0167\13"+
		"C\3D\3D\5D\u016b\nD\3E\5E\u016e\nE\3E\6E\u0171\nE\rE\16E\u0172\3F\5F\u0176"+
		"\nF\3F\3F\3F\6F\u017b\nF\rF\16F\u017c\3G\3G\7G\u0181\nG\fG\16G\u0184\13"+
		"G\3G\3G\3H\3H\7H\u018a\nH\fH\16H\u018d\13H\3H\3H\3I\3I\7I\u0193\nI\fI"+
		"\16I\u0196\13I\3I\3I\3J\5J\u019b\nJ\3J\3J\3K\3K\3K\3K\3\u0194\2L\3\3\5"+
		"\4\7\2\t\2\13\2\r\2\17\2\21\2\23\2\25\2\27\2\31\2\33\2\35\2\37\2!\2#\2"+
		"%\2\'\2)\2+\2-\2/\2\61\2\63\2\65\2\67\29\2;\5=\6?\7A\bC\tE\nG\13I\fK\r"+
		"M\16O\17Q\20S\21U\22W\23Y\24[\25]\26_\27a\30c\31e\32g\33i\34k\35m\36o"+
		"\37q s!u\"w#y${%}&\177\'\u0081(\u0083)\u0085*\u0087+\u0089,\u008b-\u008d"+
		".\u008f/\u0091\60\u0093\61\u0095\62\3\2$\4\2CCcc\4\2DDdd\4\2EEee\4\2F"+
		"Fff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4"+
		"\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWw"+
		"w\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\4\2C\\c|\5\2\62;C\\c|\3\2"+
		"\62;\3\2\62\62\5\2\62;CHch\4\2\f\f\17\17\3\2$$\4\2\13\13\"\"\2\u0191\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C"+
		"\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2"+
		"\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2"+
		"\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i"+
		"\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2"+
		"\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081"+
		"\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2"+
		"\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093"+
		"\3\2\2\2\2\u0095\3\2\2\2\3\u0097\3\2\2\2\5\u0099\3\2\2\2\7\u009b\3\2\2"+
		"\2\t\u009d\3\2\2\2\13\u009f\3\2\2\2\r\u00a1\3\2\2\2\17\u00a3\3\2\2\2\21"+
		"\u00a5\3\2\2\2\23\u00a7\3\2\2\2\25\u00a9\3\2\2\2\27\u00ab\3\2\2\2\31\u00ad"+
		"\3\2\2\2\33\u00af\3\2\2\2\35\u00b1\3\2\2\2\37\u00b3\3\2\2\2!\u00b5\3\2"+
		"\2\2#\u00b7\3\2\2\2%\u00b9\3\2\2\2\'\u00bb\3\2\2\2)\u00bd\3\2\2\2+\u00bf"+
		"\3\2\2\2-\u00c1\3\2\2\2/\u00c3\3\2\2\2\61\u00c5\3\2\2\2\63\u00c7\3\2\2"+
		"\2\65\u00c9\3\2\2\2\67\u00cb\3\2\2\29\u00cd\3\2\2\2;\u00cf\3\2\2\2=\u00d3"+
		"\3\2\2\2?\u00d7\3\2\2\2A\u00db\3\2\2\2C\u00df\3\2\2\2E\u00e2\3\2\2\2G"+
		"\u00e6\3\2\2\2I\u00ea\3\2\2\2K\u00ee\3\2\2\2M\u00f3\3\2\2\2O\u00f9\3\2"+
		"\2\2Q\u00fe\3\2\2\2S\u0102\3\2\2\2U\u0107\3\2\2\2W\u010c\3\2\2\2Y\u0112"+
		"\3\2\2\2[\u0116\3\2\2\2]\u0119\3\2\2\2_\u011d\3\2\2\2a\u0122\3\2\2\2c"+
		"\u0128\3\2\2\2e\u012b\3\2\2\2g\u012f\3\2\2\2i\u0132\3\2\2\2k\u0136\3\2"+
		"\2\2m\u013a\3\2\2\2o\u013f\3\2\2\2q\u0143\3\2\2\2s\u0146\3\2\2\2u\u0149"+
		"\3\2\2\2w\u014c\3\2\2\2y\u014f\3\2\2\2{\u0152\3\2\2\2}\u0155\3\2\2\2\177"+
		"\u0158\3\2\2\2\u0081\u015b\3\2\2\2\u0083\u015e\3\2\2\2\u0085\u0161\3\2"+
		"\2\2\u0087\u016a\3\2\2\2\u0089\u016d\3\2\2\2\u008b\u0175\3\2\2\2\u008d"+
		"\u017e\3\2\2\2\u008f\u0187\3\2\2\2\u0091\u0190\3\2\2\2\u0093\u019a\3\2"+
		"\2\2\u0095\u019e\3\2\2\2\u0097\u0098\7<\2\2\u0098\4\3\2\2\2\u0099\u009a"+
		"\7.\2\2\u009a\6\3\2\2\2\u009b\u009c\t\2\2\2\u009c\b\3\2\2\2\u009d\u009e"+
		"\t\3\2\2\u009e\n\3\2\2\2\u009f\u00a0\t\4\2\2\u00a0\f\3\2\2\2\u00a1\u00a2"+
		"\t\5\2\2\u00a2\16\3\2\2\2\u00a3\u00a4\t\6\2\2\u00a4\20\3\2\2\2\u00a5\u00a6"+
		"\t\7\2\2\u00a6\22\3\2\2\2\u00a7\u00a8\t\b\2\2\u00a8\24\3\2\2\2\u00a9\u00aa"+
		"\t\t\2\2\u00aa\26\3\2\2\2\u00ab\u00ac\t\n\2\2\u00ac\30\3\2\2\2\u00ad\u00ae"+
		"\t\13\2\2\u00ae\32\3\2\2\2\u00af\u00b0\t\f\2\2\u00b0\34\3\2\2\2\u00b1"+
		"\u00b2\t\r\2\2\u00b2\36\3\2\2\2\u00b3\u00b4\t\16\2\2\u00b4 \3\2\2\2\u00b5"+
		"\u00b6\t\17\2\2\u00b6\"\3\2\2\2\u00b7\u00b8\t\20\2\2\u00b8$\3\2\2\2\u00b9"+
		"\u00ba\t\21\2\2\u00ba&\3\2\2\2\u00bb\u00bc\t\22\2\2\u00bc(\3\2\2\2\u00bd"+
		"\u00be\t\23\2\2\u00be*\3\2\2\2\u00bf\u00c0\t\24\2\2\u00c0,\3\2\2\2\u00c1"+
		"\u00c2\t\25\2\2\u00c2.\3\2\2\2\u00c3\u00c4\t\26\2\2\u00c4\60\3\2\2\2\u00c5"+
		"\u00c6\t\27\2\2\u00c6\62\3\2\2\2\u00c7\u00c8\t\30\2\2\u00c8\64\3\2\2\2"+
		"\u00c9\u00ca\t\31\2\2\u00ca\66\3\2\2\2\u00cb\u00cc\t\32\2\2\u00cc8\3\2"+
		"\2\2\u00cd\u00ce\t\33\2\2\u00ce:\3\2\2\2\u00cf\u00d0\5\37\20\2\u00d0\u00d1"+
		"\5#\22\2\u00d1\u00d2\5\61\31\2\u00d2<\3\2\2\2\u00d3\u00d4\5\7\4\2\u00d4"+
		"\u00d5\5\r\7\2\u00d5\u00d6\5\r\7\2\u00d6>\3\2\2\2\u00d7\u00d8\5\27\f\2"+
		"\u00d8\u00d9\5!\21\2\u00d9\u00da\5\13\6\2\u00da@\3\2\2\2\u00db\u00dc\5"+
		"\7\4\2\u00dc\u00dd\5!\21\2\u00dd\u00de\5\r\7\2\u00deB\3\2\2\2\u00df\u00e0"+
		"\5#\22\2\u00e0\u00e1\5)\25\2\u00e1D\3\2\2\2\u00e2\u00e3\5\65\33\2\u00e3"+
		"\u00e4\5#\22\2\u00e4\u00e5\5)\25\2\u00e5F\3\2\2\2\u00e6\u00e7\5!\21\2"+
		"\u00e7\u00e8\5#\22\2\u00e8\u00e9\5-\27\2\u00e9H\3\2\2\2\u00ea\u00eb\5"+
		")\25\2\u00eb\u00ec\5#\22\2\u00ec\u00ed\5\35\17\2\u00edJ\3\2\2\2\u00ee"+
		"\u00ef\5\35\17\2\u00ef\u00f0\5#\22\2\u00f0\u00f1\5\7\4\2\u00f1\u00f2\5"+
		"\r\7\2\u00f2L\3\2\2\2\u00f3\u00f4\5+\26\2\u00f4\u00f5\5-\27\2\u00f5\u00f6"+
		"\5#\22\2\u00f6\u00f7\5)\25\2\u00f7\u00f8\5\17\b\2\u00f8N\3\2\2\2\u00f9"+
		"\u00fa\5%\23\2\u00fa\u00fb\5/\30\2\u00fb\u00fc\5+\26\2\u00fc\u00fd\5\25"+
		"\13\2\u00fdP\3\2\2\2\u00fe\u00ff\5%\23\2\u00ff\u0100\5#\22\2\u0100\u0101"+
		"\5%\23\2\u0101R\3\2\2\2\u0102\u0103\5\13\6\2\u0103\u0104\5\7\4\2\u0104"+
		"\u0105\5\35\17\2\u0105\u0106\5\35\17\2\u0106T\3\2\2\2\u0107\u0108\5\t"+
		"\5\2\u0108\u0109\5\67\34\2\u0109\u010a\5\27\f\2\u010a\u010b\5!\21\2\u010b"+
		"V\3\2\2\2\u010c\u010d\5\t\5\2\u010d\u010e\5\67\34\2\u010e\u010f\5#\22"+
		"\2\u010f\u0110\5/\30\2\u0110\u0111\5-\27\2\u0111X\3\2\2\2\u0112\u0113"+
		"\5\31\r\2\u0113\u0114\5\37\20\2\u0114\u0115\5%\23\2\u0115Z\3\2\2\2\u0116"+
		"\u0117\5\31\r\2\u0117\u0118\59\35\2\u0118\\\3\2\2\2\u0119\u011a\5\31\r"+
		"\2\u011a\u011b\5!\21\2\u011b\u011c\59\35\2\u011c^\3\2\2\2\u011d\u011e"+
		"\5\31\r\2\u011e\u011f\5!\21\2\u011f\u0120\5\17\b\2\u0120\u0121\5\23\n"+
		"\2\u0121`\3\2\2\2\u0122\u0123\5\31\r\2\u0123\u0124\5!\21\2\u0124\u0125"+
		"\5!\21\2\u0125\u0126\5\17\b\2\u0126\u0127\5\23\n\2\u0127b\3\2\2\2\u0128"+
		"\u0129\5\31\r\2\u0129\u012a\5\13\6\2\u012ad\3\2\2\2\u012b\u012c\5\31\r"+
		"\2\u012c\u012d\5!\21\2\u012d\u012e\5\13\6\2\u012ef\3\2\2\2\u012f\u0130"+
		"\5\31\r\2\u0130\u0131\5#\22\2\u0131h\3\2\2\2\u0132\u0133\5\31\r\2\u0133"+
		"\u0134\5!\21\2\u0134\u0135\5#\22\2\u0135j\3\2\2\2\u0136\u0137\5!\21\2"+
		"\u0137\u0138\5#\22\2\u0138\u0139\5%\23\2\u0139l\3\2\2\2\u013a\u013b\5"+
		"\25\13\2\u013b\u013c\5\7\4\2\u013c\u013d\5\35\17\2\u013d\u013e\5-\27\2"+
		"\u013en\3\2\2\2\u013f\u0140\5#\22\2\u0140\u0141\5)\25\2\u0141\u0142\5"+
		"\23\n\2\u0142p\3\2\2\2\u0143\u0144\5\7\4\2\u0144\u0145\5\65\33\2\u0145"+
		"r\3\2\2\2\u0146\u0147\5\t\5\2\u0147\u0148\5\65\33\2\u0148t\3\2\2\2\u0149"+
		"\u014a\5\13\6\2\u014a\u014b\5\65\33\2\u014bv\3\2\2\2\u014c\u014d\5\r\7"+
		"\2\u014d\u014e\5\65\33\2\u014ex\3\2\2\2\u014f\u0150\5\27\f\2\u0150\u0151"+
		"\5%\23\2\u0151z\3\2\2\2\u0152\u0153\5+\26\2\u0153\u0154\5%\23\2\u0154"+
		"|\3\2\2\2\u0155\u0156\5)\25\2\u0156\u0157\5%\23\2\u0157~\3\2\2\2\u0158"+
		"\u0159\5%\23\2\u0159\u015a\5\13\6\2\u015a\u0080\3\2\2\2\u015b\u015c\5"+
		"\r\7\2\u015c\u015d\5\t\5\2\u015d\u0082\3\2\2\2\u015e\u015f\5\r\7\2\u015f"+
		"\u0160\5\63\32\2\u0160\u0084\3\2\2\2\u0161\u0165\t\34\2\2\u0162\u0164"+
		"\t\35\2\2\u0163\u0162\3\2\2\2\u0164\u0167\3\2\2\2\u0165\u0163\3\2\2\2"+
		"\u0165\u0166\3\2\2\2\u0166\u0086\3\2\2\2\u0167\u0165\3\2\2\2\u0168\u016b"+
		"\5\u0089E\2\u0169\u016b\5\u008bF\2\u016a\u0168\3\2\2\2\u016a\u0169\3\2"+
		"\2\2\u016b\u0088\3\2\2\2\u016c\u016e\7/\2\2\u016d\u016c\3\2\2\2\u016d"+
		"\u016e\3\2\2\2\u016e\u0170\3\2\2\2\u016f\u0171\t\36\2\2\u0170\u016f\3"+
		"\2\2\2\u0171\u0172\3\2\2\2\u0172\u0170\3\2\2\2\u0172\u0173\3\2\2\2\u0173"+
		"\u008a\3\2\2\2\u0174\u0176\7/\2\2\u0175\u0174\3\2\2\2\u0175\u0176\3\2"+
		"\2\2\u0176\u0177\3\2\2\2\u0177\u0178\t\37\2\2\u0178\u017a\5\65\33\2\u0179"+
		"\u017b\t \2\2\u017a\u0179\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017a\3\2"+
		"\2\2\u017c\u017d\3\2\2\2\u017d\u008c\3\2\2\2\u017e\u0182\7=\2\2\u017f"+
		"\u0181\n!\2\2\u0180\u017f\3\2\2\2\u0181\u0184\3\2\2\2\u0182\u0180\3\2"+
		"\2\2\u0182\u0183\3\2\2\2\u0183\u0185\3\2\2\2\u0184\u0182\3\2\2\2\u0185"+
		"\u0186\bG\2\2\u0186\u008e\3\2\2\2\u0187\u018b\7$\2\2\u0188\u018a\n\"\2"+
		"\2\u0189\u0188\3\2\2\2\u018a\u018d\3\2\2\2\u018b\u0189\3\2\2\2\u018b\u018c"+
		"\3\2\2\2\u018c\u018e\3\2\2\2\u018d\u018b\3\2\2\2\u018e\u018f\7$\2\2\u018f"+
		"\u0090\3\2\2\2\u0190\u0194\7}\2\2\u0191\u0193\13\2\2\2\u0192\u0191\3\2"+
		"\2\2\u0193\u0196\3\2\2\2\u0194\u0195\3\2\2\2\u0194\u0192\3\2\2\2\u0195"+
		"\u0197\3\2\2\2\u0196\u0194\3\2\2\2\u0197\u0198\7\177\2\2\u0198\u0092\3"+
		"\2\2\2\u0199\u019b\7\17\2\2\u019a\u0199\3\2\2\2\u019a\u019b\3\2\2\2\u019b"+
		"\u019c\3\2\2\2\u019c\u019d\7\f\2\2\u019d\u0094\3\2\2\2\u019e\u019f\t#"+
		"\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a1\bK\2\2\u01a1\u0096\3\2\2\2\r\2\u0165"+
		"\u016a\u016d\u0172\u0175\u017c\u0182\u018b\u0194\u019a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}