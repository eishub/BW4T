package nl.tudelft.bw4t.scenariogui;

import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * @author        
 * @version     0.1                
 * @since       15-05-2014        
 */
public class ScenarioEditorTest {

    /** The scenario editor with the main GUI. */
    private ScenarioEditor editor;
    /** The spy version of the scenario editor. */
    private ScenarioEditor spyEditor;

    /**
     * Close the ScenarioEditor to prevent to many windows from cluttering
     * the screen during the running of the tests
     */
    @After
    public final void closeEditor() {
        editor.dispose();
    }

    /**
     * Tests whether the active pane gets set correctly.
     */
    @Test
    public final void checkActivePane() {
        editor = new ScenarioEditor();
        MainPanel panel = new MainPanel(
                new ConfigurationPanel(), new EntityPanel());
        editor.setActivePane(panel);
        assertEquals(panel, editor.getActivePane());
    }

   /* @Test
    public void testHandleException(){
        editor = new ScenarioEditor();
        spyEditor = spy(editor);
        FileNotFoundException e = new FileNotFoundException();
        String s = "test";
        Mockito.doNothing().when(spyEditor).
            showDialog(any(FileNotFoundException.class), anyString());

        spyEditor.handleException(e, s);


        verify(spyEditor, times(1)).showDialog(e, s);

    }*/


}
