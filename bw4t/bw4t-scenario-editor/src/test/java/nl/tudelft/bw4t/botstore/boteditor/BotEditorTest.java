package nl.tudelft.bw4t.botstore.boteditor;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for boteditor
 * @author Arun
 *
 */
public class BotEditorTest {

    /** The boteditor used to test */
    private BotEditor botEditor;

    /** Test the setup of boteditor */
    @Test
    public void testBotEditorPane() {
        EntityPanel entityPanel = new EntityPanel();
        entityPanel.getBotConfigs().add(new BotConfig());
        MainPanel parent = new MainPanel(new ConfigurationPanel(), entityPanel);
        botEditor = new BotEditor(parent, 0);
        BotEditorPanel panel = new BotEditorPanel(botEditor, parent);
        botEditor.setBotEditorPanel(panel);
        botEditor.setParent(parent);
        assertEquals(parent, botEditor.getParent());
        assertEquals(panel, botEditor.getBotEditorPanel());
        botEditor.setVisible(false);
    }

}