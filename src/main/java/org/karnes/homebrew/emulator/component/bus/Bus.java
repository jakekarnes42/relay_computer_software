package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.emulator.component.bus.connection.SoftwareConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * A virtual bus which provides connections (virtual or physical) between components.
 */
public class Bus extends SoftwareComponent {

    private final List<ReadableConnection> readableConnections;
    private final List<WritableConnection> writableConnections;
    private FixedBitSet currentValue;


    /**
     * A new virtual, software only Bus. Its value is initialized to all 0's.
     *
     * @param name  The name of the Bus
     * @param width The width of the Bus
     */
    public Bus(String name, int width) {
        super(name, width);
        this.readableConnections = new ArrayList<>();
        this.writableConnections = new ArrayList<>();
        this.currentValue = new FixedBitSet(width);
    }

    public void addReadableConnection(ReadableConnection readableConnection) {
        readableConnections.add(readableConnection);
    }

    public ReadableConnection createReadableConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " Readable Connection #" + readableConnections.size(), getWidth());
        addWritableConnection(connection);
        return connection;
    }

    public void addWritableConnection(WritableConnection writableConnection) {
        writableConnections.add(writableConnection);
    }

    public WritableConnection createWritableConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " Writable Connection #" + writableConnections.size(), getWidth());
        addReadableConnection(connection);
        return connection;
    }

    public SoftwareConnection createConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " Bidirectional Connection", getWidth());
        addReadableConnection(connection);
        addWritableConnection(connection);
        return connection;
    }

    /**
     * Checks if the bus needs to update, and if so, updates connected components
     *
     * @return
     */
    public boolean update() {
        //Get the values currently being written onto the Bus by the ReadableConnections
        FixedBitSet value = new FixedBitSet(width);
        //Check each connection
        for (ReadableConnection connection : readableConnections) {
            FixedBitSet fromConnectionValue = connection.readValue();
            //Check every bit of incoming values from connected devices
            for (int i = 0; i < width; i++) {
                //if it's true, set that value to true
                if (fromConnectionValue.get(i)) {
                    value = value.set(i, true);
                }
            }
        }

        //Check if the value has changed
        if (value.equals(currentValue)) {
            //The value hasn't changed. All done.
            return false;
        } else {
            //The value has changed. Update our new current value.
            currentValue = value;

            //Send the new value to all the WritableConnections
            for (WritableConnection connection : writableConnections) {
                connection.writeValue(currentValue);
            }

            //Everything has been updated. All done.
            return true;
        }

    }
}
