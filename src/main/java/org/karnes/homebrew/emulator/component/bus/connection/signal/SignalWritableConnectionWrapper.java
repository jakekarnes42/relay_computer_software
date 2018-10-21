package org.karnes.homebrew.emulator.component.bus.connection.signal;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

/**
 * A wrapper for a 1-bit {@link WritableConnection} which provides allows for callers to write booleans to the connection.
 */
public class SignalWritableConnectionWrapper extends SoftwareComponent implements SignalWritableConnection {

    private final WritableConnection connection;

    /**
     * Creates a new SignalWritableConnection by wrapping an existing {@link WritableConnection}.
     *
     * @param connection The connection to be wrapped.
     * @throws IllegalArgumentException if {@code connection} isn't 1-bit in width.
     */
    public SignalWritableConnectionWrapper(WritableConnection connection) throws IllegalArgumentException {
        super(connection.getName() + " - Signal Wrapper", 1);

        if (connection.getWidth() != 1) {
            throw new IllegalArgumentException("The connection to be wrapped must be of width 1. Found:" + connection.getWidth());
        }
        this.connection = connection;
    }

    @Override
    public void writeSignal(boolean value) {
        writeValue(new FixedBitSet(new boolean[]{value}));
    }

    @Override
    public boolean isConnected() {
        return connection.isConnected();
    }

    @Override
    public void writeValue(FixedBitSet value) {
        connection.writeValue(value);
    }
}
