package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnectionWrapper;
import org.karnes.homebrew.relay.common.emulator.component.register.RegisterSignalSet;
import org.karnes.homebrew.relay.common.hardware.*;

import static org.karnes.homebrew.relay.common.hardware.MCP23017Pin.*;

/**
 * Provides connections to the 2 Bit Register Board Rev A
 */
public class HardwareRegisterBoard extends SoftwareComponent implements RegisterBoard {

    //Signals
    private RegisterSignalSet registerASignalSet;
    private RegisterSignalSet registerBSignalSet;

    //Bus Connections
    private BidirectionalConnection addressBusConnection;
    private BidirectionalConnection dataBusConnection;

    public HardwareRegisterBoard() {
        super("Hardware 2-bit Register Board", 2);

        //Initialize hardware components
        I2CBus i2cBus = I2CBus.getI2CBus();
        MCP23017 mcp23017 = i2cBus.getMCP23017(MCP23017Address.ADDR0);
        //First, set all the ports to output for a known state
        mcp23017.setAllPinsToOutput();

        //Set up connections
        var registerASelectA = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterA_Select_A",
                        mcp23017, GPIOB_3));
        var registerALoadA = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterA_Load_A",
                        mcp23017, GPIOB_2));
        var registerASelectD = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterA_Select_D",
                        mcp23017, GPIOB_0));
        var registerALoadD = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterA_Load_D",
                        mcp23017, GPIOB_1));

        //Create wrapper
        registerASignalSet = new RegisterSignalSet("Hardware Register A", registerASelectA, registerALoadA,
                registerASelectD, registerALoadD);

        //Set up signals for B
        var registerBSelectA = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterB_Select_A",
                        mcp23017, GPIOB_7));
        var registerBLoadA = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterB_Load_A",
                        mcp23017, GPIOB_6));
        var registerBSelectD = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterB_Select_D",
                        mcp23017, GPIOB_4));
        var registerBLoadD = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterB_Load_D",
                        mcp23017, GPIOB_5));

        //Create wrapper
        registerBSignalSet = new RegisterSignalSet("Hardware Register B", registerBSelectA, registerBLoadA,
                registerBSelectD, registerBLoadD);

        //Bus Connections
        addressBusConnection =
                new BidirectionalHardwareConnection("Address Bus Connection",
                        mcp23017, new MCP23017Pin[]{GPIOA_6, GPIOA_4},
                        mcp23017, new MCP23017Pin[]{GPIOA_7, GPIOA_5});
        dataBusConnection = new BidirectionalHardwareConnection("Data Bus Connection",
                mcp23017, new MCP23017Pin[]{GPIOA_2, GPIOA_0},
                mcp23017, new MCP23017Pin[]{GPIOA_3, GPIOA_1});

    }

    @Override
    public RegisterSignalSet getRegisterASignalSet() {
        return registerASignalSet;
    }

    @Override
    public RegisterSignalSet getRegisterBSignalSet() {
        return registerBSignalSet;
    }

    @Override
    public BidirectionalConnection getAddressBusConnection() {
        return addressBusConnection;
    }

    @Override
    public BidirectionalConnection getDataBusConnection() {
        return dataBusConnection;
    }

    @Override
    public int getDelay() {
        //TODO: I don't know what this delay is yet;
        return 25;
    }
}
