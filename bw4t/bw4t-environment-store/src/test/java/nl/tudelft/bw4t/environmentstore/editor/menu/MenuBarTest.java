package nl.tudelft.bw4t.environmentstore.editor.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.AbstractMenuOption;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.environmentstore.util.YesMockOptionPrompt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MenuBarTest {
	
    /**
     * The base directory of all files used in the test.
     */
    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";

    /**
     * The path of the xml file used to test the open button.
     */
    private static final String FILE_OPEN_PATH = BASE + "open.xml";

    /**
     * The path of the xml file used to test the open button.
     */
    private static final String FILE_EXPORT_PATH = BASE + "export/";

    /**
     * The path of the xml file used to save dummy data
     */
    private static final String FILE_SAVE_PATH = BASE + "dummy.xml";

    /**
     * The Scenario editor that is spied upon for this test.
     */
    private EnvironmentStore envStore;
    
    /**
     * The Map Panel Controller to create the environmentstore from.
     */
    private MapPanelController mapController;

    /**
     * File choose used to mock the behaviour of the user.
     */
    private JFileChooser filechooser;

    /**
     * Setup the testing environment by creating the scenario editor and
     * assigning the editor attribute to a spy object of the ScenarioEditor.
     */
    @Before
    public void setUp() throws IOException {
    	mapController = new MapPanelController(5, 5);
    	envStore = spy(new EnvironmentStore(mapController));

        filechooser = mock(JFileChooser.class);

        /*
        Retrieve the controllers, should be one for each item.
         */
        ActionListener[] listeners = envStore.getTopMenuBar().getMenuItemFileOpen().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);
    }
    
    @After
    public final void closeEditor() throws IOException {
    	envStore.dispose();
    }
    
    
    /**
     * Test if the menu exit works
     * Case: New window, press exit without any changes.
     */
    @Test
    public void menuOptionsListenersTest() {
    	/* Reset the controller to the spied objects controller */
        assertEquals(envStore.getTopMenuBar().getMenuItemFileNew().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemFileOpen().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemFileSave().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemFileSaveAs().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemPreview().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemFileExit().getActionListeners().length, 1);
    }
    

}
