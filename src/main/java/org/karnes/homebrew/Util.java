package org.karnes.homebrew;


public class Util {
    public static char twoBytesToChar(byte high, byte low) {
        return (char) ((high << 8) | (low & 0xff));
    }

    public static byte getLowByteFromChar(char value) {
        return (byte) value;
    }

    public static byte getHighByteFromChar(char value) {
        return (byte) (value >>> 8);
    }

    public static char literalToChar(String literalText) {
        if (literalText.trim().startsWith("0x")) {
            //Get HEX value
            return (char) Integer.parseInt(literalText.substring(2), 16);
        } else {
            //Get DECIMAL value
            return (char) Integer.parseInt(literalText, 10);
        }
    }

}
