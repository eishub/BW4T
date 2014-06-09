package nl.tudelft.bw4t.map.editor.controller;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.controller.AbstractMapController;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Entity;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;
import nl.tudelft.bw4t.view.MapRendererInterface;

public class MapPreviewController extends AbstractMapController {

	private Map map;
	
	public MapPreviewController(Map theMap) {
		super(theMap.createMap());
		this.map = theMap;
		this.getRenderSettings().setUpdateDelay(1000);
	}

	@Override
	public List<BlockColor> getSequence() {
		return getMap().getSequence();
	}

	@Override
	public int getSequenceIndex() {
		return 0;
	}

	@Override
	public boolean isOccupied(Zone room) {
		return false;
	}

	@Override
	public Set<ViewBlock> getVisibleBlocks() {
		Set<ViewBlock> blocks = new HashSet<ViewBlock>();
		
		for (Zone z : getMap().getZones()) {
	        Rectangle2D roomBox = z.getBoundingbox().getRectangle();
	        List<Rectangle2D> newblocks = new ArrayList<Rectangle2D>();
	        
	        
	        for (BlockColor color : z.getBlocks()) {
	            Rectangle2D newpos = findFreePlace(roomBox, newblocks);
	            Point2D point = new Point2D.Double(newpos.getX(), newpos.getY());
	            ViewBlock block = new ViewBlock(0, color, point);
	            blocks.add(block);
	        }
		}
		
		return blocks;
	}

	@Override
	public Set<ViewEntity> getVisibleEntities() {
		Set<ViewEntity> entities = new HashSet<ViewEntity>();
		
		for (Entity e : getMap().getEntities()) {
			ViewEntity viewe = new ViewEntity(0, e.getName(), e.getPosition().getX(), e.getPosition().getY(), new ArrayList<ViewBlock>(), 2);
			entities.add(viewe);
		}
		
		return entities;
	}

	@Override
	public Set<ViewEPartner> getVisibleEPartners() {
		return new HashSet<ViewEPartner>();
	}

	@Override
	protected void updateRenderer(MapRendererInterface mri) {
		this.setMap(map.createMap());
		mri.validate();
		mri.repaint();
	}
	

    /**
     * find an unoccupied position for a new block in the given room, where the given list of blocks are already in that
     * room. Basically this algorithm picks random points till a free position is found.
     * 
     * @param room
     * @param blocks
     */
    private static Rectangle2D findFreePlace(Rectangle2D room, List<Rectangle2D> blocks) {
        Rectangle2D block = null;
        // max number of retries
        int retryCounter = 100;
        boolean blockPlacedOK = false;
        while (!blockPlacedOK) {
            double x = room.getMinX() + room.getWidth() * Math.random();
            double y = room.getMinY() + room.getHeight() * Math.random();
            block = new Rectangle2D.Double(x, y, ViewBlock.BLOCK_SIZE, ViewBlock.BLOCK_SIZE);

            blockPlacedOK = room.contains(block);
            for (Rectangle2D bl : blocks) {
                /*
                 * 2 blocks don't overlap if either their X or Y pos differs at least 2*SIZE.
                 */
                boolean noXoverlap = Math.abs(bl.getCenterX() - x) >= 2;
                boolean noYoverlap = Math.abs(bl.getCenterY() - y) >= 2;
                boolean noOverlap = noXoverlap || noYoverlap;
                blockPlacedOK = blockPlacedOK && noOverlap;
            }
            if (retryCounter-- == 0 && !blockPlacedOK) {
                throw new IllegalStateException("room is too small to fit more blocks");
            }
        }
        return block;
    }

}
