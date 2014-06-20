package nl.tudelft.bw4t.server.model.robots.handicap;

import nl.tudelft.bw4t.server.model.BoundedMoveableObject;

public class GripperHandicap extends AbstractRobotDecorator {
    
    public static final String GRIPPER_HANDICAP = "Gripper";

    /**
     * Calls the super method on p,
     * Sets the handicap to active,
     * Adds the handicap to the robot handicap storage.
     * @param p HandicapInterface the GripperHandicap wraps around.
     */
    public GripperHandicap(IRobot p) {
        super(p);
        robot.getHandicapsList().add(GRIPPER_HANDICAP);
        robot.setGripperCapacity(0);
    }

    /**
     * Override of the canPickUp method,
     * The non gripping robot cannot pick up blocks.
     * @param b block which the robot tries to pick up
     * @return false
     */
    @Override
    public boolean canPickUp(BoundedMoveableObject b) {
        return false;
    }
    
    @Override
    public int getGripperCapacity() {
        return 0;
    }

    @Override
    public void setGripperCapacity(int newcap) {
    }
}
