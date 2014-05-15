package nl.tudelft.bw4t;

import nl.tudelft.bw4t.gui.panel.BotPanel;
import nl.tudelft.bw4t.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.gui.panel.MainPanel;
import org.junit.Test;
import javax.swing.*;
import static org.junit.Assert.assertEquals;

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
