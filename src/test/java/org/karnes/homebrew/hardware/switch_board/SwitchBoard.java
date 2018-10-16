package org.karnes.homebrew.hardware.switch_board;

import org.karnes.homebrew.emulator.component.Component;
import org.karnes.homebrew.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

/**
 * Represents the physical or virtual BBB Connector Test Board Rev B
 */
public interface SwitchBoard extends Component {
    ReadableConnection getReadConnection();

    WritableConnection getWriteConnection();

    BidirectionalConnection getBidirectionalConnection();

    boolean LED0Status();

    boolean LED1Status();

    boolean LED2Status();

    void enableSwitch0();

    void disableSwitch0();

    void enableSwitch1();

    void disableSwitch1();
}
