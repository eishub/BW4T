package nl.tudelft.bw4t.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.MapLoader;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.Block;
import nl.tudelft.bw4t.map.view.Entity;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.view.MapRendererInterface;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import repast.simphony.context.Context;
import repast.simphony.space.Dimensions;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.util.collections.IndexedIterable;
import eis.iilang.EnvironmentState;

public class ServerMapController extends AbstractMapController {

	private final Context<Object> serverContext;
	private boolean haveRequestedFocusAlready = false;

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
		if(dropZone.size() == 0){
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
	public Set<Block> getVisibleBlocks() {
		Set<Block> blocks = new HashSet<>();
		for (Object block : serverContext.getObjects(nl.tudelft.bw4t.blocks.Block.class)) {
			blocks.add(((nl.tudelft.bw4t.blocks.Block) block).getView());
		}
		return blocks;
	}

	@Override
	public Set<Entity> getVisibleEntities() {
		Set<Entity> entities = new HashSet<>();
		for (Object robot : serverContext.getObjects(Robot.class)) {
			Robot robotTemp = (Robot) robot;
			if (robotTemp.isConnected()) {
				entities.add(robotTemp.getView());
			}
		}
		return entities;
	}

	@Override
	protected void updateRenderer(MapRendererInterface mri) {
		if (BW4TEnvironment.getInstance().getState().equals(EnvironmentState.RUNNING)
				&& !haveRequestedFocusAlready ) {
			mri.requestFocus();
			haveRequestedFocusAlready = true;
		}
		mri.validate();
		mri.repaint();
	}

}
