package org.karnes.homebrew.emulator.component.bus.connection;

/**
 * A {@link Connection} which can be read from, and have values written to it.
 *
 * @see org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection
 * @see org.karnes.homebrew.emulator.component.bus.connection.WritableConnection
 */
public interface BidirectionalConnection extends ReadableConnection, WritableConnection {
}
