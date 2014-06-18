package nl.tudelft.bw4t.client.controller;

import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.bw4t.client.controller.percept.processors.ColorProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.EPartnerProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.HoldingProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.LocationProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.NegationProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.OccupiedProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.PerceptProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.PositionProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.RobotProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.RobotSizeProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.SequenceIndexProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.SequenceProcessor;
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
    
    private Map<String, PerceptProcessor> perceptProcessors;
    
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
        perceptProcessors = new HashMap<String, PerceptProcessor>();
        perceptProcessors.put("not", new NegationProcessor());
        perceptProcessors.put("robot", new RobotProcessor());
        perceptProcessors.put("occupied", new OccupiedProcessor());
        perceptProcessors.put("holding", new HoldingProcessor());
        perceptProcessors.put("position", new PositionProcessor());
        perceptProcessors.put("color", new ColorProcessor());
        perceptProcessors.put("epartner", new EPartnerProcessor());
        perceptProcessors.put("sequence", new SequenceProcessor());
        perceptProcessors.put("sequenceIndex", new SequenceIndexProcessor());
        perceptProcessors.put("location", new LocationProcessor());
        perceptProcessors.put("robotSize", new RobotSizeProcessor());
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
    public void addOccupiedRoom(String name) {
        getOccupiedRooms().add(getMap().getZone(name));
    }

    /**
     * Removes the occupied room from the list of occupied rooms.
     * 
     * @param name
     *            the name
     */
    public void removeOccupiedRoom(String name) {
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

    public ViewBlock getBlock(Long id) {
        ViewBlock b = allBlocks.get(id);
        if (b == null) {
            b = new ViewBlock();
            allBlocks.put(id, b);
        }
        return b;
    }
    
    /**
     * @param id of the block to be checked
     * @return true iff the block is in in the environment
     */
    public boolean containsBlock(Long id) {
        return allBlocks.containsKey(id);
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
        PerceptProcessor processor = perceptProcessors.get(name);
        if (processor != null) {
            processor.process(perceptParameters, this);
        }
    }


    public ViewEPartner addEPartner(long id, long holderId) {
        LOGGER.info("creating epartner(" + id + ", " + holderId + ")");
        ViewEPartner epartner = new ViewEPartner();
        epartner.setId(id);
        getVisibleEPartners().add(epartner);
        return epartner;
    }

    /* (non-Javadoc)
     * @see nl.tudelft.bw4t.map.renderer.AbstractMapController#run()
     */
    @Override
    public void run() {
        List<Percept> percepts;
        try {
            percepts = PerceptsHandler.getAllPerceptsFromEntity(getTheBot().getName(),
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
    }

    /* (non-Javadoc)
     * @see nl.tudelft.bw4t.map.renderer.AbstractMapController#updateRenderer(nl.tudelft.bw4t.map.renderer.MapRendererInterface)
     */
    @Override
    protected void updateRenderer(MapRendererInterface mri) {
        mri.validate();
        mri.repaint();
    }
}
