package org.karnes.homebrew.forth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.karnes.homebrew.run.Runner.runDOSeForth;
import static org.karnes.homebrew.run.Runner.runRelayeForth;

public class ForthWordsTest {

    @Test
    @DisplayName("Test simple math")
    public void testSimpleMath() {
        String input = "2 2 +\r-2 -\r7 *\r.\rBYE\r";

        String relayOutput = runRelayeForth(input);

        String originalOutput = runDOSeForth(input);


        assertEquals(originalOutput, relayOutput, "The simple math program should result in the same output");
    }


}