package nl.tudelft.bw4t.server.model.robots;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.tudelft.bw4t.map.Path;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.zone.Zone;
import nl.tudelft.bw4t.server.util.PathPlanner;
import nl.tudelft.bw4t.server.util.ZoneLocator;

import org.apache.log4j.Logger;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

/**
 * Represents a self navigating robot in the BW4T environment. The self navigation means that you can go to a Zone which
 * then does path planning and keeps driving till destination reached.
 */
public class NavigatingRobot extends AbstractRobot {

    /**
     * The log4j logger, logs to the console.
     */
    private static final Logger LOGGER = Logger.getLogger(NavigatingRobot.class);

    /**
     * We keep a stack of planned motions. We may need to extend this if rotations and waiting for doors also needs to
     * be planned. For now we keep it simple.
     */
    private Queue<NdPoint> plannedMoves = new ConcurrentLinkedQueue<NdPoint>();

    /**
     * The history of the planned moves.
     */
    private Queue<NdPoint> plannedMovesHistory = plannedMoves;

    /**
     * When a move is started, it moves from the stack to currentMove. When currentMove is null, it's done.
     */
    private NdPoint currentMove = null;
    /**
     * The history of the current moves
     */
    private NdPoint currentMoveHistory = currentMove;

    /**
     * The path as displayed on the map.
     */
    private Path displayedPath = new Path();

    /**
     * Makes a new navigating robot.
     * 
     * @param name
     *            of the bot
     * @param space
     *            space in which bot is placed
     * @param grid
     *            in which the bot is placed
     * @param context
     *            the context in which bot is placed
     * @param oneBotPerZone
     *            true if max 1 bot in a zone
     * @param cap
     *            capacity of the bot
     */
    public NavigatingRobot(String name, ContinuousSpace<Object> space, Grid<Object> grid, Context<Object> context,
            boolean oneBotPerZone, int cap) {
        super(name, space, grid, context, oneBotPerZone, cap);

        context.add(displayedPath);
    }

    /**
     * Catch events when the robot stops moving. Then we have to check what to do.
     */
    @Override
    public void stopRobot() {
        // do the normal handling to stop the robot.
        super.stopRobot();
        // and handle the event
        robotStopped();
    }

    /**
     * Check if the robot reached the previous destination without problems, and then move on if necessary. This should
     * be called whenever the robots stops.
     */
    private void robotStopped() {
        if (isCollided()) {
            /**
             * Save the current move in case the bot wishes to navigate around the obstacle.
             */
            if (getZone() != null && currentMove == getZone().getLocation()) {
                // If we're already at the location, get the next one.
                currentMoveHistory = plannedMoves.poll();
            } else {
                currentMoveHistory = currentMove;
            }

            LOGGER.warn("Motion planning failed. Canceling planned path. Collision flag is " + super.isCollided());
            plannedMovesHistory = new LinkedList<NdPoint>(plannedMoves);
            plannedMoves.clear();
            return;
        }
        currentMove = null;
        useNextTarget();
    }

    /**
     * Let the robot move to the next planned target on the stack. This will clear the {@link #collided} flag. Always
     * erases the current target.
     */
    public void useNextTarget() {
        currentMove = null;
        if (plannedMoves.isEmpty()) {
            updateDrawPath();
            return;
        }

        // we arrived at the inbetween target
        currentMove = plannedMoves.poll();
        super.setTargetLocation(currentMove);
    }

    /**
     * updates the path to draw.
     */
    private void updateDrawPath() {
        if (BW4TEnvironment.getInstance().isDrawPathsEnabled()) {
            displayedPath.setPath(new ArrayList<NdPoint>(plannedMoves));
        }
    }

    /**
     * sets the target location of the robot
     * 
     * @param p
     *            point to which we want to target
     */
    @Override
    public void setTargetLocation(NdPoint p) {
        clearCollided();
        clearObstacles();
        if (plannedMoves == null) {
            throw new InternalError("plannedMoves==null. How is this possible??");
        }
        // clear old path.
        plannedMoves.clear();
        Zone startpt = ZoneLocator.getNearestZone(this.getLocation());
        Zone targetpt = ZoneLocator.getNearestZone(p);
        List<Zone> allnavs = new ArrayList<Zone>();
        for (Object o : context.getObjects(Zone.class)) {
            allnavs.add((Zone) o);
        }
        
        planPath(p, startpt, targetpt, allnavs);
        
        // and add the real target
        plannedMoves.add(p);
        updateDrawPath();
        // make the bot use the new path.
        useNextTarget();
    }

    /**
     * Plans the path and adds it to planned moves
     * @param p the point 
     * @param startpt starting point
     * @param targetpt target point
     * @param allnavs all zones
     */
    private void planPath(NdPoint p, Zone startpt, Zone targetpt, List<Zone> allnavs) {
        // plan the path between the Zones
        List<Zone> plannedPath = PathPlanner.findPath(allnavs, startpt, targetpt);
        if (plannedPath.isEmpty()) {
            throw new IllegalArgumentException("target " + p + " is unreachable from " + this);
        }
        // and copy Zone path to our stack.
        for (Zone point : plannedPath) {
            plannedMoves.add(point.getLocation());
        }
    }

    @Override
    public void setTarget(BoundedMoveableObject target) {
        clearCollided();
        clearObstacles();
        // clear old path.
        plannedMoves.clear();
        Zone startpt = ZoneLocator.getNearestZone(this.getLocation());
        Zone targetpt = ZoneLocator.getNearestZone(target.getLocation());
        List<Zone> allnavs = new ArrayList<Zone>(getAllZonesInMap());

        // plan the path between the Zones
        List<Zone> path = PathPlanner.findPath(allnavs, startpt, targetpt);
        if (path.isEmpty()) {
            throw new IllegalArgumentException("target " + target + " is unreachable from " + this);
        }
        // and copy Zone path to our stack.
        for (Zone p : path) {
            plannedMoves.add(p.getLocation());
        }
        // and add the real target
        plannedMoves.add(target.getLocation());

        // make the bot use the new path.
        updateDrawPath();
        useNextTarget();
    }

    /**
     * The possible states of the navigating robot
     */
    public enum State {
        /**
         * Arrived state
         */
        ARRIVED,
        /**
         * Collided state
         */
        COLLIDED, 
        /**
         * Traveling state
         */
        TRAVELING

    }

    @Override
    public State getState() {
        if (isCollided()) {
            return State.COLLIDED;
        }
        if (currentMove == null && plannedMoves.isEmpty()) {
            return State.ARRIVED;
        }
        return State.TRAVELING;
    }

    /**
     * State: The path has failed, the bot has collided.
     * 
     * Find a new path, going around the obstacles. Strategy: Calculate path over grid as opposed to the zones. Find a
     * path from current location to the location of the next zone (currentMove)
     *
     * Continue with normal path after this.
     */
    public void navigateObstacles() {
        List<Zone> navZones = new ArrayList<Zone>(getAllZonesInMap());

        // the end isn't rounded since maps never have .5 coordinates.
        NdPoint startLocationRounded = new NdPoint((int) getLocation().getX(), (int) getLocation().getY());
        List<NdPoint> path = PathPlanner.findPath(navZones, getObstacles(), startLocationRounded, currentMoveHistory,
                getSize());
        if (path.isEmpty()) {
            LOGGER.debug("No alternative path found.");
            throw new IllegalArgumentException("target " + getTargetLocation() + " is unreachable for " + this);
        } else {
            createPathObstacle(path);
        }
    }

    /**
     * Creates a path around an obstacle
     * @param path to create
     */
    private void createPathObstacle(List<NdPoint> path) {
        LOGGER.debug("Found path around obstacle.");
        // Now we just push the new points to the plannedMoved queue and sit back and relax!.
        for (NdPoint p : path) {
            plannedMoves.add(p);
        }

        for (NdPoint p : plannedMovesHistory) {
            plannedMoves.add(p);
        }

        clearCollided();
        clearObstacles();

        // Let's roll.
        updateDrawPath();
        useNextTarget();
    }

    /**
     * gets all zones in the map.
     * @return all zones
     */
    private Set<Zone> getAllZonesInMap() {
        Set<Zone> zones = new HashSet<Zone>();
        for (Object o : context.getObjects(Zone.class)) {
            zones.add((Zone) o);
        }
        return zones;
    }

}
