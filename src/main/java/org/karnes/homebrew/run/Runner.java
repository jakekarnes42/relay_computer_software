package org.karnes.homebrew.run;

import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.ForthRelayComputer;
import org.karnes.homebrew.emulator.JavaSimulatedIODevice;
import org.karnes.homebrew.emulator.RelayComputer;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Runner {
//    private final static String ENCODING = StandardCharsets.UTF_8.name();

    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            String code = Files.readAllLines(Paths.get("src", "main", "resources", "relay_eforth.asm")).stream()
                    .map(line -> line + "\r\n").collect(Collectors.joining());

            Assembler assembler = new Assembler(code);

            short[] RAM = assembler.assemble();

            RelayComputer computer = new ForthRelayComputer(assembler.getSymbolTable());

            //Set up input
//            String input = "2 2 +\r-2 -\r7 *\r.\rBYE\r";
            String input = "WORDS\rBYE\r";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

            JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(inputStream, System.out);


            computer.setIoDevice(ioDevice);
            computer.setMainMemory(RAM);
            computer.start();
//            System.out.println("AX = " + (int) computer.getAX());
//            System.out.println("BX = " + (int) computer.getBX());
//            System.out.println("CX = " + (int) computer.getCX());
        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
            e.printStackTrace();
            System.exit(1);
        }

//        long loop = 0;
////        Set up input
//            String input = "HI\r\n";
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
//
//        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(inputStream, System.out);
//        while (!ioDevice.hasWord()) {
//            loop++;
//        }
//        while (ioDevice.hasWord()) {
//            short word = ioDevice.getWord();
//            System.out.println((char) word);
//        }
    }
}
