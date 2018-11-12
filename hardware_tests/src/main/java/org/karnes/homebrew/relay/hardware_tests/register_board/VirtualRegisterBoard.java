package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.Bus;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnection;
import org.karnes.homebrew.relay.common.emulator.component.register.Register;

/**
 * Emulates the behavior of the 2 Bit Register Board Rev A
 */
public class VirtualRegisterBoard extends SoftwareComponent implements RegisterBoard {

    //Components
    Bus addressBus = new Bus("Address Bus", width);
    Bus dataBus = new Bus("Data Bus", width);
    Register registerA = new Register("Register A", addressBus, dataBus);
    Register registerB = new Register("Register B", addressBus, dataBus);

    //Signals
    WritableSignalConnection registerASelectA = registerA.getSelectAConnection();
    WritableSignalConnection registerALoadA = registerA.getLoadAConnection();
    WritableSignalConnection registerASelectD = registerA.getSelectDConnection();
    WritableSignalConnection registerALoadD = registerA.getLoadDConnection();

    WritableSignalConnection registerBSelectA = registerB.getSelectAConnection();
    WritableSignalConnection registerBLoadA = registerB.getLoadAConnection();
    WritableSignalConnection registerBSelectD = registerB.getSelectDConnection();
    WritableSignalConnection registerBLoadD = registerB.getLoadDConnection();

    //Bus Connections
    BidirectionalConnection addressBusConnection = addressBus.createBidirectionalConnection();
    BidirectionalConnection dataBusConnection = dataBus.createBidirectionalConnection();

    public VirtualRegisterBoard() {
        super("Virtual 2-bit Register Board", 2);
    }

    public WritableSignalConnection getRegisterASelectA() {
        return registerASelectA;
    }

    public WritableSignalConnection getRegisterALoadA() {
        return registerALoadA;
    }

    public WritableSignalConnection getRegisterASelectD() {
        return registerASelectD;
    }

    public WritableSignalConnection getRegisterALoadD() {
        return registerALoadD;
    }

    public WritableSignalConnection getRegisterBSelectA() {
        return registerBSelectA;
    }

    public WritableSignalConnection getRegisterBLoadA() {
        return registerBLoadA;
    }

    public WritableSignalConnection getRegisterBSelectD() {
        return registerBSelectD;
    }

    public WritableSignalConnection getRegisterBLoadD() {
        return registerBLoadD;
    }

    public BidirectionalConnection getAddressBusConnection() {
        return addressBusConnection;
    }

    public BidirectionalConnection getDataBusConnection() {
        return dataBusConnection;
    }
}
