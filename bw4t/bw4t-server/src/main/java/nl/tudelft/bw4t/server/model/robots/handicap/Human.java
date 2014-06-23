package nl.tudelft.bw4t.server.model.robots.handicap;

import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;

/**
 * Creates a human bot.
 */
public class Human extends AbstractRobotDecorator {
    /**
     * The e-Partner the human is holding.
     */
    private EPartner ePartner;
    
    /**
     * Sets the handicap to active,
     * Adds the handicap to the robot handicap storage.
     * @param p is the HandicapInterface the Human wraps around.
     */
    public Human(IRobot p) {
        super(p);
        robot.getHandicapsList().add("Human");
        this.ePartner = null;
    }
    
    @Override
    public void pickUpEPartner(EPartner eP) {
        if (canPickUp(eP)) {
            this.ePartner = eP;
            eP.setHolder(this);
        }
    }
    
    /**
     * @param eP the ePartner to be picked up.
     * @return true if the human can pick it up. 
     */
    @Override
    public boolean canPickUp(BoundedMoveableObject eP) {
        if (eP instanceof EPartner) {
            return (robot.distanceTo(eP) <= AbstractRobot.ARM_DISTANCE) && this.ePartner == null;
        }
        return getParent().canPickUp(eP);
    }
    
    /**
     * The Human drops the e-Partner they are holding. 
     */
    @Override
    public void dropEPartner() {
        if (!isHoldingEPartner()) {
            return;
        }
        ePartner.setHolder(null);
        this.ePartner = null;
    }

    @Override
    public boolean isHoldingEPartner() {
        return this.ePartner != null;
    }
    
    @Override
    public EPartner getEPartner() {
        return this.ePartner;
    }
}
