package nl.tudelft.bw4t.view;

import javax.swing.JFrame;

import nl.tudelft.bw4t.controller.MapRenderSettings;

public class MapRenderSettingsConfiguration extends JFrame {

    private final MapRenderSettings renderSettings;

    public MapRenderSettingsConfiguration(MapRenderSettings settings){
        this.renderSettings = settings;
    }

    private void init() {

    }

    public static void main(String[] args) {
        MapRenderSettingsConfiguration screen = new MapRenderSettingsConfiguration(new MapRenderSettings());
        screen.setVisible(true);
    }

}
