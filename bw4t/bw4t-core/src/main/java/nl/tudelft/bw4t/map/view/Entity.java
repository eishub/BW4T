package nl.tudelft.bw4t.map.view;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Entity {
	/** The width and height of the robot */
	public final static int ROBOT_SIZE = 2;
	public final static Color ROBOT_COLOR = Color.BLACK;

	private final Map<Long, Block> holding = new HashMap<>();

	private Point2D location = new Point2D.Double();

	public Map<Long, Block> getHolding() {
		return holding;
	}

	public Block getFirstHolding() {
		Iterator<Block> blocks = holding.values().iterator();
		if (blocks.hasNext()) {
			return blocks.next();
		}
		return null;
	}

	public Color getColor() {
		if (holding.isEmpty()) {
			return ROBOT_COLOR;
		}
		// FIXME looks nasty
		return holding.values().iterator().next().getColor().getColor();
	}

	public Point2D getLocation() {
		return location;
	}

	public void setLocation(double x, double y) {
		location = new Point2D.Double(x, y);
	}
}
