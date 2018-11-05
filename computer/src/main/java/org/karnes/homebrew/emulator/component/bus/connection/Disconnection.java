package org.karnes.homebrew.emulator.component.bus.connection;


import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;

/**
 * A {@link BidirectionalConnection} which is always disconnected and acts like a no-op if read from or written to.
 */
public class Disconnection extends SoftwareComponent implements BidirectionalConnection {


    public Disconnection(int width) {
        this("Disconnection", width);
    }

    public Disconnection(String name, int width) {
        super(name, width);
    }

    /**
     * Always returns all zeros
     */
    @Override
    public FixedBitSet readValue() {
        return new FixedBitSet(width);
    }

    /**
     * NO-OP
     */
    @Override
    public void writeValue(FixedBitSet value) {
        //intentionally does nothing. In space, no one can hear you scream
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
