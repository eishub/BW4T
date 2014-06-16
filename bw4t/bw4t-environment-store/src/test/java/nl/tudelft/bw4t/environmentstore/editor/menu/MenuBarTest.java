package nl.tudelft.bw4t.environmentstore.editor.menu;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.AbstractMenuOption;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.environmentstore.util.NoMockOptionPrompt;
import nl.tudelft.bw4t.environmentstore.util.OptionPrompt;
import nl.tudelft.bw4t.environmentstore.util.YesMockOptionPrompt;
import nl.tudelft.bw4t.map.BlockColor;

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
    	mapController = new MapPanelController(5, 5, 5, false, false);
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
    public void testExitNoChanges() {
        /* Reset the controller to the spied objects controller */
        ActionListener[] listeners = envStore.getTopMenuBar().getMenuItemFileExit().getActionListeners();
        assert listeners.length == 1;

        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        
        envStore.setOptionPrompt(new YesMockOptionPrompt());

        /* Don't actually close the jvm */
        doNothing().when(envStore).closeEnvironmentStore();

        /* Click the exit button */
        envStore.getTopMenuBar().getMenuItemFileExit().doClick();

        /* Verify if closeScenarioEditor is called */
        verify(envStore, atLeastOnce()).closeEnvironmentStore();
    }
    

}
