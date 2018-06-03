package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * Represents the changed value of a particular Bus
 */
public class BusValueChangedEvent {
    private final String busName;
    private final FixedBitSet updatedValue;

    public BusValueChangedEvent(String busName, FixedBitSet updatedValue) {
        this.busName = busName;
        this.updatedValue = updatedValue;
    }

    public String getBusName() {
        return busName;
    }

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
