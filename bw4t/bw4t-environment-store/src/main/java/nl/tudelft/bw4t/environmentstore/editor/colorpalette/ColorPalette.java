package nl.tudelft.bw4t.environmentstore.editor.colorpalette;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * The ColorPalette class creates the window that is used for the Palette.
 *
 */
public class ColorPalette extends JPanel implements MouseInputListener {
    
    /** Random generated serial version UID. */
    private static final long serialVersionUID = -1931059148668661725L;
    
    /** size of the palette */
    private static final int COLOR_SIZE = 30;
     
    /** the border of the palette */ 
    private static final int BORDER = 2;

    /** the color the mouse is pointing to */
    private int mouseDownColorIndex = -1;

    /** list with colors */
    private List<ColorPaletteListener> onColorClick = new LinkedList<>();

    /**
     * The constructor sets the superclass, dimensions and adds a mouselistener.
     */
    public ColorPalette() {
        super();
        Dimension d = new Dimension(BlockColor.getAvailableColors().size() * COLOR_SIZE + BORDER * 2, COLOR_SIZE
                + BORDER * 2);
        setMinimumSize(d);
        setPreferredSize(d);
        addMouseListener(this);
    }

    /**
     * Add a ColorPaletteListener to the color that has been clicked.
     * @param cpl the listener for the colorpalette
     */
    public void addColorClickListener(ColorPaletteListener cpl) {
        this.onColorClick.add(cpl);
    }

    /**
     * Remove a ColorPaletteListener from the color.
     * @param cpl the listener for the colorpalette
     */
    public void removeColorClickListener(ColorPaletteListener cpl) {
        this.onColorClick.remove(cpl);
    }
    
    /**
     * notifies what color has been clicked
     * @param c the color clicked 
     */
    private void notifyColorClick(BlockColor c) {
        for (ColorPaletteListener cpl : onColorClick) {
            cpl.colorClicked(c);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int startX = BORDER;
        int number = 1;
        for (BlockColor c : BlockColor.getAvailableColors()) {
            g.setColor(c.getColor());
            g.fillRect(startX, BORDER, COLOR_SIZE, COLOR_SIZE);
            
            int cvalue = 255 - c.getLuminosity();
            g.setColor(new Color(cvalue, cvalue, cvalue));
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(Integer.toString(number), startX + BORDER + 7, BORDER + COLOR_SIZE - 7);
            
            startX += COLOR_SIZE;
            number++;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        if (p.y <= BORDER || p.y > BORDER + COLOR_SIZE)
            return;

        int startX = BORDER;
        for (int i = 0; i < BlockColor.getAvailableColors().size(); i++) {
            if (p.x >= startX && p.x < startX + COLOR_SIZE) {
                mouseDownColorIndex = i;
                return;
            }
            startX += COLOR_SIZE;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseDownColorIndex >= 0) {
            Point p = e.getPoint();
            if (p.y > BORDER && p.y <= BORDER + COLOR_SIZE) {
                int startX = BORDER + COLOR_SIZE * mouseDownColorIndex;
                if (p.x >= startX && p.x < startX + COLOR_SIZE) {
                    notifyColorClick(BlockColor.getAvailableColors().get(mouseDownColorIndex));
                }
            }
        }
        mouseDownColorIndex = -1;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Create the window that holds the ColorPalette.
     * @param cp is the colorPalette used.
     * @return frame is the frame that has been created from the ColorPalette.
     */
    public static JFrame getColorPaletteWindow(ColorPalette cp) {
        JFrame frame = new JFrame("Color Palette");
        frame.add(cp);
        frame.setUndecorated(true);
        frame.pack();
        return frame;
    }
}
