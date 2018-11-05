package org.karnes.homebrew.relay.common.forth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.computer.assemblr.Assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForthAssemblerTest {


    @Test
    @DisplayName("Test assembly!")
    public void testAssembly() throws IOException {

        String code = Files.readAllLines(Paths.get("src", "main", "resources", "relay_eforth.asm")).stream()
                .map(line -> line + "\r\n").collect(Collectors.joining());

        Assembler assembler = new Assembler(code);


        short[] RAM = assembler.assemble();

        assertTrue(true, "Yay! We assembled the code");
    }


}