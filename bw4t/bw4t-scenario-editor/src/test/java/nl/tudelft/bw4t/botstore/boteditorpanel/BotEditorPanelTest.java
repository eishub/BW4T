package nl.tudelft.bw4t.botstore.boteditorpanel;

import java.util.ArrayList;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.YesMockOptionPrompt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
    /** A shared string declared here */
    private String zero;
    /** A non standard option prompt created to be used in tests */
    private YesMockOptionPrompt prompt;
    /** Test .goal file string. */
    private String goalTest = "test.goal";
    /** Test .goal extension. */
    private String goalExtension = ".goal";
    /** Mock a main panel for the nice weather test. */
    @Mock private MainPanel p;
    /** Mock an entity panel. */
    @Mock private EntityPanel entity;
    /** Save the return type of entity.getBotConfigs() */
    private ArrayList<BotConfig> l = new ArrayList<BotConfig>();
    /** setup the panel */
	@Before
    public final void setUp() {
		l.add(new BotConfig());
    	MockitoAnnotations.initMocks(this);
    	when(p.getEntityPanel()).thenReturn(entity);
    	when(entity.getSelectedBotRow()).thenReturn(0);
    	when(entity.getBotConfigs()).thenReturn(l);
        String name = "";
        zero = "0";
        spypanel = spy(panel);
        //MainPanel parent = new MainPanel(new ConfigurationPanel(), new EntityPanel());
        editor = new BotEditor(p, 1);
        panel = new BotEditorPanel(editor, p);
        prompt = spy(new YesMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(prompt);
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
    	assertEquals(zero, spypanel.getBatteryUseValueLabel().getText());
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
		assertEquals(editor.getBotEditorPanel().getSpeedSlider().getValue(), 100);
		assertEquals(editor.getBotEditorPanel().getSizeSlider().getValue(), 2);
		assertEquals(editor.getBotEditorPanel().getBatterySlider().getValue(), 10);
		assertEquals(editor.getBotEditorPanel().getNumberOfGrippersSlider().getValue(), 1);
		assertEquals(editor.getBotEditorPanel().getSizeSlider().isEnabled(), false);
		assertEquals(editor.getBotEditorPanel().getSpeedSlider().isEnabled(), false);
		assertEquals(editor.getBotEditorPanel().getBatterySlider().isEnabled(), false);
		assertEquals(editor.getBotEditorPanel().getNumberOfGrippersSlider().isEnabled(), true);
		assertEquals(editor.getBotEditorPanel().getGripperCheckbox().isSelected(), false);
		assertEquals(editor.getBotEditorPanel().getColorblindCheckbox().isSelected(), false);
		assertEquals(editor.getBotEditorPanel().getsizeoverloadCheckbox().isSelected(), false);
		assertEquals(editor.getBotEditorPanel().getmovespeedCheckbox().isSelected(), false);
		assertEquals(editor.getBotEditorPanel().getBatteryEnabledCheckbox().isSelected(), false);
		assertEquals(editor.getBotEditorPanel().getFileNameField().getText(), goalExtension);
		assertEquals(editor.getBotEditorPanel().getBotNameField().getText(), "");
		assertEquals(editor.getBotEditorPanel().getBatteryUseValueLabel().getText(), zero);
	}
	/**
	 * Test bot editor with an invalid file name.
	 */
	@Test
	public void invalidFileName() {
		BotEditorPanel bep = editor.getBotEditorPanel();
		bep.getFileNameField().setText("test.txt");
		bep.getApplyButton().doClick();
		verify(prompt).showMessageDialog(eq(bep), eq("The file name is invalid.\n"
				+ "File names should end in .goal."));
	}
	/**
	 * Test the bot editor with a file name 5
	 * characters or shorter.
	 */
	@Test
	public void tooShortFileName() {
		BotEditorPanel bep = editor.getBotEditorPanel();
		bep.getFileNameField().setText(goalExtension);
		bep.getApplyButton().doClick();
		verify(prompt).showMessageDialog(eq(bep), eq("Please specify a file name."));
	}
	/**
	 * Test the bot editor with a file name
	 * consisting of invalid characters.
	 */
	@Test
	public void nonAlphaNumericFileName() {
		BotEditorPanel bep = editor.getBotEditorPanel();
		bep.getFileNameField().setText("#$%.goal");
		bep.getApplyButton().doClick();
		verify(prompt).showMessageDialog(eq(bep), eq("Please specify a file name"
				+ " consisting of valid alphanumeric characters"
				+ " or use an existing file."));
	}
	/**
	 * Test the bot editor with an empty reference name.
	 */
	@Test
	public void emptyReferenceName() {
		BotEditorPanel bep = editor.getBotEditorPanel();
		bep.getFileNameField().setText(goalTest);
		bep.getBotNameField().setText("");
		bep.getApplyButton().doClick();
		verify(prompt).showMessageDialog(eq(bep), eq("Please specify a reference name."));
	}
	/**
	 * Test the bot editor with an invalid reference name.
	 */
	@Test
	public void invalidReferenceName() {
		BotEditorPanel bep = editor.getBotEditorPanel();
		bep.getFileNameField().setText(goalTest);
		bep.getBotNameField().setText("#$%");
		bep.getApplyButton().doClick();
		verify(prompt).showMessageDialog(eq(bep), eq("Please specify a reference name consisting "
				+ "of valid alphanumeric characters."));
	}
	/**
	 * Test bot editor under nice weather conditions.
	 */
	@Test
	public void niceWeather() {
		editor.setParent(p);
		BotEditorPanel bep = editor.getBotEditorPanel();
		bep.getFileNameField().setText(goalTest);
		bep.getBotNameField().setText("a");
		bep.getApplyButton().doClick();
		verify(prompt).showMessageDialog(eq(bep), eq("Bot configuration succesfully created."));
		//editor.setParent(mp);
	}
}
