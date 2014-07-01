package nl.tudelft.bw4t.scenariogui;

/**
 * The default values for in the GUI.
 */
public enum DefaultConfigurationValues {
    DEFAULT_SERVER_IP("localhost"),
    DEFAULT_CLIENT_IP("localhost"),
    DEFAULT_SERVER_PORT("8000"),
    DEFAULT_CLIENT_PORT("2000"),
    USE_GUI("FALSE"),
    VISUALIZE_PATHS("FALSE"),
    ENABLE_COLLISIONS("FALSE"),
    USE_GOAL("TRUE"),
    MAP_FILE(""),
    COLLISIONS_ENABLED("TRUE");

    private String value;

    /**
     * Constructs a new default configuration.
     *
     * @param newValue The new default value.
     */
    DefaultConfigurationValues(final String newValue) {
        this.value = newValue;
    }

    /**
     * Gets the value as a string.
     *
     * @return The value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the value as an int.
     *
     * @return The value as an int.
     */
    public int getIntValue() {
        return Integer.parseInt(value);
    }

    /**
     * Gets the value as a boolean.
     *
     * @return The value as a boolean.
     */
    public boolean getBooleanValue() {
        return Boolean.parseBoolean(value);
    }

}