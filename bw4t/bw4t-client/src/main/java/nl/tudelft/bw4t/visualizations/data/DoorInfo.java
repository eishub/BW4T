package nl.tudelft.bw4t.visualizations.data;

/**
 * Helper method to store information about doors
 * 
 */
public class DoorInfo {
    private double x, y, width, height;
    private EnvironmentDatabase environmentDatabase;

    public DoorInfo(double x, double y, double width, double height, EnvironmentDatabase environmentDatabase) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.environmentDatabase = environmentDatabase;
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