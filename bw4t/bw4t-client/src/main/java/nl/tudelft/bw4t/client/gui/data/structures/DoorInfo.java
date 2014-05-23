package nl.tudelft.bw4t.client.gui.data.structures;

/**
 * Helper method to store information about doors
 */
public class DoorInfo {
    private double x, y, width, height;

    public DoorInfo(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}