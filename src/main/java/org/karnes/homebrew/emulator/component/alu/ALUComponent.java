package org.karnes.homebrew.emulator.component.alu;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.component.alu.arithmeticunit.AU_OPCODE;
import org.karnes.homebrew.emulator.component.bus.connection.Disconnection;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.emulator.component.bus.connection.SoftwareConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

/**
 * A representation of the common logic between the ALU components.
 */
public abstract class ALUComponent extends SoftwareComponent {
    //All connections are initially disconnected
    protected ReadableConnection opcodeConnection = new Disconnection(AU_OPCODE.WIDTH);
    protected ReadableConnection tmp1BusConnection = new Disconnection(getWidth());
    protected ReadableConnection tmp2BusConnection = new Disconnection(getWidth());
    protected WritableConnection outputBusConnection = new Disconnection(getWidth());
    protected WritableConnection ccBusConnection = new Disconnection(ConditionCode.WIDTH);

    protected ALUComponent(String name, int width) {
        super(name, width);
    }

    private void handleOpcodeChange(FixedBitSet newOpcodeValue) {
        //The bus containing the opcode has changed. Our component will need to update
        updateOutput();
    }

    private void handleInputChange(FixedBitSet newInputValue) {
        // If the component is enabled, and one of the inputs changed, we need to update the output
        if (enabled()) {
            updateOutput();
        }

    }

    private boolean enabled() {
        //The third bit of the opcode decides if the ALU component is enabled.
        return opcodeConnection.readValue().get(2);
    }

    private void updateOutput() {
        //Check if the AU should do anything at all.
        if (enabled()) {
            //Component is enabled. Get the input values
            FixedBitSet opcode = opcodeConnection.readValue();
            FixedBitSet tmp1Value = tmp1BusConnection.readValue();
            FixedBitSet tmp2Value = tmp2BusConnection.readValue();

            //Hand them off to our simulated circuitry
            performOperation(opcode, tmp1Value, tmp2Value);


        } else {
            //The component is disabled, clear the outputs
            FixedBitSet zero = new FixedBitSet(getWidth());
            outputBusConnection.writeValue(zero);
            ccBusConnection.writeValue(zero);
        }
    }

    /**
     * Performs the actual operation, and updates the output bus and condition code bus
     *
     * @param opcode    The current value from the opcode bus
     * @param tmp1Value The current value from the TMP1 bus
     * @param tmp2Value The current value from the TMP2 bus
     */
    protected abstract void performOperation(FixedBitSet opcode, FixedBitSet tmp1Value, FixedBitSet tmp2Value);

    public WritableConnection getOpcodeConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " Opcode Connection", AU_OPCODE.WIDTH, this::handleOpcodeChange);
        this.opcodeConnection = connection;
        return connection;
    }

    public WritableConnection getTmp1BusConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " Tmp1 Connection", getWidth(), this::handleInputChange);
        tmp1BusConnection = connection;
        return connection;
    }

    public WritableConnection getTmp2BusConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " Tmp2 Connection", getWidth(), this::handleInputChange);
        tmp2BusConnection = connection;
        return connection;
    }

    public ReadableConnection getOutputBusConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " Output Connection", getWidth());
        outputBusConnection = connection;
        return connection;
    }

    public ReadableConnection getCcBusConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " CC Output Connection", ConditionCode.WIDTH);
        ccBusConnection = connection;
        return connection;
    }
}
