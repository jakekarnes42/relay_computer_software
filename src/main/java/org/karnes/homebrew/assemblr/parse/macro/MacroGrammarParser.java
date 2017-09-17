// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/macro/MacroGrammar.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse.macro;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MacroGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, MACRO=3, ENDM=4, NAME=5, ANY_TEXT=6, COMMENT=7, EOL=8, 
		WS=9;
	public static final int
		RULE_program = 0, RULE_line = 1, RULE_startMacro = 2, RULE_endMacro = 3, 
		RULE_other = 4, RULE_macroName = 5, RULE_param = 6, RULE_name = 7, RULE_comment = 8, 
		RULE_macroStartTag = 9, RULE_macroEndTag = 10;
	public static final String[] ruleNames = {
		"program", "line", "startMacro", "endMacro", "other", "macroName", "param", 
		"name", "comment", "macroStartTag", "macroEndTag"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'$'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "MACRO", "ENDM", "NAME", "ANY_TEXT", "COMMENT", "EOL", 
		"WS"
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

	@Override
	public String getGrammarFileName() { return "MacroGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MacroGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<TerminalNode> EOL() { return getTokens(MacroGrammarParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(MacroGrammarParser.EOL, i);
		}
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MACRO) | (1L << ENDM) | (1L << ANY_TEXT))) != 0)) {
					{
					setState(22);
					line();
					}
				}

				setState(25);
				match(EOL);
				}
				}
				setState(28); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MACRO) | (1L << ENDM) | (1L << ANY_TEXT) | (1L << EOL))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineContext extends ParserRuleContext {
		public StartMacroContext startMacro() {
			return getRuleContext(StartMacroContext.class,0);
		}
		public EndMacroContext endMacro() {
			return getRuleContext(EndMacroContext.class,0);
		}
		public OtherContext other() {
			return getRuleContext(OtherContext.class,0);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitLine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_line);
		try {
			setState(33);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MACRO:
				enterOuterAlt(_localctx, 1);
				{
				setState(30);
				startMacro();
				}
				break;
			case ENDM:
				enterOuterAlt(_localctx, 2);
				{
				setState(31);
				endMacro();
				}
				break;
			case ANY_TEXT:
				enterOuterAlt(_localctx, 3);
				{
				setState(32);
				other();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StartMacroContext extends ParserRuleContext {
		public MacroStartTagContext macroStartTag() {
			return getRuleContext(MacroStartTagContext.class,0);
		}
		public MacroNameContext macroName() {
			return getRuleContext(MacroNameContext.class,0);
		}
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public StartMacroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startMacro; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterStartMacro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitStartMacro(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitStartMacro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartMacroContext startMacro() throws RecognitionException {
		StartMacroContext _localctx = new StartMacroContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_startMacro);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			macroStartTag();
			setState(36);
			macroName();
			setState(41); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(37);
					match(T__0);
					}
				}

				setState(40);
				param();
				}
				}
				setState(43); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 || _la==NAME );
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMENT) {
				{
				setState(45);
				comment();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EndMacroContext extends ParserRuleContext {
		public MacroEndTagContext macroEndTag() {
			return getRuleContext(MacroEndTagContext.class,0);
		}
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public EndMacroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endMacro; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterEndMacro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitEndMacro(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitEndMacro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndMacroContext endMacro() throws RecognitionException {
		EndMacroContext _localctx = new EndMacroContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_endMacro);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			macroEndTag();
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMENT) {
				{
				setState(49);
				comment();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OtherContext extends ParserRuleContext {
		public TerminalNode ANY_TEXT() { return getToken(MacroGrammarParser.ANY_TEXT, 0); }
		public OtherContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_other; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterOther(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitOther(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitOther(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OtherContext other() throws RecognitionException {
		OtherContext _localctx = new OtherContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_other);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(ANY_TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MacroNameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(MacroGrammarParser.NAME, 0); }
		public MacroNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macroName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterMacroName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitMacroName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitMacroName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroNameContext macroName() throws RecognitionException {
		MacroNameContext _localctx = new MacroNameContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_macroName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(T__1);
			setState(55);
			_la = _input.LA(1);
			if ( _la <= 0 || (_la==NAME) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(MacroGrammarParser.NAME, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(MacroGrammarParser.COMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(COMMENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MacroStartTagContext extends ParserRuleContext {
		public TerminalNode MACRO() { return getToken(MacroGrammarParser.MACRO, 0); }
		public MacroStartTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macroStartTag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterMacroStartTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitMacroStartTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitMacroStartTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroStartTagContext macroStartTag() throws RecognitionException {
		MacroStartTagContext _localctx = new MacroStartTagContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_macroStartTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(MACRO);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MacroEndTagContext extends ParserRuleContext {
		public TerminalNode ENDM() { return getToken(MacroGrammarParser.ENDM, 0); }
		public MacroEndTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macroEndTag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).enterMacroEndTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MacroGrammarListener ) ((MacroGrammarListener)listener).exitMacroEndTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MacroGrammarVisitor ) return ((MacroGrammarVisitor<? extends T>)visitor).visitMacroEndTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroEndTagContext macroEndTag() throws RecognitionException {
		MacroEndTagContext _localctx = new MacroEndTagContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_macroEndTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(ENDM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13F\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\5\2\32\n\2\3\2\6\2\35\n\2\r\2\16\2\36\3\3\3\3\3\3\5\3$\n\3"+
		"\3\4\3\4\3\4\5\4)\n\4\3\4\6\4,\n\4\r\4\16\4-\3\4\5\4\61\n\4\3\5\3\5\5"+
		"\5\65\n\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3"+
		"\f\3\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26\2\3\3\2\7\7\2B\2\34\3\2\2\2\4"+
		"#\3\2\2\2\6%\3\2\2\2\b\62\3\2\2\2\n\66\3\2\2\2\f8\3\2\2\2\16;\3\2\2\2"+
		"\20=\3\2\2\2\22?\3\2\2\2\24A\3\2\2\2\26C\3\2\2\2\30\32\5\4\3\2\31\30\3"+
		"\2\2\2\31\32\3\2\2\2\32\33\3\2\2\2\33\35\7\n\2\2\34\31\3\2\2\2\35\36\3"+
		"\2\2\2\36\34\3\2\2\2\36\37\3\2\2\2\37\3\3\2\2\2 $\5\6\4\2!$\5\b\5\2\""+
		"$\5\n\6\2# \3\2\2\2#!\3\2\2\2#\"\3\2\2\2$\5\3\2\2\2%&\5\24\13\2&+\5\f"+
		"\7\2\')\7\3\2\2(\'\3\2\2\2()\3\2\2\2)*\3\2\2\2*,\5\16\b\2+(\3\2\2\2,-"+
		"\3\2\2\2-+\3\2\2\2-.\3\2\2\2.\60\3\2\2\2/\61\5\22\n\2\60/\3\2\2\2\60\61"+
		"\3\2\2\2\61\7\3\2\2\2\62\64\5\26\f\2\63\65\5\22\n\2\64\63\3\2\2\2\64\65"+
		"\3\2\2\2\65\t\3\2\2\2\66\67\7\b\2\2\67\13\3\2\2\289\7\4\2\29:\n\2\2\2"+
		":\r\3\2\2\2;<\5\20\t\2<\17\3\2\2\2=>\7\7\2\2>\21\3\2\2\2?@\7\t\2\2@\23"+
		"\3\2\2\2AB\7\5\2\2B\25\3\2\2\2CD\7\6\2\2D\27\3\2\2\2\t\31\36#(-\60\64";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}