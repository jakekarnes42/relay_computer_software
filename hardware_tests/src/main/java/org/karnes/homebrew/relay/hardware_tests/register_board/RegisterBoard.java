package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.karnes.homebrew.relay.common.emulator.component.Component;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.register.RegisterSignalSet;

/**
 * An abstraction of the "2-bit Register Board Rev A" which may be virtual or physical
 */
public interface RegisterBoard extends Component {

    /**
     * The signals for Register A
     *
     * @return A wrapper around the signals for Register A
     */
    RegisterSignalSet getRegisterASignalSet();

    /**
     * The signals for Register B
     *
     * @return A wrapper around the signals for Register B
     */
    RegisterSignalSet getRegisterBSignalSet();

    /**
     * The connection to the Register Board's Address Bus.
     *
     * @return the connection to the Register Board's Address Bus.
     */
    BidirectionalConnection getAddressBusConnection();

    /**
     * The connection to the Register Board's Data Bus.
     *
     * @return the connection to the Register Board's Data Bus.
     */
    BidirectionalConnection getDataBusConnection();

    /**
     * Gets the delay (in milliseconds) between operations required by the RegisterBoard, or 0 if there is no delay
     * required.
     *
     * @return The required delay (in milliseconds).
     */
    default int getDelay() {
        return 0;
    }
}

enum RegisterName {A, B}
