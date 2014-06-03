package nl.tudelft.bw4t.agent;

/**
 * The possible types of entities in the BW4T system may be.
 */
public enum EntityType {
    HUMAN, AGENT, EPARTNER;

    /**
     * @return {@link #name()} in lower case
     */
    public String nameLower() {
        return name().toLowerCase();
    }

    /**
     * @param type
     *            the type string to check
     * @return true iff the given string is the same as the name of this instance
     */
    public boolean isA(String type) {
        return nameLower().equals(type.toLowerCase());
    }

    /**
     * Get the type of Entity from the type string
     * @param name the type string
     * @return the type enum
     */
    public static EntityType getType(String name) {
        switch (name.toLowerCase()) {
        case "human":
            return HUMAN;
        case "agent":
            return AGENT;
        case "epartner":
            return EPARTNER;
        default:
            throw new RuntimeException("Unsupported type of entity found.");
        }
    }
}
