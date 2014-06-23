package nl.tudelft.bw4t.server.model.robots;

/**
 * Created by ContextProjectGroup2014 on 10-6-2014.
 */
public class DestinationOccupiedException extends Exception {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -8056277359870730118L;
    
    /**
     * By whom the tile is occupied.
     */
    private final AbstractRobot tileOccupiedBy;

    /**
     * The exception occurs when a tile is occupied.
     * @param message
     *          message that needs to be send to console.
     * @param tileOccupiedBy
     *              sets the occupation.
     */
    public DestinationOccupiedException(String message, AbstractRobot tileOccupiedBy) {
        super(message);
        this.tileOccupiedBy = tileOccupiedBy;
    }

    public AbstractRobot getTileOccupiedBy() {
        return tileOccupiedBy;
    }
}
