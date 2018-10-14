package org.karnes.homebrew.emulator.component;

import org.junit.jupiter.api.Test;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.Bus;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.emulator.component.bus.connection.SoftwareConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        //Before the update, the read connection should be unchanged
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Update the Bus
        boolean updated = bus.update();

        //The bus should have updated
        assertTrue(updated);

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

        //Before the update, the read connection should be unchanged
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Update the Bus
        boolean updated = bus.update();

        //The bus should have updated
        assertTrue(updated);

        //Check that the update propagated
        assertEquals(ONES_VAL, readConnection.readValue());

        //Change the write connection's value
        writeConnection.writeValue(MIX1_VAL);

        //Before the update, the read connection should be unchanged
        assertEquals(ONES_VAL, readConnection.readValue());

        //Update the Bus
        updated = bus.update();

        //The bus should have updated
        assertTrue(updated);

        //Check that the update propagated
        assertEquals(MIX1_VAL, readConnection.readValue());

        //Change the write connection's value
        writeConnection.writeValue(MIX2_VAL);

        //Before the update, the read connection should be unchanged
        assertEquals(MIX1_VAL, readConnection.readValue());

        //Update the Bus
        updated = bus.update();

        //The bus should have updated
        assertTrue(updated);

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

        //Add anothter connection which reads from the BidirectionalBus.
        ReadableConnection readConnection2 = bus.createReadableConnection();

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection2.readValue());

        //Add a connection which writes to the bus
        WritableConnection writeConnection = bus.createWritableConnection();
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Write a new value to the bus
        writeConnection.writeValue(ONES_VAL);

        //Before the update, the read connection should be unchanged
        assertEquals(ZERO_VAL, readConnection.readValue());
        assertEquals(ZERO_VAL, readConnection2.readValue());

        //Update the Bus
        boolean updated = bus.update();

        //The bus should have updated
        assertTrue(updated);

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

        //Before the update, the read connection should be unchanged
        assertEquals(ZERO_VAL, readConnection.readValue());

        //Update the Bus
        boolean updated = bus.update();

        //The bus should have updated
        assertTrue(updated);

        //Check that the update propagated
        assertEquals(MIX1_VAL, readConnection.readValue());

        //Write another new value to the BidirectionalBus
        writeConnection2.writeValue(MIX2_VAL);

        //Before the update, the read connection should be unchanged
        assertEquals(MIX1_VAL, readConnection.readValue());

        //Update the Bus
        updated = bus.update();

        //The bus should have updated
        assertTrue(updated);

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
        SoftwareConnection connection = bus.createConnection();

        //Read from the bus through the connection
        assertEquals(ZERO_VAL, connection.readValue());

        //Write a new value through the connection
        connection.writeValue(ONES_VAL);

        //Even before the update, the read connection should be changed
        assertEquals(ONES_VAL, connection.readValue());

        //Update the Bus
        boolean updated = bus.update();

        //The bus should have updated
        assertTrue(updated);

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
        SoftwareConnection connection = bus.createConnection();
        SoftwareConnection connection2 = bus.createConnection();

        //Read from the bus through the connections
        assertEquals(ZERO_VAL, connection.readValue());
        assertEquals(ZERO_VAL, connection2.readValue());

        //Write a new value through the first connection
        connection.writeValue(MIX1_VAL);

        //Even before the update, the read connection should be changed
        assertEquals(MIX1_VAL, connection.readValue());

        //Write a new value through the second connection
        connection2.writeValue(MIX2_VAL);

        //Even before the update, the read connection should be changed
        assertEquals(MIX2_VAL, connection2.readValue());

        //Update the Bus
        boolean updated = bus.update();

        //The bus should have updated
        assertTrue(updated);

        //Check that the connections have the logical OR of the input values
        assertEquals(ONES_VAL, connection.readValue());
        assertEquals(ONES_VAL, connection2.readValue());

    }

}
