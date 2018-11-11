package org.karnes.homebrew.relay.common.emulator.component.bus.connection.signal;


import org.karnes.homebrew.relay.common.emulator.component.bus.connection.Disconnection;

/**
 * A {@link BidirectionalSignalConnection} which is always disconnected and acts like a no-op if read from or written to.
 */
public class SignalDisconnection extends Disconnection implements BidirectionalSignalConnection {

    public SignalDisconnection() {
        super(1);
    }

    /**
     * Always returns false because it is disconnected.
     *
     * @return false
     */
    @Override
    public boolean readSignal() {
        return false;
    }

    /**
     * A NO-OP because it is disconnected.
     *
     * @param value - ignored
     */
    @Override
    public void writeSignal(boolean value) {

    }
}
