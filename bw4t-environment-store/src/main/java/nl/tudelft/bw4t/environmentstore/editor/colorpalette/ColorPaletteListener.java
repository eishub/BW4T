package nl.tudelft.bw4t.environmentstore.editor.colorpalette;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * ColorPaletteListener for all the different colors on the palette.
 *
 */
public interface ColorPaletteListener {
    void colorClicked(BlockColor c);
}
