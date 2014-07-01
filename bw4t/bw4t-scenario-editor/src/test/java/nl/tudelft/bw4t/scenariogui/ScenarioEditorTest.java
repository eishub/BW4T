package nl.tudelft.bw4t.scenariogui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.editor.controller.AbstractMenuOption;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPromptHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScenarioEditorTest {

    private ScenarioEditor editor;

    private JFileChooser filechooser;

    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";

    private static final String FILE_OPEN_PATH = BASE + "nonexistent.xml";
    
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

    @After
    public void breakItDown() {
        editor.dispose();
    }

    @Test
    public final void checkActivePane() {
        editor = new ScenarioEditor();
        MainPanel panel = new MainPanel(editor,
                new ConfigurationPanel(), new EntityPanel());
        editor.setActivePane(panel);
        assertEquals(panel, editor.getActivePane());
    }

    @Test
    public void testJAXBException() throws FileNotFoundException, JAXBException {
        OptionPrompt yesMockOption = OptionPromptHelper.getYesOptionPrompt();

        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));

        ScenarioEditor.setOptionPrompt(yesMockOption);

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Finally make sure the confirmation dialog was called.
        verify(yesMockOption, times(1))
                .showMessageDialog((Component) any(), anyString());
    }

    @Test
    public void testWindowNameDefault() {
        assertEquals("Scenario Editor - Untitled", editor.getTitle());
    }

    @Test
    public void testWindowNameChanged() {
        String filename = "Caramba.xml";

        editor.setWindowTitle(filename);
        assertEquals("Scenario Editor - " + filename, editor.getTitle());
    }
}
