package org.karnes.homebrew.emulator.component;

import java.util.Objects;

/**
 * A component within the CPU
 */
public abstract class SoftwareComponent implements Component {

    protected final String name;
    protected final int width;

    /**
     * Creates a new Component.
     *
     * @param name  The name of the Component
     * @param width The width of the Component's primary data
     */
    public SoftwareComponent(String name, int width) {
        this.name = Objects.requireNonNull(name, "A non-null name must be supplied for a Component");
        this.width = width;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoftwareComponent component = (SoftwareComponent) o;
        return width == component.width &&
                Objects.equals(name, component.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, width);
    }

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                ", width=" + width +
                '}';
    }
}
