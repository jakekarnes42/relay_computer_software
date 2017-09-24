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

{EM	= 0x04000}	    		;top of memory - CAN PROBABLY CHANGE THIS
{COLDD = 0}			;cold start vector - changed from 100 to 0 since we start at 0, unlike DOS

{US = 64*CELLL}     		;user area size in cells
{RTS = 64*CELLL}	    	;return stack/TIB size

{RPP = EM-8*CELLL}	    	;start of return stack (RP0)
{TIBB = RPP-RTS}			;terminal input buffer (TIB)
{SPP = TIBB-8*CELLL}		;start of data stack (SP0)
{UPP = SPP-256*CELLL}		;start of user area (UP0)
{NAMEE = UPP-8*CELLL}		;name dictionary
{CODEE = COLDD+US}  		;code dictionary

;; Initialize assembly variables

{_LINK	= 0}					;force a null link
{_NAME	= NAMEE}					;initialize name pointer
{_CODE	= CODEE	}				;initialize code pointer
{_USER	= 4*CELLL}				;first user variable offset






; _NAME needs to grow "downwards" (towards zero). _CODE needs to grow upwards (away from zero to bigger numbers)










;; Originally named D$

;; Same as $D_STR but with a label


; The function of $NEXT is to fetch the next word pointed to by the Interpreter Pointer IP,
; increment IP to point to the next word in the word list, and jump to the address just fetched.
; Since a word address points to a code field containing executable machine instructions,
; executing a word means jumping directly to the code field pointed to by the word address.
; $NEXT thus allows the virtual Forth computer to execute a list of words with very little CPU overhead.



ORG	{CODEE}					;start code dictionary

;   BYE		( -- )
;		Exit eForth.

BYE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "BYE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

QRX:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "?RX"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

TXSTO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "TX!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

STOIO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "!IO"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

DOLIT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+5) + 3)}	    ;; new header. We need to move it in front of "doLIT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

DOLST:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+6) + 3)}	    ;; new header. We need to move it in front of "doLIST"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

EXIT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "EXIT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

EXECU:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "EXECUTE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

DONXT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+4) + 3)}	    ;; new header. We need to move it in front of "next"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

QBRAN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+7) + 3)}	    ;; new header. We need to move it in front of "?branch"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

BRAN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+6) + 3)}	    ;; new header. We need to move it in front of "branch"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

STOR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

AT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "@"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

RPAT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "RP@"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

RPSTO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+3) + 3)}	    ;; new header. We need to move it in front of "RP!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

RFROM:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "R>"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

TOR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+2) + 3)}	    ;; new header. We need to move it in front of ">R"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

RAT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "R@"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

SPSTO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "SP!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

SPAT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "SP@"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

DROP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "DROP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

DUPP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "DUP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

SWAP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "SWAP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

OVER:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "OVER"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

ZLESS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "0<"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

ANDD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "AND"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

ORR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "OR"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

XORR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "XOR"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
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

UPLUS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "UM+"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "UM+"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
		POP	BX, SP          ;Pop TOS of data stack into BX
        POP	AX, SP          ;Pop next TOS of data stack into AX
        ADD	AX, BX, AX      ; ADD the elements, store result into AX
        JNC UPLUS1          ; Test if we carried or not
        LOAD CX,1           ; The addition carried (i.e. CF is 1) Load 1 into CX
        JMP UPLUS2          ; JMP to pushing onto the data stack
        UPLUS1: LOAD CX, 0  ; It did not carry, load 0 into BX
        UPLUS2:	PUSH SP, AX ; Push AX value (the sum) onto data stack (SP)
        PUSH SP, CX         ; Push CX value (the carry flag - either 1 or 0) onto data stack (SP)
    FETCH AX , EX   ; Fetch next word pointed to by IP (EX), into WP (AX)
    INC EX , EX     ; Increment IP (EX) to point to next word in word list
    MOV PC, AX          ; Jump to the next word, through WP (AX)


;; System and user variables

;   doVAR	( -- a )
;		Run time routine for VARIABLE and CREATE.

DOVAR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+5) + 3)}	    ;; new header. We need to move it in front of "doVAR"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+5)}                           ;; Name length
	DS "doVAR"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	RFROM,EXIT

;   UP		( -- a )
;		Pointer to the user area.

UP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "UP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "UP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOVAR
		DW	{UPP}

;   doUSER	( -- a )
;		Run time routine for user variables.

DOUSE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+6) + 3)}	    ;; new header. We need to move it in front of "doUSER"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+6)}                           ;; Name length
	DS "doUSER"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	RFROM,AT            ;Gets offset from the return stack
		DW  UP,AT               ;Loads UP (start of user area) from mem
		DW  PLUS,EXIT           ;Adds both and returns address of requested user variable.

;   SP0		( -- a )
;		Pointer to bottom of the data stack.

SZERO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "SP0"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "SP0"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   RP0		( -- a )
;		Pointer to bottom of the return stack.

RZERO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "RP0"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "RP0"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   '?KEY	( -- a )
;		Execution vector of ?KEY.

TQKEY:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "'?KEY"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "'?KEY"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   'EMIT	( -- a )
;		Execution vector of EMIT.

TEMIT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "'EMIT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "'EMIT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   'EXPECT	( -- a )
;		Execution vector of EXPECT.

TEXPE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "'EXPECT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "'EXPECT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   'TAP	( -- a )
;		Execution vector of TAP.

TTAP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "'TAP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "'TAP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   'ECHO	( -- a )
;		Execution vector of ECHO.

TECHO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "'ECHO"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "'ECHO"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   'PROMPT	( -- a )
;		Execution vector of PROMPT.

TPROM:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "'PROMPT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "'PROMPT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   BASE	( -- a )
;		Storage of the radix base for numeric I/O.

BASE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "BASE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "BASE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   tmp		( -- a )
;		A temporary storage location used in parse and find.

TEMP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+3) + 3)}	    ;; new header. We need to move it in front of "tmp"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+3)}                           ;; Name length
	DS "tmp"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   SPAN	( -- a )
;		Hold character count received by EXPECT.

SPAN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "SPAN"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "SPAN"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   >IN		( -- a )
;		Hold the character pointer while parsing input STORam.

INN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of ">IN"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS ">IN"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   #TIB	( -- a )
;		Hold the current count and address of the terminal input buffer.

NTIB:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "#TIB"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "#TIB"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset
		{_USER = _USER+CELLL}

;   CSP		( -- a )
;		Hold the stack pointer for error checking.

CSP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "CSP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "CSP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   'EVAL	( -- a )
;		Execution vector of EVAL.

TEVAL:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "'EVAL"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "'EVAL"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   'NUMBER	( -- a )
;		Execution vector of NUMBER?.

TNUMB:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "'NUMBER"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "'NUMBER"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   HLD		( -- a )
;		Hold a pointer in building a numeric output string.

HLD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "HLD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "HLD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   HANDLER	( -- a )
;		Hold the return stack pointer for error handling.

HANDL:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "HANDLER"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "HANDLER"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   CONTEXT	( -- a )
;		A area to specify vocabulary search order.

CNTXT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "CONTEXT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "CONTEXT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset
		{_USER = _USER+VOCSS*CELLL}	;vocabulary stack

;   CURRENT	( -- a )
;		Point to the vocabulary to be extended.

CRRNT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "CURRENT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "CURRENT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset
		{_USER = _USER+CELLL}		;vocabulary link pointer

;   CP		( -- a )
;		Point to the top of the code dictionary.

CP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "CP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "CP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   NP		( -- a )
;		Point to the bottom of the name dictionary.

NP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "NP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "NP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;   LAST	( -- a )
;		Point to the last name in the name dictionary.

LAST:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "LAST"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "LAST"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
	DW	DOUSE, {_USER}			    ;;followed by doUSER and offset
	{_USER	= _USER+CELLL}			;;update user area offset

;; Common functions

;   doVOC	( -- )
;		Run time action of VOCABULARY's.

DOVOC:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+5) + 3)}	    ;; new header. We need to move it in front of "doVOC"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+5)}                           ;; Name length
	DS "doVOC"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	RFROM,CNTXT,STOR,EXIT

;   FORTH	( -- )
;		Make FORTH the context vocabulary.

FORTH:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "FORTH"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "FORTH"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOVOC
		DW	0			;vocabulary head pointer
		DW	0			;vocabulary link pointer

;   ?DUP	( w -- w w | 0 )
;		Dup tos if its is not zero.

QDUP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "?DUP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "?DUP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DUPP
		DW	QBRAN,QDUP1
		DW	DUPP
QDUP1:		DW	EXIT

;   ROT		( w1 w2 w3 -- w2 w3 w1 )
;		Rot 3rd item to top.

ROT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "ROT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "ROT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR,SWAP,RFROM,SWAP,EXIT

;   2DROP	( w w -- )
;		Discard two items on stack.

DDROP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "2DROP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "2DROP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DROP,DROP,EXIT

;   2DUP	( w1 w2 -- w1 w2 w1 w2 )
;		Duplicate top two items.

DDUP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "2DUP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "2DUP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	OVER,OVER,EXIT

;   +		( w w -- sum )
;		Add top two items.

PLUS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "+"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "+"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	UPLUS,DROP,EXIT

;   D+		( d d -- d )
;		Double addition, as an example using UM+.
;
;		$COLON	2,"D+",DPLUS
;		DW	TOR,SWAP,TOR,UPLUS
;		DW	RFROM,RFROM,PLUS,PLUS,EXIT

;   NOT		( w -- w )
;		One's complement of tos.

INVER:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "NOT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "NOT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,-1,XORR,EXIT

;   NEGATE	( n -- -n )
;		Two's complement of tos.

NEGAT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "NEGATE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "NEGATE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	INVER,DOLIT,1,PLUS,EXIT

;   DNEGATE	( d -- -d )
;		Two's complement of top double.

DNEGA:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "DNEGATE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "DNEGATE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	INVER,TOR,INVER
		DW	DOLIT,1,UPLUS
		DW	RFROM,PLUS,EXIT

;   -		( n1 n2 -- n1-n2 )
;		Subtraction.

SUBB:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "-"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "-"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	NEGAT,PLUS,EXIT

;   ABS		( n -- n )
;		Return the absolute value of n.

ABSS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "ABS"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "ABS"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DUPP,ZLESS
		DW	QBRAN,ABS1
		DW	NEGAT
ABS1:		DW	EXIT

;   =		( w w -- t )
;		Return true if top two are equal.

EQUAL:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "="'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "="			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	XORR
		DW	QBRAN,EQU1
		DW	DOLIT,0,EXIT		;false flag
EQU1:		DW	DOLIT,-1,EXIT		;true flag

;   U<		( u u -- t )
;		Unsigned compare of top two items.

ULESS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "U<"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "U<"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DDUP,XORR,ZLESS
		DW	QBRAN,ULES1
		DW	SWAP,DROP,ZLESS,EXIT
ULES1:		DW	SUBB,ZLESS,EXIT

;   <		( n1 n2 -- t )
;		Signed compare of top two items.

LESS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "<"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "<"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DDUP,XORR,ZLESS
		DW	QBRAN,LESS1
		DW	DROP,ZLESS,EXIT
LESS1:		DW	SUBB,ZLESS,EXIT

;   MAX		( n n -- n )
;		Return the greater of two top stack items.

MAX:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "MAX"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "MAX"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DDUP,LESS
		DW	QBRAN,MAX1
		DW	SWAP
MAX1:		DW	DROP,EXIT

;   MIN		( n n -- n )
;		Return the smaller of top two stack items.

MIN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "MIN"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "MIN"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DDUP,SWAP,LESS
		DW	QBRAN,MIN1
		DW	SWAP
MIN1:		DW	DROP,EXIT

;   WITHIN	( u ul uh -- t )
;		Return true if u is within the range of ul and uh.

WITHI:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "WITHIN"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "WITHIN"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	OVER,SUBB,TOR			;ul <= u < uh
		DW	SUBB,RFROM,ULESS,EXIT

;; Divide

;   UM/MOD	( udl udh u -- ur uq )
;		Unsigned divide of a double by a single. Return mod and quotient.

UMMOD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "UM/MOD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "UM/MOD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

MSMOD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "M/MOD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "M/MOD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

SLMOD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "/MOD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "/MOD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	OVER,ZLESS,SWAP,MSMOD,EXIT

;   MOD		( n n -- r )
;		Signed divide. Return mod only.

MODD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "MOD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "MOD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SLMOD,DROP,EXIT

;   /		( n n -- q )
;		Signed divide. Return quotient only.

SLASH:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "/"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "/"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SLMOD,SWAP,DROP,EXIT

;; Multiply

;   UM*		( u u -- ud )
;		Unsigned multiply. Return double product.

UMSTA:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "UM*"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "UM*"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,0,SWAP,DOLIT,15,TOR
UMST1:		DW	DUPP,UPLUS,TOR,TOR
		DW	DUPP,UPLUS,RFROM,PLUS,RFROM
		DW	QBRAN,UMST2
		DW	TOR,OVER,UPLUS,RFROM,PLUS
UMST2:		DW	DONXT,UMST1
		DW	ROT,DROP,EXIT

;   *		( n n -- n )
;		Signed multiply. Return single product.

STAR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "*"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "*"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	UMSTA,DROP,EXIT

;   M*		( n n -- d )
;		Signed multiply. Return double product.

MSTAR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "M*"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "M*"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DDUP,XORR,ZLESS,TOR
		DW	ABSS,SWAP,ABSS,UMSTA
		DW	RFROM
		DW	QBRAN,MSTA1
		DW	DNEGA
MSTA1:		DW	EXIT

;   */MOD	( n1 n2 n3 -- r q )
;		Multiply n1 and n2, then divide by n3. Return mod and quotient.

SSMOD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "*/MOD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "*/MOD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR,MSTAR,RFROM,MSMOD,EXIT

;   */		( n1 n2 n3 -- q )
;		Multiply n1 by n2, then divide by n3. Return quotient only.

STASL:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "*/"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "*/"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SSMOD,SWAP,DROP,EXIT


;; Miscellaneous

;   CELL+	( a -- a )
;		Add cell size in byte to address.

CELLP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "CELL+"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "CELL+"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,{CELLL},PLUS,EXIT

;   CELL-	( a -- a )
;		Subtract cell size in byte from address.

CELLM:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "CELL-"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "CELL-"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,{0-CELLL},PLUS,EXIT

;   CELLS	( n -- n )
;		Multiply tos by cell size in bytes.

CELLS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "CELLS"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "CELLS"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	EXIT              ;; I CHANGED THIS. I'M TRYING TO MAKE IT A NO-OP BECAUSE IT'S ALWAYS MULTIPLY BY 1


;   ALIGNED	( b -- a )
;		Align address to the cell boundary.

ALGND:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "ALIGNED"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "ALIGNED"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	EXIT              ;; I CHANGED THIS. I'M TRYING TO MAKE IT A NO-OP BECAUSE IT'S ALWAYS ALIGNED


;   BL		( -- 32 )
;		Return 32, the blank character.

BLANK:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "BL"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "BL"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,{' '},EXIT

;   >CHAR	( c -- c )
;		Filter non-printing characters.

TCHAR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of ">CHAR"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS ">CHAR"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,0x07F,ANDD,DUPP	;mask msb
		DW	DOLIT,127,BLANK,WITHI	;check for printable
		DW	QBRAN,TCHA1
		DW	DROP,DOLIT,{'_'}		;replace non-printables
TCHA1:		DW	EXIT

;   DEPTH	( -- n )
;		Return the depth of the data stack.

DEPTH:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "DEPTH"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "DEPTH"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SPAT,SZERO,AT,SWAP,SUBB
		DW	DOLIT,{CELLL},SLASH,EXIT

;   PICK	( ... +n -- ... w )
;		Copy the nth stack item to tos.

PICK:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "PICK"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "PICK"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,1,PLUS,CELLS
		DW	SPAT,PLUS,AT,EXIT

;; Memory access

;   +!		( n a -- )
;		Add n to the contents at address a.

PSTOR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "+!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "+!"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SWAP,OVER,AT,PLUS
		DW	SWAP,STOR,EXIT

;   2!		( d a -- )
;		Store the double integer to address a.

DSTOR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "2!"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "2!"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SWAP,OVER,STOR
		DW	CELLP,STOR,EXIT

;   2@		( a -- d )
;		Fetch double integer from address a.

DAT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "2@"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "2@"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DUPP,CELLP,AT
		DW	SWAP,AT,EXIT

;   COUNT	( w -- w +n )
;		Return count word of a string and add 1 to word address.

COUNT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "COUNT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "COUNT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DUPP,DOLIT,1,PLUS
		DW	SWAP,AT,EXIT

;   HERE	( -- a )
;		Return the top of the code dictionary.

HERE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "HERE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "HERE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	CP,AT,EXIT

;   PAD		( -- a )
;		Return the address of a temporary buffer.

PAD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "PAD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "PAD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	HERE,DOLIT,80,PLUS,EXIT

;   TIB		( -- a )
;		Return the address of the terminal input buffer.

TIB:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "TIB"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "TIB"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	NTIB,CELLP,AT,EXIT

;   @EXECUTE	( a -- )
;		Execute vector stored in address a.

ATEXE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (8 + 3)}	    ;; new header. We need to move it in front of "@EXECUTE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {8}                           ;; Name length
	DS "@EXECUTE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	AT,QDUP			;?address or zero
		DW	QBRAN,EXE1
		DW	EXECU			;execute if non-zero
EXE1:		DW	EXIT			;do nothing if zero

;   CMOVE	( w1 w2 u -- )
;		Copy u bytes from b1 to b2.

CMOVE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "CMOVE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "CMOVE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

FILL:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "FILL"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "FILL"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SWAP,TOR,SWAP
		DW	BRAN,FILL2
FILL1:		DW	DDUP,STOR,DOLIT,1,PLUS
FILL2:		DW	DONXT,FILL1
		DW	DDROP,EXIT

;   -TRAILING	( w u -- w u )
;		Adjust the count to eliminate trailing white space.

DTRAI:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (9 + 3)}	    ;; new header. We need to move it in front of "-TRAILING"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {9}                           ;; Name length
	DS "-TRAILING"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR
		DW	BRAN,DTRA2
DTRA1:		DW	BLANK,OVER,RAT,PLUS,AT,LESS
		DW	QBRAN,DTRA2
		DW	RFROM,DOLIT,1,PLUS,EXIT	;adjusted count
DTRA2:		DW	DONXT,DTRA1
		DW	DOLIT,0,EXIT		;count=0

;   PACK$	( w u a -- a )
;		Build a counted string with u characters from w. Null fill.

PACKS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "PACK$"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "PACK$"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

DIGIT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "DIGIT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "DIGIT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,9,OVER,LESS
		DW	DOLIT,7,ANDD,PLUS
		DW	DOLIT,{'0'},PLUS,EXIT

;   EXTRACT	( n base -- n c )
;		Extract the least significant digit from n.

EXTRC:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "EXTRACT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "EXTRACT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,0,SWAP,UMMOD
		DW	SWAP,DIGIT,EXIT

;   <#		( -- )
;		Initiate the numeric output process.

BDIGS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "<#"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "<#"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	PAD,HLD,STOR,EXIT

;   HOLD	( c -- )
;		Insert a character into the numeric output string.

HOLD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "HOLD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "HOLD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	HLD,AT,DOLIT,1,SUBB
		DW	DUPP,HLD,STOR,STOR,EXIT

;   #		( u -- u )
;		Extract one digit from u and append the digit to output string.

DIG:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "#"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "#"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	BASE,AT,EXTRC,HOLD,EXIT

;   #S		( u -- 0 )
;		Convert u until all digits are added to the output string.

DIGS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "#S"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "#S"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
DIGS1:		DW	DIG,DUPP
		DW	QBRAN,DIGS2
		DW	BRAN,DIGS1
DIGS2:		DW	EXIT

;   SIGN	( n -- )
;		Add a minus sign to the numeric output string.

SIGN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "SIGN"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "SIGN"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	ZLESS
		DW	QBRAN,SIGN1
		DW	DOLIT,{'-'},HOLD
SIGN1:		DW	EXIT

;   #>		( w -- b u )
;		Prepare the output string to be TYPE'd.

EDIGS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "#>"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "#>"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DROP,HLD,AT
		DW	PAD,OVER,SUBB,EXIT

;   str		( n -- b u )
;		Convert a signed integer to a numeric string.

STR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "str"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "str"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DUPP,TOR,ABSS
		DW	BDIGS,DIGS,RFROM
		DW	SIGN,EDIGS,EXIT

;   HEX		( -- )
;		Use radix 16 as base for numeric conversions.

HEX:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "HEX"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "HEX"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,16,BASE,STOR,EXIT

;   DECIMAL	( -- )
;		Use radix 10 as base for numeric conversions.

DECIM:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "DECIMAL"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "DECIMAL"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,10,BASE,STOR,EXIT


;; Numeric input, single precision

;   DIGIT?	( c base -- u t )
;		Convert a character to its numeric value. A flag indicates success.

DIGTQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "DIGIT?"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "DIGIT?"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR,DOLIT,{'0'},SUBB
		DW	DOLIT,9,OVER,LESS
		DW	QBRAN,DGTQ1
		DW	DOLIT,7,SUBB
		DW	DUPP,DOLIT,10,LESS,ORR
DGTQ1:		DW	DUPP,RFROM,ULESS,EXIT

;   NUMBER?	( a -- n T | a F )
;		Convert a number string to integer. Push a flag on tos.

NUMBQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "NUMBER?"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "NUMBER?"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

QKEY:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "?KEY"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "?KEY"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TQKEY,ATEXE,EXIT

;   KEY		( -- c )
;		Wait for and return an input character.

KEY:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "KEY"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "KEY"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
KEY1:		DW	QKEY
		DW	QBRAN,KEY1
		DW	EXIT

;   EMIT	( c -- )
;		Send a character to the output device.

EMIT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "EMIT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "EMIT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TEMIT,ATEXE,EXIT

;   NUF?	( -- t )
;		Return false if no input, else pause and if CR return true.

NUFQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "NUF?"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "NUF?"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	QKEY,DUPP
		DW	QBRAN,NUFQ1
		DW	DDROP,KEY,DOLIT,{CRR},EQUAL
NUFQ1:		DW	EXIT

;   PACE	( -- )
;		Send a pace character for the file downloading process.

PACE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "PACE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "PACE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,11,EMIT,EXIT

;   SPACE	( -- )
;		Send the blank character to the output device.

SPACE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "SPACE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "SPACE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	BLANK,EMIT,EXIT

;   SPACES	( +n -- )
;		Send n spaces to the output device.

SPACS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "SPACES"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "SPACES"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,0,MAX,TOR
		DW	BRAN,CHAR2
CHAR1:		DW	SPACE
CHAR2:		DW	DONXT,CHAR1
		DW	EXIT

;   TYPE	( b u -- )
;		Output u characters from b.

TYPEE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "TYPE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "TYPE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR
		DW	BRAN,TYPE2
TYPE1:		DW	DUPP,AT,EMIT
		DW	DOLIT,1,PLUS
TYPE2:		DW	DONXT,TYPE1
		DW	DROP,EXIT

;   CR		( -- )
;		Output a carriage return and a line feed.

CR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "CR"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "CR"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,{CRR},EMIT
		DW	DOLIT,{LF},EMIT,EXIT

;   do$		( -- a )
;		Return the address of a compiled string.

DOSTR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+3) + 3)}	    ;; new header. We need to move it in front of "do$"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+3)}                           ;; Name length
	DS "do$"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	RFROM,RAT,RFROM,COUNT,PLUS
		DW	ALGND,TOR,SWAP,TOR,EXIT

;   $"|		( -- a )
;		Run time routine compiled by $". Return address of a compiled string.

STRQP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+3) + 3)}	    ;; new header. We need to move it in front of "$"|"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+3)}                           ;; Name length
	DS "$"|"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOSTR,EXIT		;force a call to do$

;   ."|		( -- )
;		Run time routine of ." . Output a compiled string.

DOTQP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+3) + 3)}	    ;; new header. We need to move it in front of "."|"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+3)}                           ;; Name length
	DS "."|"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOSTR,COUNT,TYPEE,EXIT

;   .R		( n +n -- )
;		Display an integer in a field of n columns, right justified.

DOTR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of ".R"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS ".R"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR,STR,RFROM,OVER,SUBB
		DW	SPACS,TYPEE,EXIT

;   U.R		( u +n -- )
;		Display an unsigned integer in n column, right justified.

UDOTR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "U.R"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "U.R"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR,BDIGS,DIGS,EDIGS
		DW	RFROM,OVER,SUBB
		DW	SPACS,TYPEE,EXIT

;   U.		( u -- )
;		Display an unsigned integer in free format.

UDOT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "U."'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "U."			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	BDIGS,DIGS,EDIGS
		DW	SPACE,TYPEE,EXIT

;   .		( w -- )
;		Display an integer in free format, preceeded by a space.

DOT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "."'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "."			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	BASE,AT,DOLIT,10,XORR	;?decimal
		DW	QBRAN,DOT1
		DW	UDOT,EXIT		;no, display unsigned
DOT1:		DW	STR,SPACE,TYPEE,EXIT	;yes, display signed

;   ?		( a -- )
;		Display the contents in a memory cell.

QUEST:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "?"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "?"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	AT,DOT,EXIT

;; Parsing

;   parse	( b u c -- b u delta ; <string> )
;		Scan string delimited by c. Return found string and its offset.

PARS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "parse"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "parse"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

PARSE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "PARSE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "PARSE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR,TIB,INN,AT,PLUS	;current input buffer pointer
		DW	NTIB,AT,INN,AT,SUBB	;remaining count
		DW	RFROM,PARS,INN,PSTOR,EXIT

;   .(		( -- )
;		Output following string up to next ) .

DOTPR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+2) + 3)}	    ;; new header. We need to move it in front of ".("'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+2)}                           ;; Name length
	DS ".("			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,{')'},PARSE,TYPEE,EXIT

;   (		( -- )
;		Ignore following string up to next ) . A comment.

PAREN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+1) + 3)}	    ;; new header. We need to move it in front of "("'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+1)}                           ;; Name length
	DS "("			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,{')'},PARSE,DDROP,EXIT

;   \		( -- )
;		Ignore following text till the end of line.

BKSLA:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+1) + 3)}	    ;; new header. We need to move it in front of "\"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+1)}                           ;; Name length
	DS "\"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	NTIB,AT,INN,STOR,EXIT

;   CHAR	( -- c )
;		Parse next word and return its first character.

CHAR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "CHAR"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "CHAR"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	BLANK,PARSE,DROP,AT,EXIT

;   TOKEN	( -- a ; <string> )
;		Parse a word from input stream and copy it to name dictionary.

TOKEN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "TOKEN"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "TOKEN"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	BLANK,PARSE,DOLIT,15,MIN
		DW	NP,AT,OVER,SUBB,CELLM
		DW	PACKS,EXIT

;   WORD	( c -- a ; <string> )
;		Parse a word from input stream and copy it to code dictionary.

WORDD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "WORD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "WORD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	PARSE,HERE,PACKS,EXIT

;; Dictionary search

;   NAME>	( na -- ca )
;		Return a code address given a name address.

NAMET:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "NAME>"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "NAME>"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	CELLM,CELLM,AT,EXIT

;   SAME?	( a a u -- a a f \ -0+ )
;		Compare u cells in two strings. Return 0 if identical.

SAMEQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "SAME?"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "SAME?"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

FIND:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "find"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "find"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

NAMEQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "NAME?"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "NAME?"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

BKSP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "^H"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "^H"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR,OVER,RFROM,SWAP,OVER,XORR
		DW	QBRAN,BACK1
		DW	DOLIT,{BKSPP},TECHO,ATEXE,DOLIT,1,SUBB
		DW	BLANK,TECHO,ATEXE
		DW	DOLIT,{BKSPP},TECHO,ATEXE
BACK1:		DW	EXIT

;   TAP		( bot eot cur c -- bot eot cur )
;		Accept and echo the key stroke and bump the cursor.

TAP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "TAP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "TAP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DUPP,TECHO,ATEXE
		DW	OVER,STOR,DOLIT,1,PLUS,EXIT

;   kTAP	( bot eot cur c -- bot eot cur )
;		Process a key stroke, CR or backspace.

KTAP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "kTAP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "kTAP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DUPP,DOLIT,{CRR},XORR
		DW	QBRAN,KTAP2
		DW	DOLIT,{BKSPP},XORR
		DW	QBRAN,KTAP1
		DW	BLANK,TAP,EXIT
KTAP1:		DW	BKSP,EXIT
KTAP2:		DW	DROP,SWAP,DROP,DUPP,EXIT

;   accept	( b u -- b u )
;		Accept characters to input buffer. Return with actual count.

ACCEP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "accept"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "accept"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

EXPEC:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "EXPECT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "EXPECT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TEXPE,ATEXE,SPAN,STOR,DROP,EXIT

;   QUERY	( -- )
;		Accept input stream to terminal input buffer.

QUERY:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "QUERY"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "QUERY"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TIB,DOLIT,80,TEXPE,ATEXE,NTIB,STOR
		DW	DROP,DOLIT,0,INN,STOR,EXIT

;; Error handling

;   CATCH	( ca -- 0 | err# )
;		Execute word at ca and set up an error frame for it.

CATCH:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "CATCH"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "CATCH"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SPAT,TOR,HANDL,AT,TOR	;save error frame
		DW	RPAT,HANDL,STOR,EXECU	;execute
		DW	RFROM,HANDL,STOR	;restore error frame
		DW	RFROM,DROP,DOLIT,0,EXIT	;no error

;   THROW	( err# -- err# )
;		Reset system to current local error frame an update error flag.

THROW:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "THROW"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "THROW"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	HANDL,AT,RPSTO		;restore return stack
		DW	RFROM,HANDL,STOR	;restore handler frame
		DW	RFROM,SWAP,TOR,SPSTO	;restore data stack
		DW	DROP,RFROM,EXIT

;   NULL$	( -- a )
;		Return address of a null string with zero count.

NULLS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "NULL$"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "NULL$"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOVAR			;emulate CREATE
		DW	0
		DW	99,111,121,111,116,101

;   ABORT	( -- )
;		Reset data stack and jump to QUIT.

ABORT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "ABORT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "ABORT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	NULLS,THROW

;   abort"	( f -- )
;		Run time routine of ABORT" . Abort with a message.

ABORQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+6) + 3)}	    ;; new header. We need to move it in front of "abort""'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+6)}                           ;; Name length
	DS "abort""			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	QBRAN,ABOR1		;text flag
		DW	DOSTR,THROW		;pass error string
ABOR1:		DW	DOSTR,DROP,EXIT		;drop error

;; The text interpreter

;   $INTERPRET	( a -- )
;		Interpret a word. If failed, try to convert it to an integer.

INTER:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (10 + 3)}	    ;; new header. We need to move it in front of "$INTERPRET"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {10}                           ;; Name length
	DS "$INTERPRET"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	NAMEQ,QDUP		;?defined
		DW	QBRAN,INTE1
		DW	AT,DOLIT,{COMPO},ANDD	;?compile only lexicon bits
	DW	ABORQ, {" compile only".length}		;;Write the function label and the length of the string
	DS  " compile only"                      ; Write the string
		DW	EXECU,EXIT		;execute defined word
INTE1:		DW	TNUMB,ATEXE		;convert a number
		DW	QBRAN,INTE2
		DW	EXIT
INTE2:		DW	THROW			;error

;   [		( -- )
;		Start the text interpreter.

LBRAC:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+1) + 3)}	    ;; new header. We need to move it in front of "["'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+1)}                           ;; Name length
	DS "["			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,INTER,TEVAL,STOR,EXIT

;   .OK		( -- )
;		Display "ok" only while interpreting.

DOTOK:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of ".OK"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS ".OK"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,INTER,TEVAL,AT,EQUAL
		DW	QBRAN,DOTO1
	DW	DOTQP, {" ok".length}		;;Write the function label and the length of the string
	DS  " ok"                      ; Write the string
DOTO1:		DW	CR,EXIT

;   ?STACK	( -- )
;		Abort if the data stack underflows.

QSTAC:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "?STACK"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "?STACK"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DEPTH,ZLESS		;check only for underflow
	DW	ABORQ, {" underflow".length}		;;Write the function label and the length of the string
	DS  " underflow"                      ; Write the string
		DW	EXIT

;   EVAL	( -- )
;		Interpret the input stream.

EVAL:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "EVAL"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "EVAL"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
EVAL1:		DW	TOKEN,DUPP,AT		;?input stream empty
		DW	QBRAN,EVAL2
		DW	TEVAL,ATEXE,QSTAC	;evaluate input, check stack
		DW	BRAN,EVAL1
EVAL2:		DW	DROP,TPROM,ATEXE,EXIT	;prompt

;; Shell

;   PRESET	( -- )
;		Reset data stack pointer and the terminal input buffer.

PRESE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "PRESET"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "PRESET"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SZERO,AT,SPSTO
		DW	DOLIT,{TIBB},NTIB,CELLP,STOR,EXIT

;   xio		( a a a -- )
;		Reset the I/O vectors 'EXPECT, 'TAP, 'ECHO and 'PROMPT.

XIO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+3) + 3)}	    ;; new header. We need to move it in front of "xio"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+3)}                           ;; Name length
	DS "xio"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,ACCEP,TEXPE,DSTOR
		DW	TECHO,DSTOR,EXIT

;   FILE	( -- )
;		Select I/O vectors for file download.

FILE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "FILE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "FILE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,PACE,DOLIT,DROP
		DW	DOLIT,KTAP,XIO,EXIT

;   HAND	( -- )
;		Select I/O vectors for terminal interface.

HAND:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "HAND"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "HAND"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,DOTOK,DOLIT,EMIT
		DW	DOLIT,KTAP,XIO,EXIT

;   I/O		( -- a )
;		Array to store default I/O vectors.

ISLO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "I/O"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "I/O"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOVAR			;emulate CREATE
		DW	QRX,TXSTO		;default I/O vectors

;   CONSOLE	( -- )
;		Initiate terminal interface.

CONSO:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "CONSOLE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "CONSOLE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	ISLO,DAT,TQKEY,DSTOR	;restore default I/O device
		DW	HAND,EXIT		;keyboard input

;   QUIT	( -- )
;		Reset return stack pointer and start text interpreter.

QUIT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "QUIT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "QUIT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	RZERO,AT,RPSTO		;reset return stack pointer
QUIT1:		DW	LBRAC			;start interpretation
QUIT2:		DW	QUERY			;get input
		DW	DOLIT,EVAL,CATCH,QDUP	;evaluate input
		DW	QBRAN,QUIT2		;continue till error
		DW	TPROM,AT,SWAP		;save input device
		DW	CONSO,NULLS,OVER,XORR	;?display error message
		DW	QBRAN,QUIT3
		DW	SPACE,COUNT,TYPEE	;error message
	DW	DOTQP, {" ? ".length}		;;Write the function label and the length of the string
	DS  " ? "                      ; Write the string
QUIT3:		DW	DOLIT,DOTOK,XORR	;?file input
		DW	QBRAN,QUIT4
		DW	DOLIT,{ERR},EMIT		;file error, tell host
QUIT4:		DW	PRESE			;some cleanup
		DW	BRAN,QUIT1

;; The compiler

;   '		( -- ca )
;		Search context vocabularies for the next word in input stream.

TICK:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "'"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "'"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOKEN,NAMEQ		;?defined
		DW	QBRAN,TICK1
		DW	EXIT			;yes, push code address
TICK1:		DW	THROW			;no, error

;   ALLOT	( n -- )
;		Allocate n bytes to the code dictionary.

ALLOT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "ALLOT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "ALLOT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	CP,PSTOR,EXIT		;adjust code pointer

;   ,		( w -- )
;		Compile an integer into the code dictionary.

COMMA:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of ","'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS ","			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	HERE,DUPP,CELLP		;cell boundary
		DW	CP,STOR,STOR,EXIT	;adjust code pointer, compile

;   [COMPILE]	( -- ; <string> )
;		Compile the next immediate word into code dictionary.

BCOMP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+9) + 3)}	    ;; new header. We need to move it in front of "[COMPILE]"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+9)}                           ;; Name length
	DS "[COMPILE]"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TICK,COMMA,EXIT

;   COMPILE	( -- )
;		Compile the next address in colon list to code dictionary.

COMPI:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((COMPO+7) + 3)}	    ;; new header. We need to move it in front of "COMPILE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(COMPO+7)}                           ;; Name length
	DS "COMPILE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	RFROM,DUPP,AT,COMMA	;compile address
		DW	CELLP,TOR,EXIT		;adjust return address

;   LITERAL	( w -- )
;		Compile tos to code dictionary as an integer literal.

LITER:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+7) + 3)}	    ;; new header. We need to move it in front of "LITERAL"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+7)}                           ;; Name length
	DS "LITERAL"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,DOLIT,COMMA,EXIT

;   $,"		( -- )
;		Compile a literal string up to next " .

STRCQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "$,""'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "$,""			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,{'"'},WORDD		;move string to code dictionary
		DW	COUNT,PLUS,ALGND	;calculate aligned end of string
		DW	CP,STOR,EXIT		;adjust the code pointer

;   RECURSE	( -- )
;		Make the current word available for compilation.

RECUR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+7) + 3)}	    ;; new header. We need to move it in front of "RECURSE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+7)}                           ;; Name length
	DS "RECURSE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	LAST,AT,NAMET,COMMA,EXIT

;; Structures

;   FOR		( -- a )
;		Start a FOR-NEXT loop structure in a colon definition.

FOR:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+3) + 3)}	    ;; new header. We need to move it in front of "FOR"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+3)}                           ;; Name length
	DS "FOR"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,TOR,HERE,EXIT

;   BEGIN	( -- a )
;		Start an infinite or indefinite loop structure.

BEGIN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+5) + 3)}	    ;; new header. We need to move it in front of "BEGIN"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+5)}                           ;; Name length
	DS "BEGIN"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	HERE,EXIT

;   NEXT	( a -- )
;		Terminate a FOR-NEXT loop structure.

NEXT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+4) + 3)}	    ;; new header. We need to move it in front of "NEXT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+4)}                           ;; Name length
	DS "NEXT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,DONXT,COMMA,EXIT

;   UNTIL	( a -- )
;		Terminate a BEGIN-UNTIL indefinite loop structure.

UNTIL:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+5) + 3)}	    ;; new header. We need to move it in front of "UNTIL"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+5)}                           ;; Name length
	DS "UNTIL"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,QBRAN,COMMA,EXIT

;   AGAIN	( a -- )
;		Terminate a BEGIN-AGAIN infinite loop structure.

AGAIN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+5) + 3)}	    ;; new header. We need to move it in front of "AGAIN"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+5)}                           ;; Name length
	DS "AGAIN"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,BRAN,COMMA,EXIT

;   IF		( -- A )
;		Begin a conditional branch structure.

IFF:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+2) + 3)}	    ;; new header. We need to move it in front of "IF"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+2)}                           ;; Name length
	DS "IF"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,QBRAN,HERE
		DW	DOLIT,0,COMMA,EXIT

;   AHEAD	( -- A )
;		Compile a forward branch instruction.

AHEAD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+5) + 3)}	    ;; new header. We need to move it in front of "AHEAD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+5)}                           ;; Name length
	DS "AHEAD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,BRAN,HERE,DOLIT,0,COMMA,EXIT

;   REPEAT	( A a -- )
;		Terminate a BEGIN-WHILE-REPEAT indefinite loop.

REPEA:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+6) + 3)}	    ;; new header. We need to move it in front of "REPEAT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+6)}                           ;; Name length
	DS "REPEAT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	AGAIN,HERE,SWAP,STOR,EXIT

;   THEN	( A -- )
;		Terminate a conditional branch structure.

THENN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+4) + 3)}	    ;; new header. We need to move it in front of "THEN"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+4)}                           ;; Name length
	DS "THEN"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	HERE,SWAP,STOR,EXIT

;   AFT		( a -- a A )
;		Jump to THEN in a FOR-AFT-THEN-NEXT loop the first time through.

AFT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+3) + 3)}	    ;; new header. We need to move it in front of "AFT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+3)}                           ;; Name length
	DS "AFT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DROP,AHEAD,BEGIN,SWAP,EXIT

;   ELSE	( A -- A )
;		Start the false clause in an IF-ELSE-THEN structure.

ELSEE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+4) + 3)}	    ;; new header. We need to move it in front of "ELSE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+4)}                           ;; Name length
	DS "ELSE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	AHEAD,SWAP,THENN,EXIT

;   WHILE	( a -- A a )
;		Conditional branch out of a BEGIN-WHILE-REPEAT loop.

WHILE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+5) + 3)}	    ;; new header. We need to move it in front of "WHILE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+5)}                           ;; Name length
	DS "WHILE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	IFF,SWAP,EXIT

;   ABORT"	( -- ; <string> )
;		Conditional abort with an error message.

ABRTQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+6) + 3)}	    ;; new header. We need to move it in front of "ABORT""'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+6)}                           ;; Name length
	DS "ABORT""			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,ABORQ,STRCQ,EXIT

;   $"		( -- ; <string> )
;		Compile an inline string literal.

STRQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+2) + 3)}	    ;; new header. We need to move it in front of "$""'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+2)}                           ;; Name length
	DS "$""			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,STRQP,STRCQ,EXIT

;   ."		( -- ; <string> )
;		Compile an inline string literal to be typed out at run time.

DOTQ:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+2) + 3)}	    ;; new header. We need to move it in front of ".""'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+2)}                           ;; Name length
	DS ".""			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,DOTQP,STRCQ,EXIT

;; Name compiler

;   ?UNIQUE	( a -- a )
;		Display a warning message if the word already exists.

UNIQU:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (7 + 3)}	    ;; new header. We need to move it in front of "?UNIQUE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {7}                           ;; Name length
	DS "?UNIQUE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DUPP,NAMEQ		;?name exists
		DW	QBRAN,UNIQ1		;redefinitions are OK
	DW	DOTQP, {" reDef ".length}		;;Write the function label and the length of the string
	DS  " reDef "                      ; Write the string
		DW	OVER,COUNT,TYPEE	;just in case its not planned
UNIQ1:		DW	DROP,EXIT

;   $,n		( na -- )
;		Build a new dictionary name using the string at na.

SNAME:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "$,n"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "$,n"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DUPP,AT		;?null input
		DW	QBRAN,PNAM1
		DW	UNIQU			;?redefinition
		DW	DUPP,LAST,STOR		;save na for vocabulary link
		DW	HERE,ALGND,SWAP		;align code address
		DW	CELLM			;link address
		DW	CRRNT,AT,AT,OVER,STOR
		DW	CELLM,DUPP,NP,STOR	;adjust name pointer
		DW	STOR,EXIT		;save code pointer
	PNAM1: DW	STRQP, {" name".length}		;;Write the function label and the length of the string
	DS  " name"                      ; Write the string
		DW	THROW

;; FORTH compiler

;   $COMPILE	( a -- )
;		Compile next word to code dictionary as a token or literal.

SCOMP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (8 + 3)}	    ;; new header. We need to move it in front of "$COMPILE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {8}                           ;; Name length
	DS "$COMPILE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

OVERT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "OVERT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "OVERT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	LAST,AT,CRRNT,AT,STOR,EXIT

;   ;		( -- )
;		Terminate a colon definition.

SEMIS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - ((IMEDD+COMPO+1) + 3)}	    ;; new header. We need to move it in front of ";"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {(IMEDD+COMPO+1)}                           ;; Name length
	DS ";"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	COMPI,EXIT,LBRAC,OVERT,EXIT

;   ]		( -- )
;		Start compiling the words in the input stream.

RBRAC:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of "]"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS "]"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,SCOMP,TEVAL,STOR,EXIT

;   call,	( ca -- )
;		Assemble a call instruction to ca.

CALLC:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "call,"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "call,"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,0x4040,DOLST,COMMA,HERE	;Hard coding - in binary CALL SP, DOLST
		DW	CELLP,SUBB,COMMA,EXIT

;   :		( -- ; <string> )
;		Start a new colon definition using next word as its name.

COLON:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (1 + 3)}	    ;; new header. We need to move it in front of ":"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {1}                           ;; Name length
	DS ":"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOKEN,SNAME,DOLIT,DOLST
		DW	CALLC,RBRAC,EXIT

;   IMMEDIATE	( -- )
;		Make the last compiled word an immediate word.

IMMED:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (9 + 3)}	    ;; new header. We need to move it in front of "IMMEDIATE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {9}                           ;; Name length
	DS "IMMEDIATE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,{IMEDD},LAST,AT,AT,ORR
		DW	LAST,AT,STOR,EXIT

;; Defining words

;   USER	( u -- ; <string> )
;		Compile a new user variable.

USER:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "USER"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "USER"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOKEN,SNAME,OVERT
		DW	DOLIT,DOLST,CALLC
		DW	COMPI,DOUSE,COMMA,EXIT

;   CREATE	( -- ; <string> )
;		Compile a new array entry without allocating code space.

CREAT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (6 + 3)}	    ;; new header. We need to move it in front of "CREATE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {6}                           ;; Name length
	DS "CREATE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOKEN,SNAME,OVERT
		DW	DOLIT,DOLST,CALLC
		DW	COMPI,DOVAR,EXIT

;   VARIABLE	( -- ; <string> )
;		Compile a new variable initialized to 0.

VARIA:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (8 + 3)}	    ;; new header. We need to move it in front of "VARIABLE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {8}                           ;; Name length
	DS "VARIABLE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	CREAT,DOLIT,0,COMMA,EXIT

;; Tools

;   _TYPE	( b u -- )
;		Display a string. Filter non-printing characters.

UTYPE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "_TYPE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "_TYPE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	TOR			;start count down loop
		DW	BRAN,UTYP2		;skip first pass
UTYP1:		DW	DUPP,AT,TCHAR,EMIT	;display only printable
		DW	DOLIT,1,PLUS		;increment address
UTYP2:		DW	DONXT,UTYP1		;loop till done
		DW	DROP,EXIT

;   dm+		( a u -- a )
;		Dump u bytes from , leaving a+u on the stack.

DMP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "dm+"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "dm+"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	OVER,DOLIT,4,UDOTR	;display address
		DW	SPACE,TOR		;start count down loop
		DW	BRAN,PDUM2		;skip first pass
PDUM1:		DW	DUPP,AT,DOLIT,3,UDOTR	;display numeric data
		DW	DOLIT,1,PLUS		;increment address
PDUM2:		DW	DONXT,PDUM1		;loop till done
		DW	EXIT

;   DUMP	( a u -- )
;		Dump u bytes from a, in a formatted manner.

DUMP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "DUMP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "DUMP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

DOTS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of ".S"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS ".S"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	CR,DEPTH		;stack depth
		DW	TOR			;start count down loop
		DW	BRAN,DOTS2		;skip first pass
DOTS1:		DW	RAT,PICK,DOT		;index stack, display contents
DOTS2:		DW	DONXT,DOTS1		;loop till done
	DW	DOTQP, {" <sp".length}		;;Write the function label and the length of the string
	DS  " <sp"                      ; Write the string
		DW	EXIT

;   !CSP	( -- )
;		Save stack pointer in CSP for error checking.

STCSP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "!CSP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "!CSP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SPAT,CSP,STOR,EXIT	;save pointer

;   ?CSP	( -- )
;		Abort if stack pointer differs from that saved in CSP.

QCSP:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "?CSP"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "?CSP"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	SPAT,CSP,AT,XORR	;compare pointers
	DW	ABORQ, {"stacks".length}		;;Write the function label and the length of the string
	DS  "stacks"                      ; Write the string
		DW	EXIT

;   >NAME	( ca -- na | F )
;		Convert code address to a name address.

TNAME:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of ">NAME"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS ">NAME"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

DOTID:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of ".ID"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS ".ID"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	QDUP			;if zero no name
		DW	QBRAN,DOTI1
		DW	COUNT,DOLIT,0x01F,ANDD	;mask lexicon bits
		DW	UTYPE,EXIT		;display name string
	DOTI1: DW	DOTQP, {" noName".length}		;;Write the function label and the length of the string
	DS  " noName"                      ; Write the string
		DW	EXIT

;   SEE		( -- ; <string> )
;		A simple decompiler.

SEE:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "SEE"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "SEE"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

WORDS:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "WORDS"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "WORDS"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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

VERSN:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (3 + 3)}	    ;; new header. We need to move it in front of "VER"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {3}                           ;; Name length
	DS "VER"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOLIT,{VER*256+EXT},EXIT

;   hi		( -- )
;		Display the sign-on message of eForth.

HI:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (2 + 3)}	    ;; new header. We need to move it in front of "hi"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {2}                           ;; Name length
	DS "hi"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	STOIO,CR		;initialize I/O
	DW	DOTQP, {"eForth v".length}		;;Write the function label and the length of the string
	DS  "eForth v"                      ; Write the string
		DW	BASE,AT,HEX		;save radix
		DW	VERSN,BDIGS,DIG,DIG
		DW	DOLIT,{'.'},HOLD
		DW	DIGS,EDIGS,TYPEE	;format version number
		DW	BASE,STOR,CR,EXIT	;restore radix

;   'BOOT	( -- a )
;		The application startup vector.

TBOOT:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (5 + 3)}	    ;; new header. We need to move it in front of "'BOOT"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {5}                           ;; Name length
	DS "'BOOT"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
		DW	DOVAR
		DW	HI			;application to boot

;   COLD	( -- )
;		The hilevel cold start sequence.

COLD:
	{_CODE	= $}				        ;; save code pointer
	{_NAME	= _NAME - (4 + 3)}	    ;; new header. We need to move it in front of "COLD"'s length, and additional space for the 3 words we're declaring in front of it. Use subtraction since _NAME grows downwards
    ORG	{_NAME}					        ;; set name pointer
	DW {_CODE},{_LINK}			        ;; token pointer and previous link?
	{_LINK	= $}				        ;; Update _LINK so it points to a new name string
	DW {4}                           ;; Name length
	DS "COLD"			                ;; name string
    ORG	{_CODE}					        ;; restore code pointer
	CALL	SP, DOLST				;;Call doList and push the current PC into the data stack (SP)
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



