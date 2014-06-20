package nl.tudelft.bw4t.client.message;

/**
 * Class containing all necessary information for a message Note that
 * BW4TMessage objects are only used for direct agent communication within the
 * BW4T server. This is totally separated from the GOAL messaging system.
 */
public class BW4TMessage {

    /** Information storage of the message. {@code null} if not set. */
    private final String color, playerId, room;
    /** MessageType of this message */
    private final MessageType type;
    /** Number contained in this message. {@code Integer.MAX_VALUE} if not set. */
    private final int number;

    /**
     * Create a new message
     * 
     * @param number 
     *            - Used for the number of blocks in the roomContainsAmount message.
     * @param type 
     *            - The type of the message.
     * @param room 
     *            - The room that the message pertains to (Long.MAX_VALUE if
     *            message does not pertain to a room).
     * @param color 
     *            - The color that the message pertains to (null if message does
     *            not pertain to a certain color).
     */
    public BW4TMessage(MessageType type, String room, String color, int number) {
        this.type = type;
        this.room = room;
        this.color = color;
        this.playerId = null;
        this.number = number;
    }

    /**
     * Create a new message
     * 
     * @param type 
     *            - The type of the message.
     * @param room 
     *            - The room that the message pertains to (Long.MAX_VALUE if
     *            message does not pertain to a room).
     * @param color 
     *            - The color that the message pertains to (null if message does
     *            not pertain to a certain color).
     * @param playerId 
     *            - The player that the message is directed to (null if message
     *            is not directed towards someone).
     */
    public BW4TMessage(MessageType type, String room, String color, String playerId) {
        this.type = type;
        this.room = room;
        this.color = color;
        this.playerId = playerId;
        this.number = Integer.MAX_VALUE;
    }

    /**
     * Create a new message
     * 
     * @param type
     *            - The type of the message
     * @param room
     *            - The room that the message pertains to (Long.MAX_VALUE if
     *            message does not pertain to a room).
     * @param color
     *            - The color that the message pertains to (null if message does
     *            not pertain to a certain color).
     * @param playerId
     *            - The player that the message is directed to (null if message
     *            is not directed towards someone).
     * @param num
     *            - Is the number associated with the message.Typically number of
     *            blocks of given color in room. Nonsense value if the message
     *            does not use the number.
     */
    public BW4TMessage(MessageType type, String room, String color, String playerId, Integer num) {
        this.type = type;
        this.room = room;
        this.color = color;
        this.playerId = playerId;
        this.number = num;
    }

    /**
     * Create a new message, used for the messages for which only the type is important
     * 
     * @param type
     *            - The type of the message.
     */
    public BW4TMessage(MessageType type) {
        this.type = type;
        this.room = null;
        this.color = null;
        this.playerId = null;
        this.number = Integer.MAX_VALUE;
    }

    /**
     * Get the {@link MessageType} of this message.
     * 
     * @return {@link MessageType} of this message.
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Get the room.
     * 
     * @return {@link String} holding the room, or null.
     */
    public String getRoom() {
        return room;
    }

    /**
     * Get the color.
     * 
     * @return {@link String} holding the color, or null.
     */
    public String getColor() {
        return color;
    }

    /**
     * Get the player id.
     * 
     * @return {@link String} holding the player id, or null.
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Get the number.
     * 
     * @return {@code int} holding the room, or nonsense number if the
     *         message did not contain any number.
     */
    public int getNumber() {
        return number;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + number;
        result = prime * result + ((playerId == null) ? 0 : playerId.hashCode());
        result = prime * result + ((room == null) ? 0 : room.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BW4TMessage other = (BW4TMessage) obj;
        if (color == null) {
            if (other.color != null) {
                return false;
            }
        } else if (!color.equals(other.color)) {
            return false;
        }
        if (number != other.number) {
            return false;
        }
        if (playerId == null) {
            if (other.playerId != null)
                return false;
        } else if (!playerId.equals(other.playerId)) {
            return false;
        }
        if (room == null) {
            if (other.room != null)
                return false;
        } else if (!room.equals(other.room)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BW4TMessage [color=" + color + ", playerId=" + playerId + ", room=" + room + ", type=" + type
                + ", number=" + number + "]";
    }

}
