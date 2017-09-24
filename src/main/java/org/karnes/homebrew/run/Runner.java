package org.karnes.homebrew.run;

import org.karnes.homebrew.assemblr.Assembler;
import org.karnes.homebrew.emulator.JavaSimulatedIODevice;
import org.karnes.homebrew.emulator.RelayComputer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Runner {

    public static void main(String[] args) throws Exception {
        Path binaryPath = Paths.get(args[0]);
        short[] RAM = loadBinaryExecutable(binaryPath);

        //Create a new computer to run it
        RelayComputer computer = new RelayComputer();
        computer.setMainMemory(RAM);

        //Run it.
        computer.start();


    }

    public static short[] loadBinaryExecutable(Path binaryPath) {
        //Chosen because this is the max memory in the relay computer. If the binary is bigger than this, we can't even load it.
        int maxBinary = 64_000;
        int count = 0;
        short[] buffer = new short[maxBinary];


        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(binaryPath.toFile())))) {
            while (count < maxBinary - 1) {
                short d = dis.readShort();
                buffer[count] = d;
                count++;
            }

        } catch (EOFException eof) {
            //reached the end of the file. that's expected. Let's keep going.
        } catch (IOException io) {
            //Other I/O exceptions are not expected. error!
            throw new IllegalStateException(io);
        }

        if (count == maxBinary) {
            //Too much input! Couldn't read it all.
            throw new IllegalArgumentException("The input binary is likely too large. Couldn't read it all.");
        }

        //Get the valid sub array that we actually read shorts into.
        return Arrays.copyOf(buffer, count);


    }

    public static void createBinaryExecutable(Path assemblyPath, Path outputPath) throws IOException {
        //Path assemblyPath = Paths.get("src", "main", "resources", "relay_eforth.asm");
        //Path outputPath = Paths.get("src", "main", "resources", "relay_eforth.bin");
        String code = Files.readAllLines(assemblyPath).stream()
                .map(line -> line + "\r\n").collect(Collectors.joining());

        Assembler assembler = new Assembler(code);

        short[] data = assembler.assemble();


        int bufferSize = 32 * 1024;

        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(outputPath.toFile()),
                        bufferSize)
        )) {
            for (int i = 0; i < data.length; i++) {
                dos.writeShort(data[i]);
            }
        }
    }


    public static String runRelayeForth(String forthCode) {
        try {
            //Load the binary.
            Path binaryPath = Paths.get("src", "main", "resources", "relay_eforth.bin");
            short[] RAM = loadBinaryExecutable(binaryPath);


            //Set up the canned I/O
            ByteArrayInputStream inputStream = new ByteArrayInputStream(forthCode.getBytes());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(baos);
            JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(inputStream, printStream);


            //Create a new computer to run it
            RelayComputer computer = new RelayComputer();
            computer.setIoDevice(ioDevice);
            computer.setMainMemory(RAM);

            //Run it.
            computer.start();

            //Format the output. DOS only adds line feeds and eats the carriage returns
            String output = baos.toString() + "\r\n";
            output = output.replaceAll("\\r\\n", "\n");
            return output;
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected exception while executing eForth code against the Relay computer emulator", e);
        }
    }

    /**
     * Runs dosemu to execute the forthCode against the original eForth.
     * <p>
     * <b>Assumes</b>
     * <ul>
     * <li>The application dosemu is installed an available from the command line</li>
     * <li>The original eForth executable is available at ~/eForth/original/EFORTH.COM</li>
     * </ul>
     *
     * @param forthCode
     * @return
     */
    public static String runDOSeForth(String forthCode) {
        String output = null;
        String errorOutput = null;
        try {
            //Write the code to a file, which we'll hand off to eForth
            Path inputCodePAth = Paths.get("/home", "jake", "eForth", "original", "input.f");
            Files.write(inputCodePAth, forthCode.getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

            //Get the path to the eForth executable
            Path script = Paths.get(System.getProperty("user.dir"), "eForth", "original", "dosemu_eForth.sh");
            String scriptString = script.toString();
            String eforthDOSPath = "D:\\eForth\\original\\EFORTH.COM";
            String inputCodeDOSPath = "D:\\eForth\\original\\input.f";

            ProcessBuilder pb = new ProcessBuilder(scriptString);


            Process proc = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = stdInput.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            output = builder.toString();

            // read any errors from the attempted command
            builder = new StringBuilder();
            while ((line = stdError.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            errorOutput = builder.toString();

            //Remove input file
            Files.deleteIfExists(inputCodePAth);

        } catch (Exception e) {
            throw new IllegalStateException("Unexpected exception while executing eForth code against the MS DOS emulator.\r\nOutput: "
                    + output + "\r\nError Output: " + errorOutput, e);
        }
        return output;
    }
}
