package nl.tudelft.bw4t.environmentstore.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import nl.tudelft.bw4t.environmentstore.editor.menu.controller.FileFilters;

import org.junit.Test;

public class FileFiltersTest {

    File xmlFile = new File("test.xml");

    File mapFile = new File("test.map");

    File goalFile = new File("test.goal");

    File mas2gFile = new File("test.mas2g");

    /**
     * Tests whether the XML filter accepts the right extension.
     */
    @Test
    public final void testXMLFilter() {
        assertTrue(FileFilters.xmlFilter().accept(xmlFile));
        assertFalse(FileFilters.xmlFilter().accept(mapFile));
        assertFalse(FileFilters.xmlFilter().accept(goalFile));
        assertFalse(FileFilters.xmlFilter().accept(mas2gFile));
    }
    
    /**
     * Tests whether the MAP filter accepts the right extension.
     */
    @Test
    public final void testMAPFilter() {
        assertFalse(FileFilters.mapFilter().accept(xmlFile));
        assertTrue(FileFilters.mapFilter().accept(mapFile));
        assertFalse(FileFilters.mapFilter().accept(goalFile));
        assertFalse(FileFilters.mapFilter().accept(mas2gFile));
    }
    
    /**
     * Tests whether the GOAL filter accepts the right extension.
     */
    @Test
    public final void testGOALFilter() {
        assertFalse(FileFilters.goalFilter().accept(xmlFile));
        assertFalse(FileFilters.goalFilter().accept(mapFile));
        assertTrue(FileFilters.goalFilter().accept(goalFile));
        assertFalse(FileFilters.goalFilter().accept(mas2gFile));
    }
    
    /**
     * Tests whether the XML filter accepts the right extension.
     */
    @Test
    public final void testMAS2GFilter() {
        assertFalse(FileFilters.masFilter().accept(xmlFile));
        assertFalse(FileFilters.masFilter().accept(mapFile));
        assertFalse(FileFilters.masFilter().accept(goalFile));
        assertTrue(FileFilters.masFilter().accept(mas2gFile));
    }
}