package nl.tudelft.bw4t.client.controller;

import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.bw4t.client.environment.PerceptsHandler;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.renderer.AbstractMapController;
import nl.tudelft.bw4t.map.renderer.MapRendererInterface;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;

import org.apache.log4j.Logger;

/**
 * The Client Map Controller.
 */
public class ClientMapController extends AbstractMapController {
    /**
     * The log4j logger which writes logs.
     */
    private static final Logger LOGGER = Logger.getLogger(ClientMapController.class);
    
    /** The exception string constant. */
    private static final String COULDNOTPOLL = "Could not correctly poll the percepts from the environment.";

    /**
     * The Client Controller used by this Client Map Controller.
     */
    private final ClientController clientController;

    /** The occupied rooms. */
    private Set<Zone> occupiedRooms = new HashSet<Zone>();
    
    /** The rendered bot. */
    private ViewEntity theBot = new ViewEntity();
    
    /** The color sequence index. */
    private int colorSequenceIndex = 0;
    
    /** The visible blocks. */
    private Set<ViewBlock> visibleBlocks = new HashSet<>();
    
    /** The visible e-partners. */
    private Set<ViewEPartner> visibleEPartners = new HashSet<>();
    
    /** All the blocks. */
    private Map<Long, ViewBlock> allBlocks = new HashMap<>();
    
    /** The color sequence. */
    private List<BlockColor> colorSequence = new LinkedList<>();

    /**
     * Instantiates a new client map controller.
     * 
     * @param map
     *            the map
     * @param controller
     *            the controller
     */
    public ClientMapController(NewMap map, ClientController controller) {
        super(map);
        clientController = controller;
    }

    @Override
    public List<BlockColor> getSequence() {
        return colorSequence;
    }

    @Override
    public int getSequenceIndex() {
        return colorSequenceIndex;
    }

    public void setSequenceIndex(int sequenceIndex) {
        this.colorSequenceIndex = sequenceIndex;
    }

    public Set<Zone> getOccupiedRooms() {
        return occupiedRooms;
    }

    /* (non-Javadoc)
     * @see nl.tudelft.bw4t.map.renderer.MapController#isOccupied(nl.tudelft.bw4t.map.Zone)
     */
    @Override
    public boolean isOccupied(Zone room) {
        return getOccupiedRooms().contains(room);
    }

    /**
     * Adds an occupied room to the list of occupied rooms.
     * 
     * @param name
     *            the name
     */
    private void addOccupiedRoom(String name) {
        getOccupiedRooms().add(getMap().getZone(name));
    }

    /**
     * Removes the occupied room from the list of occupied rooms.
     * 
     * @param name
     *            the name
     */
    private void removeOccupiedRoom(String name) {
        getOccupiedRooms().remove(getMap().getZone(name));
    }

    @Override
    public Set<ViewBlock> getVisibleBlocks() {
        return visibleBlocks;
    }

    @Override
    public Set<ViewEntity> getVisibleEntities() {
        Set<ViewEntity> entities = new HashSet<>();
        entities.add(theBot);
        return entities;
    }
    
    @Override
    public Set<ViewEPartner> getVisibleEPartners() {
        return visibleEPartners;
    }

    public ViewEPartner getViewEPartner(long id) {
        for (ViewEPartner ep : getVisibleEPartners()) {
            if (ep.getId() == id) {
                return ep;
            }
        }
        return null;
    }

    public ViewEntity getTheBot() {
        return theBot;
    }

    private ViewBlock getBlock(Long id) {
        ViewBlock b = allBlocks.get(id);
        if (b == null) {
            b = new ViewBlock();
            allBlocks.put(id, b);
        }
        return b;
    }

    /**
     * Handle all the percepts.
     * 
     * @param name
     *            the name of the percept
     * @param perceptParameters
     *            the percept parameters
     */
    public void handlePercept(String name, List<Parameter> perceptParameters) {
        switch (name) {
        case "not":
            negatedPercepts(perceptParameters);
            break;
        case "robot":
            processRobotPercept(perceptParameters);
            break;
        case "occupied":
            processOccupiedPercept(perceptParameters);
            break;
        case "holding":
            processHoldingPercept(perceptParameters);
            break;
        case "position":
            processPositionPercept(perceptParameters);
            break;
        case "color":
            processColorPercept(perceptParameters);
            break;
        case "epartner":
            processEPartnerPercept(name, perceptParameters);
            break;
        case "sequence":
            processSequencePercept(perceptParameters);
            break;
        case "sequenceIndex":
            getSequenceIndex(perceptParameters);
            break;
        case "location":
            processLocationPercept(perceptParameters);
            break;
        case "robotSize":
            processRobotSize(perceptParameters);
            break;
        default:
            break;
        }
    }

    /**
     * Process occupied percept.
     * 
     * @param perceptParameters
     *            the percept parameters
     */
    private void processOccupiedPercept(List<Parameter> perceptParameters) {
        addOccupiedRoom(((Identifier) perceptParameters.get(0)).getValue());
    }

    /**
     * Process robot percept.
     * 
     * @param perceptParameters
     *            the percept parameters
     */
    private void processRobotPercept(List<Parameter> perceptParameters) {
        theBot.setId(((Numeral) perceptParameters.get(0)).getValue().longValue());
    }

    /**
     * Process robot size.
     * 
     * @param perceptParameters
     *            the percept parameters
     */
    private void processRobotSize(List<Parameter> perceptParameters) {
        long id = ((Numeral) perceptParameters.get(0)).getValue().longValue();
        int x = ((Numeral) perceptParameters.get(1)).getValue().intValue();

        if (id == theBot.getId()) {
            theBot.setSize(x);
        }
    }

    /**
     * Gets the sequence index.
     * 
     * @param perceptParameters
     *            the percept parameters
     */
    private void getSequenceIndex(List<Parameter> perceptParameters) {
        int index = ((Numeral) perceptParameters.get(0)).getValue().intValue();
        setSequenceIndex(index);
    }

    /**
     * Process location percept.
     * 
     * @param perceptParameters
     *            the percept parameters
     */
    private void processLocationPercept(List<Parameter> perceptParameters) {
        double x = ((Numeral) perceptParameters.get(0)).getValue().doubleValue();
        double y = ((Numeral) perceptParameters.get(1)).getValue().doubleValue();
        theBot.setLocation(x, y);
    }

    /**
     * Process holding percept.
     * 
     * @param perceptParameters
     *            the percept parameters
     */
    private void processHoldingPercept(List<Parameter> perceptParameters) {
        Long blockId = ((Numeral) perceptParameters.get(0)).getValue().longValue();
        theBot.getHolding().put(blockId, getBlock(blockId));
    }

    /**
     * Process sequence percept. 
     * 
     * TODO: This function process a list of lists, seems like it should only process a list,
     * why is this?
     * 
     * @param perceptParameters
     *            the percept parameters
     */
    private void processSequencePercept(List<Parameter> perceptParameters) {
        for (Parameter i : perceptParameters) {
            ParameterList list = (ParameterList) i;
            for (Parameter j : list) {
                char letter = (((Identifier) j).getValue().charAt(0));
                getSequence().add(BlockColor.toAvailableColor(letter));
            }
        }
    }

    /**
     * Process color percept.
     * 
     * @param perceptParameters
     *            the percept parameters
     */
    private void processColorPercept(List<Parameter> perceptParameters) {
        long id = ((Numeral) perceptParameters.get(0)).getValue().longValue();
        char color = ((Identifier) perceptParameters.get(1)).getValue().charAt(0);

        ViewBlock b = getBlock(id);
        b.setColor(BlockColor.toAvailableColor(color));
        getVisibleBlocks().add(b);
    }

    /**
     * Process epartner percept.
     * 
     * @param name
     *            the name
     * @param perceptParameters
     *            the percept parameters
     */
    private void processEPartnerPercept(String name, List<Parameter> perceptParameters) {
        long id = ((Numeral) perceptParameters.get(0)).getValue().longValue();
        long holderId = ((Numeral) perceptParameters.get(1)).getValue().longValue();
        
        ViewEPartner epartner = getViewEPartner(id);
        if (epartner == null) {
            LOGGER.info("creating " + name + "(" + id + ", " + holderId + ")");
            epartner = new ViewEPartner();
            epartner.setId(id);
            getVisibleEPartners().add(epartner);
        }
        if (holderId == theBot.getId()) {
            if (id != theBot.getHoldingEpartner()){
                LOGGER.info("We are now holding the e-partner: " + id);
            }
            theBot.setHoldingEpartner(id);
        } else if (id == theBot.getHoldingEpartner()) {
            theBot.setHoldingEpartner(-1);
        }
        if (allBlocks.containsKey(id)) {
            epartner.setLocation(allBlocks.get(id).getPosition());
        }
        epartner.setPickedUp(holderId >= 0);
    }

    /**
     * Process position percept.
     * 
     * @param perceptParameters
     *            the percept parameters
     */
    private void processPositionPercept(List<Parameter> perceptParameters) {
        long id = ((Numeral) perceptParameters.get(0)).getValue().longValue();
        double x = ((Numeral) perceptParameters.get(1)).getValue().doubleValue();
        double y = ((Numeral) perceptParameters.get(2)).getValue().doubleValue();

        ViewBlock b = getBlock(id);
        b.setObjectId(id);
        b.setPosition(new Point2D.Double(x, y));
        ViewEPartner ep = getViewEPartner(id);
        if (ep != null) {
            ep.setLocation(b.getPosition());
        }
    }

    /**
     * Negated percepts.
     * 
     * @param parameters
     *            the parameters
     */
    private void negatedPercepts(List<Parameter> parameters) {
        Function function = ((Function) parameters.get(0));
        if (function.getName().equals("occupied")) {
            LinkedList<Parameter> paramOcc = function.getParameters();
            removeOccupiedRoom(((Identifier) paramOcc.get(0)).getValue());
        } else if (function.getName().equals("holding")) {
            theBot.getHolding().remove(((Numeral) function.getParameters().get(0)).getValue());
        }
    }


    /* (non-Javadoc)
     * @see nl.tudelft.bw4t.map.renderer.AbstractMapController#run()
     */
    @Override
    public void run() {
        List<Percept> percepts;
        try {
            percepts = PerceptsHandler.getAllPerceptsFromEntity(getTheBot().getName() + "gui",
                    clientController.getEnvironment());
            if (percepts != null) {
                clientController.handlePercepts(percepts);
            }
        } catch (PerceiveException e) {
            LOGGER.error(COULDNOTPOLL, e);
        } catch (NoEnvironmentException e) {
            LOGGER.fatal(COULDNOTPOLL + "No connection could be made to the environment", e);
            setRunning(false);
        }
        super.run();
        clientController.updatedNextFrame();
    }

    /* (non-Javadoc)
     * @see nl.tudelft.bw4t.map.renderer.AbstractMapController#updateRenderer(nl.tudelft.bw4t.map.renderer.MapRendererInterface)
     */
    @Override
    protected void updateRenderer(MapRendererInterface mri) {
        clientController.updateRenderer(mri);
        mri.validate();
        mri.repaint();
    }
}
