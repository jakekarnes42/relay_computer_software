package org.karnes.homebrew.hardware;

import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.WriteableBus;
import org.karnes.homebrew.emulator.component.bus.connection.WriteToBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WriteableBusConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WriteableHardwareBus implements WriteableBus {
    private final String name;
    private final MCP23017 mcp23017;
    private final int width;
    private final MCP23017Pin[] outputPins;
    private final List<WriteableBusConnection> connections;


    public WriteableHardwareBus(String name, MCP23017 mcp23017, MCP23017Pin[] outputPins) {

        this.name = Objects.requireNonNull(name, "A name must be provided for a HardwareBus");
        this.mcp23017 = Objects.requireNonNull(mcp23017, "An MCP23017 must be provided for a HardwareBus");

        this.outputPins = Objects.requireNonNull(outputPins, "Must provide output pins for a WriteableHardwareBus");

        if (outputPins.length == 0) {
            throw new IllegalArgumentException("Must provide output pins for a WriteableHardwareBus");
        }
        mcp23017.setOutput(outputPins);
        this.width = outputPins.length;

        connections = new ArrayList<>();
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
    public synchronized List<WriteableBusConnection> getConnections() {
        return connections;
    }


    @Override
    public synchronized void refreshValues() {
        //Check each connection to build our new value
        ArbitraryBitSet value = new ArbitraryBitSet(width);
        for (WriteableBusConnection connection : getConnections()) {
            FixedBitSet fromConnectionValue = connection.getValueFromConnection();

            //Check every bit of incoming values from connected devices
            for (int i = 0; i < width; i++) {
                //if it's true, set that value to true
                if (fromConnectionValue.get(i)) {
                    value = value.set(i, true);
                }
            }

        }

        //Write out the bits according to our software connections
        mcp23017.write(outputPins, value);
    }

    @Override
    public synchronized WriteableBusConnection getWriteConnection() {
        WriteToBusConnection connection = new WriteToBusConnection(this);
        connections.add(connection);
        return connection;
    }
}
