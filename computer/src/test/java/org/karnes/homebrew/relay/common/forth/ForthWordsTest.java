package org.karnes.homebrew.relay.common.forth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.karnes.homebrew.relay.computer.run.Runner.runDOSeForth;
import static org.karnes.homebrew.relay.computer.run.Runner.runRelayeForth;

/**
 * This class of tests ensures that the output between the relay computer's eForth implementation matches the original
 * eForth's output. As the relay computer's Forth starts to diverge, these tests may become obsolete. Overall, this
 * has been really helpful in finding bugs in the relay eForth implementation.
 * </br>
 * <p>
 * Most of these tests come directly from C.H. Ting's example programs.
 */
public class ForthWordsTest {

    @Test
    @DisplayName("Test the Hello World program in Forth")
    public void testForthHelloWord() {
        String input = ": HELLO CR .\" Hello, world!\" ;\r" +//Define the new HELLO word
                "HELLO\r" + //Run it
                "BYE\r"; //Exit

        String relayOutput = runRelayeForth(input);

        String originalOutput = runDOSeForth(input);


        assertEquals(originalOutput, relayOutput, "The simple hello world program should result in the same output");
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


        assertEquals(originalOutput, relayOutput, "The simple division program should result in the same output");
    }

    @Test
    @DisplayName("Test definitions")
    public void testDefinitions() {
        String input =
                ": question\r" +
                        " CR CR .\" Any more problems you want to solve?\"\r" +
                        " CR .\" What kind ( sex, job, money, health ) ?\"\r" +
                        " CR\r" +
                        " ;\r" +
                        ": help CR\r" +
                        " CR .\" Hello! My name is Creating Computer.\"\r" +
                        " CR .\" Hi there!\"\r" +
                        " CR .\" Are you enjoying yourself here?\"\r" +
                        " KEY 32 OR 89 =\r" +
                        " CR\r" +
                        " IF CR .\" I am glad to hear that.\"\r" +
                        " ELSE CR .\" I am sorry about that.\"\r" +
                        " CR .\" maybe we can brighten your visit a bit.\"\r" +
                        " THEN\r" +
                        " CR .\" Say!\"\r" +
                        " CR .\" I can solved all kinds of problems except those dealing\"\r" +
                        " CR .\" with Greece. \"\r" +
                        " question\r" +
                        " ;\r" +
                        ": sex CR CR .\" Is your problem TOO MUCH or TOO LITTLE?\"\r" +
                        " CR\r" +
                        " ;\r" +
                        ": too ; ( noop for syntax smoothness )\r" +
                        ": much CR CR .\" You call that a problem?!! I SHOULD have that problem.\"\r" +
                        " CR .\" If it reall y bothers you, take a cold shower.\"\r" +
                        " question\r" +
                        " ;\r" +
                        ": little\r" +
                        " CR CR .\" Why are you here!\"\r" +
                        " CR .\" You should be in Tokyo or New York of Amsterdam or\"\r" +
                        " CR .\" some place with some action.\"\r" +
                        " question\r" +
                        " ;\r" +
                        ": health\r" +
                        " CR CR .\" My advise to you is:\"\r" +
                        " CR .\" 1. Take two tablets of aspirin.\"\r" +
                        " CR .\" 2. Drink plenty of fluids.\"\r" +
                        " CR .\" 3. Go to bed (along) .\"\r" +
                        " question\r" +
                        " ;\r" +
                        ": job CR CR .\" I can sympathize with you.\"\r" +
                        " CR .\" I have to work very long every day with no pay.\"\r" +
                        " CR .\" My advise to you, is to open a rental computer store.\"\r" +
                        " question\r" +
                        " ;\r" +
                        ": money\r" +
                        " CR CR .\" Sorry! I am broke too.\"\r" +
                        " CR .\" Why don't you sell encyclopedias or marry\"\r" +
                        " CR .\" someone rich or stop eating, so you won't \"\r" +
                        " CR .\" need so much money?\"\r" +
                        " question\r" +
                        " ;\r" +
                        ": HELP help ;\r" +
                        ": H help ;\r" +
                        ": h help ;\r" +
                        "BYE\r";

        String relayOutput = runRelayeForth(input);

        String originalOutput = runDOSeForth(input);


        assertEquals(originalOutput, relayOutput, "The program declaring many words should result in the same output");
    }


}