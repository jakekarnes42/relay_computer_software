package org.karnes.homebrew.relay.common.hardware;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;

/**
 * A {@link BidirectionalConnection} which reads and writes to/from hardware.
 * <b>Warning:</b> If the connection is currently writing a value, it will read <i>at least</i> that value back. You
 * probably want to zero-out the written output before reading.
 */
public class BidirectionalHardwareConnection extends SoftwareComponent implements BidirectionalConnection {

    private final ReadableHardwareConnection readConnection;
    private final WritableHardwareConnection writeConnection;

    /**
     * Creates a new BidirectionalHardwareConnection
     *
     * @param name           The name of the connection.
     * @param inputMCP23017  The input MCP23017.
     * @param inputPins      The input pins.
     * @param outputMCP23017 The output MCP23017.
     * @param outputPins     The output pins.
     */
    public BidirectionalHardwareConnection(String name, MCP23017 inputMCP23017, MCP23017Pin[] inputPins, MCP23017 outputMCP23017, MCP23017Pin[] outputPins) {
        super(name, inputPins.length);
        if (inputPins.length != outputPins.length) {
            throw new IllegalArgumentException("Input pins size (" + inputPins.length
                    + ") must equal output pins size (" + outputPins.length + ")");
        }
        this.readConnection = new ReadableHardwareConnection(name + "-ReadableConnection", inputMCP23017, inputPins);
        this.writeConnection = new WritableHardwareConnection(name + "-WritableConnection", outputMCP23017, outputPins);
    }

    @Override
    public FixedBitSet readValue() {
        return readConnection.readValue();
    }

    @Override
    public void writeValue(FixedBitSet value) {
        writeConnection.writeValue(value);
    }

    @Override
    public boolean isConnected() {
        return readConnection.isConnected() && writeConnection.isConnected();
    }
}
