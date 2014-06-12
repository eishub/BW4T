package nl.tudelft.bw4t.map.editor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import nl.tudelft.bw4t.map.editor.controller.EnvironmentStoreController;
import nl.tudelft.bw4t.map.editor.controller.MapPanelController;
import nl.tudelft.bw4t.map.editor.gui.ExplanationPanel;
import nl.tudelft.bw4t.map.editor.gui.MapPanel;
import nl.tudelft.bw4t.map.editor.gui.MenuBar;
import nl.tudelft.bw4t.map.editor.gui.SizeDialog;
import nl.tudelft.bw4t.map.editor.util.DefaultOptionPrompt;
import nl.tudelft.bw4t.map.editor.util.OptionPrompt;

/**
 * The MapEditor class serves as a frame for the Panels and tables.
 *
 */
public class EnvironmentStore extends JFrame {
	
	private static final long serialVersionUID = 8572609341436634787L;

	private MapPanelController map;
	
	private String windowName = "BW4T Extensive Map Editor";
	
	private EnvironmentStoreController controller;
	
	private ExplanationPanel explanationPanel;
	
	private final MapPanel mapTable;
	
	private MenuBar menuBar;

	private static OptionPrompt option = new DefaultOptionPrompt();
    
    /**
     * Create the MapEditor frame which will hold all the panels, tables and buttons.
     * 
     * @param themap is a map that contains: rows, cols, entities, randomize.
     * 
     */
    public EnvironmentStore(MapPanelController themap) {
        map = themap;
        
        setWindowTitle("Untitled");
        setLayout(new BorderLayout());
        
        // Attach the menu bar.
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
        
        // TODO: Change to DO_NOTHING_ON_CLOSE when we want to ask the user whether he is sure to exit.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the explainationPanel for on top of the editor.
        explanationPanel = new ExplanationPanel();
        
        // Both the mapTable and sequencePanel are added to a rooms panel.
        // TODO: Create a roomsTable class that creates both MapTable and SequencePanel. Problems: map Data model in mapTable and boxLayout in roomsPanel.
        mapTable = new MapPanel(map);
        
        // Attach all Panels to the Editor.
        add(explanationPanel, BorderLayout.NORTH);
        add(mapTable, BorderLayout.CENTER);
        //add(legendaPanel, BorderLayout.EAST);

        // Create the controller
        controller = new EnvironmentStoreController(this, map);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Returns the menu bar.
     *
     * @return The menu bar.
     */
    public final MenuBar getTopMenuBar() {
        return menuBar;
    }
    
    /**
     * Returns the mapTable
     *
     * @return The mapTable.
     */
    public final MapPanel getMapTable() {
        return mapTable;
    }
    
    /**
     * Opens a dialog showing the description of the error and the error itself as a String.
     * 
     * @param e The exception that is thrown.
     * @param s A description that is specific for why the error occurred.
     */
    public static void showDialog(final Exception e, final String s) {

        EnvironmentStore.option.showMessageDialog(null, s + "\n" + e.toString());
    }
    
    /**
     * Opens a dialog showing a message, but no error.
     * 
     * @param s A message that is displayed to the user.
     */
    public static void showDialog(final String s) {

        EnvironmentStore.option.showMessageDialog(null, s);
    }
    
    /**
     * Used to set the OptionPrompt for the Map Editor.
     * 
     * @param o The OptionPrompt object to set option to.
     */
    public static void setOptionPrompt(OptionPrompt o) {
        option = o;
    }
    
    /**
     * Used to get the OptionPrompt for the MapEditor GUI.
     * @return option Used to handle all thread blocking GUI objects.
     */
    public static OptionPrompt getOptionPrompt() {
        return option;
    }
    
    public void setWindowTitle(String filenameBeingEdited) {
        setTitle(windowName + " - " + filenameBeingEdited);
    }
    
    /**
     * Closes the MapEditor window and all child frames.
     */
    public void closeMapEditor() {
        System.exit(0);
    }


    /**
     * Start first the dialog that gives us the options for the map.
     * Then create the MapEditor with this options.
     * 
     * @param args Unused parameter
     */
    public static void main(String[] args) {
        SizeDialog dialog = new SizeDialog();
        if (JOptionPane.CLOSED_OPTION == JOptionPane.showOptionDialog(null,
                dialog, "BW4T Map Editor - Map Size Dialog",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                null, null)) {
            System.exit(0);
        }

        MapPanelController themap = new MapPanelController(dialog.getRows(), dialog.getColumns(),
                dialog.getEntities(), dialog.isRandomMap(),
                dialog.isLabelsVisible());

        if (dialog.isRandomMap()) {
            themap.saveAsFile();
        } else {
            new EnvironmentStore(themap);
        }
    }
}