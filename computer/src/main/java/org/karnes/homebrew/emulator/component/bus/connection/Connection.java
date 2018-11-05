package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.emulator.component.Component;
import org.karnes.homebrew.emulator.component.bus.Bus;

/**
 * A connection to a {@link Component}
 *
 * @see ReadableConnection
 * @see WritableConnection
 * @see BidirectionalConnection
 */
public interface Connection extends Component {
    /**
     * The width of the {@link Bus} which this connects to.
     *
     * @return The width of the connection.
     * @see Bus#getWidth()
     */
    @Override
    int getWidth();

    /**
     * Indicates whether or not this Connection is already connected and ready for use.
     *
     * @return true if this is connected and ready for I/O, false otherwise.
     */
    boolean isConnected();
}
