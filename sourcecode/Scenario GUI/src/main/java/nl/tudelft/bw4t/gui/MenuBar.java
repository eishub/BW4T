package nl.tudelft.bw4t.gui;

import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The MenuBar class extends JMenuBar. Used in the ScenarioEditor Frame.
 * 
 * @author Xander
 */
public class MenuBar extends JMenuBar {
	
	/** The items in the menu: */
	private JMenuItem fileNew, fileOpen, fileSave, fileExit;

    /**
     * Construct a menu bar for the Scenario Editor.
     */
    public MenuBar() {
        JMenu file;

        // Build the menu.
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F); // Bind alt+f to menu
        add(file);

        // Create the menu items.
        fileNew = new JMenuItem("New");
        fileNew.setToolTipText("New configuration file");
        file.add(fileNew);

        fileOpen = new JMenuItem("Open");
        fileOpen.setToolTipText("Open configuration file");
        file.add(fileOpen);

        fileSave = new JMenuItem("Save");
        fileSave.setToolTipText("Save configuration file");
        file.add(fileSave);

        file.addSeparator();

        fileExit = new JMenuItem("Exit");
        fileExit.setToolTipText("Exit application");
        file.add(fileExit);
        
    }
    
    /**
     * Returns the JMenuItem to start a new file
     * @return The JMenuItem to start a new file
     */
	public JMenuItem getMenuItemFileNew() {
		return fileNew;
	}
    
    /**
     * Returns the JMenuItem to start a open a file
     * @return The JMenuItem to start a open a file
     */
	public JMenuItem getMenuItemFileOpen() {
		return fileOpen;
	}

    /**
     * Returns the JMenuItem to start a save a file
     * @return The JMenuItem to start a save a file
     */
	public JMenuItem getMenuItemFileSave() {
		return fileSave;
	}

    /**
     * Returns the JMenuItem to exit the program
     * @return The JMenuItem to exit the program
     */
	public JMenuItem getMenuItemFileExit() {
		return fileExit;
	}

}
