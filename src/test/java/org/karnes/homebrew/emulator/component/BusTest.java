package org.karnes.homebrew.emulator.component;

import org.junit.jupiter.api.Test;
import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.Bus;
import org.karnes.homebrew.emulator.component.bus.BusConnectedDevice;
import org.karnes.homebrew.emulator.component.bus.BusConnection;
import org.karnes.homebrew.emulator.component.bus.BusValueChangedEvent;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusTest {

    @Test
    public void testSimpleBus() {
        //Create a new bus
        Bus bus = new Bus("TestBus", 4);
        assertEquals("TestBus", bus.getName());
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(new ArbitraryBitSet(4), bus.getBusValue());

        //Add a connection to the bus
        BusConnection connection1 = new BusConnection(bus);
        assertEquals(1, bus.getConnections().size());
        assertEquals(new ArbitraryBitSet(4), bus.getBusValue());

        //Update the connection. Turn on one of the wires
        connection1.setToBusOutput(new ArbitraryBitSet("0001"));
        assertEquals(new ArbitraryBitSet("0001"), bus.getBusValue());
    }

    @Test
    public void testConnectedBus() {
        //Create a new bus
        Bus bus = new Bus("TestBus", 4);
        assertEquals(4, bus.getWidth());
        assertEquals(0, bus.getConnections().size());
        assertEquals(new ArbitraryBitSet(4), bus.getBusValue());

        //Add a connection to the bus
        BusConnection connection1 = new BusConnection(bus);
        assertEquals(new ArbitraryBitSet(4), connection1.getToBusOutput());
        assertEquals(1, bus.getConnections().size());
        assertEquals(new ArbitraryBitSet(4), bus.getBusValue());

        //Update the connection. Turn on one of the wires
        connection1.setToBusOutput(new ArbitraryBitSet("0001"));
        assertEquals(new ArbitraryBitSet("0001"), bus.getBusValue());
        assertEquals(new ArbitraryBitSet("0001"), connection1.getBusValue());

        //Add a connection to the bus
        BusConnection connection2 = new BusConnection(bus);
        assertEquals(2, bus.getConnections().size());
        assertEquals(new ArbitraryBitSet("0001"), bus.getBusValue());
        assertEquals(new ArbitraryBitSet("0001"), connection1.getBusValue());
        assertEquals(new ArbitraryBitSet("0001"), connection2.getBusValue());

        //Update the second connection
        connection2.setToBusOutput(new ArbitraryBitSet("1000"));
        assertEquals(new ArbitraryBitSet("1001"), bus.getBusValue());
        assertEquals(new ArbitraryBitSet("1001"), connection1.getBusValue());
        assertEquals(new ArbitraryBitSet("1001"), connection2.getBusValue());

        //"turn off" the first connection
        connection1.setToBusOutput(new ArbitraryBitSet("0000"));
        assertEquals(new ArbitraryBitSet("1000"), bus.getBusValue());
        assertEquals(new ArbitraryBitSet("1000"), connection1.getBusValue());
        assertEquals(new ArbitraryBitSet("1000"), connection2.getBusValue());
    }

    @Test
    public void testConnectedBusEvents() {
        //Create a new bus
        Bus bus = new Bus("TestBus", 4);

        //Add a connection to the bus, with tracking
        BusStateTracker tracker1 = new BusStateTracker(bus.getBusValue());
        BusConnection connection1 = new BusConnection(bus, tracker1);
        assertEquals(1, tracker1.values.size());

        //Update the connection. Turn on one of the wires
        connection1.setToBusOutput(new ArbitraryBitSet("0001"));
        assertEquals(2, tracker1.values.size());
        assertEquals(new ArbitraryBitSet("0001"), tracker1.values.get(1));

        //Add a connection to the bus
        BusStateTracker tracker2 = new BusStateTracker(bus.getBusValue());
        BusConnection connection2 = new BusConnection(bus, tracker2);
        assertEquals(2, tracker1.values.size());
        assertEquals(new ArbitraryBitSet("0001"), tracker1.values.get(1));

        //Update the second connection
        connection2.setToBusOutput(new ArbitraryBitSet("1000"));
        assertEquals(3, tracker1.values.size());
        assertEquals(new ArbitraryBitSet("1001"), tracker1.values.get(2));

        //"turn off" the first connection
        connection1.setToBusOutput(new ArbitraryBitSet("0000"));
        assertEquals(4, tracker1.values.size());
        assertEquals(new ArbitraryBitSet("1000"), tracker1.values.get(3));
    }
}

class BusStateTracker implements BusConnectedDevice {

    List<FixedBitSet> values = new ArrayList<>();


    public BusStateTracker(FixedBitSet initialValue) {
        values.add(initialValue);
    }

    @Override
    public void handleBusValueChangedEvent(BusValueChangedEvent busValueChangedEvent) {
        values.add(busValueChangedEvent.getUpdatedValue());
    }
}