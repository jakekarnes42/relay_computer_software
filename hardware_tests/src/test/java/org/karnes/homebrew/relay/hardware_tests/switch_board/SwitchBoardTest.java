package org.karnes.homebrew.relay.hardware_tests.switch_board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.BidirectionalSignalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.ReadableSignalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwitchBoardTest {

    private SwitchBoard board;
    private ReadableSignalConnection readConnection;
    private BidirectionalSignalConnection bidirectionalConnection;
    private WritableSignalConnection writeConnection;

    @BeforeEach
    void setUp() {
        board = new VirtualSwitchBoard();
        //board = new HardwareSwitchBoard();
        readConnection = board.getReadConnection();
        bidirectionalConnection = board.getBidirectionalConnection();
        writeConnection = board.getWriteConnection();
    }


    @Test
    public void readOnlyTest() {
        //If you turn on Switch 0, LED 0 should turn on.
        board.enableSwitch0();
        assertTrue(board.LED0Status());

        //We should be able to read that the switch is on.
        assertTrue(readConnection.readSignal());

        //turn it back off
        board.disableSwitch0();

        //LED should be off
        assertFalse(board.LED0Status());

        //We should be able to read that the switch is off
        assertFalse(readConnection.readSignal());
    }

    @Test
    public void writeOnlyTest() {
        //LED 1 should start as off.
        assertFalse(board.LED1Status());

        //We should be able to turn LED1  on
        writeConnection.writeSignal(true);

        //LED 1 should be on now
        assertTrue(board.LED1Status());

        //We should be able to turn LED1 back off
        writeConnection.writeSignal(false);

        //LED 1 should be off again
        assertFalse(board.LED1Status());
    }

    @Test
    public void bidirectionalTest() {
        //LED 2 should start as off.
        assertFalse(board.LED2Status());

        //We flip switch 1 on
        board.enableSwitch1();

        //LED 2 should be on now
        assertTrue(board.LED2Status());

        //We should be able to read that the switch is on
        assertTrue(bidirectionalConnection.readSignal());

        //We flip switch 1 off
        board.disableSwitch1();

        //LED 2 should be off now
        assertFalse(board.LED2Status());

        //We should be able to read that the switch is off
        assertFalse(bidirectionalConnection.readSignal());

        //We should be able to turn the LED on by writing to it
        bidirectionalConnection.writeSignal(true);

        //LED 2 should be on now
        assertTrue(board.LED2Status());

        //We should be able to read that the LED is on
        assertTrue(bidirectionalConnection.readSignal());

        //We should be able to turn the LED off again by writing to it
        bidirectionalConnection.writeSignal(false);

        //LED 2 should be off now
        assertFalse(board.LED2Status());

        //We should be able to read that the LED is off
        assertFalse(bidirectionalConnection.readSignal());

        //We should be able to turn on both the switch and write to it
        board.enableSwitch1();
        bidirectionalConnection.writeSignal(true);

        //LED 2 should be on now
        assertTrue(board.LED2Status());

        //We should be able to read that the LED is on
        assertTrue(bidirectionalConnection.readSignal());

        //If we turn the switch off, the LED should stay on
        board.disableSwitch1();

        //LED 2 should still be on
        assertTrue(board.LED2Status());

        //We should still be able to read that the LED is on
        assertTrue(bidirectionalConnection.readSignal());

        //We'll turn the switch on, and turn the write off
        board.enableSwitch1();
        bidirectionalConnection.writeSignal(false);

        //LED 2 should still be on
        assertTrue(board.LED2Status());

        //We should still be able to read that the LED is on
        assertTrue(bidirectionalConnection.readSignal());

        //We flip switch 1 off one last time
        board.disableSwitch1();

        //LED 2 should be off now
        assertFalse(board.LED2Status());

    }


}
