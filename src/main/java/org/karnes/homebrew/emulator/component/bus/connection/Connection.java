package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.emulator.component.Component;
import org.karnes.homebrew.emulator.component.bus.Bus;

/**
 * A connection to a Bus.
 *
 * @see ReadableConnection
 * @see WritableConnection
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
}
