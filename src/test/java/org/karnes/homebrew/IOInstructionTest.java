package temp;

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
    private final static String ENCODING = StandardCharsets.UTF_8.name();


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
                + "     TIN         ; Zero flag should be set since there's no more input\r\n"
                + "     HALT                    ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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

    @Test
    @DisplayName("Simple WRDOUT: writing 5 characters with JS expressions")
    public void testSimpleWordOutJS() throws UnsupportedEncodingException {

        String code = " LOAD AX, {'J'}  ; AX = J\r\n"
                + "     LOAD BX, {'a'}  ; BX = a\r\n"
                + "     LOAD CX, {'k'}  ; CX = k\r\n"
                + "     LOAD DX, {'e'}  ; DX = e\r\n"
                + "     LOAD EX, {'!'}  ; EX = !\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     WRDOUT BX       ; Write BX \r\n"
                + "     WRDOUT CX       ; Write CX \r\n"
                + "     WRDOUT DX       ; Write DX \r\n"
                + "     WRDOUT EX       ; Write EX \r\n"
                + "     HALT            ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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

    @Test
    @DisplayName("Simple WRDOUT: writing 5 characters with a stack")
    public void testSimpleWordOutStack() throws UnsupportedEncodingException {
        String code = " {STR_LOC = 1000}    ; Constant of where we'll write the characters\r\n"
                + "     ORG {STR_LOC}       ; Move code pointer to where we want save characters\r\n"
                + "     DS \"Jake!\"        ; Write string to memory\r\n"
                + "     ORG {0}             ; Move code pointer back to initial start location\r\n"
                + "     LOAD SP, {STR_LOC}  ; Point stack pointer at memory location, at the first\r\n"

                //Print the String
                + "     POP AX, SP  ; AX = J\r\n"
                + "     POP BX, SP  ; BX = a\r\n"
                + "     POP CX, SP  ; CX = k\r\n"
                + "     POP DX, SP  ; DX = e\r\n"
                + "     POP EX, SP  ; EX = !\r\n"
                + "     WRDOUT AX   ; Write AX \r\n"
                + "     WRDOUT BX   ; Write BX \r\n"
                + "     WRDOUT CX   ; Write CX \r\n"
                + "     WRDOUT DX   ; Write DX \r\n"
                + "     WRDOUT EX   ; Write EX \r\n"
                + "     HALT        ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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


    @Test
    @DisplayName("Bidirection I/O test. Hello <input_name>! Double-stack implementation")
    public void testHelloName() throws UnsupportedEncodingException {
        String name = "Jake";

        String code = "; Reads in a name, prints 'Hello <name>!'\r\n"
                + "         LOAD SP, 500    ; Move SP far into memory \r\n"
                + "         LOAD RP, 900    ; Move RP far into memory \r\n"
                + "READ:    TIN             ; Test if we have any remaining letters\r\n"
                + "         JZ READ_DONE    ; If there wasn't a letter, jump to READ_DONE\r\n"
                + "         WRDIN AX        ; Get the next letter into AX \r\n"
                + "         PUSH SP, AX     ; Push the letter onto SP \r\n"
                + "         INC BX, BX      ; Update our counter in BX\r\n"
                + "         JMP READ        ; Jump back to read another character \r\n"


                + "; All of the letters are on SP stack. BX has how many letters we read in\r\n"
                + "; We want to move the letters off the SP stack onto the RP stack. \r\n"
                + "READ_DONE:   MOV DX, BX  ; Save the number of letters (BX) into DX for later\r\n"
                + "SHUF:        POP CX, SP  ; Pop top of SP in CX\r\n"
                + "             PUSH RP, CX ; Push CX onto RP stack \r\n"
                + "             DEC BX, BX  ; Decrement our BX counter\r\n"
                + "             JNZ SHUF    ; If that wasn't the last letter, repeat SHUF \r\n"


                + "; Now all of the letters have been pushed onto RP in reverse order, and DX is our counter \r\n"
                + "; Start by writing 'Hello ' out \r\n"
                + "     LOAD AX, 0x48   ; AX = H\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x65   ; AX = e\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x6C   ; AX = l\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x6C   ; AX = l\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x6F   ; AX = o\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x20   ; AX = ' '\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"

                + "; Write the name by popping all elements of RP, decrementing DX until 0 \r\n"
                + "PRINT:   POP AX, RP  ; Get next letter off RP stack into AX\r\n"
                + "         WRDOUT AX   ; Write AX \r\n"
                + "         DEC DX, DX  ; Decrement our DX counter\r\n"
                + "         JNZ PRINT   ; If that wasn't the last letter, repeat PRINT \r\n"

                + "; All the input letters have been written back out, finish with exclaimation point! \r\n"
                + "     LOAD AX, 0x21   ; AX = '!'\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     HALT            ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(new ByteArrayInputStream(name.getBytes(ENCODING)),
                new PrintStream(baos));

        computer.setIoDevice(ioDevice);
        computer.setMainMemory(RAM);
        computer.start();

        //Check output
        String output = baos.toString();
        assertEquals("Hello " + name + "!", output, "The output should be the message, including input name");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set since that was the last ALU operation");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");

    }

    @Test
    @DisplayName("Bidirection I/O test. Hello <input_name>! Single-stack implementation")
    public void testHelloName2() throws UnsupportedEncodingException {
        String name = "Jake";

        String code = "; Reads in a name, prints 'Hello <name>!'\r\n"
                + "         LOAD RP, 500    ; Point RP far into memory\r\n"
                + "         LOAD CX, 500    ; Point CX far at top of RP stack\r\n"

                + "READ:    TIN             ; Check if there's any input\r\n"
                + "         JZ READ_DONE    ; If there wasn't a letter, jump to READ_DONE\r\n"
                + "         WRDIN AX        ; Get the next letter into AX \r\n"
                + "         STORE CX, AX    ; Store the letter at memory address of CX \r\n"
                + "         INC CX, CX      ; Increment CX to previous memory location\r\n"
                + "         JMP READ        ; Jump back to read another character \r\n"


                + "; All of the letters are on RP stack. And we want to calculate how many letters we read in\r\n"
                + "READ_DONE:   SUB DX , CX, RP ; DX = CX - RP, so DX now has difference between final CX and RP TOS\r\n"


                + "; Now all of the letters have been pushed onto RP in reverse order, and DX is our counter \r\n"
                + "; Start by writing 'Hello ' out \r\n"
                + "     LOAD AX, 0x48   ; AX = H\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x65   ; AX = e\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x6C   ; AX = l\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x6C   ; AX = l\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x6F   ; AX = o\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     LOAD AX, 0x20   ; AX = ' '\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"

                + "; Write the name by popping all elements of RP, decrementing DX until 0 \r\n"
                + "PRINT:   POP AX, RP  ; Get next letter off RP stack into AX\r\n"
                + "         WRDOUT AX   ; Write AX \r\n"
                + "         DEC DX, DX  ; Decrement our DX counter\r\n"
                + "         JNZ PRINT   ; If that wasn't the last letter, repeat PRINT \r\n"

                + "; All the input letters have been written back out, finish with exclaimation point! \r\n"
                + "     LOAD AX, 0x21   ; AX = '!'\r\n"
                + "     WRDOUT AX       ; Write AX \r\n"
                + "     HALT            ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(new ByteArrayInputStream(name.getBytes(ENCODING)),
                new PrintStream(baos));

        computer.setIoDevice(ioDevice);
        computer.setMainMemory(RAM);
        computer.start();

        //Check output
        String output = baos.toString();
        assertEquals("Hello " + name + "!", output, "The output should be the message, including input name");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertTrue(computer.getZeroFlag(), "The zero flag should be set since that was the last ALU operation");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");

    }
}