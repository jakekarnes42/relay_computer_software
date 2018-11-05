package org.karnes.homebrew.relay.common.hardware.switch_board;


import org.karnes.homebrew.relay.common.hardware.I2CBus;
import org.karnes.homebrew.relay.common.hardware.MCP23017;
import org.karnes.homebrew.relay.common.hardware.MCP23017Address;

/**
 * Tester class for the BBB Connection Test board Rev B
 *
 * <h2>BeagleBone Black Setup</h2>
 * First need to install Java. Next install <a href="https://github.com/intel-iot-devkit/mraa">MRAA</a>
 *
 * <h2>Board Setup</h2>
 * <p>
 * Power
 * BBB P9_1 (DGND) connected to 12V power supply's GND terminal
 * BBB P9_2 (DGND) connected to GND plug on board
 * BBB P9_3 (VDD 3.3V) connected to 3.3V plug on board
 * <p>
 * I2C lines
 * BBB P9_19 (I2C2_SCL) connected to SCL plug on board
 * BBB P9_20 (I2C2_SDA) connected to SDA plug on board
 *
 * <h2>Execution steps</h2>
 * <ol>
 * <li>Compile: <pre>mvn clean install</pre></li>
 * <li>Copy to BBB: <pre>scp target/relay-0.1-SNAPSHOT.jar debian@192.168.7.2:~</pre></li>
 * <li>SSH into BBB: <pre>ssh debian@192.168.7.2</pre></li>
 * <li>Run on BBB: <pre>java -Djava.library.path=/usr/lib/arm-linux-gnueabihf/ -jar relay-0.1-SNAPSHOT.jar</pre></li>
 * </ol>
 */
public class BBBConnectionTester {

    public static void main(String args[]) throws InterruptedException {
        I2CBus i2cBus = I2CBus.getI2CBus();
        MCP23017 mcp23017 = i2cBus.getMCP23017(MCP23017Address.ADDR0);
        //First, set all the ports to output for safety?
        mcp23017.setAllPinsToOutput();

//        //Input only bus w/ interrupt
//        ReadableBus inputOnlyBus = new ReadableHardwareBus("Input Only HardwareBus", mcp23017, new MCP23017Pin[]{MCP23017Pin.GPIOA_0});
//        inputOnlyBus.getInterruptConnection(e -> {
//            System.out.println("Input only bus from GPIOA Changed. Value: " + e.getUpdatedValue());
//        });
//
//        //Output only
//        WritableBus outputOnlyBus = new WritableHardwareBus("Output Only HardwareBus", mcp23017, new MCP23017Pin[]{MCP23017Pin.GPIOA_7});
//        WritableBusConnection writeConnection = outputOnlyBus.getWriteConnection();
//
//        //Bidirectional bus
//        BidirectionalBus biBus = new BidirectionalHardwareBus("Bidirectional HardwareBus", mcp23017, new MCP23017Pin[]{MCP23017Pin.GPIOB_0}, new MCP23017Pin[]{MCP23017Pin.GPIOB_7});
//        BidirectionalInterruptableBusConnection bidirectionalBusConnection = biBus.getBidirectionalInterruptableBusConnection(e -> {
//            System.out.println("Bidirectional bus from GPIOB Changed. Value: " + e.getUpdatedValue());
//        });
//
//
//        System.out.println("MCP23017 Test is ready");
//        System.out.println();
//
//
//        //Turn on and off the output only LED
//        writeConnection.writeValueToBus(new FixedBitSet("1"));
//        System.out.println("Output 0 turned on.");
//        Thread.sleep(3000);
//        writeConnection.writeValueToBus(new FixedBitSet("0"));
//        System.out.println("Output 0 turned off.");
//        Thread.sleep(1000);
//
//        //Do the same for the other bidirectional output LED
//        bidirectionalBusConnection.writeValueToBus(new FixedBitSet("1"));
//        System.out.println("Output 1 turned on.");
//        Thread.sleep(3000);
//        bidirectionalBusConnection.writeValueToBus(new FixedBitSet("0"));
//        System.out.println("Output 1 turned off.");
//        Thread.sleep(1000);
//
//        System.out.println("Ready for input testing. Turn on and off 12V switches");
//
//        //While loop to prevent us from exiting the program (the other threads are deamons)
//        while (true) {
//            Thread.sleep(1000);
//        }


    }


}

