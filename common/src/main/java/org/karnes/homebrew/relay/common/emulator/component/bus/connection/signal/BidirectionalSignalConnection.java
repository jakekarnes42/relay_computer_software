package org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal;

import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;

/**
 * A {@link BidirectionalConnection} that has a single bit width.
 *
 * @see ReadableSignalConnection
 * @see WritableSignalConnection
 */
public interface BidirectionalSignalConnection extends BidirectionalConnection, ReadableSignalConnection, WritableSignalConnection {
}
