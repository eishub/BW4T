package nl.tudelft.bw4t.client;

import java.util.prefs.Preferences;

/**
 * Static object to store settings of the BW4T Client.
 */
public final class BW4TClientSettings {
    
    /**
     * Use the global preference store for this user to store the settings.
     */
    public static final Preferences PREFS = Preferences.userNodeForPackage(BW4TClientSettings.class);

    /**
     * Empty constructor. Prevent a class from being explicitly instantiated by its callers. 
     */
    private BW4TClientSettings() {
    }

    /**
     * Get the preferred x position of top left corner of the window.
     * 
     * @return preferred x pos of top left corner set by user, or 0 by default
     */
    public static int getX() {
        return BW4TClientSettings.PREFS.getInt("x", 0);
    }

    /**
     * Get preferred y position of top left corner of the window.
     * 
     * @return preferred y pos of top left corner set by user, or 0 by default
     */
    public static int getY() {
        return BW4TClientSettings.PREFS.getInt("y", 0);
    }

    /**
     * Save the window settings.
     *
     * @param x            :x pos of top left corner
     * @param y            :y pos of top left corner
     */
    public static void setWindowParams(int x, int y) {
        BW4TClientSettings.PREFS.putInt("x", x);
        BW4TClientSettings.PREFS.putInt("y", y);
    }

}
