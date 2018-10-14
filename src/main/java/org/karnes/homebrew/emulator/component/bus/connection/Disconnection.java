package org.karnes.homebrew.emulator.component.bus.connection;


import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;

public class Disconnection extends SoftwareComponent implements ReadableConnection, WritableConnection {


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


}
