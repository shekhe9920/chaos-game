package edu.ntnu.stud.idatg2003.backend.mathoperations;

/**
 * The {@code Complex} class represents a complex number, with a real and imaginary part. It extends
 * the {@code Vector2D} class, and inherits mathematical operations from it.
 * The {@code real} and {@code imaginary} parts represent the components of the complex number.
 *
 * <p>
 * A complex number is represented as z = a + bi,
 * where a is the real part and b is the imaginary part.
 * </p>
 *
 * @version 0.0.3
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

    // Return the square root as a new complex number:
    return new Complex(
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
   * </p>
   *
   * @param otherVector The vector to subtract from this complex number.
   * @throws IllegalArgumentException If the other vector is null.
   * @return A new complex number representing the result of the subtraction.
   * @since 0.0.2
   */
  @Override
  public Complex subtract(Vector2D otherVector) {
    if (otherVector == null) {
      throw new IllegalArgumentException("Other vector cannot be null");
    }
    return new Complex(this.x0 - otherVector.x0, this.x1 - otherVector.x1);
  }






  /**
   * Raises this complex number to the power of the given exponent.
   *
   * <p>
   * The power of a complex number z = a + bi to the exponent n is calculated as follows:
   * Let r be the magnitude (r = sqrt(a^2 + b^2)) and theta be the angle (theta = atan2(b, a)).
   * The power is then given by:
   * <br>
   * z^n = r^n * (cos(n * theta) + i * sin(n * theta))
   * </p>
   *
   * @param exponent The exponent to raise this complex number to.
   * @return The result of the power as a new {@code Complex} number.
   * @since 0.0.3
   */
  public Complex pow(double exponent) {
    double real = getX0();
    double imag = getX1();
    double magnitude = Math.pow(Math.sqrt(real * real + imag * imag), exponent); // r^n
    double angle = Math.atan2(imag, real) * exponent;  // n * theta

    // Return the power as a new complex number
    return new Complex(
        magnitude * Math.cos(angle),  // r^n * cos(n * theta)
        magnitude * Math.sin(angle)   // r^n * sin(n * theta)
    );
  }





  /**
   * Returns the size (or absolute value) of this complex number.
   *
   * <p>
   * The magnitude of a complex number z = a + bi is given by:
   * <br>
   * |z| = sqrt(a^2 + b^2)
   * </p>
   *
   * @return The size of this complex number.
   * @since 0.0.3
   */
  public double norm() {
    return Math.sqrt(this.getX0() * this.getX0() + this.getX1() * this.getX1());
  }





  /**
   * Calculates the size of this complex number.
   *
   * <p>
   * The magnitude of a complex number z = a + bi is given by:
   * <br>
   * |z| = sqrt(a^2 + b^2)
   * </p>
   *
   * @return The size of this complex number.
   * @since 0.0.3
   */
  public double magnitude() {
    return norm();
  }





  /**
   * Calculates the angle (or argument) of this complex number.
   *
   * <p>
   * The angle of a complex number z = a + bi is given by:
   * <br>
   * arg(z) = atan2(b, a)
   * </p>
   *
   * @return The angle of this complex number in radians.
   * @since 0.0.3
   */
  public double angle() {
    return Math.atan2(getX1(), getX0());
  }
}
