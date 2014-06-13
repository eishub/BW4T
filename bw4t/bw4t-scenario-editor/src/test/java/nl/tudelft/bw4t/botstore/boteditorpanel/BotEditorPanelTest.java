
package nl.tudelft.bw4t.botstore.boteditorpanel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Test the boteditorpanel
 */
public class BotEditorPanelTest {

    private ScenarioEditor scenarioEditor;
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
        scenarioEditor = new ScenarioEditor(new ConfigurationPanel(), entityPanel, new BW4TClientConfig());
        scenarioEditor.getController().getModel().getBots().add(new BotConfig());
        MainPanel parent = scenarioEditor.getMainPanel();
        //TODO: These tests need to be fixed. 
        editor = new BotEditor(parent, 0, scenarioEditor.getController().getModel());
        panel = new BotEditorPanel(editor, parent, scenarioEditor.getController().getModel());
        spypanel = spy(panel);
    }

    /** dispose the frame after testing */
    @After
    public final void dispose() {
        scenarioEditor.dispose();
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
        BotEditorPanel botEditorPanel = editor.getBotEditorPanel();
        BotConfig config = botEditorPanel.getDataObject();
        
        botEditorPanel.getResetButton().doClick();
        
        assertEquals(botEditorPanel.getSpeedSlider().getValue(), config.getBotSpeed());
        assertEquals(botEditorPanel.getSizeSlider().getValue(), config.getBotSize());
        assertEquals(botEditorPanel.getBatterySlider().getValue(), config.getBotBatteryCapacity());
        assertEquals(botEditorPanel.getNumberOfGrippersSlider().getValue(), config.getGrippers());
        assertEquals(botEditorPanel.getSizeSlider().isEnabled(), config.getSizeOverloadHandicap());
        assertEquals(botEditorPanel.getSpeedSlider().isEnabled(), config.getMoveSpeedHandicap());
        assertEquals(botEditorPanel.getBatterySlider().isEnabled(), config.isBatteryEnabled());
        assertEquals(botEditorPanel.getNumberOfGrippersSlider().isEnabled(), !config.getGripperHandicap());
        assertEquals(botEditorPanel.getGripperCheckbox().isSelected(), config.getGripperHandicap());
        assertEquals(botEditorPanel.getColorblindCheckbox().isSelected(), config.getColorBlindHandicap());
        assertEquals(botEditorPanel.getsizeoverloadCheckbox().isSelected(), config.getSizeOverloadHandicap());
        assertEquals(botEditorPanel.getmovespeedCheckbox().isSelected(), config.getMoveSpeedHandicap());
        assertEquals(botEditorPanel.getBatteryEnabledCheckbox().isSelected(), config.isBatteryEnabled());
        assertEquals(botEditorPanel.getFileNameField().getText(), config.getFileName());
        assertEquals(botEditorPanel.getBotNameField().getText(), config.getBotName());
        assertEquals(botEditorPanel.getBotReferenceField().getText(), config.getReferenceName());
    }


}
=======

package nl.tudelft.bw4t.botstore.boteditorpanel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.spy;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.NoMockOptionPrompt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * Test the boteditorpanel
 * @author Arun
 * @author Tim
 */
public class BotEditorPanelTest {

    private ScenarioEditor scenarioEditor;
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
        scenarioEditor = new ScenarioEditor(new ConfigurationPanel(), entityPanel, new BW4TClientConfig());
        scenarioEditor.getController().getModel().getBots().add(new BotConfig());
        MainPanel parent = scenarioEditor.getMainPanel();
        editor = new BotEditor(parent, 0, scenarioEditor.getController().getModel());
        panel = new BotEditorPanel(editor, parent, scenarioEditor.getController().getModel());
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
        BotEditorPanel botEditorPanel = editor.getBotEditorPanel();
        BotConfig dataObject = botEditorPanel.getDataObject();
        botEditorPanel.getResetButton().doClick();
		assertEquals(botEditorPanel.getSpeedSlider().getValue(), dataObject.getBotSpeed());
        assertEquals(botEditorPanel.getSizeSlider().getValue(), dataObject.getBotSize());
        assertEquals(botEditorPanel.getBatterySlider().getValue(), dataObject.getBotBatteryCapacity());
        assertEquals(botEditorPanel.getNumberOfGrippersSlider().getValue(), dataObject.getGrippers());
        assertEquals(botEditorPanel.getSizeSlider().isEnabled(), dataObject.getSizeOverloadHandicap());
        assertEquals(botEditorPanel.getSpeedSlider().isEnabled(), dataObject.getMoveSpeedHandicap());
        assertEquals(botEditorPanel.getBatterySlider().isEnabled(), dataObject.isBatteryEnabled());
        assertEquals(botEditorPanel.getBatterySlider().getValue(), dataObject.getBotBatteryCapacity());
        assertEquals(botEditorPanel.getNumberOfGrippersSlider().isEnabled(), !dataObject.getGripperHandicap());
        assertEquals(botEditorPanel.getGripperCheckbox().isSelected(), dataObject.getGripperHandicap());
        assertEquals(botEditorPanel.getColorblindCheckbox().isSelected(), dataObject.getColorBlindHandicap());
        assertEquals(botEditorPanel.getsizeoverloadCheckbox().isSelected(), dataObject.getSizeOverloadHandicap());
        assertEquals(botEditorPanel.getmovespeedCheckbox().isSelected(), dataObject.getMoveSpeedHandicap());
        assertEquals(botEditorPanel.getBatteryEnabledCheckbox().isSelected(), dataObject.isBatteryEnabled());
        assertEquals(botEditorPanel.getFileNameField().getText(), dataObject.getFileName());
        assertEquals(botEditorPanel.getBotNameField().getText(), dataObject.getBotName());
        assertEquals(botEditorPanel.getBotReferenceField().getText(), dataObject.getReferenceName());
    }

    @Test
    public final void testValidFileNamePrompt() {
        NoMockOptionPrompt spyOption = spy(new NoMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);
    	editor.getBotEditorPanel().getBotNameField().setText("");
    	editor.getBotEditorPanel().getSaveButton().doClick();
    	verify(spyOption, times(1)).showMessageDialog(editor.getBotEditorPanel(), "Please specify a file name"
				+ " consisting of valid alphanumeric characters"
				+ " or use an existing file.");
    }
    
    @Test
    public final void testFileNameEndPrompt() {
        NoMockOptionPrompt spyOption = spy(new NoMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);
        editor.getBotEditorPanel().getBotNameField().setText("bob");
        editor.getBotEditorPanel().getFileNameField().setText("bob");
        editor.getBotEditorPanel().getSaveButton().doClick();
    	verify(spyOption, times(1)).showMessageDialog(editor.getBotEditorPanel(), "The file name is invalid.\n"
				+ "File names should end in .goal.");
    }
    
    @Test
    public final void testNoFileNameEndPrompt() {
        NoMockOptionPrompt spyOption = spy(new NoMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(spyOption);
        editor.getBotEditorPanel().getBotNameField().setText("bob");
        editor.getBotEditorPanel().getFileNameField().setText(".goal");
        editor.getBotEditorPanel().getSaveButton().doClick();
    	verify(spyOption, times(1)).showMessageDialog(editor.getBotEditorPanel(), "Please specify a file name.");
    }
    
    @Test 
    public final void testSaveButton() {
        BotEditorPanel botEditorPanel = editor.getBotEditorPanel();
        BotConfig dataObject = botEditorPanel.getDataObject();
    	editor.getBotEditorPanel().getBotNameField().setText("bob");
    	editor.getBotEditorPanel().getBotAmountTextField().setText("10");
    	editor.getBotEditorPanel().getBotReferenceField().setText("bobrobot");
    	editor.getBotEditorPanel().getFileNameField().setText("bob.goal");
    	editor.getBotEditorPanel().getSaveButton().doClick();
        assertEquals(botEditorPanel.getBotNameField().getText(), dataObject.getBotName());
        assertEquals(Integer.parseInt(botEditorPanel.getBotAmountTextField().getText()), dataObject.getBotAmount());
        assertEquals(botEditorPanel.getBotReferenceField().getText(), dataObject.getReferenceName());
        assertEquals(botEditorPanel.getFileNameField().getText(), dataObject.getFileName());
    }
}

