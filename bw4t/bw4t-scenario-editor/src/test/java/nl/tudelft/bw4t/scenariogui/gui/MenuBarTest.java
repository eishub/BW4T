package nl.tudelft.bw4t.scenariogui.gui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.controllers.editor.AbstractMenuOption;
import nl.tudelft.bw4t.scenariogui.controllers.editor.ScenarioEditorController;
import nl.tudelft.bw4t.scenariogui.util.NoMockOptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.YesMockOptionPrompt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <p>
<<<<<<< HEAD
<<<<<<< HEAD
 * @author      Calvin Wong Loi Sing  
=======
 * @author      Calvin Wong Loi Sing
 * @author		Katia Asmoredjo
>>>>>>> ScenarioEditor_K
=======
 * @author      Calvin Wong Loi Sing
 * @author		Katia Asmoredjo
>>>>>>> ScenarioEditor_K
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
    public void setUp() {
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
    public final void closeEditor() {
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
        verify(filechooser, times(1)).showSaveDialog((Component) any());

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
        menuOption.setController(new ScenarioEditorController(editor));

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
        menuOption.setController(new ScenarioEditorController(editor));

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
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        /* Remove the old controller, recreate the new one using the mocked editor and
         * finally mock the filechooser.
         */
        JMenuItem exit = editor.getTopMenuBar().getMenuItemFileExit();
        AbstractMenuOption menuOption = (AbstractMenuOption) exit.getActionListeners()[0];
        exit.removeActionListener(exit.getActionListeners()[0]);

        menuOption.setController(new ScenarioEditorController(editor));
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
        menuOption.setController(new ScenarioEditorController(editor));

        /* Click the new button */
        editor.getTopMenuBar().getMenuItemFileNew().doClick();

        assertTrue(editor.getMainPanel().getConfigurationPanel().isDefault());
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
        menuOption.setController(new ScenarioEditorController(editor));

        /* Click the new button */
        editor.getTopMenuBar().getMenuItemFileNew().doClick();

        /* Verify that it asked to save */
        verify(option, times(1)).showConfirmDialog(null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        assertTrue(editor.getMainPanel().getConfigurationPanel().isDefault());
    }

    /**
     * Test if the menu new item works
     * Case: New window, a change, save it
     */
    @Test
    public void testNewChangesSave() {
        /* Mock the file chooser for saving */
        when(filechooser.showSaveDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        /* Remove the old controller, recreate the new one using the mocked editor and
         * finally mock the filechooser.
         */
        JMenuItem newfile = editor.getTopMenuBar().getMenuItemFileNew();
        AbstractMenuOption menuOption = (AbstractMenuOption) newfile.getActionListeners()[0];
        newfile.removeActionListener(newfile.getActionListeners()[0]);

        menuOption.setController(new ScenarioEditorController(editor));
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
        /* Mock the file chooser for saving */
        when(filechooser.showSaveDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_SAVE_PATH));

        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileSaveAs().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);

        editor.getTopMenuBar().getMenuItemFileSaveAs().doClick();

        verify(filechooser, times(1)).showSaveDialog((Component) any());
        verify(filechooser, times(1)).getSelectedFile();
    }


}
