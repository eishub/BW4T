package nl.tudelft.bw4t.controller;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.Block;
import nl.tudelft.bw4t.map.view.Entity;

public class TestMapController extends AbstractMapController {

    public TestMapController(NewMap map) {
        super(map);
    }

    @Override
    public int getSequenceIndex() {
        return 1;
    }

    @Override
    public boolean isOccupied(Zone room) {
        return false;
    }

    @Override
    public Set<Block> getVisibleBlocks() {
        Set<Block> set = new HashSet<Block>();
        set.add(new Block(1, BlockColor.RED, new Point2D.Double(150.0, 2.0)));
        set.add(new Block(2, BlockColor.BLUE, new Point2D.Double(2.0, 3.0)));
        set.add(new Block(2, BlockColor.BLUE, new Point2D.Double(2.0, 3.0)));
        return set;
    }

    @Override
    public Set<Entity> getVisibleEntities() {
        Set<Entity> set = new HashSet<Entity>();
        return set;
    }

}
