package org.karnes.mcp23017;

public class BBBConnectionTester {


    public static void main(String args[]) throws InterruptedException {
        I2CBus bus = I2CBus.getBus();
        MCP23017 mcp23017 = bus.getMCP23017(0x20);

        //First, set all the ports to output
        mcp23017.setAllPinsToOuput();

        //Enable the two input pins
        mcp23017.setInput(MCP23017Pin.GPIOA_0);
        mcp23017.setInput(MCP23017Pin.GPIOB_0);

        System.out.println("MCP23017 Test is ready");
        System.out.println();

        mcp23017.outputOn(MCP23017Pin.GPIOA_7);
        System.out.println("Output 0 turned on.");
        Thread.sleep(3000);
        mcp23017.outputOff(MCP23017Pin.GPIOA_7);
        System.out.println("Output 0 turned off.");
        Thread.sleep(1000);

        mcp23017.outputOn(MCP23017Pin.GPIOB_7);
        System.out.println("Output 1 turned on.");
        Thread.sleep(3000);
        mcp23017.outputOff(MCP23017Pin.GPIOB_7);
        System.out.println("Output 1 turned off.");
        Thread.sleep(1000);

        System.out.println("Ready for input testing. Turn on and off 12V switches");
        while (true) {
            boolean gpioAStatus = mcp23017.getPinStatus(MCP23017Pin.GPIOA_0);
            if (gpioAStatus) {
                System.out.println("GPIOA0 was turned on!");
            }

            boolean gpioBStatus = mcp23017.getPinStatus(MCP23017Pin.GPIOB_0);
            if (gpioBStatus) {
                System.out.println("GPIOB0 was turned on!");
            }
        }


    }


}

