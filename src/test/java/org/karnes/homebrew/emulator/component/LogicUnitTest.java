package org.karnes.homebrew.emulator.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.bus.*;
import org.karnes.homebrew.emulator.component.logicunit.LogicUnit;

import static org.junit.jupiter.api.Assertions.*;

class LogicUnitTest {
    private static int DATA_WIDTH = 4;

    private LogicUnit logicUnit;
    private WriteableBusConnection luOperationBusConnection;
    private WriteableBusConnection tmp1BusConnection;
    private WriteableBusConnection tmp2BusConnection;
    private ReadableBusConnection outputBusConnection;
    private ReadableBusConnection ccBusConnection;

    @BeforeEach
    void setUp() {
        Bus luOperationBus = new VirtualBus("LU_OPERATION_BUS", 3);
        luOperationBusConnection = luOperationBus.getWriteConnection();
        Bus tmp1Bus = new VirtualBus("TMP1-ALU_BUS", DATA_WIDTH);
        tmp1BusConnection = tmp1Bus.getWriteConnection();
        Bus tmp2Bus = new VirtualBus("TMP2-ALU_BUS", DATA_WIDTH);
        tmp2BusConnection = tmp2Bus.getWriteConnection();
        Bus outputBus = new VirtualBus("LU_OUTPUT_BUS", DATA_WIDTH);
        outputBusConnection = outputBus.getReadConnection();
        Bus ccBus = new VirtualBus("CC_BUS", 4);
        ccBusConnection = ccBus.getReadConnection();

        logicUnit = new LogicUnit(luOperationBus, tmp1Bus, tmp2Bus, outputBus, ccBus);
    }


    @Test
    void simpleTest() {
        //The LU is already hooked up.
        //Enable the output. This should be LU Enable + XOR (0,0)
        luOperationBusConnection.writeValueToBus(new ArbitraryBitSet("100"));

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


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_XORs.csv", numLinesToSkip = 1)
    void testAllXORs(ArbitraryBitSet a, ArbitraryBitSet b, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1BusConnection.writeValueToBus(a);
        tmp2BusConnection.writeValueToBus(b);

        //Enable the output. This should be LU Enable + XOR (a,b)
        luOperationBusConnection.writeValueToBus(new ArbitraryBitSet("100"));

        //The output should be result.
        assertEquals(result, outputBusConnection.readBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.readBusValue());

    }


    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_ORs.csv", numLinesToSkip = 1)
    void testAllORs(ArbitraryBitSet a, ArbitraryBitSet b, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1BusConnection.writeValueToBus(a);
        tmp2BusConnection.writeValueToBus(b);

        //Enable the output. This should be LU Enable + OR (a,b)
        luOperationBusConnection.writeValueToBus(new ArbitraryBitSet("101"));

        //The output should be result.
        assertEquals(result, outputBusConnection.readBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.readBusValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_ANDs.csv", numLinesToSkip = 1)
    void testAllANDs(ArbitraryBitSet a, ArbitraryBitSet b, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1BusConnection.writeValueToBus(a);
        tmp2BusConnection.writeValueToBus(b);

        //Enable the output. This should be LU Enable + AND (a,b)
        luOperationBusConnection.writeValueToBus(new ArbitraryBitSet("110"));

        //The output should be result.
        assertEquals(result, outputBusConnection.readBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.readBusValue());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/4bit_NOTs.csv", numLinesToSkip = 1)
    void testAllNOTs(ArbitraryBitSet a, ArbitraryBitSet result, ArbitraryBitSet cc) {
        //The LU is already hooked up.
        //Set the inputs
        tmp1BusConnection.writeValueToBus(a);

        //Enable the output. This should be LU Enable + NOT (a)
        luOperationBusConnection.writeValueToBus(new ArbitraryBitSet("111"));

        //The output should be result.
        assertEquals(result, outputBusConnection.readBusValue());

        //The condition code should be cc
        assertEquals(cc, ccBusConnection.readBusValue());

    }

}