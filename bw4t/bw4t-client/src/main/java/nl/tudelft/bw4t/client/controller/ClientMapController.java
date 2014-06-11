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

import nl.tudelft.bw4t.client.environment.handlers.PerceptsHandler;
import nl.tudelft.bw4t.client.startup.Launcher;
import nl.tudelft.bw4t.controller.AbstractMapController;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.Block;
import nl.tudelft.bw4t.map.view.Entity;
import nl.tudelft.bw4t.view.MapRendererInterface;

import org.apache.log4j.Logger;

/**
 * The Class ClientMapController.
 */
public class ClientMapController extends AbstractMapController {
    /**
     * The log4j logger which writes logs.
     */
    private static final Logger LOGGER = Logger.getLogger(ClientMapController.class);

    /**
     * The ClientController used by this ClientMapController.
     */
    private final ClientController clientController;

    /** The occupied rooms. */
    private Set<Zone> occupiedRooms = new HashSet<Zone>();
    
    /** The bot entity. */
    private Entity theBot = new Entity();
    
    /** The sequence index. */
    private int sequenceIndex = 0;
    
    /** The visible blocks. */
    private Set<Block> visibleBlocks = new HashSet<>();
    
    /** A map of all blocks, relating their ID to the actual block. */
    private Map<Long, Block> allBlocks = new HashMap<>();
    
    /** The sequence of the colors of blocks which should be retrieved. */
    private List<BlockColor> sequence = new LinkedList<>();

    /**
     * Instantiates a new client map controller.
     *
     * @param map the map
     * @param controller the controller
     */
    public ClientMapController(NewMap map, ClientController controller) {
        super(map);
        clientController = controller;
    }

    /**
     * Gets the occupied rooms.
     *
     * @return the occupied rooms
     */
    public Set<Zone> getOccupiedRooms() {
        return occupiedRooms;
    }

    /**
     * Adds the occupied room.
     *
     * @param name the name
     */
    private void addOccupiedRoom(String name) {
        getOccupiedRooms().add(getMap().getZone(name));
    }

    /**
     * Removes the occupied room.
     *
     * @param name the name
     */
    private void removeOccupiedRoom(String name) {
        getOccupiedRooms().remove(getMap().getZone(name));
    }

    /**
     * Gets the the bot.
     *
     * @return the the bot
     */
    public Entity getTheBot() {
        return theBot;
    }

    /**
     * Gets the block.
     *
     * @param id the id
     * @return the block
     */
    private Block getBlock(Long id) {
        Block b = allBlocks.get(id);
        if (b == null) {
            b = new Block();
            allBlocks.put(id, b);
        }
        return b;
    }

    /**
     * Handle all of the percepts and save the data to the necessary locations.
     *
     * @param name the name
     * @param parameters the parameters
     */
    public void handlePercept(String name, List<Parameter> parameters) {
        // first process the not percepts.
        if (name.equals("not")) {
            Function function = ((Function) parameters.get(0));
            if (function.getName().equals("occupied")) {
                LinkedList<Parameter> paramOcc = function.getParameters();
                removeOccupiedRoom(((Identifier) paramOcc.get(0)).getValue());
            } else if (function.getName().equals("holding")) {
                theBot.getHolding().remove(((Numeral) function.getParameters().get(0)).getValue());
            }
        // Prepare updated occupied rooms list
    	} else if (name.equals("occupied")) {
            addOccupiedRoom(((Identifier) parameters.get(0)).getValue());
        // Check if holding a block
    	} else if (name.equals("holding")) {
            Long blockId = ((Numeral) parameters.get(0)).getValue().longValue();
            theBot.getHolding().put(blockId, getBlock(blockId));
        } else if (name.equals("position")) {
            long id = ((Numeral) parameters.get(0)).getValue().longValue();
            double x = ((Numeral) parameters.get(1)).getValue().doubleValue();
            double y = ((Numeral) parameters.get(2)).getValue().doubleValue();

            Block b = getBlock(id);
            b.setObjectId(id);
            b.setPosition(new Point2D.Double(x, y));
        } else if (name.equals("color")) {
            long id = ((Numeral) parameters.get(0)).getValue().longValue();
            char color = ((Identifier) parameters.get(1)).getValue().charAt(0);
            Block b = getBlock(id);
            b.setColor(BlockColor.toAvailableColor(color));
            getVisibleBlocks().add(b);
        // Update group goal sequence
        } else if (name.equals("sequence")) {
            for (Parameter i : parameters) {
                ParameterList list = (ParameterList) i;
                for (Parameter j : list) {
                    char letter = (((Identifier) j).getValue().charAt(0));
                    getSequence().add(BlockColor.toAvailableColor(letter));
                }
            }
        // get the current index of the sequence
        } else if (name.equals("sequenceIndex")) {
            int index = ((Numeral) parameters.get(0)).getValue().intValue();
            setSequenceIndex(index);
        // Location can be updated immediately.
        } else if (name.equals("location")) {
            double x = ((Numeral) parameters.get(0)).getValue().doubleValue();
            double y = ((Numeral) parameters.get(1)).getValue().doubleValue();
            theBot.setLocation(x, y);
        }
    }


    /** {@inheritDoc} */
    @Override
    public void run() {

        List<Percept> percepts;
        try {
            percepts = PerceptsHandler.getAllPerceptsFromEntity(getTheBot().getName() + "gui",
                    Launcher.getEnvironment());
            if (percepts != null) {
                clientController.handlePercepts(percepts);
            }
        } catch (PerceiveException e) {
            LOGGER.error("Could not correctly poll the percepts from the environment.", e);
        } catch (NoEnvironmentException e) {
            LOGGER.fatal(
                    "Could not correctly poll the percepts from the environment. " 
            + "No connection could be made to the environment", e);
            setRunning(false);
        }
        super.run();
        clientController.updatedNextFrame();
    }

    /** {@inheritDoc} */
    @Override
    protected void updateRenderer(MapRendererInterface mri) {
        clientController.updateRenderer(mri);
        mri.validate();
        mri.repaint();
    }

	/** {@inheritDoc} */
	@Override
	public List<BlockColor> getSequence() {
	    return sequence;
	}

	/** {@inheritDoc} */
	@Override
	public int getSequenceIndex() {
	    return sequenceIndex;
	}

	/**
	 * Sets the sequence index.
	 *
	 * @param sequenceIndex the new sequence index
	 */
	public void setSequenceIndex(int sequenceIndex) {
	    this.sequenceIndex = sequenceIndex;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isOccupied(Zone room) {
	    return getOccupiedRooms().contains(room);
	}

	/** {@inheritDoc} */
	@Override
	public Set<Block> getVisibleBlocks() {
	    return visibleBlocks;
	}

	/** {@inheritDoc} */
	@Override
	public Set<Entity> getVisibleEntities() {
	    Set<Entity> entities = new HashSet<>();
	    entities.add(theBot);
	    return entities;
	}
}
