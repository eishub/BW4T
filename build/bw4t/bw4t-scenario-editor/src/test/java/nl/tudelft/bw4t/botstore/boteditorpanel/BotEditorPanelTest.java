package nl.tudelft.bw4t.botstore.boteditorpanel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.botstore.controller.BotController;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotStoreViewInterface;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPromptHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Test the boteditorpanel
 */
public class BotEditorPanelTest {

    private ScenarioEditor scenarioEditor;
    private BotEditor editor;
    private BotEditorPanel panel;
    private BotEditorPanel spypanel;

    @Before
    public final void setUp() {
        EntityPanel entityPanel = new EntityPanel();
        scenarioEditor = new ScenarioEditor(new ConfigurationPanel(), entityPanel, new BW4TClientConfig());
        scenarioEditor.getController().getModel().getBots().add(new BotConfig());
        MainPanel parent = scenarioEditor.getMainPanel();
        BotController controller = new BotController(parent, 0, scenarioEditor.getController().getModel());
        editor = new BotEditor(controller);
        panel = new BotEditorPanel(controller);
        spypanel = spy(panel);
    }

    @After
    public final void dispose() {
        editor.dispose();
        scenarioEditor.dispose();
    }

    @Test
    public final void testInitialSliders() {
        int speed = 100;
        int size = 2;
        int cap = 10;
        int grippers = 1;
        assertEquals(speed, spypanel.getBotSpeed());
        assertEquals(size, spypanel.getBotSize());
        assertEquals(cap, spypanel.getBotBatteryCapacity());
        assertEquals(grippers, spypanel.getGrippers());
    }

    /** Test the reset button to see if the configuration stays the same. */
    @Test
    public final void testResetButtonForBotSpecClick() {
        BotStoreViewInterface bep = editor.getBotEditorPanel();
        BotConfig config = editor.getController().getBotConfig();

        editor.getBotEditorPanel().getResetButton().doClick();

        assertEquals(config.getBotSpeed(), bep.getBotSpeed());
        assertEquals(config.getBotSize(), bep.getBotSize());
        assertEquals(config.getBotBatteryCapacity(), bep.getBotBatteryCapacity());
        assertEquals(config.getGrippers(), bep.getGrippers());
        assertEquals(config.getSizeOverloadHandicap(), bep.getSizeOverloadHandicap());
        assertEquals(config.getMoveSpeedHandicap(), bep.getMoveSpeedHandicap());
        assertEquals(config.isBatteryEnabled(), bep.isBatteryEnabled());
        assertEquals(config.getGripperHandicap(), bep.getGripperHandicap());
        assertEquals(config.getColorBlindHandicap(), bep.getColorBlindHandicap());
    }

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
        assertEquals(speed, spypanel.getBotSpeed());
        assertEquals(size, spypanel.getBotSize());
        assertEquals(cap, spypanel.getBotBatteryCapacity());
        assertEquals(grippers, spypanel.getGrippers());
    }

    @Test
    public final void testInitialHandicaps() {
        assertFalse(spypanel.getGripperHandicap());
        assertFalse(spypanel.getColorBlindHandicap());
        assertFalse(spypanel.getSizeOverloadHandicap());
        assertFalse(spypanel.getMoveSpeedHandicap());
        assertFalse(spypanel.isBatteryEnabled());
    }

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

    @Test
    public final void testSpeedSliderValue() {
        int i = editor.getBotEditorPanel().getSpeedSlider().getValue();
        assertEquals(i, 100);
    }

    @Test
    public final void testSpeedSliderEnable() {
        editor.getBotEditorPanel().getMovespeedCheckbox().doClick();
        assertTrue(editor.getBotEditorPanel().getSpeedSlider().isEnabled());
    }

    @Test
    public final void testSizeSliderEnable() {
        editor.getBotEditorPanel().getCustomSizeCheckbox().doClick();
        assertTrue(editor.getBotEditorPanel().getSizeSlider().isEnabled());
    }

    @Test
    public final void testCapacitySliderEnable() {
        editor.getBotEditorPanel().getBatteryEnabledCheckbox().doClick();
        assertTrue(editor.getBotEditorPanel().getBatterySlider().isEnabled());
    }

    @Test
    public final void testSpeedSliderDisable() {
        editor.getBotEditorPanel().getMovespeedCheckbox().setSelected(true);
        editor.getBotEditorPanel().getMovespeedCheckbox().doClick();
        assertFalse(editor.getBotEditorPanel().getSpeedSlider().isEnabled());
    }

    @Test
    public final void testSizeSliderDisable() {
        editor.getBotEditorPanel().getCustomSizeCheckbox().setSelected(true);
        editor.getBotEditorPanel().getCustomSizeCheckbox().doClick();
        assertFalse(editor.getBotEditorPanel().getSizeSlider().isEnabled());
    }

    @Test
    public final void testCapacitySliderDisable() {
        editor.getBotEditorPanel().getBatteryEnabledCheckbox().setSelected(true);
        editor.getBotEditorPanel().getBatteryEnabledCheckbox().doClick();
        assertFalse(editor.getBotEditorPanel().getBatterySlider().isEnabled());
    }

    @Test
    public final void testGripperSliderDisable() {
        editor.getBotEditorPanel().getGripperCheckbox().setSelected(false);
        editor.getBotEditorPanel().getGripperCheckbox().doClick();
        assertFalse(editor.getBotEditorPanel().getNumberOfGrippersSlider().isEnabled());
    }

    @Test
    public final void testGripperSliderEnable() {
        editor.getBotEditorPanel().getGripperCheckbox().setSelected(true);
        editor.getBotEditorPanel().getGripperCheckbox().doClick();
        assertTrue(editor.getBotEditorPanel().getNumberOfGrippersSlider().isEnabled());
    }

    @Test
    public final void testColorBlindCheckbox() {
        final BotEditorPanel bep = editor.getBotEditorPanel();
        assertFalse(bep.getColorBlindHandicap());
        bep.getColorblindCheckbox().doClick();
        assertTrue(bep.getColorBlindHandicap());
    }

    @Test
    public final void testResetButtonClick() {
        BotEditorPanel botEditorPanel = editor.getBotEditorPanel();
        BotConfig config = botEditorPanel.getBotController().getBotConfig();

        botEditorPanel.getResetButton().doClick();
        assertEquals(botEditorPanel.getSpeedSlider().getValue(), config.getBotSpeed());
        assertEquals(botEditorPanel.getSizeSlider().getValue(), config.getBotSize());
        assertEquals(botEditorPanel.getBatterySlider().getValue(), config.getBotBatteryCapacity());
        assertEquals(botEditorPanel.getNumberOfGrippersSlider().getValue(), config.getGrippers());
        assertEquals(botEditorPanel.getSizeSlider().isEnabled(), config.getSizeOverloadHandicap());
        assertEquals(botEditorPanel.getSpeedSlider().isEnabled(), config.getMoveSpeedHandicap());
        assertEquals(botEditorPanel.getBatterySlider().isEnabled(), config.isBatteryEnabled());
        assertEquals(botEditorPanel.getBatterySlider().getValue(), config.getBotBatteryCapacity());
        assertEquals(botEditorPanel.getNumberOfGrippersSlider().isEnabled(), !config.getGripperHandicap());
        assertEquals(botEditorPanel.getGripperCheckbox().isSelected(), config.getGripperHandicap());
        assertEquals(botEditorPanel.getColorblindCheckbox().isSelected(), config.getColorBlindHandicap());
        assertEquals(botEditorPanel.getCustomSizeCheckbox().isSelected(), config.getSizeOverloadHandicap());
        assertEquals(botEditorPanel.getMovespeedCheckbox().isSelected(), config.getMoveSpeedHandicap());
        assertEquals(botEditorPanel.getBatteryEnabledCheckbox().isSelected(), config.isBatteryEnabled());
        assertEquals(botEditorPanel.getFileNameField().getText(), config.getFileName());
        assertEquals(botEditorPanel.getBotNameField().getText(), config.getBotName());
        assertEquals(botEditorPanel.getBotReferenceField().getText(), config.getReferenceName());
    }

    @Test
    public final void testValidFileNamePrompt() {
        OptionPrompt spyOption = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(spyOption);
        editor.getBotEditorPanel().getBotNameField().setText("");
        editor.getBotEditorPanel().getSaveButton().doClick();
        verify(spyOption, times(1)).showMessageDialog(editor.getBotEditorPanel(), "Please specify a file name"
                + " consisting of valid alphanumeric characters"
                + " or use an existing file.");
    }

    @Test
    public final void testFileNameEndPrompt() {
        OptionPrompt spyOption = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(spyOption);
        editor.getBotEditorPanel().getBotNameField().setText("bob");
        editor.getBotEditorPanel().getFileNameField().setText("bob");
        editor.getBotEditorPanel().getSaveButton().doClick();
        verify(spyOption, times(1)).showMessageDialog(editor.getBotEditorPanel(), "The file name is invalid.\n"
                + "File names should end in .goal.");
    }

    @Test
    public final void testNoFileNameEndPrompt() {
        OptionPrompt spyOption = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(spyOption);
        editor.getBotEditorPanel().getBotNameField().setText("bob");
        editor.getBotEditorPanel().getFileNameField().setText(".goal");
        editor.getBotEditorPanel().getSaveButton().doClick();
        verify(spyOption, times(1)).showMessageDialog(editor.getBotEditorPanel(), "Please specify a file name.");
    }

    @Test
    public final void testSaveButton() {
        BotEditorPanel botEditorPanel = editor.getBotEditorPanel();
        BotConfig config = botEditorPanel.getBotController().getBotConfig();
        editor.getBotEditorPanel().getBotNameField().setText("bob");
        editor.getBotEditorPanel().getBotAmountTextField().setText("10");
        editor.getBotEditorPanel().getBotReferenceField().setText("bobrobot");
        editor.getBotEditorPanel().getFileNameField().setText("bob.goal");
        editor.getBotEditorPanel().getSaveButton().doClick();
        assertEquals(botEditorPanel.getBotNameField().getText(), config.getBotName());
        assertEquals(Integer.parseInt(botEditorPanel.getBotAmountTextField().getText()), config.getBotAmount());
        assertEquals(botEditorPanel.getBotReferenceField().getText(), config.getReferenceName());
        assertEquals(botEditorPanel.getFileNameField().getText(), config.getFileName());
    }

    @Test
    public void testBatteryDischargeRate() {
        BotEditorPanel botEditorPanel = editor.getBotEditorPanel();
        BotConfig config = botEditorPanel.getBotController().getBotConfig();
        botEditorPanel.setBatterySliderEnabled(true);
        botEditorPanel.getSizeSlider().setValue(4);
        botEditorPanel.getSpeedSlider().setValue(80);
        double optimalDischarge = 0.0002 * 4 + 0.0004 * 80;
        assertEquals(config.getBotBatteryDischargeRate(), optimalDischarge, 1);
    }
}