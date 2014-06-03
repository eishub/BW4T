package nl.tudelft.bw4t.eis;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.omg.CORBA.Environment;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.collections.IndexedIterable;
import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Filter;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;
import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.eis.translators.BlockWithColorTranslator;
import nl.tudelft.bw4t.eis.translators.BoundedMovableObjectTranslator;
import nl.tudelft.bw4t.eis.translators.ColorTranslator;
import nl.tudelft.bw4t.eis.translators.ObjectInformationTranslator;
import nl.tudelft.bw4t.eis.translators.PointTranslator;
import nl.tudelft.bw4t.eis.translators.ZoneTranslator;
import nl.tudelft.bw4t.robots.NavigatingRobot;
import nl.tudelft.bw4t.robots.AbstractRobot;
import nl.tudelft.bw4t.server.RobotEntityInt;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.util.RoomLocator;
import nl.tudelft.bw4t.util.ZoneLocator;
import nl.tudelft.bw4t.zone.BlocksRoom;
import nl.tudelft.bw4t.zone.Corridor;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;

/**
 * @author Valentine Mairet
 */
public class EPartnerEntity implements RobotEntityInt {

	static {
        // Register our translators.
        Translator translator = Translator.getInstance();
        translator.registerJava2ParameterTranslator(new BlockWithColorTranslator());
        translator.registerJava2ParameterTranslator(new BoundedMovableObjectTranslator());
        translator.registerJava2ParameterTranslator(new ZoneTranslator());
        translator.registerJava2ParameterTranslator(new PointTranslator());
        translator.registerJava2ParameterTranslator(new ObjectInformationTranslator());
        translator.registerJava2ParameterTranslator(new ColorTranslator());

    }

    /**
     * The log4j logger, logs to the console.
     */
    private static final Logger LOGGER = Logger.getLogger(RobotEntity.class);

	private final EPartner ourEPartner;
    private final Context<Object> context;

    /**
     * Here we store data that needs to be locked for a perception cycle. See {@link #initializePerceptionCycle()}.
     */
    private Point2D ourEPartnerLocation;
    private Point2D spawnLocation;
    private Room ourEPartnerRoom;

    /**
     * each item in messages is a list with two items: the sender and the messagetext.
     */
    private List<ArrayList<String>> messages;

    /**
     * Creates a new {@link RobotEntity} that can be launched by an EIS compatible {@link Environment}.
     * 
     * @param robot
     *            The {@link AbstractRobot} that this entity can put up for controlling in EIS.
     */
    public EPartnerEntity(EPartner eP) {
        this.ourEPartner = eP;
        this.context = eP.getContext();
        messages = new ArrayList<ArrayList<String>>();
    }

    /**
     * Connect robot to repast (to be called when an agent is connected to this entity)
     */
    @Override
    public void connect() {
        spawnLocation = new Point2D.Double(ourEPartner.getLocation().getX(), ourEPartner.getLocation().getY());
        ourEPartner.connect();
    }
    
    /**
     * Disconnects the robot from repast.
     */
    public void disconnect(){
        ourEPartner.disconnect();
    }

    /**
     * This function should be called before perception cycle is started, so that we can lock the relevant data from the
     * environment.
     */
    @Override
    public void initializePerceptionCycle() {
        ourEPartnerLocation = new Point2D.Double(ourEPartner.getLocation().getX(), ourEPartner.getLocation().getY());
        ourEPartnerRoom = RoomLocator.getRoomAt(ourEPartnerLocation.getX(), ourEPartnerLocation.getY());
    }
    
    /**
     * Percept if the e-Partner was dropped.
     */
    @AsPercept(name = "dropped", multiplePercepts = false, filter = Filter.Type.ON_CHANGE)
    public boolean wasDropped() {
    	return ourEPartner.isDropped();
    	//TODO does this actually work? replacewith percept heldBy(Enitity), with on change negation filter?!
    }
    
    //TODO how does the goal agent know what we selected in the epartner gui? it needs tobestored somewhere and sent as percept

    /**
     * Percept for navpoints the robot is at. Send on change. If robot is in a {@link Zone}, that zone name is returned.
     * If not, the nearest {@link Corridor} name is returned.
     * 
     * @return a list of blockID
     * @throws PerceiveException
     */
    @AsPercept(name = "at", multiplePercepts = false, filter = Filter.Type.ON_CHANGE)
    public String getAt() throws PerceiveException {

        Zone navpt = ZoneLocator.getNearestZone(ourEPartner.getLocation());
        if (navpt == null) {
            throw new PerceiveException(
                    "perceiving 'at' percept failed, because map has no suitable navpoint for position "
                            + ourEPartnerLocation);
        }
        return navpt.getName();
    }

    /**
     * Percept for the room that the player is in, null if not in a room. Send on change
     */
    @AsPercept(name = "in", multiplePercepts = false, filter = Filter.Type.ON_CHANGE_NEG)
    public String getRoom() {

        if (ourEPartnerRoom == null) {
            return null;
        }
        return ourEPartnerRoom.getName();
    }

    /**
     * Percept for the places in the world. Send at the beginning
     */
    @AsPercept(name = "place", multiplePercepts = true, filter = Filter.Type.ONCE)
    public List<String> getRooms() {
        List<String> places = new ArrayList<String>();
        for (Object o : context.getObjects(Zone.class)) {
            Zone zone = (Zone) o;
            places.add(zone.getName());
        }

        return places;
    }


    /**
     * Returns all messages received by the player, Send on change
     * 
     * @return the messages that were received
     */
    @AsPercept(name = "message", multiplePercepts = true, filter = Filter.Type.ALWAYS)
    public List<ArrayList<String>> getMessages() {
        List<ArrayList<String>> msg = messages;
        messages = new ArrayList<ArrayList<String>>();
        return msg;
    }

    /**
     * Instructs the robot to send a message
     * 
     * @param message
     *            , the message that should be sent
     * @param receiver
     *            , the receiver of the message (can be all or the id of another robot
     * @throws ActException
     *             if the action fails
     */
    @AsAction(name = "sendMessage")
    public void sendMessage(String receiver, String message) throws ActException {
        // Translate the message into parameters
        Parameter[] parameters = new Parameter[2];
        try {
            parameters[0] = Translator.getInstance().translate2Parameter(ourEPartner.getName())[0];
            parameters[1] = Translator.getInstance().translate2Parameter(message)[0];
        } catch (TranslationException e) {
            throw new ActException("translating of message failed:" + message, e);
        }

        // Send to all other entities (except self)
        if ("all".equals(receiver)) {
            for (String entity : BW4TEnvironment.getInstance().getEntities()) {
                BW4TEnvironment.getInstance().performClientAction(entity, new Action("receiveMessage", parameters));
            }
            // Send to a single entity
        } else {
            BW4TEnvironment.getInstance().performClientAction(receiver, new Action("receiveMessage", parameters));
        }
    }

    /**
     * Instructs the robot to receive a certain message, should only be used internally in the server environment
     * 
     * @param message
     *            , the message to be received
     * @param sender
     *            , the sender of the message
     */
    @AsAction(name = "receiveMessage")
    public void receiveMessage(String sender, String message) {
        // Add message to messageArray
        List<String> messageArray = new ArrayList<String>();
        messageArray.add(sender);
        messageArray.add(message);

        messages.add((ArrayList<String>) messageArray);
    }
}
