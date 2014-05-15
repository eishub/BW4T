package nl.tudelft.bw4t.visualizations;

import java.awt.Color;

import nl.tudelft.bw4t.robots.Robot;
import saf.v3d.scene.Position;

public class RobotStyle extends BoundedMoveableObjectStyle<Robot> {

  @Override
  public Color getColor(Robot robot) {
    if(robot.isHolding() != null) {
      return robot.isHolding().getColor();
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
}
