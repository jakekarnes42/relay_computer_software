package org.karnes.homebrew;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.assemblr.parse.macro.MacroDefinitionCollector;
import org.karnes.homebrew.assemblr.parse.macro.ParsedMacro;
import org.karnes.homebrew.emulator.JavaSimulatedIODevice;
import org.karnes.homebrew.emulator.RelayComputer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

        MacroDefinitionCollector macroDefinitionCollector = new MacroDefinitionCollector();
        macroDefinitionCollector.findMacros(code);

        List<ParsedMacro> macroList = macroDefinitionCollector.getMacroList();
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

        MacroDefinitionCollector macroDefinitionCollector = new MacroDefinitionCollector();
        macroDefinitionCollector.findMacros(code);

        List<ParsedMacro> macroList = macroDefinitionCollector.getMacroList();
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
