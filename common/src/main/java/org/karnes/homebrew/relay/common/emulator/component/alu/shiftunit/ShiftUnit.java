package org.karnes.homebrew.relay.common.emulator.component.alu.shiftunit;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.ConditionCode;
import org.karnes.homebrew.relay.common.emulator.component.alu.ALUComponent;

/**
 * A virtual component of the typical ASU which performs binary shift operations.
 */
public class ShiftUnit extends ALUComponent {
    /**
     * Creates a new SU which can operate data of the specified width.
     *
     * @param width The width of the data
     */
    public ShiftUnit(int width) {
        super("ShiftUnit", width);
    }

    @Override
    protected void performOperation(FixedBitSet opcode, FixedBitSet tmp1Value, FixedBitSet tmp2Value) {

        //Parse the current operation
        boolean toLeft = opcode.get(0);
        boolean withCarry = opcode.get(1);

        //The CCR should be copied into TMP2, meaning the carry flag is in the 4th bit from the right.
        boolean carryIn = tmp2Value.get(3);

        FixedBitSet result = new FixedBitSet(width);
        for (int i = 0; i < width; i++) {
            boolean newBitValue = getNewBitValue(tmp1Value, carryIn, toLeft, withCarry, i);

            result = result.set(i, newBitValue);
        }

        //Set up condition codes
        boolean zero = result.equals(new FixedBitSet(width));
        boolean sign = result.get(width - 1);
        boolean carryOut = withCarry ? tmp1Value.get(toLeft ? width - 1 : 0) : false;
        ConditionCode conditionCodes = new ConditionCode(carryOut, false, sign, zero);

        //Set the output
        outputBusConnection.writeValue(result);
        ccBusConnection.writeValue(conditionCodes.toBitSet());
    }

    private boolean getNewBitValue(FixedBitSet input, boolean carry, boolean toLeft, boolean withCarry, int position) {
        //Handle MSB separately
        if (position == width - 1) {

            if (toLeft) {
                //ROL MSB = bit 14
                //RCL MSB = bit 14
                return input.get(position - 1);
            } else {
                if (withCarry) {
                    //RCR MSB = CARRY
                    return carry;
                } else {
                    //ROR MSB = LSB (bit 0)
                    return input.get(0);
                }
            }

        }

        //Handle LSB separately
        if (position == 0) {
            if (!toLeft) {
                //ROR LSB = bit 1
                //RCR LSB = bit 1
                return input.get(1);
            } else {
                if (withCarry) {
                    //RCL LSB = CARRY
                    return carry;
                } else {
                    //ROL LSB = MSB (bit 15)
                    return input.get(width - 1);
                }
            }
        }

        //Handle between bits
        return input.get((toLeft ? position - 1 : position + 1));
    }

}


