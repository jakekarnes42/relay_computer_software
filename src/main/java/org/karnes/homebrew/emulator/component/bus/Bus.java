package org.karnes.homebrew.emulator.component.bus;

import org.karnes.homebrew.emulator.component.bus.connection.BusConnection;

import java.util.List;

/**
 * Represents a group of wires/signals used to transmit data within the computer.
 * This may be a virtual (software-only) bus for emulation, or a physical bus which is connected to hardware.
 */
public interface Bus {
    /**
     * The name of the Bus. This is arbitrary, and mostly used for troubleshooting.
     *
     * @return The Bus' name
     */
    String getName();

    /**
     * The number of wires/signals that this Bus supports.
     *
     * @return The width of the Bus
     */
    int getWidth();

    /**
     * All of the Bus' current connections
     *
     * @return The current connections to the Bus.
     */
    List<? extends BusConnection> getConnections();
}
