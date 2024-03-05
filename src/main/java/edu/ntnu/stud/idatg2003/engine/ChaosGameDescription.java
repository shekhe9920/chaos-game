package edu.ntnu.stud.idatg2003.engine;

import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.transformation.Transform2D;
import java.util.Iterator;
import java.util.List;


/**
 * Class representing a description of a chaos game, which includes the necessary
 * information to compute the fractal.
 * This includes a list of transformations
 * (affine or Julia) and the area in the plane where the fractal should be drawn.
 * The area is specified by providing the coordinates (x_(0,min), x_(1,min)) of the lower left
 * corner and (x_(0,max), x_(1,max) of the upper right corner.
 * For example, a complete description of the chaos game generating the Sierpinski triangle
 * would be given by the three transformations in equation
 * (2) and the coordinates (0,0) and (1,1)
 * for the corners.
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class ChaosGameDescription {

  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final List<Transform2D> transformations;



  /**
   * Constructs a ChaosGameDescription object with the specified transformations
   * and the bounding coordinates of the area where the fractal should be drawn.
   *
   * @param transformations List of transformations (affine or Julia).
   * @param minCoords      Coordinates of the lower left corner of the drawing area.
   * @param maxCoords      Coordinates of the upper right corner of the drawing area.
   * @since 0.0.1
   */
  public ChaosGameDescription(List<Transform2D> transformations, Vector2D minCoords, Vector2D maxCoords) {
    this.transformations = transformations;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
  }

  /**
   * Retrieves the coordinates of the upper right corner of the drawing area.
   *
   * @return Vector2D representing the maximum coordinates.
   * @since 0.0.1
   */
  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  /**
   * Retrieves the coordinates of the lower left corner of the drawing area.
   *
   * @return Vector2D representing the minimum coordinates.
   * @since 0.0.1
   */
  public Vector2D getMinCoords() {
    return minCoords;
  }

  public List<Transform2D> getTransformations() {
    return transformations;
  }


 /*
 // Change: use iterator instead of returning the entire list
  public Iterator<Transform2D> getTransformations() {
    return transformations.iterator();
  }*/

}
