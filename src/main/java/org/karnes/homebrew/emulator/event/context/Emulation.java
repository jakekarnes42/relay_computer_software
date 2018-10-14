package org.karnes.homebrew.emulator.event.context;


import org.karnes.homebrew.emulator.component.bus.Bus;

import java.util.ArrayList;
import java.util.List;

public class Emulation {

    private List<Bus> buses = new ArrayList<>();

    /**
     * Gets all the current buses in the emulation
     *
     * @return the currnet buses
     */
    public List<Bus> getBuses() {
        return buses;
    }

    /**
     * Adds a new Bus to Emulation
     *
     * @param name
     * @param width
     * @return the newly created bus
     */
    public Bus createNewBus(String name, int width) {
        Bus bus = new Bus(name, width);
        buses.add(bus);
        return bus;
    }

    /**
     * All buses execute one set of updates (if necessary).
     *
     * @return true if any of the buses updated, false if none of the buses updated
     */
    public boolean step() {
        boolean updated = false;
        for (Bus bus : buses) {
            boolean busUpdated = bus.update();
            if (busUpdated) {
                updated = true;
            }
        }
        return updated;
    }

    /**
     * Executes all bus updates until the system reaches a stable state where there are no more propagating updates.
     */
    public void tick() {
       while (step()){}
    }
}
