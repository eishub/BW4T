package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;

/**
 * available block colors. allows to and from string conversion.Note, this is
 * not a list of {@link Color} objects, because you can't get the NAME as string
 * of color objects. The color names must be names that are also known by
 * {@link Color}.
 */
public enum BlockColor implements Serializable {
    BLUE, ORANGE, RED, WHITE, GREEN, YELLOW, PINK;

    /**
     * The log4j logger which writes logs.
     */
    private static final Logger LOGGER = Logger.getLogger(BlockColor.class);


    private BlockColor() {
    }

    /**
     * Get the letter for this color.
     * 
     * @return
     */
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
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            LOGGER.fatal("Failed to find the field in the Color class.", e);
        }
        return null;
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
    public static BlockColor toAvailableColor(Character letter) {
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
    public static BlockColor toAvailableColor(Color color) {
        for (BlockColor c : BlockColor.values()) {
            if (c.getColor().equals(color)) {
                return c;
            }
        }
        throw new IllegalArgumentException("unavailable color " + color);
    }

    /**
     * Get an explanatory name for this color.
     * 
     * @return
     */
    public String getName() {
        String name = name();
        return Character.toString(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
    }

};