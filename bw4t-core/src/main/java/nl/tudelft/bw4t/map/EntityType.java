package nl.tudelft.bw4t.map;

/**
 * The possible types of entities in the BW4T system may be.
 */
public enum EntityType {
    
    /** Possible types */
    HUMAN, AGENT, EPARTNER;

    /**
     * @return {@link #name()} in lower case
     */
    public String nameLower() {
        return name().toLowerCase();
    }
    
   /**
    * @return {@link #name()} in lower case with capital.
    */
    public String nameCamel() {
        final String name = nameLower();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    /**
     * @param type
     *            the type string to check
     * @return true iff the given string is the same as the name of this instance
     */
    public boolean isA(String type) {
        return name().equalsIgnoreCase(type);
    }

    /**
     * Get the type of Entity from the type string
     * @param name the type string
     * @return the type enum
     * 
     * @throws RuntimeException 
     */
    public static EntityType getType(String name) throws RuntimeException {
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

    /**
     * String representation of the entity, with the first letter capitalized.
     * @return Returns the controller type starting with a capital letter. Example: Agent instead of agent
     */
    @Override
    public String toString() {
        return nameLower().substring(0, 1).toUpperCase() + nameLower().substring(1);
    }
}
