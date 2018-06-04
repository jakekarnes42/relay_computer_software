package org.karnes.homebrew.emulator.component;

import org.junit.jupiter.api.Test;
import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.BidirectionalBus;
import org.karnes.homebrew.emulator.component.bus.VirtualBus;
import org.karnes.homebrew.emulator.component.bus.connection.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusTest {
    private static final int DATA_WIDTH = 4;
    private static final FixedBitSet ZERO_VAL = new ArbitraryBitSet(DATA_WIDTH);
    private static final FixedBitSet ONES_VAL = ArbitraryBitSet.allOnes(4);
    private static final FixedBitSet MIX1_VAL = new ArbitraryBitSet("0101");
    private static final FixedBitSet MIX2_VAL = new ArbitraryBitSet("1010");

    @Test
    public void testSimpleBus() {
        //Create a new software-only bus
        BidirectionalBus bus = new VirtualBus("TestBus", 4);
        assertEquals("TestBus", bus.getName());
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Add a connection which reads from the BidirectionalBus.
        ReadableBusConnection readConnection = bus.getReadConnection();
        assertEquals(1, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection.readBusValue());

        //Add a connection which writes to the bus
        WriteableBusConnection writeConnection = bus.getWriteConnection();
        assertEquals(2, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Write a new value to the BidirectionalBus
        writeConnection.writeValueToBus(ONES_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, bus.getValue());
        assertEquals(ONES_VAL, readConnection.readBusValue());

    }

    @Test
    public void testUpdateWriteBus() {
        //Create a new software-only bus
        BidirectionalBus bus = new VirtualBus("TestBus", 4);
        assertEquals("TestBus", bus.getName());
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Add a connection which reads from the BidirectionalBus.
        ReadableBusConnection readConnection = bus.getReadConnection();
        assertEquals(1, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection.readBusValue());

        //Add a connection which writes to the bus
        WriteableBusConnection writeConnection = bus.getWriteConnection();
        assertEquals(2, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Write a new value to the BidirectionalBus
        writeConnection.writeValueToBus(ONES_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, bus.getValue());
        assertEquals(ONES_VAL, readConnection.readBusValue());

        //Change the write connection's value
        writeConnection.writeValueToBus(MIX1_VAL);

        //Check that the update propagated
        assertEquals(MIX1_VAL, bus.getValue());
        assertEquals(MIX1_VAL, readConnection.readBusValue());

        //Change the write connection's value
        writeConnection.writeValueToBus(MIX2_VAL);

        //Check that the update propagated
        assertEquals(MIX2_VAL, bus.getValue());
        assertEquals(MIX2_VAL, readConnection.readBusValue());

    }


    @Test
    public void testMultiReadBus() {
        //Create a new software-only bus
        BidirectionalBus bus = new VirtualBus("TestBus", 4);
        assertEquals("TestBus", bus.getName());
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Add a connection which reads from the BidirectionalBus.
        ReadableBusConnection readConnection = bus.getReadConnection();
        assertEquals(1, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection.readBusValue());

        //Add anothter connection which reads from the BidirectionalBus.
        ReadableBusConnection readConnection2 = bus.getReadConnection();
        assertEquals(2, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection2.readBusValue());

        //Add a connection which writes to the bus
        WriteableBusConnection writeConnection = bus.getWriteConnection();
        assertEquals(3, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Write a new value to the BidirectionalBus
        writeConnection.writeValueToBus(ONES_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, bus.getValue());
        assertEquals(ONES_VAL, readConnection.readBusValue());
        assertEquals(ONES_VAL, readConnection2.readBusValue());

    }

    @Test
    public void testMultiWriteBus() {
        //Create a new software-only bus
        BidirectionalBus bus = new VirtualBus("TestBus", 4);
        assertEquals("TestBus", bus.getName());
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Add a connection which reads from the BidirectionalBus.
        ReadableBusConnection readConnection = bus.getReadConnection();
        assertEquals(1, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Read from the bus through the read connection
        assertEquals(ZERO_VAL, readConnection.readBusValue());

        //Add a connection which writes to the bus
        WriteableBusConnection writeConnection = bus.getWriteConnection();
        assertEquals(2, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Add another connection which writes to the bus
        WriteableBusConnection writeConnection2 = bus.getWriteConnection();
        assertEquals(3, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Write a new value to the BidirectionalBus
        writeConnection.writeValueToBus(MIX1_VAL);

        //Check that the update propagated
        assertEquals(MIX1_VAL, bus.getValue());
        assertEquals(MIX1_VAL, readConnection.readBusValue());

        //Write another new value to the BidirectionalBus
        writeConnection2.writeValueToBus(MIX2_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, bus.getValue());
        assertEquals(ONES_VAL, readConnection.readBusValue());

        //Turn off the first connection
        writeConnection.writeValueToBus(ZERO_VAL);

        //Check that the update propagated
        assertEquals(MIX2_VAL, bus.getValue());
        assertEquals(MIX2_VAL, readConnection.readBusValue());
    }

    @Test
    public void testInterruptFromBus() {
        //Create a new software-only bus
        BidirectionalBus bus = new VirtualBus("TestBus", 4);
        assertEquals("TestBus", bus.getName());
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Add a connection which interrupts from the BidirectionalBus.
        final List<FixedBitSet> allValues = new ArrayList<>();
        InterruptFromBusConnection interruptConnection = bus.getInterruptConnection(e -> {
            allValues.add(e.getUpdatedValue());
        });
        assertEquals(1, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());
        assertEquals(ZERO_VAL, interruptConnection.readBusValue());


        //Add a connection which writes to the bus
        WriteableBusConnection writeConnection = bus.getWriteConnection();
        assertEquals(2, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Write a new value to the BidirectionalBus
        writeConnection.writeValueToBus(ONES_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, bus.getValue());
        assertEquals(ONES_VAL, interruptConnection.readBusValue());
        assertEquals(1, allValues.size());
        assertEquals(ONES_VAL, allValues.get(0));

        //Change the write connection's value
        writeConnection.writeValueToBus(MIX1_VAL);

        //Check that the update propagated
        assertEquals(MIX1_VAL, bus.getValue());
        assertEquals(MIX1_VAL, interruptConnection.readBusValue());
        assertEquals(2, allValues.size());
        assertEquals(MIX1_VAL, allValues.get(1));


        //Change the write connection's value
        writeConnection.writeValueToBus(MIX2_VAL);

        //Check that the update propagated
        assertEquals(MIX2_VAL, bus.getValue());
        assertEquals(MIX2_VAL, interruptConnection.readBusValue());
        assertEquals(3, allValues.size());
        assertEquals(MIX2_VAL, allValues.get(2));

    }

    @Test
    public void testSimpleBidirectionalBus() {
        //Create a new software-only bus
        BidirectionalBus bus = new VirtualBus("TestBus", 4);
        assertEquals("TestBus", bus.getName());
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Add a bidirectional connection which reads from the BidirectionalBus.
        BidirectionalBusConnection connection = bus.getBidirectionalConnection();
        assertEquals(1, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Read from the bus through the connection
        assertEquals(ZERO_VAL, connection.readBusValue());


        //Write a new value to the BidirectionalBus
        connection.writeValueToBus(ONES_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, bus.getValue());
        assertEquals(ONES_VAL, connection.readBusValue());
    }

    @Test
    public void testMultiBidirectionalBus() {
        //Create a new software-only bus
        BidirectionalBus bus = new VirtualBus("TestBus", 4);
        assertEquals("TestBus", bus.getName());
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Add a bidirectional connection which reads from the BidirectionalBus.
        BidirectionalBusConnection connection = bus.getBidirectionalConnection();
        assertEquals(1, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Read from the bus through the connection
        assertEquals(ZERO_VAL, connection.readBusValue());

        //Write a new value to the BidirectionalBus
        connection.writeValueToBus(MIX1_VAL);

        //Check that the update propagated
        assertEquals(MIX1_VAL, bus.getValue());
        assertEquals(MIX1_VAL, connection.readBusValue());

        //Add another bidirectional connection which reads from the BidirectionalBus.
        BidirectionalBusConnection connection2 = bus.getBidirectionalConnection();
        assertEquals(2, bus.getConnections().size());
        assertEquals(MIX1_VAL, bus.getValue());

        //Read from the bus through the connection
        assertEquals(MIX1_VAL, connection2.readBusValue());

        //Write a new value to the BidirectionalBus
        connection2.writeValueToBus(MIX2_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, bus.getValue());
        assertEquals(ONES_VAL, connection.readBusValue());
        assertEquals(ONES_VAL, connection2.readBusValue());

        //Turn off the output from the first connection
        connection.writeValueToBus(ZERO_VAL);

        //Check that the update propagated
        assertEquals(MIX2_VAL, bus.getValue());
        assertEquals(MIX2_VAL, connection.readBusValue());
        assertEquals(MIX2_VAL, connection2.readBusValue());

    }


    @Test
    public void testInterruptableBidirectionalBus() {
        //Create a new software-only bus
        BidirectionalBus bus = new VirtualBus("TestBus", 4);
        assertEquals("TestBus", bus.getName());
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        final List<FixedBitSet> allValues = new ArrayList<>();

        //Add a bidirectional connection which reads from the BidirectionalBus.
        BidirectionalInterruptableBusConnection connection = bus.getBidirectionalInterruptableBusConnection(e -> {
            allValues.add(e.getUpdatedValue());
        });
        assertEquals(1, bus.getConnections().size());
        assertEquals(ZERO_VAL, bus.getValue());

        //Read from the bus through the connection
        assertEquals(ZERO_VAL, connection.readBusValue());

        //Write a new value to the BidirectionalBus
        connection.writeValueToBus(MIX1_VAL);

        //Check that the update propagated
        assertEquals(MIX1_VAL, bus.getValue());
        assertEquals(MIX1_VAL, connection.readBusValue());
        assertEquals(1, allValues.size());
        assertEquals(MIX1_VAL, allValues.get(0));


        //Add another bidirectional connection which reads from the BidirectionalBus.
        BidirectionalBusConnection connection2 = bus.getBidirectionalConnection();
        assertEquals(2, bus.getConnections().size());
        assertEquals(MIX1_VAL, bus.getValue());

        //Read from the bus through the connection
        assertEquals(MIX1_VAL, connection2.readBusValue());

        //Write a new value to the BidirectionalBus
        connection2.writeValueToBus(MIX2_VAL);

        //Check that the update propagated
        assertEquals(ONES_VAL, bus.getValue());
        assertEquals(ONES_VAL, connection.readBusValue());
        assertEquals(ONES_VAL, connection2.readBusValue());
        assertEquals(2, allValues.size());
        assertEquals(ONES_VAL, allValues.get(1));

        //Turn off the output from the first connection
        connection.writeValueToBus(ZERO_VAL);

        //Check that the update propagated
        assertEquals(MIX2_VAL, bus.getValue());
        assertEquals(MIX2_VAL, connection.readBusValue());
        assertEquals(MIX2_VAL, connection2.readBusValue());
        assertEquals(3, allValues.size());
        assertEquals(MIX2_VAL, allValues.get(2));
    }


}
