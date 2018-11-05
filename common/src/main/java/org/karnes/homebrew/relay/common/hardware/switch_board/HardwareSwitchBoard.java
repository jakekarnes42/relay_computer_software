package org.karnes.homebrew.relay.common.hardware.switch_board;

import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.*;
import org.karnes.homebrew.relay.common.emulator.component.simple.switch_board.SwitchBoard;
import org.karnes.homebrew.relay.common.hardware.*;

import java.util.Scanner;

/**
 * Tester class for the BBB Connection Test board Rev B
 *
 * <h2>Raspberry PI 3 B+ Setup</h2>
 * First need to install Java. Next install <a href="https://github.com/intel-iot-devkit/mraa">MRAA</a>
 *
 * <h2>Board Setup</h2>
 * <p>
 * Power
 * RPI 1 (VDD 3.3V) connected to 3.3V plug on board
 * RPI 6 (3.3V) connected to 12V power supply's GND terminal
 * RPI 9 (DGND) connected to GND plug on board
 *
 * <p>
 * I2C lines:
 * RPI 3 (I2C2_SDA) connected to SDA plug on board
 * RPI 5 (I2C2_SCL) connected to SCL plug on board
 *
 * Probably need to add -Djava.library.path=/usr/lib/arm-linux-gnueabihf/ to VM options
 */
public class HardwareSwitchBoard extends SoftwareComponent implements SwitchBoard {
    private final Scanner userInput;
    private SignalReadableConnection readConnection;
    private SignalWritableConnection writeConnection;
    private SignalBidirectionalConnection bidirectionalConnection;

    /**
     * Creates a new HardwareSwitchBoard.
     */
    public HardwareSwitchBoard() {
        super("HardwareSwitchBoard", 1);

        //Initialize hardware components
        I2CBus i2cBus = I2CBus.getI2CBus();
        MCP23017 mcp23017 = i2cBus.getMCP23017(MCP23017Address.ADDR0);
        //First, set all the ports to output for safety?
        mcp23017.setAllPinsToOutput();

        //Create the readable connection
        ReadableHardwareConnection readableHardwareConnection =
                new ReadableHardwareConnection("HardwareSwitchBoard-ReadableConnection",
                        mcp23017, MCP23017Pin.GPIOA_0);
        readConnection = new SignalReadableConnectionWrapper(readableHardwareConnection);

        //Create the write connection
        WritableHardwareConnection writableHardwareConnection =
                new WritableHardwareConnection("HardwareSwitchBoard-WritableConnection",
                        mcp23017, MCP23017Pin.GPIOA_7);
        writeConnection = new SignalWritableConnectionWrapper(writableHardwareConnection);

        //Create the bidirectional connection
        BidirectionalHardwareConnection bidirectionalHardwareConnection =
                new BidirectionalHardwareConnection("HardwareSwitchBoard-BidirectionalConnection",
                        mcp23017, new MCP23017Pin[]{MCP23017Pin.GPIOB_0},
                        mcp23017, new MCP23017Pin[]{MCP23017Pin.GPIOB_7});
        bidirectionalConnection = new SignalBidirectionalConnectionWrapper(bidirectionalHardwareConnection);

        // Setup ability to read user input
        userInput = new Scanner(System.in);

    }

    @Override
    public SignalReadableConnection getReadConnection() {
        return readConnection;
    }

    @Override
    public SignalWritableConnection getWriteConnection() {
        return writeConnection;
    }

    @Override
    public SignalBidirectionalConnection getBidirectionalConnection() {
        return bidirectionalConnection;
    }

    @Override
    public boolean LED0Status() {
        return getLEDStatus("LED 0");
    }


    @Override
    public boolean LED1Status() {
        return getLEDStatus("LED 1");
    }

    @Override
    public boolean LED2Status() {
        return getLEDStatus("LED 2");
    }

    private boolean getLEDStatus(String ledName) {
        System.out.println("Is " + ledName + " currently on? y/n");
        String input = userInput.next();
        return input.equalsIgnoreCase("y");
    }

    @Override
    public void enableSwitch0() {
        flipSwitch("Switch 0", true);
    }

    @Override
    public void disableSwitch0() {
        flipSwitch("Switch 0", false);
    }

    @Override
    public void enableSwitch1() {
        flipSwitch("Switch 1", true);
    }

    @Override
    public void disableSwitch1() {
        flipSwitch("Switch 1", false);
    }

    private void flipSwitch(String switchName, boolean enable) {
        System.out.println("Please turn " + switchName + " " + (enable ? "ON" : "OFF") + " and enter K");
        userInput.next();
    }


}
