package nl.tudelft.bw4t.model.robots;

import nl.tudelft.bw4t.model.robots.handicap.IRobot;

/**
 * Created by on 10-6-2014.
 */
public class DestinationOccupiedException extends Exception {

    private IRobot tileOccupiedBy;

    public DestinationOccupiedException(String message, IRobot tileOccupiedBy) {
        super(message);
        this.tileOccupiedBy = tileOccupiedBy;
    }

    public IRobot getTileOccupiedBy() {
        return tileOccupiedBy;
    }
}
