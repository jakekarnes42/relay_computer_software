package org.karnes.homebrew.emulator.component.bus.connection.signal;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.component.bus.connection.BidirectionalConnection;

/**
 * A wrapper for a 1-bit {@link BidirectionalConnection} which provides allows for callers to read/write booleans with the readConnection.
 */
public class SignalBidirectionalConnectionWrapper extends SoftwareComponent implements SignalBidirectionalConnection {

    private final BidirectionalConnection connection;

    /**
     * Creates a new SignalBidirectionalConnection by wrapping an existing {@link BidirectionalConnection}.
     *
     * @param connection The connection to be wrapped.
     * @throws IllegalArgumentException if {@code connection} isn't 1-bit in width.
     */
    public SignalBidirectionalConnectionWrapper(BidirectionalConnection connection) throws IllegalArgumentException {
        super(connection.getName() + " - Signal Wrapper", 1);
        this.connection = connection;

        if (connection.getWidth() != 1) {
            throw new IllegalArgumentException("The connection to be wrapped must be of width 1. Found:" + connection.getWidth());
        }
    }


    @Override
    public boolean readSignal() {
        return readValue().get(0);
    }

    @Override
    public FixedBitSet readValue() {
        return connection.readValue();
    }

    @Override
    public void writeSignal(boolean value) {
        writeValue(new FixedBitSet(new boolean[]{value}));
    }


    @Override
    public void writeValue(FixedBitSet value) {
        connection.writeValue(value);
    }

    @Override
    public boolean isConnected() {
        return connection.isConnected() && connection.isConnected();
    }
}
