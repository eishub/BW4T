package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * available block colors. allows to and from string conversion
 */
public enum BlockColor implements Serializable {
	/**
	 * Note, this is not a list of {@link Color} objects, because you can't get
	 * the NAME as string of color objects. The color names must be names that
	 * are also known by {@link Color}.
	 */
	BLUE("Blue"), ORANGE("Orange"), RED("Red"), WHITE("White"), GREEN("Green"), YELLOW(
			"Yellow"), PINK("Pink");

	private String name;

	private BlockColor(String n) {
		name = n;
	}

	/**
	 * Get the letter for this color.
	 * 
	 * @return
	 */
	public Character getLetter() {
		return name.charAt(0);
	}

	/**
	 * Convert an AvailableColor into a {@link Color}.
	 * 
	 * @return java color
	 */
	public Color getColor() {
		Field field;
		try {
			field = Class.forName("java.awt.Color").getField(this.toString());
			return (Color) field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * convert given letter to its color equivalent
	 * 
	 * @param letter
	 *            is the first letter from the {@link #values} list
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
	 * @return the name of this color
	 */
	public String getName() {
		return name;
	}

};