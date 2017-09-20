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

{CALLL = 0x0E890}		;NOP CALL opcodes (NEED TO UNDERSTAND THIS? WHAT DOES IT DO?)
                        ; My guess => CALL to the mem location of doLIST
                        ; This is raw, binary machine code (opcode. E8 is CALL rel16. 90 is relative offset added to EIP reg
                        ; I think EIP is the instruction pointer. So relative offset from current postion

;; Memory allocation	0//code>--//--<name//up>--<sp//tib>--rp//em

{EM	= 0x04000}	    		;top of memory - CAN PROBABLY CHANGE THIS
{COLDD = 0x00100}			;cold start vector - LIKELY NEEDS TO CHANGE SINCE WE DON'T START FROM 100 LIKE DOS

{US = 64*CELLL}     		;user area size in cells
{RTS = 64*CELLL}	    	;return stack/TIB size

{RPP = EM-8*CELLL}	    	;start of return stack (RP0)
{TIBB = RPP-RTS}			;terminal input buffer (TIB)
{SPP = TIBB-8*CELLL}		;start of data stack (SP0)
{UPP = EM-256*CELLL}		;start of user area (UP0)
{NAMEE = UPP-8*CELLL}		;name dictionary
{CODEE = COLDD+US}  		;code dictionary

;; Initialize assembly variables

{_LINK	= 0}					;force a null link
{_NAME	= NAMEE}					;initialize name pointer
{_CODE	= CODEE	}				;initialize code pointer
{_USER	= 4*CELLL}				;first user variable offset






; _NAME needs to grow "downwards" (towards zero). _CODE needs to grow upwards (away from zero to bigger numbers)

MACRO $CODE	@LEX,@NAME,@LABEL       ;	Compile a code definition header.
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (@LEX + 3)}	    ;; new header. We need to move it in front of @NAME's length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	@LABEL: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
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



MACRO $D_STR	@FUNCT,@STRNG       ;Compile an inline string.
	DW	@FUNCT, {@STRNG.length}		;;Write the function label and the length of the string
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



;; Place holder





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
		WRDIN BX            ; Read a character into BX.
	    ; If there's a character, it's in BX now. Otherwise, BX is 0, and the zero flag is set.
	    JZ QRX1             ; Jump if we didn't get a character
	    PUSH SP, BX         ; We got a character, so push character (in BX) onto data stack (SP)
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

		$CODE	1,"!",STRE
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
		JNEG ZLESS1         ; Test if it's negative
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
        ADD	BX, BX, AX       ; ADD the elements, store result into BX
        JC UPLUS1           ; Test if we carried
        LOAD CX,1           ; The addition carried (i.e. CF is 1) Load 1 into CX
        JMP UPLUS2          ; JMP to pushing onto the data stack
        UPLUS1: LOAD CX, 0  ; It did not carry, load 0 into BX
        UPLUS2:	PUSH SP, BX ; Push BX value (the sum) onto data stack (SP)
        PUSH SP, CX         ; Push CX value (the carry flag - either 1 or 0) onto data stack (SP)
        $NEXT
