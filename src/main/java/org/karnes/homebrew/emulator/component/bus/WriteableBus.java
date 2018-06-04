package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.emulator.component.bus.connection.WriteableBusConnection;

/**
 * A {@link Bus} which can have new values written to it through software
 */
public interface WriteableBus extends Bus {

    /**
     * When called, the Bus will ensure all values written to it from its {@link WriteableBusConnection}
     * are properly reflected in the Bus' value
     */
    void refreshValues();

    /**
     * Gets a connection to this Bus which can be used to write new values to the Bus.
     *
     * @return a connection to write to the Buss
     */
    WriteableBusConnection getWriteConnection();
}
