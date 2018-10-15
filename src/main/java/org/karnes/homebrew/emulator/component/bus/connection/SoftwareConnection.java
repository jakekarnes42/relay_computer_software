package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.Component;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.event.NoopValueChangeHandler;
import org.karnes.homebrew.emulator.event.ValueChangeHandler;

/**
 * A {@link BidirectionalConnection} between two virtual {@link Component}s
 */
public class SoftwareConnection extends SoftwareComponent implements BidirectionalConnection {

    private final ValueChangeHandler handler;

    private FixedBitSet currentValue;

    /**
     * Creates a SoftwareConnection with the given name and width, with a {@link NoopValueChangeHandler} to call when the value changes.
     *
     * @param name  The name of the connection.
     * @param width The width of the connection.
     */
    public SoftwareConnection(String name, int width) {
        this(name, width, new NoopValueChangeHandler());
    }

    /**
     * Creates a SoftwareConnection with the given name, width, and logic to execute when the value changes.
     *
     * @param name               The name of the connection.
     * @param width              The width of the connection.
     * @param valueChangeHandler The logic to execute when the value changes.
     */
    public SoftwareConnection(String name, int width, ValueChangeHandler valueChangeHandler) {
        super(name, width);
        handler = valueChangeHandler;
        //Initialize the current value to zero
        currentValue = new FixedBitSet(getWidth());
    }


    @Override
    public FixedBitSet readValue() {
        return currentValue;
    }

    @Override
    public void writeValue(FixedBitSet value) {
        this.currentValue = value;
        handler.handleChangedValue(currentValue);
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
