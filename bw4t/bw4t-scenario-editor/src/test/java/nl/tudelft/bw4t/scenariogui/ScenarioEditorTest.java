package nl.tudelft.bw4t.scenariogui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.editor.controller.AbstractMenuOption;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.YesMockOptionPrompt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created on 15-05-2014.
 */
public class ScenarioEditorTest {

    /**
     * The Scenario editor that is spied upon for this test.
     */
    private ScenarioEditor editor;

    /**
     * File choose used to mock the behaviour of the user.
     */
    private JFileChooser filechooser;

    /**
     * The base directory of all files used in the test.
     */
    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";
    
    /**
     * The path of the xml file used to test the open button.
     */
    private static final String FILE_OPEN_PATH = BASE + "nonexistent.xml";
    
    /**
     * Setup the testing environment by creating the scenario editor and
     * assigning the editor attribute to a spy object of the ScenarioEditor.
     */
    @Before
    public void setUp() {
        editor = spy(new ScenarioEditor());

        filechooser = mock(JFileChooser.class);

        /*
        Retrieve the controllers, should be one for each item.
         */
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);
    }

    /**
     * Dispose of the editor.
     */
    @After
    public void breakItDooooown() {
        editor.dispose();
    }

    /**
     * Tests whether the active pane gets set correctly.
     */
    @Test
    public final void checkActivePane() {
        editor = new ScenarioEditor();
        MainPanel panel = new MainPanel(editor,
                new ConfigurationPanel(), new EntityPanel());
        editor.setActivePane(panel);
        assertEquals(panel, editor.getActivePane());
    }

    /**
     * Tests whether the JAXBException is handled correctly
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException JAXBException, also called in some cases when a file is not found
     * by JAXB itself.
     */
    @Test
    public void testJAXBException() throws FileNotFoundException, JAXBException {
        YesMockOptionPrompt yesMockOption = spy(new YesMockOptionPrompt());

        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));

        ScenarioEditor.setOptionPrompt(yesMockOption);

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Finally make sure the confirmation dialog was called.
        verify(yesMockOption, times(1))
                .showMessageDialog((Component) any(), anyString());
    }

    /**
     * Test the default window name.
     */
    @Test
    public void testWindowNameDefault() {
        assertEquals("Scenario Editor - Untitled", editor.getTitle());
    }

    /**
     * Test if the window name changes work.
     */
    @Test
    public void testWindowNameChanged() {
        String filename = "Caramba.xml";

        editor.setWindowTitle(filename);
        assertEquals("Scenario Editor - " + filename, editor.getTitle());
    }
}
