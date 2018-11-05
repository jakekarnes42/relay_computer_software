package org.karnes.homebrew.relay.common.emulator.component.bus.connection;

/**
 * A {@link Connection} which can be read from, and have values written to it.
 *
 * @see ReadableConnection
 * @see WritableConnection
 */
public interface BidirectionalConnection extends ReadableConnection, WritableConnection {
}
