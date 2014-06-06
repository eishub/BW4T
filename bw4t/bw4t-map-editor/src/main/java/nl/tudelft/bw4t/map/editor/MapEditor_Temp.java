package nl.tudelft.bw4t.map.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;

import nl.tudelft.bw4t.map.editor.ColorLegenda;
import nl.tudelft.bw4t.map.editor.ColorSequenceEditor;
import nl.tudelft.bw4t.map.editor.MapEditor;
import nl.tudelft.bw4t.map.editor.RoomCellEditor;
import nl.tudelft.bw4t.map.editor.RoomCellRenderer;
import nl.tudelft.bw4t.map.editor.SizeDialog;
import nl.tudelft.bw4t.map.editor.model.Map;
import nl.tudelft.bw4t.map.editor.model.Room;

public class MapEditor_Temp {
    private Map map;
    private ColorSequenceEditor seqEdit;
    private JButton savebutton = new JButton("Save as Repast Map");

    // public so that others can access it for centering GUIs.
    public static JFrame frame = new JFrame("BW4T Map Editor");

    /**
     * creates an editor for the given size of map
     * 
     * @param rows
     *            number of room rows on the map
     * @param cols
     *            number of room columns
     * @param entities
     *            number of entities to be placed on the map.
     * @param randomize
     *            is true if map should contain random blocks, else false.
     */
    public MapEditor_Temp(Map themap) {
        this.map = themap;

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel explanation = new JPanel(new BorderLayout());
        explanation
                .add(new JLabel(
                        "To add blocks to the map, enter letters in rooms (up to 10) and target sequence."),
                        BorderLayout.CENTER);
        explanation.add(new JSeparator(), BorderLayout.SOUTH);

        frame.add(explanation, BorderLayout.NORTH);

        // the map
        JTable table = new JTable(map);
        table.setDefaultRenderer(Room.class, new RoomCellRenderer());
        table.setDefaultEditor(Room.class, new RoomCellEditor());
        table.setRowHeight(35);
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        // and the panel with the target sequence
        JPanel sequencepanel = new JPanel(new BorderLayout());
        sequencepanel.add(new JLabel("target sequence"), BorderLayout.WEST);
        seqEdit = new ColorSequenceEditor(map.getSequence());
        sequencepanel.add(seqEdit, BorderLayout.CENTER);
        // the only way to enforce small sequence panel??
        sequencepanel.setMaximumSize(new Dimension(400, 20));

        // hang map and panel in the rooms panel
        JPanel roomspanel = new JPanel();
        roomspanel.setLayout(new BoxLayout(roomspanel, BoxLayout.Y_AXIS));
        roomspanel.add(table);
        roomspanel.add(sequencepanel);

        frame.add(roomspanel, BorderLayout.CENTER);
        frame.add(new ColorLegenda(), BorderLayout.EAST);

        frame.add(savebutton, BorderLayout.SOUTH);

        // attach listeners
        seqEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                map.setSequence(seqEdit.getColors());
            }
        });
        seqEdit.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent arg0) {
                map.setSequence(seqEdit.getColors());

            }

            @Override
            public void focusGained(FocusEvent e) {
            }
        });

        savebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.saveAsFile();
            }

        });

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * simple startup that asks user for map size and then creates the editor.
     * 
     * @param args
     *            not used.
     */
    public static void main(String[] args) {
        SizeDialog dialog = new SizeDialog();
        if (JOptionPane.CLOSED_OPTION == JOptionPane.showOptionDialog(frame,
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