package edu.ntnu.stud.idatg2003.backend.transformations;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;

/**
 * The {@code JuliaTransform} class represents a complex transformation of the form
 * z -> ±sqrt(z - c), where z is a complex number, and c is a complex constant.
 *
 * @version 0.0.2
 * @since 0.0.0 (The version of ChaosGameEngine application when introduced)
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
    setPoint(point);
    setSign(sign);
  }




  /**
   * Sets the point of the transformation vector.
   *
   * @param point The point to set, which must be a Complex object and not null.
   * @throws IllegalArgumentException If the point is null, or not a Complex object.
   * @since 0.0.2
   */
  private void setPoint(Complex point) {
    if (point != null) {
      this.point = point;
    } else {
      throw new IllegalArgumentException("Point must be a Complex object and not null");
    }
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
    Complex complexZ = new Complex(z.getX0(), z.getX1());
    Complex zMinusC = (Complex) complexZ.subtract(point); // Calculating z - c

    Complex newComplex = zMinusC.sqrt();  // Calculating the square root of z - c

    double newReal = newComplex.getX0();  // the real part of the square root
    double newImaginary = newComplex.getX1();  // the imaginary part of the square root

    // Checking if both newReal and newImaginary are zero:
    if (newReal == 0 && newImaginary == 0) {
      return new Vector2D(0, 0);
    } else if (newReal == 0) { // Checking if newReal is zero to avoid "-0.0" output when debugging
      return new Vector2D(0, sign * newImaginary);
    }

    // Return: The transformed vector
    return new Vector2D(sign * newReal, sign * newImaginary);
  }

}
