package nl.tudelft.bw4t.environmentstore.editor.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import nl.tudelft.bw4t.environmentstore.editor.model.EnvironmentMap;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;

import org.junit.BeforeClass;
import org.junit.Test;

public class MapPreviewControllerTest {
    private static MapPreviewController mprc;
    
    @BeforeClass
    public static void init(){
        EnvironmentMap model = new EnvironmentMap(10,10);
        model = new EnvironmentMap(10, 10);
        ZoneModel startzone = new ZoneModel();
        startzone.setStartZone(true);
        model.setZone(9,9, startzone);
        ZoneModel dropzone = new ZoneModel();
        dropzone.setDropZone(true);
        model.setZone(8,9, dropzone);
        MapPanelController mpc = new MapPanelController(model);
        mprc = new MapPreviewController(mpc);
        
    }

    @Test
    public void sequenceTest() {
        assertTrue(mprc.getSequence().equals(new ArrayList<BlockColor>()));
    }
    
    @Test
    public void sequenceIndexTest() {
        assertTrue(mprc.getSequenceIndex() == 0);
    }
    
    @Test
    public void occupationTest() {
        Zone zone = new Zone();
        assertFalse(mprc.isOccupied(zone));
    }

    @Test
    public void getDropzoneTest() {
        assertTrue(mprc.getDropZone().getName().equals("DropZone"));
    }
    
    @Test
    public void  getVisibleBlocksTest() {
        assertTrue(mprc.getVisibleBlocks().equals(new HashSet<ViewBlock>()));
    }
    
    @Test
    public void  getVisibleEntitiesTest() {
        assertTrue(mprc.getVisibleBlocks().equals(new HashSet<ViewEntity>()));
    }
    
    @Test
    public void getVisibleEPartnerTest() {
        assertTrue(mprc.getVisibleEPartners().equals(new HashSet<ViewEPartner>()));
    }
}
