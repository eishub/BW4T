package nl.tudelft.bw4t.client.gui.data.structures;

/**
 * Helper class to store information about the drop zone
 * 
 * @author trens
 * 
 */
public class DropZoneInfo extends RoomInfo {
    public DropZoneInfo(double x, double y, double width, double height) {
        super(x, y, width, height, "DropZone");
    }
}
