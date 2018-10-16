package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;

/**
 * A {@link Connection} made of two {@link SoftwareConnection}s.
 * <p>
 * Writes made to ConnectionA, are readable from ConnectionB.
 * Writes made to ConnectionB, are readable from ConnectionA.
 */
public class DuplexSoftwareConnection extends SoftwareComponent {
    private final BusSideConnection connectionA;
    private final OtherSideConnection connectionB;
    private final ReadableConnection readIn;
    private final WritableConnection writeOut;

    private FixedBitSet lastValue;

    public DuplexSoftwareConnection(String name, int width, ReadableConnection readIn, WritableConnection writeOut) {
        super(name, width);
        this.readIn = readIn;
        this.writeOut = writeOut;
        connectionA = new BusSideConnection(name + " - ConnectionA", width);
        connectionB = new OtherSideConnection(name + " - ConnectionB", width);

        lastValue = new FixedBitSet(width);
    }

    public BidirectionalConnection getConnectionA() {
        return connectionA;
    }

    public BidirectionalConnection getConnectionB() {
        return connectionB;
    }

    private FixedBitSet getCurrentValue() {
        return lastValue;
    }


    private class BusSideConnection extends SoftwareComponent implements BidirectionalConnection {

        public BusSideConnection(String name, int width) {
            super(name, width);
        }

        @Override
        public FixedBitSet readValue() {
            return getCurrentValue();
        }

        @Override
        public void writeValue(FixedBitSet value) {
            //Intentionally do nothing
        }

        @Override
        public boolean isConnected() {
            return true;
        }
    }

    private class OtherSideConnection extends SoftwareComponent implements BidirectionalConnection {

        public OtherSideConnection(String name, int width) {
            super(name, width);
        }

        @Override
        public FixedBitSet readValue() {
            //Read from the Bus side
            return readIn.readValue();
        }

        @Override
        public void writeValue(FixedBitSet value) {
            //Save it
            lastValue = value;

            //Write to the Bus side
            writeOut.writeValue(lastValue);
        }

        @Override
        public boolean isConnected() {
            return true;
        }
    }

}
