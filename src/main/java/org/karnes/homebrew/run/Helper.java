package org.karnes.homebrew.run;

import org.karnes.homebrew.emulator.component.register.RegisterName;

public class Helper {

    private static String testFormat = "@Test\n" +
            "                  void decode%s_%s_%s() {\n" +
            "                      BitSet16 binary = new BitSet16(\"0001 111 %s %s 000\");\n" +
            "                      Instruction instruction = decoder.decode(binary);\n" +
            "                      assertNotNull(instruction);\n" +
            "                      assertTrue(instruction instanceof %sInstruction);\n" +
            "                      assertEquals(\"%s\", instruction.getName());\n" +
            "                      assertEquals(RegisterName.%s, ((%sInstruction) instruction).getDestinationRegister());\n" +
            "                      assertEquals(RegisterName.%s, ((%sInstruction) instruction).getSourceRegister());\n" +
            "                      assertEquals(binary, instruction.toBinary());\n" +
            "             \n" +
            "                  }";

    public static void main(String[] args) {
        String name = "NOT";

        for (RegisterName destination : RegisterName.values()) {
            for (RegisterName source1 : RegisterName.values()) {
                System.out.println(String.format(testFormat, name, destination.name(), source1.name(),
                        destination.getBitSet().toString(), source1.getBitSet().toString(),
                        name,
                        name,
                        destination.name(), name,
                        source1.name(), name));
            }


        }
    }


}




