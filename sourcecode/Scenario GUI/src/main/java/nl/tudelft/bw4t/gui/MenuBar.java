package nl.tudelft.bw4t.gui;

import javax.swing.*;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.ScenarioEditor;
import nl.tudelft.bw4t.config.BW4TClientConfig;
import nl.tudelft.bw4t.gui.panel.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The Menubar class extends JMenuBar. Used in the ScenarioEditor Frame.
 */
public class MenuBar extends JMenuBar {

    /**
     * Construct a menu bar for the Scenario Editor.
     */
    public MenuBar() {
        JMenu file;
        JMenuItem fileNew, fileOpen, fileSave, fileExit;


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
        
        // Example of how to save:
//        fileSave.addActionListener(new ActionListener() {
//            //@Override
//            public void actionPerformed(ActionEvent event) {
//            	JFileChooser fileChooser = new JFileChooser();
//            	if (fileChooser.showOpenDialog(MenuBar.this) == JFileChooser.APPROVE_OPTION) {
//            		File file = fileChooser.getSelectedFile();
//	                try {
//	        			new BW4TClientConfig((MainPanel) ((ScenarioEditor) getParent()).getContentPane(), file.getAbsolutePath()).toXML();
//	        		} catch (FileNotFoundException e) {
//	        			e.printStackTrace();
//	        		} catch (JAXBException e) {
//	        			e.printStackTrace();
//	        		}
//            	}
//            }
//        });

        file.addSeparator();

        fileExit = new JMenuItem("Exit");
        fileExit.setToolTipText("Exit application");
        file.add(fileExit);

        fileExit.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
    }
}
