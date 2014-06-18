package nl.tudelft.bw4t.environmentstore.editor.colorpalette;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

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
