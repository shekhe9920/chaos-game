package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.util.ArrayList;
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
 * @version 0.0.4
 * @since 0.0.1 (The version of Chaos-Game application when introduced)
 */
public class ChaosGameDescription {

  private Vector2D minCoords;  // Coordinates of the lower left corner of the drawing area.
  private Vector2D maxCoords;  // Coordinates of the upper right corner of the drawing area.
  private final List<Transform2D> transformations; // List of transformations (Affine or Julia).





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
  public ChaosGameDescription(
      Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transformations) {

    this.transformations = new ArrayList<>(transformations); // Initializing a transformation list
    setMinCoords(minCoords); // Setting minimum coordinates
    setMaxCoords(maxCoords); // Setting maximum coordinates

  }




  /**
   * Sets the list of transformations for generating the fractal.
   *
   * @param transformations A list of transformation rules to apply in the chaos game.
   * @since 0.0.4
   */
  public void setTransformations(List<Transform2D> transformations) {
    if (transformations != null) {
      this.transformations.clear();  // Clearing the existing transformations
      this.transformations.addAll(transformations);  // Adding the new transformations
    } else {
      throw new IllegalArgumentException("Transformations cannot be null");
    }
  }



  /**
   * Sets the coordinates marking the lower left boundary of the fractal's drawing area.
   *
   * @param minCoords A Vector2D instance representing the lower left corner coordinates.
   * @since 0.0.4
   */
  public void setMinCoords(Vector2D minCoords) {
    if (minCoords != null) {
      this.minCoords = minCoords;
    } else {
      throw new IllegalArgumentException("MinCoords cannot be null");
    }
  }



  /**
   * Sets the coordinates marking the upper right boundary of the fractal's drawing area.
   *
   * @param maxCoords A Vector2D instance representing the upper right corner coordinates.
   * @since 0.0.4
   */
  public void setMaxCoords(Vector2D maxCoords) {
    if (maxCoords != null) {
      this.maxCoords = maxCoords;
    } else {
      throw new IllegalArgumentException("MaxCoords cannot be null");
    }
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


}
