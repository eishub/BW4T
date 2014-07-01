package nl.tudelft.bw4t.server.eis;

import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Filter;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.server.eis.translators.BlockWithColorTranslator;
import nl.tudelft.bw4t.server.eis.translators.BoundedMovableObjectTranslator;
import nl.tudelft.bw4t.server.eis.translators.ColorTranslator;
import nl.tudelft.bw4t.server.eis.translators.ObjectInformationTranslator;
import nl.tudelft.bw4t.server.eis.translators.PointTranslator;
import nl.tudelft.bw4t.server.eis.translators.ZoneTranslator;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import nl.tudelft.bw4t.server.model.zone.Corridor;
import nl.tudelft.bw4t.server.model.zone.Room;
import nl.tudelft.bw4t.server.model.zone.Zone;
import nl.tudelft.bw4t.server.util.RoomLocator;
import nl.tudelft.bw4t.server.util.ZoneLocator;

import org.omg.CORBA.Environment;

import repast.simphony.context.Context;

/**
 * Interface to create an Entity that can be used for Robot or EPartner
 */
public class EPartnerEntity implements EntityInterface {

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
     * @param eP
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
    }

    /**
     * Disconnects the robot from repast.
     */
    public void disconnect() {
        // Needs to be here or Repast will cry.
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
     * @return The functionalities of the e-Partner
     */
    @AsPercept(name = "functionality", multiplePercepts = true, filter = Filter.Type.ONCE)
    public List<String> getFunctionalities() {
        return ourEPartner.getTypeList();
    }

    /**
     * Percept if the e-Partner was dropped.
     * 
     * @return id of holder
     * @throws PerceiveException
     */
    @AsPercept(name = "heldBy", multiplePercepts = false, filter = Filter.Type.ON_CHANGE_NEG)
    public long heldBy() throws PerceiveException {
        if (ourEPartner.getTypeList().contains(ViewEPartner.FORGET_ME_NOT) && ourEPartner.getHolder() != null) {
            return ourEPartner.getHolder().getId();
        }
        return -1;
    }

    /**
     * Percept if the epartner is taken, so other bots won't pick it up if a bot has forgot it.
     * 
     * @return The epartner id.
     * @throws PerceiveException
     */
    @AsPercept(name = "isTaken", multiplePercepts = false, filter = Filter.Type.ON_CHANGE_NEG)
    public long isTaken() throws PerceiveException {
        if (ourEPartner.getTypeList().contains(ViewEPartner.FORGET_ME_NOT) && ourEPartner.getHolder() != null) {
            return ourEPartner.getId();
        }
        return -1;
    }

    /**
     * Percept if the epartner is left behind, so the epartner knows when to message the holder.
     * 
     * @return 1 if it was left behind, 0 if it's still held.
     * @throws PerceiveException
     */
    @AsPercept(name = "leftBehind", multiplePercepts = false, filter = Filter.Type.ON_CHANGE)
    public int leftBehind() throws PerceiveException {
        if (ourEPartner.getTypeList().contains(ViewEPartner.FORGET_ME_NOT) && ourEPartner.getHolder() == null) {
            return 1;
        }
        return 0;
    }

    /**
     * Percept for navpoints the robot is at. Send on change. If robot is in a {@link Zone}, that zone name is returned.
     * If not, the nearest {@link Corridor} name is returned.
     * 
     * @return a list of blockID
     * @throws PerceiveException
     */
    @AsPercept(name = "at", multiplePercepts = false, filter = Filter.Type.ON_CHANGE)
    public String getAt() throws PerceiveException {
        if (ourEPartner.getTypeList().contains(ViewEPartner.GPS)) {

            Zone navpt = ZoneLocator.getNearestZone(ourEPartner.getLocation());
            if (navpt == null) {
                throw new PerceiveException(
                        "perceiving 'at' percept failed, because map has no suitable navpoint for position "
                                + ourEPartnerLocation);
            }
            return navpt.getName();
        }
        return "";
    }

    /**
     * Percept for the location of this robot Send on change
     * 
     * @return location of epartner
     */
    @AsPercept(name = "location", multiplePercepts = false, filter = Filter.Type.ON_CHANGE)
    public Point2D getLocation() {
        return new Point2D.Double(spawnLocation.getX(), spawnLocation.getY());
    }

    /**
     * Percept for the room that the player is in, null if not in a room. Send on change
     * 
     * @return room name
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
     * 
     * @return Rooms of the epartner
     * @throws PerceiveException
     */
    @AsPercept(name = "place", multiplePercepts = true, filter = Filter.Type.ONCE)
    public List<String> getRooms() throws PerceiveException {
        List<String> places = new ArrayList<String>();
        if (ourEPartner.getTypeList().contains(ViewEPartner.GPS)) {
            for (Object o : context.getObjects(Zone.class)) {
                Zone zone = (Zone) o;
                places.add(zone.getName());
            }
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
