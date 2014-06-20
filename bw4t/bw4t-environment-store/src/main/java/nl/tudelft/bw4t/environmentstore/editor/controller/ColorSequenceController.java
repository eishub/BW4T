package nl.tudelft.bw4t.environmentstore.editor.controller;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import nl.tudelft.bw4t.environmentstore.editor.colorpalette.ColorPalette;
import nl.tudelft.bw4t.environmentstore.editor.colorpalette.ColorPaletteListener;
import nl.tudelft.bw4t.map.BlockColor;

/**
 * The ColorSequenceController class serves as the controller for the ColorSequence
 *
 */
public class ColorSequenceController implements FocusListener, ColorPaletteListener {
    
    private static ColorPalette colorPalette;
    
    private static JFrame colorPaletteWindow;
    
    private ColorSequenceEditor focus = null;
    
    private Set<ColorSequenceEditor> editors = new HashSet<>();

    /**
     * The constructor calls the setupWindow method to setup the color palette window.
     * It also allows us to add colors to the colorPalette.
     */
    public ColorSequenceController() {
        setupWindow();
        colorPalette.addColorClickListener(this);
    }

    /**
     * setupWindow creates the frame of the Color Palette Window
     */
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

    /**
     * addColorSequenceEditor adds a color to the editor.
     * @param cse is the colorSequenceEditor being used.
     */
    public void addColorSequenceEditor(ColorSequenceEditor cse) {
        if (this.editors.contains(cse)) {
            return;
        }
        this.editors.add(cse);
        cse.addFocusListener(this);
    }

    /**
     * removeColorSequenceEditor removes a color from the editor.
     * @param cse is the ColorSequenceEditor that we want to remove blocks from.
     */
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

        focus = (ColorSequenceEditor) evt.getComponent();
        updatePosition();
    }

    /**
     * positionColorPalette sets the position of the color palette relative to its component.
     * @param comp is the component the position of the color palette will be set relative to.
     */
    private void positionColorPalette(Component comp) {
        colorPaletteWindow.setLocationRelativeTo(comp);

        Point p = colorPaletteWindow.getLocation();
        Rectangle b = comp.getBounds();
        p.x += colorPaletteWindow.getWidth() / 2 - b.width / 2;
        p.y += (colorPaletteWindow.getHeight() / 2) + b.height / 2;
        colorPaletteWindow.setLocation(p);
    }
    
    /** moves the palette with the main frame */
    public void updatePosition() {
        if (focus == null) {
            colorPaletteWindow.setVisible(false);
        } else {
            colorPaletteWindow.setVisible(true);
            positionColorPalette(focus);
        }
    }

    @Override
    public void focusLost(FocusEvent evt) {
        focus = null;
        updatePosition();
    }

    @Override
    public void colorClicked(BlockColor c) {
        if (focus != null) {
            focus.addColor(c);
        }
    }
    
    public ColorSequenceEditor getColorSequenceEditor() {
        return focus;
    }
}
