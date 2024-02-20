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

  private Complex point; // Represents the complex constant 'c'
  private int sign;      // Represents the sign of the transformation, which must be either 1 or -1

  /**
   * Constructs a new {@code JuliaTransform} object with the given complex constant 'c' and sign.
   *
   * @param point The complex constant 'c'.
   * @param sign The sign of the transformation, which must be either 1 or -1.
   * @throws IllegalArgumentException If the sign is not 1 or -1.
   * @since 0.0.0
   */
  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    setSign(sign);
  }


  /**
   * Sets the sign of the transformation vector.
   *
   * @param sign sign The sign to set, which must be either 1 or -1.
   * @throws IllegalArgumentException If the sign is not 1 or -1.
   * @since 0.0.0
   */
  private void setSign(int sign) {
    if (sign == 1 || sign == -1) {
      this.sign = sign;
    } else {
      throw new IllegalArgumentException("Sign must be 1 or -1");
    }
  }

  /**
   * Gets the sign of the Julia transformation vector.
   *
   * @return The sign.
   * @since 0.0.0
   */
  public int getSign() {
    return sign;
  }




  /**
   * Transforms a given complex number {@code z} according to the transformation z -> ±sqrt(z - c).
   * This method calculates the square root of the difference between the input complex number and
   * the complex constant 'c'. The sign of the square root is determined by the {@code sign} field
   * of this {@code JuliaTransform} object.
   *
   * @param z The complex number to transform.
   * @return The transformed complex number.
   * @since 0.0.0
   */
  @Override
  public Vector2D transform(Vector2D z) {

    Complex complexZ = new Complex(z.getX0(), z.getX1()); // TODO: Maybe use toComplex method from Complex class
    Complex zMinusC = (Complex) complexZ.subtract(point); // Calculate z - c

    // Calculating the magnitude for the real and imaginary parts separately
    double magnitude = Math.sqrt(zMinusC.getX0() * zMinusC.getX0() + zMinusC.getX1() * zMinusC.getX1());

    double newReal;
    double newImaginary;

    // Handling the case when zMinusC.x0 < 0 and zMinusC.x1 = 0
    if (zMinusC.getX0() < 0 && zMinusC.getX1() == 0) {
      newReal = 0;
      newImaginary = Math.sqrt(Math.abs(zMinusC.getX0()));
    } else {
      newReal = Math.sqrt(0.5 * (magnitude + zMinusC.getX0()));
      newImaginary = Math.signum(zMinusC.getX1()) * Math.sqrt(0.5 * (magnitude - zMinusC.getX0()));
    }

    // Checking if both newReal and newImaginary are zero
    if (newReal == 0 && newImaginary == 0) {
      return new Vector2D(0, 0);
    } else if (newReal == 0) { // Check if newReal is zero, to avoid "-0.0" in the output
      return new Vector2D(0, sign * newImaginary);
    }

    // Return: The transformed vector
    return new Vector2D(sign * newReal, sign * newImaginary);
  }

}
