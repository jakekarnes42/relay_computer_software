package org.karnes.homebrew.emulator;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An emulator for the Relay-based computer
 */
public class ForthRelayComputer extends RelayComputer {

    private Map<Character, String> symbolTable;
    int indentation = 0;
    private boolean inUP = false;
    private boolean inDOUSE = false;

    public ForthRelayComputer(Map<String, Character> symbolTable) {
        //Reverse KV
        this.symbolTable = symbolTable.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    @Override
    protected void callImmediate(StackReg stackRegister) {
        //Get target
        char target = (char) mainMemory[(char) super.getPC()];//Get the value in mem following instruction
        //Get label
        String targetName = symbolTable.get((char) super.getEX());
//        if (targetName != null)
        if (targetName == null) {
            indentation++;
        } else {
            indentation++;
//            System.out.println(getIndent() + "EX: " + targetName);
        }
        super.callImmediate(stackRegister);
    }

    @Override
    protected void executeMOVInstruction() {
        if (super.getINST() == (short) 0b1111_1111_11_111_000) {
            String targetName = symbolTable.get((char) super.getAX());
//            if (targetName != null)

            //Get stack info. Start w/ getting stack pointer.
            short sp = getSP();
            short rp = getRP();

//            System.out.println(getIndent() + "AX: " + targetName + ". Stack: SP=" + sp
//                    + " ( " + ((short) mainMemory[sp + 4])
//                    + " " + ((short) mainMemory[sp + 3])
//                    + " " + ((short) mainMemory[sp + 2])
//                    + " " + ((short) mainMemory[sp + 1])
//                    + " " + ((short) mainMemory[sp]) + " )"
//                    + ". Return: RP=" + rp
//                    + " ( " + ((short) mainMemory[rp + 4])
//                    + " " + ((short) mainMemory[rp + 3])
//                    + " " + ((short) mainMemory[rp + 2])
//                    + " " + ((short) mainMemory[rp + 1])
//                    + " " + ((short) mainMemory[rp]) + " )");
            if (targetName.equals("EXIT")) {
                indentation--;
                if (inUP) {
                    //Extra indentation fix
                    indentation--;
                    inUP = false;
                }
                if (inDOUSE) {
                    //Extra indentation fix
                    indentation--;
                    inDOUSE = false;
                }
            } else if (targetName.equals("UP")) {
                inUP = true;
            } else if (targetName.equals("DOUSE")) {
                inDOUSE = true;
            }

        }

        super.executeMOVInstruction();
    }


    @Override
    protected void executeLOADJMPInstruction() {
        super.executeLOADJMPInstruction();

        if (super.getINST() == (short) 0b0010_0001_0000_0_111) {
            char pc = (char) super.getPC();
            String targetName = symbolTable.get(pc);
//            if (targetName != null)
//            System.out.println("JMP: " + targetName);
        }
    }

    private String getIndent() {
        return String.join("", Collections.nCopies(indentation, "\t"));
    }
}
