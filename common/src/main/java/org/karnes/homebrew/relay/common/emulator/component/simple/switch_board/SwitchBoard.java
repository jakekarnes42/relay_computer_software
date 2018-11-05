package org.karnes.homebrew.relay.common.emulator.component.simple.switch_board;

import org.karnes.homebrew.relay.common.emulator.component.Component;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.SignalBidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.SignalReadableConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.SignalWritableConnection;

/**
 * Represents the physical or virtual BBB Connector Test Board Rev B
 */
public interface SwitchBoard extends Component {
    SignalReadableConnection getReadConnection();

    SignalWritableConnection getWriteConnection();

    SignalBidirectionalConnection getBidirectionalConnection();

    boolean LED0Status();

    boolean LED1Status();

    boolean LED2Status();

    void enableSwitch0();

    void disableSwitch0();

    void enableSwitch1();

    void disableSwitch1();
}
