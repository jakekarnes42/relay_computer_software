package org.karnes.homebrew.emulator.component.bus.connection;

/**
 * Marker interface which indicates that the connection can handle interrupts.
 */
public interface InterruptableBusConnection extends ReadableBusConnection, BusValueChangeHandler {


}
