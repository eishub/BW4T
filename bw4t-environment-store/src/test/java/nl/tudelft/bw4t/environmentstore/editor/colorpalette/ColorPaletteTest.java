package nl.tudelft.bw4t.environmentstore.editor.colorpalette;

import java.awt.Graphics;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

public class ColorPaletteTest {
    
    private ColorPalette palette;
    
    private ColorPaletteListener listener;
    
    @Before
    public void setUp() {
        palette = new ColorPalette();

    }
 
    @Test
    public void test() {
        JFrame cp = ColorPalette.getColorPaletteWindow(palette);
        Graphics g = cp.getGraphics();
        cp.paint(g);

    }

}
