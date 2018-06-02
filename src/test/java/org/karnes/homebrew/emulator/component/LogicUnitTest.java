package org.karnes.homebrew.emulator.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.karnes.homebrew.bitset.ArbitraryBitSet;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LogicUnitTest {
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
        Bus tmp1Bus = new Bus("TMP1-ALU_BUS", 3);
        tmp1BusConnection = new BusConnection(tmp1Bus);
        Bus tmp2Bus = new Bus("TMP2-ALU_BUS", 3);
        tmp2BusConnection = new BusConnection(tmp2Bus);
        Bus outputBus = new Bus("LU_OUTPUT_BUS", 3);
        outputBusConnection = new BusConnection(outputBus);
        Bus ccBus = new Bus("CC_BUS", 4);
        ccBusConnection = new BusConnection(ccBus);

        logicUnit = new LogicUnit(luOperationBus, tmp1Bus, tmp2Bus, outputBus, ccBus);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void simpleTest() {
        //The LU is already hooked up.
        //Enable the output. This should be LU Enable + XOR (0,0)
        luOperationBusConnection.setToBusOutput(new ArbitraryBitSet("100"));

        //The output should be 0.
        assertEquals(new ArbitraryBitSet(3), outputBusConnection.getBusValue());

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
    @MethodSource("someXORs")
    void testSomeXORs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
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

    private static Stream<Arguments> someXORs() {
        return Stream.of(
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("000"), new ArbitraryBitSet("101"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("001"), new ArbitraryBitSet("100"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("010"), new ArbitraryBitSet("111"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("011"), new ArbitraryBitSet("110"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("100"), new ArbitraryBitSet("001"), new ArbitraryBitSet("0000")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("101"), new ArbitraryBitSet("000"), new ArbitraryBitSet("0001")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("110"), new ArbitraryBitSet("011"), new ArbitraryBitSet("0000")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("111"), new ArbitraryBitSet("010"), new ArbitraryBitSet("0000"))
        );
    }

    @ParameterizedTest
    @MethodSource("someORs")
    void testSomeORs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
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

    private static Stream<Arguments> someORs() {
        return Stream.of(
                Arguments.of(new ArbitraryBitSet("000"), new ArbitraryBitSet("000"), new ArbitraryBitSet("000"), new ArbitraryBitSet("0001")),
                Arguments.of(new ArbitraryBitSet("001"), new ArbitraryBitSet("001"), new ArbitraryBitSet("001"), new ArbitraryBitSet("0000")),
                Arguments.of(new ArbitraryBitSet("100"), new ArbitraryBitSet("000"), new ArbitraryBitSet("100"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("111"), new ArbitraryBitSet("011"), new ArbitraryBitSet("111"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("100"), new ArbitraryBitSet("101"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("110"), new ArbitraryBitSet("101"), new ArbitraryBitSet("111"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("110"), new ArbitraryBitSet("111"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("111"), new ArbitraryBitSet("111"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("111"), new ArbitraryBitSet("111"), new ArbitraryBitSet("111"), new ArbitraryBitSet("0010"))
        );
    }

    @ParameterizedTest
    @MethodSource("someANDs")
    void testSomeANDs(FixedBitSet a, FixedBitSet b, FixedBitSet result, FixedBitSet cc) {
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

    private static Stream<Arguments> someANDs() {
        return Stream.of(
                Arguments.of(new ArbitraryBitSet("000"), new ArbitraryBitSet("000"), new ArbitraryBitSet("000"), new ArbitraryBitSet("0001")),
                Arguments.of(new ArbitraryBitSet("001"), new ArbitraryBitSet("001"), new ArbitraryBitSet("001"), new ArbitraryBitSet("0000")),
                Arguments.of(new ArbitraryBitSet("100"), new ArbitraryBitSet("000"), new ArbitraryBitSet("000"), new ArbitraryBitSet("0001")),
                Arguments.of(new ArbitraryBitSet("111"), new ArbitraryBitSet("011"), new ArbitraryBitSet("011"), new ArbitraryBitSet("0000")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("100"), new ArbitraryBitSet("100"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("110"), new ArbitraryBitSet("101"), new ArbitraryBitSet("100"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("110"), new ArbitraryBitSet("100"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("111"), new ArbitraryBitSet("101"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("111"), new ArbitraryBitSet("111"), new ArbitraryBitSet("111"), new ArbitraryBitSet("0010"))
        );
    }


    @ParameterizedTest
    @MethodSource("someNots")
    void testSomeNOTs(FixedBitSet a, FixedBitSet result, FixedBitSet cc) {
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

    private static Stream<Arguments> someNots() {
        return Stream.of(
                Arguments.of(new ArbitraryBitSet("000"), new ArbitraryBitSet("111"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("001"), new ArbitraryBitSet("110"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("010"), new ArbitraryBitSet("101"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("011"), new ArbitraryBitSet("100"), new ArbitraryBitSet("0010")),
                Arguments.of(new ArbitraryBitSet("100"), new ArbitraryBitSet("011"), new ArbitraryBitSet("0000")),
                Arguments.of(new ArbitraryBitSet("101"), new ArbitraryBitSet("010"), new ArbitraryBitSet("0000")),
                Arguments.of(new ArbitraryBitSet("110"), new ArbitraryBitSet("001"), new ArbitraryBitSet("0000")),
                Arguments.of(new ArbitraryBitSet("111"), new ArbitraryBitSet("000"), new ArbitraryBitSet("0001"))

        );
    }


}