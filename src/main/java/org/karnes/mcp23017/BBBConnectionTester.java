package org.karnes.mcp23017;


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
 * <li>Run on BBB: <pre>java -Djava.library.path=/usr/lib/arm-linux-gnueabihf/ -jar <output jar filename></pre></li>
 * </ol>
 */
public class BBBConnectionTester {

    public static void main(String args[]) throws InterruptedException {
        I2CBus bus = I2CBus.getBus();
        MCP23017 mcp23017 = bus.getMCP23017(MCP23017Address.ADDR0);

        //First, set all the ports to output
        mcp23017.setAllPinsToOuput();

        //Enable the two input pins
        mcp23017.setInput(MCP23017Pin.GPIOA_0);
        mcp23017.setInput(MCP23017Pin.GPIOB_0);

        //Start monitoring threads
        Thread monitor1 = new Thread(new PinMonitor(mcp23017, MCP23017Pin.GPIOA_0));
        monitor1.start();
        Thread monitor2 = new Thread(new PinMonitor(mcp23017, MCP23017Pin.GPIOB_0));
        monitor2.start();

        System.out.println("MCP23017 Test is ready");
        System.out.println();

        //Turn on and off one of the output LEDs
        mcp23017.outputOn(MCP23017Pin.GPIOA_7);
        System.out.println("Output 0 turned on.");
        Thread.sleep(3000);
        mcp23017.outputOff(MCP23017Pin.GPIOA_7);
        System.out.println("Output 0 turned off.");
        Thread.sleep(1000);

        //Do the same for the other output LED
        mcp23017.outputOn(MCP23017Pin.GPIOB_7);
        System.out.println("Output 1 turned on.");
        Thread.sleep(3000);
        mcp23017.outputOff(MCP23017Pin.GPIOB_7);
        System.out.println("Output 1 turned off.");
        Thread.sleep(1000);

        System.out.println("Ready for input testing. Turn on and off 12V switches");

        //We can exit the main method now because the monitoring threads will keep the JVM up
    }


}

/**
 * Naive monitor which captures when an input pin's state changes. This isn't efficient, but it's correct
 */
class PinMonitor implements Runnable {

    private final MCP23017 mcp23017;
    private final MCP23017Pin pin;

    public PinMonitor(MCP23017 mcp23017, MCP23017Pin pin) {
        this.mcp23017 = mcp23017;
        this.pin = pin;
    }

    @Override
    public void run() {
        boolean oldStatus = mcp23017.getPinStatus(pin);
        while (true) {
            boolean currentStatus = mcp23017.getPinStatus(pin);
            if (oldStatus != currentStatus) {
                String message = currentStatus ? "on" : "off";
                System.out.println("Pin " + pin.toString() + " turned " + message);
                oldStatus = currentStatus;
            }
        }

    }
}

