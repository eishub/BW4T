package nl.tudelft.bw4t.environmentstore.sizedialog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import nl.tudelft.bw4t.environmentstore.sizedialog.view.SizeDialog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the SizeDialog frame.
 *
 */
public class SizeDialogTest {
    
    private SizeDialog sizedialog;
    private SizeDialog spysizedialog;
    
    /**
     * Setup the frame
     */
    @Before
    public final void setUp() {
        sizedialog = new SizeDialog();
        spysizedialog = spy(sizedialog);
    }
    
    /**
     * Dispose the frame after testing
     */
    @After
    public final void dispose() { 
        sizedialog.dispose();
    }
    
    /**
     * Testing the initial values of rows and columns.
     */
    @Test
    public final void testInitialSpinners() {
        int standardrows = 5;
        int standardcolumns = 5;
        assertEquals(standardrows, spysizedialog.getRows());
        assertEquals(standardcolumns, spysizedialog.getColumns());
}
    
    /**
     * Testing modifying the spinners.
     */
    @Test
    public final void testModifySpinners() {
        int rows = 7;
        int columns = 8;
        spysizedialog.getRowSpinner().setValue(rows);
        spysizedialog.getColumnsSpinner().setValue(columns);
        assertEquals(rows, spysizedialog.getRows());
        assertEquals(columns, spysizedialog.getColumns());
    }
    
    /**
     * Testing the method to get the controller.
     */
    @Test
    public final void testGetController() {
        assertTrue(spysizedialog.getSizeDialogController() != null);
    }
    /**
     * Testing to get more coverage.
     */
    @Test
    public final void testClickButton() {
        spysizedialog.getStartButton().doClick();
    }
}

