package org.karnes.homebrew.relay.hardware_tests.simple;


import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.*;

/**
 * Represents a set of physical LEDs which can be used to monitor signals
 */
public class LED extends SoftwareComponent {

    private FixedBitSet currentValue;
    private ReadableConnection connection;

    /**
     * Creates a new set of LEDs of the given size.
     *
     * @param name  The name of the Component
     * @param width The width of the Component's primary data
     */
    public LED(String name, int width) {
        super(name, width);
        currentValue = new FixedBitSet(width);
        connection = new Disconnection(width);
    }

    /**
     * Gets the most recent state of the LEDs. This doesn't refresh.
     *
     * @return The most recent state of the LEDs.
     */
    public FixedBitSet getState() {
        return currentValue;
    }

    /**
     * Gets the state of the LED at the given position.
     *
     * @param position which LED to check.
     * @return The state of the LED.
     * @see FixedBitSet#get(int)
     */
    public boolean isOn(int position) {
        return currentValue.get(position);
    }

    private void update(FixedBitSet newState) {
        this.currentValue = connection.readValue();
    }

    /**
     * Gets the input connection to the LEDs
     *
     * @return The connection to the LEDs
     */
    public WritableConnection getConnection() {
        //Check if we're already connected
        if (this.connection.isConnected()) {
            throw new ExistingConnectionException("The input Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " input Connection", getWidth(), this::update);
            this.connection = connection;
            return connection;
        }
    }


}
