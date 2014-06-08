package nl.tudelft.bw4t.map.editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.bw4t.map.BlockColor;

@SuppressWarnings("serial")
public class ColorLegenda extends JPanel {

    public ColorLegenda() {
        setLayout(new GridLayout(0, 2));
        setBackground(Color.CYAN);

        add(new JLabel("Letter"));
        add(new JLabel("Color"));
        // the table is two wide, we put two values each round.
        for (BlockColor blockcol : BlockColor.values()) {
            String colorname = blockcol.toString();
            add(new JLabel(colorname.substring(0, 1)));
            add(new JLabel(blockcol.getName()));
        }
    }
}