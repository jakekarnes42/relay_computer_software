package org.karnes.homebrew.emulator.isa;

import org.karnes.homebrew.bitset.BitSet16;
import org.karnes.homebrew.emulator.component.register.RegisterName;
import org.karnes.homebrew.emulator.component.register.StackRegisterName;

/**
 * Big scary boolean logic to convert from binary to an {@link Instruction}
 */
public class InstructionDecoder {
    public Instruction decode(BitSet16 binary) {
        /*
         * Check all the easy ones first
         */
        if (binary.equals(new BitSet16())) {
            return new NOPInstruction();
        }

        if (binary.equals(new BitSet16("1111 1111 11 111 111"))) {
            return new HALTInstruction();
        }

        if (binary.equals(new BitSet16("0010 0001 0000 0 111"))) {
            return new JMPInstruction();
        }

        if (binary.equals(new BitSet16("1000 0000 0000 0000"))) {
            return new TINInstruction();
        }

        if (binary.equals(new BitSet16("1000 1000 0000 0000"))) {
            return new TOUTInstruction();
        }

        //Split on the top-most bit
        if (binary.get(15)) {
            //Check the second-top-most bit
            if (binary.get(14)) {
                //Must be a MOV 1111 1111 11 ddd sss
                RegisterName dest = RegisterName.fromBitSet(binary.getSlice(3, 6));
                RegisterName src = RegisterName.fromBitSet(binary.getSlice(0, 3));
                return new MOVInstruction(dest, src);

            } else {
                //Must be I/O
                if (binary.get(11)) {
                    //WRDOUT 1000 1000 0000 1 sss
                    RegisterName src = RegisterName.fromBitSet(binary.getSlice(0, 3));
                    return new WRDOUTInstruction(src);
                } else {
                    //WRDIN 1000 0000 0000 1 ddd
                    RegisterName dest = RegisterName.fromBitSet(binary.getSlice(0, 3));
                    return new WRDINInstruction(dest);
                }

            }


        } else {
            //Check the second-top-most bit
            if (binary.get(14)) {
                //Must be stack based instruction
                StackRegisterName stack = StackRegisterName.fromBitSet(binary.getSlice(3, 4));
                RegisterName reg = RegisterName.fromBitSet(binary.getSlice(0, 3));
                if (binary.get(4)) {
                    //PUSH  0100 0000 0001 d sss
                    return new PUSHInstruction(stack, reg);
                } else {
                    if (binary.get(5)) {
                        //Some kind of POP
                        if (reg == RegisterName.PC) {
                            //RET   0100 0000 0010 s 111
                            return new RETInstruction(stack);
                        } else {
                            //POP   0100 0000 0010 s ddd
                            return new POPInstruction(reg, stack);
                        }
                    } else {
                        //CALL  0100 0000 0100 d 000
                        return new CALLInstruction(stack);
                    }
                }
            } else {
                if (binary.get(13)) {
                    //Must be memory instruction
                    if (binary.get(8)) {
                        //LOAD 0010 0001 0000 0 ddd
                        RegisterName dest = RegisterName.fromBitSet(binary.getSlice(0, 3));
                        return new LOADInstruction(dest);
                    } else {
                        RegisterName dest = RegisterName.fromBitSet(binary.getSlice(3, 6));
                        RegisterName src = RegisterName.fromBitSet(binary.getSlice(0, 3));
                        if (binary.get(9)) {
                            //Store 0010 0010 00 ddd sss
                            return new STOREInstruction(dest, src);
                        } else {
                            //Fetch 0010 0100 00 ddd sss
                            return new FETCHInstruction(dest, src);
                        }
                    }
                } else {
                    if (binary.get(12)) {
                        // Must be ALU instruction ALU 0001 fff ddd aaa bbb
                        RegisterName dest = RegisterName.fromBitSet(binary.getSlice(6, 9));
                        RegisterName src2 = RegisterName.fromBitSet(binary.getSlice(3, 6));
                        RegisterName src1 = RegisterName.fromBitSet(binary.getSlice(0, 3));

                        if (binary.get(11)) {
                            //Must be LU instruction
                            if (binary.get(10)) {
                                //Must be AND or NOT
                                if (binary.get(9)) {
                                    //Must be NOT
                                    return new NOTInstruction(dest, src2);
                                } else {
                                    //Must be AND
                                    return new ANDInstruction(dest, src2, src1);
                                }
                            } else {
                                //Must be a type of OR
                                if (binary.get(9)) {
                                    //Must be OR
                                    return new ORInstruction(dest, src2, src1);
                                } else {
                                    //Must be XOR
                                    return new XORInstruction(dest, src2, src1);
                                }
                            }
                        } else {
                            //Must be AU instruction
                            if (binary.get(10)) {
                                //Must be a type of subtraction
                                if (binary.get(9)) {
                                    //Must be DEC
                                    return new DECInstruction(dest, src2);
                                } else {
                                    //Must be SUB
                                    return new SUBInstruction(dest, src2, src1);
                                }
                            } else {
                                //Must be a type of addition
                                if (binary.get(9)) {
                                    //Must be INC
                                    return new INCInstruction(dest, src2);
                                } else {
                                    //Must be ADD
                                    return new ADDInstruction(dest, src2, src1);
                                }
                            }
                        }

                    } else {
                        //Must be a conditional jump
                        // CJMP 0000 1000 000 n cosz
                        ConditionalJumpType jumpType = ConditionalJumpType.fromBitSet(binary.getSlice(0, 5));
                        return new ConditionalJMPInstruction(jumpType);
                    }
                }

            }
        }

    }
}
