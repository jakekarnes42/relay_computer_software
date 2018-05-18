package org.karnes.mcp23017;

import mraa.*;

/**
 * A quick tester class for the MCP23017 and the BeagleBone Black
 *
 * <h2>BeagleBone Black Setup</h2>
 * First need to install Java. Next install <a href="https://github.com/intel-iot-devkit/mraa">MRAA</a>
 *
 * <h2>Board Setup</h2>
 * 8-bit I/O Port B
 * BBB P9_12 (GPIO_60) connected to MCP23017 PIN 1 (GPB0) - MRAA 58
 * BBB P9_15 (GPIO_48) connected to MCP23017 PIN 2 (GPB1) - MRAA 61
 * BBB P9_23 (GPIO_49) connected to MCP23017 PIN 3 (GPB2) - MRAA 69
 * BBB P9_25 (GPIO_117) connected to MCP23017 PIN 4 (GPB3) - MRAA 71
 * BBB P9_27 (GPIO_115) connected to MCP23017 PIN 5 (GPB4) - MRAA 73
 * BBB P9_41 (GPIO_20) connected to MCP23017 PIN 6 (GPB5) - MRAA 87
 * BBB P9_42 (GPIO_7) connected to MCP23017 PIN 7 (GPB6) - MRAA 88
 * BBB P8_7 (GPIO_66) connected to MCP23017 PIN 8 (GPB7) - MRAA 07
 * <p>
 * Power and I2C lines
 * BBB P9_3 (VDD 3.3V) connected to MCP23017 PIN 9 (VDD) - MRAA 49
 * BBB P9_2 (DGND) connected to MCP23017 PIN 10 (VSS) - MRAA 47
 * BBB P9_19 (I2C2_SCL) connected to MCP23017 PIN 12 (SCK) - MRAA 65
 * BBB P9_20 (I2C2_SDA) connected to MCP23017 PIN 13 (SDA) - MRAA 66
 * <p>
 * Hardware address and RESET
 * BBB P9_2 (DGND) connected to MCP23017 PIN 15 (A0) - MRAA 47
 * BBB P9_2 (DGND) connected to MCP23017 PIN 16 (A1) - MRAA 47
 * BBB P9_2 (DGND) connected to MCP23017 PIN 17 (A2) - MRAA 47
 * BBB P9_3 (VDD 3.3V) connected to MCP23017 PIN 18 (~RESET) - MRAA 49
 * <p>
 * Interrupts
 * BBB P8_8 (GPIO_67) connected to MCP23017 PIN 19 (INTB) - MRAA 08
 * BBB P8_18 (GPIO_65) connected to MCP23017 PIN 20 (INTA) - MRAA 18
 * <p>
 * 8-bit I/O Port A
 * BBB P8_9 (GPIO_69) connected to MCP23017 PIN 21 (GPA0) - MRAA 09
 * BBB P8_10 (GPIO_68) connected to MCP23017 PIN 22 (GPA1) - MRAA 10
 * BBB P8_11 (GPIO_45) connected to MCP23017 PIN 23 (GPA2) - MRAA 11
 * BBB P8_12 (GPIO_44) connected to MCP23017 PIN 24 (GPA3) - MRAA 12
 * BBB P8_14 (GPIO_26) connected to MCP23017 PIN 25 (GPA4) - MRAA 14
 * BBB P8_15 (GPIO_47) connected to MCP23017 PIN 26 (GPA5) - MRAA 15
 * BBB P8_16 (GPIO_46) connected to MCP23017 PIN 27 (GPA6) - MRAA 16
 * BBB P8_17 (GPIO_27) connected to MCP23017 PIN 28 (GPA7) - MRAA 17
 *
 * <h2>Execution</h2>
 * java -Djava.library.path=/usr/lib/arm-linux-gnueabihf/ -jar <output jar filename>
 */

public class MCP23017Tester {

    static int[] gpioAPins = {9, 10, 11, 12, 14, 15, 16, 17};
    static int[] gpioBPins = {58, 61, 69, 71, 73, 87, 88, 7};

    public static void main(String args[]) {
        try {
            System.loadLibrary("mraajava");
        } catch (UnsatisfiedLinkError e) {
            System.err.println(
                    "Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n" +
                            e);
            System.exit(1);
        }
        mraa.init();
        String board = mraa.getPlatformName();
        String version = mraa.getVersion();
        System.out.println("hello mraa");
        System.out.println(String.format("Version: %s", version));
        System.out.println(String.format("Running on %s", board));
        System.out.println();

//        int i2cBusCount = mraa.getI2cBusCount();
//        System.out.println(String.format("I2C Bus Count: %d", i2cBusCount));
//        int defaultI2cBus = mraa.getDefaultI2cBus();
//        System.out.println(String.format("I2C Default Bus: %d", defaultI2cBus));

        //This is really i2c-2 on the Beagle Bone Black
        I2c i2c = new I2c(1);
        short hardwareAddress = (short) 0x20;//The hardware address of the MCP23017
        Result addressResult = i2c.address(hardwareAddress);
        if (addressResult != Result.SUCCESS) {
            System.err.println("Error getting device at hardware address " + hardwareAddress + ": " + addressResult);
            mraa.printError(addressResult);
            System.exit(addressResult.swigValue());
        }

        short valueAt12 = i2c.readReg((short) 0x12);
        System.out.println(String.format("GPIOA input status: %8s", String.format("%8s", Integer.toBinaryString(valueAt12)).replace(" ", "0")));

        short valueAt13 = i2c.readReg((short) 0x13);
        System.out.println(String.format("GPIOB input status: %8s", String.format("%8s", Integer.toBinaryString(valueAt13)).replace(" ", "0")));

        resetAllGPIOPins();

        valueAt12 = i2c.readReg((short) 0x12);
        System.out.println(String.format("GPIOA input status: %8s", String.format("%8s", Integer.toBinaryString(valueAt12)).replace(" ", "0")));

        valueAt13 = i2c.readReg((short) 0x13);
        System.out.println(String.format("GPIOB input status: %8s", String.format("%8s", Integer.toBinaryString(valueAt13)).replace(" ", "0")));

        enableAllPullups(i2c);


        valueAt12 = i2c.readReg((short) 0x12);
        System.out.println(String.format("GPIOA input status: %8s", String.format("%8s", Integer.toBinaryString(valueAt12)).replace(" ", "0")));

        valueAt13 = i2c.readReg((short) 0x13);
        System.out.println(String.format("GPIOB input status: %8s", String.format("%8s", Integer.toBinaryString(valueAt13)).replace(" ", "0")));

    }

    private static void enableAllPullups(I2c i2c) {
        i2c.writeReg((short) 0xC, (short) 0b1111_1111); //Enable all pull ups on GPPUA
        i2c.writeReg((short) 0xD, (short) 0b1111_1111); //Enable all pull ups on GPPUB

        short GPPUA = i2c.readReg((short) 0xC);
        System.out.println(String.format("GPPUA status: %8s", String.format("%8s", Integer.toBinaryString(GPPUA)).replace(" ", "0")));

        short GPPUB = i2c.readReg((short) 0xD);
        System.out.println(String.format("GPPUB input status: %8s", String.format("%8s", Integer.toBinaryString(GPPUB)).replace(" ", "0")));
        System.out.println("Enabled all pull ups.");
    }

    private static void setGPIOOff(int pinNum) {
        Gpio gpio = new Gpio(pinNum);
        Result result = gpio.write(0);
        if (result != Result.SUCCESS) {
            System.err.println("Error setting pin " + pinNum + " off: " + result);
            mraa.printError(result);
            System.exit(result.swigValue());
        }
    }

    private static void setGPIOOn(int pinNum) {
        Gpio gpio = new Gpio(pinNum);
        Result result = gpio.write(1);
        if (result != Result.SUCCESS) {
            System.err.println("Error setting pin " + pinNum + " off: " + result);
            mraa.printError(result);
            System.exit(result.swigValue());
        }
    }

    //Sets all GPIO pins to DIR OUT and turns them off.
    private static void resetAllGPIOPins() {
        for (int i = 0; i < gpioAPins.length; i++) {
            setOutAndOff(gpioAPins[i]);
        }
        for (int i = 0; i < gpioBPins.length; i++) {
            setOutAndOff(gpioBPins[i]);
        }
        System.out.println("Reset all pins to direction out and off.");
    }

    private static void setOutAndOff(int pinNum) {
        Gpio gpio = new Gpio(pinNum);
        Result result = gpio.dir(Dir.DIR_OUT_LOW);
        if (result != Result.SUCCESS) {
            System.err.println("Error setting pin " + pinNum + " out and off: " + result);
            mraa.printError(result);
//            System.exit(result.swigValue());
        }
    }


}

