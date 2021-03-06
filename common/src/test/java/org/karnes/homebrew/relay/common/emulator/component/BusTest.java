package org.karnes.homebrew.relay.common.emulator.component;

import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.bus.Bus;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.WritableConnection;
import org.karnes.homebrew.relay.common.emulator.event.CapturingValueChangeHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusTest {
    private static final int DATA_WIDTH = 4;
    private static final FixedBitSet ZERO_VAL = new FixedBitSet(DATA_WIDTH);
    private static final FixedBitSet ONES_VAL = FixedBitSet.allOnes(DATA_WIDTH);
    private static final FixedBitSet MIX1_VAL = new FixedBitSet("0101");
    private static final FixedBitSet MIX2_VAL = new FixedBitSet("1010");

    @Test
    public void testSimpleBus() {
        //Create a new
        Bus bus = new Bus("TestBus", DATA_WIDTH);
        assertEquals("TestBus", bus.getName());
        assertEquals(DATA_WIDTH, bus.getWidth());

        //Get a connection which reads from the bus.
        ReadableConnection readConnection = bus.createReadableConnection();

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Add a connection which writes to the bus
        WritableConnection writeConnection = bus.createWritableConnection();
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Write a new value to the bus
        writeConnection.writeValue(ONES_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, readConnection.readValue());

    }

    @Test
    public void testUpdateWriteBus() {
        //Create a new
        Bus bus = new Bus("TestBus", DATA_WIDTH);
        assertEquals("TestBus", bus.getName());
        assertEquals(DATA_WIDTH, bus.getWidth());

        //Get a connection which reads from the bus.
        ReadableConnection readConnection = bus.createReadableConnection();

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Add a connection which writes to the bus
        WritableConnection writeConnection = bus.createWritableConnection();
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Write a new value to the bus
        writeConnection.writeValue(ONES_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, readConnection.readValue());

        //Change the write connection's value
        writeConnection.writeValue(MIX1_VAL);

        //Check that the update propagated
        assertEquals(MIX1_VAL, readConnection.readValue());

        //Change the write connection's value
        writeConnection.writeValue(MIX2_VAL);

        //Check that the update propagated
        assertEquals(MIX2_VAL, readConnection.readValue());

    }


    @Test
    public void testMultiReadBus() {
        //Create a new
        Bus bus = new Bus("TestBus", DATA_WIDTH);
        assertEquals("TestBus", bus.getName());
        assertEquals(DATA_WIDTH, bus.getWidth());

        //Get a connection which reads from the bus.
        ReadableConnection readConnection = bus.createReadableConnection();

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Add another connection which reads from the BidirectionalBus.
        ReadableConnection readConnection2 = bus.createReadableConnection();

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection2.readValue());

        //Add a connection which writes to the bus
        WritableConnection writeConnection = bus.createWritableConnection();
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Write a new value to the bus
        writeConnection.writeValue(ONES_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, readConnection.readValue());
        assertEquals(ONES_VAL, readConnection2.readValue());

    }

    @Test
    public void testMultiWriteBus() {
        //Create a new
        Bus bus = new Bus("TestBus", DATA_WIDTH);
        assertEquals("TestBus", bus.getName());
        assertEquals(DATA_WIDTH, bus.getWidth());

        //Get a connection which reads from the bus.
        ReadableConnection readConnection = bus.createReadableConnection();

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Add a connection which writes to the bus
        WritableConnection writeConnection = bus.createWritableConnection();
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Add another connection which writes to the bus
        WritableConnection writeConnection2 = bus.createWritableConnection();
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Write a new value to the BidirectionalBus
        writeConnection.writeValue(MIX1_VAL);

        //Check that the update propagated
        assertEquals(MIX1_VAL, readConnection.readValue());

        //Write another new value to the BidirectionalBus
        writeConnection2.writeValue(MIX2_VAL);

        //Check that the connection has the logical OR of the input values
        assertEquals(ONES_VAL, readConnection.readValue());

    }


    @Test
    public void testSimpleBidirectionalBus() {
        //Create a new
        Bus bus = new Bus("TestBus", DATA_WIDTH);
        assertEquals("TestBus", bus.getName());
        assertEquals(DATA_WIDTH, bus.getWidth());

        //Add a bidirectional connection
        BidirectionalConnection connection = bus.createBidirectionalConnection();

        //Read from the bus through the connection
        assertEquals(ZERO_VAL, connection.readValue());

        //Write a new value through the connection
        connection.writeValue(ONES_VAL);

        //Check that the connection has the value
        assertEquals(ONES_VAL, connection.readValue());
    }

    @Test
    public void testMultiBidirectionalBus() {
        //Create a new
        Bus bus = new Bus("TestBus", DATA_WIDTH);
        assertEquals("TestBus", bus.getName());
        assertEquals(DATA_WIDTH, bus.getWidth());

        //Add 2 bidirectional connections
        BidirectionalConnection connection = bus.createBidirectionalConnection();
        BidirectionalConnection connection2 = bus.createBidirectionalConnection();

        //Read from the bus through the connections
        assertEquals(ZERO_VAL, connection.readValue());
        assertEquals(ZERO_VAL, connection2.readValue());

        //Write a new value through the first connection
        connection.writeValue(MIX1_VAL);

        //We should be able to read the change
        assertEquals(MIX1_VAL, connection.readValue());
        assertEquals(MIX1_VAL, connection2.readValue());

        //Write a new value through the second connection
        connection2.writeValue(MIX2_VAL);

        //Check that the connections have the logical OR of the input values
        assertEquals(ONES_VAL, connection.readValue());
        assertEquals(ONES_VAL, connection2.readValue());

        //Zero out the first connection
        connection.writeValue(ZERO_VAL);

        //Check that the connections have the only the second value
        assertEquals(MIX2_VAL, connection.readValue());
        assertEquals(MIX2_VAL, connection2.readValue());

        //Zero out the second connection
        connection2.writeValue(ZERO_VAL);

        //Check that the connections are back to zero
        assertEquals(ZERO_VAL, connection.readValue());
        assertEquals(ZERO_VAL, connection2.readValue());

    }

    @Test
    public void testSimpleBusWithChangeHandler() {
        //Create a new
        Bus bus = new Bus("TestBus", DATA_WIDTH);
        assertEquals("TestBus", bus.getName());
        assertEquals(DATA_WIDTH, bus.getWidth());

        //Create a new handler which we can inspect later
        CapturingValueChangeHandler handler = new CapturingValueChangeHandler();

        //Get a connection which reads from the bus with the change handler
        ReadableConnection readConnection = bus.createReadableConnection(handler);

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Add a connection which writes to the bus
        WritableConnection writeConnection = bus.createWritableConnection();
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Write a new value to the bus
        writeConnection.writeValue(ONES_VAL);

        //Check that our handler got the update
        assertEquals(ONES_VAL, handler.getLastValue());

        //Check that we can still read the update
        assertEquals(ONES_VAL, readConnection.readValue());

    }

    @Test
    public void testMultiBidirectionalBusWithChangeHandlers() {
        //Create a new
        Bus bus = new Bus("TestBus", DATA_WIDTH);
        assertEquals("TestBus", bus.getName());
        assertEquals(DATA_WIDTH, bus.getWidth());

        //Add 2 bidirectional connections with change handlers
        CapturingValueChangeHandler handler = new CapturingValueChangeHandler();
        BidirectionalConnection connection = bus.createBidirectionalConnection(handler);
        CapturingValueChangeHandler handler2 = new CapturingValueChangeHandler();
        BidirectionalConnection connection2 = bus.createBidirectionalConnection(handler2);

        //Read from the bus through the connections
        assertEquals(ZERO_VAL, connection.readValue());
        assertEquals(ZERO_VAL, connection2.readValue());

        //Write a new value through the first connection
        connection.writeValue(MIX1_VAL);

        //The handlers should have the change.
        assertEquals(MIX1_VAL, handler.getLastValue());
        assertEquals(MIX1_VAL, handler2.getLastValue());

        //We should be able to read the change
        assertEquals(MIX1_VAL, connection.readValue());
        assertEquals(MIX1_VAL, connection2.readValue());

        //Write a new value through the second connection
        connection2.writeValue(MIX2_VAL);

        //Check that the connections have the logical OR of the input values
        assertEquals(ONES_VAL, connection.readValue());
        assertEquals(ONES_VAL, connection2.readValue());

        //The handlers should have the logical OR as well
        assertEquals(ONES_VAL, handler.getLastValue());
        assertEquals(ONES_VAL, handler2.getLastValue());

        //Zero out the first connection
        connection.writeValue(ZERO_VAL);

        //Check that the connections have the only the second value
        assertEquals(MIX2_VAL, connection.readValue());
        assertEquals(MIX2_VAL, connection2.readValue());

        //The handlers should have the only the second value too
        assertEquals(MIX2_VAL, handler.getLastValue());
        assertEquals(MIX2_VAL, handler2.getLastValue());

        //Zero out the second connection
        connection2.writeValue(ZERO_VAL);

        //Check that the connections are back to zero
        assertEquals(ZERO_VAL, connection.readValue());
        assertEquals(ZERO_VAL, connection2.readValue());

        //The handlers should be back to zero
        assertEquals(ZERO_VAL, handler.getLastValue());
        assertEquals(ZERO_VAL, handler2.getLastValue());

    }

}
