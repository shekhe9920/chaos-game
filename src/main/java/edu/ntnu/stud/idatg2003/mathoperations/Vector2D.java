package edu.ntnu.stud.idatg2003.mathoperations;

/**
 * {@code Vector2D} is a class representing a 2-dimensional vector.
 * Where x0 and x1 are the coordinates or components of the vector,
 * representing the x and components of a vector.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
public class Vector2D {


  protected double x0;
  protected double x1;



  /**
   * Construct a new {@code Vector2D} object with the given coordinates, x0 and x1.
   *
   * @param x0 The x0 coordinate.
   * @param x1 The x1 coordinate.
   * @since 0.0.0
   */
  public Vector2D(double x0, double x1) {
    this.x0 = x0;
    this.x1 = x1;
  }




  /**
   * Gets the x0 coordinate of the vector.
   *
   * @return The x0 coordinate.
   */
  public double getX0() {
    return x0;
  }




  /**
   * Gets the x1 coordinate of the vector.
   *
   * @return The x1 coordinate
   * @since 0.0.0
   */
  public double getX1() {
    return x1;
  }




  /**
   * Adds the given vector to this vector and returns the result.
   *
   * @param otherVector The vector to add to this vector
   * @return The result of the addition
   * @since 0.0.0
   */
  public Vector2D add(Vector2D otherVector) {
    return new Vector2D(this.x0 + otherVector.x0, this.x1 + otherVector.x1);
  }




  /**
   * Subtracts the given vector from this vector and returns the result.
   *
   * @param otherVector The vector to subtract from this vector
   * @return The result of the subtraction
   * @since 0.0.0
   */
  public Vector2D subtract(Vector2D otherVector) {
    return new Vector2D(this.x0 - otherVector.x0, this.x1 - otherVector.x1);
  }

}
