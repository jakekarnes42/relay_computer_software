package org.karnes.mcp23017;

/**
 * Represents a MCP23017 attached to the I2C bus.
 */
public class MCP23017 {

    private final I2CBus bus;
    private final short address;

    private final short IO_DIRECTION_REGISTER = 0x0;
    private final short INPUT_POLARITY_REGISTER = 0x2;
    private final short INTERRUPT_ON_CHANGE_CONTROL_REGISTER = 0x4;
    private final short DEFAULT_COMPARE_REGISTER = 0x6;
    private final short INTERRUPT_CONTROL_REGISTER = 0x8;
    private final short CONFIGURATION_REGISTER = 0xA;
    private final short PULLUP_CONFIGURATION_REGISTER = 0xC;
    private final short INTERRUPT_FLAG_REGISTER = 0xE;
    private final short INTERRUPT_CAPTURED_REGISTER = 0x10;
    private final short GPIO_PORT_REGISTER = 0x12;
    private final short OUTPUT_LATCH_REGISTER = 0x14;

    /**
     * This shouldn't be used directly, instead use {@link I2CBus#getMCP23017(short)}
     *
     * @param bus
     * @param address
     */
    protected MCP23017(I2CBus bus, short address) {
        this.bus = bus;
        this.address = address;

        setAllPinsToOuput();
    }

    /**
     * Sets all GPIO pins (A and B) to outputs
     */
    public synchronized void setAllPinsToOuput() {
        bus.writeByte(address, (short) (IO_DIRECTION_REGISTER + MCP23017Port.A.getOffset()), (byte) 0x0);
        bus.writeByte(address, (short) (IO_DIRECTION_REGISTER + MCP23017Port.B.getOffset()), (byte) 0x0);
    }


    public synchronized void setInput(MCP23017Pin pin) {
        updateRegisterForPin(IO_DIRECTION_REGISTER, pin, true);

    }

    public synchronized boolean getPinStatus(MCP23017Pin pin) {
        //Get the whole port's current status
        byte portStatus = bus.readByte(address, (short) (GPIO_PORT_REGISTER + pin.getPort().getOffset()));

        //Create a mask for just this pin
        byte shiftMask = (byte) (((byte) 1) << pin.getPosition());

        //Mask with AND to get just the bit we're interested in
        byte result = (byte) (portStatus & shiftMask);

        //Return whether or not it's zero
        return result != 0;
    }

    public synchronized void outputOn(MCP23017Pin pin) {
        updateRegisterForPin(GPIO_PORT_REGISTER, pin, true);
    }

    public synchronized void outputOff(MCP23017Pin pin) {
        updateRegisterForPin(GPIO_PORT_REGISTER, pin, false);
    }

    private synchronized void updateRegisterForPin(short register, MCP23017Pin pin, boolean value) {
        short exactRegister = (short) (register + pin.getPort().getOffset());
        //get the current pin status for the port
        byte currentStatus = bus.readByte(address, exactRegister);

        //Update the status accordingly
        byte newStatus;
        if (value) {
            newStatus = (byte) (currentStatus | (1 << pin.getPosition()));
        } else {
            newStatus = (byte) (currentStatus & (~(1 << pin.getPosition()));
        }

        //update the pin status
        bus.writeByte(address, exactRegister, newStatus);
    }
}

enum MCP23017Pin {

    GPIOA_0(MCP23017Port.A, (byte) 0),
    GPIOA_1(MCP23017Port.A, (byte) 1),
    GPIOA_2(MCP23017Port.A, (byte) 2),
    GPIOA_3(MCP23017Port.A, (byte) 3),
    GPIOA_4(MCP23017Port.A, (byte) 4),
    GPIOA_5(MCP23017Port.A, (byte) 5),
    GPIOA_6(MCP23017Port.A, (byte) 6),
    GPIOA_7(MCP23017Port.A, (byte) 7),

    GPIOB_0(MCP23017Port.B, (byte) 0),
    GPIOB_1(MCP23017Port.B, (byte) 1),
    GPIOB_2(MCP23017Port.B, (byte) 2),
    GPIOB_3(MCP23017Port.B, (byte) 3),
    GPIOB_4(MCP23017Port.B, (byte) 4),
    GPIOB_5(MCP23017Port.B, (byte) 5),
    GPIOB_6(MCP23017Port.B, (byte) 6),
    GPIOB_7(MCP23017Port.B, (byte) 7);

    private final MCP23017Port port;
    private final byte position;

    MCP23017Pin(MCP23017Port port, byte position) {
        this.port = port;
        this.position = position;
    }

    public byte getPosition() {
        return position;
    }

    public MCP23017Port getPort() {
        return port;
    }
}

enum MCP23017Port {
    A((short) 0), B((short) 1);

    private final short offset;

    MCP23017Port(short offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
