package nl.tudelft.bw4t.server.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Path;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.renderer.AbstractMapController;
import nl.tudelft.bw4t.map.renderer.MapController;
import nl.tudelft.bw4t.map.renderer.MapRendererInterface;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.server.model.zone.DropZone;
import nl.tudelft.bw4t.server.model.zone.Room;
import nl.tudelft.bw4t.server.repast.MapLoader;
import repast.simphony.context.Context;
import repast.simphony.space.Dimensions;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.util.collections.IndexedIterable;
import eis.iilang.EnvironmentState;

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
        for (Object block : serverContext.getObjects(nl.tudelft.bw4t.server.model.blocks.Block.class)) {
            blocks.add(((nl.tudelft.bw4t.server.model.blocks.Block) block).getView());
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
    public Set<ViewEPartner> getVisibleEPartners() {
        Set<ViewEPartner> epartners = new HashSet<>();
        for (Object epartner : serverContext.getObjects(EPartner.class)) {
            EPartner epartnerTemp = (EPartner) epartner;
            epartners.add(epartnerTemp.getView());
        }
        return epartners;
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

    @Override
    public Set<Path> getPaths() {
        Set<Path> paths = new HashSet<Path>();
        for(Object pathTemp : serverContext.getObjects(Path.class)) {
            Path path = (Path) pathTemp;
            paths.add(path);
        }
        return paths;
    }

}
