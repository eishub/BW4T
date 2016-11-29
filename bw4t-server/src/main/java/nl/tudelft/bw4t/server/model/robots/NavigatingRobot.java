package nl.tudelft.bw4t.server.model.robots;

import static nl.tudelft.bw4t.server.util.SpatialMath.distance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import nl.tudelft.bw4t.map.Path;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.zone.Room;
import nl.tudelft.bw4t.server.model.zone.Zone;
import nl.tudelft.bw4t.server.util.PathPlanner;
import nl.tudelft.bw4t.server.util.ZoneLocator;
import repast.simphony.space.continuous.NdPoint;

/**
 * Represents a self navigating robot in the BW4T environment. The self navigation means that you can go to a Zone which
 * then does path planning and keeps driving till destination reached.
 */
public class NavigatingRobot extends AbstractRobot {
    /**
     * We keep a stack of planned motions. We may need to extend this if rotations and waiting for doors also needs to
     * be planned. For now we keep it simple.
     */
    private Queue<NdPoint> plannedMoves = new ConcurrentLinkedQueue<NdPoint>();
    /**
     * When a move is started, it moves from the stack to currentMove. When currentMove is null, it's done.
     */
    private NdPoint currentMove = null;

    /**
     * The path as displayed on the map.
     */
    private Path displayedPath = new Path();

    /**
     * Makes a new navigating robot.
     *
     * @param name          of the bot
     * @param context       the context in which bot is placed
     */
    public NavigatingRobot(String name, BW4TServerMap context) {
        super(name, context);
        getContext().add(displayedPath);
    }

    /**
     * Plans the path and adds it to planned moves
     *
     * @param current  where we start our move from
     * @param target   the point
     * @param startpt  zone closest to our current position
     * @param targetpt target zone
     * @param allnavs  all navigation points in the system
     * @return the path to take through the navpoints
     */
    public static Queue<NdPoint> planPath(NdPoint current, NdPoint target, Zone startpt, Zone targetpt,
                                          Collection<Zone> allnavs) {
        // plan the path between the Zones
        List<NdPoint> plannedPath = PathPlanner.findPath(allnavs, startpt, targetpt);
        if (plannedPath.isEmpty()) {
            throw new IllegalArgumentException("target " + target + " is unreachable from " + current);
        }

        final NdPoint first = plannedPath.get(0);
        final Queue<NdPoint> actualPath = new LinkedBlockingQueue<>();
        if (plannedPath.size() == 1) {
            actualPath.add(target);
            return actualPath;
        }
        final boolean startsInRoom = startpt instanceof Room;
        if (startsInRoom || !current.equals(first) && !skipFirstNode(current, first, plannedPath.get(1))) {
            actualPath.add(first);
        }

        // and copy Zone path to our stack.
        int preLast = plannedPath.size() - 2;
        for (int i = 1; i < preLast; i++) {
            actualPath.add(plannedPath.get(i));
        }

        NdPoint toAdd = plannedPath.get(preLast);
        final NdPoint last = plannedPath.get(preLast + 1);
        if (preLast > 0) {
            actualPath.add(toAdd);
        }
        if (preLast == 0 && startsInRoom || targetpt instanceof Room || !skipLastNode(toAdd, last, target)) {
            actualPath.add(last);
        }
        actualPath.add(target);
        return actualPath;
    }

    /**
     * Check whether we should skip the next node because the one after that is closer.
     *
     * @param pzone  The previous zone
     * @param tzone  the zone next to the target node
     * @param target the target node
     * @return true if the target zone should be skipped
     */
    private static boolean skipLastNode(NdPoint pzone, NdPoint tzone, NdPoint target) {
        final double THRESHOLD = 2.;
        if (Math.abs(pzone.getX() - tzone.getX()) > THRESHOLD && Math.abs(pzone.getY() - tzone.getY()) > THRESHOLD) {
            return false;
        }

        return distance(pzone, target) < distance(pzone, tzone);
    }

    /**
     * Check whether we should skip the First node because it is faster, but still feasible to go directly to the
     * second node.
     *
     * @param current current position of the robot
     * @param fzone   the first zone to navigate to
     * @param szone   the second zone to navigate to
     * @return true if the first zone should be skipped
     */
    private static boolean skipFirstNode(NdPoint current, NdPoint fzone, NdPoint szone) {
        final double THRESHOLD = 2.;
        if (Math.abs(szone.getX() - fzone.getX()) > THRESHOLD && Math.abs(szone.getY() - fzone.getY()) > THRESHOLD) {
            return false;
        }

        return false;
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
            final List<Point> path = new LinkedList<>();
            for (NdPoint p : plannedMoves) {
                path.add(new Point(p.getX(), p.getY()));
            }
            if (plannedMoves.size() > 0) {
                NdPoint p = this.getLocation();
                path.add(0, new Point(p.getX(), p.getY()));
            }
            displayedPath.setPath(path);
        }
    }

    /**
     * sets the target location of the robot
     *
     * @param p point to which we want to target
     */
    @Override
    public void setTargetLocation(NdPoint p) {
        // clear old path.
        plannedMoves.clear();
        Zone startpt = ZoneLocator.getNearestZone(this.getLocation());
        Zone targetpt = ZoneLocator.getNearestZone(p);
        Collection<Zone> allnavs = getServerMap().getObjectsFromContext(Zone.class);

        plannedMoves.addAll(planPath(this.getLocation(), p, startpt, targetpt, allnavs));

        updateDrawPath();
        // make the bot use the new path.
        useNextTarget();
    }

    @Override
    public void setTarget(BoundedMoveableObject target) {
        // clear old path.
        plannedMoves.clear();
        Zone startpt = ZoneLocator.getNearestZone(this.getLocation());
        Zone targetpt = ZoneLocator.getNearestZone(target.getLocation());
        List<Zone> allnavs = new ArrayList<>(getServerMap().getObjectsFromContext(Zone.class));

        // plan the path between the Zones
        List<NdPoint> path = PathPlanner.findPath(allnavs, startpt, targetpt);
        if (path.isEmpty()) {
            throw new IllegalArgumentException("target " + target + " is unreachable from " + this);
        }
        // and copy Zone path to our stack.
        for (NdPoint p : path) {
            plannedMoves.add(p);
        }
        // and add the real target
        plannedMoves.add(target.getLocation());

        // make the bot use the new path.
        updateDrawPath();
        useNextTarget();
    }

    @Override
    public State getState() {
        if (currentMove == null && plannedMoves.isEmpty()) {
            return State.ARRIVED;
        }
        return State.TRAVELING;
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
         * Traveling state
         */
        TRAVELING
    }
}
