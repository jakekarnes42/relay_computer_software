package org.karnes.homebrew.forth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.karnes.homebrew.run.Runner.runDOSeForth;
import static org.karnes.homebrew.run.Runner.runRelayeForth;

public class ForthWordsTest {

    @Test
    @DisplayName("Test the Hello World program in Forth")
    public void testForthHelloWord() {
        String input = ": HELLO CR .\" Hello, world!\" ;\r" +//Define the new HELLO word
                        "HELLO\r" + //Run it
                        "BYE\r"; //Exit

        String relayOutput = runRelayeForth(input);

        String originalOutput = runDOSeForth(input);


        assertEquals(originalOutput, relayOutput, "The simple math program should result in the same output");
    }


    @Test
    @DisplayName("Test simple math")
    public void testSimpleMath() {
        String input =
                        "2 2 +\r" +
                        "-2 -\r" +
                        "7 *\r" +
                        ".\r" +
                        "BYE\r";

        String relayOutput = runRelayeForth(input);

        String originalOutput = runDOSeForth(input);


        assertEquals(originalOutput, relayOutput, "The simple math program should result in the same output");
    }

    @Test
    @DisplayName("Test division")
    public void testDivision() {
        String input =
                "1 1 \\MOD \r" + "BYE\r";

        String relayOutput = runRelayeForth(input);

        String originalOutput = runDOSeForth(input);


        assertEquals(originalOutput, relayOutput, "The simple math program should result in the same output");
    }


}