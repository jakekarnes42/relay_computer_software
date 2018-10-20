package org.karnes.homebrew.hardware;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;

/**
 * A {@link ReadableConnection} which reads from hardware.
 */
public class ReadableHardwareConnection extends SoftwareComponent implements ReadableConnection {

    private final MCP23017 mcp23017;
    private final MCP23017Pin[] inputPins;

    public ReadableHardwareConnection(String name, MCP23017 mcp23017, MCP23017Pin... inputPins) {
        super(name, inputPins.length);
        this.mcp23017 = mcp23017;
        this.inputPins = inputPins;
    }


    @Override
    public FixedBitSet readValue() {
        FixedBitSet value = mcp23017.read(inputPins);
        //TODO: do we need to de-bounce?
        return value;
    }

    @Override
    public boolean isConnected() {
        //TODO: more sophisticated checking here.
        return true;
    }
}
