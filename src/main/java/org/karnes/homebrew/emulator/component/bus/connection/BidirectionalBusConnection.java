package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.BidirectionalBus;

/**
 * A connection to a {@link BidirectionalBus}. This connection can be used to read from and write to the bus through software.
 *
 * @see ReadableBusConnection
 * @see WriteableBusConnection
 */
public class BidirectionalBusConnection implements ReadableBusConnection, WriteableBusConnection {

    private ReadableBusConnection readConnection;
    private WriteableBusConnection writeConnection;


    public BidirectionalBusConnection(BidirectionalBus bus) {
        readConnection = new ReadFromBusConnection(bus);
        writeConnection = new WriteToBusConnection(bus);
    }

    @Override
    public int getWidth() {
        return readConnection.getWidth();
    }

    @Override
    public FixedBitSet readBusValue() {
        return readConnection.readBusValue();
    }

    @Override
    public FixedBitSet getValueFromConnection() {
        FixedBitSet valueFromConnection = writeConnection.getValueFromConnection();
        return valueFromConnection;
    }

    @Override
    public void writeValueToBus(FixedBitSet value) {
        writeConnection.writeValueToBus(value);
    }
}
