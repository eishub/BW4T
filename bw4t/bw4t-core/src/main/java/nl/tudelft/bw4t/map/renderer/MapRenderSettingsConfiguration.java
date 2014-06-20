package nl.tudelft.bw4t.map.renderer;

import javax.swing.JFrame;

/**
 * A Configuration screen for the map renderer.
 *
 */
public class MapRenderSettingsConfiguration extends JFrame {
   
    /** Serialization id. */
    private static final long serialVersionUID = 9202873246960555527L;
    
    private final MapRenderSettings renderSettings;

    /**
     * @param settings to which renderSettings will be set.
     */
    public MapRenderSettingsConfiguration(MapRenderSettings settings) {
        this.renderSettings = settings;
    }
    
    public static void main(String[] args) {
        MapRenderSettingsConfiguration screen = new MapRenderSettingsConfiguration(new MapRenderSettings());
        screen.setVisible(true);
    }

}
