package edu.ntnu.stud.idatg2003.backend.mathoperations;

/**
 * The {@code Complex} class represents a complex number, with a real and imaginary part. It extends
 * the {@code Vector2D} class, and inherits mathematical operations from it.
 * The {@code real} and {@code imaginary} parts represent the components of the complex number.
 *
 * <p>
 * A complex number is represented as z = a + bi, where a is the real part and b is the imaginary part.
 * </p>
 *
 * @version 0.0.2
 * @since 0.0.0 (The version of Chaos-Game application when introduced)
 */
public class Complex extends Vector2D {



  /**
   * Constructs a new {@code Complex} number with the specified real and imaginary parts.
   *
   * @param realPart The real part of the complex number.
   * @param imaginaryPart The imaginary part of the complex number.
   * @since 0.0.0
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);  // Real and imaginary fields are validated in the superclass
  }






  /**
   * Multiplies this complex number with the given complex number.
   *
   * <p>
   * The multiplication of two complex numbers (a + bi) and (c + di) is given by:
   * <br>
   * (a + bi) * (c + di) = (ac - bd) + (ad + bc)i
   * </br>
   * </p>
   *
   * @param other The complex number to multiply with.
   * @return The result of the multiplication.
   * @throws IllegalArgumentException If the other complex number is null.
   * @since 0.0.0
   */
  public Complex multiply(Complex other) {
    if (other != null) {
      double real = this.getX0() * other.getX0() - this.getX1() * other.getX1(); // ac - bd
      double imaginary = this.getX0() * other.getX1() + this.getX1() * other.getX0(); // ad + bc

      return new Complex(real, imaginary); // Return the result as a new complex number
    } else {
      throw new IllegalArgumentException("Other complex number cannot be null");
    }
  }





  /**
   * Calculates the square root of this complex number.
   *
   * <p>
   * The square root of a complex number z = a + bi is calculated as follows:
   * Let r be the magnitude (r = sqrt(a^2 + b^2)) and theta be the angle (theta = atan2(b, a)).
   * The square root is then given by:
   * <br>
   * sqrt(z) = sqrt(r) * (cos(theta / 2) + i * sin(theta / 2))
   * </br>
   * </p>
   *
   * @return The result of the square root as a new {@code Complex} number.
   * @since 0.0.0
   */
  public Complex sqrt() {
    double real = getX0();
    double imag = getX1();
    double magnitude = Math.sqrt(real * real + imag * imag); // r = sqrt(a^2 + b^2)
    double angle = Math.atan2(imag, real) / 2.0;  // theta / 2

    return new Complex( // Return the square root as a new complex number
        Math.sqrt(magnitude) * Math.cos(angle),  // sqrt(r) * cos(theta / 2)
        Math.sqrt(magnitude) * Math.sin(angle)   // sqrt(r) * sin(theta / 2)
    );
  }





  /**
   * Subtracts the given vector from this complex number.
   * If the other vector is also a complex number,
   * the subtraction is performed on the real and imaginary parts separately.
   *
   * <p>
   * The subtraction of two complex numbers (a + bi) and (c + di) is given by:
   * <br>
   * (a + bi) - (c + di) = (a - c) + (b - d)i
   * </br>
   * </p>
   *
   * @param otherVector The vector to subtract from this complex number.
   * @return A new complex number representing the result of the subtraction.
   * @since 0.0.2
   */
  @Override
  public Vector2D subtract(Vector2D otherVector) {
    if (otherVector instanceof Complex) {
      return new Complex(
          this.x0 - otherVector.x0, this.x1 - otherVector.x1);
    }
    return super.subtract(otherVector);
  }


}
