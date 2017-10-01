; The following is essentially a direct copy of eForth assembler constants/variables without deeper understanding
;; Version control

{VER = 0x01}			;major release version
{EXT = 0x01}			;minor extension

;; Constants

{COMPO = 0x040}			;lexicon compile only bit
{IMEDD = 0x080}			;lexicon immediate bit
{MASKK = 0x07F1F}		;lexicon bit mask

{CELLL = 1}			    ;size of a cell - CHANGED FROM 2 to 1 since we only work with 16 bit words
{BASEE = 10}			;default radix
{VOCSS = 8}			    ;depth of vocabulary stack

{BKSPP = 8} 			;backspace
{LF = 10}   			;line feed
{CRR = 13}	    		;carriage return
{ERR = 27}  			;error escape
{TIC = 39}			    ;tick AKA APOSTROPHE

;{CALLL = 0x0E890}		;NOP CALL opcodes (NEED TO UNDERSTAND THIS? WHAT DOES IT DO?)
                        ; My guess => CALL to the mem location of doLIST
                        ; This is raw, binary machine code (opcode. E8 is CALL rel16. 90 is relative offset added to EIP reg
                        ; I think EIP is the instruction pointer. So relative offset from current postion

;; Memory allocation	0//code>--//--<name//up>--<sp//tib>--rp//em

{EM	= 64000}	    		;top of memory. The real end of memory is 65536, but this gives a bit of wiggle room in case we mess up the return stack.
{COLDD = 0}			        ;cold start vector - changed from 100 to 0 since we start at 0, unlike DOS

{US = 1024*CELLL}     		;user area size in cells
{RTS = 16384*CELLL}	    	;return stack/TIB size
{DTS = 16384*CELLL}         ;data stack size

{RPP = EM-8*CELLL}	    	;start of return stack (RP0)
{TIBB = RPP-RTS}			;terminal input buffer (TIB)
{SPP = TIBB-8*CELLL}		;start of data stack (SP0)
{UPP = SPP-DTS*CELLL}		;start of user area (UP0)
{NAMEE = UPP-8*CELLL}		;name dictionary
{CODEE = COLDD+US}  		;code dictionary

;; Initialize assembly variables

{_LINK	= 0}					;force a null link
{_NAME	= NAMEE}					;initialize name pointer
{_CODE	= CODEE	}				;initialize code pointer
{_USER	= 4*CELLL}				;first user variable offset



; _NAME needs to grow "downwards" (towards zero). _CODE needs to grow upwards (away from zero to bigger numbers)

MACRO $CODE	@LEX,@NAME,@LABEL           ;	Compile a code definition header.
@LABEL:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (@LEX + 3)}	    ;; new header. We need to move it in front of @NAME's length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {@LEX}                           ;; Name length
	DS @NAME			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
ENDM



MACRO $COLON	@LEX,@NAME,@LABEL ;	Compile a colon definition header.
	$CODE	@LEX,@NAME,@LABEL
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
ENDM



MACRO $USER	@LEX,@NAME,@LABEL ;	Compile a user variable header.
	$CODE	@LEX,@NAME,@LABEL
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset
ENDM



;; Originally named D$
MACRO $D_STR	@FUNCT,@STRNG       ;Compile an inline string.
	DW	@FUNCT, {@STRNG.length}		;;Write the function label and the length of the string
	DS  @STRNG                      ; Write the string
ENDM

;; Same as $D_STR but with a label
MACRO $D_STRL	@LABEL,@FUNCT,@STRNG       ;Compile an inline string.
	@LABEL: DW	@FUNCT, {@STRNG.length}		;;Write the function label and the length of the string
	DS  @STRNG                      ; Write the string
ENDM


; The function of $NEXT is to fetch the next word pointed to by the Interpreter Pointer IP,
; increment IP to point to the next word in the word list, and jump to the address just fetched.
; Since a word address points to a code field containing executable machine instructions,
; executing a word means jumping directly to the code field pointed to by the word address.
; $NEXT thus allows the virtual Forth computer to execute a list of words with very little CPU overhead.

MACRO $NEXT         ; Assemble inline direct threaded code ending.
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)
ENDM


ORG	{CODEE}					;start code dictionary

;   BYE		( -- )
;		Exit eForth.

		$CODE	3,"BYE",BYE
		HALT			;Terminate execution

;; ?RX provides the functions required of both KEY and KEY? which accept input from a terminal.
;; ?RX inspects the terminal device and returns a character and a true flag if the character has been received and is waiting to be retrieved.
;; If no character was received, ?RX simply returns a false flag.
;; With ?RX, both KEY and KEY? can be defined as high level colon definitions.

;   ?RX		( -- c T | F )
;		Return input character and true, or a false if no input.

		$CODE	3,"?RX",QRX
		LOAD BX , 0         ; Start with an initial 0 value in BX.
		TIN                 ; Check if there's a character to be read
		JZ QRX1             ; Jump if we didn't get a character
		WRDIN BX            ; Get the input character into BX
		PUSH SP, BX         ; Push character (in BX) onto data stack (SP)
	    LOAD BX, -1         ; Load true flag (any non-zero value, in this case -1) into BX
QRX1:	PUSH SP, BX         ; BX contains our flag (true or false depending on if character was read in), so we push it onto the data stack (SP)
		$NEXT               ; All done, let's continue.


;; TX! sends a character on the data stack to the terminal device.

;   TX!		( c -- )
;		Send character c to the output device.

		$CODE	3,"TX!",TXSTO
		POP	BX, SP			; Pop the top of data stack (SP) into BX.
		LOAD CX, -1         ; Load -1 into CX to prep for comparison
		CMP CX, BX, CX      ; Compare BX and CX
		JNZ TX1             ; If we find that BX != CX (i.e. BX != -1) JMP to outputting the character
		LOAD BX, 32         ; Change -1 to blank character (32). This is what original eForth did, but that may have been DOS-specific.
TX1:	WRDOUT BX           ; Send character to output.
		$NEXT               ; Done


;   !IO		( -- )
;		Initialize the serial I/O devices.

		$CODE	3,"!IO",STOIO
		$NEXT                   ;Our I/O device doesn't need any special setup. Continue

;; The kernel


;; doLIT pushes the next word onto the data stack as an integer literal instead of as an addresses to be executed by $NEXT.
;; It allows numbers to be compiled as in-line literals, supplying data to the data stack at run time.
;; doLIT is not used by itself, but rather compiled by LITERAL which inserts doLIT and its associated integer into the address list under construction.
;; Anytime you see a number in a colon definition, LITERAL is invoked to compile an integer literal with doLIT.

;   doLIT	( -- w )
;		Push an inline literal.

		$CODE	(COMPO+5),"doLIT",DOLIT
		FETCH AX , EX   ; Fetch next word (the literal) pointed to by IP (EX), into WP (AX)
        INC EX , EX     ; Increment IP (EX) to point to next word in word list
		PUSH SP, AX     ; Push the inline literal (in AX) onto the data stack (SP)
		$NEXT


;   doLIST	( a -- )
;		Process colon list.

		$CODE	(COMPO+6),"doLIST",DOLST
		PUSH RP, EX     ; Save IP (EX) onto return stack (RP)
		POP	EX, SP		; Copies the address of the first entry in its address list into IP (EX)
		$NEXT           ; $NEXT will then start executing this list of addresses in sequence

;   EXIT	( -- )
;		Terminate a colon definition.

		$CODE	4,"EXIT",EXIT
		POP EX, RP      ; Pops the top item on the return stack (RP) into the IP (EX) register
		$NEXT           ; continues the processing of the word list, briefly interrupted by the last colon word in this word list

;; EXECUTE takes the execution address from the data stack and executes that word.
;; This powerful word allows the user to execute any word which is not a part of an address list.

;   EXECUTE	( ca -- )
;		Execute the word at ca.

		$CODE	7,"EXECUTE",EXECU
		POP	BX, SP      ; Get the code address (ca) off the data stack and into BX.
		MOV PC,	BX			; Jump to the code address


;; Loops and Branches

;   next	( -- )
;		Run time code for the single index loop.
;		: next ( -- ) \ hilevel model
;		  r> r> dup if 1 - >r @ >r exit then drop cell+ >r ;

		$CODE	(COMPO+4),"next",DONXT
		POP BX, RP          ; Get index at return stack (RP) TOS
		DEC BX, BX          ; Decrement the index
		PUSH RP, BX         ; Push index back onto stack
		JC	NEXT1			; Did we decrement the index (BX) below zero?
		FETCH	EX,EX		; no, continue loop. Load IP (EX) with value pointed to by IP (EX). IP:=(IP)
		$NEXT               ; Continue.
NEXT1:	INC	RP,RP   		;yes, pop (drop) the index by simply incrementing the return stack (RP)
		INC	EX,EX		    ;exit loop by moving IP (EX) to next word.
		$NEXT

;; Conditional branch
;   ?branch	( f -- )
;		Branch if flag is zero.

		$CODE	(COMPO+7),"?branch",QBRAN
		POP	BX, SP			; pop flag off of data stack (SP) into BX
		OR	BX, BX, BX		; OR the value with itself so we can test if it's zero
		JZ	BRAN1			; Test if it's zero
		INC EX, EX  		; No. Not zero. Point IP (EX) to next cell by incrementing it.
		$NEXT
BRAN1:	    FETCH EX,EX	    ; Yes, it's zero. Load IP (EX) with value pointed to by IP (EX). IP:=(IP)
		$NEXT

;; Unconditional branch
;   branch	( -- )
;		Branch to an inline address.

		$CODE	(COMPO+6),"branch",BRAN
		FETCH EX,EX		; Branch unconditionally. Load IP (EX) with value pointed to by IP (EX). IP:=(IP)
		$NEXT

;; Memory access

;   !		( w a -- )
;		Pop the data stack to memory.

		$CODE	1,"!",STOR
		POP	BX, SP      ; Get address (a) from TOS data stack (SP), and store into BX
		POP CX, SP      ; Get word (w) from TOS data stack (SP), and store into CX
		STORE BX, CX    ; Store w (in CX) into address a (in BX).
		$NEXT

;   @		( a -- w )
;		Push memory location to the data stack.

		$CODE	1,"@",AT
		POP	BX, SP          ; Get address (a) from TOS data stack (SP), and store into BX
		FETCH CX, BX        ; Get the value (w) at address (a) and store into CX
		PUSH  SP, CX        ; Push the value (w) (in CX) onto data stack (SP)
		$NEXT

;; Return stack


;; RP@ pushes the contents of the return stack pointer RP on the data stack. Used very rarely in applications.

;   RP@		( -- a )
;		Push the current RP to the data stack.

		$CODE	3,"RP@",RPAT
		PUSH	SP, RP          ;Push the contents of the return stack pointer (RP) onto the data stack (SP)
		$NEXT

;; RP! pushes the address on the top of the data stack to the return stack and thus initializes the return stack.
;; RP! is only used to initialize the system and are seldom used in applications

;   RP!		( a -- )
;		Set the return stack pointer.

		$CODE	(COMPO+3),"RP!",RPSTO
		POP	RP, SP                  ; Pop the TOS of the data stack (SP) into the return stack pointer reg (RP)
		$NEXT

;; >R pops a word off the return stack and pushes it on the data stack.

;   R>		( -- w )
;		Pop the return stack to the data stack.

		$CODE	2,"R>",RFROM
		POP BX, RP          ; Pop TOS of return stack (RP) into temp (BX)
		PUSH SP, BX         ; Push value (BX) onto data stack (SP)
		$NEXT

;; >R pops a number off the data stack and pushes it on the return stack

;   >R		( w -- )
;		Push the data stack to the return stack.

		$CODE	(COMPO+2),">R",TOR
		POP BX, SP          ; Pop TOS of data stack (SP) into temp (BX)
        PUSH RP, BX         ; Push value (BX) onto return stack (RP)
		$NEXT

;; R@ copies the top item on the return stack and pushes it on the data stack.

;   R@		( -- w )
;		Copy top of return stack to the data stack.

		$CODE	2,"R@",RAT
		FETCH BX, RP          ; Copy (not pop) TOS of return stack (RP) into temp (BX)
        PUSH SP, BX         ; Push value (BX) onto data stack (SP)
		$NEXT

;; Data stack

;; Data stack is initialized by SP!

;   SP!		( a -- )
;		Set the data stack pointer.

		$CODE	3,"SP!",SPSTO
		POP	SP, SP  ;Set the data stack pointer (SP) to the TOS data stack value (a). This is kinda scary to me.
		$NEXT

;; The depth of data stack can be examined by SP@

;   SP@		( -- a )
;		Push the current data stack pointer.

		$CODE	3,"SP@",SPAT
		PUSH SP, SP			; Push the value of the data stack pointer (SP) onto the data stack (SP). Again, kinda scary.
		$NEXT

;   DROP	( w -- )
;		Discard top stack item.

		$CODE	4,"DROP",DROP
		INC	SP,SP		;Increment the stack pointer (SP) past the current TOS to effectively drop it.
		$NEXT

;   DUP		( w -- w w )
;		Duplicate the top stack item.

		$CODE	3,"DUP",DUPP
		FETCH BX,SP			;Copy the TOS of the data stack in temp (BX), without popping
		PUSH  SP, BX        ; Push the duplicate onto the TOS of the data stack (SP)
		$NEXT

;   SWAP	( w1 w2 -- w2 w1 )
;		Exchange top two stack items.

		$CODE	4,"SWAP",SWAP
		POP	BX, SP          ; Pop TOS (w2) data stack (SP) into BX
		POP	CX, SP          ; Pop new TOS (w1) data stack (SP) into CX
		PUSH SP, BX         ; Push w2 (BX) onto data stack (SP)
		PUSH SP, CX         ; Push w1 (CX) onto data stack (SP)
		$NEXT

;   OVER	( w1 w2 -- w1 w2 w1 )
;		Copy second stack item to top.

		$CODE	4,"OVER",OVER
		POP	BX, SP          ; Pop TOS (w2) data stack (SP) into BX
        FETCH CX, SP        ; Copy new TOS (w1) data stack (SP) into CX
        PUSH SP, BX         ; Push w2 (BX) onto data stack (SP)
        PUSH SP, CX         ; Push w1 (CX) onto data stack (SP)
		$NEXT

;;Logical Words


;; The only primitive word which generates flags is '0<', which examines the top item on the data stack for its negativeness.
;; If it is negative, '0<' will return a -1 for true. If it is 0 or positive, '0<' will return a 0 for false.

;   0<		( n -- t )
;		Return true if n is negative.

		$CODE	2,"0<",ZLESS
		POP	AX, SP          ; Pop value (n) at TOS data stack (SP) into AX
		OR AX, AX, AX       ; Or value with itself to check the sign
		JS ZLESS1         ; Test if it's negative
		LOAD BX,0           ; It is not negative (i.e. AX is 0 or positive) Load 0 into BX
		JMP ZLESS2          ; JMP to pushing onto the data stack
ZLESS1: LOAD BX, -1         ; It is negative, load -1 into BX
ZLESS2:	PUSH SP, BX         ; Push BX value (either -1 or 0) onto data stack (SP)
		$NEXT


;   AND		( w w -- w )
;		Bitwise AND.

		$CODE	3,"AND",ANDD
		POP	BX, SP          ;Pop TOS of data stack into BX
		POP	AX, SP          ;Pop next TOS of data stack into AX
		AND	BX, BX,AX       ; AND the elements, store result into BX
		PUSH SP,	BX          ; Push result of logical AND onto data stack
		$NEXT

;   OR		( w w -- w )
;		Bitwise inclusive OR.

		$CODE	2,"OR",ORR
		POP	BX, SP          ;Pop TOS of data stack into BX
        POP	AX, SP          ;Pop next TOS of data stack into AX
        OR	BX, BX,AX       ; OR the elements, store result into BX
        PUSH SP,	BX          ; Push result of logical OR onto data stack
		$NEXT

;   XOR		( w w -- w )
;		Bitwise exclusive OR.

		$CODE	3,"XOR",XORR
		POP	BX, SP          ;Pop TOS of data stack into BX
        POP	AX, SP          ;Pop next TOS of data stack into AX
        XOR	BX, BX,AX       ; XOR the elements, store result into BX
        PUSH SP,	BX          ; Push result of logical XOR onto data stack
		$NEXT

;; Primitive Arithmetic

;; UM+ adds two unsigned number on the top of the data stack and returns to the data stack the sum of these
;; two numbers and the carry as one number on top of the sum.

;   UM+		( w w -- w cy )
;		Add two numbers, return the sum and carry flag.

		$CODE	3,"UM+",UPLUS
		POP	BX, SP          ;Pop TOS of data stack into BX
        POP	AX, SP          ;Pop next TOS of data stack into AX
        ADD	AX, BX, AX      ; ADD the elements, store result into AX
        JNC UPLUS1          ; Test if we carried or not
        LOAD CX,1           ; The addition carried (i.e. CF is 1) Load 1 into CX
        JMP UPLUS2          ; JMP to pushing onto the data stack
        UPLUS1: LOAD CX, 0  ; It did not carry, load 0 into BX
        UPLUS2:	PUSH SP, AX ; Push AX value (the sum) onto data stack (SP)
        PUSH SP, CX         ; Push CX value (the carry flag - either 1 or 0) onto data stack (SP)
        $NEXT


;; System and user variables

;   doVAR	( -- a )
;		Run time routine for VARIABLE and CREATE.

		$COLON	(COMPO+5),"doVAR",DOVAR
		DW	RFROM,EXIT

;   UP		( -- a )
;		Pointer to the user area.

		$COLON	2,"UP",UP
		DW	DOVAR
		DW	{UPP}

;   doUSER	( -- a )
;		Run time routine for user variables.

		$COLON	(COMPO+6),"doUSER",DOUSE
		DW	RFROM,AT            ;Gets offset from the return stack
		DW  UP,AT               ;Loads UP (start of user area) from mem
		DW  PLUS,EXIT           ;Adds both and returns address of requested user variable.

;   SP0		( -- a )
;		Pointer to bottom of the data stack.

		$USER	3,"SP0",SZERO

;   RP0		( -- a )
;		Pointer to bottom of the return stack.

		$USER	3,"RP0",RZERO

;   '?KEY	( -- a )
;		Execution vector of ?KEY.

		$USER	5,"'?KEY",TQKEY

;   'EMIT	( -- a )
;		Execution vector of EMIT.

		$USER	5,"'EMIT",TEMIT

;   'EXPECT	( -- a )
;		Execution vector of EXPECT.

		$USER	7,"'EXPECT",TEXPE

;   'TAP	( -- a )
;		Execution vector of TAP.

		$USER	4,"'TAP",TTAP

;   'ECHO	( -- a )
;		Execution vector of ECHO.

		$USER	5,"'ECHO",TECHO

;   'PROMPT	( -- a )
;		Execution vector of PROMPT.

		$USER	7,"'PROMPT",TPROM

;   BASE	( -- a )
;		Storage of the radix base for numeric I/O.

		$USER	4,"BASE",BASE

;   tmp		( -- a )
;		A temporary storage location used in parse and find.

		$USER	(COMPO+3),"tmp",TEMP

;   SPAN	( -- a )
;		Hold character count received by EXPECT.

		$USER	4,"SPAN",SPAN

;   >IN		( -- a )
;		Hold the character pointer while parsing input STORam.

		$USER	3,">IN",INN

;   #TIB	( -- a )
;		Hold the current count and address of the terminal input buffer.

		$USER	4,"#TIB",NTIB
		{_USER = _USER+CELLL}

;   CSP		( -- a )
;		Hold the stack pointer for error checking.

		$USER	3,"CSP",CSP

;   'EVAL	( -- a )
;		Execution vector of EVAL.

		$USER	5,"'EVAL",TEVAL

;   'NUMBER	( -- a )
;		Execution vector of NUMBER?.

		$USER	7,"'NUMBER",TNUMB

;   HLD		( -- a )
;		Hold a pointer in building a numeric output string.

		$USER	3,"HLD",HLD

;   HANDLER	( -- a )
;		Hold the return stack pointer for error handling.

		$USER	7,"HANDLER",HANDL

;   CONTEXT	( -- a )
;		A area to specify vocabulary search order.

		$USER	7,"CONTEXT",CNTXT
		{_USER = _USER+VOCSS*CELLL}	;vocabulary stack

;   CURRENT	( -- a )
;		Point to the vocabulary to be extended.

		$USER	7,"CURRENT",CRRNT
		{_USER = _USER+CELLL}		;vocabulary link pointer

;   CP		( -- a )
;		Point to the top of the code dictionary.

		$USER	2,"CP",CP

;   NP		( -- a )
;		Point to the bottom of the name dictionary.

		$USER	2,"NP",NP

;   LAST	( -- a )
;		Point to the last name in the name dictionary.

		$USER	4,"LAST",LAST

;; Common functions

;   doVOC	( -- )
;		Run time action of VOCABULARY's.

		$COLON	(COMPO+5),"doVOC",DOVOC
		DW	RFROM,CNTXT,STOR,EXIT

;   FORTH	( -- )
;		Make FORTH the context vocabulary.

		$COLON	5,"FORTH",FORTH
		DW	DOVOC
		DW	0			;vocabulary head pointer
		DW	0			;vocabulary link pointer

;   ?DUP	( w -- w w | 0 )
;		Dup tos if its is not zero.

		$COLON	4,"?DUP",QDUP
		DW	DUPP
		DW	QBRAN,QDUP1
		DW	DUPP
QDUP1:		DW	EXIT

;   ROT		( w1 w2 w3 -- w2 w3 w1 )
;		Rot 3rd item to top.

		$COLON	3,"ROT",ROT
		DW	TOR,SWAP,RFROM,SWAP,EXIT

;   2DROP	( w w -- )
;		Discard two items on stack.

		$COLON	5,"2DROP",DDROP
		DW	DROP,DROP,EXIT

;   2DUP	( w1 w2 -- w1 w2 w1 w2 )
;		Duplicate top two items.

		$COLON	4,"2DUP",DDUP
		DW	OVER,OVER,EXIT

;   +		( w w -- sum )
;		Add top two items.

		$COLON	1,"+",PLUS
		DW	UPLUS,DROP,EXIT

;   D+		( d d -- d )
;		Double addition, as an example using UM+.
;
;		$COLON	2,"D+",DPLUS
;		DW	TOR,SWAP,TOR,UPLUS
;		DW	RFROM,RFROM,PLUS,PLUS,EXIT

;   NOT		( w -- w )
;		One's complement of tos.

		$COLON	3,"NOT",INVER
		DW	DOLIT,-1,XORR,EXIT

;   NEGATE	( n -- -n )
;		Two's complement of tos.

		$COLON	6,"NEGATE",NEGAT
		DW	INVER,DOLIT,1,PLUS,EXIT

;   DNEGATE	( d -- -d )
;		Two's complement of top double.

		$COLON	7,"DNEGATE",DNEGA
		DW	INVER,TOR,INVER
		DW	DOLIT,1,UPLUS
		DW	RFROM,PLUS,EXIT

;   -		( n1 n2 -- n1-n2 )
;		Subtraction.

		$COLON	1,"-",SUBB
		DW	NEGAT,PLUS,EXIT

;   ABS		( n -- n )
;		Return the absolute value of n.

		$COLON	3,"ABS",ABSS
		DW	DUPP,ZLESS
		DW	QBRAN,ABS1
		DW	NEGAT
ABS1:		DW	EXIT

;   =		( w w -- t )
;		Return true if top two are equal.

		$COLON	1,"=",EQUAL
		DW	XORR
		DW	QBRAN,EQU1
		DW	DOLIT,0,EXIT		;false flag
EQU1:		DW	DOLIT,-1,EXIT		;true flag

;   U<		( u u -- t )
;		Unsigned compare of top two items.

		$COLON	2,"U<",ULESS
		DW	DDUP,XORR,ZLESS
		DW	QBRAN,ULES1
		DW	SWAP,DROP,ZLESS,EXIT
ULES1:		DW	SUBB,ZLESS,EXIT

;   <		( n1 n2 -- t )
;		Signed compare of top two items.

		$COLON	1,"<",LESS
		DW	DDUP,XORR,ZLESS
		DW	QBRAN,LESS1
		DW	DROP,ZLESS,EXIT
LESS1:		DW	SUBB,ZLESS,EXIT

;   MAX		( n n -- n )
;		Return the greater of two top stack items.

		$COLON	3,"MAX",MAX
		DW	DDUP,LESS
		DW	QBRAN,MAX1
		DW	SWAP
MAX1:		DW	DROP,EXIT

;   MIN		( n n -- n )
;		Return the smaller of top two stack items.

		$COLON	3,"MIN",MIN
		DW	DDUP,SWAP,LESS
		DW	QBRAN,MIN1
		DW	SWAP
MIN1:		DW	DROP,EXIT

;   WITHIN	( u ul uh -- t )
;		Return true if u is within the range of ul and uh.

		$COLON	6,"WITHIN",WITHI
		DW	OVER,SUBB,TOR			;ul <= u < uh
		DW	SUBB,RFROM,ULESS,EXIT

;; Divide

;   UM/MOD	( udl udh u -- ur uq )
;		Unsigned divide of a double by a single. Return mod and quotient.

		$COLON	6,"UM/MOD",UMMOD
		DW	DDUP,ULESS
		DW	QBRAN,UMM4
		DW	NEGAT,DOLIT,15,TOR
UMM1:		DW	TOR,DUPP,UPLUS
		DW	TOR,TOR,DUPP,UPLUS
		DW	RFROM,PLUS,DUPP
		DW	RFROM,RAT,SWAP,TOR
		DW	UPLUS,RFROM,ORR
		DW	QBRAN,UMM2
		DW	TOR,DROP,DOLIT,1,PLUS,RFROM
		DW	BRAN,UMM3
UMM2:		DW	DROP
UMM3:		DW	RFROM
		DW	DONXT,UMM1
		DW	DROP,SWAP,EXIT
UMM4:		DW	DROP,DDROP
		DW	DOLIT,-1,DUPP,EXIT	;overflow, return max

;   M/MOD	( d n -- r q )
;		Signed floored divide of double by single. Return mod and quotient.

		$COLON	5,"M/MOD",MSMOD
		DW	DUPP,ZLESS,DUPP,TOR
		DW	QBRAN,MMOD1
		DW	NEGAT,TOR,DNEGA,RFROM
MMOD1:		DW	TOR,DUPP,ZLESS
		DW	QBRAN,MMOD2
		DW	RAT,PLUS
MMOD2:		DW	RFROM,UMMOD,RFROM
		DW	QBRAN,MMOD3
		DW	SWAP,NEGAT,SWAP
MMOD3:		DW	EXIT

;   /MOD	( n n -- r q )
;		Signed divide. Return mod and quotient.

		$COLON	4,"/MOD",SLMOD
		DW	OVER,ZLESS,SWAP,MSMOD,EXIT

;   MOD		( n n -- r )
;		Signed divide. Return mod only.

		$COLON	3,"MOD",MODD
		DW	SLMOD,DROP,EXIT

;   /		( n n -- q )
;		Signed divide. Return quotient only.

		$COLON	1,"/",SLASH
		DW	SLMOD,SWAP,DROP,EXIT

;; Multiply

;   UM*		( u u -- ud )
;		Unsigned multiply. Return double product.

		$COLON	3,"UM*",UMSTA
		DW	DOLIT,0,SWAP,DOLIT,15,TOR
UMST1:		DW	DUPP,UPLUS,TOR,TOR
		DW	DUPP,UPLUS,RFROM,PLUS,RFROM
		DW	QBRAN,UMST2
		DW	TOR,OVER,UPLUS,RFROM,PLUS
UMST2:		DW	DONXT,UMST1
		DW	ROT,DROP,EXIT

;   *		( n n -- n )
;		Signed multiply. Return single product.

		$COLON	1,"*",STAR
		DW	UMSTA,DROP,EXIT

;   M*		( n n -- d )
;		Signed multiply. Return double product.

		$COLON	2,"M*",MSTAR
		DW	DDUP,XORR,ZLESS,TOR
		DW	ABSS,SWAP,ABSS,UMSTA
		DW	RFROM
		DW	QBRAN,MSTA1
		DW	DNEGA
MSTA1:		DW	EXIT

;   */MOD	( n1 n2 n3 -- r q )
;		Multiply n1 and n2, then divide by n3. Return mod and quotient.

		$COLON	5,"*/MOD",SSMOD
		DW	TOR,MSTAR,RFROM,MSMOD,EXIT

;   */		( n1 n2 n3 -- q )
;		Multiply n1 by n2, then divide by n3. Return quotient only.

		$COLON	2,"*/",STASL
		DW	SSMOD,SWAP,DROP,EXIT


;; Miscellaneous

;   CELL+	( a -- a )
;		Add cell size in byte to address.

		$COLON	5,"CELL+",CELLP
		DW	DOLIT,{CELLL},PLUS,EXIT

;   CELL-	( a -- a )
;		Subtract cell size in byte from address.

		$COLON	5,"CELL-",CELLM
		DW	DOLIT,{0-CELLL},PLUS,EXIT

;   CELLS	( n -- n )
;		Multiply tos by cell size in bytes.

		$COLON	5,"CELLS",CELLS
		DW	EXIT              ;; I CHANGED THIS. I'M TRYING TO MAKE IT A NO-OP BECAUSE IT'S ALWAYS MULTIPLY BY 1


;   ALIGNED	( b -- a )
;		Align address to the cell boundary.

		$COLON	7,"ALIGNED",ALGND
		DW	EXIT              ;; I CHANGED THIS. I'M TRYING TO MAKE IT A NO-OP BECAUSE IT'S ALWAYS ALIGNED


;   BL		( -- 32 )
;		Return 32, the blank character.

		$COLON	2,"BL",BLANK
		DW	DOLIT,{' '},EXIT

;   >CHAR	( c -- c )
;		Filter non-printing characters.

		$COLON	5,">CHAR",TCHAR
		DW	DOLIT,0x07F,ANDD,DUPP	;mask msb
		DW	DOLIT,127,BLANK,WITHI	;check for printable
		DW	QBRAN,TCHA1
		DW	DROP,DOLIT,{'_'}		;replace non-printables
TCHA1:		DW	EXIT

;   DEPTH	( -- n )
;		Return the depth of the data stack.

		$COLON	5,"DEPTH",DEPTH
		DW	SPAT,SZERO,AT,SWAP,SUBB
		DW	DOLIT,{CELLL},SLASH,EXIT

;   PICK	( ... +n -- ... w )
;		Copy the nth stack item to tos.

		$COLON	4,"PICK",PICK
		DW	DOLIT,1,PLUS,CELLS
		DW	SPAT,PLUS,AT,EXIT

;; Memory access

;   +!		( n a -- )
;		Add n to the contents at address a.

		$COLON	2,"+!",PSTOR
		DW	SWAP,OVER,AT,PLUS
		DW	SWAP,STOR,EXIT

;   2!		( d a -- )
;		Store the double integer to address a.

		$COLON	2,"2!",DSTOR
		DW	SWAP,OVER,STOR
		DW	CELLP,STOR,EXIT

;   2@		( a -- d )
;		Fetch double integer from address a.

		$COLON	2,"2@",DAT
		DW	DUPP,CELLP,AT
		DW	SWAP,AT,EXIT

;   COUNT	( w -- w +n )
;		Return count word of a string and add 1 to word address.

		$COLON	5,"COUNT",COUNT
		DW	DUPP,DOLIT,1,PLUS
		DW	SWAP,AT,EXIT

;   HERE	( -- a )
;		Return the top of the code dictionary.

		$COLON	4,"HERE",HERE
		DW	CP,AT,EXIT

;   PAD		( -- a )
;		Return the address of a temporary buffer.

		$COLON	3,"PAD",PAD
		DW	HERE,DOLIT,80,PLUS,EXIT

;   TIB		( -- a )
;		Return the address of the terminal input buffer.

		$COLON	3,"TIB",TIB
		DW	NTIB,CELLP,AT,EXIT

;   @EXECUTE	( a -- )
;		Execute vector stored in address a.

		$COLON	8,"@EXECUTE",ATEXE
		DW	AT,QDUP			;?address or zero
		DW	QBRAN,EXE1
		DW	EXECU			;execute if non-zero
EXE1:		DW	EXIT			;do nothing if zero

;   CMOVE	( w1 w2 u -- )
;		Copy u bytes from b1 to b2.

		$COLON	5,"CMOVE",CMOVE
		DW	TOR
		DW	BRAN,CMOV2
CMOV1:		DW	TOR,DUPP,AT
		DW	RAT,STOR
		DW	DOLIT,1,PLUS
		DW	RFROM,DOLIT,1,PLUS
CMOV2:		DW	DONXT,CMOV1
		DW	DDROP,EXIT

;   FILL	( b u c -- )
;		Fill u bytes of character c to area beginning at b.

		$COLON	4,"FILL",FILL
		DW	SWAP,TOR,SWAP
		DW	BRAN,FILL2
FILL1:		DW	DDUP,STOR,DOLIT,1,PLUS
FILL2:		DW	DONXT,FILL1
		DW	DDROP,EXIT

;   -TRAILING	( w u -- w u )
;		Adjust the count to eliminate trailing white space.

		$COLON	9,"-TRAILING",DTRAI
		DW	TOR
		DW	BRAN,DTRA2
DTRA1:		DW	BLANK,OVER,RAT,PLUS,AT,LESS
		DW	QBRAN,DTRA2
		DW	RFROM,DOLIT,1,PLUS,EXIT	;adjusted count
DTRA2:		DW	DONXT,DTRA1
		DW	DOLIT,0,EXIT		;count=0

;   PACK$	( w u a -- a )
;		Build a counted string with u characters from w. Null fill.

		$COLON	5,"PACK$",PACKS
		DW	ALGND,DUPP,TOR		;strings only on cell boundary
		DW	OVER,DUPP,DOLIT,0
		DW	DOLIT,{CELLL},UMMOD,DROP	;count mod cell
		DW	SUBB,OVER,PLUS
		DW	DOLIT,0,SWAP,STOR	;null fill cell
		DW	DDUP,STOR,DOLIT,1,PLUS	;save count
		DW	SWAP,CMOVE,RFROM,EXIT	;move string


;; Numeric output, single precision

;   DIGIT	( u -- c )
;		Convert digit u to a character.

		$COLON	5,"DIGIT",DIGIT
		DW	DOLIT,9,OVER,LESS
		DW	DOLIT,7,ANDD,PLUS
		DW	DOLIT,{'0'},PLUS,EXIT

;   EXTRACT	( n base -- n c )
;		Extract the least significant digit from n.

		$COLON	7,"EXTRACT",EXTRC
		DW	DOLIT,0,SWAP,UMMOD
		DW	SWAP,DIGIT,EXIT

;   <#		( -- )
;		Initiate the numeric output process.

		$COLON	2,"<#",BDIGS
		DW	PAD,HLD,STOR,EXIT

;   HOLD	( c -- )
;		Insert a character into the numeric output string.

		$COLON	4,"HOLD",HOLD
		DW	HLD,AT,DOLIT,1,SUBB
		DW	DUPP,HLD,STOR,STOR,EXIT

;   #		( u -- u )
;		Extract one digit from u and append the digit to output string.

		$COLON	1,"#",DIG
		DW	BASE,AT,EXTRC,HOLD,EXIT

;   #S		( u -- 0 )
;		Convert u until all digits are added to the output string.

		$COLON	2,"#S",DIGS
DIGS1:		DW	DIG,DUPP
		DW	QBRAN,DIGS2
		DW	BRAN,DIGS1
DIGS2:		DW	EXIT

;   SIGN	( n -- )
;		Add a minus sign to the numeric output string.

		$COLON	4,"SIGN",SIGN
		DW	ZLESS
		DW	QBRAN,SIGN1
		DW	DOLIT,{'-'},HOLD
SIGN1:		DW	EXIT

;   #>		( w -- b u )
;		Prepare the output string to be TYPE'd.

		$COLON	2,"#>",EDIGS
		DW	DROP,HLD,AT
		DW	PAD,OVER,SUBB,EXIT

;   str		( n -- b u )
;		Convert a signed integer to a numeric string.

		$COLON	3,"str",STR
		DW	DUPP,TOR,ABSS
		DW	BDIGS,DIGS,RFROM
		DW	SIGN,EDIGS,EXIT

;   HEX		( -- )
;		Use radix 16 as base for numeric conversions.

		$COLON	3,"HEX",HEX
		DW	DOLIT,16,BASE,STOR,EXIT

;   DECIMAL	( -- )
;		Use radix 10 as base for numeric conversions.

		$COLON	7,"DECIMAL",DECIM
		DW	DOLIT,10,BASE,STOR,EXIT


;; Numeric input, single precision

;   DIGIT?	( c base -- u t )
;		Convert a character to its numeric value. A flag indicates success.

		$COLON	6,"DIGIT?",DIGTQ
		DW	TOR,DOLIT,{'0'},SUBB
		DW	DOLIT,9,OVER,LESS
		DW	QBRAN,DGTQ1
		DW	DOLIT,7,SUBB
		DW	DUPP,DOLIT,10,LESS,ORR
DGTQ1:		DW	DUPP,RFROM,ULESS,EXIT

;   NUMBER?	( a -- n T | a F )
;		Convert a number string to integer. Push a flag on tos.

		$COLON	7,"NUMBER?",NUMBQ
		DW	BASE,AT,TOR,DOLIT,0,OVER,COUNT
		DW	OVER,AT,DOLIT,{'$'},EQUAL
		DW	QBRAN,NUMQ1
		DW	HEX,SWAP,DOLIT,1,PLUS
		DW	SWAP,DOLIT,1,SUBB
NUMQ1:		DW	OVER,AT,DOLIT,{'-'},EQUAL,TOR
		DW	SWAP,RAT,SUBB,SWAP,RAT,PLUS,QDUP
		DW	QBRAN,NUMQ6
		DW	DOLIT,1,SUBB,TOR
NUMQ2:		DW	DUPP,TOR,AT,BASE,AT,DIGTQ
		DW	QBRAN,NUMQ4
		DW	SWAP,BASE,AT,STAR,PLUS,RFROM
		DW	DOLIT,1,PLUS
		DW	DONXT,NUMQ2
		DW	RAT,SWAP,DROP
		DW	QBRAN,NUMQ3
		DW	NEGAT
NUMQ3:		DW	SWAP
		DW	BRAN,NUMQ5
NUMQ4:		DW	RFROM,RFROM,DDROP,DDROP,DOLIT,0
NUMQ5:		DW	DUPP
NUMQ6:		DW	RFROM,DDROP
		DW	RFROM,BASE,STOR,EXIT


;; Basic I/O

;   ?KEY	( -- c T | F )
;		Return input character and true, or a false if no input.

		$COLON	4,"?KEY",QKEY
		DW	TQKEY,ATEXE,EXIT

;   KEY		( -- c )
;		Wait for and return an input character.

		$COLON	3,"KEY",KEY
KEY1:		DW	QKEY
		DW	QBRAN,KEY1
		DW	EXIT

;   EMIT	( c -- )
;		Send a character to the output device.

		$COLON	4,"EMIT",EMIT
		DW	TEMIT,ATEXE,EXIT

;   NUF?	( -- t )
;		Return false if no input, else pause and if CR return true.

		$COLON	4,"NUF?",NUFQ
		DW	QKEY,DUPP
		DW	QBRAN,NUFQ1
		DW	DDROP,KEY,DOLIT,{CRR},EQUAL
NUFQ1:		DW	EXIT

;   PACE	( -- )
;		Send a pace character for the file downloading process.

		$COLON	4,"PACE",PACE
		DW	DOLIT,11,EMIT,EXIT

;   SPACE	( -- )
;		Send the blank character to the output device.

		$COLON	5,"SPACE",SPACE
		DW	BLANK,EMIT,EXIT

;   SPACES	( +n -- )
;		Send n spaces to the output device.

		$COLON	6,"SPACES",SPACS
		DW	DOLIT,0,MAX,TOR
		DW	BRAN,CHAR2
CHAR1:		DW	SPACE
CHAR2:		DW	DONXT,CHAR1
		DW	EXIT

;   TYPE	( b u -- )
;		Output u characters from b.

		$COLON	4,"TYPE",TYPEE
		DW	TOR
		DW	BRAN,TYPE2
TYPE1:		DW	DUPP,AT,EMIT
		DW	DOLIT,1,PLUS
TYPE2:		DW	DONXT,TYPE1
		DW	DROP,EXIT

;   CR		( -- )
;		Output a carriage return and a line feed.

		$COLON	2,"CR",CR
		DW	DOLIT,{CRR},EMIT
		DW	DOLIT,{LF},EMIT,EXIT

;   do$		( -- a )
;		Return the address of a compiled string.

		$COLON	(COMPO+3),"do$",DOSTR
		DW	RFROM,RAT,RFROM,COUNT,PLUS
		DW	ALGND,TOR,SWAP,TOR,EXIT

;   $"|		( -- a )
;		Run time routine compiled by $". Return address of a compiled string.

		$COLON	(COMPO+3),"$"|",STRQP
		DW	DOSTR,EXIT		;force a call to do$

;   ."|		( -- )
;		Run time routine of ." . Output a compiled string.

		$COLON	(COMPO+3),"."|",DOTQP
		DW	DOSTR,COUNT,TYPEE,EXIT

;   .R		( n +n -- )
;		Display an integer in a field of n columns, right justified.

		$COLON	2,".R",DOTR
		DW	TOR,STR,RFROM,OVER,SUBB
		DW	SPACS,TYPEE,EXIT

;   U.R		( u +n -- )
;		Display an unsigned integer in n column, right justified.

		$COLON	3,"U.R",UDOTR
		DW	TOR,BDIGS,DIGS,EDIGS
		DW	RFROM,OVER,SUBB
		DW	SPACS,TYPEE,EXIT

;   U.		( u -- )
;		Display an unsigned integer in free format.

		$COLON	2,"U.",UDOT
		DW	BDIGS,DIGS,EDIGS
		DW	SPACE,TYPEE,EXIT

;   .		( w -- )
;		Display an integer in free format, preceeded by a space.

		$COLON	1,".",DOT
		DW	BASE,AT,DOLIT,10,XORR	;?decimal
		DW	QBRAN,DOT1
		DW	UDOT,EXIT		;no, display unsigned
DOT1:		DW	STR,SPACE,TYPEE,EXIT	;yes, display signed

;   ?		( a -- )
;		Display the contents in a memory cell.

		$COLON	1,"?",QUEST
		DW	AT,DOT,EXIT

;; Parsing

;   parse	( b u c -- b u delta ; <string> )
;		Scan string delimited by c. Return found string and its offset.

		$COLON	5,"parse",PARS
		DW	TEMP,STOR,OVER,TOR,DUPP
		DW	QBRAN,PARS8
		DW	DOLIT,1,SUBB,TEMP,AT,BLANK,EQUAL
		DW	QBRAN,PARS3
		DW	TOR
PARS1:		DW	BLANK,OVER,AT		;skip leading blanks ONLY
		DW	SUBB,ZLESS,INVER
		DW	QBRAN,PARS2
		DW	DOLIT,1,PLUS
		DW	DONXT,PARS1
		DW	RFROM,DROP,DOLIT,0,DUPP,EXIT
PARS2:		DW	RFROM
PARS3:		DW	OVER,SWAP
		DW	TOR
PARS4:		DW	TEMP,AT,OVER,AT,SUBB	;scan for delimiter
		DW	TEMP,AT,BLANK,EQUAL
		DW	QBRAN,PARS5
		DW	ZLESS
PARS5:		DW	QBRAN,PARS6
		DW	DOLIT,1,PLUS
		DW	DONXT,PARS4
		DW	DUPP,TOR
		DW	BRAN,PARS7
PARS6:		DW	RFROM,DROP,DUPP
		DW	DOLIT,1,PLUS,TOR
PARS7:		DW	OVER,SUBB
		DW	RFROM,RFROM,SUBB,EXIT
PARS8:		DW	OVER,RFROM,SUBB,EXIT

;   PARSE	( c -- b u ; <string> )
;		Scan input stream and return counted string delimited by c.

		$COLON	5,"PARSE",PARSE
		DW	TOR,TIB,INN,AT,PLUS	;current input buffer pointer
		DW	NTIB,AT,INN,AT,SUBB	;remaining count
		DW	RFROM,PARS,INN,PSTOR,EXIT

;   .(		( -- )
;		Output following string up to next ) .

		$COLON	(IMEDD+2),".(",DOTPR
		DW	DOLIT,{')'},PARSE,TYPEE,EXIT

;   (		( -- )
;		Ignore following string up to next ) . A comment.

		$COLON	(IMEDD+1),"(",PAREN
		DW	DOLIT,{')'},PARSE,DDROP,EXIT

;   \		( -- )
;		Ignore following text till the end of line.

		$COLON	(IMEDD+1),"\",BKSLA
		DW	NTIB,AT,INN,STOR,EXIT

;   CHAR	( -- c )
;		Parse next word and return its first character.

		$COLON	4,"CHAR",CHAR
		DW	BLANK,PARSE,DROP,AT,EXIT

;   TOKEN	( -- a ; <string> )
;		Parse a word from input stream and copy it to name dictionary.

		$COLON	5,"TOKEN",TOKEN
		DW	BLANK,PARSE,DOLIT,31,MIN
		DW	NP,AT,OVER,SUBB,CELLM
		DW	PACKS,EXIT

;   WORD	( c -- a ; <string> )
;		Parse a word from input stream and copy it to code dictionary.

		$COLON	4,"WORD",WORDD
		DW	PARSE,HERE,PACKS,EXIT

;; Dictionary search

;   NAME>	( na -- ca )
;		Return a code address given a name address.

		$COLON	5,"NAME>",NAMET
		DW	CELLM,CELLM,AT,EXIT

;   SAME?	( a a u -- a a f \ -0+ )
;		Compare u cells in two strings. Return 0 if identical.

		$COLON	5,"SAME?",SAMEQ
		DW	TOR
		DW	BRAN,SAME2
SAME1:		DW	OVER,RAT,CELLS,PLUS,AT
		DW	OVER,RAT,CELLS,PLUS,AT
		DW	SUBB,QDUP
		DW	QBRAN,SAME2
		DW	RFROM,DROP,EXIT		;strings not equal
SAME2:		DW	DONXT,SAME1
		DW	DOLIT,0,EXIT		;strings equal

;   find	( a va -- ca na | a F )
;		Search a vocabulary for a string. Return ca and na if succeeded.

		$COLON	4,"find",FIND
		DW	SWAP,DUPP,AT
		DW	DOLIT,{CELLL},SLASH,TEMP,STOR
		DW	DUPP,AT,TOR,CELLP,SWAP
FIND1:		DW	AT,DUPP
		DW	QBRAN,FIND6
		DW	DUPP,AT,DOLIT,{MASKK},ANDD,RAT,XORR
		DW	QBRAN,FIND2
		DW	CELLP,DOLIT,-1		;true flag
		DW	BRAN,FIND3
FIND2:		DW	CELLP,TEMP,AT,SAMEQ
FIND3:		DW	BRAN,FIND4
FIND6:		DW	RFROM,DROP
		DW	SWAP,CELLM,SWAP,EXIT
FIND4:		DW	QBRAN,FIND5
		DW	CELLM,CELLM
		DW	BRAN,FIND1
FIND5:		DW	RFROM,DROP,SWAP,DROP
		DW	CELLM
		DW	DUPP,NAMET,SWAP,EXIT

;   NAME?	( a -- ca na | a F )
;		Search all context vocabularies for a string.

		$COLON	5,"NAME?",NAMEQ
		DW	CNTXT,DUPP,DAT,XORR	;?context=also
		DW	QBRAN,NAMQ1
		DW	CELLM			;no, start with context
NAMQ1:		DW	TOR
NAMQ2:		DW	RFROM,CELLP,DUPP,TOR	;next in search order
		DW	AT,QDUP
		DW	QBRAN,NAMQ3
		DW	FIND,QDUP		;search vocabulary
		DW	QBRAN,NAMQ2
		DW	RFROM,DROP,EXIT		;found name
NAMQ3:		DW	RFROM,DROP		;name not found
		DW	DOLIT,0,EXIT		;false flag


;; Terminal response

;   ^H		( bot eot cur -- bot eot cur )
;		Backup the cursor by one character.

		$COLON	2,"^H",BKSP
		DW	TOR,OVER,RFROM,SWAP,OVER,XORR
		DW	QBRAN,BACK1
		DW	DOLIT,{BKSPP},TECHO,ATEXE,DOLIT,1,SUBB
		DW	BLANK,TECHO,ATEXE
		DW	DOLIT,{BKSPP},TECHO,ATEXE
BACK1:		DW	EXIT

;   TAP		( bot eot cur c -- bot eot cur )
;		Accept and echo the key stroke and bump the cursor.

		$COLON	3,"TAP",TAP
		DW	DUPP,TECHO,ATEXE
		DW	OVER,STOR,DOLIT,1,PLUS,EXIT

;   kTAP	( bot eot cur c -- bot eot cur )
;		Process a key stroke, CR or backspace.

		$COLON	4,"kTAP",KTAP
		DW	DUPP,DOLIT,{CRR},XORR
		DW	QBRAN,KTAP2
		DW	DOLIT,{BKSPP},XORR
		DW	QBRAN,KTAP1
		DW	BLANK,TAP,EXIT
KTAP1:		DW	BKSP,EXIT
KTAP2:		DW	DROP,SWAP,DROP,DUPP,EXIT

;   accept	( b u -- b u )
;		Accept characters to input buffer. Return with actual count.

		$COLON	6,"accept",ACCEP
		DW	OVER,PLUS,OVER
ACCP1:		DW	DDUP,XORR
		DW	QBRAN,ACCP4
		DW	KEY,DUPP
;		DW	BLANK,SUBB,DOLIT,95,ULESS
		DW	BLANK,DOLIT,127,WITHI
		DW	QBRAN,ACCP2
		DW	TAP
		DW	BRAN,ACCP3
ACCP2:		DW	TTAP,ATEXE
ACCP3:		DW	BRAN,ACCP1
ACCP4:		DW	DROP,OVER,SUBB,EXIT

;   EXPECT	( b u -- )
;		Accept input stream and store count in SPAN.

		$COLON	6,"EXPECT",EXPEC
		DW	TEXPE,ATEXE,SPAN,STOR,DROP,EXIT

;   QUERY	( -- )
;		Accept input stream to terminal input buffer.

		$COLON	5,"QUERY",QUERY
		DW	TIB,DOLIT,80,TEXPE,ATEXE,NTIB,STOR
		DW	DROP,DOLIT,0,INN,STOR,EXIT

;; Error handling

;   CATCH	( ca -- 0 | err# )
;		Execute word at ca and set up an error frame for it.

		$COLON	5,"CATCH",CATCH
		DW	SPAT,TOR,HANDL,AT,TOR	;save error frame
		DW	RPAT,HANDL,STOR,EXECU	;execute
		DW	RFROM,HANDL,STOR	;restore error frame
		DW	RFROM,DROP,DOLIT,0,EXIT	;no error

;   THROW	( err# -- err# )
;		Reset system to current local error frame an update error flag.

		$COLON	5,"THROW",THROW
		DW	HANDL,AT,RPSTO		;restore return stack
		DW	RFROM,HANDL,STOR	;restore handler frame
		DW	RFROM,SWAP,TOR,SPSTO	;restore data stack
		DW	DROP,RFROM,EXIT

;   NULL$	( -- a )
;		Return address of a null string with zero count.

		$COLON	5,"NULL$",NULLS
		DW	DOVAR			;emulate CREATE
		DW	0
		DW	99,111,121,111,116,101

;   ABORT	( -- )
;		Reset data stack and jump to QUIT.

		$COLON	5,"ABORT",ABORT
		DW	NULLS,THROW

;   abort"	( f -- )
;		Run time routine of ABORT" . Abort with a message.

		$COLON	(COMPO+6),"abort"",ABORQ
		DW	QBRAN,ABOR1		;text flag
		DW	DOSTR,THROW		;pass error string
ABOR1:		DW	DOSTR,DROP,EXIT		;drop error

;; The text interpreter

;   $INTERPRET	( a -- )
;		Interpret a word. If failed, try to convert it to an integer.

		$COLON	10,"$INTERPRET",INTER
		DW	NAMEQ,QDUP		;?defined
		DW	QBRAN,INTE1
		DW	AT,DOLIT,{COMPO},ANDD	;?compile only lexicon bits
		$D_STR	ABORQ," compile only"
		DW	EXECU,EXIT		;execute defined word
INTE1:		DW	TNUMB,ATEXE		;convert a number
		DW	QBRAN,INTE2
		DW	EXIT
INTE2:		DW	THROW			;error

;   [		( -- )
;		Start the text interpreter.

		$COLON	(IMEDD+1),"[",LBRAC
		DW	DOLIT,INTER,TEVAL,STOR,EXIT

;   .OK		( -- )
;		Display "ok" only while interpreting.

		$COLON	3,".OK",DOTOK
		DW	DOLIT,INTER,TEVAL,AT,EQUAL
		DW	QBRAN,DOTO1
		$D_STR	DOTQP," ok"
DOTO1:		DW	CR,EXIT

;   ?STACK	( -- )
;		Abort if the data stack underflows.

		$COLON	6,"?STACK",QSTAC
		DW	DEPTH,ZLESS		;check only for underflow
		$D_STR	ABORQ," underflow"
		DW	EXIT

;   EVAL	( -- )
;		Interpret the input stream.

		$COLON	4,"EVAL",EVAL
EVAL1:		DW	TOKEN,DUPP,AT		;?input stream empty
		DW	QBRAN,EVAL2
		DW	TEVAL,ATEXE,QSTAC	;evaluate input, check stack
		DW	BRAN,EVAL1
EVAL2:		DW	DROP,TPROM,ATEXE,EXIT	;prompt

;; Shell

;   PRESET	( -- )
;		Reset data stack pointer and the terminal input buffer.

		$COLON	6,"PRESET",PRESE
		DW	SZERO,AT,SPSTO
		DW	DOLIT,{TIBB},NTIB,CELLP,STOR,EXIT

;   xio		( a a a -- )
;		Reset the I/O vectors 'EXPECT, 'TAP, 'ECHO and 'PROMPT.

		$COLON	(COMPO+3),"xio",XIO
		DW	DOLIT,ACCEP,TEXPE,DSTOR
		DW	TECHO,DSTOR,EXIT

;   FILE	( -- )
;		Select I/O vectors for file download.

		$COLON	4,"FILE",FILE
		DW	DOLIT,PACE,DOLIT,DROP
		DW	DOLIT,KTAP,XIO,EXIT

;   HAND	( -- )
;		Select I/O vectors for terminal interface.

		$COLON	4,"HAND",HAND
		DW	DOLIT,DOTOK,DOLIT,EMIT
		DW	DOLIT,KTAP,XIO,EXIT

;   I/O		( -- a )
;		Array to store default I/O vectors.

		$COLON	3,"I/O",ISLO
		DW	DOVAR			;emulate CREATE
		DW	QRX,TXSTO		;default I/O vectors

;   CONSOLE	( -- )
;		Initiate terminal interface.

		$COLON	7,"CONSOLE",CONSO
		DW	ISLO,DAT,TQKEY,DSTOR	;restore default I/O device
		DW	HAND,EXIT		;keyboard input

;   QUIT	( -- )
;		Reset return stack pointer and start text interpreter.

		$COLON	4,"QUIT",QUIT
		DW	RZERO,AT,RPSTO		;reset return stack pointer
QUIT1:		DW	LBRAC			;start interpretation
QUIT2:		DW	QUERY			;get input
		DW	DOLIT,EVAL,CATCH,QDUP	;evaluate input
		DW	QBRAN,QUIT2		;continue till error
		DW	TPROM,AT,SWAP		;save input device
		DW	CONSO,NULLS,OVER,XORR	;?display error message
		DW	QBRAN,QUIT3
		DW	SPACE,COUNT,TYPEE	;error message
		$D_STR	DOTQP," ? "		;error prompt
QUIT3:		DW	DOLIT,DOTOK,XORR	;?file input
		DW	QBRAN,QUIT4
		DW	DOLIT,{ERR},EMIT		;file error, tell host
QUIT4:		DW	PRESE			;some cleanup
		DW	BRAN,QUIT1

;; The compiler

;   '		( -- ca )
;		Search context vocabularies for the next word in input stream.

		$COLON	1,"'",TICK
		DW	TOKEN,NAMEQ		;?defined
		DW	QBRAN,TICK1
		DW	EXIT			;yes, push code address
TICK1:		DW	THROW			;no, error

;   ALLOT	( n -- )
;		Allocate n bytes to the code dictionary.

		$COLON	5,"ALLOT",ALLOT
		DW	CP,PSTOR,EXIT		;adjust code pointer

;   ,		( w -- )
;		Compile an integer into the code dictionary.

		$COLON	1,",",COMMA
		DW	HERE,DUPP,CELLP		;cell boundary
		DW	CP,STOR,STOR,EXIT	;adjust code pointer, compile

;   [COMPILE]	( -- ; <string> )
;		Compile the next immediate word into code dictionary.

		$COLON	(IMEDD+9),"[COMPILE]",BCOMP
		DW	TICK,COMMA,EXIT

;   COMPILE	( -- )
;		Compile the next address in colon list to code dictionary.

		$COLON	(COMPO+7),"COMPILE",COMPI
		DW	RFROM,DUPP,AT,COMMA	;compile address
		DW	CELLP,TOR,EXIT		;adjust return address

;   LITERAL	( w -- )
;		Compile tos to code dictionary as an integer literal.

		$COLON	(IMEDD+7),"LITERAL",LITER
		DW	COMPI,DOLIT,COMMA,EXIT

;   $,"		( -- )
;		Compile a literal string up to next " .

		$COLON	3,"$,"",STRCQ
		DW	DOLIT,{'"'},WORDD		;move string to code dictionary
		DW	COUNT,PLUS,ALGND	;calculate aligned end of string
		DW	CP,STOR,EXIT		;adjust the code pointer

;   RECURSE	( -- )
;		Make the current word available for compilation.

		$COLON	(IMEDD+7),"RECURSE",RECUR
		DW	LAST,AT,NAMET,COMMA,EXIT

;; Structures

;   FOR		( -- a )
;		Start a FOR-NEXT loop structure in a colon definition.

		$COLON	(IMEDD+3),"FOR",FOR
		DW	COMPI,TOR,HERE,EXIT

;   BEGIN	( -- a )
;		Start an infinite or indefinite loop structure.

		$COLON	(IMEDD+5),"BEGIN",BEGIN
		DW	HERE,EXIT

;   NEXT	( a -- )
;		Terminate a FOR-NEXT loop structure.

		$COLON	(IMEDD+4),"NEXT",NEXT
		DW	COMPI,DONXT,COMMA,EXIT

;   UNTIL	( a -- )
;		Terminate a BEGIN-UNTIL indefinite loop structure.

		$COLON	(IMEDD+5),"UNTIL",UNTIL
		DW	COMPI,QBRAN,COMMA,EXIT

;   AGAIN	( a -- )
;		Terminate a BEGIN-AGAIN infinite loop structure.

		$COLON	(IMEDD+5),"AGAIN",AGAIN
		DW	COMPI,BRAN,COMMA,EXIT

;   IF		( -- A )
;		Begin a conditional branch structure.

		$COLON	(IMEDD+2),"IF",IFF
		DW	COMPI,QBRAN,HERE
		DW	DOLIT,0,COMMA,EXIT

;   AHEAD	( -- A )
;		Compile a forward branch instruction.

		$COLON	(IMEDD+5),"AHEAD",AHEAD
		DW	COMPI,BRAN,HERE,DOLIT,0,COMMA,EXIT

;   REPEAT	( A a -- )
;		Terminate a BEGIN-WHILE-REPEAT indefinite loop.

		$COLON	(IMEDD+6),"REPEAT",REPEA
		DW	AGAIN,HERE,SWAP,STOR,EXIT

;   THEN	( A -- )
;		Terminate a conditional branch structure.

		$COLON	(IMEDD+4),"THEN",THENN
		DW	HERE,SWAP,STOR,EXIT

;   AFT		( a -- a A )
;		Jump to THEN in a FOR-AFT-THEN-NEXT loop the first time through.

		$COLON	(IMEDD+3),"AFT",AFT
		DW	DROP,AHEAD,BEGIN,SWAP,EXIT

;   ELSE	( A -- A )
;		Start the false clause in an IF-ELSE-THEN structure.

		$COLON	(IMEDD+4),"ELSE",ELSEE
		DW	AHEAD,SWAP,THENN,EXIT

;   WHILE	( a -- A a )
;		Conditional branch out of a BEGIN-WHILE-REPEAT loop.

		$COLON	(IMEDD+5),"WHILE",WHILE
		DW	IFF,SWAP,EXIT

;   ABORT"	( -- ; <string> )
;		Conditional abort with an error message.

		$COLON	(IMEDD+6),"ABORT"",ABRTQ
		DW	COMPI,ABORQ,STRCQ,EXIT

;   $"		( -- ; <string> )
;		Compile an inline string literal.

		$COLON	(IMEDD+2),"$"",STRQ
		DW	COMPI,STRQP,STRCQ,EXIT

;   ."		( -- ; <string> )
;		Compile an inline string literal to be typed out at run time.

		$COLON	(IMEDD+2),"."",DOTQ
		DW	COMPI,DOTQP,STRCQ,EXIT

;; Name compiler

;   ?UNIQUE	( a -- a )
;		Display a warning message if the word already exists.

		$COLON	7,"?UNIQUE",UNIQU
		DW	DUPP,NAMEQ		;?name exists
		DW	QBRAN,UNIQ1		;redefinitions are OK
		$D_STR	DOTQP," reDef "		;but warn the user
		DW	OVER,COUNT,TYPEE	;just in case its not planned
UNIQ1:		DW	DROP,EXIT

;   $,n		( na -- )
;		Build a new dictionary name using the string at na.

		$COLON	3,"$,n",SNAME
		DW	DUPP,AT		;?null input
		DW	QBRAN,PNAM1
		DW	UNIQU			;?redefinition
		DW	DUPP,LAST,STOR		;save na for vocabulary link
		DW	HERE,ALGND,SWAP		;align code address
		DW	CELLM			;link address
		DW	CRRNT,AT,AT,OVER,STOR
		DW	CELLM,DUPP,NP,STOR	;adjust name pointer
		DW	STOR,EXIT		;save code pointer
	    $D_STRL	PNAM1, STRQP," name"		;null input
		DW	THROW

;; FORTH compiler

;   $COMPILE	( a -- )
;		Compile next word to code dictionary as a token or literal.

		$COLON	8,"$COMPILE",SCOMP
		DW	NAMEQ,QDUP		;?defined
		DW	QBRAN,SCOM2
		DW	AT,DOLIT,{IMEDD},ANDD	;?immediate
		DW	QBRAN,SCOM1
		DW	EXECU,EXIT		;its immediate, execute
SCOM1:		DW	COMMA,EXIT		;its not immediate, compile
SCOM2:		DW	TNUMB,ATEXE		;try to convert to number
		DW	QBRAN,SCOM3
		DW	LITER,EXIT		;compile number as integer
SCOM3:		DW	THROW			;error

;   OVERT	( -- )
;		Link a new word into the current vocabulary.

		$COLON	5,"OVERT",OVERT
		DW	LAST,AT,CRRNT,AT,STOR,EXIT

;   ;		( -- )
;		Terminate a colon definition.

		$COLON	(IMEDD+COMPO+1),";",SEMIS
		DW	COMPI,EXIT,LBRAC,OVERT,EXIT

;   ]		( -- )
;		Start compiling the words in the input stream.

		$COLON	1,"]",RBRAC
		DW	DOLIT,SCOMP,TEVAL,STOR,EXIT

;   call,	( ca -- )
;		Assemble a call instruction to ca.

		$COLON	5,"call,",CALLC
		DW DOLIT,0x4040,COMMA,COMMA        ;Hard coding - in binary CALL SP, DOLST
		DW EXIT	;

;   :		( -- ; <string> )
;		Start a new colon definition using next word as its name.

		$COLON	1,":",COLON
		DW	TOKEN,SNAME,DOLIT,DOLST
		DW	CALLC,RBRAC,EXIT

;   IMMEDIATE	( -- )
;		Make the last compiled word an immediate word.

		$COLON	9,"IMMEDIATE",IMMED
		DW	DOLIT,{IMEDD},LAST,AT,AT,ORR
		DW	LAST,AT,STOR,EXIT

;; Defining words

;   USER	( u -- ; <string> )
;		Compile a new user variable.

		$COLON	4,"USER",USER
		DW	TOKEN,SNAME,OVERT
		DW	DOLIT,DOLST,CALLC
		DW	COMPI,DOUSE,COMMA,EXIT

;   CREATE	( -- ; <string> )
;		Compile a new array entry without allocating code space.

		$COLON	6,"CREATE",CREAT
		DW	TOKEN,SNAME,OVERT
		DW	DOLIT,DOLST,CALLC
		DW	COMPI,DOVAR,EXIT

;   VARIABLE	( -- ; <string> )
;		Compile a new variable initialized to 0.

		$COLON	8,"VARIABLE",VARIA
		DW	CREAT,DOLIT,0,COMMA,EXIT

;; Tools

;   _TYPE	( b u -- )
;		Display a string. Filter non-printing characters.

		$COLON	5,"_TYPE",UTYPE
		DW	TOR			;start count down loop
		DW	BRAN,UTYP2		;skip first pass
UTYP1:		DW	DUPP,AT,TCHAR,EMIT	;display only printable
		DW	DOLIT,1,PLUS		;increment address
UTYP2:		DW	DONXT,UTYP1		;loop till done
		DW	DROP,EXIT

;   dm+		( a u -- a )
;		Dump u bytes from , leaving a+u on the stack.

		$COLON	3,"dm+",DMP
		DW	OVER,DOLIT,4,UDOTR	;display address
		DW	SPACE,TOR		;start count down loop
		DW	BRAN,PDUM2		;skip first pass
PDUM1:		DW	DUPP,AT,DOLIT,3,UDOTR	;display numeric data
		DW	DOLIT,1,PLUS		;increment address
PDUM2:		DW	DONXT,PDUM1		;loop till done
		DW	EXIT

;   DUMP	( a u -- )
;		Dump u bytes from a, in a formatted manner.

		$COLON	4,"DUMP",DUMP
		DW	BASE,AT,TOR,HEX		;save radix, set hex
		DW	DOLIT,16,SLASH		;change count to lines
		DW	TOR			;start count down loop
DUMP1:		DW	CR,DOLIT,16,DDUP,DMP	;display numeric
		DW	ROT,ROT
		DW	SPACE,SPACE,UTYPE	;display printable characters
		DW	NUFQ,INVER		;user control
		DW	QBRAN,DUMP2
		DW	DONXT,DUMP1		;loop till done
		DW	BRAN,DUMP3
DUMP2:		DW	RFROM,DROP		;cleanup loop stack, early exit
DUMP3:		DW	DROP,RFROM,BASE,STOR	;restore radix
		DW	EXIT

;   .S		( ... -- ... )
;		Display the contents of the data stack.

		$COLON	2,".S",DOTS
		DW	CR,DEPTH		;stack depth
		DW	TOR			;start count down loop
		DW	BRAN,DOTS2		;skip first pass
DOTS1:		DW	RAT,PICK,DOT		;index stack, display contents
DOTS2:		DW	DONXT,DOTS1		;loop till done
		$D_STR	DOTQP," <sp"
		DW	EXIT

;   !CSP	( -- )
;		Save stack pointer in CSP for error checking.

		$COLON	4,"!CSP",STCSP
		DW	SPAT,CSP,STOR,EXIT	;save pointer

;   ?CSP	( -- )
;		Abort if stack pointer differs from that saved in CSP.

		$COLON	4,"?CSP",QCSP
		DW	SPAT,CSP,AT,XORR	;compare pointers
		$D_STR	ABORQ,"stacks"		;abort if different
		DW	EXIT

;   >NAME	( ca -- na | F )
;		Convert code address to a name address.

		$COLON	5,">NAME",TNAME
		DW	CRRNT			;vocabulary link
TNAM1:		DW	CELLP,AT,QDUP		;check all vocabularies
		DW	QBRAN,TNAM4
		DW	DDUP
TNAM2:		DW	AT,DUPP			;?last word in a vocabulary
		DW	QBRAN,TNAM3
		DW	DDUP,NAMET,XORR		;compare
		DW	QBRAN,TNAM3
		DW	CELLM			;continue with next word
		DW	BRAN,TNAM2
TNAM3:		DW	SWAP,DROP,QDUP
		DW	QBRAN,TNAM1
		DW	SWAP,DROP,SWAP,DROP,EXIT
TNAM4:		DW	DROP,DOLIT,0,EXIT	;false flag

;   .ID		( na -- )
;		Display the name at address.

		$COLON	3,".ID",DOTID
		DW	QDUP			;if zero no name
		DW	QBRAN,DOTI1
		DW	COUNT,DOLIT,0x01F,ANDD	;mask lexicon bits
		DW	UTYPE,EXIT		;display name string
    	$D_STRL	DOTI1, DOTQP," noName"
		DW	EXIT

;   SEE		( -- ; <string> )
;		A simple decompiler.

		$COLON	3,"SEE",SEE
		DW	TICK			;starting address
		DW	CR,CELLP
SEE1:		DW	CELLP,DUPP,AT,DUPP	;?does it contain a zero
		DW	QBRAN,SEE2
		DW	TNAME			;?is it a name
SEE2:		DW	QDUP			;name address or zero
		DW	QBRAN,SEE3
		DW	SPACE,DOTID		;display name
		DW	BRAN,SEE4
SEE3:		DW	DUPP,AT,UDOT		;display number
SEE4:		DW	NUFQ			;user control
		DW	QBRAN,SEE1
		DW	DROP,EXIT

;   WORDS	( -- )
;		Display the names in the context vocabulary.

		$COLON	5,"WORDS",WORDS
		DW	CR,CNTXT,AT		;only in context
WORS1:		DW	AT,QDUP			;?at end of list
		DW	QBRAN,WORS2
		DW	DUPP,SPACE,DOTID	;display a name
		DW	CELLM,NUFQ		;user control
		DW	QBRAN,WORS1
		DW	DROP
WORS2:		DW	EXIT

;; Hardware reset

;   VER		( -- n )
;		Return the version number of this implementation.

		$COLON	3,"VER",VERSN
		DW	DOLIT,{VER*256+EXT},EXIT

;   hi		( -- )
;		Display the sign-on message of eForth.

		$COLON	2,"hi",HI
		DW	STOIO,CR		;initialize I/O
		$D_STR	DOTQP,"eForth v"	;model
		DW	BASE,AT,HEX		;save radix
		DW	VERSN,BDIGS,DIG,DIG
		DW	DOLIT,{'.'},HOLD
		DW	DIGS,EDIGS,TYPEE	;format version number
		DW	BASE,STOR,CR,EXIT	;restore radix

;   'BOOT	( -- a )
;		The application startup vector.

		$COLON	5,"'BOOT",TBOOT
		DW	DOVAR
		DW	HI			;application to boot

;   COLD	( -- )
;		The hilevel cold start sequence.

		$COLON	4,"COLD",COLD
COLD1:		DW	DOLIT,UZERO,DOLIT,{UPP}
		DW	DOLIT,38,CMOVE	;initialize user area
		DW	PRESE			;initialize stack and TIB
		DW	TBOOT,ATEXE		;application boot
		DW	FORTH,CNTXT,AT,DUPP	;initialize search order
		DW	CRRNT,DSTOR,OVERT
		DW	QUIT			;start interpretation
		DW	BRAN,COLD1		;just in case

;===============================================================

{LASTN	=	_NAME+CELLL*2}			;last name address

{NTOP	=	_NAME-0}			;next available memory in name dictionary
{CTOP	=	$+0}			;next available memory in code dictionary


;===============================================================

;; Main entry points and COLD start data
ORG	{COLDD}					;beginning of cold boot

ORIG:	LOAD SP, {SPP}			;initialize SP
		LOAD RP, {RPP}			;initialize RP
		JMP	COLD			;to high level cold start

; COLD start moves the following to USER variables.
; MUST BE IN SAME ORDER AS USER VARIABLES.


UZERO:	DW	0,0,0,0	;reserved
		DW	{SPP}			;SP0
		DW	{RPP}			;RP0
		DW	QRX	    		;'?KEY
		DW	TXSTO			;'EMIT
		DW	ACCEP			;'EXPECT
		DW	KTAP			;'TAP
		DW	TXSTO			;'ECHO
		DW	DOTOK			;'PROMPT
		DW	{BASEE}			;BASE
		DW	0			;tmp
		DW	0			;SPAN
		DW	0			;>IN
		DW	0			;#TIB
		DW	{TIBB}			;TIB
		DW	0			;CSP
		DW	INTER			;'EVAL
		DW	NUMBQ			;'NUMBER
		DW	0			;HLD
		DW	0			;HANDLER
		DW	0			;CONTEXT pointer
		DW	0,0,0,0,0,0,0,0		;vocabulary stack
		DW	0			;CURRENT pointer
		DW	0			;vocabulary link pointer
		DW	{CTOP}			;CP
		DW	{NTOP}   		;NP
		DW	{LASTN}			;LAST
ULAST:  DW  0



