package org.karnes.homebrew.emulator.event;

import org.karnes.homebrew.bitset.FixedBitSet;

public class NoopValueChangeHandler implements ValueChangeHandler {
    @Override
    public void handleChangedValue(FixedBitSet newValue) {
        //Intentionally do nothing. This is a NO-OP handler.
    }
}
