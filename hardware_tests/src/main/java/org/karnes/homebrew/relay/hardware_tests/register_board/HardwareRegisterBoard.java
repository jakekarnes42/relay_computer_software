package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnectionWrapper;
import org.karnes.homebrew.relay.common.hardware.*;

import static org.karnes.homebrew.relay.common.hardware.MCP23017Pin.*;

/**
 * Provides connections to the 2 Bit Register Board Rev A
 */
public class HardwareRegisterBoard extends SoftwareComponent implements RegisterBoard {


    //Signals
    WritableSignalConnection registerASelectA;
    WritableSignalConnection registerALoadA;
    WritableSignalConnection registerASelectD;
    WritableSignalConnection registerALoadD;

    WritableSignalConnection registerBSelectA;
    WritableSignalConnection registerBLoadA;
    WritableSignalConnection registerBSelectD;
    WritableSignalConnection registerBLoadD;

    //Bus Connections
    BidirectionalConnection addressBusConnection;
    BidirectionalConnection dataBusConnection;

    public HardwareRegisterBoard() {
        super("Hardware 2-bit Register Board", 2);

        //Initialize hardware components
        I2CBus i2cBus = I2CBus.getI2CBus();
        MCP23017 mcp23017 = i2cBus.getMCP23017(MCP23017Address.ADDR0);
        //First, set all the ports to output for a known state
        mcp23017.setAllPinsToOutput();

        //Set up connections
        registerASelectA = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterA_Select_A",
                        mcp23017, GPIOB_3));
        registerALoadA = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterA_Load_A",
                        mcp23017, GPIOB_2));
        registerASelectD = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterA_Select_D",
                        mcp23017, GPIOB_0));
        registerALoadD = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterA_Load_D",
                        mcp23017, GPIOB_1));

        registerBSelectA = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterB_Select_A",
                        mcp23017, GPIOB_7));
        registerBLoadA = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterB_Load_A",
                        mcp23017, GPIOB_6));
        registerBSelectD = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterB_Select_D",
                        mcp23017, GPIOB_4));
        registerBLoadD = new WritableSignalConnectionWrapper(
                new WritableHardwareConnection("RegisterB_Load_D",
                        mcp23017, GPIOB_5));

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
    public WritableSignalConnection getRegisterASelectA() {
        return registerASelectA;
    }

    @Override
    public WritableSignalConnection getRegisterALoadA() {
        return registerALoadA;
    }

    @Override
    public WritableSignalConnection getRegisterASelectD() {
        return registerASelectD;
    }

    @Override
    public WritableSignalConnection getRegisterALoadD() {
        return registerALoadD;
    }

    @Override
    public WritableSignalConnection getRegisterBSelectA() {
        return registerBSelectA;
    }

    @Override
    public WritableSignalConnection getRegisterBLoadA() {
        return registerBLoadA;
    }

    @Override
    public WritableSignalConnection getRegisterBSelectD() {
        return registerBSelectD;
    }

    @Override
    public WritableSignalConnection getRegisterBLoadD() {
        return registerBLoadD;
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