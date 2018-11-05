package org.karnes.homebrew.relay.computer;


import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

public class Util {


    public static FixedBitSet literalToFixedBitSet(String literalText) {
        if (literalText.trim().startsWith("0x")) {
            //Get HEX value
            char c = (char) Integer.parseInt(literalText.substring(2), 16);
            return FixedBitSet.fromChar(c);
        } else {
            //Get DECIMAL value
            char c = (char) Integer.parseInt(literalText, 10);
            return FixedBitSet.fromChar(c);
        }
    }

}
