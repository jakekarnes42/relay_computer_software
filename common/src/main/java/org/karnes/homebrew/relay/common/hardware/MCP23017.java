package org.karnes.homebrew.relay.common.hardware;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

import java.util.Arrays;

/**
 * Represents a MCP23017 attached to the I2C bus.
 * <p>
 * TODO: this needs a nicer interface. It works, but let's make this "better"
 */
public class MCP23017 {

    private final I2CBus bus;
    private final MCP23017Address address;

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
     * This shouldn't be used directly, instead use {@link I2CBus#getMCP23017(MCP23017Address)}
     *
     * @param bus
     * @param address
     */
    protected MCP23017(I2CBus bus, MCP23017Address address) {
        this.bus = bus;
        this.address = address;

        //Gives us a stable starting place
        setAllPinsToOutput();
    }

    /**
     * Sets all GPIO pins (A and B) to outputs
     */
    public synchronized void setAllPinsToOutput() {
        FixedBitSet zero = new FixedBitSet(Byte.SIZE);
        bus.writeByte(address, (short) (IO_DIRECTION_REGISTER + MCP23017Port.A.getOffset()), zero);
        bus.writeByte(address, (short) (IO_DIRECTION_REGISTER + MCP23017Port.B.getOffset()), zero);
    }


    /**
     * Sets the provided pin(s) to input mode, meaning they can be used to read incoming values.
     *
     * @param pins The pin(s) which should be set to input mode.
     */
    public synchronized void setInput(MCP23017Pin... pins) {
        updateRegisterForPins(IO_DIRECTION_REGISTER, pins, FixedBitSet.allOnes(pins.length));
    }

    /**
     * Sets the provided pin(s) to output mode, meaning they can be used to write outgoing values.
     *
     * @param pins The pin(s) which should be set to output mode.
     */
    public synchronized void setOutput(MCP23017Pin... pins) {
        updateRegisterForPins(IO_DIRECTION_REGISTER, pins, new FixedBitSet(pins.length));

    }

    /**
     * Writes the value to the outputPins. The outputPins should already be set to output mode via {@link #setAllPinsToOutput()} or {@link #setOutput(MCP23017Pin...)}
     *
     * @param outputPins
     * @param value
     */
    public synchronized void write(FixedBitSet value, MCP23017Pin... outputPins) {
        updateRegisterForPins(GPIO_PORT_REGISTER, outputPins, value);
    }

    public synchronized FixedBitSet read(MCP23017Pin... inputPins) {
        //Get A and B current values
        FixedBitSet aValue = bus.readByte(address, (short) (GPIO_PORT_REGISTER + MCP23017Port.A.getOffset()));
        FixedBitSet bValue = bus.readByte(address, (short) (GPIO_PORT_REGISTER + MCP23017Port.B.getOffset()));

        FixedBitSet result = new FixedBitSet(inputPins.length);
        for (int i = 0; i < inputPins.length; i++) {
            MCP23017Pin pin = inputPins[i];
            boolean value;
            if (pin.getPort() == MCP23017Port.A) {
                value = aValue.get(pin.getPosition());
            } else {
                value = bValue.get(pin.getPosition());
            }
            result = result.set(i, value);
        }
        return result;
    }

    /**
     * Reads a single pin's current status
     *
     * @param pin
     * @return The value of that pin
     */
    public synchronized boolean getPinStatus(MCP23017Pin pin) {
        //Get the whole port's current status
        FixedBitSet portStatus = bus.readByte(address, (short) (GPIO_PORT_REGISTER + pin.getPort().getOffset()));

        return portStatus.get(pin.getPosition());
    }


    private synchronized void updateRegisterForPins(short register, MCP23017Pin[] pins, FixedBitSet values) {
        //Check lengths
        if (pins.length != values.size()) {
            throw new IllegalStateException("Unequal pins and value. Pins: " + Arrays.toString(pins) + " Value: " + values);
        }

        //Update pins for Port A
        updateRegisterForPortWithPins(register, MCP23017Port.A, pins, values);

        //Update the pins for Port B
        updateRegisterForPortWithPins(register, MCP23017Port.B, pins, values);

    }

    private synchronized void updateRegisterForPortWithPins(short register, MCP23017Port port, MCP23017Pin[] pins, FixedBitSet values) {
        //Get the new value for port
        FixedBitSet currentValue = bus.readByte(address, (short) (register + port.getOffset()));
        FixedBitSet newValue = currentValue.copy();
        for (int i = 0; i < pins.length; i++) {
            MCP23017Pin pin = pins[i];
            if (pin.getPort() == port) {
                newValue = newValue.set(pin.getPosition(), values.get(i));
            }
        }

        //Write the updates if needed
        if (!currentValue.equals(newValue)) {
            bus.writeByte(address, (short) (register + port.getOffset()), newValue);
        }
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

