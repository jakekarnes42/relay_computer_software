package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.bus.BusName;
import org.karnes.homebrew.relay.common.test.TestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.karnes.homebrew.relay.common.emulator.component.bus.BusName.ADDRESS;
import static org.karnes.homebrew.relay.common.emulator.component.bus.BusName.DATA;
import static org.karnes.homebrew.relay.hardware_tests.register_board.RegisterName.A;
import static org.karnes.homebrew.relay.hardware_tests.register_board.RegisterName.B;

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

    private RegisterBoard board = new VirtualRegisterBoard();
    private RegisterBoardController registerBoardController = new RegisterBoardController(board);


    @Test
    void simpleGetSetClearRegisterATest() {
        //Get the value before
        FixedBitSet before = registerBoardController.getRegisterValue(A, ADDRESS);

        //There should be no existing value
        assertEquals(ZERO_VAL, before);

        //Set a new value
        registerBoardController.setRegisterValue(A, ADDRESS, ONES_VAL);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterValue(A, ADDRESS);

        //We should be able to read back the value we set
        assertEquals(ONES_VAL, after);

        //Clear the register
        registerBoardController.clearRegister(A);

        //Get the value after the clear
        FixedBitSet cleared = registerBoardController.getRegisterValue(A, ADDRESS);

        //There should be no value after the clear
        assertEquals(ZERO_VAL, cleared);
    }

    @Test
    void simpleGetSetClearRegisterBTest() {
        //Get the value before
        FixedBitSet before = registerBoardController.getRegisterValue(B, DATA);

        //There should be no existing value
        assertEquals(ZERO_VAL, before);

        //Set a new value
        registerBoardController.setRegisterValue(B, DATA, ONES_VAL);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterValue(B, DATA);

        //We should be able to read back the value we set
        assertEquals(ONES_VAL, after);

        //Clear the register
        registerBoardController.clearRegister(B);

        //Get the value after the clear
        FixedBitSet cleared = registerBoardController.getRegisterValue(B, DATA);

        //There should be no value after the clear
        assertEquals(ZERO_VAL, cleared);
    }

    @ParameterizedTest
    @MethodSource("getRegisterBusValueCombos")
    void testGetSetClearAll(RegisterName reg, BusName bus, FixedBitSet value) {
        //Set a new value
        registerBoardController.setRegisterValue(reg, bus, value);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterValue(reg, bus);

        //We should be able to read back the value we set
        assertEquals(value, after);

        //Clear the register
        registerBoardController.clearRegister(reg);

        //Get the value after the clear
        FixedBitSet cleared = registerBoardController.getRegisterValue(reg, bus);

        //There should be no value after the clear
        assertEquals(ZERO_VAL, cleared);
    }


    @ParameterizedTest
    @MethodSource("getRegisterRegisterBusValueCombos")
    void testMoves(RegisterName src, RegisterName dst, BusName bus, FixedBitSet value) {
        //Set a new value
        registerBoardController.setRegisterValue(src, bus, value);

        //Move from A to B
        registerBoardController.moveValue(src, dst, bus);

        //Check if src and dst are equal
        if (src == dst) {
            //The move should clear the src register

            //Get the value from src
            FixedBitSet after = registerBoardController.getRegisterValue(src, bus);

            //The register should be cleared
            assertEquals(new FixedBitSet(WIDTH), after);

        } else {
            //The move should put the src value in dst, while leaving dst alone

            //Get the value from dst
            FixedBitSet after = registerBoardController.getRegisterValue(dst, bus);

            //We should be able to read back the value we set
            assertEquals(value, after);

            //The value should still be in src
            after = registerBoardController.getRegisterValue(src, bus);
            assertEquals(value, after);

            //Clear the registers
            registerBoardController.clearBothRegisters();
        }


    }


    @ParameterizedTest
    @MethodSource("getRegisterBusValueCombos")
    void testGetRegisterBothBuses(RegisterName reg, BusName bus, FixedBitSet value) {
        //Set a new value
        registerBoardController.setRegisterValue(reg, bus, value);

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterBothBuses(reg);

        //We should be able to read back the value we set
        assertEquals(value, after);

        //Clear the register
        registerBoardController.clearRegister(reg);
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


    private static List<Arguments> getRegisterRegisterBusValueCombos() {
        return TestUtil.cartesianProduct(
                List.of(A, B),
                List.of(A, B),
                List.of(ADDRESS, DATA),
                allValues
        );
    }

    private static List<Arguments> getRegisterBusValueCombos() {
        return TestUtil.cartesianProduct(
                List.of(A, B),
                List.of(ADDRESS, DATA),
                allValues
        );
    }

    private static List<Arguments> allValueCombinations() {
        return TestUtil.cartesianProduct(
                allValues,
                allValues
        );
    }
}