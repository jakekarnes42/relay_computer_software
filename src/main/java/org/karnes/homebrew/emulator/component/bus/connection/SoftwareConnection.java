package org.karnes.homebrew.emulator.component.bus.connection;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.SoftwareComponent;
import org.karnes.homebrew.emulator.event.NoopValueChangeHandler;
import org.karnes.homebrew.emulator.event.ValueChangeHandler;


public class SoftwareConnection extends SoftwareComponent implements ReadableConnection, WritableConnection {

    private final ValueChangeHandler handler;

    private FixedBitSet currentValue;


    public SoftwareConnection(String name, int width) {
        this(name, width, new NoopValueChangeHandler());
    }

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
}
