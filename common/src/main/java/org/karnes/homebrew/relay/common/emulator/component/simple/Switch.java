package org.karnes.homebrew.relay.common.emulator.component.simple;


import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.*;

/**
 * Represents a set of physical switches which can be used to manually turn on a signal.
 */
public class Switch extends SoftwareComponent {

    private FixedBitSet currentValue;
    private WritableConnection connection;

    /**
     * Creates a new set of switches of the given size.
     *
     * @param name  The name of the Component
     * @param width The width of the Component's primary data
     */
    public Switch(String name, int width) {
        super(name, width);
        currentValue = new FixedBitSet(width);
        connection = new Disconnection(width);
    }

    /**
     * Updates all switches at once to the new state.
     *
     * @param newState The new state of each switch
     */
    public void setState(FixedBitSet newState) {
        //Check the size
        if (newState.size() != getWidth()) {
            throw new IllegalArgumentException("Cannot except new state: " + newState +
                    ". Expected size: " + getWidth() + " found size: " + newState.size());
        }

        update(newState);
    }

    /**
     * Turns on the switch at the given position.
     *
     * @param position which switch to turn on.
     * @see FixedBitSet#set(int, boolean)
     */
    public void turnOn(int position) {
        FixedBitSet newValue = currentValue.set(position, true);
        update(newValue);
    }

    /**
     * Turns off the switch at the given position.
     *
     * @param position which switch to turn off.
     * @see FixedBitSet#set(int, boolean)
     */
    public void turnOff(int position) {
        FixedBitSet newValue = currentValue.set(position, false);
        update(newValue);
    }

    /**
     * Toggles the switch at the given position.
     *
     * @param position which switch to turn off.
     * @see FixedBitSet#set(int, boolean)
     */
    public void toggle(int position) {
        boolean state = currentValue.get(position);
        FixedBitSet newValue = currentValue.set(position, !state);
        update(newValue);
    }

    private void update(FixedBitSet newState) {
        this.currentValue = newState;
        connection.writeValue(currentValue);
    }

    /**
     * Gets the output connection from the switches
     *
     * @return The connection to the switch(es)
     */
    public ReadableConnection getConnection() {
        //Check if we're already connected
        if (this.connection.isConnected()) {
            throw new ExistingConnectionException("The Output Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " Output Connection", getWidth());
            this.connection = connection;
            return connection;
        }
    }


}
