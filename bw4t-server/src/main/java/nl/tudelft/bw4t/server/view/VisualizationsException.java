package nl.tudelft.bw4t.server.view;

public class VisualizationsException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public VisualizationsException() {
    }

    public VisualizationsException(String message) {
        super(message);
    }

    public VisualizationsException(Throwable cause) {
        super(cause);
    }

    public VisualizationsException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisualizationsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
