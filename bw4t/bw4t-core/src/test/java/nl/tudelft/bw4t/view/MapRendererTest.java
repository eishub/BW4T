package nl.tudelft.bw4t.view;

import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.controller.MapController;
import nl.tudelft.bw4t.controller.TestMapController;
import nl.tudelft.bw4t.map.NewMap;

public class MapRendererTest extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 3165062664023548124L;

    MapRenderer mapRenderer;
    NewMap map;
    MapController mapController;

    public MapRendererTest() throws JAXBException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        InputStream inputStream = MapRendererTest.class.getResourceAsStream("/Rainbow");
        map = NewMap.create(inputStream);
        mapController = new TestMapController(map);
        mapRenderer = new MapRenderer(mapController);
        this.add(new JScrollPane(mapRenderer));
        this.pack();
    }

    public static void main(String[] args) {
        try {
            JFrame testing = new MapRendererTest();
            testing.setVisible(true);
            testing.setLocationRelativeTo(null);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
