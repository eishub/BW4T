package nl.tudelft.bw4t.botstore.boteditorpanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;


public class BotEditorPanelTest {
    private BotEditor editor;
    private BotEditorPanel panel;
    private BotEditorPanel spypanel;
    
    @Before
    public final void setUp(){
        String name = "";
        panel = new BotEditorPanel(name);
        spypanel = spy(panel);
        MainPanel parent = new MainPanel(new ConfigurationPanel(), new EntityPanel());
        editor = new BotEditor(parent, name);
    }
    
    @After
    public final void dispose() {
        editor.dispose();
    }
    
    @Test
    public final void testInitialSliders(){
        int speed = 100;
        int size = 2;
        int cap = 10;
        assertEquals(speed, spypanel.getSpeedSlider().getValue());
        assertEquals(size, spypanel.getSizeSlider().getValue());
        assertEquals(cap, spypanel.getBatterySlider().getValue());
    }
    @Test
    public final void testModifySliders(){
        int speed = 140;
        int size = 5;
        int cap = 100;
        spypanel.getSpeedSlider().setValue(speed);
        spypanel.getSizeSlider().setValue(size);
        spypanel.getBatterySlider().setValue(cap);
        assertEquals(speed, spypanel.getSpeedSlider().getValue());
        assertEquals(size, spypanel.getSizeSlider().getValue());
        assertEquals(cap, spypanel.getBatterySlider().getValue());  
    }
    @Test
    public final void testInitialHandicaps(){
    	assertFalse(spypanel.getGripperCheckbox().isSelected());
    	assertFalse(spypanel.getColorblindCheckbox().isSelected());
    	assertFalse(spypanel.getCustomSizeCheckbox().isSelected());
    	assertFalse(spypanel.getMovespeedCheckbox().isSelected());
    	assertFalse(spypanel.getBatteryEnabledCheckbox().isSelected());
    }
    
    @Test
    public final void testModifyCheckBoxes(){
    	spypanel.getGripperCheckbox().setSelected(true);
    	spypanel.getColorblindCheckbox().setSelected(true);
    	spypanel.getCustomSizeCheckbox().setSelected(true);
    	spypanel.getMovespeedCheckbox().setSelected(true);
    	spypanel.getBatteryEnabledCheckbox().setSelected(true);
    	assertTrue(spypanel.getGripperCheckbox().isSelected());
    	assertTrue(spypanel.getColorblindCheckbox().isSelected());
    	assertTrue(spypanel.getCustomSizeCheckbox().isSelected());
    	assertTrue(spypanel.getMovespeedCheckbox().isSelected());
    	assertTrue(spypanel.getBatteryEnabledCheckbox().isSelected());
    }
}
