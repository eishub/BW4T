package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Utility class to translate colors. CHECK can we now use BlockColor instead?
 * 
 * @author Lennard de Rijk
 * @author W.Pasman removed magenta and cyan
 */
public final class ColorTranslator {

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
        if (color.equals("Blue"))
            return Color.BLUE;
        else if (color.equals("Orange"))
            return new Color(255, 146, 0);
        else if (color.equals("Red"))
            return Color.RED;
        else if (color.equals("White"))
            return Color.WHITE;
        else if (color.equals("Green"))
            return Color.GREEN;
        else if (color.equals("Yellow"))
            return Color.YELLOW;
        else if (color.equals("Pink"))
            return new Color(255, 201, 195);
        else
            throw new IllegalArgumentException(color
                    + " is not a valid color indicator");
    }

    /**
     * Translates a color to a character
     * 
     * @param color
     *            , the color to be translated
     * @return the character representing the color, otherwise U when it is
     *         unknown
     */
    public static String translate2ColorString(Color color) {
        if (color.equals(Color.BLUE)) {
            return "Blue";
        } else if (color.equals(Color.ORANGE)) {
            return "Orange";
        } else if (color.equals(Color.RED)) {
            return "Red";
        } else if (color.equals(Color.WHITE)) {
            return "White";
        } else if (color.equals(Color.GREEN)) {
            return "Green";
        } else if (color.equals(Color.YELLOW)) {
            return "Yellow";
        } else if (color.equals(Color.PINK)) {
            return "Pink";
        } else {
            return "Unknown";
        }
    }

    /**
     * Get all colors available in the environment
     * 
     * @return char list of colors
     */
    public static ArrayList<String> getAllColors() {
        ArrayList<String> allColors = new ArrayList<String>();
        allColors.add("Blue");
        allColors.add("Orange");
        allColors.add("Red");
        allColors.add("White");
        allColors.add("Green");
        allColors.add("Yellow");
        allColors.add("Pink");
        return allColors;
    }
}
