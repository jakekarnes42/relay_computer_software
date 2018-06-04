package org.karnes.homebrew.hardware;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.component.bus.ReadableBus;
import org.karnes.homebrew.emulator.component.bus.connection.BusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.BusValueChangedEvent;
import org.karnes.homebrew.emulator.component.bus.connection.InterruptableBusConnection;

/**
 * Periodically tells a {@link ReadableBus} to check itself for new values and alert the appropriate connections
 */
public class BusMonitor implements Runnable {
    private static final int DELAY_MILLIS = 10;
    private final ReadableBus bus;
    private FixedBitSet lastValue;

    public BusMonitor(ReadableBus bus) {
        this.bus = bus;
        lastValue = bus.getValue();
    }


    @Override
    public synchronized void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                FixedBitSet currentValue = bus.getValue();
                //Check if the value has changed
                if (!currentValue.equals(lastValue)) {
                    //The value has changed. Fire interrupts
                    BusValueChangedEvent event = new BusValueChangedEvent(bus.getName(), currentValue);

                    for (BusConnection busConnection : bus.getConnections()) {
                        if (busConnection instanceof InterruptableBusConnection) {
                            ((InterruptableBusConnection) busConnection).handleBusValueChangedEvent(event);
                        }
                    }

                    //Save the updated value for the next check
                    lastValue = currentValue;
                }

                Thread.sleep(DELAY_MILLIS);
            }
        } catch (InterruptedException e) {
            //We're done here.
        }
    }
}
