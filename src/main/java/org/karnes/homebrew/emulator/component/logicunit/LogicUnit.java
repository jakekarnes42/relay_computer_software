package org.karnes.homebrew.emulator.component.logicunit;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.bus.Bus;
import org.karnes.homebrew.emulator.component.bus.BusConnectedDevice;
import org.karnes.homebrew.emulator.component.bus.BusConnection;
import org.karnes.homebrew.emulator.component.bus.BusValueChangedEvent;


public class LogicUnit implements BusConnectedDevice {

    private final BusConnection luOperationConnection;
    private final BusConnection tmp1BusConnection;
    private final BusConnection tmp2BusConnection;
    private final BusConnection outputBusConnection;
    private final BusConnection ccBusConnection;
    private final String LU_BUS_NAME;
    private final int DATA_WIDTH;
    private final String INPUT1_BUS_NAME;
    private final String INPUT2_BUS_NAME;

    public LogicUnit(Bus luOperationBus, Bus tmp1Bus, Bus tmp2Bus, Bus outputBus, Bus ccBus) {
        if (luOperationBus.getWidth() != 3) {
            throw new IllegalArgumentException("Unexpected width for LU Operation Bus. Expected 3. Found: "
                    + luOperationBus.getWidth());
        }
        LU_BUS_NAME = luOperationBus.getName();
        luOperationConnection = new BusConnection(luOperationBus, this);

        if (tmp1Bus.getWidth() != tmp2Bus.getWidth() || tmp2Bus.getWidth() != outputBus.getWidth()) {
            throw new IllegalArgumentException("All buses for data must have the same width. TMP1 width "
                    + tmp1Bus.getWidth() + "TMP2 width " + tmp2Bus.getWidth() + " Output Width" + outputBus.getWidth());
        }

        DATA_WIDTH = tmp1Bus.getWidth();
        INPUT1_BUS_NAME = tmp1Bus.getName();
        tmp1BusConnection = new BusConnection(tmp1Bus, this);
        INPUT2_BUS_NAME = tmp2Bus.getName();
        tmp2BusConnection = new BusConnection(tmp2Bus, this);
        outputBusConnection = new BusConnection(outputBus, this);

        if (ccBus.getWidth() != 4) {
            throw new IllegalArgumentException("Unexpected width for Condition Code Bus. Expected 4. Found: "
                    + ccBus.getWidth());
        }

        ccBusConnection = new BusConnection(ccBus, this);

        //Now that we're connected, it's possible that there's already live data, even though this is a no-no
        if (isLUEnabled()) {
            System.err.println("The Logic Unit shouldn't be connected to buses which already have live data");
            updateOutput();
        }
    }

    @Override
    public void handleBusValueChangedEvent(BusValueChangedEvent busValueChangedEvent) {
        if (busValueChangedEvent.getBusName().equals(LU_BUS_NAME)) {
            //The bus contain the LU opcode has changed. Our LU will need to update
            updateOutput();
        } else {
            //The opcode hasn't changed.
            //But if the LU is enabled, and one of the inputs changed, we need to update the output
            if (isLUEnabled() &&
                    (busValueChangedEvent.getBusName().equals(INPUT1_BUS_NAME) ||
                            busValueChangedEvent.getBusName().equals(INPUT2_BUS_NAME))) {
                updateOutput();
            }
        }
    }

    private boolean isLUEnabled() {
        //The third bit of the opcode decides if the LU is enabled.
        return luOperationConnection.getBusValue().get(2);
    }

    private LU_OPCODE getCurrentOpCode() {
        if (isLUEnabled()) {
            //The first two bits decide the opcode
            FixedBitSet slice = luOperationConnection.getBusValue().getSlice(0, 2);
            return LU_OPCODE.fromBitSet(slice);
        } else {
            //The LU isn't enabled
            return null;
        }
    }

    private void updateOutput() {
        //Check if the LU should do anything at all.
        if (isLUEnabled()) {
            //LU is enabled. Get the input values
            FixedBitSet tmp1Value = tmp1BusConnection.getBusValue();
            FixedBitSet tmp2Value = tmp2BusConnection.getBusValue();

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
                    throw new IllegalStateException("Unexpected LU opcode");

            }

            //Get the appropriate condition codes for this result
            ConditionCode conditionCodes = getConditionCodes(result);

            //Set the output
            outputBusConnection.setToBusOutput(result);
            ccBusConnection.setToBusOutput(conditionCodes.toBitSet());

        } else {
            //The LU is disabled, clear the outputs
            ArbitraryBitSet zero = new ArbitraryBitSet(DATA_WIDTH);
            outputBusConnection.setToBusOutput(zero);
            ccBusConnection.setToBusOutput(zero);
        }
    }

    private ConditionCode getConditionCodes(FixedBitSet result) {
        boolean zero = result.equals(new ArbitraryBitSet(DATA_WIDTH));
        boolean sign = result.get(DATA_WIDTH - 1);

        return new ConditionCode(false, false, sign, zero);

    }


    private FixedBitSet performXOR(FixedBitSet a, FixedBitSet b) {
        ArbitraryBitSet result = new ArbitraryBitSet(DATA_WIDTH);
        for (int i = 0; i < DATA_WIDTH; i++) {
            result = result.set(i, a.get(i) ^ b.get(i));
        }
        return result;
    }

    private FixedBitSet performOR(FixedBitSet a, FixedBitSet b) {
        ArbitraryBitSet result = new ArbitraryBitSet(DATA_WIDTH);
        for (int i = 0; i < DATA_WIDTH; i++) {
            result = result.set(i, a.get(i) || b.get(i));
        }
        return result;
    }

    private FixedBitSet performAND(FixedBitSet a, FixedBitSet b) {
        ArbitraryBitSet result = new ArbitraryBitSet(DATA_WIDTH);
        for (int i = 0; i < DATA_WIDTH; i++) {
            result = result.set(i, a.get(i) && b.get(i));
        }
        return result;
    }

    private FixedBitSet performNOT(FixedBitSet a) {
        ArbitraryBitSet result = new ArbitraryBitSet(DATA_WIDTH);
        for (int i = 0; i < DATA_WIDTH; i++) {
            result = result.set(i, !a.get(i));
        }
        return result;
    }


}


