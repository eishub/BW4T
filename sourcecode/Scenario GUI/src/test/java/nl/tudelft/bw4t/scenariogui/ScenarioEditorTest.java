package nl.tudelft.bw4t.scenariogui;

import static org.junit.Assert.assertEquals;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

import org.junit.Test;


/**
 * Created on 15-05-2014.
 */
public class ScenarioEditorTest {

    /** The scenario editor with the main GUI. */
    private ScenarioEditor editor;
    /** The spy version of the scenario editor. */
    private ScenarioEditor spyEditor;

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
