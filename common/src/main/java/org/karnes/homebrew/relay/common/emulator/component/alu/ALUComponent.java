package org.karnes.homebrew.relay.common.emulator.component.alu;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.ConditionCode;
import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.alu.arithmeticunit.AU_OPCODE;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.*;

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

    private void handleInputChange(FixedBitSet newValue) {
        // One of the inputs have changed, we need to update the output
        updateOutput();
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

    /**
     * Gets the Opcode connection for this ALU Component. Writes to this connection change the calculation performed by the component.
     *
     * @return The opcode connection.
     */
    public WritableConnection getOpcodeConnection() {
        //Check if we're already connected
        if (this.opcodeConnection.isConnected()) {
            throw new ExistingConnectionException("The Opcode Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " Opcode Connection", AU_OPCODE.WIDTH, this::handleInputChange);
            this.opcodeConnection = connection;
            return connection;
        }
    }

    /**
     * Gets the TMP1 connection for this ALU Component. This connection represents the first of the two operands.
     *
     * @return The TMP1 connection.
     * @throws ExistingConnectionException if the connection has already been gotten
     */
    public WritableConnection getTmp1BusConnection() throws ExistingConnectionException {
        //Check if we're already connected
        if (this.tmp1BusConnection.isConnected()) {
            throw new ExistingConnectionException("The TMP1 Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " TMP1 Connection", getWidth(), this::handleInputChange);
            tmp1BusConnection = connection;
            return connection;
        }
    }

    /**
     * Gets the TMP2 connection for this ALU Component. This connection represents the second of the two operands.
     *
     * @return The TMP2 connection.
     * @throws ExistingConnectionException if the connection has already been gotten
     */
    public WritableConnection getTmp2BusConnection() throws ExistingConnectionException {
        //Check if we're already connected
        if (this.tmp2BusConnection.isConnected()) {
            throw new ExistingConnectionException("The TMP2 Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " TMP2 Connection", getWidth(), this::handleInputChange);
            tmp2BusConnection = connection;
            return connection;
        }
    }

    /**
     * Gets the output connection for this ALU Component. This is the "result" of the current operation given the
     * current operands.
     *
     * @return The output connection.
     * @throws ExistingConnectionException if the connection has already been gotten
     */
    public ReadableConnection getOutputBusConnection() throws ExistingConnectionException {
        //Check if we're already connected
        if (this.outputBusConnection.isConnected()) {
            throw new ExistingConnectionException("The Output Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " Output Connection", getWidth());
            outputBusConnection = connection;
            return connection;
        }
    }

    /**
     * Gets the condition code connection for this ALU Component. This is the condition codes resulting from the current
     * operation given the current operands.
     *
     * @return The output connection.
     * @throws ExistingConnectionException if the connection has already been gotten
     */
    public ReadableConnection getCcBusConnection() throws ExistingConnectionException {
        //Check if we're already connected
        if (this.ccBusConnection.isConnected()) {
            throw new ExistingConnectionException("The CC Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " CC Output Connection", ConditionCode.WIDTH);
            ccBusConnection = connection;
            return connection;
        }
    }
}
