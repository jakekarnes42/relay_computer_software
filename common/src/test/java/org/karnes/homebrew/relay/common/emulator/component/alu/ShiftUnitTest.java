package org.karnes.homebrew.relay.common.emulator.component.alu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.ConditionCode;
import org.karnes.homebrew.relay.common.emulator.component.alu.shiftunit.SU_OPCODE;
import org.karnes.homebrew.relay.common.emulator.component.alu.shiftunit.ShiftUnit;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.WritableConnection;

import static org.junit.jupiter.api.Assertions.*;


class ShiftUnitTest {
    private static int DATA_WIDTH = 4;

    private WritableConnection suOperationConnection;
    private WritableConnection tmp1Connection;
    private WritableConnection tmp2Connection;
    private ReadableConnection outputConnection;
    private ReadableConnection ccConnection;

    @BeforeEach
    void setUp() {
        //Create a new AU
        ShiftUnit arithmeticUnit = new ShiftUnit(DATA_WIDTH);

        //Get connections for testing
        suOperationConnection = arithmeticUnit.getOpcodeConnection();
        tmp1Connection = arithmeticUnit.getTmp1BusConnection();
        tmp2Connection = arithmeticUnit.getTmp2BusConnection();
        outputConnection = arithmeticUnit.getOutputBusConnection();
        ccConnection = arithmeticUnit.getCcBusConnection();
    }


    @Test
    void simpleTest() {
        //The SU is already hooked up.
        //Enable the output. This should be SU Enable + XOR (0,0)
        suOperationConnection.writeValue(SU_OPCODE.ROR.toBitSet());

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
        //The SU is already hooked up.
        //First send the OFF signal
        suOperationConnection.writeValue(SU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Set some inputs
        tmp1Connection.writeValue(new FixedBitSet("0101"));
        tmp2Connection.writeValue(new FixedBitSet("1000"));

        //The output and cc should be still 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Enable ROR
        suOperationConnection.writeValue(SU_OPCODE.ROR.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("1010"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Change to ROL
        suOperationConnection.writeValue(SU_OPCODE.ROL.toBitSet());

        //The output and cc should change accordingly.
        assertEquals(new FixedBitSet("1010"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Change to RCR
        // Inputs:  0101 1
        // Outputs: 1010 1
        suOperationConnection.writeValue(SU_OPCODE.RCR.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("1010"), outputConnection.readValue());
        assertEquals(new FixedBitSet("1010"), ccConnection.readValue());

        //Turn off carry bit and check that the output updated
        // Inputs:  0101 0
        // Outputs: 0010 1
        tmp2Connection.writeValue(new FixedBitSet("0000"));

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("0010"), outputConnection.readValue());
        assertEquals(new FixedBitSet("1000"), ccConnection.readValue());

        //Change to RCL
        // Inputs:  0101 0
        // Outputs: 1010 0
        suOperationConnection.writeValue(SU_OPCODE.RCL.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("1010"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Turn on carry bit and check that the output updated
        // Inputs:  0101 1
        // Outputs: 1011 0
        tmp2Connection.writeValue(new FixedBitSet("1000"));

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("1011"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Turn it back to OFF
        suOperationConnection.writeValue(SU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());
    }

    @Test
    void testChangingInputs() {
        //The SU is already hooked up.
        //First send the OFF signal
        suOperationConnection.writeValue(SU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        //Set some inputs
        tmp1Connection.writeValue(new FixedBitSet("0101"));
        tmp2Connection.writeValue(new FixedBitSet("1000"));

        //The output and cc should be still 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());

        // Enable RCR
        // Inputs:  0101 1
        // Outputs: 1010 1
        suOperationConnection.writeValue(SU_OPCODE.RCR.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("1010"), outputConnection.readValue());
        assertEquals(new FixedBitSet("1010"), ccConnection.readValue());

        //Change TMP1 value
        // Inputs:  1010 1
        // Outputs: 1101 0
        tmp1Connection.writeValue(new FixedBitSet("1010"));

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("1101"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0010"), ccConnection.readValue());

        //Change CARRY value
        // Inputs:  1010 0
        // Outputs: 0101 0
        tmp2Connection.writeValue(new FixedBitSet(DATA_WIDTH));

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("0101"), outputConnection.readValue());
        assertEquals(new FixedBitSet("0000"), ccConnection.readValue());

        //Change TMP value
        // Inputs:  0101 0
        // Outputs: 0010 1
        tmp1Connection.writeValue(new FixedBitSet("0101"));

        //The output and cc should now have valid values.
        assertEquals(new FixedBitSet("0010"), outputConnection.readValue());
        assertEquals(new FixedBitSet("1000"), ccConnection.readValue());

        //Turn it back to OFF
        suOperationConnection.writeValue(SU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());
    }

//    @Test
//    void generateTestData() {
//        //The SU is already hooked up.
//        //First send the OFF signal
//        suOperationConnection.writeValue(SU_OPCODE.OFF.toBitSet());
//
//        //The output and cc should be 0.
//        assertEquals(new FixedBitSet(DATA_WIDTH), outputConnection.readValue());
//        assertEquals(new FixedBitSet(DATA_WIDTH), ccConnection.readValue());
//
//        //Set opcode
//        suOperationConnection.writeValue(SU_OPCODE.RCL.toBitSet());
//
//        for(int i = 0; i<=255; i++){
//            byte val = (byte) i;
//            FixedBitSet bits = FixedBitSet.fromByte(val);
//            FixedBitSet tmp1 = bits.getSlice(4,8);
//            FixedBitSet tmp2 = bits.getSlice(0,4);
//
//            tmp1Connection.writeValue(tmp1);
//            tmp2Connection.writeValue(tmp2);
//
//            FixedBitSet result = outputConnection.readValue();
//            FixedBitSet conditionCode = ccConnection.readValue();
//            System.out.println(String.join(",", tmp1.toString(), tmp2.toString(), result.toString(), conditionCode.toString()));
//
//        }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_RORs.csv", numLinesToSkip = 1)
    void testAllXORs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
        //The SU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);
        tmp2Connection.writeValue(b);

        //Enable the output. This should be SU Enable + ROR (a,b)
        suOperationConnection.writeValue(SU_OPCODE.ROR.toBitSet());

        //The output should be result.
        assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        assertEquals(cc, ccConnection.readValue());

    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_ROLs.csv", numLinesToSkip = 1)
    void testAllROLs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
        //The SU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);
        tmp2Connection.writeValue(b);

        //Enable the output. This should be SU Enable + ROL (a,b)
        suOperationConnection.writeValue(SU_OPCODE.ROL.toBitSet());

        //The output should be result.
        assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        assertEquals(cc, ccConnection.readValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_RCRs.csv", numLinesToSkip = 1)
    void testAllRCRs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
        //The SU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);
        tmp2Connection.writeValue(b);

        //Enable the output. This should be SU Enable + RCR (a,b)
        suOperationConnection.writeValue(SU_OPCODE.RCR.toBitSet());

        //The output should be result.
        assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        assertEquals(cc, ccConnection.readValue());

    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_RCLs.csv", numLinesToSkip = 1)
    void testAllRCLs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
        //The SU is already hooked up.
        //Set the inputs
        tmp1Connection.writeValue(a);
        tmp2Connection.writeValue(b);

        //Enable the output. This should be SU Enable + RCL (a,b)
        suOperationConnection.writeValue(SU_OPCODE.RCL.toBitSet());

        //The output should be result.
        assertEquals(result, outputConnection.readValue());

        //The condition code should be cc
        assertEquals(cc, ccConnection.readValue());

    }
}