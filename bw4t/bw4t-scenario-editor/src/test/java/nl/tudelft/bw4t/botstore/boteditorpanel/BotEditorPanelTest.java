package nl.tudelft.bw4t.botstore.boteditorpanel;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;


/**
 * Test the boteditorpanel
 * @author Arun
 * @author Tim
 */
public class BotEditorPanelTest {

    /** the frame on which we put the panel */
    private BotEditor editor;
    /** the panel we test */
    private BotEditorPanel panel;
    /** the spy entity of panel */
    private BotEditorPanel spypanel;

    /** setup the panel */
    @Before
    public final void setUp() {
        EntityPanel entityPanel = new EntityPanel();
        entityPanel.getBotConfigs().add(new BotConfig());
        MainPanel parent = new MainPanel(new ConfigurationPanel(), entityPanel);
        editor = new BotEditor(parent, 0);
        panel = new BotEditorPanel(editor, parent);
        spypanel = spy(panel);
    }

    /** dispose the frame after testing */
    @After
    public final void dispose() {
        editor.dispose();
    }

    /** testing the initial slider setup */
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

    /** test modify sliders */
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

    /** test initial handicaps */
    @Test
    public final void testInitialHandicaps() {
        assertFalse(spypanel.getGripperCheckbox().isSelected());
        assertFalse(spypanel.getColorblindCheckbox().isSelected());
        assertFalse(spypanel.getCustomSizeCheckbox().isSelected());
        assertFalse(spypanel.getmovespeedCheckbox().isSelected());
        assertFalse(spypanel.getBatteryEnabledCheckbox().isSelected());
    }

    /** test modify handicaps */
    @Test
    public final void testModifyCheckBoxes() {
        spypanel.getGripperCheckbox().setSelected(true);
        spypanel.getColorblindCheckbox().setSelected(true);
        spypanel.getCustomSizeCheckbox().setSelected(true);
        spypanel.getmovespeedCheckbox().setSelected(true);
        spypanel.getBatteryEnabledCheckbox().setSelected(true);
        assertTrue(spypanel.getGripperCheckbox().isSelected());
        assertTrue(spypanel.getColorblindCheckbox().isSelected());
        assertTrue(spypanel.getCustomSizeCheckbox().isSelected());
        assertTrue(spypanel.getmovespeedCheckbox().isSelected());
        assertTrue(spypanel.getBatteryEnabledCheckbox().isSelected());
    }

    /** test the batteryusevalue */
    @Test
    public final void testBatteryUseValue() {
        assertEquals("0", spypanel.getBatteryUseValueLabel().getText());
    }

    /** Test the speed slider */
    @Test
    public final void testSpeedSliderValue() {
        int i = editor.getBotEditorPanel().getSpeedSlider().getValue();
        assertEquals(i, 100);
    }

    /** Test the enabling of the speed slider */
    @Test
    public final void testSpeedSliderEnable() {
        editor.getBotEditorPanel().getmovespeedCheckbox().doClick();
        assertTrue(editor.getBotEditorPanel().getSpeedSlider().isEnabled());
    }

    /** Test the enabling of the size slider */
    @Test
    public final void testSizeSliderEnable() {
        editor.getBotEditorPanel().getsizeoverloadCheckbox().doClick();
        assertTrue(editor.getBotEditorPanel().getSizeSlider().isEnabled());
    }

    /** Test the enabling of the size slider */
    @Test
    public final void testCapacitySliderEnable() {
        editor.getBotEditorPanel().getBatteryEnabledCheckbox().doClick();
        assertTrue(editor.getBotEditorPanel().getBatterySlider().isEnabled());
    }

    /** Test the disabling of the speed slider */
    @Test
    public final void testSpeedSliderDisable() {
        editor.getBotEditorPanel().getmovespeedCheckbox().setSelected(true);
        editor.getBotEditorPanel().getmovespeedCheckbox().doClick();
        assertFalse(editor.getBotEditorPanel().getSpeedSlider().isEnabled());
    }

    /** Test the disabling of the size slider */
    @Test
    public final void testSizeSliderDisable() {
        editor.getBotEditorPanel().getsizeoverloadCheckbox().setSelected(true);
        editor.getBotEditorPanel().getsizeoverloadCheckbox().doClick();
        assertFalse(editor.getBotEditorPanel().getSizeSlider().isEnabled());
    }

    /** Test the disabling of the size slider */
    @Test
    public final void testCapacitySliderDisable() {
        editor.getBotEditorPanel().getBatteryEnabledCheckbox().setSelected(true);
        editor.getBotEditorPanel().getBatteryEnabledCheckbox().doClick();
        assertFalse(editor.getBotEditorPanel().getBatterySlider().isEnabled());
    }

    /** Test the disabling of the gripper slider */
    @Test
    public final void testGripperSliderDisable() {
        editor.getBotEditorPanel().getGripperCheckbox().doClick();
        assertFalse(editor.getBotEditorPanel().getNumberOfGrippersSlider().isEnabled());
    }

    /** Test the enabling of the gripper slider */
    @Test
    public final void testGripperSliderEnable() {
        editor.getBotEditorPanel().getGripperCheckbox().setSelected(true);
        editor.getBotEditorPanel().getGripperCheckbox().doClick();
        assertTrue(editor.getBotEditorPanel().getNumberOfGrippersSlider().isEnabled());
    }

    /** Test the selection of the color blind checkbox */
    @Test
    public final void testColorBlindCheckbox() {
        editor.getBotEditorPanel().getColorblindCheckbox().doClick();
    }

    /** Test the reset button */
    @Test
    public final void testResetButtonClick() {
        editor.getBotEditorPanel().getResetButton().doClick();
        assertEquals(editor.getBotEditorPanel().getSpeedSlider().getValue(), editor.getBotEditorPanel().getDataObject().getBotSpeed());
        assertEquals(editor.getBotEditorPanel().getSizeSlider().getValue(), editor.getBotEditorPanel().getDataObject().getBotSize());
        assertEquals(editor.getBotEditorPanel().getBatterySlider().getValue(), editor.getBotEditorPanel().getDataObject().getBotBatteryCapacity());
        assertEquals(editor.getBotEditorPanel().getNumberOfGrippersSlider().getValue(), editor.getBotEditorPanel().getDataObject().getGrippers());
        assertEquals(editor.getBotEditorPanel().getSizeSlider().isEnabled(), editor.getBotEditorPanel().getDataObject().getSizeOverloadHandicap());
        assertEquals(editor.getBotEditorPanel().getSpeedSlider().isEnabled(), editor.getBotEditorPanel().getDataObject().getMoveSpeedHandicap());
        assertEquals(editor.getBotEditorPanel().getBatterySlider().isEnabled(), editor.getBotEditorPanel().getDataObject().isBatteryEnabled());
        assertEquals(editor.getBotEditorPanel().getNumberOfGrippersSlider().isEnabled(), !editor.getBotEditorPanel().getDataObject().getGripperHandicap());
        assertEquals(editor.getBotEditorPanel().getGripperCheckbox().isSelected(), editor.getBotEditorPanel().getDataObject().getGripperHandicap());
        assertEquals(editor.getBotEditorPanel().getColorblindCheckbox().isSelected(), editor.getBotEditorPanel().getDataObject().getColorBlindHandicap());
        assertEquals(editor.getBotEditorPanel().getsizeoverloadCheckbox().isSelected(), editor.getBotEditorPanel().getDataObject().getSizeOverloadHandicap());
        assertEquals(editor.getBotEditorPanel().getmovespeedCheckbox().isSelected(), editor.getBotEditorPanel().getDataObject().getMoveSpeedHandicap());
        assertEquals(editor.getBotEditorPanel().getBatteryEnabledCheckbox().isSelected(), editor.getBotEditorPanel().getDataObject().isBatteryEnabled());
        assertEquals(editor.getBotEditorPanel().getFileNameField().getText(), editor.getBotEditorPanel().getDataObject().getFileName());
        assertEquals(editor.getBotEditorPanel().getBotNameField().getText(), editor.getBotEditorPanel().getDataObject().getBotName());
        assertEquals(editor.getBotEditorPanel().getBotReferenceField().getText(), editor.getBotEditorPanel().getDataObject().getReferenceName());
    }
}
