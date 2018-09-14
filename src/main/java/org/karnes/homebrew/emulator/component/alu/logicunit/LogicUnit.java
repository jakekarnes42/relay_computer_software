package org.karnes.homebrew.emulator.component.alu.logicunit;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.alu.ALUComponent;
import org.karnes.homebrew.emulator.component.bus.ReadableBus;
import org.karnes.homebrew.emulator.component.bus.WriteableBus;

/**
 * A virtual half of the typical ALU which performs binary logic operations.
 */
public class LogicUnit extends ALUComponent {

    public LogicUnit(ReadableBus opcodeBus, ReadableBus tmp1Bus, ReadableBus tmp2Bus, WriteableBus outputBus, WriteableBus ccBus) {
        super(opcodeBus, tmp1Bus, tmp2Bus, outputBus, ccBus);
    }


    @Override
    protected void performOperation(FixedBitSet opcode, FixedBitSet tmp1Value, FixedBitSet tmp2Value) {
        //Interpret the current operation
        LU_OPCODE currentOperation = LU_OPCODE.fromBitSet(opcode);

        // Execute the logic based on the current opcode
        FixedBitSet result;
        switch (currentOperation) {
            case XOR:
                result = performXOR(tmp1Value, tmp2Value);
                break;
            case OR:
                result = performOR(tmp1Value, tmp2Value);
                break;
            case AND:
                result = performAND(tmp1Value, tmp2Value);
                break;
            case NOT:
                result = performNOT(tmp1Value);
                break;
            default: //Should not reach
                throw new IllegalStateException("Unexpected LU opcode: " + currentOperation);

        }

        //Get the appropriate condition codes for this result
        ConditionCode conditionCodes = getConditionCodes(result);

        //Set the output
        outputBusConnection.writeValueToBus(result);
        ccBusConnection.writeValueToBus(conditionCodes.toBitSet());
    }


    private ConditionCode getConditionCodes(FixedBitSet result) {
        boolean zero = result.equals(new FixedBitSet(DATA_WIDTH));
        boolean sign = result.get(DATA_WIDTH - 1);

        //The LU cannot produce the conditions which would set the carry or overflow flags
        return new ConditionCode(false, false, sign, zero);

    }


    private FixedBitSet performXOR(FixedBitSet a, FixedBitSet b) {
        FixedBitSet result = new FixedBitSet(DATA_WIDTH);
        for (int i = 0; i < DATA_WIDTH; i++) {
            result = result.set(i, a.get(i) ^ b.get(i));
        }
        return result;
    }

    private FixedBitSet performOR(FixedBitSet a, FixedBitSet b) {
        FixedBitSet result = new FixedBitSet(DATA_WIDTH);
        for (int i = 0; i < DATA_WIDTH; i++) {
            result = result.set(i, a.get(i) || b.get(i));
        }
        return result;
    }

    private FixedBitSet performAND(FixedBitSet a, FixedBitSet b) {
        FixedBitSet result = new FixedBitSet(DATA_WIDTH);
        for (int i = 0; i < DATA_WIDTH; i++) {
            result = result.set(i, a.get(i) && b.get(i));
        }
        return result;
    }

    private FixedBitSet performNOT(FixedBitSet a) {
        FixedBitSet result = new FixedBitSet(DATA_WIDTH);
        for (int i = 0; i < DATA_WIDTH; i++) {
            result = result.set(i, !a.get(i));
        }
        return result;
    }


}


