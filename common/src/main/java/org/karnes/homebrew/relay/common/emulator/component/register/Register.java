package org.karnes.homebrew.relay.common.emulator.component.register;

import org.karnes.homebrew.relay.common.bitset.FixedBitSet;
import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;
import org.karnes.homebrew.relay.common.emulator.component.bus.Bus;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.*;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.*;

/**
 * A representation of a hardware register with an arbitrary number of bits.
 * <p>
 * The register is connected to both the address bus and the data bus. Both registers have 2 corresponding signals
 * (SELECT and LOAD) which connect the register to bus.
 */
public class Register extends SoftwareComponent {

    /*
     * All connections start disconnected
     */
    private ReadableSignalConnection selectAConnection = new SignalDisconnection();
    private ReadableSignalConnection loadAConnection = new SignalDisconnection();
    private ReadableSignalConnection selectDConnection = new SignalDisconnection();
    private ReadableSignalConnection loadDConnection = new SignalDisconnection();

    private BidirectionalConnection addressBusConnection;
    private BidirectionalConnection dataBusConnection;

    //Used by TMP registers, and useful for testing/direct checking
    private WritableConnection registerOutputConnection = new Disconnection(getWidth());

    /**
     * The current value of the register
     */
    private FixedBitSet value = new FixedBitSet(getWidth());

    /**
     * Set {@code true} if loadA was previously set HIGH. This helps us track the falling behavior.
     */
    private boolean loadAAlreadyHigh = false;

    /**
     * Set {@code true} if loadD was previously set HIGH. This helps us track the falling behavior.
     */
    private boolean loadDAlreadyHigh = false;


    /**
     * Creates a new Register.
     *
     * @param name       The name of the register
     * @param addressBus The address bus to connect to.
     * @param dataBus    The data bus to connect to.
     */
    public Register(String name, Bus addressBus, Bus dataBus) {
        super(name, addressBus.getWidth());
        if (addressBus.getWidth() != dataBus.getWidth()) {
            throw new IllegalArgumentException("The bus widths must match. Address Bus width: " +
                    addressBus.getWidth() + " Data Bus width: " + dataBus.getWidth());
        }

        addressBusConnection = addressBus.createBidirectionalConnection(this::handleAddressBusConnectionChange);
        dataBusConnection = dataBus.createBidirectionalConnection(this::handleDataBusConnectionChange);
    }


    private void handleSelectAConnectionChange(FixedBitSet newValue) {
        //get incoming connections as booleans
        boolean selectA = selectAConnection.readSignal();

        checkState();

        //SELECTA is high, output value onto address bus
        if (selectA) {
            addressBusConnection.writeValue(value);
        } else {
            //Ensure we're not putting anything on the bus
            addressBusConnection.writeValue(new FixedBitSet(getWidth()));
        }
    }


    private void handleLoadAConnectionChange(FixedBitSet newValue) {
        //get incoming connections as booleans
        boolean loadA = loadAConnection.readSignal();

        checkState();

        //LOADA rising, clear the value
        if (loadA && !loadAAlreadyHigh) {
            updateRegisterValue(new FixedBitSet(getWidth()));
            //Set the flag to track rising/falling
            loadAAlreadyHigh = true;
        }

        //LOADA is FALLING, read in address bus and save it
        if (!loadA && loadAAlreadyHigh) {
            updateRegisterValue(addressBusConnection.readValue());
            //Set the flag to track rising/falling
            loadAAlreadyHigh = false;
        }
    }

    private void handleSelectDConnectionChange(FixedBitSet newValue) {
        //get incoming connections as booleans
        boolean selectD = selectDConnection.readSignal();

        checkState();

        //SELECTD is high, output value onto data bus
        if (selectD) {
            dataBusConnection.writeValue(value);
        } else {
            //Ensure we're not putting anything on the bus
            dataBusConnection.writeValue(new FixedBitSet(getWidth()));
        }
    }

    private void handleLoadDConnectionChange(FixedBitSet newValue) {
        //get incoming connections as booleans
        boolean loadD = loadDConnection.readSignal();

        checkState();

        //LOADD rising, clear the value
        if (loadD && !loadDAlreadyHigh) {
            updateRegisterValue(new FixedBitSet(getWidth()));
            //Set the flag to track rising/falling
            loadDAlreadyHigh = true;
        }

        //LOADD is FALLING, read in data bus and save it
        if (!loadD && loadDAlreadyHigh) {
            updateRegisterValue(dataBusConnection.readValue());
            //Set the flag to track rising/falling
            loadDAlreadyHigh = false;
        }
    }

    private void handleAddressBusConnectionChange(FixedBitSet newValue) {
        checkState();
    }

    private void handleDataBusConnectionChange(FixedBitSet newValue) {
        checkState();
    }


    private void updateRegisterValue(FixedBitSet newValue) {
        //Update our internal holder
        value = newValue;
        //Output our direct output connection
        registerOutputConnection.writeValue(newValue);

        //Update the address bus if SELECT_A is HIGH
        if (selectAConnection.readSignal()) {
            addressBusConnection.writeValue(value);
        }

        //Update the data bus if SELECT_D is HIGH
        if (selectDConnection.readSignal()) {
            dataBusConnection.writeValue(value);
        }
    }


    /**
     * Checks the incoming connections to ensure we're in a legal state. This may be overly cautious and disregard
     * states that the hardware could techinically handle.
     *
     * @throws IllegalStateException if the register is in an illegal state.
     */
    private void checkState() throws IllegalStateException {
        //get incoming connections as booleans
        boolean selectA = selectAConnection.readSignal();
        boolean loadA = loadAConnection.readSignal();
        boolean selectD = selectDConnection.readSignal();
        boolean loadD = loadDConnection.readSignal();

        //Can't have both LOADs HIGH at the same time
        if (loadA && loadD) {
            throw new IllegalStateException("Illegal Register State:" +
                    "LOAD_A and LOAD_D cannot be high at the same time.");
        }

        //Can't have opposite LOAD and SELECT high at the same time
        if (loadA && selectD) {
            throw new IllegalStateException("Illegal Register State:" +
                    "LOAD_A and SELECT_D cannot be high at the same time.");
        }
        if (loadD && selectA) {
            throw new IllegalStateException("Illegal Register State:" +
                    "LOAD_D and SELECT_A cannot be high at the same time.");
        }

    }


    /**
     * Gets the SELECT_A connection for this Register. Writes to this connection connect and disconnect the register
     * to/from the address bus.
     *
     * @return The SELECT_A connection.
     */
    public WritableSignalConnection getSelectAConnection() {
        //Check if we're already connected
        if (this.selectAConnection.isConnected()) {
            throw new ExistingConnectionException("The SelectA Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " Select A Connection", 1, this::handleSelectAConnectionChange);
            this.selectAConnection = new ReadableSignalConnectionWrapper(connection);
            return new WritableSignalConnectionWrapper(connection);
        }
    }


    /**
     * Gets the LOAD_A connection for this Register. Writes to this connection connect and disconnect the register
     * to/from the address bus.
     *
     * @return The LOAD_A connection.
     */
    public WritableSignalConnection getLoadAConnection() {
        //Check if we're already connected
        if (this.loadAConnection.isConnected()) {
            throw new ExistingConnectionException("The LoadA Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " Load A Connection", 1, this::handleLoadAConnectionChange);
            this.loadAConnection = new ReadableSignalConnectionWrapper(connection);
            return new WritableSignalConnectionWrapper(connection);
        }
    }


    /**
     * Gets the SELECT_D connection for this Register. Writes to this connection connect and disconnect the register
     * to/from the data bus.
     *
     * @return The SELECT_D connection.
     */
    public WritableSignalConnection getSelectDConnection() {
        //Check if we're already connected
        if (this.selectDConnection.isConnected()) {
            throw new ExistingConnectionException("The SelectD Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " Select D Connection", 1, this::handleSelectDConnectionChange);
            this.selectDConnection = new ReadableSignalConnectionWrapper(connection);
            return new WritableSignalConnectionWrapper(connection);
        }
    }


    /**
     * Gets the LOAD_D connection for this Register. Writes to this connection connect and disconnect the register
     * to/from the data bus.
     *
     * @return The LOAD_D connection.
     */
    public WritableSignalConnection getLoadDConnection() {
        //Check if we're already connected
        if (this.loadDConnection.isConnected()) {
            throw new ExistingConnectionException("The LoadD Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " Load D Connection", 1, this::handleLoadDConnectionChange);
            this.loadDConnection = new ReadableSignalConnectionWrapper(connection);
            return new WritableSignalConnectionWrapper(connection);
        }
    }


    public ReadableConnection getRegisterOutputConnection() {
        //Check if we're already connected
        if (this.registerOutputConnection.isConnected()) {
            throw new ExistingConnectionException("The Register Output Connection already exists for: " + getName());
        } else {
            //This connection isn't in use. Create a new connection
            SoftwareConnection connection = new SoftwareConnection(getName() + " Register Output Connection", getWidth());
            this.registerOutputConnection = connection;
            return connection;
        }
    }

}
