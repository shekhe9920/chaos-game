package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.util.List;



/**
 * Represents the configuration for a chaos game,
 * encapsulating the information required to generate a fractal.
 * This includes a set of transformations (which can be affine transformations or Julia sets) and
 * the designated drawing area within a plane.
 * The drawing area is defined by specifying the coordinates of its lower left
 * and upper right corners.
 * This setup allows for the dynamic generation of various fractals, including but not
 * limited to the Sierpinski triangle,
 * by applying the specified transformations to points within the area.
 *
 * @version 0.0.3
 * @since 0.0.1 (The version of ChaosGameEngine application when introduced)
 */
public class ChaosGameDescription {

  private final Vector2D minCoords;    // Coordinates of the lower left corner of the drawing area.
  private final Vector2D maxCoords;    // Coordinates of the upper right corner of the drawing area.
  private final List<Transform2D> transformations; // List of transformations (affine or Julia).





  /**
   * Constructs a new ChaosGameDescription with specified transformation rules and
   * the bounds for the fractal's drawing area.
   * This constructor initializes the chaos game description with detailed parameters
   * defining how the fractal is to be generated and where it is to be plotted.
   *
   * @param transformations A list of transformation rules to apply in the chaos game.
   * @param minCoords The coordinates marking the lower left corner of the plotting area,
   *                  defining the minimum boundary.
   * @param maxCoords The coordinates marking the upper right corner of the plotting area,
   *                  defining the maximum boundary.
   * @since 0.0.1
   */
  public ChaosGameDescription(Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transformations) {
    this.transformations = transformations;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
  }




  /**
   * Retrieves the coordinates marking the upper right boundary of the fractal's drawing area.
   * These coordinates define the maximum extent of the area where the fractal can be plotted.
   *
   * @return A Vector2D instance representing the upper right corner coordinates.
   * @since 0.0.1
   */
  public Vector2D getMaxCoords() {
    return maxCoords;
  }




  /**
   * Retrieves the coordinates marking the lower left boundary of the fractal's drawing area.
   *
   * @return A Vector2D instance representing the lower left corner coordinates.
   * @since 0.0.1
   */
  public Vector2D getMinCoords() {
    return minCoords;
  }




  /**
   * Provides access to the list of transformations specified for generating the fractal.
   *
   * @return A list of Transform2D instances.
   * @since 0.0.1
   */
  public List<Transform2D> getTransformations() {
    return transformations;
  }




/*
 // Change: use iterator instead of returning the entire list
  public Iterator<Transform2D> getTransformations() {
    return transformations.iterator();
  }*/

}
