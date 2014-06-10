package nl.tudelft.bw4t.map.editor.controller;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import org.omg.CORBA.Bounds;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.editor.gui.ColorPalette;
import nl.tudelft.bw4t.map.editor.gui.ColorPaletteListener;
import nl.tudelft.bw4t.map.editor.gui.ColorSequenceEditor;

public class ColorSequenceController implements FocusListener, ColorPaletteListener {
    private static ColorPalette colorPalette;
    private static JFrame colorPaletteWindow;
    private ColorSequenceEditor focus = null;
    private Set<ColorSequenceEditor> editors = new HashSet<>();

    public ColorSequenceController() {
        setupWindow();
        colorPalette.addColorClickListener(this);
    }

    public static void setupWindow() {
        if (colorPalette == null) {
            colorPalette = new ColorPalette();
        }
        if (colorPaletteWindow == null) {
            colorPaletteWindow = new JFrame("Color Palette Window");
            colorPaletteWindow.setUndecorated(true);
            colorPaletteWindow.add(colorPalette);
            colorPaletteWindow.setAlwaysOnTop(true);
            colorPaletteWindow.pack();
            colorPaletteWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            colorPaletteWindow.setFocusableWindowState(false);
        }
    }

    public void addColorSequenceEditor(ColorSequenceEditor cse) {
        if (this.editors.contains(cse)) {
            return;
        }
        this.editors.add(cse);
        cse.addFocusListener(this);
    }

    public void removeColorSequenceEditor(ColorSequenceEditor cse) {
        if (!this.editors.contains(cse)) {
            return;
        }
        this.editors.remove(cse);
        cse.removeFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent evt) {
        if (!(evt.getComponent() instanceof ColorSequenceEditor)) {
            return;
        }

        positionColorPalette(evt.getComponent());
        colorPaletteWindow.setVisible(true);
        focus = (ColorSequenceEditor) evt.getComponent();
    }

    private void positionColorPalette(Component comp) {
        colorPaletteWindow.setLocationRelativeTo(comp);

        Point p = colorPaletteWindow.getLocation();
        Rectangle b = comp.getBounds();
        p.x += colorPaletteWindow.getWidth() / 2 - b.width / 2;
        p.y += (colorPaletteWindow.getHeight() / 2) + b.height / 2;
        colorPaletteWindow.setLocation(p);
    }

    @Override
    public void focusLost(FocusEvent evt) {
        colorPaletteWindow.setVisible(false);
        focus = null;
    }

    @Override
    public void colorClicked(BlockColor c) {
        if (focus != null) {
            focus.addColor(c);
        }
    }
}
