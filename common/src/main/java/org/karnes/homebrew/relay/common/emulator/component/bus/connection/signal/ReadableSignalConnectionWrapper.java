package org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.ReadableConnection;

/**
 * A wrapper for a 1-bit {@link ReadableConnection} which provides allows for callers to read boolean output.
 */
public class ReadableSignalConnectionWrapper extends SoftwareComponent implements ReadableSignalConnection {

    private final ReadableConnection connection;

    /**
     * Creates a new ReadableSignalConnection by wrapping an existing {@link ReadableConnection}.
     *
     * @param connection The connection to be wrapped.
     * @throws IllegalArgumentException if {@code connection} isn't 1-bit in width.
     */
    public ReadableSignalConnectionWrapper(ReadableConnection connection) throws IllegalArgumentException {
        super(connection.getName() + " - Signal Wrapper", 1);

        if (connection.getWidth() != 1) {
            throw new IllegalArgumentException("The connection to be wrapped must be of width 1. Found:" + connection.getWidth());
        }
        this.connection = connection;
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
    public boolean isConnected() {
        return connection.isConnected();
    }
}
