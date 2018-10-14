package org.karnes.homebrew.hardware;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

public class BidirectionalHardwareConnection extends SoftwareComponent implements ReadableConnection, WritableConnection {

    private final ReadableHardwareConnection readConnection;
    private final WritableHardwareConnection writeConnection;

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
}
