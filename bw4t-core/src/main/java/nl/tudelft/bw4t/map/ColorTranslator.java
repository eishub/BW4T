package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Utility class to translate colors.
 */
public final class ColorTranslator {
    /**
     * The log4j logger which writes logs.
     */
    private static final Logger LOGGER = Logger.getLogger(ColorTranslator.class);

    /**
     * Utility class, cannot be instantiated.
     */
    private ColorTranslator() {
    }

    /**
     * Translates a character to a color.
     * 
     * @param color
     *            the character that represents a color.
     * @return The color represented by the character.
     */
    public static Color translate2Color(String color) {
        return BlockColor.toAvailableColor(color.charAt(0)).getColor();
    }

    /**
     * Translates a color to a character
     * 
     * @param color
     *            , the color to be translated
     * @return the character representing the color, otherwise U when it is unknown
     */
    public static String translate2ColorString(Color color) {
        BlockColor bc = null;
        try {
            bc = BlockColor.toAvailableColor(color);
            return bc.getName();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Tried to find Color: " + color, e);
            return "Unknown";
        }
    }

    /**
     * Get all colors available in the environment
     * 
     * @return char list of colors
     */
    public static List<String> getAllColors() {
        List<String> allColors = new ArrayList<String>();
        for (BlockColor c : BlockColor.values()) {
            allColors.add(c.getName());
        }
        return allColors;
    }
}
