package org.karnes.homebrew;


import org.karnes.homebrew.bitset.BitSet16;

public class Util {


    public static BitSet16 literalToBitSet16(String literalText) {
        if (literalText.trim().startsWith("0x")) {
            //Get HEX value
            char c = (char) Integer.parseInt(literalText.substring(2), 16);
            return BitSet16.fromChar(c);
        } else {
            //Get DECIMAL value
            char c = (char) Integer.parseInt(literalText, 10);
            return BitSet16.fromChar(c);
        }
    }

}
