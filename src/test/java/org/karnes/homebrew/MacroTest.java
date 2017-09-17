package org.karnes.homebrew;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.JavaSimulatedIODevice;
import org.karnes.homebrew.emulator.RelayComputer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MacroTest {

    @Test
    @DisplayName("Test a simple macro usage")
    public void testSimpleMacro() {
        String code = ";Let's play with macros!\r\n"
                //Start by declaring macro and its parameters
                + "MACRO $PNT_CHR RG , CHAR     ; Macro to print CHAR through register RG\r\n"
                + "   LOAD RG , CHAR            ; Load CHAR into RG\r\n"
                + "   WRDOUT RG                 ; Print through that reg\r\n"
                + "   ENDM                      ; End of $PNT_CHR\r\n"

                // Now let's use that macro to print Hello!
                + "$PNT_CHR AX , {'H'}   ; Print 'H'\r\n"
                + "$PNT_CHR BX , {'E'}   ; Print 'E'\r\n"
                + "$PNT_CHR CX , {'L'}   ; Print 'L'\r\n"
                + "$PNT_CHR DX , {'L'}   ; Print 'L'\r\n"
                + "$PNT_CHR EX , {'O'}   ; Print 'O'\r\n"


                + " HALT                ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(new ByteArrayInputStream("".getBytes()),
                new PrintStream(baos));

        computer.setIoDevice(ioDevice);
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals((short) 'H', computer.getAX(), "AX should have the first character");
        assertEquals((short) 'E', computer.getBX(), "BX should have the second character");
        assertEquals((short) 'L', computer.getCX(), "CX should have the third character");
        assertEquals((short) 'L', computer.getDX(), "DX should have the fourth character");
        assertEquals((short) 'O', computer.getEX(), "EX should have the fifth character");

        //Check output
        String output = baos.toString();
        assertEquals("HELLO", output, "The output should match the characters that were sent");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Test macros nested inside each other")
    public void testNestedMacro() {
        String code = ";Let's play with macros!\r\n"
                //Start by declaring character printing macro and its parameters
                + "MACRO $PNT_CHR RG , CHAR         ; Macro to print CHAR through register RG\r\n"
                + "     LOAD RG , CHAR            ; Load CHAR into RG\r\n"
                + "     WRDOUT RG                 ; Print through that reg\r\n"
                + "     ENDM                      ; End of $PNT_CHR\r\n"

                //Start by declaring word printing macro and its parameters
                + "MACRO $PNT_5 @1 , @2, @3, @4, @5 ; Macro to print 5 characters through all registers\r\n"
                + "     $PNT_CHR AX , @1            ; Print @1\r\n"
                + "     $PNT_CHR BX , @2            ; Print @2\r\n"
                + "     $PNT_CHR CX , @3            ; Print @3\r\n"
                + "     $PNT_CHR DX , @4            ; Print @4\r\n"
                + "     $PNT_CHR EX , @5            ; Print @5\r\n"
                + "     ENDM                        ; End of $PNT_5\r\n"


                // Now let's use that macro to print Hello!
                + "$PNT_5 {'H'} , {'E'}, {'L'}, {'L'}, {'O'}   ; Print 'HELLO'\r\n"

                + " HALT                ; DONE\r\n";

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        RelayComputer computer = new RelayComputer();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(new ByteArrayInputStream("".getBytes()),
                new PrintStream(baos));

        computer.setIoDevice(ioDevice);
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals((short) 'H', computer.getAX(), "AX should have the first character");
        assertEquals((short) 'E', computer.getBX(), "BX should have the second character");
        assertEquals((short) 'L', computer.getCX(), "CX should have the third character");
        assertEquals((short) 'L', computer.getDX(), "DX should have the fourth character");
        assertEquals((short) 'O', computer.getEX(), "EX should have the fifth character");

        //Check output
        String output = baos.toString();
        assertEquals("HELLO", output, "The output should match the characters that were sent");

        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }



}
