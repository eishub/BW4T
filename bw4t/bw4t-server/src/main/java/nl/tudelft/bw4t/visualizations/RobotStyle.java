package nl.tudelft.bw4t.visualizations;

import java.awt.Color;

import nl.tudelft.bw4t.robots.Robot;
import saf.v3d.scene.Position;

public class RobotStyle extends BoundedMoveableObjectStyle<Robot> {

  @Override
  public Color getColor(Robot robot) {
    if(!robot.isHolding().isEmpty()) {
      Color c = new Color(0,0,0,0);
      for(int i = 0; i < robot.isHolding().size(); i++) {
    	  c = blend(c,robot.isHolding().get(i).getColor());
      }
      return c;
    }
    return Color.BLACK;
  }

  @Override
  public String getLabel(Robot robot) {
    return robot.getName();
  }

  @Override
  public Position getLabelPosition(Robot robot) {
    return Position.SOUTH;
  }
  /**
   * A method used to blend colors, to represent multiple colors
   * at once. Found on http://www.java2s.com/Code/Java/2D-Graphics-GUI/Blendtwocolors.htm
   * @param c0 The first color.
   * @param c1 The second color.
   * @return The color that is a result of both colors.
   */
  private Color blend(Color c0, Color c1) {
	    double totalAlpha = c0.getAlpha() + c1.getAlpha();
	    double weight0 = c0.getAlpha() / totalAlpha;
	    double weight1 = c1.getAlpha() / totalAlpha;

	    double r = weight0 * c0.getRed() + weight1 * c1.getRed();
	    double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
	    double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
	    double a = Math.max(c0.getAlpha(), c1.getAlpha());

	    return new Color((int) r, (int) g, (int) b, (int) a);
  }
}