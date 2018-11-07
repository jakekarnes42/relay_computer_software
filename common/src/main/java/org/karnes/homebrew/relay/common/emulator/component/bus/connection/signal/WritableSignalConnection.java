package org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal;

import org.karnes.homebrew.relay.common.emulator.component.bus.connection.WritableConnection;

/**
 * A {@link WritableConnection} that has a single bit width
 */
public interface WritableSignalConnection extends WritableConnection {
    /**
     * Writes the single bit to the connection. The value should be {@code true} if the signal is HIGH, or {@code false} if the signal is LOW.
     */
    void writeSignal(boolean value);
}
