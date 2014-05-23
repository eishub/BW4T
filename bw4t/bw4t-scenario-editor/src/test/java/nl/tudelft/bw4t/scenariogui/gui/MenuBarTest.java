package nl.tudelft.bw4t.scenariogui.gui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.config.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.controllers.NoMockOptionPrompt;
import nl.tudelft.bw4t.scenariogui.controllers.YesMockOptionPrompt;
import nl.tudelft.bw4t.scenariogui.controllers.menubar.AbstractMenuOption;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created on 21-5-2014.
 *
 * @author Calvin
 */
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
     * The path of the xml file used to save dummy data
     */
    private static final String FILE_SAVE_PATH = BASE + "dummy.xml";

    /**
     * The Scenario editor that is spied upon for this test.
     */
    private ScenarioEditor editor;

    /**
     * File choose used to mock the behaviour of the user.
     */
    private JFileChooser filechooser;

    /**
     * Setup the testing environment by creating the scenario editor and
     * assigning the editor attribute to a spy object of the ScenarioEditor.
     */
    @Before
    public void setUp() {
        ScenarioEditor real_editor = new ScenarioEditor();
        editor = spy(real_editor);
        filechooser = mock(JFileChooser.class);

        // Retrieve the action listeners, it should be one.
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();
        if (listeners.length == 1) {
            AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
            menuOption.setCurrentFileChooser(filechooser);
        }

    }

    /**
     * Close the ScenarioEditor to prevent to many windows from cluttering
     * the screen during the running of the tests
     */
    @After
    public final void closeEditor() {
        editor.dispose();
    }

    /**
     * Test if the open button works.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButton() throws FileNotFoundException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig temp = BW4TClientConfig.fromXML(FILE_OPEN_PATH);

        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientIP(), temp.getClientIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientPort(), temp.getClientPort());

        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerIP(), temp.getServerIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerPort(), temp.getServerPort());
    }

    /**
     * Test if the open button works after changing the defaults and clicking yes on the prompt.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButtonNonDefaultYes() throws FileNotFoundException, JAXBException {
        // Create a YesMockOptionPrompt object to spy on.
        YesMockOptionPrompt yesMockOption = spy(new YesMockOptionPrompt());

        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH)).thenReturn(new File(FILE_OPEN_PATH));

        // Set the controllers to mock yes.
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();

        // There should be one listener, so we check that and then change the option pane.
        assert listeners.length == 1;
        AbstractMenuOption option = (AbstractMenuOption) listeners[0];
        option.setOptionPrompt(yesMockOption);

        // Change the defaults
        editor.getMainPanel().getConfigurationPanel().setClientIP("randomvalue");

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig temp = BW4TClientConfig.fromXML(FILE_OPEN_PATH);

        // Check the final state. The opened configurational file should be equal to the one in the program.
        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientIP(), temp.getClientIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientPort(), temp.getClientPort());

        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerIP(), temp.getServerIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerPort(), temp.getServerPort());

        // Finally make sure the confirmation dialog was called.
        verify(yesMockOption, times(1))
                .showConfirmDialog((Component) any(), anyObject(), anyString(), anyInt(), anyInt());
        // And the file dialog  for saving and opening
        verify(filechooser, times(1)).showOpenDialog((Component) any());
        verify(filechooser, times(1)).showSaveDialog((Component) any());

    }

    /**
     * Test if the open button works after changing the defaults and clicking yes on the prompt.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButtonNonDefaultNo() throws FileNotFoundException, JAXBException {
        // Create a YesMockOptionPrompt object to spy on.
        NoMockOptionPrompt noMockOption = spy(new NoMockOptionPrompt());

        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        // Set the controllers to mock yes.
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();

        // There should be one listener, so we check that and then change the option pane.
        assert listeners.length == 1;
        AbstractMenuOption option = (AbstractMenuOption) listeners[0];
        option.setOptionPrompt(noMockOption);

        option.setOptionPrompt(noMockOption);

        // Change the defaults
        editor.getMainPanel().getConfigurationPanel().setClientIP("randomval");

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig temp = BW4TClientConfig.fromXML(FILE_SAVE_PATH);

        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientIP(), temp.getClientIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientPort(), temp.getClientPort());

        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerIP(), temp.getServerIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerPort(), temp.getServerPort());

        // Finally make sure the confirmation dialog was called.
        verify(noMockOption, times(1))
                .showConfirmDialog((Component) any(), anyObject(), anyString(), anyInt(), anyInt());

        // File chooser should not have been called for the actual opening
        verify(filechooser, times(1)).showOpenDialog((Component) any());
    }


}
