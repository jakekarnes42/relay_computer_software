package org.karnes.homebrew.hardware.switch_board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WritableConnection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwitchBoardTest {

    private SwitchBoard board;
    private ReadableConnection readConnection;
    private BidirectionalConnection bidirectionalConnection;
    private WritableConnection writeConnection;

    @BeforeEach
    void setUp() {
        board = new VirtualSwitchBoard();
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
        FixedBitSet readValue = readConnection.readValue();
        assertTrue(readValue.get(0));

        //turn it back off
        board.disableSwitch0();

        //LED should be off
        assertFalse(board.LED0Status());

        //We should be able to read that the switch is off
        readValue = readConnection.readValue();
        assertFalse(readValue.get(0));
    }

    @Test
    public void writeOnlyTest() {
        //LED 1 should start as off.
        assertFalse(board.LED1Status());

        //We should be able to turn LED1  on
        writeConnection.writeValue(new FixedBitSet("1"));

        //LED 1 should be on now
        assertTrue(board.LED1Status());

        //We should be able to turn LED1 back off
        writeConnection.writeValue(new FixedBitSet("0"));

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
        FixedBitSet readValue = bidirectionalConnection.readValue();
        assertTrue(readValue.get(0));

        //We flip switch 1 off
        board.disableSwitch1();

        //LED 2 should be off now
        assertFalse(board.LED2Status());

        //We should be able to read that the switch is off
        readValue = bidirectionalConnection.readValue();
        assertFalse(readValue.get(0));

        //We should be able to turn the LED on by writing to it
        bidirectionalConnection.writeValue(new FixedBitSet("1"));

        //LED 2 should be on now
        assertTrue(board.LED2Status());

        //We should be able to read that the LED is on
        readValue = bidirectionalConnection.readValue();
        assertTrue(readValue.get(0));

        //We should be able to turn the LED off again by writing to it
        bidirectionalConnection.writeValue(new FixedBitSet("0"));

        //LED 2 should be off now
        assertFalse(board.LED2Status());

        //We should be able to read that the LED is off
        readValue = bidirectionalConnection.readValue();
        assertFalse(readValue.get(0));

        //We should be able to turn on both the switch and write to it
        board.enableSwitch1();
        bidirectionalConnection.writeValue(new FixedBitSet("1"));

        //LED 2 should be on now
        assertTrue(board.LED2Status());

        //We should be able to read that the LED is on
        readValue = bidirectionalConnection.readValue();
        assertTrue(readValue.get(0));

        //If we turn the switch off, the LED should stay on
        board.disableSwitch1();

        //LED 2 should still be on
        assertTrue(board.LED2Status());

        //We should still be able to read that the LED is on
        readValue = bidirectionalConnection.readValue();
        assertTrue(readValue.get(0));

        //We'll turn the switch on, and turn the write off
        board.enableSwitch1();
        bidirectionalConnection.writeValue(new FixedBitSet("0"));

        //LED 2 should still be on
        assertTrue(board.LED2Status());

        //We should still be able to read that the LED is on
        readValue = bidirectionalConnection.readValue();
        assertTrue(readValue.get(0));

        //We flip switch 1 off one last time
        board.disableSwitch1();

        //LED 2 should be off now
        assertFalse(board.LED2Status());

    }


}
