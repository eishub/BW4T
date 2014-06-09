package nl.tudelft.bw4t.map.editor.controller;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import nl.tudelft.bw4t.map.editor.gui.ColorSequenceEditor;
import nl.tudelft.bw4t.map.editor.model.Room;

/**
 * This is a cell editor for a room. It renders a table cell for the room. The
 * cell contains the room name (x,y numbers) and a ColorSequenceEditor.
 */
public class RoomCellEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = -6620921724261764896L;
	private Room room; // the room currently edited.
    private ColorSequenceEditor colorEditor; // and the editor that we have

    @Override
    /**
     * {@inheritDoc}
     */
    public Object getCellEditorValue() {
        Room newRoom = new Room(0, 0); // coords are irrelevant for temp room
        newRoom.setColors(colorEditor.getColors());
        return newRoom;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Component getTableCellEditorComponent(JTable arg0, Object arg1,
            boolean isSelected, int arg3, int arg4) {
        if (!(arg1 instanceof Room)) {
            return null; // no editor in that case.
        }
        if (!isSelected) {
            return null;
        }
        room = (Room) arg1;
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(room.toString()), BorderLayout.NORTH);
        colorEditor = new ColorSequenceEditor(room.getColors());
        panel.add(colorEditor, BorderLayout.CENTER);
        return panel;
    }

}
