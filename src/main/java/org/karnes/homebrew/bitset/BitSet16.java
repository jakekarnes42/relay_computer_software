package org.karnes.homebrew.bitset;

public class BitSet16 extends FixedBitSet {

    public BitSet16() {
        this(16);
    }

    public BitSet16(int size) {
        super(size);

        if (bits.length != 16) {
            throw new IllegalArgumentException("A BitSet16 must be exactly 16 bits");
        }

    }

    public BitSet16(boolean[] value) {
        super(value);

        if (bits.length != 16) {
            throw new IllegalArgumentException("A BitSet16 must be exactly 16 bits");
        }
    }

    public BitSet16(String bitString) {
        super(bitString);

        if (bits.length != 16) {
            throw new IllegalArgumentException("A BitSet16 must be exactly 16 bits");
        }
    }

    public static BitSet16 fromShort(short value) {
        final boolean[] bits = new boolean[16];
        for (int i = 0; i < 16; i++) {
            bits[16 - 1 - i] = (1 << i & value) != 0;
        }
        return new BitSet16(bits);
    }

    public short toShort() {
        if (bits.length != 16) {
            throw new IllegalArgumentException("A BitSet16 must be exactly 16 bits");
        }

        //Convert to an int in the interm
        int n = 0;
        for (int i = 0; i < bits.length; ++i) {
            n = (n << 1) + (bits[i] ? 1 : 0);
        }
        return (short) n;
    }

    public static BitSet16 fromChar(char value) {
        return fromShort((short) value);
    }


    public char toChar() {
        return (char) toShort();
    }
}
