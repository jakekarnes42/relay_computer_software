package org.karnes.homebrew.relay.common.emulator.component.simple.switch_board;

import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.Bus;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.WritableConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.*;
import org.karnes.homebrew.relay.common.emulator.component.simple.LED;
import org.karnes.homebrew.relay.common.emulator.component.simple.Switch;

/**
 * Emulate the behavior of the BBB Connector Test Board Rev B
 */
public class VirtualSwitchBoard extends SoftwareComponent implements SwitchBoard {

    /*
     * Connections within the board
     */

    //LEDs
    private LED led0 = new LED("LED0", 1);
    private WritableConnection LED0Connection = led0.getConnection();
    private LED led1 = new LED("LED1", 1);
    private WritableConnection LED1Connection = led1.getConnection();
    private LED led2 = new LED("LED2", 1);
    private WritableConnection LED2Connection = led2.getConnection();

    //Switches
    private Switch switch0 = new Switch("Switch0", 1);
    private ReadableConnection switch0Connection = switch0.getConnection();
    private Switch switch1 = new Switch("Switch1", 1);
    private ReadableConnection switch1Connection = switch1.getConnection();

    //Buses
    private Bus led0Bus = new Bus("LED0 Bus", 1);
    private Bus led1Bus = new Bus("LED1 Bus", 1);
    private Bus led2Bus = new Bus("LED2 Bus", 1);

    /**
     * Creates a new VirtualSwitchBoard.
     */
    public VirtualSwitchBoard() {
        super("VirtualSwitchBoard", 1);

        //Connect components
        led0Bus.addWritableConnection(LED0Connection);
        led0Bus.addReadableConnection(switch0Connection);

        led1Bus.addWritableConnection(LED1Connection);

        led2Bus.addWritableConnection(LED2Connection);
        led2Bus.addReadableConnection(switch1Connection);
    }

    @Override
    public SignalReadableConnection getReadConnection() {
        return new SignalReadableConnectionWrapper(led0Bus.createReadableConnection());
    }


    @Override
    public SignalWritableConnection getWriteConnection() {
        return new SignalWritableConnectionWrapper(led1Bus.createWritableConnection());
    }

    @Override
    public SignalBidirectionalConnection getBidirectionalConnection() {
        return new SignalBidirectionalConnectionWrapper(led2Bus.createBidirectionalConnection());
    }

    @Override
    public boolean LED0Status() {
        updateBuses();
        return led0.isOn(0);
    }

    @Override
    public boolean LED1Status() {
        updateBuses();
        return led1.isOn(0);
    }

    @Override
    public boolean LED2Status() {
        updateBuses();
        return led2.isOn(0);
    }

    @Override
    public void enableSwitch0() {
        switch0.turnOn(0);
        updateBuses();
    }

    @Override
    public void disableSwitch0() {
        switch0.turnOff(0);
        updateBuses();
    }

    @Override
    public void enableSwitch1() {
        switch1.turnOn(0);
        updateBuses();
    }

    @Override
    public void disableSwitch1() {
        switch1.turnOff(0);
        updateBuses();
    }

    private void updateBuses() {
        led0Bus.update();
        led1Bus.update();
        led2Bus.update();
    }


}
