package org.karnes.homebrew;


public class Util {


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
