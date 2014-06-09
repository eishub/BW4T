package nl.tudelft.bw4t.map.editor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.controller.TestMapController;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.editor.controller.Map;
import nl.tudelft.bw4t.map.editor.controller.MapPreviewController;
import nl.tudelft.bw4t.view.MapRenderer;

import org.apache.log4j.BasicConfigurator;

/**
 * The MenuBar class extends JMenuBar. Used in the Map Editor Frame.
 */
public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 3985946589128847791L;

	private String lastFileLocation;

    private JMenuItem fileNew, fileOpen, fileSave, fileSaveAs, preview, fileExit;
    
    private Map map;

    /**
     * Construct a menu bar for the Map Editor
     */
    public MenuBar(Map themap) {
    	this.map = themap;
    	
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

        preview = new JMenuItem("Preview Map");
        preview.setToolTipText("Preview Current Map");
        //TODO: Refactor for MVC
        preview.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {		    	
		    	NewMap newmap = map.createMap();
		    	JFrame preview = new JFrame("Map Preview");
		    	preview.add(new MapRenderer(new MapPreviewController(newmap)));
		    	preview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    	preview.pack();
		    	preview.setVisible(true);
			}
		});
        
        file.add(preview);

        file.addSeparator();

        fileExit = new JMenuItem("Exit");
        fileExit.setToolTipText("Exit application");
        file.add(fileExit);

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
     * JMenuItem used to exit the program.
     * @return The JMenuItem to exit the program
     */
    public final JMenuItem getMenuItemFileExit() {
        return fileExit;
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
