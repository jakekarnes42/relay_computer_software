package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.karnes.homebrew.relay.common.emulator.component.Component;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnection;

public interface RegisterBoard extends Component {
    WritableSignalConnection getRegisterASelectA();

    WritableSignalConnection getRegisterALoadA();

    WritableSignalConnection getRegisterASelectD();

    WritableSignalConnection getRegisterALoadD();

    WritableSignalConnection getRegisterBSelectA();

    WritableSignalConnection getRegisterBLoadA();

    WritableSignalConnection getRegisterBSelectD();

    WritableSignalConnection getRegisterBLoadD();

    BidirectionalConnection getAddressBusConnection();

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
