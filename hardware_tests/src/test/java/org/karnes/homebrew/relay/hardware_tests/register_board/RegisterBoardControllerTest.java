package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterBoardControllerTest {
    private final int WIDTH = 2;
    RegisterBoard board = new VirtualRegisterBoard();
    RegisterBoardController registerBoardController = new RegisterBoardController(board);


    @Test
    void simpleGetSetClearRegisterATest() {
        //Get the value before
        FixedBitSet before = registerBoardController.getRegisterAValue(RegisterBoardBusType.ADDRESS);

        //There should be no existing value
        assertEquals(new FixedBitSet(WIDTH), before);

        //Set a new value
        registerBoardController.setRegisterAValue(RegisterBoardBusType.ADDRESS, FixedBitSet.allOnes(WIDTH));

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterAValue(RegisterBoardBusType.ADDRESS);

        //We should be able to read back the value we set
        assertEquals(FixedBitSet.allOnes(WIDTH), after);

        //Clear the register
        registerBoardController.clearRegisterA();

        //Get the value after the clear
        FixedBitSet cleared = registerBoardController.getRegisterAValue(RegisterBoardBusType.ADDRESS);

        //There should be no value after the clear
        assertEquals(new FixedBitSet(WIDTH), cleared);
    }

    @Test
    void simpleGetSetClearRegisterBTest() {
        //Get the value before
        FixedBitSet before = registerBoardController.getRegisterBValue(RegisterBoardBusType.DATA);

        //There should be no existing value
        assertEquals(new FixedBitSet(WIDTH), before);

        //Set a new value
        registerBoardController.setRegisterBValue(RegisterBoardBusType.DATA, FixedBitSet.allOnes(WIDTH));

        //Get the value again
        FixedBitSet after = registerBoardController.getRegisterBValue(RegisterBoardBusType.DATA);

        //We should be able to read back the value we set
        assertEquals(FixedBitSet.allOnes(WIDTH), after);

        //Clear the register
        registerBoardController.clearRegisterB();

        //Get the value after the clear
        FixedBitSet cleared = registerBoardController.getRegisterBValue(RegisterBoardBusType.DATA);

        //There should be no value after the clear
        assertEquals(new FixedBitSet(WIDTH), cleared);
    }

//    @ParameterizedTest
//    void test() {
//
//    }
}