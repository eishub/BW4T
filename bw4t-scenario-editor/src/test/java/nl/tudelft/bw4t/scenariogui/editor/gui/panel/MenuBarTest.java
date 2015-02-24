package nl.tudelft.bw4t.scenariogui.editor.gui.panel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.controller.AbstractMenuOption;
import nl.tudelft.bw4t.scenariogui.editor.controller.ScenarioEditorController;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.util.AbstractTableModel;
import nl.tudelft.bw4t.scenariogui.util.ExportToMASTest;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPromptHelper;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MenuBarTest {

    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";
    private static final String FILE_OPEN_PATH = BASE + "open.xml";
    private static final String FILE_EXPORT_PATH = BASE + "export/";
    private static final String FILE_SAVE_PATH = BASE + "dummy.xml";
    private static final String FILE_MAP = BASE + "test.map";

    private ScenarioEditor editor;
    private JFileChooser filechooser;

    @Before
    public void setUp() throws IOException {
        editor = spy(new ScenarioEditor());

        filechooser = mock(JFileChooser.class);

        /*
        Retrieve the controllers, should be one for each item.
         */
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);
    }

    @After
    public final void closeEditor() throws IOException {
        editor.dispose();
    }

    /**
     * Test if the open button works for the configuration panel.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButtonConfigurationPanel() throws FileNotFoundException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Get the saved configuration
        BW4TClientConfig config = BW4TClientConfig.fromXML(FILE_OPEN_PATH);
        ConfigurationPanel configurationPanel = editor.getMainPanel().getConfigurationPanel();

        assertEquals(config.getClientIp(), configurationPanel.getClientIP());
        assertEquals(config.getClientPort(), configurationPanel.getClientPort());

        assertEquals(config.getServerIp(), configurationPanel.getServerIP());
        assertEquals(config.getServerPort(), configurationPanel.getServerPort());

        assertEquals(config.isLaunchGui(), configurationPanel.useGui());
        assertEquals(config.isVisualizePaths(), configurationPanel.isVisualizePaths());
        assertEquals(config.isCollisionEnabled(), configurationPanel.isEnableCollisions());
        assertEquals(config.getMapFile(), configurationPanel.getMapFile());
    }

    /**
     * Test if the open button works for the bot list in the entity panel.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButtonBotList() throws FileNotFoundException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig config = BW4TClientConfig.fromXML(FILE_OPEN_PATH);

        AbstractTableModel botTableModel = editor.getMainPanel().getEntityPanel().getBotTableModel();

        //Actual testing whether the values are inserted correctly
        assertEquals(config.getBot(0).getBotName(), botTableModel.getValueAt(0, 0));
        assertEquals(config.getBot(1).getBotName(), botTableModel.getValueAt(1, 0));

        assertEquals(config.getBot(0).getBotController().toString(), botTableModel.getValueAt(0, 1));
        assertEquals(config.getBot(1).getBotController().toString(), botTableModel.getValueAt(1, 1));

        assertEquals(config.getBot(0).getFileName(), botTableModel.getValueAt(0, 2));
        assertEquals(config.getBot(1).getFileName(), botTableModel.getValueAt(1, 2));

        assertEquals(config.getBot(0).getBotAmount(), Integer.parseInt(botTableModel.getValueAt(0, 3).toString()));
        assertEquals(config.getBot(1).getBotAmount(), Integer.parseInt(botTableModel.getValueAt(1, 3).toString()));
    }

    /**
     * Test if the open button works for the epartner list in the entity panel.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButtonEpartnerList() throws FileNotFoundException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig config = BW4TClientConfig.fromXML(FILE_OPEN_PATH);

        DefaultTableModel ePartnerTableModel = editor.getMainPanel().getEntityPanel().getEPartnerTableModel();

        //Actual testing whether the values are inserted correctly
        assertEquals(config.getEpartner(0).getEpartnerName(), ePartnerTableModel.getValueAt(0, 0));
        assertEquals(config.getEpartner(1).getEpartnerName(), ePartnerTableModel.getValueAt(1, 0));

        assertEquals(config.getEpartner(0).getFileName(), ePartnerTableModel.getValueAt(0, 1));
        assertEquals(config.getEpartner(1).getFileName(), ePartnerTableModel.getValueAt(1, 1));

        assertEquals(config.getEpartner(0).getEpartnerAmount(), Integer.parseInt(ePartnerTableModel.getValueAt(0,
                2).toString()));
        assertEquals(config.getEpartner(1).getEpartnerAmount(), Integer.parseInt(ePartnerTableModel.getValueAt(1,
                2).toString()));
    }

    /**
     * Tests if clicking open and then cancelling doesn't change the configuration.
     */
    @Test
    public void testOpenCancel() {
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.CANCEL_OPTION);

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        assertTrue(editor.getMainPanel().getEntityPanel().isDefault());
        assertTrue(editor.getMainPanel().getConfigurationPanel().isDefault());
    }

    /**
     * Tests if saving a saved configuration doesn't show a dialog.
     *
     * @throws IOException
     */
    @Test
    public void testSaveAfterSave() throws IOException {
        // Create the file so that the check if the file exists doesn't get triggered.
        new File(FILE_SAVE_PATH).createNewFile();
        // set the last file location, so the quick save is possible
        editor.getTopMenuBar().setLastFileLocation(FILE_SAVE_PATH);

        editor.getTopMenuBar().getMenuItemFileSave().doClick();

        //verify if the filechooser doesn't open its dialog
        verify(filechooser, never()).showDialog((Component) any(), (String) any());
    }

    /**
     * Tests if saving a saved configuration which has been deleted
     * saves correctly.
     *
     * @throws IOException
     */
    @Test
    public void testSaveAfterDeletedSave() throws IOException {
        /* Mock the file chooser for saving */
        when(filechooser.showSaveDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        /* Remove the old controller, recreate the new one using the mocked editor and
         * finally mock the filechooser.
         */
        replaceListener(editor.getTopMenuBar().getMenuItemFileSave());

        /* Fake the prompt */
        ScenarioEditor.setOptionPrompt(OptionPromptHelper.getYesOptionPrompt());

        // set the last file location without creating the file
        String testPath = "TestPath";
        assertFalse(new File(testPath).exists());
        editor.getTopMenuBar().setLastFileLocation(testPath);

        editor.getTopMenuBar().getMenuItemFileSave().doClick();

        verify(filechooser, times(1)).showDialog((Component) any(), (String) any());
        verify(filechooser, times(1)).getSelectedFile();
    }

    /**
     * Tests if the configuration doesn't ask to save when save is clicked
     * if there is no map given and clicking no on the prompt.
     */
    @Test
    public void testSaveNoMapCancel() {
        // Create a NoMockOptionPrompt object to spy on.
        OptionPrompt noMockOption = OptionPromptHelper.getNoOptionPrompt();

        // Set the controllers to mock no.
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();

        // There should be one listener, so we check that and then change the option pane.
        assert listeners.length == 1;
        ScenarioEditor.setOptionPrompt(noMockOption);

        editor.getTopMenuBar().getMenuItemFileSave().doClick();

        //verify if the confirmation dialog opened once
        verify(noMockOption, times(1)).showConfirmDialog((Component) any(), anyObject(), anyString(), anyInt(),
                anyInt());
        //verify if the filechooser doesn't open its dialog
        verify(filechooser, never()).showDialog((Component) any(), (String) any());
    }

    /**
     * Test if the open button works after changing the defaults and clicking yes on the prompt,
     * it will also ask if we want to save without a map
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButtonNonDefaultSaveNoMap() throws FileNotFoundException, JAXBException {
        // Create a YesMockOptionPrompt object to spy on.
        OptionPrompt yesMockOption = OptionPromptHelper.getYesOptionPrompt();

        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH)).thenReturn(new File(FILE_OPEN_PATH));

        // Set the controllers to mock yes.
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();

        // There should be one listener, so we check that and then change the option pane.
        assert listeners.length == 1;
        ScenarioEditor.setOptionPrompt(yesMockOption);

        // Change the defaults
        ConfigurationPanel configurationPanel = editor.getMainPanel().getConfigurationPanel();
        configurationPanel.setClientIP("randomvalue");

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig temp = BW4TClientConfig.fromXML(FILE_OPEN_PATH);

        // Check the final state. The opened configurational file should be equal to the one in the program.
        assertEquals(configurationPanel.getClientIP(), temp.getClientIp());
        assertEquals(configurationPanel.getClientPort(), temp.getClientPort());

        assertEquals(configurationPanel.getServerIP(), temp.getServerIp());
        assertEquals(configurationPanel.getServerPort(), temp.getServerPort());

        // Finally make sure the confirmation dialog was called twice, once complaining about the map.
        verify(yesMockOption, times(2))
                .showConfirmDialog((Component) any(), anyObject(), anyString(), anyInt(), anyInt());
        // And the file dialog  for saving and opening
        verify(filechooser, times(1)).showOpenDialog((Component) any());
        verify(filechooser, times(1)).showDialog((Component) any(), (String) any());

    }

    /**
     * Test if the open button works after changing the defaults and clicking yes on the prompt.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButtonNonDefaultSaveYesMap() throws FileNotFoundException, JAXBException {
        // Create a YesMockOptionPrompt object to spy on.
        OptionPrompt yesMockOption = OptionPromptHelper.getYesOptionPrompt();

        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH)).thenReturn(new File(FILE_OPEN_PATH));

        // Set the controllers to mock yes.
        ScenarioEditor.setOptionPrompt(yesMockOption);

        // Change the defaults
        ConfigurationPanel configurationPanel = editor.getMainPanel().getConfigurationPanel();
        configurationPanel.setClientIP("randomvalue");

        // Set a map.
        configurationPanel.setMapFile(BASE + "maps/Banana");

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig temp = BW4TClientConfig.fromXML(FILE_OPEN_PATH);

        // Check the final state. The opened configurational file should be equal to the one in the program.
        assertEquals(configurationPanel.getClientIP(), temp.getClientIp());
        assertEquals(configurationPanel.getClientPort(), temp.getClientPort());

        assertEquals(configurationPanel.getServerIP(), temp.getServerIp());
        assertEquals(configurationPanel.getServerPort(), temp.getServerPort());

        // Finally make sure the confirmation dialog was called once.
        verify(yesMockOption, times(1))
                .showConfirmDialog((Component) any(), anyObject(), anyString(), anyInt(), anyInt());
        // And the file dialog  for saving and opening
        verify(filechooser, times(1)).showOpenDialog((Component) any());
        verify(filechooser, times(1)).showDialog((Component) any(), (String) any());

    }

    @Test
    public void testOneListenerForMenuFileOpen() {
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();

        // There should be one listener, so we check that.
        assert listeners.length == 1;
    }

    /**
     * Test if the open button works after changing the defaults and clicking no on the prompt.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButtonNonDefaultNo() throws FileNotFoundException, JAXBException {
        // Create a NoMockOptionPrompt object to spy on.
        OptionPrompt noMockOption = OptionPromptHelper.getNoOptionPrompt();

        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        // Set the controllers to mock no.
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();

        // There should be one listener, so we check that and then change the option pane.
        assert listeners.length == 1;
        ScenarioEditor.setOptionPrompt(noMockOption);

        // Change the defaults
        ConfigurationPanel configurationPanel = editor.getMainPanel().getConfigurationPanel();
        configurationPanel.setClientIP("randomval");

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig temp = BW4TClientConfig.fromXML(FILE_SAVE_PATH);

        assertEquals(configurationPanel.getClientIP(), temp.getClientIp());
        assertEquals(configurationPanel.getClientPort(), temp.getClientPort());

        assertEquals(configurationPanel.getServerIP(), temp.getServerIp());
        assertEquals(configurationPanel.getServerPort(), temp.getServerPort());

        // Finally make sure the confirmation dialog was called.
        verify(noMockOption, times(1))
                .showConfirmDialog((Component) any(), anyObject(), anyString(), anyInt(), anyInt());

        // File chooser should not have been called for the actual opening
        verify(filechooser, times(1)).showOpenDialog((Component) any());
    }

    @Test
    public void testNewAfterOpen() {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();
        EntityPanel entityPanel = editor.getMainPanel().getEntityPanel();
        assertEquals(2, entityPanel.getBotTableModel().getRowCount());
        assertEquals(2, entityPanel.getEPartnerTableModel().getRowCount());

        //open new configuration
        editor.getTopMenuBar().getMenuItemFileNew().doClick();

        //check if the rows have actually been flushed
        assertEquals(0, entityPanel.getBotTableModel().getRowCount());
        assertEquals(0, entityPanel.getEPartnerTableModel().getRowCount());
    }

    /**
     * Test if the menu exit works
     * Case: New window, press exit with no changes.
     */
    @Test
    public void testExitNoChanges() {
        /* Reset the controller to the spied objects controller */
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileExit().getActionListeners();
        assert listeners.length == 1;

        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setController(new ScenarioEditorController(editor, new BW4TClientConfig()));

        ScenarioEditor.setOptionPrompt(OptionPromptHelper.getYesOptionPrompt());

        /* Don't actually close the jvm */
        doNothing().when(editor).closeScenarioEditor();

        /* Click the exit button */
        editor.getTopMenuBar().getMenuItemFileExit().doClick();

        /* Verify if closeScenarioEditor is called */
        verify(editor, atLeastOnce()).closeScenarioEditor();
    }

    /**
     * Test if the menu exit works
     * Case: New window, changed the data, dont save.
     */
    @Test
    public void testExitDontSaveChanges() {
        assert editor.getTopMenuBar().getMenuItemFileExit().getActionListeners().length == 1;
        
        /* Reset the controller to the spied objects controller */
        replaceListener(editor.getTopMenuBar().getMenuItemFileExit());

        /* Fake the prompt to no */
        OptionPrompt option = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(option);

        /* Change some random field */
        editor.getMainPanel().getConfigurationPanel().setClientIP("0.0");

        /* Don't actually close the jvm */
        doNothing().when(editor).closeScenarioEditor();

        /* Click the exit button */
        editor.getTopMenuBar().getMenuItemFileExit().doClick();

        /* Verify if closeScenarioEditor is called */
        verify(editor, atLeastOnce()).closeScenarioEditor();

        /* Verify if it asked if we wanted to save */
        verify(option, times(1)).showConfirmDialog(null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Test if the menu exit works
     * Case: New window, changed the data, save it.
     */
    @Test
    public void testExitSaveChanges() {
        OptionPrompt option = prepareForSave(editor.getTopMenuBar().getMenuItemFileExit(),
                OptionPromptHelper.getYesOptionPrompt());

        /* Change some random field */
        editor.getMainPanel().getConfigurationPanel().setClientIP("0.0");

        /* Don't actually close the jvm */
        doNothing().when(editor).closeScenarioEditor();

        /* Click the exit button */
        editor.getTopMenuBar().getMenuItemFileExit().doClick();

        /* Verify if closeScenarioEditor is called */
        verify(editor, atLeastOnce()).closeScenarioEditor();

        /* Verify if it asked if we wanted to save */
        verify(option, times(1)).showConfirmDialog(null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        /* Verify if it tried to save */
        verify(filechooser, times(1)).getSelectedFile();
    }

    /**
     * Test if the menu new item works
     * Case: New window, no changes
     */
    @Test
    public void testNewNoChanges() {
        /* Reset the controller to the spied objects controller */
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileNew().getActionListeners();
        assert listeners.length == 1;

        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setController(new ScenarioEditorController(editor, new BW4TClientConfig()));

        /* Click the new button */
        editor.getTopMenuBar().getMenuItemFileNew().doClick();

        assertTrue(editor.getMainPanel().getConfigurationPanel().isDefault());
        assertTrue(editor.getMainPanel().getEntityPanel().isDefault());
    }

    /**
     * Test if the menu new item works
     * Case: New window, a change, dont save
     */
    @Test
    public void testNewChangesNoSave() {
        /* Reset the controller to the spied objects controller */
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileNew().getActionListeners();
        assert listeners.length == 1;

        /* Some change */
        editor.getMainPanel().getConfigurationPanel().setClientIP("blaaas");

        /* Fake the prompt to no */
        OptionPrompt option = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(option);

        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setController(new ScenarioEditorController(editor, new BW4TClientConfig()));

        /* Click the new button */
        editor.getTopMenuBar().getMenuItemFileNew().doClick();

        /* Verify that it asked to save */
        verify(option, times(1)).showConfirmDialog(null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        assertTrue(editor.getMainPanel().getConfigurationPanel().isDefault());
        assertTrue(editor.getMainPanel().getEntityPanel().isDefault());
    }

    /**
     * Test if the menu new item works
     * Case: New window, a change, save it
     */
    @Test
    public void testNewChangesSave() {
        /* Mock the file chooser for saving */
        when(filechooser.showSaveDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        replaceListener(editor.getTopMenuBar().getMenuItemFileNew());

        /* Some change */
        editor.getMainPanel().getConfigurationPanel().setClientIP("blaaas");

        /* Fake the prompt to yes */
        OptionPrompt option = OptionPromptHelper.getYesOptionPrompt();
        ScenarioEditor.setOptionPrompt(option);

        /* Click the new button */
        editor.getTopMenuBar().getMenuItemFileNew().doClick();

        /* Verify if it asked if we wanted to save */
        verify(option, times(1)).showConfirmDialog(null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        /* Verify if it tried to save */
        verify(filechooser, times(1)).getSelectedFile();

        assertTrue(editor.getMainPanel().getConfigurationPanel().isDefault());
        assertTrue(editor.getMainPanel().getEntityPanel().isDefault());
    }

    /**
     * Tests whether a configuration is saved correctly.
     * 
     * Open open.xml in editor and then saves as dummy.xml.
     * Then compares the two files and asserts true when their content is equal.
     */
    @Test
    public void testSaveCorrectValues() throws IOException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Set it to an existing map file.
        editor.getMainPanel().getConfigurationPanel().setMapFile(FILE_MAP);

        saveWithMockedFileChooser();

        BW4TClientConfig opened = BW4TClientConfig.fromXML(FILE_OPEN_PATH);
        BW4TClientConfig saved = BW4TClientConfig.fromXML(FILE_SAVE_PATH);

        assertTrue("Comparing Bot Config", opened.compareBotConfigs(saved.getBots()));
        assertTrue("Comparing EPartner Config", opened.compareEpartnerConfigs(saved.getEpartners()));
        assertEquals(opened.getClientPort(), saved.getClientPort());
        assertEquals(opened.getClientIp(), saved.getClientIp());
        assertEquals(opened.getServerPort(), saved.getServerPort());
        assertEquals(opened.getServerIp(), saved.getServerIp());
        assertEquals(opened.isLaunchGui(), saved.isLaunchGui());
        assertEquals(opened.isVisualizePaths(), saved.isVisualizePaths());
        assertEquals(opened.isCollisionEnabled(), saved.isCollisionEnabled());
        // Ignore the map file during this test.
    }

    /**
     * Test the save as button. The save as button always shows the filechooser.
     * Even if there no changes. Thus the case:
     * New file, no changes, save as. Verify if the filechooser is shown.
     */
    @Test
    public void testSaveAs() {
        editor.getMainPanel().getConfigurationPanel().getMapFileTextField().setText(
                "Prevents no map file warning.");
        saveWithMockedFileChooser();
        verify(filechooser, times(1)).showDialog((Component) any(), (String) any());
        verify(filechooser, times(1)).getSelectedFile();
    }

    /**
     * Test if the export function executes when the user chooses to do so.
     *
     * @throws FileNotFoundException
     * @throws JAXBException
     */
    @Test
    public void testExportButtonYes() throws IOException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + "" +
                ".mas2g"));

        new File(FILE_EXPORT_PATH).mkdir();

        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileExport().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);

        editor.getMainPanel().getConfigurationPanel().getMapFileTextField().setText("Prevents no map file warning.");
        editor.getTopMenuBar().getMenuItemFileExport().doClick();

        assertTrue("mas2g Exists", new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + ".mas2g").exists());
        FileUtils.forceDelete(new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + ".mas2g"));

    }

    /**
     * Test if the export function does nothing when the user cancels the filechooser.
     *
     * @throws FileNotFoundException
     * @throws JAXBException
     */
    @Test
    public void testExportButtonNo() throws IOException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.CANCEL_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.CANCEL_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + "" +
                ".mas2g"));

        // Spy the message
        OptionPrompt spyPrompt = OptionPromptHelper.getYesOptionPrompt();
        ScenarioEditor.setOptionPrompt(spyPrompt);

        new File(FILE_EXPORT_PATH).mkdir();

        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileExport().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);

        editor.getTopMenuBar().getMenuItemFileExport().doClick();

        verify(spyPrompt, times(1)).showMessageDialog(null, "Error: Can not export an unsaved scenario.");
    }

    /**
     * Checks if it's allowed to save when a correct amount
     * of bots have been selected for the selected map.
     */
    @Test
    public void checkProperAmountOfBotsInMap() {

        //The banana map can hold 3 bots:
        setMapFile("Banana");
        addBotsToTable(2);

        saveWithMockedFileChooser();

        verify(filechooser, times(1)).showDialog((Component) any(), (String) any());

    }

    /**
     * Checks if it's not allowed to save when an incorrect amount
     * of bots have been selected for the selected map.
     */
    @Test
    public void checkTooManyBotsInMap() {

        //The banana map can hold 3 bots:
        setMapFile("Banana");
        addBotsToTable(10);

        //Once the option prompt is set, we know that the 'too many' bots
        //message was sent, so we can close the GUI when that happens:
        OptionPrompt noPrompt = OptionPromptHelper.getNoOptionPrompt();
        ScenarioEditor.setOptionPrompt(noPrompt);

        //Should not be able to save due to too many bots
        saveWithMockedFileChooser();

        verify(filechooser, never()).showDialog((Component) any(), (String) any());
        verify(noPrompt, times(1)).showMessageDialog((Component) any(), anyString());

    }

    /**
     * Sets the map file path in the GUI.
     *
     * @param mapName The new map file path to select.
     */
    private void setMapFile(String mapName) {
        editor.getMainPanel().getConfigurationPanel().setMapFile(BASE + "maps/" + mapName);
    }

    /**
     * Adds a specified amount of bots to the entity table.
     *
     * @param amount The amount of bots to add.
     */
    private void addBotsToTable(int amount) {
        for (int i = 0; i < amount; i++) {
            editor.getMainPanel().getEntityPanel().getNewBotButton().doClick();
        }
    }

    /**
     * Saves the config as XML file using the mocked file chooser to save.
     */
    private void saveWithMockedFileChooser() {
        /** Mocks the file chooser to react without user input: */
        when(filechooser.showSaveDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        /** Sets the mocked file chooser for the listener to the menu option: */
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileSaveAs().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);


        /** Saves the config as XML: */
        editor.getTopMenuBar().getMenuItemFileSaveAs().doClick();
    }

    /**
     * Replaces the listener of the menu item.
     *
     * @param menuItem The menu item.
     */
    private void replaceListener(JMenuItem menuItem) {
        AbstractMenuOption menuOption = (AbstractMenuOption) menuItem.getActionListeners()[0];
        menuItem.removeActionListener(menuOption);

        menuOption.setController(new ScenarioEditorController(editor, new BW4TClientConfig()));
        menuOption = (AbstractMenuOption) menuItem.getActionListeners()[0];
        menuOption.setCurrentFileChooser(filechooser);
    }

    /**
     * Prepares the file chooser so that when it's opened it closes right away
     * in the desired way.
     *
     * @param menuItem The menu item to replace the listener of (the menu item we're testing).
     * @param option   The mocked option prompt that could pop up during execution.
     * @return The spied version of the option prompt.
     */
    private OptionPrompt prepareForSave(JMenuItem menuItem, OptionPrompt option) {
        /* Mock the file chooser for saving */
        when(filechooser.showSaveDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        /* Remove the old controller, recreate the new one using the mocked editor and
         * finally mock the filechooser.
         */
        replaceListener(menuItem);

        /* Fake the prompt */
        ScenarioEditor.setOptionPrompt(option);

        return option;
    }

}
