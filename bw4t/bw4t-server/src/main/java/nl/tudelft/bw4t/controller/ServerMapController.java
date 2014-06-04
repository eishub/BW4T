package nl.tudelft.bw4t.controller;

import eis.iilang.EnvironmentState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.MapLoader;
import nl.tudelft.bw4t.handicap.IRobot;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEntity;
import nl.tudelft.bw4t.robots.AbstractRobot;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.view.MapRendererInterface;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import repast.simphony.context.Context;
import repast.simphony.space.Dimensions;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.util.collections.IndexedIterable;

/**
 * the {@link MapController} used by the server.
 */
public class ServerMapController extends AbstractMapController {

    /**
     * the repast context containing block and entities.
     */
    private final Context<Object> serverContext;
    /**
     * make sure we focus on the map once we started the application.
     */
    private boolean haveRequestedFocusAlready = false;

    /**
     * Instantiate the MapController with the given repast context and the map used.
     * 
     * @param map
     *            the map to be displayed
     * @param context
     *            the repast context containing block and entities
     */
    public ServerMapController(NewMap map, Context<Object> context) {
        super(map);
        getRenderSettings().setRenderEntityName(true);
        serverContext = context;
        Dimensions size = ((ContinuousSpace) context.getProjection(MapLoader.PROJECTION_ID)).getDimensions();
        getRenderSettings().setWorldDimensions((int) size.getWidth(), (int) size.getHeight());
    }

    @Override
    public List<BlockColor> getSequence() {
        IndexedIterable<Object> dropZone = serverContext.getObjects(DropZone.class);
        if (dropZone.size() <= 0) {
            return new ArrayList<>();
        }
        DropZone zone = (DropZone) dropZone.get(0);
        return zone.getSequence();
    }

    @Override
    public int getSequenceIndex() {
        DropZone dropZoneTemp = null;
        for (Object dropZone : serverContext.getObjects(DropZone.class)) {
            dropZoneTemp = (DropZone) dropZone;
        }
        if (dropZoneTemp != null) {
            return dropZoneTemp.getSequenceIndex();
        }
        return 0;
    }

    @Override
    public boolean isOccupied(Zone room) {
        for (Object roomObj : serverContext.getObjects(Room.class)) {
            Room sroom = (Room) roomObj;
            if (sroom.getName().equals(room.getName())) {
                return sroom.getOccupier() != null;
            }
        }
        return false;
    }

    @Override
    public Set<ViewBlock> getVisibleBlocks() {
        Set<ViewBlock> blocks = new HashSet<>();
        for (Object block : serverContext.getObjects(nl.tudelft.bw4t.blocks.Block.class)) {
            blocks.add(((nl.tudelft.bw4t.blocks.Block) block).getView());
        }
        return blocks;
    }

    @Override
    public Set<ViewEntity> getVisibleEntities() {
        Set<ViewEntity> entities = new HashSet<>();
        for (Object robot : serverContext.getObjects(IRobot.class)) {
            IRobot robotTemp = (IRobot) robot;
            if (robotTemp.isConnected()) {
                entities.add(robotTemp.getView());
            }
        }
        return entities;
    }

    @Override
    protected void updateRenderer(MapRendererInterface mri) {
        if (BW4TEnvironment.getInstance().getState().equals(EnvironmentState.RUNNING) && !haveRequestedFocusAlready) {
            mri.requestFocus();
            haveRequestedFocusAlready = true;
        }
        mri.validate();
        mri.repaint();
    }

}
