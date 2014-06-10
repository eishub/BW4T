package nl.tudelft.bw4t.map.editor.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.bw4t.map.BlockColor;

public class ColorLegendaPanel extends JPanel {

	private static final long serialVersionUID = -748109518409415372L;

	public ColorLegendaPanel() {
        setLayout(new GridLayout(0, 2));
        setBackground(Color.CYAN);

        add(new JLabel("Letter"));
        add(new JLabel("Color"));
        // the table is two wide, we put two values each round.
        for (BlockColor blockcol : BlockColor.values()) {
        	if (blockcol != BlockColor.DARK_GRAY) {
	            String colorname = blockcol.toString();
	            add(new JLabel(colorname.substring(0, 1)));
	            add(new JLabel(blockcol.getName()));
        	}
        }
    }
}