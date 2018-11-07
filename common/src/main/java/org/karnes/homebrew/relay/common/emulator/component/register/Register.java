package org.karnes.homebrew.relay.common.emulator.component.register;

import org.karnes.homebrew.relay.common.emulator.component.SoftwareComponent;

/**
 * A representation of a hardware register with an arbitrary number of bits
 */
public class Register extends SoftwareComponent {
    /**
     * Creates a new Component.
     *
     * @param name  The name of the Component
     * @param width The width of the Component's primary data
     */
    public Register(String name, int width) {
        super(name, width);
    }

    //private Readable

}
