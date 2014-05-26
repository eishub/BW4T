package nl.tudelft.bw4t.client.controller;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.bw4t.controller.AbstractMapController;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.Block;
import nl.tudelft.bw4t.map.view.Entity;
import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

public class ClientMapController extends AbstractMapController {
	private final Set<Zone> occupiedRooms = new HashSet<Zone>();
	private final Entity theBot = new Entity();
	private int sequenceIndex = 0;
	private final Set<Block> visibleBlocks = new HashSet<>();
	private final Map<Long, Block> allBlocks = new HashMap<>();
	private final List<BlockColor> sequence = new LinkedList<>();

	public ClientMapController(NewMap map) {
		super(map);
		// TODO Auto-generated constructor stub
	}

	public Set<Zone> getOccupiedRooms() {
		return occupiedRooms;
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

	@Override
	public boolean isOccupied(Zone room) {
		return getOccupiedRooms().contains(room);
	}

	@Override
	public Set<Block> getVisibleBlocks() {
		return visibleBlocks;
	}

	@Override
	public Set<Entity> getVisibleEntities() {
		Set<Entity> entities = new HashSet<>();
		entities.add(theBot);
		return entities;
	}

	public Entity getTheBot() {
		return theBot;
	}

	private void removeOccupiedRoom(String name) {
		getOccupiedRooms().remove(getMap().getZone(name));
	}

	private Block getBlock(Long id) {
		Block b = allBlocks.get(id);
		if(b == null) {
			b = new Block();
			allBlocks.put(id, b);
		}
		return b;
	}

	public void handlePercepts(List<Percept> percepts) {

		getVisibleBlocks().clear();

		for (Percept percept : percepts) {
			String name = percept.getName();
			List<Parameter> parameters = percept.getParameters();

			// first process the not percepts.
			if (name.equals("not")) {
				Function function = ((Function) parameters.get(0));
				if (function.getName().equals("occupied")) {
					LinkedList<Parameter> paramOcc = function.getParameters();
					removeOccupiedRoom(((Identifier) paramOcc.get(0)).getValue());
				}
				else if (function.getName().equals("holding")) {
					theBot.getHolding().remove(((Numeral) function.getParameters().get(0)).getValue());
				}
			}
			// Prepare updated occupied rooms list
			else if (name.equals("occupied")) {

				String id = ((Identifier) parameters.get(0)).getValue();
				getOccupiedRooms().add(getMap().getZone(id));
			}
			// Check if holding a block
			else if (name.equals("holding")) {
				Long blockId = ((Numeral) percept.getParameters().get(0)).getValue().longValue();
				theBot.getHolding().put(blockId, getBlock(blockId));
			}
			else if (name.equals("position")) {
				long id = ((Numeral) parameters.get(0)).getValue().longValue();
				double x = ((Numeral) parameters.get(1)).getValue().doubleValue();
				double y = ((Numeral) parameters.get(2)).getValue().doubleValue();

				Block b = getBlock(id);
				b.setObjectId(id);
				b.setPosition(new Point2D.Double(x, y));
			}
			else if (name.equals("color")) {
				long id = ((Numeral) parameters.get(0)).getValue().longValue();
				char color = ((Identifier) parameters.get(1)).getValue().charAt(0);

				Block b = getBlock(id);
				b.setColor(BlockColor.toAvailableColor(color));
				getVisibleBlocks().add(b);
			}
			// Update group goal sequence
			else if (name.equals("sequence")) {
				for (Parameter i : parameters) {
					ParameterList list = (ParameterList) i;
					for (Parameter j : list) {
						char letter = (((Identifier) j).getValue().charAt(0));
						getSequence().add(BlockColor.toAvailableColor(letter));
					}
				}
			}
			// get the current index of the sequence
			else if (name.equals("sequenceIndex")) {
				int index = ((Numeral) parameters.get(0)).getValue().intValue();
				setSequenceIndex(index);
			}
			// Location can be updated immediately.
			else if (name.equals("location")) {
				double x = ((Numeral) parameters.get(0)).getValue().doubleValue();
				double y = ((Numeral) parameters.get(1)).getValue().doubleValue();
				theBot.setLocation(x, y);
			}
		}

	}

}
