package org.karnes.homebrew.hardware;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.BidirectionalBus;
import org.karnes.homebrew.emulator.component.bus.connection.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BidirectionalHardwareBus implements BidirectionalBus {


    private final String name;
    private final MCP23017 mcp23017;
    private final List<BusConnection> connections;
    private final int width;
    private final MCP23017Pin[] inputPins;
    private final MCP23017Pin[] outputPins;

    private FixedBitSet lastValue;
    private Thread monitorThread = null;


    public BidirectionalHardwareBus(String name, MCP23017 mcp23017, MCP23017Pin[] inputPins, MCP23017Pin[] outputPins) {

        this.name = Objects.requireNonNull(name, "A name must be provided for a HardwareBus");
        this.mcp23017 = Objects.requireNonNull(mcp23017, "An MCP23017 must be provided for a HardwareBus");
        this.inputPins = Objects.requireNonNull(inputPins, "Must provide input pins for a ReadableHardwareBus");

        if (inputPins.length == 0) {
            throw new IllegalArgumentException("Must provide input pins for a ReadableHardwareBus");
        }
        mcp23017.setInput(inputPins);

        this.outputPins = Objects.requireNonNull(outputPins, "Must provide output pins for a WriteableHardwareBus");

        if (outputPins.length == 0) {
            throw new IllegalArgumentException("Must provide output pins for a WriteableHardwareBus");
        }
        mcp23017.setOutput(outputPins);

        if (inputPins.length != outputPins.length) {
            throw new IllegalArgumentException("The number of input pins must match the number of output pins");
        }
        this.width = inputPins.length;


        connections = new ArrayList<>();
        lastValue = getValue();
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
    public synchronized List<? extends BusConnection> getConnections() {
        return connections;
    }


    @Override
    public synchronized void refreshValues() {
        //Copy the old value for comparison
        FixedBitSet oldValue = lastValue.copy();

        //Check each connection to build our new value
        ArbitraryBitSet value = new ArbitraryBitSet(width);
        for (BusConnection connection : getConnections()) {
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

        //Write out the bits according to our software connections
        mcp23017.write(outputPins, value);

        FixedBitSet newValue = getValue();
        //Compare to see if there was a change after updating
        if (!oldValue.equals(newValue)) {
            //There was a change
            BusValueChangedEvent event = new BusValueChangedEvent(name, newValue);

            //Notify all interruptable connections
            for (BusConnection connection : connections) {
                if (connection instanceof InterruptableBusConnection) {
                    ((InterruptableBusConnection) connection).handleBusValueChangedEvent(event);
                }
            }
        }

    }

    @Override
    public synchronized WriteableBusConnection getWriteConnection() {
        WriteToBusConnection connection = new WriteToBusConnection(this);
        connections.add(connection);
        return connection;
    }

    @Override
    public synchronized FixedBitSet getValue() {
        if (inputPins == null) {
            throw new IllegalStateException("Unable to read from hardware bus " + name + " because no input pins were provided");
        }

        //Read the value from the hardware
        FixedBitSet value = mcp23017.read(inputPins);
        //TODO: Do we need to do any debouncing?

        return value;
    }

    synchronized void startMonitorThread() {
        if (monitorThread == null || !monitorThread.isAlive()) {
            //Start a new thread to monitor the values
            monitorThread = new Thread(new BusMonitor(this));
            //This thread should not prevent Java from exiting
            monitorThread.setDaemon(true);
            monitorThread.start();
        }
    }

    @Override
    public synchronized ReadableBusConnection getReadConnection() {
        ReadFromBusConnection connection = new ReadFromBusConnection(this);
        connections.add(connection);
        return connection;
    }

    @Override
    public synchronized InterruptFromBusConnection getInterruptConnection(BusValueChangeHandler handler) {
        InterruptFromBusConnection connection = new InterruptFromBusConnection(this, handler);
        connections.add(connection);

        startMonitorThread();

        return connection;
    }


    @Override
    public synchronized BidirectionalBusConnection getBidirectionalConnection() {
        BidirectionalBusConnection connection = new BidirectionalBusConnection(this);
        connections.add(connection);
        return connection;
    }

    @Override
    public synchronized BidirectionalInterruptableBusConnection getBidirectionalInterruptableBusConnection(BusValueChangeHandler handler) {
        BidirectionalInterruptableBusConnection connection = new BidirectionalInterruptableBusConnection(this, handler);
        connections.add(connection);

        startMonitorThread();

        return connection;
    }
}
