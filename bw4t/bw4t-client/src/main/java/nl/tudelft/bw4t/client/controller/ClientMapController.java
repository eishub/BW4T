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
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.renderer.AbstractMapController;
import nl.tudelft.bw4t.map.renderer.MapRendererInterface;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;

import org.apache.log4j.Logger;

public class ClientMapController extends AbstractMapController {
    /**
     * The log4j logger which writes logs.
     */
    private static final Logger LOGGER = Logger.getLogger(ClientMapController.class);

    /**
     * The ClientController used by this ClientMapController.
     */
    private final ClientController clientController;

    private Set<Zone> occupiedRooms = new HashSet<Zone>();
    private ViewEntity theBot = new ViewEntity();
    private int sequenceIndex = 0;
    private Set<ViewBlock> visibleBlocks = new HashSet<>();
    private Set<ViewEPartner> visibleEPartners = new HashSet<>();
    private Map<Long, ViewBlock> allBlocks = new HashMap<>();
    private List<BlockColor> sequence = new LinkedList<>();

    public ClientMapController(NewMap map, ClientController controller) {
        super(map);
        clientController = controller;
    }

    @Override
    public List<BlockColor> getSequence() {
        return sequence;
    }

    @Override
    public int getSequenceIndex() {
        return sequenceIndex;
    }

    public void setSequenceIndex(int sequenceIndex) {
        this.sequenceIndex = sequenceIndex;
    }

    public Set<Zone> getOccupiedRooms() {
        return occupiedRooms;
    }

    @Override
    public boolean isOccupied(Zone room) {
        return getOccupiedRooms().contains(room);
    }

    private void addOccupiedRoom(String name) {
        getOccupiedRooms().add(getMap().getZone(name));
    }

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

    public void handlePercept(String name, List<Parameter> parameters) {
        // first process the not percepts.
        if (name.equals("not")) {
            Function function = ((Function) parameters.get(0));
            if (function.getName().equals("occupied")) {
                LinkedList<Parameter> paramOcc = function.getParameters();
                removeOccupiedRoom(((Identifier) paramOcc.get(0)).getValue());
            }else if (function.getName().equals("holding")) {
                theBot.getHolding().remove(((Numeral) function.getParameters().get(0)).getValue());
            }
        }
        // Receive our robots blockId
        else if (name.equals("robot")) {
            theBot.setId(((Numeral) parameters.get(0)).getValue().longValue());
        }
        // Prepare updated occupied rooms list
        else if (name.equals("occupied")) {
            addOccupiedRoom(((Identifier) parameters.get(0)).getValue());
        
        // Check if holding a block
    	} else if (name.equals("holding")) {
            Long blockId = ((Numeral) parameters.get(0)).getValue().longValue();
            theBot.getHolding().put(blockId, getBlock(blockId));
        } else if (name.equals("position")) {
            long id = ((Numeral) parameters.get(0)).getValue().longValue();
            double x = ((Numeral) parameters.get(1)).getValue().doubleValue();
            double y = ((Numeral) parameters.get(2)).getValue().doubleValue();

            ViewBlock b = getBlock(id);
            b.setObjectId(id);
            b.setPosition(new Point2D.Double(x, y));
            ViewEPartner ep = getViewEPartner(id);
            if (ep != null) {
                ep.setLocation(b.getPosition());
            }
        } else if (name.equals("color")) {
            long id = ((Numeral) parameters.get(0)).getValue().longValue();
            char color = ((Identifier) parameters.get(1)).getValue().charAt(0);

            ViewBlock b = getBlock(id);
            b.setColor(BlockColor.toAvailableColor(color));
            getVisibleBlocks().add(b);
        // Update the state of the epartners
        } else if (name.equals("epartner")) {
            long id = ((Numeral) parameters.get(0)).getValue().longValue();
            long holderId = ((Numeral) parameters.get(1)).getValue().longValue();
            
            ViewEPartner epartner = getViewEPartner(id);
            if (epartner == null) {
                LOGGER.info("creating " +name + "("+id + ", " + holderId + ")");
                epartner = new ViewEPartner();
                epartner.setId(id);
                getVisibleEPartners().add(epartner);
            }
            if (holderId == theBot.getId()) {
                if(id != theBot.getHoldingEpartner()){
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
        else if (name.equals("robotSize")) {
            long id = ((Numeral) parameters.get(0)).getValue().longValue();
            int x = ((Numeral) parameters.get(1)).getValue().intValue();
            
            if (id == theBot.getId()) {
                theBot.setSize(x);
            }
        }
    }


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
            LOGGER.error("Could not correctly poll the percepts from the environment.", e);
        } catch (NoEnvironmentException e) {
            LOGGER.fatal(
                    "Could not correctly poll the percepts from the environment. No connection could be made to the environment",
                    e);
            setRunning(false);
        }
        super.run();
        clientController.updatedNextFrame();
    }

    @Override
    protected void updateRenderer(MapRendererInterface mri) {
        clientController.updateRenderer(mri);
        mri.validate();
        mri.repaint();
    }
}
