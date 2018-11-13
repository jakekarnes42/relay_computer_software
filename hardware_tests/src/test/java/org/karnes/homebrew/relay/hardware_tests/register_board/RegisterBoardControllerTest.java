package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.test.TestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.karnes.homebrew.relay.hardware_tests.register_board.RegisterBoardBus.ADDRESS;
import static org.karnes.homebrew.relay.hardware_tests.register_board.RegisterBoardBus.DATA;

class RegisterBoardControllerTest {
    private static final int WIDTH = 2;
    private static final FixedBitSet ZERO_VAL = new FixedBitSet(WIDTH);
    private static final FixedBitSet ONES_VAL = FixedBitSet.allOnes(WIDTH);
    private static final List<FixedBitSet> allValues = List.of(
            new FixedBitSet("00"),
            new FixedBitSet("01"),
            new FixedBitSet("10"),
            new FixedBitSet("11")
    );

    RegisterBoard board = new VirtualRegisterBoard();
    RegisterBoardController registerBoardController = new RegisterBoardController(board);


    @Test
    void simpleGetSetClearRegisterATest() {
        //Get the value before
        FixedBitSet before = registerBoardController.getRegisterAValue(ADDRESS);

        //There should be no existing value
        assertEquals(ZERO_VAL, before);

        //Set a new value
        registerBoardController.setRegisterAValue(ADDRESS, ONES_VAL);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterAValue(ADDRESS);

        //We should be able to read back the value we set
        assertEquals(ONES_VAL, after);

        //Clear the register
        registerBoardController.clearRegisterA();

        //Get the value after the clear
        FixedBitSet cleared = registerBoardController.getRegisterAValue(ADDRESS);

        //There should be no value after the clear
        assertEquals(ZERO_VAL, cleared);
    }

    @Test
    void simpleGetSetClearRegisterBTest() {
        //Get the value before
        FixedBitSet before = registerBoardController.getRegisterBValue(DATA);

        //There should be no existing value
        assertEquals(ZERO_VAL, before);

        //Set a new value
        registerBoardController.setRegisterBValue(DATA, ONES_VAL);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterBValue(DATA);

        //We should be able to read back the value we set
        assertEquals(ONES_VAL, after);

        //Clear the register
        registerBoardController.clearRegisterB();

        //Get the value after the clear
        FixedBitSet cleared = registerBoardController.getRegisterBValue(DATA);

        //There should be no value after the clear
        assertEquals(ZERO_VAL, cleared);
    }

    @ParameterizedTest
    @MethodSource("getValueBusPairs")
    void testGetSetClearAllRegisterA(FixedBitSet value, RegisterBoardBus bus) {
        //Set a new value
        registerBoardController.setRegisterAValue(bus, value);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterAValue(bus);

        //We should be able to read back the value we set
        assertEquals(value, after);

        //Clear the register
        registerBoardController.clearRegisterA();

        //Get the value after the clear
        FixedBitSet cleared = registerBoardController.getRegisterAValue(bus);

        //There should be no value after the clear
        assertEquals(ZERO_VAL, cleared);
    }

    @ParameterizedTest
    @MethodSource("getValueBusPairs")
    void testGetSetClearAllRegisterB(FixedBitSet value, RegisterBoardBus bus) {
        //Set a new value
        registerBoardController.setRegisterBValue(bus, value);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterBValue(bus);

        //We should be able to read back the value we set
        assertEquals(value, after);

        //Clear the register
        registerBoardController.clearRegisterB();

        //Get the value after the clear
        FixedBitSet cleared = registerBoardController.getRegisterBValue(bus);

        //There should be no value after the clear
        assertEquals(ZERO_VAL, cleared);
    }

    @ParameterizedTest
    @MethodSource("getValueBusPairs")
    void testMovAtoB(FixedBitSet value, RegisterBoardBus bus) {
        //Set a new value
        registerBoardController.setRegisterAValue(bus, value);

        //Move from A to B
        registerBoardController.moveAtoB(bus);

        //Get the value from B
        FixedBitSet after = registerBoardController.getRegisterBValue(bus);

        //We should be able to read back the value we set
        assertEquals(value, after);

        //The value should still be in A
        after = registerBoardController.getRegisterAValue(bus);
        assertEquals(value, after);

        //Clear the registers
        registerBoardController.clearRegisterA();
        registerBoardController.clearRegisterB();

    }

    @ParameterizedTest
    @MethodSource("getValueBusPairs")
    void testMovBtoA(FixedBitSet value, RegisterBoardBus bus) {
        //Set a new value
        registerBoardController.setRegisterBValue(bus, value);

        //Move from B to A
        registerBoardController.moveBtoA(bus);

        //Get the value from A
        FixedBitSet after = registerBoardController.getRegisterAValue(bus);

        //We should be able to read back the value we set
        assertEquals(value, after);

        //The value should still be in B
        after = registerBoardController.getRegisterBValue(bus);
        assertEquals(value, after);

        //Clear the registers
        registerBoardController.clearRegisterA();
        registerBoardController.clearRegisterB();

    }

    @ParameterizedTest
    @MethodSource("allValues")
    void testGetRegisterABothBuses(FixedBitSet value) {
        //Set a new value
        registerBoardController.setRegisterAValue(ADDRESS, value);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterABothBuses();

        //We should be able to read back the value we set
        assertEquals(value, after);

        //Clear the register
        registerBoardController.clearRegisterA();
    }

    @ParameterizedTest
    @MethodSource("allValues")
    void testGetRegisterBBothBuses(FixedBitSet value) {
        //Set a new value
        registerBoardController.setRegisterBValue(DATA, value);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterBBothBuses();

        //We should be able to read back the value we set
        assertEquals(value, after);

        //Clear the register
        registerBoardController.clearRegisterB();
    }

    @ParameterizedTest
    @MethodSource("allValueCombinations")
    void testSetGetClearBothRegisterValues(FixedBitSet aValue, FixedBitSet bValue) {
        //Set both values
        registerBoardController.setBothRegisterValues(aValue, bValue);

        //Get the values back out
        FixedBitSet[] bothRegisterValues = registerBoardController.getBothRegisterValues();

        //Check that they are the values we set
        assertEquals(aValue, bothRegisterValues[0]);
        assertEquals(bValue, bothRegisterValues[1]);

        //Clear the values
        registerBoardController.clearBothRegisters();

        //Get the values back out
        bothRegisterValues = registerBoardController.getBothRegisterValues();

        //Check that they were cleared
        assertEquals(ZERO_VAL, bothRegisterValues[0]);
        assertEquals(ZERO_VAL, bothRegisterValues[1]);


    }


    private static List<Arguments> allValues() {
        return TestUtil.cartesianProduct(
                allValues
        );
    }

    private static List<Arguments> getValueBusPairs() {
        return TestUtil.cartesianProduct(
                allValues,
                List.of(ADDRESS, DATA)
        );
    }

    private static List<Arguments> allValueCombinations() {
        return TestUtil.cartesianProduct(
                allValues,
                allValues
        );
    }
}