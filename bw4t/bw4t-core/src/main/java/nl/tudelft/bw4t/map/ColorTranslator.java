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
		return BlockColor.toAvailableColor(color.charAt(0)).getColor();
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
		BlockColor bc = null;
		try {
			bc = BlockColor.toAvailableColor(color);
			return bc.getName();
		} catch(IllegalArgumentException e) {
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
		for(BlockColor c : BlockColor.values()){
			allColors.add(c.getName());
		}
		return allColors;
	}
}
