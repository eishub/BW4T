package nl.tudelft.bw4t.server.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Path;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.renderer.AbstractMapController;
import nl.tudelft.bw4t.map.renderer.MapController;
import nl.tudelft.bw4t.map.renderer.MapRendererInterface;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.server.model.zone.DropZone;
import nl.tudelft.bw4t.server.model.zone.Room;
import repast.simphony.space.Dimensions;
import eis.iilang.EnvironmentState;

/**
 * the {@link MapController} used by the server.
 */
public class ServerMapController extends AbstractMapController {

    /**
     * the repast context containing block and entities.
     */
    private final BW4TServerMap serverMap;
    /**
     * make sure we focus on the map once we started the application.
     */
    private boolean haveRequestedFocusAlready = false;

    /**
     * Instantiate the MapController with the given server map used.
     * 
     * @param serverMap the server map containing the context and map
     */
    public ServerMapController(BW4TServerMap serverMap) {
        super(serverMap.getMap());
        this.serverMap = serverMap;
        getRenderSettings().setRenderEntityName(true);
        Dimensions size = serverMap.getContinuousSpace().getDimensions();
        getRenderSettings().setWorldDimensions((int) size.getWidth(), (int) size.getHeight());
    }

    @Override
    public List<BlockColor> getSequence() {
        Set<DropZone> dropZone = serverMap.getObjectsFromContext(DropZone.class);
        if (dropZone.size() <= 0) {
            return new ArrayList<>();
        }
        return dropZone.iterator().next().getSequence();
    }

    @Override
    public int getSequenceIndex() {
        Set<DropZone> dropZone = serverMap.getObjectsFromContext(DropZone.class);
        if (dropZone.size() <= 0) {
            return 0;
        }
        return dropZone.iterator().next().getSequenceIndex();
    }

    @Override
    public boolean isOccupied(Zone room) {
        for (Object roomObj : serverMap.getContext().getObjects(Room.class)) {
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
        for (Object block : serverMap.getContext().getObjects(Block.class)) {
            blocks.add(((Block) block).getView());
        }
        return blocks;
    }

    @Override
    public Set<ViewEntity> getVisibleEntities() {
        Set<ViewEntity> entities = new HashSet<>();
        for (Object robot : serverMap.getContext().getObjects(IRobot.class)) {
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
        for (Object epartner : serverMap.getContext().getObjects(EPartner.class)) {
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
        for (Object pathTemp : serverMap.getContext().getObjects(Path.class)) {
            Path path = (Path) pathTemp;
            paths.add(path);
        }
        return paths;
    }

}
