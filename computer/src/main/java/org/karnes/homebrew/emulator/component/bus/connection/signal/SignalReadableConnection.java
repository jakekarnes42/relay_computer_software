package org.karnes.homebrew.emulator.component.bus.connection.signal;

import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;

/**
 * A {@link ReadableConnection} that has a single bit width
 */
public interface SignalReadableConnection extends ReadableConnection {
    /**
     * Reads the single bit of the connection, converting to a boolean.
     *
     * @return {@code true} if the signal is HIGH, or {@code false} if the signal is LOW.
     */
    boolean readSignal();

}
