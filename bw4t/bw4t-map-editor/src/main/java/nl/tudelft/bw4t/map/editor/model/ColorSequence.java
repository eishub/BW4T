package nl.tudelft.bw4t.map.editor.model;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * This class contains a sequence of {@link BlockColor}. The order of this list
 * is maintained, although in some cases (eg, room blocks) the order is
 * irrelevant.
 */
public class ColorSequence {
    private List<BlockColor> colors = new ArrayList<BlockColor>();

    /**
     * add new color to the end of the list.
     * 
     * @param availableColor
     */
    public void add(BlockColor c) {
        colors.add(c);
    }

    /**
     * get a string with the first letters of the {@link #colors}
     * 
     * @return string with letters of the {@link #colors}
     */
    public String getLetters() {
        StringBuffer buf = new StringBuffer();
        for (BlockColor c : colors) {
        	buf.append(c.toString().charAt(0)); 
        }
        return buf.toString();
    }

    /**
     * returns space-separated list of color names with first character upper
     * case (as is required for the BW4T2 map files).
     * 
     * @return
     */
    public String toColorString() {
        StringBuffer buf = new StringBuffer(); 
        for (BlockColor c : colors) {
            String colorstr = c.toString(); // all upper case.
            buf.append(colorstr.substring(0, 1) + colorstr.substring(1).toLowerCase() + " ");
        }
        return buf.toString();
    }

    /**
     * get size of sequence.
     * 
     * @return
     */
    public int size() {
        return colors.size();
    }

    /**
     * adds all given colors to THIS.
     * 
     * @param colors2
     */
    public void addAll(ColorSequence theColors) {
        colors.addAll(theColors.getColors());
    }

    /**
     * get list of all colors in the sequence
     * 
     * @return list of all colors in the sequence
     */
    public List<BlockColor> getColors() {
        return colors;
    }

    /**
     * Removes all colors from this.
     * 
     * @param theSequence
     */
    public void removeAll(ColorSequence theSequence) {
        for (BlockColor color : theSequence.getColors()) {
            if (colors.remove(color) == false) {
                throw new IllegalArgumentException("Lacking " + color);
            }
        }
    }

    /**
     * returns true if the sequence is empty.
     * 
     * @return true if the sequence is empty, else false.
     */
    public boolean isEmpty() {
        return colors.isEmpty();
    }

}