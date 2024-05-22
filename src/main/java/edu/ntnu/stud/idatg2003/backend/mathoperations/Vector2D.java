package edu.ntnu.stud.idatg2003.backend.mathoperations;

import java.util.Objects;

/**
 * {@code Vector2D} is a class representing a 2-dimensional vector.
 * The {@code x0} and {@code x1} are the coordinates or components of the vector,
 * representing the x and y components of a vector respectively.
 *
 * @version 0.0.3
 * @since 0.0.0 (The version of Chaos-Game application when introduced)
 */
public class Vector2D {


  protected double x0;  // The x coordinate of the vector.
  protected double x1;  // The y coordinate of the vector.





  /**
   * Constructs a new {@code Vector2D} object with the given coordinates, {@code x0} and {@code x1}.
   *
   * @param x0 The x-coordinate.
   * @param x1 The y-coordinate.
   * @since 0.0.0
   */
  public Vector2D(double x0, double x1) {
    this.x0 = x0;
    this.x1 = x1;
    validateCoordinates();
  }



  /**
   * Validates that an element is a valid number.
   *
   * @throws IllegalArgumentException if the element is NaN or infinite.
   * @since 0.0.2
   */
  protected void validateCoordinates() {
    if (Double.isNaN(x0) || Double.isInfinite(x0) || Double.isNaN(x1) || Double.isInfinite(x1)) {
      throw new IllegalArgumentException("The Vector coordinates must be a valid number");
    }
  }





  /**
   * Adds the given vector to this vector and returns the result.
   * If the other vector is a complex number, the result will be a complex number.
   * Otherwise, the result will be a vector object.
   *
   * <p>
   * The addition of two vectors (x0, y0) and (x1, y1) is given by:
   * <pre>
   * (x0 + x1, y0 + y1)
   * </pre>
   * </p>
   *
   * @param otherVector The vector to add to this vector.
   * @return A new vector representing the result of the addition.
   * @throws IllegalArgumentException if the provided vector is null.
   * @since 0.0.0
   */
  public Vector2D add(Vector2D otherVector) {
    if (otherVector != null) {
      if (otherVector instanceof Complex) {
        return new Complex(this.x0 + otherVector.x0, this.x1 + otherVector.x1);

      } else {
        return new Vector2D(this.x0 + otherVector.x0, this.x1 + otherVector.x1);

      }
    } else {
      throw new IllegalArgumentException("Other vector cannot be null");

    }

  }




  /**
   * Subtracts the given vector from this vector and returns the result.
   *
   * <p>
   * The subtraction of two vectors (x0, y0) and (x1, y1) is given by:
   * <pre>
   * (x0 - x1, y0 - y1)
   * </pre>
   * </p>
   *
   * @param otherVector The vector to subtract from this vector.
   * @return A new vector representing the result of the subtraction.
   * @throws IllegalArgumentException if the provided vector is null.
   * @since 0.0.0
   */
  public Vector2D subtract(Vector2D otherVector) {
    return new Vector2D(this.x0 - otherVector.x0, this.x1 - otherVector.x1);
  }



  // Getters for vector elements:

  /**
   * Gets the {@code x0} coordinate of the vector.
   *
   * @return The x0 coordinate.
   * @since 0.0.0
   */
  public double getX0() {
    return x0;
  }




  /**
   * Gets the {@code x1} coordinate of the vector.
   *
   * @return The x1 coordinate
   * @since 0.0.0
   */
  public double getX1() {
    return x1;
  }



  /**
   * Returns a string representation of the vector.
   *
   * @return A string representation of the vector.
   * @since 0.0.3
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vector2D vector2D = (Vector2D) o;
    return Double.compare(vector2D.x0, x0) == 0 &&
        Double.compare(vector2D.x1, x1) == 0;
  }


  /**
   * Returns a hash code value for the vector.
   *
   * @return A hash code value for this vector.
   * @since 0.0.3
   */
  @Override
  public int hashCode() {
    return Objects.hash(x0, x1);
  }


  /**
   * Returns a string representation of the vector.
   *
   * @return A string representation of the vector.
   * @since 0.0.3
   */
  @Override
  public String toString() {
    return "Vector2D{" +
        "x0=" + x0 +
        ", x1=" + x1 +
        '}';
  }

}
