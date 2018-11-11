package org.karnes.homebrew.relay.common.emulator.component.register;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.bus.Bus;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.BidirectionalConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.ReadableConnection;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterTest {
    private static int DATA_WIDTH = 4;
    private static final FixedBitSet ZERO_VAL = new FixedBitSet(DATA_WIDTH);
    private static final FixedBitSet ONES_VAL = FixedBitSet.allOnes(DATA_WIDTH);
    private static final FixedBitSet MIX1_VAL = new FixedBitSet("0101");
    private static final FixedBitSet MIX2_VAL = new FixedBitSet("1010");


    //Components
    private Register register;
    private Bus addressBus;
    private Bus dataBus;

    //Register Connections
    private WritableSignalConnection selectAConnection;
    private WritableSignalConnection loadAConnection;
    private WritableSignalConnection selectDConnection;
    private WritableSignalConnection loadDConnection;
    private ReadableConnection registerOutputConnection;

    //Bus Connections
    private BidirectionalConnection addressBusConnection;
    private BidirectionalConnection dataBusConnection;

    @BeforeEach
    void setUp() {
        //Set up the Buses
        dataBus = new Bus("Data Bus", DATA_WIDTH);
        addressBus = new Bus("Address Bus", DATA_WIDTH);

        //Bus connections
        addressBusConnection = addressBus.createBidirectionalConnection();
        dataBusConnection = dataBus.createBidirectionalConnection();

        //Create a new register
        register = new Register("Test Register", addressBus, dataBus);

        //Get signal connections to the register
        selectAConnection = register.getSelectAConnection();
        loadAConnection = register.getLoadAConnection();
        selectDConnection = register.getSelectDConnection();
        loadDConnection = register.getLoadDConnection();

        //Get output connection from the register
        registerOutputConnection = register.getRegisterOutputConnection();
    }

    @Test
    void loadAddressTest() {
        //First put a value on the bus
        addressBusConnection.writeValue(ONES_VAL);

        //Send the LOAD signal HIGH
        loadAConnection.writeSignal(true);

        //Send the LOAD signal back to LOW to latch
        loadAConnection.writeSignal(false);

        //Stop writing the value to the bus
        addressBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, addressBusConnection.readValue());

        //Read the register's current value
        assertEquals(ONES_VAL, registerOutputConnection.readValue());
    }

    @Test
    void loadAddressAndSelectTest() {
        //First put a value on the bus
        addressBusConnection.writeValue(ONES_VAL);

        //Send the LOAD signal HIGH
        loadAConnection.writeSignal(true);

        //Send the LOAD signal back to LOW to latch
        loadAConnection.writeSignal(false);

        //Stop writing the value to the bus
        addressBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, addressBusConnection.readValue());

        //Read the register's current value
        assertEquals(ONES_VAL, registerOutputConnection.readValue());

        //Output the register's value onto the bus
        selectAConnection.writeSignal(true);

        //Ensure that we get the correct value back on the bus
        assertEquals(ONES_VAL, addressBusConnection.readValue());

        //Stop outputting the register's value onto the bus
        selectAConnection.writeSignal(false);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, addressBusConnection.readValue());

    }

    @Test
    void addressSideClearTest() {
        //First put a value on the bus
        addressBusConnection.writeValue(ONES_VAL);

        //Send the LOAD signal HIGH
        loadAConnection.writeSignal(true);

        //Send the LOAD signal back to LOW to latch
        loadAConnection.writeSignal(false);

        //Stop writing the value to the bus
        addressBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, addressBusConnection.readValue());

        //Read the register's current value
        assertEquals(ONES_VAL, registerOutputConnection.readValue());

        //Set the select signal high
        selectAConnection.writeSignal(true);

        //The value should be back on the bus
        assertEquals(ONES_VAL, addressBusConnection.readValue());

        //Set the load signal high
        loadAConnection.writeSignal(true);

        //The value should be cleared from the bus
        assertEquals(ZERO_VAL, addressBusConnection.readValue());

        //Let the load signal fall
        loadAConnection.writeSignal(false);

        //The value should be cleared from the bus and the register
        assertEquals(ZERO_VAL, addressBusConnection.readValue());
        assertEquals(ZERO_VAL, registerOutputConnection.readValue());

        //Let the select signal fall
        selectAConnection.writeSignal(false);

        //The value should be cleared from the bus and the register
        assertEquals(ZERO_VAL, addressBusConnection.readValue());
        assertEquals(ZERO_VAL, registerOutputConnection.readValue());
    }

    @Test
    void addressLatchFallingLoadSignalTest() {
        //First put a value on the bus
        addressBusConnection.writeValue(MIX1_VAL);

        //Send the LOAD signal HIGH
        loadAConnection.writeSignal(true);

        //Change the value on the Bus
        addressBusConnection.writeValue(MIX2_VAL);

        //Send the LOAD signal back to LOW to latch
        loadAConnection.writeSignal(false);

        //Stop writing the value to the bus
        addressBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, addressBusConnection.readValue());

        //The register's value should be the value before the falling edge, not the rising edge
        assertEquals(MIX2_VAL, registerOutputConnection.readValue());

    }

    @Test
    void loadDataTest() {
        //First put a value on the bus
        dataBusConnection.writeValue(ONES_VAL);

        //Send the LOAD signal HIGH
        loadDConnection.writeSignal(true);

        //Send the LOAD signal back to LOW to latch
        loadDConnection.writeSignal(false);

        //Stop writing the value to the bus
        dataBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, dataBusConnection.readValue());

        //Read the register's current value
        assertEquals(ONES_VAL, registerOutputConnection.readValue());
    }

    @Test
    void loadDataAndSelectTest() {
        //First put a value on the bus
        dataBusConnection.writeValue(ONES_VAL);

        //Send the LOAD signal HIGH
        loadDConnection.writeSignal(true);

        //Send the LOAD signal back to LOW to latch
        loadDConnection.writeSignal(false);

        //Stop writing the value to the bus
        dataBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, dataBusConnection.readValue());

        //Read the register's current value
        assertEquals(ONES_VAL, registerOutputConnection.readValue());

        //Output the register's value onto the bus
        selectDConnection.writeSignal(true);

        //Ensure that we get the correct value back on the bus
        assertEquals(ONES_VAL, dataBusConnection.readValue());

        //Stop outputting the register's value onto the bus
        selectDConnection.writeSignal(false);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, dataBusConnection.readValue());

    }

    @Test
    void dataSideClearTest() {
        //First put a value on the bus
        dataBusConnection.writeValue(ONES_VAL);

        //Send the LOAD signal HIGH
        loadDConnection.writeSignal(true);

        //Send the LOAD signal back to LOW to latch
        loadDConnection.writeSignal(false);

        //Stop writing the value to the bus
        dataBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, dataBusConnection.readValue());

        //Read the register's current value
        assertEquals(ONES_VAL, registerOutputConnection.readValue());

        //Set the select signal high
        selectDConnection.writeSignal(true);

        //The value should be back on the bus
        assertEquals(ONES_VAL, dataBusConnection.readValue());

        //Set the load signal high
        loadDConnection.writeSignal(true);

        //The value should be cleared from the bus
        assertEquals(ZERO_VAL, dataBusConnection.readValue());

        //Let the load signal fall
        loadDConnection.writeSignal(false);

        //The value should be cleared from the bus and the register
        assertEquals(ZERO_VAL, dataBusConnection.readValue());
        assertEquals(ZERO_VAL, registerOutputConnection.readValue());

        //Let the select signal fall
        selectDConnection.writeSignal(false);

        //The value should be cleared from the bus and the register
        assertEquals(ZERO_VAL, dataBusConnection.readValue());
        assertEquals(ZERO_VAL, registerOutputConnection.readValue());
    }

    @Test
    void dataLatchFallingLoadSignalTest() {
        //First put a value on the bus
        dataBusConnection.writeValue(MIX1_VAL);

        //Send the LOAD signal HIGH
        loadDConnection.writeSignal(true);

        //Change the value on the Bus
        dataBusConnection.writeValue(MIX2_VAL);

        //Send the LOAD signal back to LOW to latch
        loadDConnection.writeSignal(false);

        //Stop writing the value to the bus
        dataBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, dataBusConnection.readValue());

        //The register's value should be the value before the falling edge, not the rising edge
        assertEquals(MIX2_VAL, registerOutputConnection.readValue());

    }

    @Test
    void loadAddressAndSelectDataTest() {
        //First put a value on the bus
        addressBusConnection.writeValue(ONES_VAL);

        //Send the LOAD signal HIGH
        loadAConnection.writeSignal(true);

        //Send the LOAD signal back to LOW to latch
        loadAConnection.writeSignal(false);

        //Stop writing the value to the bus
        addressBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, addressBusConnection.readValue());

        //Read the register's current value
        assertEquals(ONES_VAL, registerOutputConnection.readValue());

        //Output the register's value onto the other bus
        selectDConnection.writeSignal(true);

        //Ensure that we get the correct value back on the bus
        assertEquals(ONES_VAL, dataBusConnection.readValue());

        //Stop outputting the register's value onto the bus
        selectDConnection.writeSignal(false);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, dataBusConnection.readValue());

    }

    @Test
    void loadDataAndSelectAddressTest() {
        //First put a value on the bus
        dataBusConnection.writeValue(ONES_VAL);

        //Send the LOAD signal HIGH
        loadDConnection.writeSignal(true);

        //Send the LOAD signal back to LOW to latch
        loadDConnection.writeSignal(false);

        //Stop writing the value to the bus
        dataBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, dataBusConnection.readValue());

        //Read the register's current value
        assertEquals(ONES_VAL, registerOutputConnection.readValue());

        //Output the register's value onto the other bus
        selectAConnection.writeSignal(true);

        //Ensure that we get the correct value back on the bus
        assertEquals(ONES_VAL, addressBusConnection.readValue());

        //Stop outputting the register's value onto the bus
        selectAConnection.writeSignal(false);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, addressBusConnection.readValue());

    }

    @Test
    void selectBothSidesTest() {
        //First put a value on the bus
        dataBusConnection.writeValue(ONES_VAL);

        //Send the LOAD signal HIGH
        loadDConnection.writeSignal(true);

        //Send the LOAD signal back to LOW to latch
        loadDConnection.writeSignal(false);

        //Stop writing the value to the bus
        dataBusConnection.writeValue(ZERO_VAL);

        //Ensure there's no value on the bus
        assertEquals(ZERO_VAL, dataBusConnection.readValue());

        //Read the register's current value
        assertEquals(ONES_VAL, registerOutputConnection.readValue());

        //Set both select signals high
        selectAConnection.writeSignal(true);
        selectDConnection.writeSignal(true);

        //We should be able to read the value on both buses
        assertEquals(ONES_VAL, addressBusConnection.readValue());
        assertEquals(ONES_VAL, dataBusConnection.readValue());

    }

}