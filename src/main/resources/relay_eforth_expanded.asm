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











; The function of $NEXT is to fetch the next word pointed to by the Interpreter Pointer IP,
; increment IP to point to the next word in the word list, and jump to the address just fetched.
; Since a word address points to a code field containing executable machine instructions,
; executing a word means jumping directly to the code field pointed to by the word address.
; $NEXT thus allows the virtual Forth computer to execute a list of words with very little CPU overhead.




;; Place holder





;   BYE		( -- )
;		Exit eForth.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "BYE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	BYE: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "BYE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		HALT			;Terminate execution

;; ?RX provides the functions required of both KEY and KEY? which accept input from a terminal.
;; ?RX inspects the terminal device and returns a character and a true flag if the character has been received and is waiting to be retrieved.
;; If no character was received, ?RX simply returns a false flag.
;; With ?RX, both KEY and KEY? can be defined as high level colon definitions.

;   ?RX		( -- c T | F )
;		Return input character and true, or a false if no input.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "?RX"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	QRX: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "?RX"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		WRDIN BX            ; Read a character into BX.
	    ; If there's a character, it's in BX now. Otherwise, BX is 0, and the zero flag is set.
	    JZ QRX1             ; Jump if we didn't get a character
	    PUSH SP, BX         ; We got a character, so push character (in BX) onto data stack (SP)
	    LOAD BX, -1         ; Load true flag (any non-zero value, in this case -1) into BX
QRX1:	PUSH SP, BX         ; BX contains our flag (true or false depending on if character was read in), so we push it onto the data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)


;; TX! sends a character on the data stack to the terminal device.

;   TX!		( c -- )
;		Send character c to the output device.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "TX!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	TXSTO: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "TX!"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP			; Pop the top of data stack (SP) into BX.
		LOAD CX, -1         ; Load -1 into CX to prep for comparison
		CMP CX, BX, CX      ; Compare BX and CX
		JNZ TX1             ; If we find that BX != CX (i.e. BX != -1) JMP to outputting the character
		LOAD BX, 32         ; Change -1 to blank character (32). This is what original eForth did, but that may have been DOS-specific.
TX1:	WRDOUT BX           ; Send character to output.
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)


;   !IO		( -- )
;		Initialize the serial I/O devices.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "!IO"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	STOIO: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "!IO"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; The kernel


;; doLIT pushes the next word onto the data stack as an integer literal instead of as an addresses to be executed by $NEXT.
;; It allows numbers to be compiled as in-line literals, supplying data to the data stack at run time.
;; doLIT is not used by itself, but rather compiled by LITERAL which inserts doLIT and its associated integer into the address list under construction.
;; Anytime you see a number in a colon definition, LITERAL is invoked to compile an integer literal with doLIT.

;   doLIT	( -- w )
;		Push an inline literal.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+5) + 3)}	    ;; new header. We need to move it in front of "doLIT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DOLIT: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+5)}                           ;; Name length
	DS "doLIT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		FETCH AX , EX   ; Fetch next word (the literal) pointed to by IP (EX), into WP (AX)
        INC EX , EX     ; Increment IP (EX) to point to next word in word list
		PUSH SP, AX     ; Push the inline literal (in AX) onto the data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)


;   doLIST	( a -- )
;		Process colon list.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+6) + 3)}	    ;; new header. We need to move it in front of "doLIST"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DOLST: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+6)}                           ;; Name length
	DS "doLIST"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		PUSH RP, EX     ; Save IP (EX) onto return stack (RP)
		POP	EX, SP		; Copies the address of the first entry in its address list into IP (EX)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;   EXIT	( -- )
;		Terminate a colon definition.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "EXIT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	EXIT: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "EXIT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP EX, RP      ; Pops the top item on the return stack (RP) into the IP (EX) register
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; EXECUTE takes the execution address from the data stack and executes that word.
;; This powerful word allows the user to execute any word which is not a part of an address list.

;   EXECUTE	( ca -- )
;		Execute the word at ca.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "EXECUTE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	EXECU: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "EXECUTE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP      ; Get the code address (ca) off the data stack and into BX.
		MOV PC,	BX			; Jump to the code address


;; Loops and Branches

;   next	( -- )
;		Run time code for the single index loop.
;		: next ( -- ) \ hilevel model
;		  r> r> dup if 1 - >r @ >r exit then drop cell+ >r ;

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+4) + 3)}	    ;; new header. We need to move it in front of "next"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DONXT: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+4)}                           ;; Name length
	DS "next"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP BX, RP          ; Get index at return stack (RP) TOS
		DEC BX, BX          ; Decrement the index
		PUSH RP, BX         ; Push index back onto stack
		JC	NEXT1			; Did we decrement the index (BX) below zero?
		FETCH	EX,EX		; no, continue loop. Load IP (EX) with value pointed to by IP (EX). IP:=(IP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)
NEXT1:	INC	RP,RP   		;yes, pop (drop) the index by simply incrementing the return stack (RP)
		INC	EX,EX		    ;exit loop by moving IP (EX) to next word.
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; Conditional branch
;   ?branch	( f -- )
;		Branch if flag is zero.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+7) + 3)}	    ;; new header. We need to move it in front of "?branch"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	QBRAN: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+7)}                           ;; Name length
	DS "?branch"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP			; pop flag off of data stack (SP) into BX
		OR	BX, BX, BX		; OR the value with itself so we can test if it's zero
		JZ	BRAN1			; Test if it's zero
		INC EX, EX  		; No. Not zero. Point IP (EX) to next cell by incrementing it.
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)
BRAN1:	    FETCH EX,EX	    ; Yes, it's zero. Load IP (EX) with value pointed to by IP (EX). IP:=(IP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; Unconditional branch
;   branch	( -- )
;		Branch to an inline address.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+6) + 3)}	    ;; new header. We need to move it in front of "branch"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	BRAN: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+6)}                           ;; Name length
	DS "branch"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		FETCH EX,EX		; Branch unconditionally. Load IP (EX) with value pointed to by IP (EX). IP:=(IP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; Memory access

;   !		( w a -- )
;		Pop the data stack to memory.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	STRE: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "!"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP      ; Get address (a) from TOS data stack (SP), and store into BX
		POP CX, SP      ; Get word (w) from TOS data stack (SP), and store into CX
		STORE BX, CX    ; Store w (in CX) into address a (in BX).
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;   @		( a -- w )
;		Push memory location to the data stack.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "@"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	AT: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "@"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP          ; Get address (a) from TOS data stack (SP), and store into BX
		FETCH CX, BX        ; Get the value (w) at address (a) and store into CX
		PUSH  SP, CX        ; Push the value (w) (in CX) onto data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; Return stack


;; RP@ pushes the contents of the return stack pointer RP on the data stack. Used very rarely in applications.

;   RP@		( -- a )
;		Push the current RP to the data stack.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "RP@"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	RPAT: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "RP@"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		PUSH	SP, RP          ;Push the contents of the return stack pointer (RP) onto the data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; RP! pushes the address on the top of the data stack to the return stack and thus initializes the return stack.
;; RP! is only used to initialize the system and are seldom used in applications

;   RP!		( a -- )
;		Set the return stack pointer.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+3) + 3)}	    ;; new header. We need to move it in front of "RP!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	RPSTO: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+3)}                           ;; Name length
	DS "RP!"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	RP, SP                  ; Pop the TOS of the data stack (SP) into the return stack pointer reg (RP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; >R pops a word off the return stack and pushes it on the data stack.

;   R>		( -- w )
;		Pop the return stack to the data stack.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "R>"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	RFROM: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "R>"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP BX, RP          ; Pop TOS of return stack (RP) into temp (BX)
		PUSH SP, BX         ; Push value (BX) onto data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; >R pops a number off the data stack and pushes it on the return stack

;   >R		( w -- )
;		Push the data stack to the return stack.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+2) + 3)}	    ;; new header. We need to move it in front of ">R"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	TOR: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+2)}                           ;; Name length
	DS ">R"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP BX, SP          ; Pop TOS of data stack (SP) into temp (BX)
        PUSH RP, BX         ; Push value (BX) onto return stack (RP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; R@ copies the top item on the return stack and pushes it on the data stack.

;   R@		( -- w )
;		Copy top of return stack to the data stack.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "R@"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	RAT: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "R@"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		FETCH BX, RP          ; Copy (not pop) TOS of return stack (RP) into temp (BX)
        PUSH SP, BX         ; Push value (BX) onto data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; Data stack

;; Data stack is initialized by SP!

;   SP!		( a -- )
;		Set the data stack pointer.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "SP!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	SPSTO: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "SP!"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	SP, SP  ;Set the data stack pointer (SP) to the TOS data stack value (a). This is kinda scary to me.
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; The depth of data stack can be examined by SP@

;   SP@		( -- a )
;		Push the current data stack pointer.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "SP@"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	SPAT: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "SP@"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		PUSH SP, SP			; Push the value of the data stack pointer (SP) onto the data stack (SP). Again, kinda scary.
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;   DROP	( w -- )
;		Discard top stack item.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "DROP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DROP: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "DROP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		INC	SP,SP		;Increment the stack pointer (SP) past the current TOS to effectively drop it.
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;   DUP		( w -- w w )
;		Duplicate the top stack item.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "DUP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DUPP: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "DUP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		FETCH BX,SP			;Copy the TOS of the data stack in temp (BX), without popping
		PUSH  SP, BX        ; Push the duplicate onto the TOS of the data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;   SWAP	( w1 w2 -- w2 w1 )
;		Exchange top two stack items.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "SWAP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	SWAP: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "SWAP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP          ; Pop TOS (w2) data stack (SP) into BX
		POP	CX, SP          ; Pop new TOS (w1) data stack (SP) into CX
		PUSH SP, BX         ; Push w2 (BX) onto data stack (SP)
		PUSH SP, CX         ; Push w1 (CX) onto data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;   OVER	( w1 w2 -- w1 w2 w1 )
;		Copy second stack item to top.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "OVER"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	OVER: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "OVER"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP          ; Pop TOS (w2) data stack (SP) into BX
        FETCH CX, SP        ; Copy new TOS (w1) data stack (SP) into CX
        PUSH SP, BX         ; Push w2 (BX) onto data stack (SP)
        PUSH SP, CX         ; Push w1 (CX) onto data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;;Logical Words


;; The only primitive word which generates flags is '0<', which examines the top item on the data stack for its negativeness.
;; If it is negative, '0<' will return a -1 for true. If it is 0 or positive, '0<' will return a 0 for false.

;   0<		( n -- t )
;		Return true if n is negative.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "0<"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	ZLESS: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "0<"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	AX, SP          ; Pop value (n) at TOS data stack (SP) into AX
		OR AX, AX, AX       ; Or value with itself to check the sign
		JNEG ZLESS1         ; Test if it's negative
		LOAD BX,0           ; It is not negative (i.e. AX is 0 or positive) Load 0 into BX
		JMP ZLESS2          ; JMP to pushing onto the data stack
ZLESS1: LOAD BX, -1         ; It is negative, load -1 into BX
ZLESS2:	PUSH SP, BX         ; Push BX value (either -1 or 0) onto data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)


;   AND		( w w -- w )
;		Bitwise AND.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "AND"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	ANDD: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "AND"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP          ;Pop TOS of data stack into BX
		POP	AX, SP          ;Pop next TOS of data stack into AX
		AND	BX, BX,AX       ; AND the elements, store result into BX
		PUSH SP,	BX          ; Push result of logical AND onto data stack
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;   OR		( w w -- w )
;		Bitwise inclusive OR.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "OR"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	ORR: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "OR"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP          ;Pop TOS of data stack into BX
        POP	AX, SP          ;Pop next TOS of data stack into AX
        OR	BX, BX,AX       ; OR the elements, store result into BX
        PUSH SP,	BX          ; Push result of logical OR onto data stack
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;   XOR		( w w -- w )
;		Bitwise exclusive OR.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "XOR"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	XORR: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "XOR"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP          ;Pop TOS of data stack into BX
        POP	AX, SP          ;Pop next TOS of data stack into AX
        XOR	BX, BX,AX       ; XOR the elements, store result into BX
        PUSH SP,	BX          ; Push result of logical XOR onto data stack
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)

;; Primitive Arithmetic

;; UM+ adds two unsigned number on the top of the data stack and returns to the data stack the sum of these
;; two numbers and the carry as one number on top of the sum.

;   UM+		( w w -- w cy )
;		Add two numbers, return the sum and carry flag.

	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "UM+"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	UPLUS: DW {_CODE},{_LINK}			;; token pointer and previous link? with assembly label
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "UM+"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP          ;Pop TOS of data stack into BX
        POP	AX, SP          ;Pop next TOS of data stack into AX
        ADD	BX, BX, AX       ; ADD the elements, store result into BX
        JC UPLUS1           ; Test if we carried
        LOAD CX,1           ; The addition carried (i.e. CF is 1) Load 1 into CX
        JMP UPLUS2          ; JMP to pushing onto the data stack
        UPLUS1: LOAD CX, 0  ; It did not carry, load 0 into BX
        UPLUS2:	PUSH SP, BX ; Push BX value (the sum) onto data stack (SP)
        PUSH SP, CX         ; Push CX value (the carry flag - either 1 or 0) onto data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)
