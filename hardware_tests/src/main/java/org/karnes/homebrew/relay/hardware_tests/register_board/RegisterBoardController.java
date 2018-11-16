package org.karnes.homebrew.relay.hardware_tests.register_board;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.bus.BusName;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.register.RegisterSignalSet;

import java.util.HashMap;
import java.util.Map;

import static org.karnes.homebrew.relay.common.emulator.component.bus.BusName.ADDRESS;
import static org.karnes.homebrew.relay.common.emulator.component.bus.BusName.DATA;
import static org.karnes.homebrew.relay.hardware_tests.register_board.RegisterName.A;
import static org.karnes.homebrew.relay.hardware_tests.register_board.RegisterName.B;

/**
 * Controls a register board, providing a nicer interface to available operations
 */
public class RegisterBoardController {

    private final int WIDTH = 2;
    private final int delay;

    //Register Map
    Map<RegisterName, RegisterSignalSet> registerMap = new HashMap<>();

    //Bus Map
    Map<BusName, BidirectionalConnection> busMap = new HashMap<>();

    //Bus Connections
    private BidirectionalConnection addressBusConnection;
    private BidirectionalConnection dataBusConnection;

    /**
     * Creates a new controller for the given register board
     *
     * @param board The board to control
     */
    public RegisterBoardController(RegisterBoard board) {
        //Save our signals for register A
        var registerASignalSet = board.getRegisterASignalSet();
        registerMap.put(A, registerASignalSet);

        //Save our signals for register B
        var registerBSignalSet = board.getRegisterBSignalSet();
        registerMap.put(B, registerBSignalSet);

        //Save address bus connection
        addressBusConnection = board.getAddressBusConnection();
        busMap.put(ADDRESS, addressBusConnection);

        //Save data bus connection
        dataBusConnection = board.getDataBusConnection();
        busMap.put(DATA, dataBusConnection);

        //Get the delay specified by the board
        delay = board.getDelay();
    }

    /**
     * Gets the current value in {@code reg} via the specified {@code bus}.
     *
     * @param reg which register to get the value from.
     * @param bus which bus to use to get the value.
     * @return The current value in Register A.
     */
    public FixedBitSet getRegisterValue(RegisterName reg, BusName bus) {
        //Get the signals for the specified register
        RegisterSignalSet signals = registerMap.get(reg);

        //Set the SELECT signal high on the specified bus
        signals.select(bus, true);
        pause();

        //Read the value
        FixedBitSet value = busMap.get(bus).readValue();

        //Set the SELECT signal low again
        signals.select(bus, false);
        pause();

        return value;
    }

    /**
     * Gets the value in the specified register by reading from both buses. There is no real advantage to this. Its primary
     * purpose is to allow for easy validation that we can select onto both values at once.
     *
     * @return the value in {@code reg}.
     */
    public FixedBitSet getRegisterBothBuses(RegisterName reg) {
        //Get the signals for the specified register
        RegisterSignalSet signals = registerMap.get(reg);

        //Set select signals HIGH
        signals.select(ADDRESS, true);
        signals.select(DATA, true);
        pause();

        //Read the value
        FixedBitSet addressValue = addressBusConnection.readValue();
        FixedBitSet dataValue = dataBusConnection.readValue();

        //Set the signal low again
        signals.select(ADDRESS, false);
        signals.select(DATA, false);
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
     * {@link  #getRegisterValue(RegisterName, BusName)} for both registers individually.
     *
     * @return An array of FixedBitSets where the value of register A is in position 0 and the value of register B is
     * in position 1.
     */
    public FixedBitSet[] getBothRegisterValues() {
        //Get the signals for the both registers
        RegisterSignalSet aSignals = registerMap.get(A);
        RegisterSignalSet bSignals = registerMap.get(B);

        //Start outputting register A on the address bus, and register B on the data bus
        aSignals.select(ADDRESS, true);
        bSignals.select(DATA, true);
        pause();

        //Read the values
        FixedBitSet registerAValue = addressBusConnection.readValue();
        FixedBitSet registerBValue = dataBusConnection.readValue();

        //Set the signals low again
        aSignals.select(ADDRESS, false);
        bSignals.select(DATA, false);
        pause();

        return new FixedBitSet[]{registerAValue, registerBValue};

    }

    /**
     * Sets the value of reg using the specified bus.
     *
     * @param reg   which register to set the value of.
     * @param bus   which bus to use to set the value.
     * @param value the new value.
     */
    public void setRegisterValue(RegisterName reg, BusName bus, FixedBitSet value) {
        //Get the signals for the specified register
        RegisterSignalSet signals = registerMap.get(reg);
        BidirectionalConnection busConnection = busMap.get(bus);

        //Put the value on the bus and set the LOAD signal HIGH
        busConnection.writeValue(value);
        signals.load(bus, true);
        pause();

        //Set the signal low again. The falling edge latches the value
        signals.load(bus, false);
        pause();

        //Stop writing the value to the bus.
        busConnection.writeValue(new FixedBitSet(WIDTH));
        pause();
    }


    /**
     * Sets both register values at once, using both buses. This is faster than calling
     * {@link #setRegisterValue(RegisterName, BusName, FixedBitSet)} for both registers individually individually.
     *
     * @param registerAValue the new value for register A.
     * @param registerBValue the new value for register B.
     */
    public void setBothRegisterValues(FixedBitSet registerAValue, FixedBitSet registerBValue) {
        //Get the signals for the both registers
        RegisterSignalSet aSignals = registerMap.get(A);
        RegisterSignalSet bSignals = registerMap.get(B);

        //Put register A's value on the address bus, and register B's value on the data bus
        addressBusConnection.writeValue(registerAValue);
        dataBusConnection.writeValue(registerBValue);

        //Set the load signals HIGH
        aSignals.load(ADDRESS, true);
        bSignals.load(DATA, true);
        pause();

        //Set the signals low again, latching the value
        aSignals.load(ADDRESS, false);
        bSignals.load(DATA, false);
        pause();

        //Stop writing the values onto the buses
        addressBusConnection.writeValue(new FixedBitSet(WIDTH));
        dataBusConnection.writeValue(new FixedBitSet(WIDTH));
        pause();
    }


    /**
     * Moves the value from the src register to the dst register using the specified bus. Note, if src and dst are the
     * same register, this will clear the register.
     *
     * @param src The register to move the value from.
     * @param dst The register to move the value into.
     * @param bus The bus to use.
     */
    public void moveValue(RegisterName src, RegisterName dst, BusName bus) {
        //Get the signals for the both registers
        RegisterSignalSet srcSignals = registerMap.get(src);
        RegisterSignalSet dstSignals = registerMap.get(dst);


        //Set the SELECT and the LOAD signals high at the same time
        srcSignals.select(bus, true);
        dstSignals.load(bus, true);
        pause();

        //Set the load signal low, latching the empty value
        dstSignals.load(bus, false);
        pause();

        //Set the select signal low
        srcSignals.select(bus, false);
        pause();
    }

    /**
     * Clears the value in register A
     */
    public void clearRegister(RegisterName reg) {
        //Reuse the move functionality like the instruction decoder will.
        //The choice of using the ADDRESS bus here is arbitrary.
        moveValue(reg, reg, ADDRESS);
    }


    /**
     * Clears both registers at once
     */
    public void clearBothRegisters() {

        /*
         * Note: this method COULD just use the load signal, but I'm trying to test what the instruction decoder will
         * likely produce.
         */
        //Get the signals for the both registers
        RegisterSignalSet aSignals = registerMap.get(A);
        RegisterSignalSet bSignals = registerMap.get(B);

        //Set the SELECT and the LOAD signals high at the same time
        aSignals.select(ADDRESS, true);
        aSignals.load(ADDRESS, true);
        bSignals.select(DATA, true);
        bSignals.load(DATA, true);
        pause();

        //Set the load signal low, latching the empty value
        aSignals.load(ADDRESS, false);
        bSignals.load(DATA, false);
        pause();

        //Set the select signal low
        aSignals.select(ADDRESS, false);
        bSignals.select(DATA, false);
        pause();

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
