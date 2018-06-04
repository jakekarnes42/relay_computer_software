package org.karnes.homebrew.hardware;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.ReadableBus;
import org.karnes.homebrew.emulator.component.bus.connection.BusValueChangeHandler;
import org.karnes.homebrew.emulator.component.bus.connection.InterruptFromBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.ReadFromBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableBusConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReadableHardwareBus implements ReadableBus {
    private final String name;
    private final MCP23017 mcp23017;
    private final int width;
    private final MCP23017Pin[] inputPins;
    private final List<ReadableBusConnection> connections;

    private Thread monitorThread = null;
    private FixedBitSet lastValue;


    public ReadableHardwareBus(String name, MCP23017 mcp23017, MCP23017Pin[] inputPins) {

        this.name = Objects.requireNonNull(name, "A name must be provided for a HardwareBus");
        this.mcp23017 = Objects.requireNonNull(mcp23017, "An MCP23017 must be provided for a HardwareBus");

        this.inputPins = Objects.requireNonNull(inputPins, "Must provide input pins for a ReadableHardwareBus");

        if (inputPins.length == 0) {
            throw new IllegalArgumentException("Must provide input pins for a ReadableHardwareBus");
        }
        mcp23017.setInput(inputPins);
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
    public synchronized List<ReadableBusConnection> getConnections() {
        return connections;
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


}
