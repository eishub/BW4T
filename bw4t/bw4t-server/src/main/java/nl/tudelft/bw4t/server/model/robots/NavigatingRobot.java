package nl.tudelft.bw4t.server.model.robots;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.tudelft.bw4t.map.Path;
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
    Queue<NdPoint> plannedMoves = new ConcurrentLinkedQueue<NdPoint>();

    Queue<NdPoint> plannedMovesHistory = plannedMoves;

    /**
     * When a move is started, it moves from the stack to currentMove. When currnetMove is null, it's done.
     */
    NdPoint currentMove = null;
    NdPoint currentMoveHistory = currentMove;

    /**
     * The current target location.
     */
    NdPoint targetLocation = null;

    /**
     * The path as displayed on the map.
     */
    Path path = new Path();


    /**
     * @param name
     * @param space
     * @param context
     * @param oneBotPerZone true if max 1 bot in a zone
     */
    public NavigatingRobot(String name, ContinuousSpace<Object> space, Grid<Object> grid, Context<Object> context, boolean oneBotPerZone, int cap) {
        super(name, space, grid, context, oneBotPerZone, cap);

        context.add(path);
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
        if(currentMove == getZone().getLocation()) {
            currentMoveHistory = plannedMoves.poll();
        } else {
            currentMoveHistory = currentMove;
        }
        currentMove = null;
        if (isCollided()) {
            LOGGER.warn("Motion planning failed. Canceling planned path. Collision flag is " + super.isCollided());
            plannedMovesHistory = new LinkedList(plannedMoves);
            plannedMoves.clear();
            return;
        }
        useNextTarget();
    }

    @Override
    public void setTargetLocation(NdPoint p) {
        targetLocation = p;

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
        // plan the path between the Zones
        List<Zone> path = PathPlanner.findPath(allnavs, startpt, targetpt);
        if (path.isEmpty()) {
            throw new IllegalArgumentException("target " + p + " is unreachable from " + this);
        }
        // and copy Zone path to our stack.
        for (Zone point : path) {
            plannedMoves.add(point.getLocation());
        }
        // and add the real target
        plannedMoves.add(p);
        updateDrawPath();
        useNextTarget(); // make the bot use the new path.
    }

    /**
     * Let the robot move to the next planned target on the stack. This will clear the {@link #collided} flag. Always
     * erases the current target.
     */
    public void useNextTarget() {
        currentMove = null;
        if (plannedMoves.isEmpty()) {
            // we're there.
            targetLocation = null;
            updateDrawPath();
            return;
        }

        // we arrived at the inbetween target
        currentMove = plannedMoves.poll();
        super.setTargetLocation(currentMove);
    }

    private void updateDrawPath() {
        path.setPath(new ArrayList(plannedMoves));
    }

    @Override
    public void setTarget(BoundedMoveableObject target) {
        targetLocation = target.getLocation();

        // clear old path.
        plannedMoves.clear();
        Zone startpt = ZoneLocator.getNearestZone(this.getLocation());
        Zone targetpt = ZoneLocator.getNearestZone(target.getLocation());
        List<Zone> allnavs = new ArrayList<Zone>();
        for (Object o : context.getObjects(Zone.class)) {
            allnavs.add((Zone) o);
        }
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
        ARRIVED, COLLIDED, TRAVELING
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
     * <p/>
     * Find a new path, going around the obstacles.
     * Strategy: Calculate path over grid as opposed to the zones. Find a path from current location
     * to the location of the next zone (currentMove)
     * <p/>
     * Continue with normal path after this.
     */
    public void navigateObstacles(boolean useEntireMap) {
        LOGGER.debug("Navigate block started");
        Zone start = getZone();
        Zone end = start;

        List<Zone> navZones = new ArrayList<Zone>();
        Set<Zone> referenceZones;
        if (useEntireMap) {
            referenceZones = getAllZonesInMap();
        }
        else {
            // Make a copy!
            referenceZones = new HashSet(start.getNeighbours());
            referenceZones.add(start);
        }

        // Search for the zone we're going towards.
        boolean match = false;
        for (Zone zone : referenceZones) {
            if (zone.getBoundingBox().contains(currentMoveHistory.getX(), currentMoveHistory.getY())) {
                match = true;
                end = zone;
                break;
            }
        }

        // If we didn't find it yet, search over all zones, and
        // immediately run pathfinding over the entire map.
        if(!match) {
            referenceZones = getAllZonesInMap();

            for (Zone zone : referenceZones) {
                navZones.add(zone);
                if (zone.getBoundingBox().contains(currentMoveHistory.getX(), currentMoveHistory.getY())) {
                    end = zone;
                }
            }
        } else {
            // First try to find a path using the current zone and that of the destination zone.
            navZones.add(start);
            navZones.add(end);
        }

        List<NdPoint> path = PathPlanner.findPath(navZones, getObstacles(), start, end);
        if (path.isEmpty() && !useEntireMap) {
            LOGGER.debug("No path found, checking entire map");
            navigateObstacles(true);
        }
        else if (path.isEmpty() && useEntireMap) {
            LOGGER.debug("No path found. giving up.");
            throw new IllegalArgumentException("target " + targetLocation + " is unreachable for " + this);
        }
        else {
            LOGGER.debug("Found new path");
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
    }

    private Set<Zone> getAllZonesInMap() {
        Set<Zone> zones = new HashSet<Zone>();
        for (Object o : context.getObjects(Zone.class)) {
            zones.add((Zone) o);
        }
        return zones;
    }
}
