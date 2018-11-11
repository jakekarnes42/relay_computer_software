package org.karnes.homebrew.relay.common.emulator.event;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

public class CapturingValueChangeHandler implements ValueChangeHandler {
    FixedBitSet value = null;

    @Override
    public void handleChangedValue(FixedBitSet newValue) {
        value = newValue;
    }

    /**
     * Gets the last value seen by this handler, or null if this handler hasn't seen any values.
     *
     * @return the last value or null
     */
    public FixedBitSet getLastValue() {
        return value;
    }
}
