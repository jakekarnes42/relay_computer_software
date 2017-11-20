package org.karnes.homebrew.run;import org.karnes.homebrew.assemblr.Assembler;import org.karnes.homebrew.emulator.ForthRelayComputer;import org.karnes.homebrew.emulator.JavaSimulatedIODevice;import org.karnes.homebrew.emulator.RelayComputer;import java.io.*;import java.nio.file.Files;import java.nio.file.Path;import java.nio.file.Paths;import java.nio.file.StandardOpenOption;import java.util.Arrays;import java.util.Map;import java.util.stream.Collectors;/** * Contains the main-method for command line execution, and utility methods for executing code against the emulator. */public class Runner {    public static void main(String[] args) throws Exception {//        Path binaryPath = Paths.get(args[0]);//        short[] RAM = loadBinaryExecutable(binaryPath);////        //Create a new computer to run it//        RelayComputer computer = new RelayComputer();//        computer.setMainMemory(RAM);////        //Run it.//        computer.start();//////        String forthCode = ": HELLO CR .\" Hello, world!\" ;\r" +//Define the new HELLO word//                "HELLO\r" + //Run it//                "BYE\r"; //Exit//        String forthCode =                ": question\r" +                        " CR CR .\" Any more problems you want to solve?\"\r" +                        " CR .\" What kind ( sex, job, money, health ) ?\"\r" +                        " CR\r" +                        " ;\r" +                        ": help CR\r" +                        " CR .\" Hello! My name is Creating Computer.\"\r" +                        " CR .\" Hi there!\"\r" +                        " CR .\" Are you enjoying yourself here?\"\r" +                        " KEY 32 OR 89 =\r" +                        " CR\r" +                        " IF CR .\" I am glad to hear that.\"\r" +                        " ELSE CR .\" I am sorry about that.\"\r" +                        " CR .\" maybe we can brighten your visit a bit.\"\r" +                        " THEN\r" +                        " CR .\" Say!\"\r" +                        " CR .\" I can solved all kinds of problems except those dealing\"\r" +                        " CR .\" with Greece. \"\r" +                        " question\r" +                        " ;\r" +                        ": sex CR CR .\" Is your problem TOO MUCH or TOO LITTLE?\"\r" +                        " CR\r" +                        " ;\r" +                        ": too ; ( noop for syntax smoothness )\r" +                        ": much CR CR .\" You call that a problem?!! I SHOULD have that problem.\"\r" +                        " CR .\" If it reall y bothers you, take a cold shower.\"\r" +                        " question\r" +                        " ;\r" +                        ": little\r" +                        " CR CR .\" Why are you here!\"\r" +                        " CR .\" You should be in Tokyo or New York of Amsterdam or\"\r" +                        " CR .\" some place with some action.\"\r" +                        " question\r" +                        " ;\r" +                        ": health\r" +                        " CR CR .\" My advise to you is:\"\r" +                        " CR .\" 1. Take two tablets of aspirin.\"\r" +                        " CR .\" 2. Drink plenty of fluids.\"\r" +                        " CR .\" 3. Go to bed (along) .\"\r" +                        " question\r" +                        " ;\r" +                        ": job CR CR .\" I can sympathize with you.\"\r" +                        " CR .\" I have to work very long every day with no pay.\"\r" +                        " CR .\" My advise to you, is to open a rental computer store.\"\r" +                        " question\r" +                        " ;\r" +                        ": money\r" +                        " CR CR .\" Sorry! I am broke too.\"\r" +                        " CR .\" Why don't you sell encyclopedias or marry\"\r" +                        " CR .\" someone rich or stop eating, so you won't \"\r" +                        " CR .\" need so much money?\"\r" +                        " question\r" +                        " ;\r" +                        ": HELP help ;\r" +                        ": H help ;\r" +                        ": h help ;\r" +                        "h \r";        Path assemblyPath = Paths.get("src", "main", "resources", "relay_eforth.asm");        String code = Files.readAllLines(assemblyPath).stream()                .map(line -> line + "\r\n").collect(Collectors.joining());        Assembler assembler = new Assembler(code);        short[] RAM = assembler.assemble();        Map<String, Character> symbolTable = assembler.getSymbolTable();        //Set up the canned I/O        ByteArrayInputStream inputStream = new ByteArrayInputStream(forthCode.getBytes());        ByteArrayOutputStream baos = new ByteArrayOutputStream();        PrintStream printStream = new PrintStream(baos);        JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(inputStream, System.out);        //Create a new computer to run it        ForthRelayComputer computer = new ForthRelayComputer();        computer.setIoDevice(ioDevice);        computer.setMainMemory(RAM);        //Run it.        computer.start();        System.out.println();        System.out.println("Instructions executed: " + computer.getInstructionsExecuted());//        //Format the output. DOS only adds line feeds and eats the carriage returns//        String output = baos.toString() + "\r\n";//        output = output.replaceAll("\\r\\n", "\n");//        return output;//        Path assemblyPath = Paths.get("src", "main", "resources", "relay_eforth.asm");//        Path expandedPath = Paths.get("src", "main", "resources", "relay_eforth_expanded.asm");//        createExpandedAssembly(assemblyPath, expandedPath);//        Path binaryPath = Paths.get("src", "main", "resources", "relay_eforth.bin");//        createBinaryExecutable(assemblyPath, binaryPath);    }    /**     * Loads a binary executable from disk into a short[] that can be loaded into the emulator.     *     * @param binaryPath The path to the executable on disk     * @return a short[] that can be loaded into the emulator.     */    public static short[] loadBinaryExecutable(Path binaryPath) {        //Chosen because this is the max memory in the relay computer. If the binary is bigger than this, we can't even load it.        int maxBinary = Character.MAX_VALUE;        int count = 0;        short[] buffer = new short[maxBinary];        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(binaryPath.toFile())))) {            while (count < maxBinary - 1) {                short d = dis.readShort();                buffer[count] = d;                count++;            }        } catch (EOFException eof) {            //reached the end of the file. that's expected. Let's keep going.        } catch (IOException io) {            //Other I/O exceptions are not expected. error!            throw new IllegalStateException(io);        }        if (count == maxBinary) {            //Too much input! Couldn't read it all.            throw new IllegalArgumentException("The input binary is likely too large. Couldn't read it all.");        }        //Get the valid sub array that we actually read shorts into.        return Arrays.copyOf(buffer, count);    }    /**     * Assembles source assembly code with expanded macros, and saves that assembly to disk.     *     * @param assemblyPath The Path to source code. Ex: {@code Path assemblyPath = Paths.get("src", "main", "resources", "relay_eforth.asm");}     * @param outputPath   The Path to where the output expanded assembly file should be written. Ex: {@code Path outputPath = Paths.get("src", "main", "resources", "relay_eforth_expanded.asm");}     * @throws IOException if unable to create/save the expanded assembly.     */    public static void createExpandedAssembly(Path assemblyPath, Path outputPath) throws IOException {        String code = Files.readAllLines(assemblyPath).stream()                .map(line -> line + "\r\n").collect(Collectors.joining());        Assembler assembler = new Assembler(code);        assembler.assemble();        String expandedCode = assembler.getExpandedCodeWithMacros();        Files.write(outputPath, expandedCode.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);    }    /**     * Assembles source assembly code into a binary executable, and saves that binary to disk.     *     * @param assemblyPath The Path to source code. Ex: {@code Path assemblyPath = Paths.get("src", "main", "resources", "relay_eforth.asm");}     * @param outputPath   The Path to where the output binary file should be written. Ex: {@code Path outputPath = Paths.get("src", "main", "resources", "relay_eforth.bin");}     * @throws IOException if unable to create/save the executable binary.     */    public static void createBinaryExecutable(Path assemblyPath, Path outputPath) throws IOException {        String code = Files.readAllLines(assemblyPath).stream()                .map(line -> line + "\r\n").collect(Collectors.joining());        Assembler assembler = new Assembler(code);        short[] data = assembler.assemble();        int bufferSize = 32 * 1024;        try (DataOutputStream dos = new DataOutputStream(                new BufferedOutputStream(                        new FileOutputStream(outputPath.toFile()),                        bufferSize)        )) {            for (int i = 0; i < data.length; i++) {                dos.writeShort(data[i]);            }        }    }    public static String runRelayeForth(String forthCode) {        try {            //Load the binary.            Path binaryPath = Paths.get("src", "main", "resources", "relay_eforth.bin");            short[] RAM = loadBinaryExecutable(binaryPath);            //Set up the canned I/O            ByteArrayInputStream inputStream = new ByteArrayInputStream(forthCode.getBytes());            ByteArrayOutputStream baos = new ByteArrayOutputStream();            PrintStream printStream = new PrintStream(baos);            JavaSimulatedIODevice ioDevice = new JavaSimulatedIODevice(inputStream, printStream);            //Create a new computer to run it            RelayComputer computer = new RelayComputer();            computer.setIoDevice(ioDevice);            computer.setMainMemory(RAM);            //Run it.            computer.start();            //Format the output. DOS only adds line feeds and eats the carriage returns            String output = baos.toString() + "\r\n";            output = output.replaceAll("\\r\\n", "\n");            return output;        } catch (Exception e) {            throw new IllegalStateException("Unexpected exception while executing eForth code against the Relay computer emulator", e);        }    }    /**     * Runs dosemu to execute the forthCode against the original eForth.     * <p>     * <b>Assumes</b>     * <ul>     * <li>The application dosemu is installed an available from the command line</li>     * <li>The original eForth executable is available at ~/eForth/original/EFORTH.COM</li>     * </ul>     *     * @param forthCode     * @return     */    public static String runDOSeForth(String forthCode) {        String output = null;        String errorOutput = null;        try {            //Write the code to a file, which we'll hand off to eForth            Path inputCodePAth = Paths.get("/home", "jake", "eForth", "original", "input.f");            Files.write(inputCodePAth, forthCode.getBytes(), StandardOpenOption.CREATE,                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);            //Get the path to the eForth executable            Path script = Paths.get(System.getProperty("user.dir"), "eForth", "original", "dosemu_eForth.sh");            String scriptString = script.toString();            String eforthDOSPath = "D:\\eForth\\original\\EFORTH.COM";            String inputCodeDOSPath = "D:\\eForth\\original\\input.f";            ProcessBuilder pb = new ProcessBuilder(scriptString);            Process proc = pb.start();            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));            // read the output from the command            StringBuilder builder = new StringBuilder();            String line;            while ((line = stdInput.readLine()) != null) {                builder.append(line);                builder.append(System.getProperty("line.separator"));            }            output = builder.toString();            // read any errors from the attempted command            builder = new StringBuilder();            while ((line = stdError.readLine()) != null) {                builder.append(line);                builder.append(System.getProperty("line.separator"));            }            errorOutput = builder.toString();            //Remove input file            Files.deleteIfExists(inputCodePAth);        } catch (Exception e) {            throw new IllegalStateException("Unexpected exception while executing eForth code against the MS DOS emulator.\r\nOutput: "                    + output + "\r\nError Output: " + errorOutput, e);        }        return output;    }}