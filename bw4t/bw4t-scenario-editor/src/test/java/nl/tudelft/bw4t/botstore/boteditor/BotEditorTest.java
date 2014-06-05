package nl.tudelft.bw4t.botstore.boteditor;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

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
        ScenarioEditor main = new ScenarioEditor(new ConfigurationPanel(), entityPanel, new BW4TClientConfig());
        MainPanel parent = main.getMainPanel();
        main.getController().getModel().getBots().add(new BotConfig());
        botEditor = new BotEditor(parent, 0, main.getController().getModel());
        BotEditorPanel panel = new BotEditorPanel(botEditor, parent, main.getController().getModel());
        botEditor.setBotEditorPanel(panel);
        botEditor.setParent(parent);
        assertEquals(parent, botEditor.getParent());
        assertEquals(panel, botEditor.getBotEditorPanel());
    }

}