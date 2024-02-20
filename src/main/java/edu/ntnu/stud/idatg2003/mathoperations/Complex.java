package edu.ntnu.stud.idatg2003.mathoperations;

/**
 * The {@code Complex} class represents a complex number, with a real and imaginary part. It extends
 * the {@code Vector2D} class, and inherits mathematical operations from it.
 * {@code real} and {@code imaginary} are the real and imaginary parts of the complex number.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
public class Complex extends Vector2D {


  /**
   * Constructs a new {@code Complex} number with the specified real and imaginary parts.
   *
   * @param real The real part of the complex number.
   * @param imaginary The imaginary part of the complex number.
   * @since 0.0.0
   */
  public Complex(double real, double imaginary){
    super(real, imaginary);
  }



  /**
   * Creates a new complex number from the given vector.
   *
   * @param vector The vector to convert to a complex number.
   * @return The complex number.
   * @throws IllegalArgumentException If the vector is null or contains invalid components.
   * @since 0.0.0
   */
  public Complex toComplex(Vector2D vector) {
    if (vector != null) {

      // Check for the validity of vector components
      if (Double.isNaN(vector.getX0()) || Double.isNaN(vector.getX1())) {
        throw new IllegalArgumentException("Vector components must be valid numbers");
      }
      return new Complex(vector.getX0(), vector.getX1());

    } else {
      throw new IllegalArgumentException("Vector cannot be null");
    }
  }




  /**
   * Multiplies this complex number with the given complex number.
   *
   * @param other The complex number to multiply with.
   * @return The result of the multiplication.
   * @throws IllegalArgumentException If the other complex number is null.
   * @since 0.0.0
   */
  public Complex multiply(Vector2D other) {
    if (other != null) {
      double resultX0 = this.x0 * other.x0 - this.x1 * other.x1;
      double resultX1 = this.x0 * other.x1 + this.x1 * other.x0;

      return new Complex(resultX0, resultX1);
    } else {
      throw new IllegalArgumentException("Other complex number cannot be null");
    }
  }




  /**
   * Calculates the square root of a complex number.
   *
   * @return The result of the square root.
   * @since 0.0.0
   */
  public Complex sqrt() {
    double magnitude = Math.sqrt(x0 * x0 + x1 * x1);

    if (x0 < 0 && x1 == 0) {
      double newReal = 0.0;
      double newImaginary = Math.sqrt(Math.abs(x0));

      return new Complex(newReal, newImaginary);
    } else {
      double newReal = Math.sqrt(0.5 * (magnitude + x0));
      double newImaginary = Math.signum(x1) * Math.sqrt(0.5 * (magnitude - x0));

      return new Complex(newReal, newImaginary);
    }
  }
}
