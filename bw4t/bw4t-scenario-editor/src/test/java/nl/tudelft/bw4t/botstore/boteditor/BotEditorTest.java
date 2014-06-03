package nl.tudelft.bw4t.botstore.boteditor;

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
        MainPanel parent = new MainPanel(new ConfigurationPanel(), new EntityPanel());
        botEditor = new BotEditor(parent, "");
        BotEditorPanel panel = new BotEditorPanel("");
        botEditor.setBotEditorPanel(panel);
        botEditor.setParent(parent);
        assertEquals(parent, botEditor.getParent());
        assertEquals(panel, botEditor.getBotEditorPanel());
    }

}
