package org.karnes.homebrew.emulator.component.bus.connection.signal;

import org.karnes.homebrew.emulator.component.bus.connection.BidirectionalConnection;

/**
 * A {@link BidirectionalConnection} that has a single bit width.
 *
 * @see SignalReadableConnection
 * @see SignalWritableConnection
 */
public interface SignalBidirectionalConnection extends BidirectionalConnection, SignalReadableConnection, SignalWritableConnection {
}
