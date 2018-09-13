package org.karnes.homebrew.emulator.component.logicunit;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.bus.BidirectionalBus;
import org.karnes.homebrew.emulator.component.bus.ReadableBus;
import org.karnes.homebrew.emulator.component.bus.connection.BusValueChangedEvent;
import org.karnes.homebrew.emulator.component.bus.connection.InterruptFromBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WriteableBusConnection;


public class LogicUnit {

    private final InterruptFromBusConnection luOperationConnection;
    private final InterruptFromBusConnection tmp1BusConnection;
    private final InterruptFromBusConnection tmp2BusConnection;
    private final WriteableBusConnection outputBusConnection;
    private final WriteableBusConnection ccBusConnection;
    private final int DATA_WIDTH;

    public LogicUnit(ReadableBus luOperationBus, ReadableBus tmp1Bus, ReadableBus tmp2Bus, BidirectionalBus outputBus, BidirectionalBus ccBus) {
        //Get the opcode bus
        if (luOperationBus.getWidth() != LU_OPCODE.WIDTH) {
            throw new IllegalArgumentException("Unexpected width for LU Operation BidirectionalBus. Expected " + LU_OPCODE.WIDTH + ". Found: "
                    + luOperationBus.getWidth());
        }

        luOperationConnection = luOperationBus.getInterruptConnection(this::handleLUOperationChange);

        //Get the various data-related buses
        if (tmp1Bus.getWidth() != tmp2Bus.getWidth() || tmp2Bus.getWidth() != outputBus.getWidth()) {
            throw new IllegalArgumentException("All buses for data must have the same width. TMP1 width "
                    + tmp1Bus.getWidth() + "TMP2 width " + tmp2Bus.getWidth() + " Output Width" + outputBus.getWidth());
        }
        DATA_WIDTH = tmp1Bus.getWidth();
        tmp1BusConnection = tmp1Bus.getInterruptConnection(this::handleInputChange);
        tmp2BusConnection = tmp2Bus.getInterruptConnection(this::handleInputChange);
        outputBusConnection = outputBus.getWriteConnection();


        //Get the Condition Code bus
        if (ccBus.getWidth() != ConditionCode.WIDTH) {
            throw new IllegalArgumentException("Unexpected width for Condition Code BidirectionalBus. Expected " + ConditionCode.WIDTH + ". Found: "
                    + ccBus.getWidth());
        }
        ccBusConnection = ccBus.getWriteConnection();

        //Now that we're connected, it's possible that there's already live data, even though this is a no-no
        if (isLUEnabled()) {
            System.err.println("The Logic Unit shouldn't be connected to buses which already have live data");
            updateOutput();
        }
    }

    private void handleLUOperationChange(BusValueChangedEvent e) {
        //The bus contain the LU opcode has changed. Our LU will need to update
        updateOutput();
    }

    public void handleInputChange(BusValueChangedEvent busValueChangedEvent) {
        // If the LU is enabled, and one of the inputs changed, we need to update the output
        if (isLUEnabled()) {
            updateOutput();
        }

    }

    private boolean isLUEnabled() {
        //The third bit of the opcode decides if the LU is enabled.
        return luOperationConnection.readBusValue().get(2);
    }

    private LU_OPCODE getCurrentOpCode() {
        return LU_OPCODE.fromBitSet(luOperationConnection.readBusValue());
    }

    private void updateOutput() {
        //Check if the LU should do anything at all.
        if (isLUEnabled()) {
            //LU is enabled. Get the input values
            FixedBitSet tmp1Value = tmp1BusConnection.readBusValue();
            FixedBitSet tmp2Value = tmp2BusConnection.readBusValue();

            // Execute the logic based on the current opcode
            FixedBitSet result;
            switch (getCurrentOpCode()) {
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
                    throw new IllegalStateException("Unexpected LU opcode: " + getCurrentOpCode());

            }

            //Get the appropriate condition codes for this result
            ConditionCode conditionCodes = getConditionCodes(result);

            //Set the output
            outputBusConnection.writeValueToBus(result);
            ccBusConnection.writeValueToBus(conditionCodes.toBitSet());

        } else {
            //The LU is disabled, clear the outputs
            FixedBitSet zero = new FixedBitSet(DATA_WIDTH);
            outputBusConnection.writeValueToBus(zero);
            ccBusConnection.writeValueToBus(zero);
        }
    }

    private ConditionCode getConditionCodes(FixedBitSet result) {
        boolean zero = result.equals(new FixedBitSet(DATA_WIDTH));
        boolean sign = result.get(DATA_WIDTH - 1);

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


