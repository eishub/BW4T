package nl.tudelft.bw4t.botstore.boteditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

public class BotEditorTest {
    
    private BotEditor botEditor;
    
    @Test
    public void setBotEditorPane(){
    	EntityPanel entityPanel = new EntityPanel();
    	entityPanel.getBotConfigs().add(new BotConfig());
        MainPanel parent = new MainPanel(new ConfigurationPanel(), entityPanel);
        botEditor = new BotEditor(parent, 0);
        BotEditorPanel panel = new BotEditorPanel(botEditor, parent);
        botEditor.setBotEditorPanel(panel);
        botEditor.setParent(parent);
        assertEquals(parent, botEditor.getParent());
        assertEquals(panel, botEditor.getBotEditorPanel());
    }

}
