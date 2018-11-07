package org.karnes.homebrew.relay.hardware_tests.switch_board;

import org.karnes.homebrew.relay.common.emulator.component.Component;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.BidirectionalSignalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.ReadableSignalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnection;

/**
 * Represents the physical or virtual BBB Connector Test Board Rev B
 */
public interface SwitchBoard extends Component {
    ReadableSignalConnection getReadConnection();

    WritableSignalConnection getWriteConnection();

    BidirectionalSignalConnection getBidirectionalConnection();

    boolean LED0Status();

    boolean LED1Status();

    boolean LED2Status();

    void enableSwitch0();

    void disableSwitch0();

    void enableSwitch1();

    void disableSwitch1();
}
