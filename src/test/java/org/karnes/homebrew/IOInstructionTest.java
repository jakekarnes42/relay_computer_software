package org.karnes.homebrew;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.JavaSimulatedIODevice;
import org.karnes.homebrew.emulator.RelayComputer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class IOInstructionTest {
    private final static String ENCODING = StandardCharsets.UTF_16.name();

    @Test
    @DisplayName("Test basic functionality of I/O simulation ")
    public void testIODevice() throws UnsupportedEncodingException {
        //Set up input
        String input = "test";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(ENCODING));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);

        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(inputStream, printStream);

        //Read the input
        assertTrue(ioDevice.hasWord(), "The I/O device should have a word");
        assertEquals((short) 't', ioDevice.getWord(), "The first letter should match.");
        assertTrue(ioDevice.hasWord(), "The I/O device should have another word");
        assertEquals((short) 'e', ioDevice.getWord(), "The second letter should match.");
        assertTrue(ioDevice.hasWord(), "The I/O device should have another word");
        assertEquals((short) 's', ioDevice.getWord(), "The third letter should match.");
        assertTrue(ioDevice.hasWord(), "The I/O device should have another word");
        assertEquals((short) 't', ioDevice.getWord(), "The fourth letter should match.");

        //Confirm we've read all the input
        assertFalse(ioDevice.hasWord(), "The I/O device should have no more words");

        //Send some output
        ioDevice.sendWord((short) 'l');
        ioDevice.sendWord((short) 'o');
        ioDevice.sendWord((short) 'v');
        ioDevice.sendWord((short) 'e');

        String output = baos.toString();
        assertEquals("love", output, "The output should match the characters that were sent");
    }

    @Test
    @DisplayName("Simple WRDIN: reading 5 characters until we run out ")
    public void testSimpleWordIn() throws UnsupportedEncodingException {

        String code = " WRDIN AX    ; AX = H\r\n"
                + "     WRDIN BX    ; BX = e\r\n"
                + "     WRDIN CX    ; CX = l\r\n"
                + "     WRDIN DX    ; DX = l\r\n"
                + "     WRDIN EX    ; EX = o\r\n"
                + "     LOAD SP, 10 ; some value into SP so it's not starting as 0\r\n"
                + "     WRDIN SP    ; SP should be zero since there's no more input\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();

        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(new ByteArrayInputStream("Hello".getBytes(ENCODING)),
                new PrintStream(new ByteArrayOutputStream()));

        computer.setIoDevice(ioDevice);
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals((short) 'H', computer.getAX(), "AX should have the first character");
        assertEquals((short) 'e', computer.getBX(), "BX should have the second character");
        assertEquals((short) 'l', computer.getCX(), "CX should have the third character");
        assertEquals((short) 'l', computer.getDX(), "DX should have the fourth character");
        assertEquals((short) 'o', computer.getEX(), "EX should have the fifth character");
        assertEquals(0, computer.getSP(), "SP should have 0 since there were no more characters");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set since we ran out of characters");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Simple WRDOUT: writing 5 characters ")
    public void testSimpleWordOut() throws UnsupportedEncodingException {

        //I looked up the hex codes in the UTF-16 character set
        String code = " LOAD AX, 0x4A   ; AX = J\r\n"
                + "     LOAD BX, 0x61   ; BX = a\r\n"
                + "     LOAD CX, 0x6B   ; CX = k\r\n"
                + "     LOAD DX, 0x65   ; DX = e\r\n"
                + "     LOAD EX, 0x21   ; EX = !\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     WRDOUT BX       ; Write BX \r\n"
                + "     WRDOUT CX       ; Write CX \r\n"
                + "     WRDOUT DX       ; Write DX \r\n"
                + "     WRDOUT EX       ; Write EX \r\n"
                + "     HALT            ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(new ByteArrayInputStream("".getBytes(ENCODING)),
                new PrintStream(baos));

        computer.setIoDevice(ioDevice);
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals((short) 'J', computer.getAX(), "AX should have the first character");
        assertEquals((short) 'a', computer.getBX(), "BX should have the second character");
        assertEquals((short) 'k', computer.getCX(), "CX should have the third character");
        assertEquals((short) 'e', computer.getDX(), "DX should have the fourth character");
        assertEquals((short) '!', computer.getEX(), "EX should have the fifth character");

        //Check output
        String output = baos.toString();
        assertEquals("Jake!", output, "The output should match the characters that were sent");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }


    //TODO: write a test that does both in an output. Example: hello <name>!
}