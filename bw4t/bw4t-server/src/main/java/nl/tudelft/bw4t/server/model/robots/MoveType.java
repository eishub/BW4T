package nl.tudelft.bw4t.server.model.robots;

/**
 * This class contains all the move types possible for a robot.
 */
public enum MoveType {
    /**
     * start and end point are in same room/corridor
     */
    SAME_AREA,
    /**
     * move is attempting to go through a wall
     */
    HIT_WALL,
    /**
     * Entering room (through open door).
     */
    ENTERING_ROOM,
    /**
     * bumped into closed door
     */
    HIT_CLOSED_DOOR,
    /**
     * bumped into an occupied zone
     */
    HIT_OCCUPIED_ZONE,
    /**
     * Going from a Zone into free unzoned space.
     */
    ENTERING_FREESPACE,
    /**
     * Entering a corridor
     * */
    ENTER_CORRIDOR;

    /**
     * Merge the move type if multiple zones are entered at once. The result
     * is the 'worst' event that happens
     * 
     * @param other movetype to be merged
     * @return the merge
     */
    public MoveType merge(MoveType other) {
        if (this.isHit()) {
            return this;
        } else if (other.isHit() || (this == SAME_AREA || this == ENTERING_FREESPACE)) {
            return other;
        } else {
            return this;
        }
    }

    public boolean isHit() {
        return this == HIT_CLOSED_DOOR || this == HIT_WALL
                || this == HIT_OCCUPIED_ZONE;
    }
}
