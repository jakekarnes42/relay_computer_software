// Generated from /home/jake/IdeaProjects/relay/src/main/java/org/karnes/homebrew/assemblr/parse/asm/antlr/AsmHomeBrew.g4 by ANTLR 4.7
package org.karnes.homebrew.assemblr.parse.asm.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AsmHomeBrewParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, MOV=3, CLR=4, ADD=5, INC=6, DEC=7, AND=8, OR=9, XOR=10, 
		NOT=11, ROL=12, CMP=13, SUB=14, LOAD=15, FETCH=16, STORE=17, PUSH=18, 
		POP=19, RET=20, CALL=21, WRDIN=22, WRDOUT=23, JMP=24, JZ=25, JNZ=26, JNEG=27, 
		JNNEG=28, JC=29, JNC=30, JO=31, JNO=32, NOP=33, HALT=34, ORG=35, DW=36, 
		DS=37, AX=38, BX=39, CX=40, DX=41, EX=42, SP=43, RP=44, PC=45, NAME=46, 
		MACRO_NAME=47, NUMBER=48, DECIMAL=49, HEX=50, COMMENT=51, STRING=52, JAVASCRIPT=53, 
		EOL=54, WS=55;
	public static final int
		RULE_program = 0, RULE_line = 1, RULE_instruction = 2, RULE_lbl = 3, RULE_operation = 4, 
		RULE_noArgOperation = 5, RULE_unaryOperation = 6, RULE_returnOperation = 7, 
		RULE_clearOperation = 8, RULE_ioOperation = 9, RULE_ioOpcode = 10, RULE_oneArgOpcode = 11, 
		RULE_jumpOperation = 12, RULE_jumpOpcode = 13, RULE_binaryOperation = 14, 
		RULE_binaryRegRegOperation = 15, RULE_binaryRegRegOpCode = 16, RULE_binaryRegValOperation = 17, 
		RULE_binaryRegValOpCode = 18, RULE_stackOperation = 19, RULE_pushOperation = 20, 
		RULE_popOperation = 21, RULE_callOperation = 22, RULE_stackOpcode = 23, 
		RULE_ternaryOperation = 24, RULE_aluTernaryOperation = 25, RULE_aluTernaryOpcode = 26, 
		RULE_value = 27, RULE_label = 28, RULE_register = 29, RULE_stackRegister = 30, 
		RULE_assemblerDirective = 31, RULE_assemblerOrgDirective = 32, RULE_assemblerWordDeclaration = 33, 
		RULE_assemblerStringDeclaration = 34, RULE_macro = 35, RULE_macroParamValue = 36, 
		RULE_jsExpression = 37, RULE_name = 38, RULE_macroName = 39, RULE_number = 40, 
		RULE_comment = 41, RULE_string = 42;
	public static final String[] ruleNames = {
		"program", "line", "instruction", "lbl", "operation", "noArgOperation", 
		"unaryOperation", "returnOperation", "clearOperation", "ioOperation", 
		"ioOpcode", "oneArgOpcode", "jumpOperation", "jumpOpcode", "binaryOperation", 
		"binaryRegRegOperation", "binaryRegRegOpCode", "binaryRegValOperation", 
		"binaryRegValOpCode", "stackOperation", "pushOperation", "popOperation", 
		"callOperation", "stackOpcode", "ternaryOperation", "aluTernaryOperation", 
		"aluTernaryOpcode", "value", "label", "register", "stackRegister", "assemblerDirective", 
		"assemblerOrgDirective", "assemblerWordDeclaration", "assemblerStringDeclaration", 
		"macro", "macroParamValue", "jsExpression", "name", "macroName", "number", 
		"comment", "string"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "MOV", "CLR", "ADD", "INC", "DEC", "AND", "OR", "XOR", 
		"NOT", "ROL", "CMP", "SUB", "LOAD", "FETCH", "STORE", "PUSH", "POP", "RET", 
		"CALL", "WRDIN", "WRDOUT", "JMP", "JZ", "JNZ", "JNEG", "JNNEG", "JC", 
		"JNC", "JO", "JNO", "NOP", "HALT", "ORG", "DW", "DS", "AX", "BX", "CX", 
		"DX", "EX", "SP", "RP", "PC", "NAME", "MACRO_NAME", "NUMBER", "DECIMAL", 
		"HEX", "COMMENT", "STRING", "JAVASCRIPT", "EOL", "WS"
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
	public String getGrammarFileName() { return "AsmHomeBrew.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AsmHomeBrewParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<TerminalNode> EOL() { return getTokens(AsmHomeBrewParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(AsmHomeBrewParser.EOL, i);
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
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitProgram(this);
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
			setState(90); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << CLR) | (1L << ADD) | (1L << INC) | (1L << DEC) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT) | (1L << ROL) | (1L << CMP) | (1L << SUB) | (1L << LOAD) | (1L << FETCH) | (1L << STORE) | (1L << PUSH) | (1L << POP) | (1L << RET) | (1L << CALL) | (1L << WRDIN) | (1L << WRDOUT) | (1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JNEG) | (1L << JNNEG) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO) | (1L << NOP) | (1L << HALT) | (1L << ORG) | (1L << DW) | (1L << DS) | (1L << NAME) | (1L << MACRO_NAME) | (1L << COMMENT) | (1L << JAVASCRIPT))) != 0)) {
					{
					setState(86);
					line();
					}
				}

				setState(89);
				match(EOL);
				}
				}
				setState(92); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << CLR) | (1L << ADD) | (1L << INC) | (1L << DEC) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT) | (1L << ROL) | (1L << CMP) | (1L << SUB) | (1L << LOAD) | (1L << FETCH) | (1L << STORE) | (1L << PUSH) | (1L << POP) | (1L << RET) | (1L << CALL) | (1L << WRDIN) | (1L << WRDOUT) | (1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JNEG) | (1L << JNNEG) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO) | (1L << NOP) | (1L << HALT) | (1L << ORG) | (1L << DW) | (1L << DS) | (1L << NAME) | (1L << MACRO_NAME) | (1L << COMMENT) | (1L << JAVASCRIPT) | (1L << EOL))) != 0) );
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
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public AssemblerDirectiveContext assemblerDirective() {
			return getRuleContext(AssemblerDirectiveContext.class,0);
		}
		public MacroContext macro() {
			return getRuleContext(MacroContext.class,0);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitLine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_line);
		int _la;
		try {
			setState(104);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(94);
				comment();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(95);
				instruction();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(96);
				assemblerDirective();
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(97);
					comment();
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(100);
				macro();
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMENT) {
					{
					setState(101);
					comment();
					}
				}

				}
				break;
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

	public static class InstructionContext extends ParserRuleContext {
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public LblContext lbl() {
			return getRuleContext(LblContext.class,0);
		}
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_instruction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(106);
				lbl();
				}
			}

			setState(109);
			operation();
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMENT) {
				{
				setState(110);
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

	public static class LblContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public LblContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lbl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterLbl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitLbl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitLbl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LblContext lbl() throws RecognitionException {
		LblContext _localctx = new LblContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_lbl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			label();
			setState(114);
			match(T__0);
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

	public static class OperationContext extends ParserRuleContext {
		public NoArgOperationContext noArgOperation() {
			return getRuleContext(NoArgOperationContext.class,0);
		}
		public UnaryOperationContext unaryOperation() {
			return getRuleContext(UnaryOperationContext.class,0);
		}
		public BinaryOperationContext binaryOperation() {
			return getRuleContext(BinaryOperationContext.class,0);
		}
		public TernaryOperationContext ternaryOperation() {
			return getRuleContext(TernaryOperationContext.class,0);
		}
		public OperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperationContext operation() throws RecognitionException {
		OperationContext _localctx = new OperationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_operation);
		try {
			setState(120);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOP:
			case HALT:
				enterOuterAlt(_localctx, 1);
				{
				setState(116);
				noArgOperation();
				}
				break;
			case CLR:
			case RET:
			case WRDIN:
			case WRDOUT:
			case JMP:
			case JZ:
			case JNZ:
			case JNEG:
			case JNNEG:
			case JC:
			case JNC:
			case JO:
			case JNO:
				enterOuterAlt(_localctx, 2);
				{
				setState(117);
				unaryOperation();
				}
				break;
			case MOV:
			case INC:
			case DEC:
			case NOT:
			case ROL:
			case LOAD:
			case FETCH:
			case STORE:
			case PUSH:
			case POP:
			case CALL:
				enterOuterAlt(_localctx, 3);
				{
				setState(118);
				binaryOperation();
				}
				break;
			case ADD:
			case AND:
			case OR:
			case XOR:
			case CMP:
			case SUB:
				enterOuterAlt(_localctx, 4);
				{
				setState(119);
				ternaryOperation();
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

	public static class NoArgOperationContext extends ParserRuleContext {
		public TerminalNode NOP() { return getToken(AsmHomeBrewParser.NOP, 0); }
		public TerminalNode HALT() { return getToken(AsmHomeBrewParser.HALT, 0); }
		public NoArgOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_noArgOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterNoArgOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitNoArgOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitNoArgOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NoArgOperationContext noArgOperation() throws RecognitionException {
		NoArgOperationContext _localctx = new NoArgOperationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_noArgOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			_la = _input.LA(1);
			if ( !(_la==NOP || _la==HALT) ) {
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

	public static class UnaryOperationContext extends ParserRuleContext {
		public IoOperationContext ioOperation() {
			return getRuleContext(IoOperationContext.class,0);
		}
		public ReturnOperationContext returnOperation() {
			return getRuleContext(ReturnOperationContext.class,0);
		}
		public JumpOperationContext jumpOperation() {
			return getRuleContext(JumpOperationContext.class,0);
		}
		public ClearOperationContext clearOperation() {
			return getRuleContext(ClearOperationContext.class,0);
		}
		public UnaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterUnaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitUnaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitUnaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperationContext unaryOperation() throws RecognitionException {
		UnaryOperationContext _localctx = new UnaryOperationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_unaryOperation);
		try {
			setState(128);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WRDIN:
			case WRDOUT:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				ioOperation();
				}
				break;
			case RET:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				returnOperation();
				}
				break;
			case JMP:
			case JZ:
			case JNZ:
			case JNEG:
			case JNNEG:
			case JC:
			case JNC:
			case JO:
			case JNO:
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
				jumpOperation();
				}
				break;
			case CLR:
				enterOuterAlt(_localctx, 4);
				{
				setState(127);
				clearOperation();
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

	public static class ReturnOperationContext extends ParserRuleContext {
		public TerminalNode RET() { return getToken(AsmHomeBrewParser.RET, 0); }
		public StackRegisterContext stackRegister() {
			return getRuleContext(StackRegisterContext.class,0);
		}
		public ReturnOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterReturnOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitReturnOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitReturnOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnOperationContext returnOperation() throws RecognitionException {
		ReturnOperationContext _localctx = new ReturnOperationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_returnOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			match(RET);
			setState(131);
			stackRegister();
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

	public static class ClearOperationContext extends ParserRuleContext {
		public TerminalNode CLR() { return getToken(AsmHomeBrewParser.CLR, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public ClearOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clearOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterClearOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitClearOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitClearOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClearOperationContext clearOperation() throws RecognitionException {
		ClearOperationContext _localctx = new ClearOperationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_clearOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(CLR);
			setState(134);
			register();
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

	public static class IoOperationContext extends ParserRuleContext {
		public IoOpcodeContext ioOpcode() {
			return getRuleContext(IoOpcodeContext.class,0);
		}
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public IoOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ioOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterIoOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitIoOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitIoOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IoOperationContext ioOperation() throws RecognitionException {
		IoOperationContext _localctx = new IoOperationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ioOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			ioOpcode();
			setState(137);
			register();
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

	public static class IoOpcodeContext extends ParserRuleContext {
		public TerminalNode WRDIN() { return getToken(AsmHomeBrewParser.WRDIN, 0); }
		public TerminalNode WRDOUT() { return getToken(AsmHomeBrewParser.WRDOUT, 0); }
		public IoOpcodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ioOpcode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterIoOpcode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitIoOpcode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitIoOpcode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IoOpcodeContext ioOpcode() throws RecognitionException {
		IoOpcodeContext _localctx = new IoOpcodeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_ioOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			_la = _input.LA(1);
			if ( !(_la==WRDIN || _la==WRDOUT) ) {
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

	public static class OneArgOpcodeContext extends ParserRuleContext {
		public TerminalNode RET() { return getToken(AsmHomeBrewParser.RET, 0); }
		public TerminalNode CLR() { return getToken(AsmHomeBrewParser.CLR, 0); }
		public TerminalNode WRDIN() { return getToken(AsmHomeBrewParser.WRDIN, 0); }
		public TerminalNode WRDOUT() { return getToken(AsmHomeBrewParser.WRDOUT, 0); }
		public OneArgOpcodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oneArgOpcode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterOneArgOpcode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitOneArgOpcode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitOneArgOpcode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OneArgOpcodeContext oneArgOpcode() throws RecognitionException {
		OneArgOpcodeContext _localctx = new OneArgOpcodeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_oneArgOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CLR) | (1L << RET) | (1L << WRDIN) | (1L << WRDOUT))) != 0)) ) {
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

	public static class JumpOperationContext extends ParserRuleContext {
		public JumpOpcodeContext jumpOpcode() {
			return getRuleContext(JumpOpcodeContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public JumpOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterJumpOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitJumpOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitJumpOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpOperationContext jumpOperation() throws RecognitionException {
		JumpOperationContext _localctx = new JumpOperationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_jumpOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			jumpOpcode();
			setState(144);
			value();
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

	public static class JumpOpcodeContext extends ParserRuleContext {
		public TerminalNode JMP() { return getToken(AsmHomeBrewParser.JMP, 0); }
		public TerminalNode JZ() { return getToken(AsmHomeBrewParser.JZ, 0); }
		public TerminalNode JNZ() { return getToken(AsmHomeBrewParser.JNZ, 0); }
		public TerminalNode JNEG() { return getToken(AsmHomeBrewParser.JNEG, 0); }
		public TerminalNode JNNEG() { return getToken(AsmHomeBrewParser.JNNEG, 0); }
		public TerminalNode JC() { return getToken(AsmHomeBrewParser.JC, 0); }
		public TerminalNode JNC() { return getToken(AsmHomeBrewParser.JNC, 0); }
		public TerminalNode JO() { return getToken(AsmHomeBrewParser.JO, 0); }
		public TerminalNode JNO() { return getToken(AsmHomeBrewParser.JNO, 0); }
		public JumpOpcodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpOpcode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterJumpOpcode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitJumpOpcode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitJumpOpcode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpOpcodeContext jumpOpcode() throws RecognitionException {
		JumpOpcodeContext _localctx = new JumpOpcodeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_jumpOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << JMP) | (1L << JZ) | (1L << JNZ) | (1L << JNEG) | (1L << JNNEG) | (1L << JC) | (1L << JNC) | (1L << JO) | (1L << JNO))) != 0)) ) {
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

	public static class BinaryOperationContext extends ParserRuleContext {
		public BinaryRegRegOperationContext binaryRegRegOperation() {
			return getRuleContext(BinaryRegRegOperationContext.class,0);
		}
		public BinaryRegValOperationContext binaryRegValOperation() {
			return getRuleContext(BinaryRegValOperationContext.class,0);
		}
		public StackOperationContext stackOperation() {
			return getRuleContext(StackOperationContext.class,0);
		}
		public BinaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryOperationContext binaryOperation() throws RecognitionException {
		BinaryOperationContext _localctx = new BinaryOperationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_binaryOperation);
		try {
			setState(151);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MOV:
			case INC:
			case DEC:
			case NOT:
			case ROL:
			case FETCH:
			case STORE:
				enterOuterAlt(_localctx, 1);
				{
				setState(148);
				binaryRegRegOperation();
				}
				break;
			case LOAD:
				enterOuterAlt(_localctx, 2);
				{
				setState(149);
				binaryRegValOperation();
				}
				break;
			case PUSH:
			case POP:
			case CALL:
				enterOuterAlt(_localctx, 3);
				{
				setState(150);
				stackOperation();
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

	public static class BinaryRegRegOperationContext extends ParserRuleContext {
		public BinaryRegRegOpCodeContext binaryRegRegOpCode() {
			return getRuleContext(BinaryRegRegOpCodeContext.class,0);
		}
		public List<RegisterContext> register() {
			return getRuleContexts(RegisterContext.class);
		}
		public RegisterContext register(int i) {
			return getRuleContext(RegisterContext.class,i);
		}
		public BinaryRegRegOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryRegRegOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryRegRegOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryRegRegOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryRegRegOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryRegRegOperationContext binaryRegRegOperation() throws RecognitionException {
		BinaryRegRegOperationContext _localctx = new BinaryRegRegOperationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_binaryRegRegOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			binaryRegRegOpCode();
			setState(154);
			register();
			setState(155);
			match(T__1);
			setState(156);
			register();
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

	public static class BinaryRegRegOpCodeContext extends ParserRuleContext {
		public TerminalNode MOV() { return getToken(AsmHomeBrewParser.MOV, 0); }
		public TerminalNode INC() { return getToken(AsmHomeBrewParser.INC, 0); }
		public TerminalNode DEC() { return getToken(AsmHomeBrewParser.DEC, 0); }
		public TerminalNode NOT() { return getToken(AsmHomeBrewParser.NOT, 0); }
		public TerminalNode ROL() { return getToken(AsmHomeBrewParser.ROL, 0); }
		public TerminalNode STORE() { return getToken(AsmHomeBrewParser.STORE, 0); }
		public TerminalNode FETCH() { return getToken(AsmHomeBrewParser.FETCH, 0); }
		public BinaryRegRegOpCodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryRegRegOpCode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryRegRegOpCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryRegRegOpCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryRegRegOpCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryRegRegOpCodeContext binaryRegRegOpCode() throws RecognitionException {
		BinaryRegRegOpCodeContext _localctx = new BinaryRegRegOpCodeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_binaryRegRegOpCode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MOV) | (1L << INC) | (1L << DEC) | (1L << NOT) | (1L << ROL) | (1L << FETCH) | (1L << STORE))) != 0)) ) {
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

	public static class BinaryRegValOperationContext extends ParserRuleContext {
		public BinaryRegValOpCodeContext binaryRegValOpCode() {
			return getRuleContext(BinaryRegValOpCodeContext.class,0);
		}
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public BinaryRegValOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryRegValOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryRegValOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryRegValOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryRegValOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryRegValOperationContext binaryRegValOperation() throws RecognitionException {
		BinaryRegValOperationContext _localctx = new BinaryRegValOperationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_binaryRegValOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			binaryRegValOpCode();
			setState(161);
			register();
			setState(162);
			match(T__1);
			setState(163);
			value();
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

	public static class BinaryRegValOpCodeContext extends ParserRuleContext {
		public TerminalNode LOAD() { return getToken(AsmHomeBrewParser.LOAD, 0); }
		public BinaryRegValOpCodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryRegValOpCode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterBinaryRegValOpCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitBinaryRegValOpCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitBinaryRegValOpCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryRegValOpCodeContext binaryRegValOpCode() throws RecognitionException {
		BinaryRegValOpCodeContext _localctx = new BinaryRegValOpCodeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_binaryRegValOpCode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(LOAD);
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

	public static class StackOperationContext extends ParserRuleContext {
		public PushOperationContext pushOperation() {
			return getRuleContext(PushOperationContext.class,0);
		}
		public PopOperationContext popOperation() {
			return getRuleContext(PopOperationContext.class,0);
		}
		public CallOperationContext callOperation() {
			return getRuleContext(CallOperationContext.class,0);
		}
		public StackOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stackOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterStackOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitStackOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitStackOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StackOperationContext stackOperation() throws RecognitionException {
		StackOperationContext _localctx = new StackOperationContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_stackOperation);
		try {
			setState(170);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PUSH:
				enterOuterAlt(_localctx, 1);
				{
				setState(167);
				pushOperation();
				}
				break;
			case POP:
				enterOuterAlt(_localctx, 2);
				{
				setState(168);
				popOperation();
				}
				break;
			case CALL:
				enterOuterAlt(_localctx, 3);
				{
				setState(169);
				callOperation();
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

	public static class PushOperationContext extends ParserRuleContext {
		public TerminalNode PUSH() { return getToken(AsmHomeBrewParser.PUSH, 0); }
		public StackRegisterContext stackRegister() {
			return getRuleContext(StackRegisterContext.class,0);
		}
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public PushOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pushOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterPushOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitPushOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitPushOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PushOperationContext pushOperation() throws RecognitionException {
		PushOperationContext _localctx = new PushOperationContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_pushOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(PUSH);
			setState(173);
			stackRegister();
			setState(174);
			match(T__1);
			setState(175);
			register();
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

	public static class PopOperationContext extends ParserRuleContext {
		public TerminalNode POP() { return getToken(AsmHomeBrewParser.POP, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public StackRegisterContext stackRegister() {
			return getRuleContext(StackRegisterContext.class,0);
		}
		public PopOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_popOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterPopOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitPopOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitPopOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PopOperationContext popOperation() throws RecognitionException {
		PopOperationContext _localctx = new PopOperationContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_popOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			match(POP);
			setState(178);
			register();
			setState(179);
			match(T__1);
			setState(180);
			stackRegister();
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

	public static class CallOperationContext extends ParserRuleContext {
		public TerminalNode CALL() { return getToken(AsmHomeBrewParser.CALL, 0); }
		public StackRegisterContext stackRegister() {
			return getRuleContext(StackRegisterContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public CallOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterCallOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitCallOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitCallOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallOperationContext callOperation() throws RecognitionException {
		CallOperationContext _localctx = new CallOperationContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_callOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(CALL);
			setState(183);
			stackRegister();
			setState(184);
			match(T__1);
			setState(185);
			value();
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

	public static class StackOpcodeContext extends ParserRuleContext {
		public TerminalNode PUSH() { return getToken(AsmHomeBrewParser.PUSH, 0); }
		public TerminalNode POP() { return getToken(AsmHomeBrewParser.POP, 0); }
		public TerminalNode CALL() { return getToken(AsmHomeBrewParser.CALL, 0); }
		public StackOpcodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stackOpcode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterStackOpcode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitStackOpcode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitStackOpcode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StackOpcodeContext stackOpcode() throws RecognitionException {
		StackOpcodeContext _localctx = new StackOpcodeContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_stackOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PUSH) | (1L << POP) | (1L << CALL))) != 0)) ) {
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

	public static class TernaryOperationContext extends ParserRuleContext {
		public AluTernaryOperationContext aluTernaryOperation() {
			return getRuleContext(AluTernaryOperationContext.class,0);
		}
		public TernaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ternaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterTernaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitTernaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitTernaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TernaryOperationContext ternaryOperation() throws RecognitionException {
		TernaryOperationContext _localctx = new TernaryOperationContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_ternaryOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			aluTernaryOperation();
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

	public static class AluTernaryOperationContext extends ParserRuleContext {
		public AluTernaryOpcodeContext aluTernaryOpcode() {
			return getRuleContext(AluTernaryOpcodeContext.class,0);
		}
		public List<RegisterContext> register() {
			return getRuleContexts(RegisterContext.class);
		}
		public RegisterContext register(int i) {
			return getRuleContext(RegisterContext.class,i);
		}
		public AluTernaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aluTernaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAluTernaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAluTernaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAluTernaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AluTernaryOperationContext aluTernaryOperation() throws RecognitionException {
		AluTernaryOperationContext _localctx = new AluTernaryOperationContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_aluTernaryOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			aluTernaryOpcode();
			setState(192);
			register();
			setState(193);
			match(T__1);
			setState(194);
			register();
			setState(195);
			match(T__1);
			setState(196);
			register();
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

	public static class AluTernaryOpcodeContext extends ParserRuleContext {
		public TerminalNode ADD() { return getToken(AsmHomeBrewParser.ADD, 0); }
		public TerminalNode AND() { return getToken(AsmHomeBrewParser.AND, 0); }
		public TerminalNode OR() { return getToken(AsmHomeBrewParser.OR, 0); }
		public TerminalNode XOR() { return getToken(AsmHomeBrewParser.XOR, 0); }
		public TerminalNode CMP() { return getToken(AsmHomeBrewParser.CMP, 0); }
		public TerminalNode SUB() { return getToken(AsmHomeBrewParser.SUB, 0); }
		public AluTernaryOpcodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aluTernaryOpcode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAluTernaryOpcode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAluTernaryOpcode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAluTernaryOpcode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AluTernaryOpcodeContext aluTernaryOpcode() throws RecognitionException {
		AluTernaryOpcodeContext _localctx = new AluTernaryOpcodeContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_aluTernaryOpcode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << AND) | (1L << OR) | (1L << XOR) | (1L << CMP) | (1L << SUB))) != 0)) ) {
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

	public static class ValueContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public JsExpressionContext jsExpression() {
			return getRuleContext(JsExpressionContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_value);
		try {
			setState(203);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(200);
				label();
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(201);
				number();
				}
				break;
			case JAVASCRIPT:
				enterOuterAlt(_localctx, 3);
				{
				setState(202);
				jsExpression();
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

	public static class LabelContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
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

	public static class RegisterContext extends ParserRuleContext {
		public TerminalNode AX() { return getToken(AsmHomeBrewParser.AX, 0); }
		public TerminalNode BX() { return getToken(AsmHomeBrewParser.BX, 0); }
		public TerminalNode CX() { return getToken(AsmHomeBrewParser.CX, 0); }
		public TerminalNode DX() { return getToken(AsmHomeBrewParser.DX, 0); }
		public TerminalNode EX() { return getToken(AsmHomeBrewParser.EX, 0); }
		public TerminalNode SP() { return getToken(AsmHomeBrewParser.SP, 0); }
		public TerminalNode RP() { return getToken(AsmHomeBrewParser.RP, 0); }
		public TerminalNode PC() { return getToken(AsmHomeBrewParser.PC, 0); }
		public RegisterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_register; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterRegister(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitRegister(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitRegister(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegisterContext register() throws RecognitionException {
		RegisterContext _localctx = new RegisterContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_register);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AX) | (1L << BX) | (1L << CX) | (1L << DX) | (1L << EX) | (1L << SP) | (1L << RP) | (1L << PC))) != 0)) ) {
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

	public static class StackRegisterContext extends ParserRuleContext {
		public TerminalNode SP() { return getToken(AsmHomeBrewParser.SP, 0); }
		public TerminalNode RP() { return getToken(AsmHomeBrewParser.RP, 0); }
		public StackRegisterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stackRegister; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterStackRegister(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitStackRegister(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitStackRegister(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StackRegisterContext stackRegister() throws RecognitionException {
		StackRegisterContext _localctx = new StackRegisterContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_stackRegister);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			_la = _input.LA(1);
			if ( !(_la==SP || _la==RP) ) {
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

	public static class AssemblerDirectiveContext extends ParserRuleContext {
		public JsExpressionContext jsExpression() {
			return getRuleContext(JsExpressionContext.class,0);
		}
		public AssemblerOrgDirectiveContext assemblerOrgDirective() {
			return getRuleContext(AssemblerOrgDirectiveContext.class,0);
		}
		public AssemblerWordDeclarationContext assemblerWordDeclaration() {
			return getRuleContext(AssemblerWordDeclarationContext.class,0);
		}
		public AssemblerStringDeclarationContext assemblerStringDeclaration() {
			return getRuleContext(AssemblerStringDeclarationContext.class,0);
		}
		public AssemblerDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblerDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAssemblerDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAssemblerDirective(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAssemblerDirective(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblerDirectiveContext assemblerDirective() throws RecognitionException {
		AssemblerDirectiveContext _localctx = new AssemblerDirectiveContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_assemblerDirective);
		try {
			setState(215);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(211);
				jsExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(212);
				assemblerOrgDirective();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(213);
				assemblerWordDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(214);
				assemblerStringDeclaration();
				}
				break;
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

	public static class AssemblerOrgDirectiveContext extends ParserRuleContext {
		public TerminalNode ORG() { return getToken(AsmHomeBrewParser.ORG, 0); }
		public JsExpressionContext jsExpression() {
			return getRuleContext(JsExpressionContext.class,0);
		}
		public AssemblerOrgDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblerOrgDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAssemblerOrgDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAssemblerOrgDirective(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAssemblerOrgDirective(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblerOrgDirectiveContext assemblerOrgDirective() throws RecognitionException {
		AssemblerOrgDirectiveContext _localctx = new AssemblerOrgDirectiveContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_assemblerOrgDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			match(ORG);
			setState(218);
			jsExpression();
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

	public static class AssemblerWordDeclarationContext extends ParserRuleContext {
		public TerminalNode DW() { return getToken(AsmHomeBrewParser.DW, 0); }
		public LblContext lbl() {
			return getRuleContext(LblContext.class,0);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public AssemblerWordDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblerWordDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAssemblerWordDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAssemblerWordDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAssemblerWordDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblerWordDeclarationContext assemblerWordDeclaration() throws RecognitionException {
		AssemblerWordDeclarationContext _localctx = new AssemblerWordDeclarationContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_assemblerWordDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(220);
				lbl();
				}
			}

			setState(223);
			match(DW);
			setState(228); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(224);
					match(T__1);
					}
				}

				setState(227);
				value();
				}
				}
				setState(230); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << NAME) | (1L << NUMBER) | (1L << JAVASCRIPT))) != 0) );
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

	public static class AssemblerStringDeclarationContext extends ParserRuleContext {
		public TerminalNode DS() { return getToken(AsmHomeBrewParser.DS, 0); }
		public TerminalNode STRING() { return getToken(AsmHomeBrewParser.STRING, 0); }
		public LblContext lbl() {
			return getRuleContext(LblContext.class,0);
		}
		public AssemblerStringDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblerStringDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterAssemblerStringDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitAssemblerStringDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitAssemblerStringDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblerStringDeclarationContext assemblerStringDeclaration() throws RecognitionException {
		AssemblerStringDeclarationContext _localctx = new AssemblerStringDeclarationContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_assemblerStringDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(232);
				lbl();
				}
			}

			setState(235);
			match(DS);
			setState(236);
			match(STRING);
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

	public static class MacroContext extends ParserRuleContext {
		public MacroNameContext macroName() {
			return getRuleContext(MacroNameContext.class,0);
		}
		public List<MacroParamValueContext> macroParamValue() {
			return getRuleContexts(MacroParamValueContext.class);
		}
		public MacroParamValueContext macroParamValue(int i) {
			return getRuleContext(MacroParamValueContext.class,i);
		}
		public MacroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterMacro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitMacro(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitMacro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroContext macro() throws RecognitionException {
		MacroContext _localctx = new MacroContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_macro);
		try {
			setState(359);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(238);
				macroName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(239);
				macroName();
				setState(240);
				macroParamValue();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(242);
				macroName();
				setState(243);
				macroParamValue();
				setState(244);
				match(T__1);
				setState(245);
				macroParamValue();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(247);
				macroName();
				setState(248);
				macroParamValue();
				setState(249);
				match(T__1);
				setState(250);
				macroParamValue();
				setState(251);
				match(T__1);
				setState(252);
				macroParamValue();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(254);
				macroName();
				setState(255);
				macroParamValue();
				setState(256);
				match(T__1);
				setState(257);
				macroParamValue();
				setState(258);
				match(T__1);
				setState(259);
				macroParamValue();
				setState(260);
				match(T__1);
				setState(261);
				macroParamValue();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(263);
				macroName();
				setState(264);
				macroParamValue();
				setState(265);
				match(T__1);
				setState(266);
				macroParamValue();
				setState(267);
				match(T__1);
				setState(268);
				macroParamValue();
				setState(269);
				match(T__1);
				setState(270);
				macroParamValue();
				setState(271);
				match(T__1);
				setState(272);
				macroParamValue();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(274);
				macroName();
				setState(275);
				macroParamValue();
				setState(276);
				match(T__1);
				setState(277);
				macroParamValue();
				setState(278);
				match(T__1);
				setState(279);
				macroParamValue();
				setState(280);
				match(T__1);
				setState(281);
				macroParamValue();
				setState(282);
				match(T__1);
				setState(283);
				macroParamValue();
				setState(284);
				match(T__1);
				setState(285);
				macroParamValue();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(287);
				macroName();
				setState(288);
				macroParamValue();
				setState(289);
				match(T__1);
				setState(290);
				macroParamValue();
				setState(291);
				match(T__1);
				setState(292);
				macroParamValue();
				setState(293);
				match(T__1);
				setState(294);
				macroParamValue();
				setState(295);
				match(T__1);
				setState(296);
				macroParamValue();
				setState(297);
				match(T__1);
				setState(298);
				macroParamValue();
				setState(299);
				match(T__1);
				setState(300);
				macroParamValue();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(302);
				macroName();
				setState(303);
				macroParamValue();
				setState(304);
				match(T__1);
				setState(305);
				macroParamValue();
				setState(306);
				match(T__1);
				setState(307);
				macroParamValue();
				setState(308);
				match(T__1);
				setState(309);
				macroParamValue();
				setState(310);
				match(T__1);
				setState(311);
				macroParamValue();
				setState(312);
				match(T__1);
				setState(313);
				macroParamValue();
				setState(314);
				match(T__1);
				setState(315);
				macroParamValue();
				setState(316);
				match(T__1);
				setState(317);
				macroParamValue();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(319);
				macroName();
				setState(320);
				macroParamValue();
				setState(321);
				match(T__1);
				setState(322);
				macroParamValue();
				setState(323);
				match(T__1);
				setState(324);
				macroParamValue();
				setState(325);
				match(T__1);
				setState(326);
				macroParamValue();
				setState(327);
				match(T__1);
				setState(328);
				macroParamValue();
				setState(329);
				match(T__1);
				setState(330);
				macroParamValue();
				setState(331);
				match(T__1);
				setState(332);
				macroParamValue();
				setState(333);
				match(T__1);
				setState(334);
				macroParamValue();
				setState(335);
				match(T__1);
				setState(336);
				macroParamValue();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(338);
				macroName();
				setState(339);
				macroParamValue();
				setState(340);
				match(T__1);
				setState(341);
				macroParamValue();
				setState(342);
				match(T__1);
				setState(343);
				macroParamValue();
				setState(344);
				match(T__1);
				setState(345);
				macroParamValue();
				setState(346);
				match(T__1);
				setState(347);
				macroParamValue();
				setState(348);
				match(T__1);
				setState(349);
				macroParamValue();
				setState(350);
				match(T__1);
				setState(351);
				macroParamValue();
				setState(352);
				match(T__1);
				setState(353);
				macroParamValue();
				setState(354);
				match(T__1);
				setState(355);
				macroParamValue();
				setState(356);
				match(T__1);
				setState(357);
				macroParamValue();
				}
				break;
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

	public static class MacroParamValueContext extends ParserRuleContext {
		public AluTernaryOpcodeContext aluTernaryOpcode() {
			return getRuleContext(AluTernaryOpcodeContext.class,0);
		}
		public StackOpcodeContext stackOpcode() {
			return getRuleContext(StackOpcodeContext.class,0);
		}
		public BinaryRegValOpCodeContext binaryRegValOpCode() {
			return getRuleContext(BinaryRegValOpCodeContext.class,0);
		}
		public BinaryRegRegOpCodeContext binaryRegRegOpCode() {
			return getRuleContext(BinaryRegRegOpCodeContext.class,0);
		}
		public JumpOpcodeContext jumpOpcode() {
			return getRuleContext(JumpOpcodeContext.class,0);
		}
		public IoOpcodeContext ioOpcode() {
			return getRuleContext(IoOpcodeContext.class,0);
		}
		public OneArgOpcodeContext oneArgOpcode() {
			return getRuleContext(OneArgOpcodeContext.class,0);
		}
		public NoArgOperationContext noArgOperation() {
			return getRuleContext(NoArgOperationContext.class,0);
		}
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public MacroParamValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macroParamValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterMacroParamValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitMacroParamValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitMacroParamValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroParamValueContext macroParamValue() throws RecognitionException {
		MacroParamValueContext _localctx = new MacroParamValueContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_macroParamValue);
		try {
			setState(372);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(361);
				aluTernaryOpcode();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(362);
				stackOpcode();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(363);
				binaryRegValOpCode();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(364);
				binaryRegRegOpCode();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(365);
				jumpOpcode();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(366);
				ioOpcode();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(367);
				oneArgOpcode();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(368);
				noArgOperation();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(369);
				register();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(370);
				string();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(371);
				value();
				}
				break;
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

	public static class JsExpressionContext extends ParserRuleContext {
		public TerminalNode JAVASCRIPT() { return getToken(AsmHomeBrewParser.JAVASCRIPT, 0); }
		public JsExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterJsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitJsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitJsExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsExpressionContext jsExpression() throws RecognitionException {
		JsExpressionContext _localctx = new JsExpressionContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_jsExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(374);
			match(JAVASCRIPT);
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
		public TerminalNode NAME() { return getToken(AsmHomeBrewParser.NAME, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(376);
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

	public static class MacroNameContext extends ParserRuleContext {
		public TerminalNode MACRO_NAME() { return getToken(AsmHomeBrewParser.MACRO_NAME, 0); }
		public MacroNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macroName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterMacroName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitMacroName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitMacroName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroNameContext macroName() throws RecognitionException {
		MacroNameContext _localctx = new MacroNameContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_macroName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			match(MACRO_NAME);
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

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(AsmHomeBrewParser.NUMBER, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(380);
			match(NUMBER);
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
		public TerminalNode COMMENT() { return getToken(AsmHomeBrewParser.COMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
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

	public static class StringContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(AsmHomeBrewParser.STRING, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsmHomeBrewListener ) ((AsmHomeBrewListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsmHomeBrewVisitor ) return ((AsmHomeBrewVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			match(STRING);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\39\u0185\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\3\2\5\2Z\n\2\3\2\6\2]\n\2\r\2\16\2^\3\3\3\3\3\3\3\3\5\3e\n\3\3\3"+
		"\3\3\5\3i\n\3\5\3k\n\3\3\4\5\4n\n\4\3\4\3\4\5\4r\n\4\3\5\3\5\3\5\3\6\3"+
		"\6\3\6\3\6\5\6{\n\6\3\7\3\7\3\b\3\b\3\b\3\b\5\b\u0083\n\b\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3"+
		"\20\3\20\3\20\5\20\u009a\n\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\5\25\u00ad\n\25\3\26\3\26"+
		"\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\31"+
		"\3\31\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\35\3\35"+
		"\3\35\5\35\u00ce\n\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3!\3!\5!\u00da\n"+
		"!\3\"\3\"\3\"\3#\5#\u00e0\n#\3#\3#\5#\u00e4\n#\3#\6#\u00e7\n#\r#\16#\u00e8"+
		"\3$\5$\u00ec\n$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\5%\u016a\n%\3&\3&\3&\3&\3&"+
		"\3&\3&\3&\3&\3&\3&\5&\u0177\n&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3"+
		",\2\2-\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<"+
		">@BDFHJLNPRTV\2\13\3\2#$\3\2\30\31\5\2\6\6\26\26\30\31\3\2\32\"\6\2\5"+
		"\5\b\t\r\16\22\23\4\2\24\25\27\27\5\2\7\7\n\f\17\20\3\2(/\3\2-.\2\u0189"+
		"\2\\\3\2\2\2\4j\3\2\2\2\6m\3\2\2\2\bs\3\2\2\2\nz\3\2\2\2\f|\3\2\2\2\16"+
		"\u0082\3\2\2\2\20\u0084\3\2\2\2\22\u0087\3\2\2\2\24\u008a\3\2\2\2\26\u008d"+
		"\3\2\2\2\30\u008f\3\2\2\2\32\u0091\3\2\2\2\34\u0094\3\2\2\2\36\u0099\3"+
		"\2\2\2 \u009b\3\2\2\2\"\u00a0\3\2\2\2$\u00a2\3\2\2\2&\u00a7\3\2\2\2(\u00ac"+
		"\3\2\2\2*\u00ae\3\2\2\2,\u00b3\3\2\2\2.\u00b8\3\2\2\2\60\u00bd\3\2\2\2"+
		"\62\u00bf\3\2\2\2\64\u00c1\3\2\2\2\66\u00c8\3\2\2\28\u00cd\3\2\2\2:\u00cf"+
		"\3\2\2\2<\u00d1\3\2\2\2>\u00d3\3\2\2\2@\u00d9\3\2\2\2B\u00db\3\2\2\2D"+
		"\u00df\3\2\2\2F\u00eb\3\2\2\2H\u0169\3\2\2\2J\u0176\3\2\2\2L\u0178\3\2"+
		"\2\2N\u017a\3\2\2\2P\u017c\3\2\2\2R\u017e\3\2\2\2T\u0180\3\2\2\2V\u0182"+
		"\3\2\2\2XZ\5\4\3\2YX\3\2\2\2YZ\3\2\2\2Z[\3\2\2\2[]\78\2\2\\Y\3\2\2\2]"+
		"^\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_\3\3\2\2\2`k\5T+\2ak\5\6\4\2bd\5@!\2ce"+
		"\5T+\2dc\3\2\2\2de\3\2\2\2ek\3\2\2\2fh\5H%\2gi\5T+\2hg\3\2\2\2hi\3\2\2"+
		"\2ik\3\2\2\2j`\3\2\2\2ja\3\2\2\2jb\3\2\2\2jf\3\2\2\2k\5\3\2\2\2ln\5\b"+
		"\5\2ml\3\2\2\2mn\3\2\2\2no\3\2\2\2oq\5\n\6\2pr\5T+\2qp\3\2\2\2qr\3\2\2"+
		"\2r\7\3\2\2\2st\5:\36\2tu\7\3\2\2u\t\3\2\2\2v{\5\f\7\2w{\5\16\b\2x{\5"+
		"\36\20\2y{\5\62\32\2zv\3\2\2\2zw\3\2\2\2zx\3\2\2\2zy\3\2\2\2{\13\3\2\2"+
		"\2|}\t\2\2\2}\r\3\2\2\2~\u0083\5\24\13\2\177\u0083\5\20\t\2\u0080\u0083"+
		"\5\32\16\2\u0081\u0083\5\22\n\2\u0082~\3\2\2\2\u0082\177\3\2\2\2\u0082"+
		"\u0080\3\2\2\2\u0082\u0081\3\2\2\2\u0083\17\3\2\2\2\u0084\u0085\7\26\2"+
		"\2\u0085\u0086\5> \2\u0086\21\3\2\2\2\u0087\u0088\7\6\2\2\u0088\u0089"+
		"\5<\37\2\u0089\23\3\2\2\2\u008a\u008b\5\26\f\2\u008b\u008c\5<\37\2\u008c"+
		"\25\3\2\2\2\u008d\u008e\t\3\2\2\u008e\27\3\2\2\2\u008f\u0090\t\4\2\2\u0090"+
		"\31\3\2\2\2\u0091\u0092\5\34\17\2\u0092\u0093\58\35\2\u0093\33\3\2\2\2"+
		"\u0094\u0095\t\5\2\2\u0095\35\3\2\2\2\u0096\u009a\5 \21\2\u0097\u009a"+
		"\5$\23\2\u0098\u009a\5(\25\2\u0099\u0096\3\2\2\2\u0099\u0097\3\2\2\2\u0099"+
		"\u0098\3\2\2\2\u009a\37\3\2\2\2\u009b\u009c\5\"\22\2\u009c\u009d\5<\37"+
		"\2\u009d\u009e\7\4\2\2\u009e\u009f\5<\37\2\u009f!\3\2\2\2\u00a0\u00a1"+
		"\t\6\2\2\u00a1#\3\2\2\2\u00a2\u00a3\5&\24\2\u00a3\u00a4\5<\37\2\u00a4"+
		"\u00a5\7\4\2\2\u00a5\u00a6\58\35\2\u00a6%\3\2\2\2\u00a7\u00a8\7\21\2\2"+
		"\u00a8\'\3\2\2\2\u00a9\u00ad\5*\26\2\u00aa\u00ad\5,\27\2\u00ab\u00ad\5"+
		".\30\2\u00ac\u00a9\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac\u00ab\3\2\2\2\u00ad"+
		")\3\2\2\2\u00ae\u00af\7\24\2\2\u00af\u00b0\5> \2\u00b0\u00b1\7\4\2\2\u00b1"+
		"\u00b2\5<\37\2\u00b2+\3\2\2\2\u00b3\u00b4\7\25\2\2\u00b4\u00b5\5<\37\2"+
		"\u00b5\u00b6\7\4\2\2\u00b6\u00b7\5> \2\u00b7-\3\2\2\2\u00b8\u00b9\7\27"+
		"\2\2\u00b9\u00ba\5> \2\u00ba\u00bb\7\4\2\2\u00bb\u00bc\58\35\2\u00bc/"+
		"\3\2\2\2\u00bd\u00be\t\7\2\2\u00be\61\3\2\2\2\u00bf\u00c0\5\64\33\2\u00c0"+
		"\63\3\2\2\2\u00c1\u00c2\5\66\34\2\u00c2\u00c3\5<\37\2\u00c3\u00c4\7\4"+
		"\2\2\u00c4\u00c5\5<\37\2\u00c5\u00c6\7\4\2\2\u00c6\u00c7\5<\37\2\u00c7"+
		"\65\3\2\2\2\u00c8\u00c9\t\b\2\2\u00c9\67\3\2\2\2\u00ca\u00ce\5:\36\2\u00cb"+
		"\u00ce\5R*\2\u00cc\u00ce\5L\'\2\u00cd\u00ca\3\2\2\2\u00cd\u00cb\3\2\2"+
		"\2\u00cd\u00cc\3\2\2\2\u00ce9\3\2\2\2\u00cf\u00d0\5N(\2\u00d0;\3\2\2\2"+
		"\u00d1\u00d2\t\t\2\2\u00d2=\3\2\2\2\u00d3\u00d4\t\n\2\2\u00d4?\3\2\2\2"+
		"\u00d5\u00da\5L\'\2\u00d6\u00da\5B\"\2\u00d7\u00da\5D#\2\u00d8\u00da\5"+
		"F$\2\u00d9\u00d5\3\2\2\2\u00d9\u00d6\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9"+
		"\u00d8\3\2\2\2\u00daA\3\2\2\2\u00db\u00dc\7%\2\2\u00dc\u00dd\5L\'\2\u00dd"+
		"C\3\2\2\2\u00de\u00e0\5\b\5\2\u00df\u00de\3\2\2\2\u00df\u00e0\3\2\2\2"+
		"\u00e0\u00e1\3\2\2\2\u00e1\u00e6\7&\2\2\u00e2\u00e4\7\4\2\2\u00e3\u00e2"+
		"\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e7\58\35\2\u00e6"+
		"\u00e3\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8\u00e9\3\2"+
		"\2\2\u00e9E\3\2\2\2\u00ea\u00ec\5\b\5\2\u00eb\u00ea\3\2\2\2\u00eb\u00ec"+
		"\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ee\7\'\2\2\u00ee\u00ef\7\66\2\2"+
		"\u00efG\3\2\2\2\u00f0\u016a\5P)\2\u00f1\u00f2\5P)\2\u00f2\u00f3\5J&\2"+
		"\u00f3\u016a\3\2\2\2\u00f4\u00f5\5P)\2\u00f5\u00f6\5J&\2\u00f6\u00f7\7"+
		"\4\2\2\u00f7\u00f8\5J&\2\u00f8\u016a\3\2\2\2\u00f9\u00fa\5P)\2\u00fa\u00fb"+
		"\5J&\2\u00fb\u00fc\7\4\2\2\u00fc\u00fd\5J&\2\u00fd\u00fe\7\4\2\2\u00fe"+
		"\u00ff\5J&\2\u00ff\u016a\3\2\2\2\u0100\u0101\5P)\2\u0101\u0102\5J&\2\u0102"+
		"\u0103\7\4\2\2\u0103\u0104\5J&\2\u0104\u0105\7\4\2\2\u0105\u0106\5J&\2"+
		"\u0106\u0107\7\4\2\2\u0107\u0108\5J&\2\u0108\u016a\3\2\2\2\u0109\u010a"+
		"\5P)\2\u010a\u010b\5J&\2\u010b\u010c\7\4\2\2\u010c\u010d\5J&\2\u010d\u010e"+
		"\7\4\2\2\u010e\u010f\5J&\2\u010f\u0110\7\4\2\2\u0110\u0111\5J&\2\u0111"+
		"\u0112\7\4\2\2\u0112\u0113\5J&\2\u0113\u016a\3\2\2\2\u0114\u0115\5P)\2"+
		"\u0115\u0116\5J&\2\u0116\u0117\7\4\2\2\u0117\u0118\5J&\2\u0118\u0119\7"+
		"\4\2\2\u0119\u011a\5J&\2\u011a\u011b\7\4\2\2\u011b\u011c\5J&\2\u011c\u011d"+
		"\7\4\2\2\u011d\u011e\5J&\2\u011e\u011f\7\4\2\2\u011f\u0120\5J&\2\u0120"+
		"\u016a\3\2\2\2\u0121\u0122\5P)\2\u0122\u0123\5J&\2\u0123\u0124\7\4\2\2"+
		"\u0124\u0125\5J&\2\u0125\u0126\7\4\2\2\u0126\u0127\5J&\2\u0127\u0128\7"+
		"\4\2\2\u0128\u0129\5J&\2\u0129\u012a\7\4\2\2\u012a\u012b\5J&\2\u012b\u012c"+
		"\7\4\2\2\u012c\u012d\5J&\2\u012d\u012e\7\4\2\2\u012e\u012f\5J&\2\u012f"+
		"\u016a\3\2\2\2\u0130\u0131\5P)\2\u0131\u0132\5J&\2\u0132\u0133\7\4\2\2"+
		"\u0133\u0134\5J&\2\u0134\u0135\7\4\2\2\u0135\u0136\5J&\2\u0136\u0137\7"+
		"\4\2\2\u0137\u0138\5J&\2\u0138\u0139\7\4\2\2\u0139\u013a\5J&\2\u013a\u013b"+
		"\7\4\2\2\u013b\u013c\5J&\2\u013c\u013d\7\4\2\2\u013d\u013e\5J&\2\u013e"+
		"\u013f\7\4\2\2\u013f\u0140\5J&\2\u0140\u016a\3\2\2\2\u0141\u0142\5P)\2"+
		"\u0142\u0143\5J&\2\u0143\u0144\7\4\2\2\u0144\u0145\5J&\2\u0145\u0146\7"+
		"\4\2\2\u0146\u0147\5J&\2\u0147\u0148\7\4\2\2\u0148\u0149\5J&\2\u0149\u014a"+
		"\7\4\2\2\u014a\u014b\5J&\2\u014b\u014c\7\4\2\2\u014c\u014d\5J&\2\u014d"+
		"\u014e\7\4\2\2\u014e\u014f\5J&\2\u014f\u0150\7\4\2\2\u0150\u0151\5J&\2"+
		"\u0151\u0152\7\4\2\2\u0152\u0153\5J&\2\u0153\u016a\3\2\2\2\u0154\u0155"+
		"\5P)\2\u0155\u0156\5J&\2\u0156\u0157\7\4\2\2\u0157\u0158\5J&\2\u0158\u0159"+
		"\7\4\2\2\u0159\u015a\5J&\2\u015a\u015b\7\4\2\2\u015b\u015c\5J&\2\u015c"+
		"\u015d\7\4\2\2\u015d\u015e\5J&\2\u015e\u015f\7\4\2\2\u015f\u0160\5J&\2"+
		"\u0160\u0161\7\4\2\2\u0161\u0162\5J&\2\u0162\u0163\7\4\2\2\u0163\u0164"+
		"\5J&\2\u0164\u0165\7\4\2\2\u0165\u0166\5J&\2\u0166\u0167\7\4\2\2\u0167"+
		"\u0168\5J&\2\u0168\u016a\3\2\2\2\u0169\u00f0\3\2\2\2\u0169\u00f1\3\2\2"+
		"\2\u0169\u00f4\3\2\2\2\u0169\u00f9\3\2\2\2\u0169\u0100\3\2\2\2\u0169\u0109"+
		"\3\2\2\2\u0169\u0114\3\2\2\2\u0169\u0121\3\2\2\2\u0169\u0130\3\2\2\2\u0169"+
		"\u0141\3\2\2\2\u0169\u0154\3\2\2\2\u016aI\3\2\2\2\u016b\u0177\5\66\34"+
		"\2\u016c\u0177\5\60\31\2\u016d\u0177\5&\24\2\u016e\u0177\5\"\22\2\u016f"+
		"\u0177\5\34\17\2\u0170\u0177\5\26\f\2\u0171\u0177\5\30\r\2\u0172\u0177"+
		"\5\f\7\2\u0173\u0177\5<\37\2\u0174\u0177\5V,\2\u0175\u0177\58\35\2\u0176"+
		"\u016b\3\2\2\2\u0176\u016c\3\2\2\2\u0176\u016d\3\2\2\2\u0176\u016e\3\2"+
		"\2\2\u0176\u016f\3\2\2\2\u0176\u0170\3\2\2\2\u0176\u0171\3\2\2\2\u0176"+
		"\u0172\3\2\2\2\u0176\u0173\3\2\2\2\u0176\u0174\3\2\2\2\u0176\u0175\3\2"+
		"\2\2\u0177K\3\2\2\2\u0178\u0179\7\67\2\2\u0179M\3\2\2\2\u017a\u017b\7"+
		"\60\2\2\u017bO\3\2\2\2\u017c\u017d\7\61\2\2\u017dQ\3\2\2\2\u017e\u017f"+
		"\7\62\2\2\u017fS\3\2\2\2\u0180\u0181\7\65\2\2\u0181U\3\2\2\2\u0182\u0183"+
		"\7\66\2\2\u0183W\3\2\2\2\25Y^dhjmqz\u0082\u0099\u00ac\u00cd\u00d9\u00df"+
		"\u00e3\u00e8\u00eb\u0169\u0176";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}