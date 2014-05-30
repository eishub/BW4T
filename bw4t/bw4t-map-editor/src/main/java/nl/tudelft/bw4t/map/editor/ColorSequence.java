package nl.tudelft.bw4t.map.editor;

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
        String letters = "";
        for (BlockColor c : colors) {
            letters = letters + c.toString().charAt(0);
        }
        return letters;
    }

    /**
     * returns space-separated list of color names with first character upper
     * case (as is required for the BW4T2 map files).
     * 
     * @return
     */
    public String toColorString() {
        String string = "";
        for (BlockColor c : colors) {
            String colorstr = c.toString(); // all upper case.
            string += colorstr.substring(0, 1)
                    + colorstr.substring(1).toLowerCase() + " ";
        }
        return string;
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
    public void addAll(ColorSequence colors2) {
        colors.addAll(colors2.getColors());

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
     * 
     * @param seq2
     */
    public void removeAll(ColorSequence seq2) {
        for (BlockColor color : seq2.getColors()) {
            if (colors.remove(color) == false) {
                throw new IllegalArgumentException("lacking " + color);
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