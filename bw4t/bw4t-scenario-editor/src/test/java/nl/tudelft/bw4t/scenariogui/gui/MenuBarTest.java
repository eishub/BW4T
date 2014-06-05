package nl.tudelft.bw4t.scenariogui.gui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.controller.AbstractMenuOption;
import nl.tudelft.bw4t.scenariogui.editor.controller.ScenarioEditorController;
import nl.tudelft.bw4t.scenariogui.util.ExportToMAS;
import nl.tudelft.bw4t.scenariogui.util.ExportToMASTest;
import nl.tudelft.bw4t.scenariogui.util.NoMockOptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.TestUtils;
import nl.tudelft.bw4t.scenariogui.util.YesMockOptionPrompt;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

/**
 * <p>
 * @author      Calvin Wong Loi Sing  
 * @author        Katia Asmoredjo
 * @version     0.1                
 * @since       21-05-2014        
 */
public class MenuBarTest {

    /**
     * The base directory of all files used in the test.
     */
    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";

    /**
     * The path of the xml file used to test the open button.
     */
    private static final String FILE_OPEN_PATH = BASE + "open.xml";

    /**
     * The path of the xml file used to test the open button.
     */
    private static final String FILE_EXPORT_PATH = BASE + "export/";

    /**
     * The path of the xml file used to save dummy data
     */
    private static final String FILE_SAVE_PATH = BASE + "dummy.xml";

    /**
     * The Scenario editor that is spied upon for this test.
     */
    private ScenarioEditor editor;

    /**
     * File choose used to mock the behaviour of the user.
     */
    private JFileChooser filechooser;

    /**
     * Setup the testing environment by creating the scenario editor and
     * assigning the editor attribute to a spy object of the ScenarioEditor.
     */
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

    /**
     * Close the ScenarioEditor to prevent to many windows from cluttering
     * the screen during the running of the tests
     */
    @After
    public final void closeEditor() throws IOException {
        editor.dispose();
    }

    /**
     * Test if the open button works.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButton() throws FileNotFoundException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_OPEN_PATH));

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig temp = BW4TClientConfig.fromXML(FILE_OPEN_PATH);

        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientIP(), temp.getClientIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientPort(), temp.getClientPort());

        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerIP(), temp.getServerIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerPort(), temp.getServerPort());
    }

    /**
     * Test if the open button works after changing the defaults and clicking yes on the prompt.
     *
     * @throws FileNotFoundException File not found exception
     * @throws JAXBException         JAXBException, also called in some cases when a file is not found
     *                               by JAXB itself.
     */
    @Test
    public void testOpenButtonNonDefaultYes() throws FileNotFoundException, JAXBException {
        // Create a YesMockOptionPrompt object to spy on.
        YesMockOptionPrompt yesMockOption = spy(new YesMockOptionPrompt());

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
        editor.getMainPanel().getConfigurationPanel().setClientIP("randomvalue");

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig temp = BW4TClientConfig.fromXML(FILE_OPEN_PATH);

        // Check the final state. The opened configurational file should be equal to the one in the program.
        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientIP(), temp.getClientIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientPort(), temp.getClientPort());

        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerIP(), temp.getServerIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerPort(), temp.getServerPort());

        // Finally make sure the confirmation dialog was called.
        verify(yesMockOption, times(1))
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
    public void testOpenButtonNonDefaultNo() throws FileNotFoundException, JAXBException {
        // Create a YesMockOptionPrompt object to spy on.
        NoMockOptionPrompt noMockOption = spy(new NoMockOptionPrompt());

        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        // Set the controllers to mock yes.
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileOpen().getActionListeners();

        // There should be one listener, so we check that and then change the option pane.
        assert listeners.length == 1;
        ScenarioEditor.setOptionPrompt(noMockOption);

        // Change the defaults
        editor.getMainPanel().getConfigurationPanel().setClientIP("randomval");

        editor.getTopMenuBar().getMenuItemFileOpen().doClick();

        // Open the actual file with the xml reader.
        BW4TClientConfig temp = BW4TClientConfig.fromXML(FILE_SAVE_PATH);

        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientIP(), temp.getClientIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getClientPort(), temp.getClientPort());

        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerIP(), temp.getServerIp());
        assertEquals(editor.getMainPanel().getConfigurationPanel().getServerPort(), temp.getServerPort());

        // Finally make sure the confirmation dialog was called.
        verify(noMockOption, times(1))
                .showConfirmDialog((Component) any(), anyObject(), anyString(), anyInt(), anyInt());

        // File chooser should not have been called for the actual opening
        verify(filechooser, times(1)).showOpenDialog((Component) any());
    }
    
    /**
     * Test whether the lists with bots and epartners are flushed when a new configuration is opened.
     */
    @Test
    public void testFlushEntityLists() {
        //add bots and epartners
        editor.getMainPanel().getEntityPanel().getNewBotButton().doClick();
        editor.getMainPanel().getEntityPanel().getNewBotButton().doClick();
        editor.getMainPanel().getEntityPanel().getNewBotButton().doClick();
        assertEquals(editor.getMainPanel().getEntityPanel().getBotTableModel().getRowCount(), 3);
        
        editor.getMainPanel().getEntityPanel().getNewEPartnerButton().doClick();
        editor.getMainPanel().getEntityPanel().getNewEPartnerButton().doClick();
        assertEquals(editor.getMainPanel().getEntityPanel().getEPartnerTableModel().getRowCount(), 2);
        
        //open new configuration
        editor.getTopMenuBar().getMenuItemFileNew().doClick();
        
        //check if the rows have actually been flushed
        assertEquals(editor.getMainPanel().getEntityPanel().getBotTableModel().getRowCount(), 0);
        assertEquals(editor.getMainPanel().getEntityPanel().getEPartnerTableModel().getRowCount(), 0);
    }

    /**
     * Test if the menu exit works
     * Case: New window, press exit with any changes.
     */
    @Test
    public void testExitNoChanges() {
        /* Reset the controller to the spied objects controller */
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileExit().getActionListeners();
        assert listeners.length == 1;

        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setController(new ScenarioEditorController(editor, new BW4TClientConfig()));

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
        /* Reset the controller to the spied objects controller */
        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileExit().getActionListeners();
        assert listeners.length == 1;

        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        editor.getTopMenuBar().getMenuItemFileExit().removeActionListener(listeners[0]);
        menuOption.setController(new ScenarioEditorController(editor, new BW4TClientConfig()));

        /* Fake the prompt to no */
        OptionPrompt option = spy(new NoMockOptionPrompt());
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
        /* Mock the file chooser for saving */
        when(filechooser.showSaveDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        /* Remove the old controller, recreate the new one using the mocked editor and
         * finally mock the filechooser.
         */
        JMenuItem exit = editor.getTopMenuBar().getMenuItemFileExit();
        AbstractMenuOption menuOption = (AbstractMenuOption) exit.getActionListeners()[0];
        exit.removeActionListener(exit.getActionListeners()[0]);

        menuOption.setController(new ScenarioEditorController(editor, new BW4TClientConfig()));
        menuOption = (AbstractMenuOption) exit.getActionListeners()[0];
        menuOption.setCurrentFileChooser(filechooser);


        /* Fake the prompt to no */
        OptionPrompt option = spy(new YesMockOptionPrompt());
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
        OptionPrompt option = spy(new NoMockOptionPrompt());
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

        /* Remove the old controller, recreate the new one using the mocked editor and
         * finally mock the filechooser.
         */
        JMenuItem newfile = editor.getTopMenuBar().getMenuItemFileNew();
        AbstractMenuOption menuOption = (AbstractMenuOption) newfile.getActionListeners()[0];
        newfile.removeActionListener(newfile.getActionListeners()[0]);

        menuOption.setController(new ScenarioEditorController(editor, new BW4TClientConfig()));
        menuOption = (AbstractMenuOption) newfile.getActionListeners()[0];
        menuOption.setCurrentFileChooser(filechooser);

        /* Some change */
        editor.getMainPanel().getConfigurationPanel().setClientIP("blaaas");

        /* Fake the prompt to yes */
        OptionPrompt option = spy(new YesMockOptionPrompt());
        ScenarioEditor.setOptionPrompt(option);

        /* Click the new button */
        editor.getTopMenuBar().getMenuItemFileNew().doClick();

        /* Verify if it asked if we wanted to save */
        verify(option, times(1)).showConfirmDialog(null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        /* Verify if it tried to save */
        verify(filechooser, times(1)).getSelectedFile();
    }

    /**
     * Test the save as button. The save as button always shows the filechooser.
     * Even if there any no changes. Thus the case:
     * New file, no changes, save as. Verify if the filechooser is shown.
     */
    @Test
    public void testSaveAs() {
        saveWithMockedFileChooser();
        verify(filechooser, times(1)).showDialog((Component) any(), (String) any());
        verify(filechooser, times(1)).getSelectedFile();
    }

    /**
     * Test if the export function executes when the user chooses to do so.
     * @throws FileNotFoundException
     * @throws JAXBException
     */
    @Test
    public void testExportButtonYes() throws IOException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + ".mas2g"));

        new File(FILE_EXPORT_PATH).mkdir();

        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileExport().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);

        editor.getTopMenuBar().getMenuItemFileExport().doClick();

        assertTrue("mas2g Exists", new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + ".mas2g").exists());
        FileUtils.forceDelete(new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + ".mas2g"));

    }

    /**
     * Test if the export function does nothing when the user cancels the filechooser.
     * @throws FileNotFoundException
     * @throws JAXBException
     */
    @Test
    public void testExportButtonNo() throws IOException, JAXBException {
        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.CANCEL_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.CANCEL_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + ".mas2g"));

        // Spy the message
        OptionPrompt spyPrompt = spy(new YesMockOptionPrompt());
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
        
        //should not be able to save due to too many bots
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
        TestUtils.prepareGUIClose(editor, new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return ScenarioEditor.getOptionPrompt() != null;
            }
            
        });
        
        //Should not be able to save due to too many bots
        saveWithMockedFileChooser();

        verify(filechooser, never()).showDialog((Component) any(), (String) any());
        
    }
    
    /**
     * Sets the map file path in the GUI.
     * @param mapName The new map file path to select.
     */
    private void setMapFile(String mapName) {
        editor.getMainPanel().getConfigurationPanel().setMapFile(BASE + "maps/" + mapName);
    }
    
    /**
     * Adds a specified amount of bots to the entity table.
     * @param amount The amount of bots to add.
     */
    private void addBotsToTable(int amount) {
        for (int i = 0; i < amount; i++)
            editor.getMainPanel().getEntityPanel().getNewBotButton().doClick();
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


}
