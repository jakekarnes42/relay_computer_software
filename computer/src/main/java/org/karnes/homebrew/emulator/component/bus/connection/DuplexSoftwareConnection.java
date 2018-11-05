package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;

/**
 * A {@link Connection} made of two {@link SoftwareConnection}s.
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

    /**
     * The connection which should be used by the Bus.
     *
     * @return a BidirectionalConnection
     */
    public BidirectionalConnection getBusSideConnection() {
        return connectionA;
    }

    /**
     * The connection which should be given to the "other side" or other component to use
     *
     * @return a BidirectionalConnection
     */
    public BidirectionalConnection getOtherSideConnection() {
        return connectionB;
    }

    private FixedBitSet getCurrentValue() {
        return lastValue;
    }


    private class BusSideConnection extends SoftwareComponent implements BidirectionalConnection {

        private BusSideConnection(String name, int width) {
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

        private OtherSideConnection(String name, int width) {
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
