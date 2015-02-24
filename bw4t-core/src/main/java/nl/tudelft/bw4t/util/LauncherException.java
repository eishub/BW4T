package nl.tudelft.bw4t.util;

/**
 * 
 */

/**
 * This type of exception is thrown by the Launcher whenever it encounters
 * errors in starting up the system.
 */
public class LauncherException extends RuntimeException {

    /** Serial id. */
    private static final long serialVersionUID = -7076592660325024223L;

    /**
     * Constructs a new runtime exception with null as its detail message.
     */
    public LauncherException() {
        super("An error occured while starting up the BW4TServer.");
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * 
     * @param msg
     *            the detail message.
     */
    public LauncherException(String msg) {
        super(msg);
    }

    /**
     * Pipes through the given exception by wrapping it.
     * 
     * @param cause
     *            the cause of this exception
     */
    public LauncherException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.
     * 
     * @param msg
     *            the detail message
     * @param cause
     *            the exception that caused this one
     */
    public LauncherException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
