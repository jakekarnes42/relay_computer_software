package org.karnes.homebrew.emulator.component.bus.connection;

/**
 * Indicates that the requested {@link Connection} is already in use and cannot be provided again. A component's
 * connection cannot be reused. You likely need to use a {@link org.karnes.homebrew.emulator.component.bus.Bus}
 * for connecting multiple connections to a single component
 */
public class ExistingConnectionException extends IllegalStateException {

    /**
     * Constructs an ExistingConnectionException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public ExistingConnectionException() {
        super();
    }

    /**
     * Constructs an ExistingConnectionException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * @param s the String that contains a detailed message
     */
    public ExistingConnectionException(String s) {
        super(s);
    }

}
