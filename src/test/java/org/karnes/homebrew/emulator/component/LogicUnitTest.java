package org.karnes.homebrew.emulator.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;

import static org.junit.jupiter.api.Assertions.*;

class LogicUnitTest {
    private static int DATA_WIDTH = 4;
    private BusConnection luOperationBusConnection;
    private BusConnection tmp1BusConnection;
    private BusConnection tmp2BusConnection;
    private BusConnection outputBusConnection;
    private BusConnection ccBusConnection;
    private LogicUnit logicUnit;

    @BeforeEach
    void setUp() {
        Bus luOperationBus = new Bus("LU_OPERATION_BUS", 3);
        luOperationBusConnection = new BusConnection(luOperationBus);
        Bus tmp1Bus = new Bus("TMP1-ALU_BUS", DATA_WIDTH);
        tmp1BusConnection = new BusConnection(tmp1Bus);
        Bus tmp2Bus = new Bus("TMP2-ALU_BUS", DATA_WIDTH);
        tmp2BusConnection = new BusConnection(tmp2Bus);
        Bus outputBus = new Bus("LU_OUTPUT_BUS", DATA_WIDTH);
        outputBusConnection = new BusConnection(outputBus);
        Bus ccBus = new Bus("CC_BUS", 4);
        ccBusConnection = new BusConnection(ccBus);

        logicUnit = new LogicUnit(luOperationBus, tmp1Bus, tmp2Bus, outputBus, ccBus);
    }


    @Test
    void simpleTest() {
        //The LU is already hooked up.
        //Enable the output. This should be LU Enable + XOR (0,0)
        luOperationBusConnection.setToBusOutput(new ArbitraryBitSet("100"));

        //The output should be 0.
        assertEquals(new ArbitraryBitSet(DATA_WIDTH), outputBusConnection.getBusValue());

        //The condition code for ZERO should be set
        FixedBitSet ccBusValue = ccBusConnection.getBusValue();
        assertEquals(new ArbitraryBitSet("0001"), ccBusValue);

        ConditionCode conditionCode = new ConditionCode(ccBusValue);
        assertTrue(conditionCode.isZero());
        assertFalse(conditionCode.isSign());
        assertFalse(conditionCode.isOverflow());
        assertFalse(conditionCode.isCarry());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_XORs.csv", numLinesToSkip = 1)
    void testAllXORs(ArbitraryBitSet a, ArbitraryBitSet b, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1BusConnection.setToBusOutput(a);
        tmp2BusConnection.setToBusOutput(b);

        //Enable the output. This should be LU Enable + XOR (a,b)
        luOperationBusConnection.setToBusOutput(new ArbitraryBitSet("100"));

        //The output should be result.
        assertEquals(result, outputBusConnection.getBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.getBusValue());

    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_ORs.csv", numLinesToSkip = 1)
    void testAllORs(ArbitraryBitSet a, ArbitraryBitSet b, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1BusConnection.setToBusOutput(a);
        tmp2BusConnection.setToBusOutput(b);

        //Enable the output. This should be LU Enable + OR (a,b)
        luOperationBusConnection.setToBusOutput(new ArbitraryBitSet("101"));

        //The output should be result.
        assertEquals(result, outputBusConnection.getBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.getBusValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_ANDs.csv", numLinesToSkip = 1)
    void testAllANDs(ArbitraryBitSet a, ArbitraryBitSet b, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1BusConnection.setToBusOutput(a);
        tmp2BusConnection.setToBusOutput(b);

        //Enable the output. This should be LU Enable + AND (a,b)
        luOperationBusConnection.setToBusOutput(new ArbitraryBitSet("110"));

        //The output should be result.
        assertEquals(result, outputBusConnection.getBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.getBusValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_NOTs.csv", numLinesToSkip = 1)
    void testAllNOTs(ArbitraryBitSet a, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1BusConnection.setToBusOutput(a);

        //Enable the output. This should be LU Enable + NOT (a)
        luOperationBusConnection.setToBusOutput(new ArbitraryBitSet("111"));

        //The output should be result.
        assertEquals(result, outputBusConnection.getBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.getBusValue());

    }

}