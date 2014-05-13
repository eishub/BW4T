package nl.tudelft.bw4t.robots;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.util.PathPlanner;
import nl.tudelft.bw4t.util.ZoneLocator;
import nl.tudelft.bw4t.zone.Zone;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

/**
 * Represents a self navigating robot in the BW4T environment. The self
 * navigation means that you can go to a Zone which then does path planning and
 * keeps driving till destination reached.
 * 
 * @author W.Pasman 22aug
 */
public class NavigatingRobot extends Robot {

	/**
	 * We keep a stack of planned motions. We may need to extend this if
	 * rotations and waiting for doors also needs to be planned. For now we keep
	 * it simple.
	 */
	Queue<NdPoint> plannedMoves = new ConcurrentLinkedQueue<NdPoint>();

	/**
	 * When a move is started, it moves from the stack to currentMove. When
	 * currnetMove is null, it's done.
	 */
	NdPoint currentMove = null;

	/**
	 * 
	 * @param name
	 * @param space
	 * @param context
	 * @param oneBotPerZone
	 *            true if max 1 bot in a zone
	 */
	public NavigatingRobot(String name, ContinuousSpace<Object> space,
			Context<Object> context, boolean oneBotPerZone) {
		super(name, space, context, oneBotPerZone);
	}

	/**
	 * Catch events when the robot stops moving. Then we have to check what to
	 * do.
	 */
	@Override
	public void stopRobot() {
		super.stopRobot(); // do the normal handling to stop the robot.
		robotStopped(); // and handle the event
	}

	/**
	 * Check if the robot reached the previous destination without problems, and
	 * then move on if necessary. This should be called whenever the robots
	 * stops.
	 */
	private void robotStopped() {
		currentMove = null;
		if (isCollided()) {
			// move failed for some reason.
			System.out
					.println("planned motion failed! Cancelling planned path. collision flag = "
							+ super.isCollided());
			plannedMoves.clear();
			return;
		}
		useNextTarget();
	}

	@Override
	public void setTargetLocation(NdPoint p) {
		if (plannedMoves == null) {
			throw new InternalError(
					"plannedMoves==null. How is this possible??");
		}
		plannedMoves.clear(); // clear old path.
		Zone startpt = ZoneLocator.getNearestZone(this.getLocation());
		Zone targetpt = ZoneLocator.getNearestZone(p);
		List<Zone> allnavs = new ArrayList<Zone>();
		for (Object o : context.getObjects(Zone.class)) {
			allnavs.add((Zone) o);
		}
		// plan the path between the Zones
		List<Zone> path = PathPlanner.findPath(allnavs, startpt, targetpt);
		if (path == null) {
			throw new IllegalArgumentException("target " + p
					+ " is unreachable from " + this);
		}
		// and copy Zone path to our stack.
		for (Zone point : path) {
			plannedMoves.add(point.getLocation());
		}
		// and add the real target
		plannedMoves.add(p);
		useNextTarget(); // make the bot use the new path.
	}

	/**
	 * Let the robot move to the next planned target on the stack. This will
	 * clear the {@link #collided} flag. Always erases the current target.
	 */
	public void useNextTarget() {
		currentMove = null;
		if (plannedMoves.isEmpty()) {
			return; // we're there.
		}

		// we arrived at the inbetween target
		currentMove = plannedMoves.poll();
		super.setTargetLocation(currentMove);
	}

	/**
	 * Set a target for the navigating robot. If your start and/or target is not
	 * near a Zone, we go through the nearest Zone.
	 * 
	 * @param target
	 */
	public void setTarget(BoundedMoveableObject target) {
		plannedMoves.clear(); // clear old path.
		Zone startpt = ZoneLocator.getNearestZone(this.getLocation());
		Zone targetpt = ZoneLocator.getNearestZone(target.getLocation());
		List<Zone> allnavs = new ArrayList<Zone>();
		for (Object o : context.getObjects(Zone.class)) {
			allnavs.add((Zone) o);
		}
		// plan the path between the Zones
		List<Zone> path = PathPlanner.findPath(allnavs, startpt, targetpt);
		if (path == null) {
			throw new IllegalArgumentException("target " + target
					+ " is unreachable from " + this);
		}
		// and copy Zone path to our stack.
		for (Zone p : path) {
			plannedMoves.add(p.getLocation());
		}
		// and add the real target
		plannedMoves.add(target.getLocation());
		useNextTarget(); // make the bot use the new path.
	}

	/** The possible states of the navigating robot */
	public enum State {
		ARRIVED, COLLIDED, TRAVELING
	}

	public State getState() {
		if (isCollided()) {
			return State.COLLIDED;
		}
		if (currentMove == null && plannedMoves.isEmpty()) {
			return State.ARRIVED;
		}
		return State.TRAVELING;
	}

}
