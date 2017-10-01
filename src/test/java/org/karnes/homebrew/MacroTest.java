package org.karnes.homebrew;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.assemblr.parse.macro.MacroDefinitionFinder;
import org.karnes.homebrew.assemblr.parse.macro.ParsedMacro;
import org.karnes.homebrew.emulator.JavaSimulatedIODevice;
import org.karnes.homebrew.emulator.RelayComputer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MacroTest {

    @Test
    @DisplayName("Test macro collection of a single macro")
    public void testMacroCollector1() {
        String code = ";Let's play with macros!\r\n"
                //Start by declaring macro and its parameters
                + "MACRO $PNT_CHR @RG , @CHAR     ; Macro to print @CHAR through register @RG\r\n"
                + "   LOAD @RG , @CHAR            ; Load @CHAR into @RG\r\n"
                + "   WRDOUT @RG                 ; Print through that reg\r\n"
                + "   ENDM                      ; End of $PNT_CHR\r\n"
                // Now let's use that macro to print Hello!
                + "$PNT_CHR AX , {'H'}   ; Print 'H'\r\n"
                + "$PNT_CHR BX , {'E'}   ; Print 'E'\r\n"
                + "$PNT_CHR CX , {'L'}   ; Print 'L'\r\n"
                + "$PNT_CHR DX , {'L'}   ; Print 'L'\r\n"
                + "$PNT_CHR EX , {'O'}   ; Print 'O'\r\n"
                + "\r\n"
                + " HALT                ; DONE\r\n";

        MacroDefinitionFinder macroDefinitionFinder = new MacroDefinitionFinder();
        macroDefinitionFinder.findMacros(code);

        List<ParsedMacro> macroList = macroDefinitionFinder.getMacroList();
        assertEquals(1, macroList.size(), "There should be one macro");

        ParsedMacro macro = macroList.get(0);
        assertEquals("$PNT_CHR", macro.getName(), "The macro should be named correctly");

        List<String> paramNames = macro.getParamNames();
        assertEquals(2, paramNames.size(), "The macro should have two parameters");
        assertEquals("@RG", paramNames.get(0), "The first parameter should be named correctly");
        assertEquals("@CHAR", paramNames.get(1), "The second parameter should be named correctly");
    }

    @Test
    @DisplayName("Test macro collection of a two macros")
    public void testMacroCollector2() {
        String code = ";Let's play with macros!\r\n"
                //Start by declaring character printing macro and its parameters
                + "MACRO $PNT_CHR @RG , @CHAR         ; Macro to print @CHAR through register @RG\r\n"
                + "     LOAD @RG , @CHAR            ; Load @CHAR into @RG\r\n"
                + "     WRDOUT @RG                 ; Print through that reg\r\n"
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

        MacroDefinitionFinder macroDefinitionFinder = new MacroDefinitionFinder();
        macroDefinitionFinder.findMacros(code);

        List<ParsedMacro> macroList = macroDefinitionFinder.getMacroList();
        assertEquals(2, macroList.size(), "There should be one macro");

        //First macro
        ParsedMacro macro = macroList.get(0);
        assertEquals("$PNT_CHR", macro.getName(), "The macro should be named correctly");

        //First macro's params
        List<String> paramNames = macro.getParamNames();
        assertEquals(2, paramNames.size(), "The macro should have two parameters");
        assertEquals("@RG", paramNames.get(0), "The first parameter should be named correctly");
        assertEquals("@CHAR", paramNames.get(1), "The second parameter should be named correctly");

        //Second macro
        macro = macroList.get(1);
        assertEquals("$PNT_5", macro.getName(), "The macro should be named correctly");

        //Second macro's params
        paramNames = macro.getParamNames();
        assertEquals(5, paramNames.size(), "The macro should have two parameters");
        assertEquals("@1", paramNames.get(0), "The first parameter should be named correctly");
        assertEquals("@2", paramNames.get(1), "The second parameter should be named correctly");
        assertEquals("@3", paramNames.get(2), "The third parameter should be named correctly");
        assertEquals("@4", paramNames.get(3), "The fourth parameter should be named correctly");
        assertEquals("@5", paramNames.get(4), "The fifth parameter should be named correctly");

    }


    @Test
    @DisplayName("Test a simple macro usage")
    public void testSimpleMacro() {
        String code = ";Let's play with macros!\r\n"
                //Start by declaring macro and its parameters
                + "MACRO $PNT_CHR @RG , @CHAR     ; Macro to print @CHAR through register @RG\r\n"
                + "   LOAD @RG , @CHAR            ; Load @CHAR into @RG\r\n"
                + "   WRDOUT @RG                 ; Print through that reg\r\n"
                + "   ENDM                      ; End of $PNT_CHR\r\n"

                // Now let's use that macro to print Hello!
                + "$PNT_CHR AX , {'H'}   ; Print 'H'\r\n"
                + "$PNT_CHR BX , {'E'}   ; Print 'E'\r\n"
                + "$PNT_CHR CX , {'L'}   ; Print 'L'\r\n"
                + "$PNT_CHR DX , {'L'}   ; Print 'L'\r\n"
                + "$PNT_CHR EX , {'O'}   ; Print 'O'\r\n"


                + " HALT                ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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
    @DisplayName("Test a macro without parameters")
    public void testMacroNoParams() {
        String code = ";Let's play with macros!\r\n"
                //Start by declaring macro
                + "MACRO $MOVE_STKS     ; Macro to move stack pointers far into mem\r\n"
                + "   LOAD SP , 10000   ; Point SP far into memory\r\n"
                + "   LOAD RP , 20000   ; Point RP far into memory\r\n"
                + "   ENDM              ; End of $MOVE_STCKS\r\n"

                // Now let's use that macro
                + "$MOVE_STKS           ; Reference the macro\r\n"

                + "HALT                ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();

        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals(10000, computer.getSP(), "SP should be updated by macro execution");
        assertEquals(20000, computer.getRP(), "RP should be updated by macro execution");


        //Check condition registers
        assertFalse(computer.getCarryFlag(), "The carry flag should not be set");
        assertFalse(computer.getOverflowFlag(), "The overflow flag should not be set");
        assertFalse(computer.getZeroFlag(), "The zero flag should not be set");
        assertFalse(computer.getSignFlag(), "The sign flag should not be set");
    }

    @Test
    @DisplayName("Test macros nested inside each other, which share a parameter")
    public void testNestedMacroPassThroughParam() {
        String code = ";Let's play with macros!\r\n"
                //Start by declaring character printing macro and its parameters
                + "MACRO $PNT_CHR @RG , @CHAR         ; Macro to print @CHAR through register @RG\r\n"
                + "     LOAD @RG , @CHAR            ; Load @CHAR into @RG\r\n"
                + "     WRDOUT @RG                 ; Print through that reg\r\n"
                + "     ENDM                      ; End of $PNT_CHR\r\n"

                //Start by declaring word printing macro and its parameters
                + "MACRO $PNT_5 @RG, @1 , @2, @3, @4, @5 ; Macro to print 5 characters through all @RG\r\n"
                + "     $PNT_CHR @RG , @1            ; Print @1\r\n"
                + "     $PNT_CHR @RG , @2            ; Print @2\r\n"
                + "     $PNT_CHR @RG , @3            ; Print @3\r\n"
                + "     $PNT_CHR @RG , @4            ; Print @4\r\n"
                + "     $PNT_CHR @RG , @5            ; Print @5\r\n"
                + "     ENDM                        ; End of $PNT_5\r\n"


                // Now let's use that macro to print Hello!
                + "$PNT_5 AX, {'H'} , {'E'}, {'L'}, {'L'}, {'O'}   ; Print 'HELLO'\r\n"

                + " HALT                ; DONE\r\n";

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

        RelayComputer computer = new RelayComputer();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(new ByteArrayInputStream("".getBytes()),
                new PrintStream(baos));

        computer.setIoDevice(ioDevice);
        computer.setMainMemory(RAM);
        computer.start();

        //Check registers
        assertEquals((short) 'O', computer.getAX(), "EX should have the fifth character");

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
                + "MACRO $PNT_CHR @RG , @CHAR         ; Macro to print @CHAR through register @RG\r\n"
                + "     LOAD @RG , @CHAR            ; Load @CHAR into @RG\r\n"
                + "     WRDOUT @RG                 ; Print through that reg\r\n"
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

        Assembler assembler = new Assembler(code);

        short[] RAM = assembler.assemble();

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
    @DisplayName("Test macros nested inside each other, but declared in the wrong order")
    public void testNestedMacroWrongOrder() {
        String code = ";Let's test some bad macros!\r\n"
                //Uh-oh, we're using nesting a macro before it's been declared
                + "MACRO $PNT_5 @1 , @2, @3, @4, @5 ; Macro to print 5 characters through all registers\r\n"
                + "     $PNT_CHR AX , @1            ; Print @1\r\n"
                + "     $PNT_CHR BX , @2            ; Print @2\r\n"
                + "     $PNT_CHR CX , @3            ; Print @3\r\n"
                + "     $PNT_CHR DX , @4            ; Print @4\r\n"
                + "     $PNT_CHR EX , @5            ; Print @5\r\n"
                + "     ENDM                        ; End of $PNT_5\r\n"


                //Our declaration is too late!
                + "MACRO $PNT_CHR @RG , @CHAR         ; Macro to print @CHAR through register @RG\r\n"
                + "     LOAD @RG , @CHAR            ; Load @CHAR into @RG\r\n"
                + "     WRDOUT @RG                 ; Print through that reg\r\n"
                + "     ENDM                      ; End of $PNT_CHR\r\n"


                // Now let's use that macro to print Hello!
                + "$PNT_5 {'H'} , {'E'}, {'L'}, {'L'}, {'O'}   ; Print 'HELLO'\r\n"

                + " HALT                ; DONE\r\n";

        assertThrows(IllegalStateException.class, () -> {
            Assembler assembler = new Assembler(code);
            short[] RAM = assembler.assemble();
        });
    }


    @Test
    @DisplayName("Test macro declaration with duplicate param names")
    public void testMacroSameParamNames() {
        String code = ";Let's test some bad macros!\r\n"
                //Uh-oh, two parameters have the same name!
                + "MACRO $PNT_CHR @C , @C ; Macro to print @C through register @C\r\n"
                + "     LOAD @C , @C      ; Load @C into @C\r\n"
                + "     WRDOUT @C         ; Print through that reg\r\n"
                + "     ENDM              ; End of $PNT_CHR\r\n"


                // Now let's use that macro to print H
                + "$PNT_CHR AX , {'H'}          ; Print 'H'\r\n"

                + " HALT                ; DONE\r\n";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Assembler assembler = new Assembler(code);
            short[] RAM = assembler.assemble();
        });

        assertTrue(exception.getMessage().contains("$PNT_CHR"),
                "There should be some reference to \"$PNT_CHR\" in this exception");

    }

    @Test
    @DisplayName("Test macro reference without macro declaration")
    public void testMissingMacro() {
        String code =
                //Start by declaring character printing macro and its parameters
                "          MACRO $PNT_CHR @RG , @CHAR         ; Macro to print @CHAR through register @RG\r\n"
                        + "     LOAD @RG , @CHAR            ; Load @CHAR into @RG\r\n"
                        + "     WRDOUT @RG                 ; Print through that reg\r\n"
                        + "     ENDM                      ; End of $PNT_CHR\r\n"

                        //Start by declaring word printing macro and its parameters
                        + "MACRO $PNT_5 @1 , @2, @3, @4, @5 ; Macro to print 5 characters through all registers\r\n"
                        + "     $PNT_CHR AX , @1            ; Print @1\r\n"
                        + "     $PNT_CHR BX , @2            ; Print @2\r\n"
                        + "     $PNT_CHR CX , @3            ; Print @3\r\n"
                        + "     $PNT_CHR DX , @4            ; Print @4\r\n"
                        + "     $PNT_CHR EX , @5            ; Print @5\r\n"
                        + "     ENDM                        ; End of $PNT_5\r\n"


                        // Uh-oh, we never defined a $PNT_6 macro!
                        + "$PNT_6 {'H'} , {'E'}, {'L'}, {'L'}, {'O'} , {'!'}   ; Print 'HELLO!'\r\n"

                        + " HALT                ; DONE\r\n";


        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            Assembler assembler = new Assembler(code);
            short[] RAM = assembler.assemble();
        });

        assertTrue(exception.getMessage().contains("$PNT_6"),
                "There should be some reference to \"$PNT_6\" in this exception");
    }

    @Test
    @DisplayName("Test macro that didn't have it's ENDM tag.")
    public void testMacroMissingENDM() {
        String code = ";Let's test some bad macros!\r\n"
                + "MACRO $PNT_CHR @RG , @CHAR     ; Macro to print @CHAR through register @RG\r\n"
                + "   LOAD @RG , @CHAR            ; Load @CHAR into @RG\r\n"
                + "   WRDOUT @RG                 ; Print through that reg\r\n"

                //Uh-oh, we "forgot" our ENDM statement
                //+ "   ENDM                      ; End of $PNT_CHR\r\n"

                // Now let's use that macro to print Hello!
                + "$PNT_CHR AX , {'H'}   ; Print 'H'\r\n"
                + "$PNT_CHR BX , {'E'}   ; Print 'E'\r\n"
                + "$PNT_CHR CX , {'L'}   ; Print 'L'\r\n"
                + "$PNT_CHR DX , {'L'}   ; Print 'L'\r\n"
                + "$PNT_CHR EX , {'O'}   ; Print 'O'\r\n"


                + " HALT                ; DONE\r\n";

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            Assembler assembler = new Assembler(code);
            short[] RAM = assembler.assemble();
        });

        assertTrue(exception.getMessage().contains("ENDM"),
                "There should be some reference to \"ENDM\" in this exception");
    }


}
