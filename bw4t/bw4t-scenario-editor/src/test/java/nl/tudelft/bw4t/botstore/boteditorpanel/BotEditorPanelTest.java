package nl.tudelft.bw4t.botstore.boteditorpanel;


import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;


/**
 * Test the boteditorpanel
 *
 * @author ???
 * @author Tim
 */
public class BotEditorPanelTest {

    /**
     * the frame on which we put the panel
     */
    private BotEditor editor;
    /**
     * the panel we test
     */
    private BotEditorPanel panel;
    /**
     * the spy entity of panel
     */
    private BotEditorPanel spypanel;

    /**
     * setup the panel
     */
    @Before
    public final void setUp() {
        EntityPanel entityPanel = new EntityPanel();
        entityPanel.getBotConfigs().add(new BotConfig());
        MainPanel parent = new MainPanel(new ConfigurationPanel(), entityPanel);
        editor = new BotEditor(parent, 0);
        panel = new BotEditorPanel(editor, parent);
        spypanel = spy(panel);
    }

    /**
     * dispose the frame after testing
     */
    @After
    public final void dispose() {
        editor.dispose();
    }

    /**
     * testing the initial slider setup
     */
    @Test
    public final void testInitialSliders() {
        int speed = 100;
        int size = 2;
        int cap = 10;
        int grippers = 1;
        assertEquals(speed, spypanel.getSpeedSlider().getValue());
        assertEquals(size, spypanel.getSizeSlider().getValue());
        assertEquals(cap, spypanel.getBatterySlider().getValue());
        assertEquals(grippers, spypanel.getNumberOfGrippersSlider().getValue());
    }

    /**
     * test modify sliders
     */
    @Test
    public final void testModifySliders() {
        int speed = 140;
        int size = 5;
        int cap = 100;
        int grippers = 5;
        spypanel.getSpeedSlider().setValue(speed);
        spypanel.getSizeSlider().setValue(size);
        spypanel.getBatterySlider().setValue(cap);
        spypanel.getNumberOfGrippersSlider().setValue(5);
        assertEquals(speed, spypanel.getSpeedSlider().getValue());
        assertEquals(size, spypanel.getSizeSlider().getValue());
        assertEquals(cap, spypanel.getBatterySlider().getValue());
        assertEquals(grippers, spypanel.getNumberOfGrippersSlider().getValue());
    }

    /**
     * test initial handicaps
     */
    @Test
    public final void testInitialHandicaps() {
        assertFalse(spypanel.getGripperCheckbox().isSelected());
        assertFalse(spypanel.getColorblindCheckbox().isSelected());
        assertFalse(spypanel.getCustomSizeCheckbox().isSelected());
        assertFalse(spypanel.getMovespeedCheckbox().isSelected());
        assertFalse(spypanel.getBatteryEnabledCheckbox().isSelected());
    }

    /**
     * test modify handicaps
     */
    @Test
    public final void testModifyCheckBoxes() {
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

    /**
     * test the batteryusevale
     */
    @Test
    public final void testBatteryUseValue() {
        String value = "0";
        assertEquals(value, spypanel.getBatteryUseValueLabel().getText());
    }


}
