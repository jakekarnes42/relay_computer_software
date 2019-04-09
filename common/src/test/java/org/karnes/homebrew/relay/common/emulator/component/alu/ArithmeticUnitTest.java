package org.karnes.homebrew.relay.common.emulator.component.alu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.ConditionCode;
import org.karnes.homebrew.relay.common.emulator.component.alu.arithmeticunit.AU_OPCODE;
import org.karnes.homebrew.relay.common.emulator.component.alu.arithmeticunit.ArithmeticUnit;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.WritableConnection;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticUnitTest {
    private static int DATA_WIDTH = 4;

    private WritableConnection auOperationConnection;
    private WritableConnection tmp1Connection;
    private WritableConnection tmp2Connection;
    private ReadableConnection outputConnection;
    private ReadableConnection ccConnection;

    @BeforeEach
    void setUp() {
        //Create a new AU
        ArithmeticUnit arithmeticUnit = new ArithmeticUnit(DATA_WIDTH);

        //Get connections for testing
        auOperationConnection = arithmeticUnit.getOpcodeConnection();
        tmp1Connection = arithmeticUnit.getTmp1BusConnection();
        tmp2Connection = arithmeticUnit.getTmp2BusConnection();
        outputConnection = arithmeticUnit.getOutputBusConnection();
        ccConnection = arithmeticUnit.getCcBusConnection();
    }


    @Test
    void simpleTest() {
        //The AU is already hooked up.
        //Enable the output. This should be AU Enable + ADD (0,0)
        auOperationConnection.writeValue(AU_OPCODE.ADD.toBitSet());

        //The output should be 0.
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());

        //The condition code for ZERO should be set
        FixedBitSet ccBusValue = ccConnection.readValue();
        assertEquals(new FixedBitSet("0001"), ccBusValue);

        ConditionCode conditionCode = new ConditionCode(ccBusValue);
        assertTrue(conditionCode.isZero());
        assertFalse(conditionCode.isSign());
        assertFalse(conditionCode.isOverflow());
        assertFalse(conditionCode.isCarry());
    }

    @Test
    void testChangingOperations() {
        //The AU is already hooked up.
        //First send the OFF signal
        auOperationConnection.writeValue(AU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Set some inputs
        tmp1Connection.writeValue(new FixedBitSet("0101")); // 5
        tmp2Connection.writeValue(new FixedBitSet("1010")); // -6

        //The output and cc should be still 0.
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Enable ADD
        auOperationConnection.writeValue(AU_OPCODE.ADD.toBitSet());

        //The output and cc should now have valid values.
        Assertions.assertEquals(FixedBitSet.allOnes(DATA_WIDTH), outputConnection.readValue()); //-1
        Assertions.assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Change to SUB
        auOperationConnection.writeValue(AU_OPCODE.SUB.toBitSet());

        //The output and cc should change accordingly.
        Assertions.assertEquals(new FixedBitSet("1011"), outputConnection.readValue()); //is 11 -> overflow to: -5
        Assertions.assertEquals(new FixedBitSet("1110"), ccConnection.readValue());

        //Change to INC
        auOperationConnection.writeValue(AU_OPCODE.INC.toBitSet());

        //The output and cc should now have valid values.
        Assertions.assertEquals(new FixedBitSet("0110"), outputConnection.readValue()); //6
        Assertions.assertEquals(new FixedBitSet("0000"), ccConnection.readValue());

        //Change to DEC
        auOperationConnection.writeValue(AU_OPCODE.DEC.toBitSet());

        //The output and cc should now have valid values.
        Assertions.assertEquals(new FixedBitSet("0100"), outputConnection.readValue()); //4
        Assertions.assertEquals(new FixedBitSet("0000"), ccConnection.readValue());

        //Turn it back to OFF
        auOperationConnection.writeValue(AU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());
    }

    @Test
    void testChangingInputs() {
        //The AU is already hooked up.
        //First send the OFF signal
        auOperationConnection.writeValue(AU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Set some inputs
        tmp1Connection.writeValue(new FixedBitSet("0101")); // 5
        tmp2Connection.writeValue(new FixedBitSet("1010")); // -6

        //The output and cc should be still 0.
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Enable ADD
        auOperationConnection.writeValue(AU_OPCODE.ADD.toBitSet());

        //The output and cc should now have valid values.
        Assertions.assertEquals(FixedBitSet.allOnes(DATA_WIDTH), outputConnection.readValue()); //-1
        Assertions.assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Change the first input
        tmp1Connection.writeValue(new FixedBitSet("0000")); // 0

        //The output and cc should change accordingly.
        Assertions.assertEquals(new FixedBitSet("1010"), outputConnection.readValue()); // -6
        Assertions.assertEquals(new FixedBitSet("0010"), ccConnection.readValue());


        //Change the second input
        tmp2Connection.writeValue(new FixedBitSet("0000")); //0

        //The output and cc should change accordingly.
        Assertions.assertEquals(new FixedBitSet("0000"), outputConnection.readValue());
        Assertions.assertEquals(new FixedBitSet("0001"), ccConnection.readValue());

        //Change the first input again
        tmp1Connection.writeValue(new FixedBitSet("0101"));

        //The output and cc should change accordingly.
        Assertions.assertEquals(new FixedBitSet("0101"), outputConnection.readValue());
        Assertions.assertEquals(new FixedBitSet("0000"), ccConnection.readValue());


        //Turn it back to OFF
        auOperationConnection.writeValue(AU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        Assertions.assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_ADDs.csv", numLinesToSkip = 1)
    void testAllADDs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
        //The AU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);
        tmp2Connection.writeValue(b);

        //Enable the output. This should be AU Enable + ADD (a,b)
        auOperationConnection.writeValue(AU_OPCODE.ADD.toBitSet());

        //The output should be result.
        Assertions.assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        Assertions.assertEquals(cc, ccConnection.readValue());

    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_SUBs.csv", numLinesToSkip = 1)
    void testAllSUBs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
        //The AU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);
        tmp2Connection.writeValue(b);

        //Enable the output. This should be AU Enable + SUB (a,b)
        auOperationConnection.writeValue(AU_OPCODE.SUB.toBitSet());

        //The output should be result.
        Assertions.assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        Assertions.assertEquals(cc, ccConnection.readValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_INCs.csv", numLinesToSkip = 1)
    void testAllINCs(FixedBitSet a, FixedBitSet result, FixedBitSet cc) {
        //The AU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);

        //Enable the output. This should be AU Enable + INC (a,b)
        auOperationConnection.writeValue(AU_OPCODE.INC.toBitSet());

        //The output should be result.
        Assertions.assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        Assertions.assertEquals(cc, ccConnection.readValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_DECs.csv", numLinesToSkip = 1)
    void testAllDECs(FixedBitSet a, FixedBitSet result, FixedBitSet cc) {
        //The AU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);

        //Enable the output. This should be AU Enable + DEC (a)
        auOperationConnection.writeValue(AU_OPCODE.DEC.toBitSet());

        //The output should be result.
        Assertions.assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        Assertions.assertEquals(cc, ccConnection.readValue());

    }


}