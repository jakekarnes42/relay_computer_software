package org.karnes.homebrew.emulator.event;

import org.karnes.homebrew.bitset.FixedBitSet;

/**
 * A default implementation of a {@link ValueChangeHandler} which does nothing when the value changes.
 */
public class NoopValueChangeHandler implements ValueChangeHandler {
    @Override
    public void handleChangedValue(FixedBitSet newValue) {
        //Intentionally do nothing. This is a NO-OP handler.
    }
}
