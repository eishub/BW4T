package nl.tudelft.bw4t.scenariogui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Menu {
    JTextArea output;
    JScrollPane scrollPane;
	
	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu file;
		JMenuItem fileNew, fileOpen, fileSave, fileExit;
		
		// Create the menu bar.
		menuBar = new JMenuBar();
		
		// Build the menu.
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F); // Bind alt+f to menu
		menuBar.add(file);
		
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
		
		fileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		
		return menuBar;
	}
	
	public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
 
        //Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);
 
        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);
 
        return contentPane;
    }
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        Menu demo = new Menu();
        frame.setJMenuBar(demo.createMenuBar());
        frame.setContentPane(demo.createContentPane());
 
        //Display the window.
        frame.setSize(450, 260);
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
