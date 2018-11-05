package org.karnes.homebrew.emulator.component.alu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.alu.logicunit.LU_OPCODE;
import org.karnes.homebrew.emulator.component.alu.logicunit.LogicUnit;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

import static org.junit.jupiter.api.Assertions.*;


class LogicUnitTest {
    private static int DATA_WIDTH = 4;

    private LogicUnit arithmeticUnit;
    private WritableConnection luOperationConnection;
    private WritableConnection tmp1Connection;
    private WritableConnection tmp2Connection;
    private ReadableConnection outputConnection;
    private ReadableConnection ccConnection;

    @BeforeEach
    void setUp() {
        //Create a new AU
        arithmeticUnit = new LogicUnit(DATA_WIDTH);

        //Get connections for testing
        luOperationConnection = arithmeticUnit.getOpcodeConnection();
        tmp1Connection = arithmeticUnit.getTmp1BusConnection();
        tmp2Connection = arithmeticUnit.getTmp2BusConnection();
        outputConnection = arithmeticUnit.getOutputBusConnection();
        ccConnection = arithmeticUnit.getCcBusConnection();
    }


    @Test
    void simpleTest() {
        //The LU is already hooked up.
        //Enable the output. This should be LU Enable + XOR (0,0)
        luOperationConnection.writeValue(LU_OPCODE.XOR.toBitSet());

        //The output should be 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());

        //The condition code for ZERO should be set
        FixedBitSet ccValue = ccConnection.readValue();
        assertEquals(new FixedBitSet("0001"), ccValue);

        ConditionCode conditionCode = new ConditionCode(ccValue);
        assertTrue(conditionCode.isZero());
        assertFalse(conditionCode.isSign());
        assertFalse(conditionCode.isOverflow());
        assertFalse(conditionCode.isCarry());
    }

    @Test
    void testChangingOperations() {
        //The LU is already hooked up.
        //First send the OFF signal
        luOperationConnection.writeValue(LU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Set some inputs
        tmp1Connection.writeValue(new FixedBitSet("0101"));
        tmp2Connection.writeValue(new FixedBitSet("1010"));

        //The output and cc should be still 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Enable OR
        luOperationConnection.writeValue(LU_OPCODE.OR.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(FixedBitSet.allOnes(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Change to AND
        luOperationConnection.writeValue(LU_OPCODE.AND.toBitSet());

        //The output and cc should change accordingly.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet("0001"), ccConnection.readValue());

        //Change to XOR
        luOperationConnection.writeValue(LU_OPCODE.XOR.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(FixedBitSet.allOnes(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Change to NOT
        luOperationConnection.writeValue(LU_OPCODE.NOT.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("1010"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Turn it back to OFF
        luOperationConnection.writeValue(LU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());
    }

    @Test
    void testChangingInputs() {
        //The LU is already hooked up.
        //First send the OFF signal
        luOperationConnection.writeValue(LU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Set some inputs
        tmp1Connection.writeValue(new FixedBitSet("0101"));
        tmp2Connection.writeValue(new FixedBitSet("1010"));

        //The output and cc should be still 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Enable OR
        luOperationConnection.writeValue(LU_OPCODE.OR.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(FixedBitSet.allOnes(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Change the first input
        tmp1Connection.writeValue(new FixedBitSet("0000"));

        //The output and cc should change accordingly.
        assertEquals(new FixedBitSet("1010"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());


        //Change the second input
        tmp2Connection.writeValue(new FixedBitSet("0000"));

        //The output and cc should change accordingly.
        assertEquals(new FixedBitSet("0000"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0001"), ccConnection.readValue());

        //Change the first input again
        tmp1Connection.writeValue(new FixedBitSet("0101"));

        //The output and cc should change accordingly.
        assertEquals(new FixedBitSet("0101"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0000"), ccConnection.readValue());


        //Turn it back to OFF
        luOperationConnection.writeValue(LU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_XORs.csv", numLinesToSkip = 1)
    void testAllXORs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);
        tmp2Connection.writeValue(b);

        //Enable the output. This should be LU Enable + XOR (a,b)
        luOperationConnection.writeValue(LU_OPCODE.XOR.toBitSet());

        //The output should be result.
        assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        assertEquals(cc, ccConnection.readValue());

    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_ORs.csv", numLinesToSkip = 1)
    void testAllORs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);
        tmp2Connection.writeValue(b);

        //Enable the output. This should be LU Enable + OR (a,b)
        luOperationConnection.writeValue(LU_OPCODE.OR.toBitSet());

        //The output should be result.
        assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        assertEquals(cc, ccConnection.readValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_ANDs.csv", numLinesToSkip = 1)
    void testAllANDs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);
        tmp2Connection.writeValue(b);

        //Enable the output. This should be LU Enable + AND (a,b)
        luOperationConnection.writeValue(LU_OPCODE.AND.toBitSet());

        //The output should be result.
        assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        assertEquals(cc, ccConnection.readValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_NOTs.csv", numLinesToSkip = 1)
    void testAllNOTs(FixedBitSet a, FixedBitSet result, FixedBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);

        //Enable the output. This should be LU Enable + NOT (a)
        luOperationConnection.writeValue(LU_OPCODE.NOT.toBitSet());

        //The output should be result.
        assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        assertEquals(cc, ccConnection.readValue());

    }

}