package org.karnes.homebrew.forth;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SmallForthTest {
    private final static String ENCODING = StandardCharsets.UTF_16.name();


    @Test
    @DisplayName("Simulate the ?RX primitive word")
    public void testRXPrimitive() throws UnsupportedEncodingException {
        short stackStart = 1000;

        String code = " LOAD SP, " + stackStart + "       ; Move Stack pointer far away\r\n"
                + "     WRDIN BX            ; Read a character into BX.\r\n"

                + "; If there's a character, it's in BX now. Otherwise, BX is 0, and the zero flag is set.\r\n"
                + "     JZ QRX1             ; Jump if we didn't get a character \r\n"
                + "     PUSH SP, BX         ; We got a character, so push character (in BX) onto data stack (SP)\r\n"
                + "     LOAD BX, -1         ; Load true flag (any non-zero value, in this case -1) into BX\r\n"
                + "QRX1:    PUSH SP, BX     ; BX contains our flag (true or false depending on if character was read in), so we push it onto the data stack (SP)\r\n"
                + "\r\n"

                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();

        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(new ByteArrayInputStream("P".getBytes(ENCODING)),
                new PrintStream(new ByteArrayOutputStream()));

        computer.setIoDevice(ioDevice);
        computer.setMainMemory(RAM);
        computer.start();

        //Check stack
        assertNotEquals(stackStart, computer.getSP(), "The stack pointer should not be in its original location");
        assertEquals(stackStart - 2, computer.getSP(), "The stack pointer should be decremented by 2, since we pushed two elements.");
        assertEquals((short) 'P', computer.getMainMemory()[stackStart - 1], "The BOS for SP should be the character we read in.");
        assertEquals(-1, computer.getMainMemory()[stackStart - 2], "The TOS for SP should be the true flag.");

        //Reset computer
        computer = new RelayComputer();

        //No input this time
        ioDevice = new JavaSimulatedIODevice();

        computer.setIoDevice(ioDevice);
        computer.setMainMemory(RAM);
        computer.start();

        //Check stack
        assertNotEquals(stackStart, computer.getSP(), "The stack pointer should not be in its original location");
        assertEquals(stackStart - 1, computer.getSP(), "The stack pointer should be decremented by 1, since we pushed one element.");
        assertEquals(0, computer.getMainMemory()[stackStart - 1], "The BOS for SP should be the false flag.");

    }


}