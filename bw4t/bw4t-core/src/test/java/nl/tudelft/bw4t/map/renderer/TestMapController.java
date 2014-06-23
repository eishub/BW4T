package nl.tudelft.bw4t.map.renderer;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;

import org.apache.log4j.BasicConfigurator;

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
    public Set<ViewBlock> getVisibleBlocks() {
        Set<ViewBlock> set = new HashSet<ViewBlock>();
        set.add(new ViewBlock(1, BlockColor.RED, new Point2D.Double(150.0, 2.0)));
        set.add(new ViewBlock(2, BlockColor.BLUE, new Point2D.Double(2.0, 3.0)));
        set.add(new ViewBlock(2, BlockColor.BLUE, new Point2D.Double(2.0, 3.0)));
        return set;
    }

    @Override
    public Set<ViewEntity> getVisibleEntities() {
        Set<ViewEntity> set = new HashSet<ViewEntity>();
        return set;
    }

    @Override
    protected void updateRenderer(MapRendererInterface mri) {
        mri.validate();
        mri.repaint();
    }

    @Override
    public List<BlockColor> getSequence() {
        List<BlockColor> set = new LinkedList<BlockColor>();
        set.add(BlockColor.RED);
        set.add(BlockColor.BLUE);
        set.add(BlockColor.BLUE);
        return set;
    }
    
    @Override
    public Set<ViewEPartner> getVisibleEPartners() {
        Set<ViewEPartner> set = new HashSet<>();
        set.add(new ViewEPartner(0, new Point2D.Double(5.0, 4.0), false));
        set.add(new ViewEPartner(1, new Point2D.Double(10.0, 8.0), true));
        
        return set;
    }
    
    public static void main(String[] args) throws JAXBException {
        BasicConfigurator.configure();
        NewMap map = NewMap.create(TestMapController.class.getResourceAsStream("/Rainbow"));
        JFrame jan = new JFrame("Pédé");
        jan.add(new MapRenderer(new TestMapController(map)));
        jan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jan.pack();
        jan.setVisible(true);
    }
}
