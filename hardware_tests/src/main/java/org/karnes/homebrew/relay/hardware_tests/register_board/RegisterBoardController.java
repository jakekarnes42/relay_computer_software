package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnection;

import static org.karnes.homebrew.relay.hardware_tests.register_board.RegisterBoardBus.ADDRESS;

/**
 * Controls a register board, providing a nicer interface to available operations
 */
public class RegisterBoardController {

    private final int WIDTH = 2;
    private final int delay;

    //Signals
    private WritableSignalConnection registerASelectA;
    private WritableSignalConnection registerALoadA;
    private WritableSignalConnection registerASelectD;
    private WritableSignalConnection registerALoadD;

    private WritableSignalConnection registerBSelectA;
    private WritableSignalConnection registerBLoadA;
    private WritableSignalConnection registerBSelectD;
    private WritableSignalConnection registerBLoadD;

    //Bus Connections
    private BidirectionalConnection addressBusConnection;
    private BidirectionalConnection dataBusConnection;

    public RegisterBoardController(RegisterBoard board) {
        registerASelectA = board.getRegisterASelectA();
        registerALoadA = board.getRegisterALoadA();
        registerASelectD = board.getRegisterASelectD();
        registerALoadD = board.getRegisterALoadD();

        registerBSelectA = board.getRegisterBSelectA();
        registerBLoadA = board.getRegisterBLoadA();
        registerBSelectD = board.getRegisterBSelectD();
        registerBLoadD = board.getRegisterBLoadD();

        addressBusConnection = board.getAddressBusConnection();
        dataBusConnection = board.getDataBusConnection();

        delay = board.getDelay();
    }

    /**
     * Gets the current value in Register A via the specified bus.
     *
     * @param busType which bus to use to get the value.
     * @return The current value in Register A.
     */
    public FixedBitSet getRegisterAValue(RegisterBoardBus busType) {
        FixedBitSet value = null;
        //Check which Bus we're going to be using
        if (busType == ADDRESS) {
            //We're using the address bus
            //Set SELECT_A HIGH
            registerASelectA.writeSignal(true);
            pause();

            //Read the value
            value = addressBusConnection.readValue();

            //Set the signal low again
            registerASelectA.writeSignal(false);
            pause();
        } else {
            //We're using the data bus
            //Set SELECT_A HIGH
            registerASelectD.writeSignal(true);
            pause();

            //Read the value
            value = dataBusConnection.readValue();

            //Set the signal low again
            registerASelectD.writeSignal(false);
            pause();
        }

        return value;
    }

    /**
     * Gets the value in register A by reading from both buses. There is no real advantage to this. Its primary
     * purpose is to allow for easy validation that we can select onto both values at once.
     *
     * @return the value in register A.
     */
    public FixedBitSet getRegisterABothBuses() {
        //Set select signals HIGH
        registerASelectA.writeSignal(true);
        registerASelectD.writeSignal(true);
        pause();

        //Read the value
        FixedBitSet addressValue = addressBusConnection.readValue();
        FixedBitSet dataValue = dataBusConnection.readValue();

        //Set the signal low again
        registerASelectA.writeSignal(false);
        registerASelectD.writeSignal(false);
        pause();

        //Check that the values match
        if (!addressValue.equals(dataValue)) {
            throw new IllegalStateException("The address value (" + addressValue + ") and data value (" + dataValue + ") " +
                    "should match");
        } else {
            //They both match, just return whichever. 
            return addressValue;
        }
    }

    /**
     * Gets the current value in Register B via the specified bus.
     *
     * @param busType which bus to use to get the value.
     * @return The current value in B.
     */
    public FixedBitSet getRegisterBValue(RegisterBoardBus busType) {
        FixedBitSet value = null;
        //Check which Bus we're going to be using
        if (busType == ADDRESS) {
            //We're using the address bus
            //Set SELECT_A HIGH
            registerBSelectA.writeSignal(true);
            pause();

            //Read the value
            value = addressBusConnection.readValue();

            //Set the signal low again
            registerBSelectA.writeSignal(false);
            pause();
        } else {
            //We're using the data bus
            //Set SELECT_A HIGH
            registerBSelectD.writeSignal(true);
            pause();

            //Read the value
            value = dataBusConnection.readValue();

            //Set the signal low again
            registerBSelectD.writeSignal(false);
            pause();
        }

        return value;
    }

    /**
     * Gets the value in register B by reading from both buses. There is no real advantage to this. Its primary
     * purpose is to allow for easy validation that we can select onto both values at once.
     *
     * @return the value in register B.
     */
    public FixedBitSet getRegisterBBothBuses() {
        //Set select signals HIGH
        registerBSelectA.writeSignal(true);
        registerBSelectD.writeSignal(true);
        pause();

        //Read the value
        FixedBitSet addressValue = addressBusConnection.readValue();
        FixedBitSet dataValue = dataBusConnection.readValue();

        //Set the signal low again
        registerBSelectA.writeSignal(false);
        registerBSelectD.writeSignal(false);
        pause();

        //Check that the values match
        if (!addressValue.equals(dataValue)) {
            throw new IllegalStateException("The address value (" + addressValue + ") and data value (" + dataValue + ") " +
                    "should match");
        } else {
            //They both match, just return whichever. 
            return addressValue;
        }
    }


    /**
     * Gets the values of both registers at once, using both buses simultaneously. This is faster than calling
     * {@link #getRegisterAValue(RegisterBoardBus)} and {@link #getRegisterBValue(RegisterBoardBus)}
     * individually.
     *
     * @return An array of FixedBitSets where the value of register A is in position 0 and the value of register B is
     * in position 1.
     */
    public FixedBitSet[] getBothRegisterValues() {
        //Start outputting register A on the address bus, and register B on the data bus
        registerASelectA.writeSignal(true);
        registerBSelectD.writeSignal(true);
        pause();

        //Read the values
        FixedBitSet registerAValue = addressBusConnection.readValue();
        FixedBitSet registerBValue = dataBusConnection.readValue();

        //Set the signals low again
        registerASelectA.writeSignal(false);
        registerBSelectD.writeSignal(false);
        pause();

        return new FixedBitSet[]{registerAValue, registerBValue};

    }

    /**
     * Sets the value of register A using the specified bus.
     *
     * @param busType which bus to use to set the value.
     * @param value   the new value.
     */
    public void setRegisterAValue(RegisterBoardBus busType, FixedBitSet value) {
        //Check which Bus we're going to be using
        if (busType == ADDRESS) {
            //We're using the address bus
            //Put the value on the bus and set the LOAD signal HIGH
            addressBusConnection.writeValue(value);
            registerALoadA.writeSignal(true);
            pause();

            //Set the signal low again. The falling edge latches the value
            registerALoadA.writeSignal(false);
            pause();

            //Stop writing the value to the bus.
            addressBusConnection.writeValue(new FixedBitSet(WIDTH));
            pause();
        } else {
            //We're using the data bus
            //Put the value on the bus and set the LOAD signal HIGH
            dataBusConnection.writeValue(value);
            registerALoadD.writeSignal(true);
            pause();

            //Set the signal low again. The falling edge latches the value
            registerALoadD.writeSignal(false);
            pause();

            //Stop writing the value to the bus.
            dataBusConnection.writeValue(new FixedBitSet(WIDTH));
            pause();
        }
    }

    /**
     * Sets the value of register B using the specified bus.
     *
     * @param busType which bus to use to set the value.
     * @param value   the new value.
     */
    public void setRegisterBValue(RegisterBoardBus busType, FixedBitSet value) {
        //Check which Bus we're going to be using
        if (busType == ADDRESS) {
            //We're using the address bus
            //Put the value on the bus and set the LOAD signal HIGH
            addressBusConnection.writeValue(value);
            registerBLoadA.writeSignal(true);
            pause();

            //Set the signal low again. The falling edge latches the value
            registerBLoadA.writeSignal(false);
            pause();

            //Stop writing the value to the bus.
            addressBusConnection.writeValue(new FixedBitSet(WIDTH));
            pause();
        } else {
            //We're using the data bus
            //Put the value on the bus and set the LOAD signal HIGH
            dataBusConnection.writeValue(value);
            registerBLoadD.writeSignal(true);
            pause();

            //Set the signal low again. The falling edge latches the value
            registerBLoadD.writeSignal(false);
            pause();

            //Stop writing the value to the bus.
            dataBusConnection.writeValue(new FixedBitSet(WIDTH));
            pause();
        }
    }

    /**
     * Sets both register values at once, using both buses. This is faster than calling
     * {@link #setRegisterAValue(RegisterBoardBus, FixedBitSet)} then
     * {@link #setRegisterBValue(RegisterBoardBus, FixedBitSet)} individually.
     *
     * @param registerAValue the new value for register A.
     * @param registerBValue the new value for register B.
     */
    public void setBothRegisterValues(FixedBitSet registerAValue, FixedBitSet registerBValue) {
        //Put register A's value on the address bus, and register B's value on the data bus
        addressBusConnection.writeValue(registerAValue);
        dataBusConnection.writeValue(registerBValue);
        //Set the load signals HIGH
        registerALoadA.writeSignal(true);
        registerBLoadD.writeSignal(true);
        pause();

        //Set the signals low again, latching the value
        registerALoadA.writeSignal(false);
        registerBLoadD.writeSignal(false);
        pause();

        //Stop writing the values onto the buses
        addressBusConnection.writeValue(new FixedBitSet(WIDTH));
        dataBusConnection.writeValue(new FixedBitSet(WIDTH));
        pause();
    }

    /**
     * Clears the value in register A
     */
    public void clearRegisterA() {
        /*
         * Note: this method COULD just use the load signal, but I'm trying to test what the instruction decoder will
         * likely produce.
         */

        //Set the SELECT and the LOAD signals high at the same time
        registerASelectA.writeSignal(true);
        registerALoadA.writeSignal(true);
        pause();

        //Set the load signal low, latching the empty value
        registerALoadA.writeSignal(false);
        pause();

        //Set the select signal low
        registerASelectA.writeSignal(false);
        pause();
    }

    /**
     * Clears the value in register B
     */
    public void clearRegisterB() {
        /*
         * Note: this method COULD just use the load signal, but I'm trying to test what the instruction decoder will
         * likely produce.
         */

        //Set the SELECT and the LOAD signals high at the same time
        registerBSelectD.writeSignal(true);
        registerBLoadD.writeSignal(true);
        pause();

        //Set the load signal low, latching the empty value
        registerBLoadD.writeSignal(false);
        pause();

        //Set the select signal low
        registerBSelectD.writeSignal(false);
        pause();
    }

    /**
     * Clears both registers at once
     */
    public void clearBothRegisters() {

        /*
         * Note: this method COULD just use the load signal, but I'm trying to test what the instruction decoder will
         * likely produce.
         */

        //Set the SELECT and the LOAD signals high at the same time
        registerASelectA.writeSignal(true);
        registerALoadA.writeSignal(true);
        registerBSelectD.writeSignal(true);
        registerBLoadD.writeSignal(true);
        pause();

        //Set the load signal low, latching the empty value
        registerALoadA.writeSignal(false);
        registerBLoadD.writeSignal(false);
        pause();

        //Set the select signal low
        registerASelectA.writeSignal(false);
        registerBSelectD.writeSignal(false);
        pause();

    }


    /**
     * Moves the current value in Register A into Register B, without affecting Register's A value.
     *
     * @param busType which bus to use for the move.
     */
    public void moveAtoB(RegisterBoardBus busType) {
        //Check which Bus we're going to be using
        if (busType == ADDRESS) {
            //We're using the address bus
            //Put the value on the bus and set the LOAD signal HIGH
            registerASelectA.writeSignal(true);
            registerBLoadA.writeSignal(true);
            pause();

            //Set the signal low again. The falling edge latches the value
            registerBLoadA.writeSignal(false);
            pause();

            //Stop writing the value to the bus.
            registerASelectA.writeSignal(false);
            pause();
        } else {
            //We're using the data bus
            //Put the value on the bus and set the LOAD signal HIGH
            registerASelectD.writeSignal(true);
            registerBLoadD.writeSignal(true);
            pause();

            //Set the signal low again. The falling edge latches the value
            registerBLoadD.writeSignal(false);
            pause();

            //Stop writing the value to the bus.
            registerASelectD.writeSignal(false);
            pause();
        }
    }

    /**
     * Moves the current value in Register B into Register A, without affecting Register's B value.
     *
     * @param busType which bus to use for the move.
     */
    public void moveBtoA(RegisterBoardBus busType) {
        //Check which Bus we're going to be using
        if (busType == ADDRESS) {
            //We're using the address bus
            //Put the value on the bus and set the LOAD signal HIGH
            registerBSelectA.writeSignal(true);
            registerALoadA.writeSignal(true);
            pause();

            //Set the signal low again. The falling edge latches the value
            registerALoadA.writeSignal(false);
            pause();

            //Stop writing the value to the bus.
            registerBSelectA.writeSignal(false);
            pause();
        } else {
            //We're using the data bus
            //Put the value on the bus and set the LOAD signal HIGH
            registerBSelectD.writeSignal(true);
            registerALoadD.writeSignal(true);
            pause();

            //Set the signal low again. The falling edge latches the value
            registerALoadD.writeSignal(false);
            pause();

            //Stop writing the value to the bus.
            registerBSelectD.writeSignal(false);
            pause();
        }
    }


    private void pause() {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted while paused", e);
            }
        }
    }
}
