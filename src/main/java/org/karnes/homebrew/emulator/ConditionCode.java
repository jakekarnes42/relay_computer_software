package org.karnes.homebrew.emulator;

import org.karnes.homebrew.bitset.BitSet4;
import org.karnes.homebrew.bitset.FixedBitSet;

import java.util.Objects;

public class ConditionCode {
    public final static int ZERO_POSITION = 0;
    public final static int SIGN_POSITION = 1;
    public final static int OVERFLOW_POSITION = 2;
    public final static int CARRY_POSITION = 3;
    public final static int WIDTH = 4;

    private final boolean carry;
    private final boolean overflow;
    private final boolean sign;
    private final boolean zero;

    public ConditionCode(FixedBitSet bitSet) {
        this(bitSet.get(CARRY_POSITION), bitSet.get(OVERFLOW_POSITION), bitSet.get(SIGN_POSITION), bitSet.get(ZERO_POSITION));
        if (bitSet.size() != WIDTH) {
            throw new IllegalArgumentException("The input bitset is not the correct size for ConditionCode. Input: " + bitSet);
        }
    }

    public ConditionCode(boolean carry, boolean overflow, boolean sign, boolean zero) {
        //Quick logical check
        if (sign && zero) {
            throw new IllegalStateException("Sign and Zero are both true. " +
                    "The output value cannot be zero while having its MSB set to true");
        }

        this.carry = carry;
        this.overflow = overflow;
        this.sign = sign;
        this.zero = zero;
    }

    public boolean isCarry() {
        return carry;
    }

    public boolean isOverflow() {
        return overflow;
    }

    public boolean isSign() {
        return sign;
    }

    public boolean isZero() {
        return zero;
    }

    public BitSet4 toBitSet() {
        return new BitSet4(new boolean[]{carry, overflow, sign, zero});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConditionCode)) return false;
        ConditionCode that = (ConditionCode) o;
        return carry == that.carry &&
                overflow == that.overflow &&
                sign == that.sign &&
                zero == that.zero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carry, overflow, sign, zero);
    }

    @Override
    public String toString() {
        return "ConditionCode{" +
                "carry=" + carry +
                ", overflow=" + overflow +
                ", sign=" + sign +
                ", zero=" + zero +
                '}';
    }
}
