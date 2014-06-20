package nl.tudelft.bw4t.server.model.robots;

import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;

/**
 * Created by on 10-6-2014.
 */
public class DestinationOccupiedException extends Exception {

    private AbstractRobot tileOccupiedBy;

    public DestinationOccupiedException(String message, AbstractRobot tileOccupiedBy) {
        super(message);
        this.tileOccupiedBy = tileOccupiedBy;
    }

    public AbstractRobot getTileOccupiedBy() {
        return tileOccupiedBy;
    }
}
