package org.karnes.homebrew.relay.computer.emulator;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An extension of the standard relay computer emulator {@link RelayComputer}, that is helpful when debugging Forth code execution.
 * <p>
 * When the print lines are uncommented, this will print out the information akin to a stack trace, so it's easy to see which
 * Forth words are calling other Forth words, and the values they receive/return.
 */
public class ForthRelayComputer extends RelayComputer {

    private final boolean debugMode;
    private Map<Character, String> symbolTable = null;
    private int indentation = 0;
    private boolean inUP = false;
    private boolean inDOUSE = false;

    private long instructionsExecuted = 0;

    public ForthRelayComputer() {
        debugMode = false;
    }

    public ForthRelayComputer(Map<String, Character> symbolTable) {
        //Reverse KV
        this.symbolTable = symbolTable.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        debugMode = true;
    }

    //Overwritten to allow instruction counting
    @Override
    protected void executeCurrentInstruction() {
        instructionsExecuted++;
        super.executeCurrentInstruction();
    }

    @Override
    protected void callImmediate(StackReg stackRegister) {
        //The CALL instruction takes us another level deeper into the call path
        if (debugMode) {
            //Get label
            String targetName = symbolTable.get((char) super.getEX());
            if (targetName != null) {
                printDebugInfo(targetName, "EX");
            }
            indentation++;
        }
        super.callImmediate(stackRegister);
    }

    @Override
    protected void executeMOVInstruction() {
        if (debugMode) {
            //Check if the instruction is MOV PC, AX. If so, we need to analyze it for debug info.

            if (super.getINST() == (short) 0b1111_1111_11_111_000) {
                String targetName = symbolTable.get((char) super.getAX());

                //Print information for debugging purposes
                printDebugInfo(targetName, "AX");

                //Depending on which word we're executing, we may need to update our indentation level
                updateDebugTracking(targetName);
            }
        }
        super.executeMOVInstruction();
    }


    private void printDebugInfo(String targetName, String registerName) {
        short sp = getSP();
        short rp = getRP();

        System.out.println(getIndent() + registerName + ": " + targetName + ". Stack: SP=" + sp
                + " ( " + getDataStack(4)
                + " " + getDataStack(3)
                + " " + getDataStack(2)
                + " " + getDataStack(1)
                + " " + getDataStack(0) + " )"
                + ". Return: RP=" + rp
                + " ( " + getReturnStack(4)
                + " " + getReturnStack(3)
                + " " + getReturnStack(2)
                + " " + getReturnStack(1)
                + " " + getReturnStack(0) + " )");
    }

    //Updates our tracking information so the indentation depth is correct
    private void updateDebugTracking(String targetName) {
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

    /**
     * Gets the value on the data stack at the {@code index} position from the top of stack (TOS). If index is 0, this will return the TOS.
     *
     * @param index
     * @return The value on the data stack at position X
     * @throws IllegalArgumentException if {@code index} is negative.
     */
    public short getDataStack(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index to getDataStack may not be negative");
        }
        short sp = getSP();
        short value = mainMemory[sp + index];
        return value;

    }

    /**
     * Gets the value on the return stack at the {@code index} position from the top of stack (TOS). If index is 0, this will return the TOS.
     *
     * @param index
     * @return The value on the data stack at position X
     * @throws IllegalArgumentException if {@code index} is negative.
     */
    public short getReturnStack(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index to getReturnStack may not be negative");
        }
        short rp = getRP();
        short value = mainMemory[rp + index];
        return value;
    }

    private String getIndent() {
        return String.join("", Collections.nCopies(indentation, "\t"));
    }

    public long getInstructionsExecuted() {
        return instructionsExecuted;
    }
}
