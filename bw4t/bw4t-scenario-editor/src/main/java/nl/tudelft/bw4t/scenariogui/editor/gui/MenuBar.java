package nl.tudelft.bw4t.scenariogui.editor.gui;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The MenuBar class extends JMenuBar. Used in the ScenarioEditor Frame.
 */
public class MenuBar extends JMenuBar {

    private static final long serialVersionUID = -7693079154027274860L;

    private String lastFileLocation;

    private JMenuItem fileNew, fileOpen, fileSave, fileSaveAs, exportGOAL, fileExit;

    /**
     * Construct a menu bar for the Scenario Editor.
     */
    public MenuBar() {
        JMenu file;

        // Build the menu.
        file = new JMenu("File");
        // Bind alt+f to menu
        file.setMnemonic(KeyEvent.VK_F);
        add(file);

        // Create the menu items.
        fileNew = new JMenuItem("New");
        fileNew.setToolTipText("New configuration file");
        file.add(fileNew);

        fileOpen = new JMenuItem("Open..");
        fileOpen.setToolTipText("Open configuration file");
        file.add(fileOpen);

        String toolTipText = "Save configuration file";
        fileSave = new JMenuItem("Save");
        fileSave.setToolTipText(toolTipText);
        file.add(fileSave);

        fileSaveAs = new JMenuItem("Save As..");
        fileSaveAs.setToolTipText(toolTipText);
        file.add(fileSaveAs);

        file.addSeparator();

        exportGOAL = new JMenuItem("Export as MAS Project");
        exportGOAL.setToolTipText("Export to Scenario as a MAS project");
        file.add(exportGOAL);

        file.addSeparator();

        fileExit = new JMenuItem("Exit");
        fileExit.setToolTipText("Exit application");
        file.add(fileExit);

    }

    public final JMenuItem getMenuItemFileNew() {
        return fileNew;
    }

    public final JMenuItem getMenuItemFileOpen() {
        return fileOpen;
    }

    public final JMenuItem getMenuItemFileSave() {
        return fileSave;
    }

    public final JMenuItem getMenuItemFileSaveAs() {
        return fileSaveAs;
    }

    public final JMenuItem getMenuItemFileExit() {
        return fileExit;
    }

    public final JMenuItem getMenuItemFileExport() {
        return exportGOAL;
    }

    public final String getLastFileLocation() {
        return lastFileLocation;
    }

    /**
     * hasLastFileLocation checks if the current configuration has a
     * known file location which can be used.
     * @return if this configuration has a known file location
     */
    public final boolean hasLastFileLocation() {
        return lastFileLocation != null;
    }

    public final void setLastFileLocation(final String fileLocation) {
        this.lastFileLocation = fileLocation;
    }

    public void addExitController(ActionListener controller) {
        getMenuItemFileExit().addActionListener(controller);
    }

    public void addNewController(ActionListener controller) {
        getMenuItemFileNew().addActionListener(controller);
    }

    public void addOpenController(ActionListener controller) {
        getMenuItemFileOpen().addActionListener(controller);
    }

    public void addSaveController(ActionListener controller) {
        getMenuItemFileSave().addActionListener(controller);
    }

    public void addSaveAsController(ActionListener controller) {
        getMenuItemFileSaveAs().addActionListener(controller);
    }

    public void addExportController(ActionListener controller) {
        getMenuItemFileExport().addActionListener(controller);
    }

}
