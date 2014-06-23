package nl.tudelft.bw4t.scenariogui.editor.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.util.ExportToMASTest;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPromptHelper;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MenuOptionExportTest {

    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";
    private static final String FILE_EXPORT_PATH = BASE + "export/";

    private ScenarioEditor editor;
    private JFileChooser filechooser;

    @Before
    public void setUp() throws IOException {
        editor = spy(new ScenarioEditor());
        filechooser = mock(JFileChooser.class);
    }

    @After
    public void dispose() {
        editor.dispose();
    }

    /**
     * Tests if the warning pops up when exporting
     * while not all goal files exist.
     *
     * @throws IOException
     */
    @Test
    public void testBotTableWarning() throws IOException {
        // Create the dir
        File directory = new File(FILE_EXPORT_PATH);
        directory.mkdirs();

        // Setup the behaviour
        when(filechooser.showOpenDialog((Component) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        when(filechooser.showDialog((Component) any(), (String) any())).thenReturn(JFileChooser.APPROVE_OPTION);
        new File(FILE_EXPORT_PATH).mkdirs();
        when(filechooser.getSelectedFile()).thenReturn(new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + "" +
                ".mas2g"));

        editor.getMainPanel().getEntityPanel().getNewBotButton().doClick();

        String testFileName = "testRobot.goal";
        editor.getMainPanel().getEntityPanel().getBotTableModel().setValueAt(testFileName, 0, 2);

        //Once the option prompt is set, we know that the export warning
        //message was sent.
        OptionPrompt yesPrompt = OptionPromptHelper.getYesOptionPrompt();
        ScenarioEditor.setOptionPrompt(yesPrompt);

        ActionListener[] listeners = editor.getTopMenuBar().getMenuItemFileExport().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);

        editor.getMainPanel().getConfigurationPanel().getMapFileTextField().setText("Prevents no map file warning.");
        editor.getTopMenuBar().getMenuItemFileExport().doClick();

        verify(yesPrompt, times(1)).showMessageDialog((Component) any(), anyString());

        assertTrue("mas2g Exists", new File(FILE_EXPORT_PATH + ExportToMASTest.CONFIG_NAME + ".mas2g").exists());
        FileUtils.deleteDirectory(directory);
    }
}
