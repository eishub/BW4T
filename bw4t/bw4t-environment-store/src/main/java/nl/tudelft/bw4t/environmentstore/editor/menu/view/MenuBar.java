package nl.tudelft.bw4t.environmentstore.editor.menu.view;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The MenuBar class extends JMenuBar. Used in the Map Editor Frame.
 */
public class MenuBar extends JMenuBar {

    /** Random generated serial version UID */
    private static final long serialVersionUID = 3985946589128847791L;

    /** The last known save file location */
    private String lastFileLocation;

    /** the items added to the menubar */
    private JMenuItem fileNew, fileOpen, fileSave, fileSaveAs, preview, fileExit;
    private JMenuItem toolsRandomizeZones, toolsRandomizeBlocks, toolsRandomizeSequence;

    /**
     * Construct a menu bar for the Map Editor
     * 
     */
    public MenuBar() {
        
        JMenu file;
        JMenu tools;

        // Build the File menu.
        file = new JMenu("File");
        // Bind alt+f to menu
        file.setMnemonic(KeyEvent.VK_F);
        add(file);
        
        // Build the File menu.
        tools = new JMenu("Tools");
        // Bind alt+t to menu
        file.setMnemonic(KeyEvent.VK_T);
        add(tools);

        // Create the File menu items.
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

        preview = new JMenuItem("Preview Map");
        preview.setToolTipText("Preview Current Map");
        file.add(preview);

        file.addSeparator();

        fileExit = new JMenuItem("Exit");
        fileExit.setToolTipText("Exit application");
        file.add(fileExit);
        
        // Create the Tools menu items.        
        toolsRandomizeZones = new JMenuItem("Randomize Zones");
        toolsRandomizeZones.setToolTipText("Randomize the locations of Zones");
        tools.add(toolsRandomizeZones);
        
        toolsRandomizeBlocks = new JMenuItem("Randomize Blocks");
        toolsRandomizeBlocks.setToolTipText("Randomize the distribution of all blocks");
        tools.add(toolsRandomizeBlocks);
        
        tools.addSeparator();
        
        toolsRandomizeSequence = new JMenuItem("Randomize Sequence");
        toolsRandomizeSequence.setToolTipText("Randomize the target sequence");
        tools.add(toolsRandomizeSequence);


    }

    /**
     * JMenuItem to start a new configuration with the default values filled in.
     * @return The JMenuItem to start a new configuration
     */
    public final JMenuItem getMenuItemFileNew() {
        return fileNew;
    }

    /**
     * JMenuItem to open a configuration from a file.
     * @return The JMenuItem to open a file
     */
    public final JMenuItem getMenuItemFileOpen() {
        return fileOpen;
    }

    /**
     * JMenuItem used to save the configuration
     * to a file at a new file location.
     *
     * @return The JMenuItem to start a save a file at a new file location
     */
    public final JMenuItem getMenuItemFileSave() {
        return fileSave;
    }

    /**
     * JMenuItem used to save the configuration to a file at a chosen location.
     * @return The JMenuItem to save a file at a chosen location
     */
    public final JMenuItem getMenuItemFileSaveAs() {
        return fileSaveAs;
    }
    
    /**
     * JMenuItem used to preview the map.
     * @return the JMenuItem to preview the map
     */
    public final JMenuItem getMenuItemPreview() {
        return preview;
    }

    /**
     * JMenuItem used to exit the program.
     * @return The JMenuItem to exit the program
     */
    public final JMenuItem getMenuItemFileExit() {
        return fileExit;
    }
    
    /**
     * JMenuItem used to randomize the rooms
     * @return The JMenuItem to randomize the rooms
     */
    public final JMenuItem getMenuItemRandomizeZones() {
        return toolsRandomizeZones;
    }
    
    /**
     * JMenuItem used to randomize the blocks distribution
     * @return The JMenuItem to randomize the blocks distribution
     */
    public final JMenuItem getMenuItemRandomizeBlocks() {
        return toolsRandomizeBlocks;
    }
    
     /**
     * JMenuItem used to randomize the target sequence
     * @return The JMenuItem to randomize the target sequence
     */
    public final JMenuItem getMenuItemRandomizeSequence() {
        return toolsRandomizeSequence;
    }

    /**
     * Variable to get the file location used to save the configuration
     * immediately instead of having to browse to the same location again.
     *
     * @return the last know file location
     */
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

    /**
     * Variable to save the file location used to save the configuration
     * immediately instead of having to browse to the same location again.
     *
     * @param fileLocation is the last used file location
     */
    public final void setLastFileLocation(final String fileLocation) {
        this.lastFileLocation = fileLocation;
    }


}
