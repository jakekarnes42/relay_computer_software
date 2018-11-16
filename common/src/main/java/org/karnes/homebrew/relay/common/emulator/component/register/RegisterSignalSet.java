package org.karnes.homebrew.relay.common.emulator.component.register;

import org.karnes.homebrew.relay.common.emulator.component.bus.BusName;
import org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal.WritableSignalConnection;

/**
 * A wrapper around the various signals that control a register
 */
public class RegisterSignalSet {
    private final String name;
    private final WritableSignalConnection selectAConnection;
    private final WritableSignalConnection loadAConnection;
    private final WritableSignalConnection selectDConnection;
    private final WritableSignalConnection loadDConnection;

    /**
     * Creates a new RegisterSignalSet
     *
     * @param registerName      The name of the {@link Register} that these signals are connected to.
     * @param selectAConnection The SELECT_A signal for the register.
     * @param loadAConnection   The LOAD_A signal for the register.
     * @param selectDConnection The SELECT_D signal for the register.
     * @param loadDConnection   The LOAD_D signal for the register.
     */
    public RegisterSignalSet(String registerName, WritableSignalConnection selectAConnection, WritableSignalConnection loadAConnection, WritableSignalConnection selectDConnection, WritableSignalConnection loadDConnection) {

        this.name = registerName + " Signal Set";
        this.selectAConnection = selectAConnection;
        this.loadAConnection = loadAConnection;
        this.selectDConnection = selectDConnection;
        this.loadDConnection = loadDConnection;
    }

    /**
     * Sets the value of the SELECT signal for the specified bus
     *
     * @param bus    which bus side should be used
     * @param signal {@code true} if the SELECT signal should be set HIGH, {@code false} if the SELECT signal should be set LOW.
     */
    public void select(BusName bus, boolean signal) {
        switch (bus) {
            case ADDRESS:
                selectAConnection.writeSignal(signal);
                break;
            case DATA:
                selectDConnection.writeSignal(signal);
                break;
        }
    }

    /**
     * Sets the value of the LOAD signal for the specified bus
     *
     * @param bus    which bus side should be used
     * @param signal {@code true} if the LOAD signal should be set HIGH, {@code false} if the LOAD signal should be set LOW.
     */
    public void load(BusName bus, boolean signal) {
        switch (bus) {
            case ADDRESS:
                loadAConnection.writeSignal(signal);
                break;
            case DATA:
                loadDConnection.writeSignal(signal);
                break;
        }
    }
}
