package nl.tudelft.bw4t.map.editor.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import nl.tudelft.bw4t.map.editor.model.Room;

/**
 * This is a renderer for a room.
 */
@SuppressWarnings("serial")
public class RoomCellRenderer extends JPanel implements TableCellRenderer {

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellRendererComponent(JTable arg0, Object arg1,
            boolean arg2, boolean arg3, int arg4, int arg5) {
        if (arg1 instanceof Room) {
            Room room = (Room) arg1;
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel(room.toString()), BorderLayout.NORTH);
            // panel.setBackground(Color.black);
            panel.add(new JTextField(room.getColors().getLetters()),
                    BorderLayout.CENTER);
            return panel;
        }
        return new JLabel("??");
    }
}
