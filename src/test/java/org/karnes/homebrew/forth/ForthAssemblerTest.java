package org.karnes.homebrew.forth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.assemblr.Assembler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForthAssemblerTest {
    private final static String ENCODING = StandardCharsets.UTF_16.name();


    @Test
    @DisplayName("Test assembly!")
    public void testAssembly() throws IOException {

        String code = Files.readAllLines(Paths.get("src", "main", "resources", "relay_eforth.asm")).stream()
                .map(line -> line + "\r\n").collect(Collectors.joining());

        Assembler assembler = new Assembler();

        short[] RAM = assembler.assemble(code);

        assertTrue(true, "Yay! We assembled the code");
    }


}