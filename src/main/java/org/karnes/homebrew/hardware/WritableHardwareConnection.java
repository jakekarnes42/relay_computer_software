package org.karnes.homebrew.hardware;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

/**
 * A {@link WritableConnection} which writes to hardware.
 */
public class WritableHardwareConnection extends SoftwareComponent implements WritableConnection {
    private final MCP23017 mcp23017;
    private final MCP23017Pin[] outputPins;

    public WritableHardwareConnection(String name, MCP23017 mcp23017, MCP23017Pin... outputPins) {
        super(name, outputPins.length);
        this.mcp23017 = mcp23017;
        this.outputPins = outputPins;
    }

    @Override
    public void writeValue(FixedBitSet value) {
        mcp23017.write(value, outputPins);
    }

    @Override
    public boolean isConnected() {
        //TODO: more sophisticated checking here.
        return true;
    }
}
