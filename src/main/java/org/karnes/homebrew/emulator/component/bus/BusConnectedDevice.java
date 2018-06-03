package org.karnes.homebrew.emulator.component.bus;

/**
 * A device in the computer which is connected to one or more Buses.
 */
public interface BusConnectedDevice {
    /**
     * Called to handle the change in one of the buses' value. If there are multiple Buses connected to the device,
     * The BusConnectedDevice should check which Bus the event originated from.
     *
     * @param busValueChangedEvent
     */
    void handleBusValueChangedEvent(BusValueChangedEvent busValueChangedEvent);
}
