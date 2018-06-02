package org.karnes.homebrew.emulator.component;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Bus (group of wires)
 */
public class Bus {
    private final String name;
    private final int width;
    private List<BusConnection> connections = new ArrayList<>();
    private FixedBitSet lastValue;

    public Bus(String name, int width) {
        this.name = name;
        this.width = width;
        lastValue = new ArbitraryBitSet(width);
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public List<BusConnection> getConnections() {
        return connections;
    }

    public void addConnection(BusConnection connection) {
        if (width != connection.getWidth()) {
            throw new IllegalArgumentException("Invalid width. Expected: " + width + " Recieved: " + connection.getWidth());
        }
        connections.add(connection);
    }

    /**
     * Gets the current value of the bus, which is the results of OR-ing all the inputs from the Bus' connections
     *
     * @return current value of the bus
     */
    public FixedBitSet getBusValue() {
        ArbitraryBitSet value = new ArbitraryBitSet(width);
        //Check each connection
        for (BusConnection connection : connections) {
            FixedBitSet fromConnectionValue = connection.getToBusOutput();
            //Check every bit of incoming values from connected devices
            for (int i = 0; i < width; i++) {
                //if it's true, set that value to true
                if (fromConnectionValue.get(i)) {
                    value = value.set(i, true);
                }
            }
        }

        lastValue = value;
        return value;
    }

    /**
     * External notification that one of the inputs to the Bus has changed, the Bus will in turn notify each of its BusConnections
     */
    public void notifyInputChanged() {
        //Copy the old value for comparison
        FixedBitSet oldValue = lastValue.copy();

        //Reload the value
        FixedBitSet updatedValue = getBusValue();

        //Compare to see if there was a change after updating
        if (!oldValue.equals(updatedValue)) {
            //There was a change
            //Notify all connections
            for (BusConnection connection : connections) {
                connection.handleBusValueChangedEvent(new BusValueChangedEvent(name, updatedValue));
            }
        }

    }
}
