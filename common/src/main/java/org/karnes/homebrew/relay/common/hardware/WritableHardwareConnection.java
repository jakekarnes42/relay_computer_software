package org.karnes.homebrew.relay.common.hardware;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.WritableConnection;

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

        //set to output
        mcp23017.setOutput(outputPins);
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
