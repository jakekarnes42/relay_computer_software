package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.component.bus.connection.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A virtual bus which provides connections between virtual components.
 * <b>Warning:</b> The Bus should not be connected to another bus (physical or virtual) yet, as this isn't appropriately
 * handled.
 */
public class Bus extends SoftwareComponent {

    private final List<ReadableConnection> readableConnections;
    private final List<WritableConnection> writableConnections;
    private FixedBitSet currentValue;


    /**
     * A new virtual, software-only Bus. Its value is initialized to all 0's.
     *
     * @param name  The name of the Bus.
     * @param width The width of the Bus.
     */
    public Bus(String name, int width) {
        super(name, width);
        this.readableConnections = new ArrayList<>();
        this.writableConnections = new ArrayList<>();
        this.currentValue = new FixedBitSet(width);
    }

    /**
     * Connects this Bus to a {@link ReadableConnection}. The Bus will read from this connection when determining its state.
     *
     * @param readableConnection The connection.
     */
    public void addReadableConnection(ReadableConnection readableConnection) {
        readableConnections.add(readableConnection);
    }

    /**
     * Creates a new {@link ReadableConnection} from this Bus, which can be used to read the Bus' state.
     *
     * @return The connection.
     */
    public ReadableConnection createReadableConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " Readable Connection #" + readableConnections.size(), getWidth(), this::handleNewValue);
        addWritableConnection(connection);
        return connection;
    }

    /**
     * Connects this Bus to a {@link WritableConnection} which can receive updates as this Bus changes its value.
     *
     * @param writableConnection The connection.
     */
    public void addWritableConnection(WritableConnection writableConnection) {
        writableConnections.add(writableConnection);
    }

    /**
     * Creates a new {@link WritableConnection} from this Bus, which can be used to write new values onto the Bus.
     *
     * @return The connection.
     */
    public WritableConnection createWritableConnection() {
        SoftwareConnection connection = new SoftwareConnection(getName() + " Writable Connection #" + writableConnections.size(), getWidth(), this::handleNewValue);
        addReadableConnection(connection);
        return connection;
    }

    /**
     * Creates a new {@link BidirectionalConnection} which can be used to read from or write to the Bus.
     *
     * @return The connection.
     */
    public BidirectionalConnection createBidirectionalConnection() {
        DuplexSoftwareConnection duplexConnection = new DuplexSoftwareConnection(getName() + " Duplex Connection", getWidth(), createReadableConnection(), createWritableConnection());

        BidirectionalConnection busSideConnection = duplexConnection.getBusSideConnection();
        addReadableConnection(busSideConnection);
        addWritableConnection(busSideConnection);

        BidirectionalConnection otherSideConnection = duplexConnection.getOtherSideConnection();
        return otherSideConnection;
    }

    private void handleNewValue(FixedBitSet newValue) {
        //Send out an update to the connections
        update();
    }

    /**
     * Checks if the bus needs to update, and if so, updates connected components.
     *
     * @return {@code true} if the Bus updated its Connections, {@code false} otherwise.
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
