# Relay Computer (Software)
This repo contains the software information for a computer constructed out of relays. Its hardware companion project can be found [here](https://github.com/jakekarnes42/relay_computer_hardware). 

The documentation is still a work-in-progress and is likely to change as the design itself changes. 

# Instruction Set
## Harry’s Instructions:
See documentation here: https://web.cecs.pdx.edu/~harry/Relay/RelayPaper.htm

## My 16 bit Instructions (v0.4):
All instruction opcodes limited to one word (16 bits or 2 bytes)
### NOP: Continue without updates	
Format: `00000000 00000000`

This instruction continues execution of the program without any changes. 

### MOV: The 16-Bit Move Instruction
Format: `1111 1111 11  ddd sss`

This instruction moves the contents of one of the 16-bit registers (sss) to any other register (ddd).
 
If the “source” and “destination”  register happen to be the same, this instruction will set that register to zero.  In many computers, this function is called the “CLEAR” instruction.

When PC is used as the destination, this provides unconditional jumps
 
Register Codes (ddd and sss):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC
### HALT: End execution	
Format: `1111 1111 1111 1111 `
This instruction ends execution of the program. This is effectively, CLEAR PC, PC
### Single Operand ALU Instruction:
Format: `0001 0000 ff ddd sss`

This instruction applies the function (ff) to the value in the register (sss), and storing the result in ddd. This is an inline replacement. Internally, this value will first be moved to the TMP1 register, which is connected to the ALU. 

This function updates the status register based on the results. 

Function Code (ff):
00 = INC
01 = DEC
10 = NOT
11 = ROL (rotate one bit left)

Register Codes (ddd and sss):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC

### Dual Operand ALU Instruction:
Format: `0001 1 ff ddd aaa bbb`

This instruction applies the function (ff) to the operands (aaa and bbb) and stores the result into ddd. Internally, the operands are moved to the TMP1 and TMP2 registers, which are connected to the ALU. 

This function updates the status register based on the results. 

Function Code (ff):
00 = ADD
01 = AND
10 = OR
11 = XOR

Register Codes (ddd, aaa and bbb):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC

### CMP/SUB subtraction:
Format: `0001 010 ddd aaa bbb`

Performs aaa - bbb → ddd. This must set the flags as well. This can be used as a subtraction or as a comparison.

Register Codes (ddd, aaa and bbb):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC


### LOAD: Immediately load word from memory to register
Format: `0010 0001 0000 0 ddd vvvvvvvv vvvvvvvv`

This instruction takes the immediate next word in memory (vvvvvvvv vvvvvvvv) and stores it into the destination register defined by ddd. 

Note that PC is omitted from the list, see below.

Register Codes (ddd):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP

Note that PC is omitted intentionally.
### JMP (unconditional): Jump to the address of following word in memory 
Format: `0010 0001 0000 0 111 vvvvvvvv vvvvvvvv`

Basically LOAD for PC

This instruction takes the immediate next word in memory (vvvvvvvv vvvvvvvv) and moves it into PC. This causes an unconditional Jump.
### Conditional Jumps: JMP to address if flag
Format:  `0000 1000 000 n zsoc vvvvvvvv vvvvvvvv`

This instruction takes the immediate next word in memory (vvvvvvvv vvvvvvvv) and moves it into PC, conditionally. The JMP will occur if the condition registers are storing a ‘1’ for any of the condition codes (zsoc) in the instruction which also contain ‘1’. Multiple condition registers can be tested in a single instruction by having multiple condition codes turned on in the instruction. 

The n bit negates. That is, if the n bit is ‘1’, then the opposite logic will enforced. 

Expressed as a boolean function, this equates to:
JMP ≡ (n) ⊕ ( (z ∧ zero_reg) ∨ (s ∧ sign_reg) ∨ (o ∧ overflow_reg) ∨ (c ∧ carry_reg) )

Condition Codes (nzsoc):
	n = negation of logic
	z = zero
s = sign
o = overflow
c = carry

### STORE: Store register value into memory
Format: `0010 0010 00 ddd  sss`

This instruction moves the value from sss into memory at the address pointed to by ddd.

Register Codes (ddd and sss):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC

### FETCH: Load word from memory to register
Format: `0010 0100 00 ddd sss`

Moves the value in memory at the address pointed to by sss into ddd.

Register Codes (ddd and sss):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC
### PUSH: Push value of register onto stack
Format:  `0100 0000 0001 d sss`
Increments the destination stack register (d),  then stores the value from the source register (sss) into the memory address now pointed to by the destination register ([d]).

Stack codes (d):
	0 = SP
	1 = RP

Register Codes (sss):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC
### POP: Pop value off of stack into register
Format: `0100 0000 0010 s ddd`

Retrieves the value from the memory address pointed to by the stack register ([s]), decrements the stack register (s), and inserts the value into the destination register (ddd).

Note: When PC is the destination, this can be used like a RET instruction

Stack codes (s):
	0 = SP
	1 = RP

Register Codes (ddd):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC


### CALL: Push PC to stack and Jump Immediate
Format: `0100 0000 0100 d 000 vvvvvvvv vvvvvvvv`

Increments the stack register ([d]), and stores the value from the PC register into the memory address now pointed to by the stack register ([d]). Then it takes the immediate next word in memory (vvvvvvvv vvvvvvvv) and moves it into PC.

Stack codes (d):
	0 = SP
	1 = RP

### WRDIN: RECEIVE I/O WORD
Format: `1000 0000 0000 0 ddd`

This instruction attempts to receive a word from the I/O device, and store it into the destination register ddd. If there is no content, the destination register’s value is set to zero and the zero flag is set true. 

Register Codes (ddd):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC

### WRDOUT: SEND I/O WORD
Format: `1000 1000 0000 0 sss`

This instruction attempts to send a word to the I/O device from the source register sss.

Register Codes (sss):
            000 = AX
            001 = BX
            010 = CX
            011 = DX
            100 = EX
            101 = SP
            110 = RP
            111 = PC
# Assembler Notes
## Instructions:
### MOV-based Instructions
The instructions in this group are based on the MOV function, regarding moving values between registers. 
#### MOV DST , SRC
Move value from SRC register to DST register. If DST and SRC are the same register, then the register is cleared and its value is set to 0 (see CLR instruction). If DST and SRC are both the PC register, this halts instruction execution (see HALT instruction).
#### CLR REG
Clear the given register and set its value to zero. This is an alias for the MOV instruction in the case where DST and SRC are the same register (e.g. MOV AX , AX). If REG is PC, this halts instruction execution (see HALT instruction).
#### HALT
Ends program execution. Once this instruction is executed, no further instructions should be executed. This an alias for MOV PC, PC and CLR PC. 

### ALU-based Instructions
The results of the instructions in this group are calculated through the computer’s ALU. The condition registers will be updated appropriately based on the result. 
#### INC DST , SRC
Increment the value in the SRC register by one, and store the result into DST. This is equivalent to “DST = SRC + 1” in most C-based languages. DST and SRC may refer to the same register, in which case the instruction is equivalent to “SRC++” in most C-based languages.
#### DEC DST , SRC
Decrement the value in the SRC register by one, and store the result into DST. This is equivalent to “DST = SRC - 1” in most C-based languages. DST and SRC may refer to the same register, in which case the instruction is equivalent to “SRC--” in most C-based languages.
#### NOT DST , SRC
Perform the bitwise Not of the SRC register’s value, and store the result into DST. This is equivalent to “DST = ~SRC” in most C-based languages. DST and SRC may refer to the same register.
#### ROL DST , SRC
Perform a left circular shift (or bitwise left rotation) of the SRC register’s value, and store the result into DST. Unlike an arithmetic shift, a circular shift does not preserve a number's sign bit or distinguish a number's exponent from its significand (sometimes referred to as the mantissa). Unlike a logical shift, the vacant bit positions are not filled in with zeros but are filled in with the bits that are shifted out of the sequence.

DST and SRC may refer to the same register.
#### ADD DST , SRC1, SRC2
Perform the addition of SRC1 register’s value and SRC2 register’s value, and store the result into DST. This is equivalent to “DST = SRC1 + SRC2” in most C-based languages. SRC1 and SRC2 may refer to the same register, in which case the instruction is equivalent to “DST = SRC * 2” in most C-based languages. 
#### AND DST, SRC1, SRC2
Perform a bitwise And of SRC1 register’s value and SRC2 register values, and store the result into DST. This is equivalent to “DST = SRC1 & SRC2” in most C-based languages. SRC1, SRC2 and DST may refer to the same register.
#### OR DST, SRC1, SRC2
Perform a bitwise Or of SRC1 register’s value and SRC2 register values, and store the result into DST. This is equivalent to “DST = SRC1 | SRC2” in most C-based languages. SRC1, SRC2 and DST may refer to the same register.
#### XOR DST, SRC1, SRC2
Perform a bitwise Xor of SRC1 register’s value and SRC2 register values, and store the result into DST. This is equivalent to “DST = SRC1 ^ SRC2” in most C-based languages. SRC1, SRC2 and DST may refer to the same register.
#### SUB DST, SRC1, SRC2
Perform the subtraction of SRC2 register’s value from SRC1 register’s values, and store the result into DST. This is equivalent to “DST = SRC1 - SRC2” in most C-based languages. SRC1, SRC2 and DST may refer to the same register.
#### CMP DST, SRC1, SRC2
See the SUB instruction above. This instruction is simply an alias for SUB. 
### Memory-based Instructions
The instructions in this group handling moving values into and out of the computer’s memory. The condition registers will not be updated by any of these instructions. 
#### LOAD  DST , [LABEL | CONST]
Loads a 16-bit value into DST register from memory. The 16 bit value may be an address label, or a constant value. If a value is loaded into PC, this instruction will perform an unconditional jump to that value in memory. See JMP instruction below.
#### STORE DST , SRC
Stores the SRC register’s 16-bit value into the memory address pointed to by the DST register. 
#### FETCH DST , SRC
Fetches the 16-bit value at the memory address pointed to by the SRC register, and stores that value into the DST register. 
### Stack-based Instructions
The instructions in this group handling moving values onto and off of the computer’s two stacks. The two stack pointers are the SP and RP registers. The condition registers will not be updated by any of these instructions. 
#### PUSH STK , SRC
Increments the destination stack register STK (SP or RP), then stores the value from the source register (SRC) into the memory address now pointed to by the destination stack register.

Before invoking this instruction, the stack register should be updated to point to a clear section of memory. By default, both stack registers have an initial value of 0. If the PUSH instruction is used before these are updated, other memory values may be unintentionally overwritten.
#### POP DST , STK
Fetches the value from the top of the stack for the source stack register STK (SP or RP), moves that value into the DST register, and decrements the stack pointer.

Care should be taken to ensure that the POP instruction isn’t executed more times than the PUSH instruction. 

If the destination register is PC, this will also perform an unconditional jump. RET is an alias for this operation.
#### CALL STK, [LABEL | CONST]
This instruction is used to invoke a subroutine. It should be paired with a RET instruction.
#### RET STK
This instruction returns from a subroutine. 
