package org.karnes.homebrew.emulator.component;

public interface Component {
    /**
     * Gets the Component's name, which is useful for debugging.
     *
     * @return The Component's name.
     */
    String getName();

    /**
     * Gets the Component's width, which is the number of bits of its primary data.
     *
     * @return The Component's bit width.
     */
    int getWidth();
}
