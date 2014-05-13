package nl.tudelft.bw4t.visualizations;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

import nl.tudelft.bw4t.BoundedMoveableObject;
import repast.simphony.visualizationOGL2D.StyleOGL2D;
import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;

/**
 * Base class for visualization of {@link BoundedMoveableObject}.
 * 
 * @param <T> Subclass of {@link BoundedMoveableObject} to visualize.
 * 
 * @author Lennard de Rijk
 */
public abstract class BoundedMoveableObjectStyle<T extends BoundedMoveableObject> implements
    StyleOGL2D<T> {

  /** Unit size of the display, how many pixels is one world coordinate. */
  public static final int UNIT_SIZE = 15;

  private static final Font FONT = new Font("Arial", Font.PLAIN, 22);

  /** Factory for creating shapes/spatials. */
  private ShapeFactory2D factory;

  @Override
  public void init(ShapeFactory2D factory) {
    this.factory = factory;
  }

  @Override
  public VSpatial getVSpatial(T object, VSpatial spatial) {
    if (spatial == null) {
      Rectangle2D boundingBox = object.getBoundingBox();
      spatial =
          factory
              .createRectangle((int) boundingBox.getWidth(), (int) boundingBox.getHeight(), true);
    }
    return spatial;
  }

  @Override
  public int getBorderSize(T object) {
    return 0;
  }

  @Override
  public Color getBorderColor(T object) {
    return null;
  }

  @Override
  public Color getColor(T object) {
    return null;
  }

  @Override
  public float getRotation(T object) {
    return 0;
  }

  @Override
  public float getScale(T object) {
    return UNIT_SIZE;
  }

  @Override
  public String getLabel(T object) {
    return null;
  }

  @Override
  public Font getLabelFont(T object) {
    return FONT;
  }

  @Override
  public float getLabelXOffset(T object) {
    return 0;
  }

  @Override
  public float getLabelYOffset(T object) {
    return 0;
  }

  @Override
  public Position getLabelPosition(T object) {
    return null;
  }

  @Override
  public Color getLabelColor(T object) {
    return Color.BLACK;
  }
}
