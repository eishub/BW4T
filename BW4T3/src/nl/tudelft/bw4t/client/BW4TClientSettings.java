package nl.tudelft.bw4t.client;

import java.util.prefs.Preferences;

/**
 * Static object to store settings
 * 
 * @author W.Pasman 14jan2014
 * 
 */

public class BW4TClientSettings {

	/**
	 * Use the global preference store for this user to store the settings.
	 */
	static public Preferences prefs = Preferences
			.userNodeForPackage(BW4TClientSettings.class);

	/**
	 * get preferred x position of top left corner of the window.
	 * 
	 * @return preferred x pos of top left corner set by user, or 0 by default
	 */
	public static int getX() {
		return BW4TClientSettings.prefs.getInt("x", 0);
	}

	/**
	 * get preferred y position of top left corner of the window.
	 * 
	 * @return preferred y pos of top left corner set by user, or 0 by default
	 */
	public static int getY() {
		return BW4TClientSettings.prefs.getInt("y", 0);
	}

	/**
	 * save the window settings
	 * 
	 * @param x
	 *            :x pos of top left corner
	 * @param y
	 *            :y pos of top left corner
	 */
	public static void setWindowParams(int x, int y) {
		BW4TClientSettings.prefs.putInt("x", x);
		BW4TClientSettings.prefs.putInt("y", y);

	}

}