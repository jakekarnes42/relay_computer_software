package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;

import java.util.ArrayList;
import java.util.List;

public class VirtualBus implements Bus {
    private final String name;
    private final int width;
    private final List<AbstractBusConnection> connections;
    private FixedBitSet lastValue;


    public VirtualBus(String name, int width) {
        this.name = name;
        this.width = width;
        connections = new ArrayList<>();
        lastValue = new ArbitraryBitSet(width);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public List<AbstractBusConnection> getConnections() {
        return connections;
    }

    @Override
    public FixedBitSet getValue() {
        ArbitraryBitSet value = new ArbitraryBitSet(width);
        //Check each connection
        for (AbstractBusConnection connection : connections) {
            if (connection instanceof WriteableBusConnection) {
                FixedBitSet fromConnectionValue = ((WriteableBusConnection) connection).getValueFromConnection();
                //Check every bit of incoming values from connected devices
                for (int i = 0; i < width; i++) {
                    //if it's true, set that value to true
                    if (fromConnectionValue.get(i)) {
                        value = value.set(i, true);
                    }
                }
            }
        }

        lastValue = value;
        return value;
    }

    @Override
    public void update() {
        //Copy the old value for comparison
        FixedBitSet oldValue = lastValue.copy();

        //Reload the value
        FixedBitSet updatedValue = getValue();

        //Compare to see if there was a change after updating
        if (!oldValue.equals(updatedValue)) {
            //There was a change
            BusValueChangedEvent event = new BusValueChangedEvent(name, updatedValue);

            //Notify all interruptable connections
            for (AbstractBusConnection connection : connections) {
                if (connection instanceof InterruptableBusConnection) {
                    ((InterruptableBusConnection) connection).handleBusValueChangedEvent(event);
                }
            }
        }
    }

    @Override
    public ReadableBusConnection getReadConnection() {
        ReadFromBusConnection connection = new ReadFromBusConnection(this);
        connections.add(connection);
        return connection;
    }

    @Override
    public InterruptFromBusConnection getInterruptConnection(BusValueChangeHandler handler) {
        InterruptFromBusConnection connection = new InterruptFromBusConnection(this, handler);
        connections.add(connection);
        return connection;
    }

    @Override
    public WriteableBusConnection getWriteConnection() {
        WriteToBusConnection connection = new WriteToBusConnection(this);
        connections.add(connection);
        return connection;
    }

    @Override
    public BidirectionalBusConnection getBidirectionalConnection() {
        BidirectionalBusConnection connection = new BidirectionalBusConnection(this);
        connections.add(connection);
        return connection;
    }


    @Override
    public BidirectionalInterruptableBusConnection getBidirectionalInterruptableBusConnection(BusValueChangeHandler handler) {
        BidirectionalInterruptableBusConnection connection = new BidirectionalInterruptableBusConnection(this, handler);
        connections.add(connection);
        return connection;
    }


}
