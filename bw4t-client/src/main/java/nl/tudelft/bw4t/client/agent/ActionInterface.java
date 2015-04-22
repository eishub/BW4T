package nl.tudelft.bw4t.client.agent;

import eis.exceptions.ActException;

import nl.tudelft.bw4t.client.message.BW4TMessage;

/**
 * Interface for the actions that an agent should be able to perform.
 */
public interface ActionInterface {

    /**
     * The "go to" action for specified coordinates.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @throws ActException the act exception which is thrown if the requested action cannot be performed on the server.
     */
    void goTo(double x, double y) throws ActException;
    
    /**
     * The "nagivate obstacles action". Takes a different route after colliding with obstacles.
     * @throws ActException the act exception which is thrown if the requested action cannot be performed on the server.
     */
    void navigateObstacles() throws ActException;

    /**
     * The "go to block" action. Picks up a specified block.
     *
     * @param id the id of the block to be retrieved.
     * @throws ActException the act exception which is thrown if the requested action cannot be performed on the server.
     */
    void goToBlock(long id) throws ActException;

    /**
     * The "go to" action for a specified navPoint.
     *
     * @param navPointId the navigation point id where the bot needs to travel to.
     * @throws ActException the act exception which is thrown if the requested action cannot be performed on the server.
     */
    void goTo(String navPointId) throws ActException;

    /**
     * The "pick up" action. When located at a block, picks up the block.
     *
     * @throws ActException the act exception which is thrown if the requested action cannot be performed on the server.
     */
    void pickUp() throws ActException;

    /**
     * The "put down" action. Which puts down the block somewhere in the current Room/Zone.
     *
     * @throws ActException the act exception which is thrown if the requested action cannot be performed on the server.
     */
    void putDown() throws ActException;

    /**
     * The "send message" action. Sends a BW4T Message to another agent.
     *
     * @param receiver the receiving agent
     * @param message the message to be sent
     * @throws ActException the act exception which is thrown if the requested action cannot be performed on the server.
     */
    void sendMessage(String receiver, BW4TMessage message) throws ActException;

}
