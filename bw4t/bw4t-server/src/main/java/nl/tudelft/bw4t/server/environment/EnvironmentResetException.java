package nl.tudelft.bw4t.server.environment;

public class EnvironmentResetException extends Exception {

    /**
     * Processes the exceptions of environment
     * 
     */
    private static final long serialVersionUID = 1L;

    public EnvironmentResetException() {
    }

    public EnvironmentResetException(String arg0) {
        super(arg0);
    }

    public EnvironmentResetException(Throwable arg0) {
        super(arg0);
    }

    public EnvironmentResetException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public EnvironmentResetException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
