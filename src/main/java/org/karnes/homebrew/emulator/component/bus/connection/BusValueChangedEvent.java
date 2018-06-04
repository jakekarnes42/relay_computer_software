package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.ReadableBus;

/**
 * Represents the changed value of a particular {@link ReadableBus}
 */
public class BusValueChangedEvent {
    private final String busName;
    private final FixedBitSet updatedValue;

    public BusValueChangedEvent(String busName, FixedBitSet updatedValue) {
        this.busName = busName;
        this.updatedValue = updatedValue;
    }

    /**
     * The name of the Bus which originated this event.
     *
     * @return The updated Bus' name
     */
    public String getBusName() {
        return busName;
    }

    /**
     * The new value of the Bus
     *
     * @return The updated value
     */
    public FixedBitSet getUpdatedValue() {
        return updatedValue;
    }

    @Override
    public String toString() {
        return "BusValueChangedEvent{" +
                "busName='" + busName + '\'' +
                ", updatedValue=" + updatedValue +
                '}';
    }
}
