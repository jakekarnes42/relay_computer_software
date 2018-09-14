package org.karnes.homebrew.emulator.component.alu;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.alu.arithmeticunit.AU_OPCODE;
import org.karnes.homebrew.emulator.component.bus.ReadableBus;
import org.karnes.homebrew.emulator.component.bus.WriteableBus;
import org.karnes.homebrew.emulator.component.bus.connection.BusValueChangedEvent;
import org.karnes.homebrew.emulator.component.bus.connection.InterruptFromBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WriteableBusConnection;

/**
 * A represenatation of the common logic between the ALU components.
 */
public abstract class ALUComponent {
    private final InterruptFromBusConnection opcodeConnection;
    private final InterruptFromBusConnection tmp1BusConnection;
    private final InterruptFromBusConnection tmp2BusConnection;
    protected final WriteableBusConnection outputBusConnection;
    protected final WriteableBusConnection ccBusConnection;
    protected final int DATA_WIDTH;

    public ALUComponent(ReadableBus opcodeBus, ReadableBus tmp1Bus, ReadableBus tmp2Bus, WriteableBus outputBus, WriteableBus ccBus) {
        //Get the opcode bus
        if (opcodeBus.getWidth() != AU_OPCODE.WIDTH) {
            throw new IllegalArgumentException("Unexpected width for LU Operation bus. Expected " + AU_OPCODE.WIDTH + ". Found: "
                    + opcodeBus.getWidth());
        }

        opcodeConnection = opcodeBus.getInterruptConnection(this::handleOpcodeChange);

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
            throw new IllegalArgumentException("Unexpected width for Condition Code bus. Expected " + ConditionCode.WIDTH + ". Found: "
                    + ccBus.getWidth());
        }
        ccBusConnection = ccBus.getWriteConnection();

        //Now that we're connected, it's possible that there's already live data, even though this is a no-no
        if (enabled()) {
            System.err.println("The Logic Unit shouldn't be connected to buses which already have live data");
            updateOutput();
        }
    }

    private void handleOpcodeChange(BusValueChangedEvent e) {
        //The bus containing the AU opcode has changed. Our AU will need to update
        updateOutput();
    }

    private void handleInputChange(BusValueChangedEvent busValueChangedEvent) {
        // If the AU is enabled, and one of the inputs changed, we need to update the output
        if (enabled()) {
            updateOutput();
        }

    }

    private boolean enabled() {
        //The third bit of the opcode decides if the ALU component is enabled.
        return opcodeConnection.readBusValue().get(2);
    }

    private void updateOutput() {
        //Check if the AU should do anything at all.
        if (enabled()) {
            //Component is enabled. Get the input values
            FixedBitSet opcode = opcodeConnection.readBusValue();
            FixedBitSet tmp1Value = tmp1BusConnection.readBusValue();
            FixedBitSet tmp2Value = tmp2BusConnection.readBusValue();

            //Hand them off to our simulated circuitry
            performOperation(opcode, tmp1Value, tmp2Value);


        } else {
            //The component is disabled, clear the outputs
            FixedBitSet zero = new FixedBitSet(DATA_WIDTH);
            outputBusConnection.writeValueToBus(zero);
            ccBusConnection.writeValueToBus(zero);
        }
    }

    /**
     * Performs the actual operation, and updates the output bus and condition code bus
     *
     * @param opcode    The current value from the opcode bus
     * @param tmp1Value The current value from the TMP1 bus
     * @param tmp2Value The current value fromt the TMP2 bus
     */
    protected abstract void performOperation(FixedBitSet opcode, FixedBitSet tmp1Value, FixedBitSet tmp2Value);
}
