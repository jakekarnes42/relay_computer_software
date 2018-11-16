package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.Bus;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.register.Register;
import org.karnes.homebrew.relay.common.emulator.component.register.RegisterSignalSet;

/**
 * Emulates the behavior of the 2 Bit Register Board Rev A
 */
public class VirtualRegisterBoard extends SoftwareComponent implements RegisterBoard {

    //Components
    private Bus addressBus = new Bus("Address Bus", width);
    private Bus dataBus = new Bus("Data Bus", width);
    private Register registerA = new Register("Register A", addressBus, dataBus);
    private Register registerB = new Register("Register B", addressBus, dataBus);

    //Signals
    private RegisterSignalSet registerASignalSet = registerA.getAllSignals();
    private RegisterSignalSet registerBSignalSet = registerB.getAllSignals();

    //Bus Connections
    private BidirectionalConnection addressBusConnection = addressBus.createBidirectionalConnection();
    private BidirectionalConnection dataBusConnection = dataBus.createBidirectionalConnection();

    public VirtualRegisterBoard() {
        super("Virtual 2-bit Register Board", 2);
    }

    @Override
    public RegisterSignalSet getRegisterASignalSet() {
        return registerASignalSet;
    }

    @Override
    public RegisterSignalSet getRegisterBSignalSet() {
        return registerBSignalSet;
    }

    public BidirectionalConnection getAddressBusConnection() {
        return addressBusConnection;
    }

    public BidirectionalConnection getDataBusConnection() {
        return dataBusConnection;
    }
}
