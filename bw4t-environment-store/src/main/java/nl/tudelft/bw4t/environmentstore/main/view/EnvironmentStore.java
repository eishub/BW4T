package nl.tudelft.bw4t.environmentstore.main.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.view.ExplanationPanel;
import nl.tudelft.bw4t.environmentstore.editor.view.MapPanel;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;
import nl.tudelft.bw4t.environmentstore.sizedialog.view.SizeDialog;
import nl.tudelft.bw4t.environmentstore.util.DefaultOptionPrompt;
import nl.tudelft.bw4t.environmentstore.util.OptionPrompt;

/**
 * The EnvironmentStore class serves as a frame for the Panels and tables.
 *
 */
public class EnvironmentStore extends JFrame {
    
    /** Random generated serial version UID. */
    private static final long serialVersionUID = 8572609341436634787L;

    /** The controller of the map panel. */
    private MapPanelController mapController;
    
    /** The controller for this view class. */
    private EnvironmentStoreController envController;
    
    /** The name of the window. */
    private String windowName = "Environment Store";
    
    /** The explanation panel where a short How-To-Use explanation is displayed. */
    private ExplanationPanel explanationPanel;
    
    /** The map panel. */
    private final MapPanel mapTable;
    
    /** The menu bar. */
    private MenuBar menuBar;

    /** The option prompt that will pop up for some events. */
    private static OptionPrompt option = new DefaultOptionPrompt();
    
    /**
     * Create the MapEditor frame which will hold all the panels, tables and buttons.
     * 
     * @param themap is a map that contains: rows, cols, entities, randomize.
     * 
     */
    public EnvironmentStore(MapPanelController mc) {
        mapController = mc;
        
        setWindowTitle("Untitled");
        setLayout(new BorderLayout());
        
        // Attach the menu bar.
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Create the explainationPanel for on top of the editor.
        explanationPanel = new ExplanationPanel();
        
        // Both the mapTable and sequencePanel are added to a rooms panel.
        mapTable = new MapPanel(mapController);
        
        // Attach all Panels to the Editor.
        add(explanationPanel, BorderLayout.NORTH);
        add(mapTable, BorderLayout.CENTER);
        //add(legendaPanel, BorderLayout.EAST);

        envController = new EnvironmentStoreController(this, mapController);
        
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
     * Returns the EnvironmentStoreController
     *
     * @return The EnvironmentStoreController.
     */
    public final EnvironmentStoreController getEnvironmentStoreController() {
        return envController;
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
    
    /**
     * Used to set the window title of the frame with the filename.
     * @param filenameBeingEdited is the filename of the map that is being edited.
     */
    public void setWindowTitle(String filenameBeingEdited) {
        setTitle(windowName + " - " + stripExtension(filenameBeingEdited));
    }

    /**
     * Used to strip the extension of the filename.
     * @param str is the filename that should be stripped
     * @return the filename without extension
     */
    public String stripExtension (String str) {
        // Handle null case specially.
        if (str == null) return null;
        // Get position of last '.'.
        int pos = str.lastIndexOf(".");
        // If there wasn't any '.' just return the string as is.
        if (pos == -1) return str;
        // Otherwise return the string, up to the dot.
        return str.substring(0, pos);
    }
    
    /** Closes the MapEditor window and all child frames. */
    public void closeEnvironmentStore() {
        System.exit(0);
    }

    /**
     * Start first the dialog that gives us the options for the map.
     * Then create the MapEditor with this options.
     * 
     * @param args Unused parameter
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.out.println("Unable to use the systems look and feel.");
        }
        SizeDialog dialog = new SizeDialog();
        dialog.setVisible(true);
    }
}

