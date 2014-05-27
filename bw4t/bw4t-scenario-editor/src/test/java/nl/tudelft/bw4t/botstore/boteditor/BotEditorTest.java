package nl.tudelft.bw4t.botstore.boteditor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

public class BotEditorTest {
	
	private BotEditor botEditor;
	
	@Test
	public void setBotEditorPane(){
		MainPanel parent = new MainPanel(new ConfigurationPanel(),new EntityPanel());
		botEditor = new BotEditor(parent);
		BotEditorPanel panel = new BotEditorPanel();
		botEditor.setBotEditorPanel(panel);
		botEditor.setParent(parent);
		assertEquals(parent, botEditor.getParent());
		assertEquals(panel, botEditor.getBotEditorPanel());
	}

}
