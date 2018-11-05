package org.karnes.homebrew.emulator.component.bus.connection.signal;

import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

/**
 * A {@link WritableConnection} that has a single bit width
 */
public interface SignalWritableConnection extends WritableConnection {
    /**
     * Writes the single bit to the connection. The value should be {@code true} if the signal is HIGH, or {@code false} if the signal is LOW.
     */
    void writeSignal(boolean value);
}
