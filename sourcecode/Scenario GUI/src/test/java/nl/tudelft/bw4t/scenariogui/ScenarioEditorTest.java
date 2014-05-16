package nl.tudelft.bw4t.scenariogui;

import static org.junit.Assert.assertEquals;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.panel.BotPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

import org.junit.Test;

/**
 * Created on 15-05-2014
 */
public class ScenarioEditorTest {

    ScenarioEditor editor;

    @Test
    public void checkActivePane() {
        editor = new ScenarioEditor();

        MainPanel panel = new MainPanel(new ConfigurationPanel(), new BotPanel());

        editor.setActivePane(panel);
        assertEquals(panel, editor.getActivePane());
    }


}
