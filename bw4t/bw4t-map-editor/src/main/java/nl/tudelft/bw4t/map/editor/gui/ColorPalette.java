package nl.tudelft.bw4t.map.editor.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import nl.tudelft.bw4t.map.BlockColor;

public class ColorPalette extends JPanel implements MouseInputListener {
    private static final long serialVersionUID = -1931059148668661725L;
    private static final int COLOR_SIZE = 30;
    private static final int BORDER = 2;

    private List<ColorPaletteListener> onColorClick = new LinkedList<>();

    public ColorPalette() {
        super();
        Dimension d = new Dimension(BlockColor.getAvailableColors().size() * COLOR_SIZE + BORDER * 2, COLOR_SIZE
                + BORDER * 2);
        setMinimumSize(d);
        setPreferredSize(d);
        addMouseListener(this);
    }

    public void addColorClickListener(ColorPaletteListener cpl) {
        this.onColorClick.add(cpl);
    }

    public void removeColorClickListener(ColorPaletteListener cpl) {
        this.onColorClick.remove(cpl);
    }

    private void notifyColorClick(BlockColor c) {
        for (ColorPaletteListener cpl : onColorClick) {
            cpl.colorClicked(c);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int startX = BORDER;
        for (BlockColor c : BlockColor.getAvailableColors()) {
            g.setColor(c.getColor());
            g.fillRect(startX, BORDER, COLOR_SIZE, COLOR_SIZE);
            startX += COLOR_SIZE;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        if (p.y <= BORDER || p.y > BORDER + COLOR_SIZE)
            return;

        int startX = BORDER;
        for (BlockColor c : BlockColor.getAvailableColors()) {
            if (p.x >= startX && p.x < startX + COLOR_SIZE) {
                notifyColorClick(c);
                return;
            }
            startX += COLOR_SIZE;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = getColorPaletteWindow(new ColorPalette());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static JFrame getColorPaletteWindow(ColorPalette cp) {
        JFrame frame = new JFrame("Color Palette");
        frame.add(cp);
        frame.setUndecorated(true);
        frame.pack();
        return frame;
    }
}
