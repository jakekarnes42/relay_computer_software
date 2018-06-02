package org.karnes.homebrew.emulator.component;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * Represents the wired connection between a Bus and some other device on the computer. The BusConnection facilitates
 * passing values back and forth and firing update events
 */
public class BusConnection {
    private final Bus bus;
    private final BusConnectedDevice device;
    private FixedBitSet value;

    public BusConnection(Bus bus) {
        this(bus, null);
    }

    public BusConnection(Bus bus, BusConnectedDevice device) {
        this.bus = bus;
        value = new ArbitraryBitSet(bus.getWidth());
        this.device = device;

        //Give a reference back to the bus
        bus.addConnection(this);
    }

    public int getWidth() {
        return bus.getWidth();
    }

    /**
     * The current value of the Bus, which is the result of a logical OR of all the connection's output values onto the Bus
     *
     * @return
     */
    public FixedBitSet getBusValue() {
        return bus.getBusValue();
    }

    /**
     * Represents that a new value from the device is being outputted onto the Bus. Note that there may be other devices
     * on the bus which are outputting different values, so that Bus' value after this method call may not be the same
     * value passed to this method.
     *
     * @param value The value to output onto the Bus
     */
    public void setToBusOutput(FixedBitSet value) {
        if (value.size() != getWidth()) {
            throw new IllegalArgumentException("Invalid new value. Expected size: " + getWidth() + " Received: " + value.size());
        }
        this.value = value;
        //This connection's input to the Bus has changed, the bus should handle this.
        bus.notifyInputChanged();
    }

    /**
     * The current value that this device is outputting to the connected Bus
     *
     * @return The current output value from this device to the connected Bus
     */
    public FixedBitSet getToBusOutput() {
        return value;
    }

    /**
     * This method simply passes the event along to the device to handle the fact that the bus has a new value.
     * The device can do whatever it needs to with this information, or ignore it.
     *
     * @param busValueChangedEvent
     */
    public void handleBusValueChangedEvent(BusValueChangedEvent busValueChangedEvent) {
        if (device != null) {
            device.handleBusValueChangedEvent(busValueChangedEvent);
        }
    }
}
