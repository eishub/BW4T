package nl.tudelft.bw4t.map.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import nl.tudelft.bw4t.map.editor.controller.RoomCellEditor;
import nl.tudelft.bw4t.map.editor.gui.ColorLegendaPanel;
import nl.tudelft.bw4t.map.editor.gui.ColorSequencePanel;
import nl.tudelft.bw4t.map.editor.gui.ExplanationPanel;
import nl.tudelft.bw4t.map.editor.gui.RoomCellRenderer;
import nl.tudelft.bw4t.map.editor.gui.SizeDialog;
import nl.tudelft.bw4t.map.editor.model.Map;
import nl.tudelft.bw4t.map.editor.model.Room;
import nl.tudelft.bw4t.map.editor.util.DefaultOptionPrompt;
import nl.tudelft.bw4t.map.editor.util.OptionPrompt;
import nl.tudelft.bw4t.map.editor.gui.MenuBar;

/**
 * The MapEditor class serves as a frame for the Panels and tables.
 * @author Rothweiler
 *
 */
public class MapEditor extends JFrame {
	
	private static final long serialVersionUID = 8572609341436634787L;

	private Map map;
	
	private String windowName = "Map Editor";
	
	private ColorLegendaPanel legendaPanel;
	
	private ExplanationPanel explanationPanel;
	
	private final JTable mapTable;
	
	private JPanel roomsPanel;
	
	private ColorSequencePanel sequencePanel;
	
	private MenuBar menuBar;

	private static OptionPrompt option = new DefaultOptionPrompt();
    
    private JButton savebutton = new JButton("Save as Repast Map");
    
    /**
     * Create the MapEditor frame which will hold all the panels, tables and buttons.
     * 
     * @param themap is a map that contains: rows, cols, entities, randomize.
     * 
     */
    public MapEditor(Map themap) {
        this.map = themap;
        
        setWindowTitle("Untitled");
        setLayout(new BorderLayout());
        
        // Attach the menu bar.
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
        
        // TODO: Change to DO_NOTHING_ON_CLOSE when we want to ask the user whether he is sure to exit.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the explainationPanel for on top of the editor.
        explanationPanel = new ExplanationPanel();
        
        // Create the colorLegendaPanel for the right side of the editor.
        legendaPanel = new ColorLegendaPanel();
        
        // Create the colorSequencePanel to be added to the roomsPanel.
        sequencePanel = new ColorSequencePanel(map);   
        
        // Both the mapTable and sequencePanel are added to a rooms panel.
        // TODO: Create a roomsTable class that creates both MapTable and SequencePanel. Problems: map Data model in mapTable and boxLayout in roomsPanel.
        mapTable = new JTable(map);
        mapTable.setDefaultRenderer(Room.class, new RoomCellRenderer());
        mapTable.setDefaultEditor(Room.class, new RoomCellEditor());
        mapTable.setRowHeight(55);
        mapTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        // Create a roomsPanel that has both the mapTable and the sequencePanel.
        roomsPanel = new JPanel();
        roomsPanel.setLayout(new BoxLayout(roomsPanel, BoxLayout.Y_AXIS));
        roomsPanel.add(mapTable);
        roomsPanel.add(sequencePanel);
        
        
        // Attach all Panels to the Editor.
        add(explanationPanel, BorderLayout.NORTH);
        add(roomsPanel, BorderLayout.CENTER);
        add(legendaPanel, BorderLayout.EAST);
        add(savebutton, BorderLayout.SOUTH);

        // attach listeners
        savebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.saveAsFile();
            }

        });

        pack();
        setVisible(true);
    }
    
    /**
     * Opens a dialog showing the description of the error and the error itself as a String.
     * 
     * @param e The exception that is thrown.
     * @param s A description that is specific for why the error occurred.
     */
    public static void showDialog(final Exception e, final String s) {

        MapEditor.option.showMessageDialog(null, s + "\n" + e.toString());
    }
    
    /**
     * Opens a dialog showing a message, but no error.
     * 
     * @param s A message that is displayed to the user.
     */
    public static void showDialog(final String s) {

        MapEditor.option.showMessageDialog(null, s);
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

        Map themap = new Map(dialog.getRows(), dialog.getColumns(),
                dialog.getEntities(), dialog.isRandomMap(),
                dialog.isLabelsVisible());

        if (dialog.isRandomMap()) {
            themap.saveAsFile();
        } else {
            new MapEditor(themap);
        }
    }
}