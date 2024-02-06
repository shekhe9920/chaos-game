package edu.ntnu.stud.idatg2003.transformation;

import edu.ntnu.stud.idatg2003.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;

/**
 * The {@code JuliaTransform} class represents a complex transformation of the form
 * z -> ±sqrt(z - c), where z is a complex number, and c is a complex constant.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
public class JuliaTransform implements Transform2D {

  private Complex point; // c
  private int sign;      // ±

  /**
   * Construct a new {@code JuliaTransform} object with the given point and sign.
   *
   * @param point The point.
   * @param sign The sign.
   * @since 0.0.0
   */
  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    this.sign = sign;
  }

  /**
   * This method is an implementation of the {@code transform} method from
   * the {@code Transform2D} interface. It takes a complex number as input and returns a new
   * complex number as output, according to the transformation z -> ±sqrt(z - c).
   *
   * @param z The complex number to transform.
   * @return The transformed complex number.
   */
  @Override
  public Vector2D transform(Vector2D z) {

    Complex complexZ = new Complex(z.getX0(), z.getX1()); // a new complex number from the vector
    Complex zMinusC = (Complex) complexZ.subtract(point); // z - c

    // Calculating the magnitude for the real and imaginary parts separately
    double magnitude = // sqrt( (zMinusC.x0)^2 + (zMinusC.x1)^2
        Math.sqrt(zMinusC.getX0() * zMinusC.getX0() + zMinusC.getX1() * zMinusC.getX1());

    // Calculating numerator using the sqrt method from the Complex class (explicit casting)
    Complex numerator = (Complex) zMinusC.sqrt().multiply(new Complex(sign, 0)).add(point);

    // Calculate denominator using magnitude
    Complex denominator = new Complex(Math.sqrt(magnitude), 0);

    // If the denominator is zero, return a new vector with x0 and x1 set to 0
    if(denominator.getX0() == 0) {
      return new Vector2D(0, 0);
    }

    // Return the result of the division
    return new
        Vector2D(numerator.getX0() / denominator.getX0(), numerator.getX1() / denominator.getX0());

  }

}
