package nl.tudelft.bw4t.map.renderer;

import javax.swing.JFrame;

/**
 * TODO: Configuration screen for the map renderer.
 *
 */
public class MapRenderSettingsConfiguration extends JFrame {

    private final MapRenderSettings renderSettings;

    public MapRenderSettingsConfiguration(MapRenderSettings settings){
        this.renderSettings = settings;
    }
    
    public static void main(String[] args) {
        MapRenderSettingsConfiguration screen = new MapRenderSettingsConfiguration(new MapRenderSettings());
        screen.setVisible(true);
    }

}
