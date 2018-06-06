package org.karnes.homebrew.emulator.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.arithmeticunit.AU_OPCODE;
import org.karnes.homebrew.emulator.component.arithmeticunit.ArithmeticUnit;
import org.karnes.homebrew.emulator.component.bus.BidirectionalBus;
import org.karnes.homebrew.emulator.component.bus.VirtualBus;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WriteableBusConnection;

import static org.junit.jupiter.api.Assertions.*;

class ArithemeticUnitTest {
    private static int DATA_WIDTH = 4;

    private ArithmeticUnit arithmeticUnit;
    private WriteableBusConnection auOperationBusConnection;
    private WriteableBusConnection tmp1BusConnection;
    private WriteableBusConnection tmp2BusConnection;
    private ReadableBusConnection outputBusConnection;
    private ReadableBusConnection ccBusConnection;

    @BeforeEach
    void setUp() {
        BidirectionalBus auOperationBus = new VirtualBus("AU_OPERATION_BUS", 3);
        auOperationBusConnection = auOperationBus.getWriteConnection();
        BidirectionalBus tmp1Bus = new VirtualBus("TMP1-ALU_BUS", DATA_WIDTH);
        tmp1BusConnection = tmp1Bus.getWriteConnection();
        BidirectionalBus tmp2Bus = new VirtualBus("TMP2-ALU_BUS", DATA_WIDTH);
        tmp2BusConnection = tmp2Bus.getWriteConnection();
        BidirectionalBus outputBus = new VirtualBus("AU_OUTPUT_BUS", DATA_WIDTH);
        outputBusConnection = outputBus.getReadConnection();
        BidirectionalBus ccBus = new VirtualBus("CC_BUS", 4);
        ccBusConnection = ccBus.getReadConnection();

        arithmeticUnit = new ArithmeticUnit(auOperationBus, tmp1Bus, tmp2Bus, outputBus, ccBus);
    }


    @Test
    void simpleTest() {
        //The AU is already hooked up.
        //Enable the output. This should be AU Enable + ADD (0,0)
        auOperationBusConnection.writeValueToBus(AU_OPCODE.ADD.toBitSet());

        //The output should be 0.
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), outputBusConnection.readBusValue());

        //The condition code for ZERO should be set
        FixedBitSet ccBusValue = ccBusConnection.readBusValue();
        assertEquals(new ArbitraryBitSet("0001"), ccBusValue);

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
        auOperationBusConnection.writeValueToBus(AU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), outputBusConnection.readBusValue());
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), ccBusConnection.readBusValue());

        //Set some inputs
        tmp1BusConnection.writeValueToBus(new ArbitraryBitSet("0101")); // 5
        tmp2BusConnection.writeValueToBus(new ArbitraryBitSet("1010")); // -6

        //The output and cc should be still 0.
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), outputBusConnection.readBusValue());
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), ccBusConnection.readBusValue());

        //Enable ADD
        auOperationBusConnection.writeValueToBus(AU_OPCODE.ADD.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(ArbitraryBitSet.allOnes(DATA_WIDTH), outputBusConnection.readBusValue()); //-1
        assertEquals(new ArbitraryBitSet("0010"), ccBusConnection.readBusValue());

        //Change to SUB
        auOperationBusConnection.writeValueToBus(AU_OPCODE.SUB.toBitSet());

        //The output and cc should change accordingly.
        assertEquals(new ArbitraryBitSet("1011"), outputBusConnection.readBusValue()); //is 11 -> overflow to: -5 
        assertEquals(new ArbitraryBitSet("1110"), ccBusConnection.readBusValue());

        //Change to INC
        auOperationBusConnection.writeValueToBus(AU_OPCODE.INC.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(new ArbitraryBitSet("0110"), outputBusConnection.readBusValue()); //6
        assertEquals(new ArbitraryBitSet("0000"), ccBusConnection.readBusValue());

        //Change to DEC
        auOperationBusConnection.writeValueToBus(AU_OPCODE.DEC.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(new ArbitraryBitSet("0100"), outputBusConnection.readBusValue()); //4
        assertEquals(new ArbitraryBitSet("0000"), ccBusConnection.readBusValue());

        //Turn it back to OFF
        auOperationBusConnection.writeValueToBus(AU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), outputBusConnection.readBusValue());
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), ccBusConnection.readBusValue());
    }

    @Test
    void testChangingInputs() {
        //The AU is already hooked up.
        //First send the OFF signal
        auOperationBusConnection.writeValueToBus(AU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), outputBusConnection.readBusValue());
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), ccBusConnection.readBusValue());

        //Set some inputs
        tmp1BusConnection.writeValueToBus(new ArbitraryBitSet("0101")); // 5
        tmp2BusConnection.writeValueToBus(new ArbitraryBitSet("1010")); // -6

        //The output and cc should be still 0.
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), outputBusConnection.readBusValue());
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), ccBusConnection.readBusValue());
 
        //Enable ADD
        auOperationBusConnection.writeValueToBus(AU_OPCODE.ADD.toBitSet());

        //The output and cc should now have valid values.
        assertEquals(ArbitraryBitSet.allOnes(DATA_WIDTH), outputBusConnection.readBusValue()); //-1
        assertEquals(new ArbitraryBitSet("0010"), ccBusConnection.readBusValue());

        //Change the first input
        tmp1BusConnection.writeValueToBus(new ArbitraryBitSet("0000")); // 0

        //The output and cc should change accordingly.
        assertEquals(new ArbitraryBitSet("1010"), outputBusConnection.readBusValue()); // -6
        assertEquals(new ArbitraryBitSet("0010"), ccBusConnection.readBusValue());


        //Change the second input
        tmp2BusConnection.writeValueToBus(new ArbitraryBitSet("0000")); //0

        //The output and cc should change accordingly.
        assertEquals(new ArbitraryBitSet("0000"), outputBusConnection.readBusValue());
        assertEquals(new ArbitraryBitSet("0001"), ccBusConnection.readBusValue());

        //Change the first input again
        tmp1BusConnection.writeValueToBus(new ArbitraryBitSet("0101"));

        //The output and cc should change accordingly.
        assertEquals(new ArbitraryBitSet("0101"), outputBusConnection.readBusValue());
        assertEquals(new ArbitraryBitSet("0000"), ccBusConnection.readBusValue());


        //Turn it back to OFF
        auOperationBusConnection.writeValueToBus(AU_OPCODE.OFF.toBitSet());

        //The output and cc should be 0.
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), outputBusConnection.readBusValue());
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), ccBusConnection.readBusValue());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_ADDs.csv", numLinesToSkip = 1)
    void testAllADDs(ArbitraryBitSet a, ArbitraryBitSet b, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The AU is already hooked up.
        //Set the inputs
        tmp1BusConnection.writeValueToBus(a);
        tmp2BusConnection.writeValueToBus(b);

        //Enable the output. This should be AU Enable + ADD (a,b)
        auOperationBusConnection.writeValueToBus(AU_OPCODE.ADD.toBitSet());

        //The output should be result.
        assertEquals(result, outputBusConnection.readBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.readBusValue());

    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_SUBs.csv", numLinesToSkip = 1)
    void testAllSUBs(ArbitraryBitSet a, ArbitraryBitSet b, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The AU is already hooked up.
        //Set the inputs
        tmp1BusConnection.writeValueToBus(a);
        tmp2BusConnection.writeValueToBus(b);

        //Enable the output. This should be AU Enable + SUB (a,b)
        auOperationBusConnection.writeValueToBus(AU_OPCODE.SUB.toBitSet());

        //The output should be result.
        assertEquals(result, outputBusConnection.readBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.readBusValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_INCs.csv", numLinesToSkip = 1)
    void testAllINCs(ArbitraryBitSet a, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The AU is already hooked up.
        //Set the inputs
        tmp1BusConnection.writeValueToBus(a);

        //Enable the output. This should be AU Enable + INC (a,b)
        auOperationBusConnection.writeValueToBus(AU_OPCODE.INC.toBitSet());

        //The output should be result.
        assertEquals(result, outputBusConnection.readBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.readBusValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_DECs.csv", numLinesToSkip = 1)
    void testAllDECs(ArbitraryBitSet a, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The AU is already hooked up.
        //Set the inputs
        tmp1BusConnection.writeValueToBus(a);

        //Enable the output. This should be AU Enable + DEC (a)
        auOperationBusConnection.writeValueToBus(AU_OPCODE.DEC.toBitSet());

        //The output should be result.
        assertEquals(result, outputBusConnection.readBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.readBusValue());

    }

}