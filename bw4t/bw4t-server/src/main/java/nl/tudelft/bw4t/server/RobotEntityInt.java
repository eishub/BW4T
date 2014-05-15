package nl.tudelft.bw4t.server;

/**
 * class to specify for our RobotEntity that it needs to implement
 * initializePerceptionCycle. We can't cast to RobotEntity directly because
 * RobotEntity class is loaded only in Repast since it is a Repast object, we
 * can't load that class here because of access restrictions to repast classes.
 */

public interface RobotEntityInt {
	/**
	 * To be called when a new perception cycle starts. This allows the entity
	 * to 'lock' the current repast state so that percepts are coherent.
	 */
	void initializePerceptionCycle();

	/**
	 * to be called when robot is connected, so that we can inject the entity
	 * into Repast for painting. #2326
	 */
	void connect();
}