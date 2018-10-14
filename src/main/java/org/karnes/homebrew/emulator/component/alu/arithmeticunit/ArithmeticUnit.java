package org.karnes.homebrew.emulator.component.alu.arithmeticunit;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.alu.ALUComponent;

/**
 * A virtual half of the typical ALU which performs addition and subtraction operations.
 */
public class ArithmeticUnit extends ALUComponent {


    public ArithmeticUnit(int width) {
        super("ArithmeticUnit", width);
    }


    /*
     * This method attempts to follow the same steps/transformations taken by the hardware.
     * Using the signal names as variables, that's why the naming convention are strange here.
     */
    @Override
    protected void performOperation(FixedBitSet opcode, FixedBitSet A, FixedBitSet B) {
        //Transform the B according to the opcode
        boolean INC_DEC = opcode.get(0);
        if (INC_DEC) {
            //If the INC_DEC line is high, the B is replaced with the number 00..01
            B = new FixedBitSet(width).set(0, true);
        }

        //We XOR the B with the SUB signal
        boolean SUB = opcode.get(1);
        for (int i = 0; i < width; i++) {
            boolean Bi = B.get(i);
            B = B.set(i, Bi ^ SUB);
        }

        //Set up some variables for use later
        FixedBitSet SUM = new FixedBitSet(width);
        boolean CIN = SUB; //Initialize CIN with the SUB signal
        boolean COUT = false;
        boolean penultimateCOUT = false;

        //Now we perform the addition on A and B
        for (int i = 0; i < width; i++) {
            //Get the bits
            boolean Ai = A.get(i);
            boolean Bi = B.get(i);

            //combinatorial logic
            if (!Ai && !Bi && !CIN) {
                COUT = false;
                SUM = SUM.set(i, false);
            } else if (!Ai && !Bi && CIN) {
                COUT = false;
                SUM = SUM.set(i, true);
            } else if (!Ai && Bi && !CIN) {
                COUT = false;
                SUM = SUM.set(i, true);
            } else if (!Ai && Bi && CIN) {
                COUT = true;
                SUM = SUM.set(i, false);
            } else if (Ai && !Bi && !CIN) {
                COUT = false;
                SUM = SUM.set(i, true);
            } else if (Ai && !Bi && CIN) {
                COUT = true;
                SUM = SUM.set(i, false);
            } else if (Ai && Bi && !CIN) {
                COUT = true;
                SUM = SUM.set(i, false);
            } else if (Ai && Bi && CIN) {
                COUT = true;
                SUM = SUM.set(i, true);
            } else {
                throw new IllegalStateException("Something is wrong with combinatorial logic!");
            }

            //On the second to last bit, we need to save the COUT for later comparison
            if (i == width - 2) {
                penultimateCOUT = COUT;
            }

            //Set up for the next iteration
            CIN = COUT;
        }

        //Check for condition codes

        //Zero detection
        boolean ZERO = SUM.equals(new FixedBitSet(width));
        //SIGN is the highest bit of the SUM
        boolean SIGN = SUM.get(width - 1);
        //OVERFLOW is COUT xor'd with the second to last COUT
        boolean OVERFLOW = COUT ^ penultimateCOUT;
        //CARRY is COUT xor'd with sub
        boolean CARRY = COUT ^ SUB;

        ConditionCode conditionCode = new ConditionCode(CARRY, OVERFLOW, SIGN, ZERO);


        //Set the output
        outputBusConnection.writeValue(SUM);
        ccBusConnection.writeValue(conditionCode.toBitSet());

    }

}


