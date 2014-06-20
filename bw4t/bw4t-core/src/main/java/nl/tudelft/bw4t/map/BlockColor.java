package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * available block colors. allows to and from string conversion.Note, this is
 * not a list of {@link Color} objects, because you can't get the NAME as string
 * of color objects. The color names must be names that are also known by
 * {@link Color}.
 */
public enum BlockColor implements Serializable {
    /** The possible colors a block can have. */
    RED, ORANGE, YELLOW, GREEN, BLUE, PINK, WHITE, DARK_GRAY;

    /** The log4j logger which writes logs into console */
    private static final Logger LOGGER = Logger.getLogger(BlockColor.class);
    
    /** List containing all possible block colors. */
    private static List<BlockColor> colors = null;

    /** 
     * Empty constructor of BlockColor. 
     */
    private BlockColor() {
    }

    public Character getLetter() {
        return getName().charAt(0);
    }

    /**
     * Convert an AvailableColor into a {@link Color}.
     * 
     * @return java color
     */
    public Color getColor() {
        Field field;
        try {
            field = Class.forName("java.awt.Color").getField(this.name());
            return (Color) field.get(null);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException 
                | IllegalArgumentException | IllegalAccessException e) {
            LOGGER.fatal("Failed to find the field in the Color class.", e);
        }
        return null;
    }
    
    /**
     * Calculate the luminosity value using the CIE 1931({@link http://en.wikipedia.org/wiki/CIE_1931_color_space}).
     * @return the luminosity value
     */
    public int getLuminosity() {
        Color c = getColor();
        return (int) (0.2126 * c.getRed() + 0.7152 * c.getGreen() + 0.0722 * c.getBlue());
    }

    /**
     * convert given letter to its color equivalent
     * 
     * @param letter
     *            is the first letter from the {@link #availableColors} list
     * @return Color of given letter
     * @throws IllegalArgumentException
     *             if unknown color letter is given.
     */
    public static BlockColor toAvailableColor(Character letter) throws IllegalArgumentException {
        for (BlockColor c : BlockColor.values()) {
            char colLetter = c.toString().toLowerCase().charAt(0);
            if (Character.toLowerCase(letter) == colLetter) {
                return c;
            }
        }
        throw new IllegalArgumentException("unknown color letter " + letter);
    }

    /**
     * Convert {@link Color} to a {@link BlockColor}
     * 
     * @param color
     *            is {@link Color} to convert
     * @return {@link BlockColor}
     * @throws IllegalArgumentException
     *             if color is not availale.
     */
    public static BlockColor toAvailableColor(Color color) throws IllegalArgumentException {
        for (BlockColor c : BlockColor.values()) {
            if (c.getColor().equals(color)) {
                return c;
            }
        }
        throw new IllegalArgumentException("unavailable color " + color);
    }

    public String getName() {
        return Character.toString(name().charAt(0)).toUpperCase() + name().substring(1).toLowerCase();
    }
    
    /**
     * Use this function instead of the values, it will skip over the dark grey option!
     * @return list of available block colors
     */
    public static List<BlockColor> getAvailableColors() {
        if (colors == null) {
            List<BlockColor> cs = new ArrayList<>(Arrays.asList(BlockColor.values()));
            cs.remove(BlockColor.DARK_GRAY);
            colors = cs;
        }
        return colors;
    }

}
