package org.karnes.homebrew.run;

import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.RelayComputer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Runner {

    public static void main(String[] args) {
        try {
            String code = Files.readAllLines(Paths.get("src", "main", "resources", "relay_eforth_expanded.asm")).stream()
                    .map(line -> line + "\r\n").collect(Collectors.joining());

            Assembler assembler = new Assembler();

            short[] RAM = assembler.assemble(code);

            RelayComputer computer = new RelayComputer();
            computer.setMainMemory(RAM);
            computer.start();
            System.out.println("AX = " + (int) computer.getAX());
            System.out.println("BX = " + (int) computer.getBX());
            System.out.println("CX = " + (int) computer.getCX());
        } catch(Exception e){
            System.err.println("Error: " + e.toString());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
