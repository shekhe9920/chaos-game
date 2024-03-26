package edu.ntnu.stud.idatg2003.backend.mathoperations;


/**
 * The {@code Complex} class represents a complex number, with a real and imaginary part. It extends
 * the {@code Vector2D} class, and inherits mathematical operations from it.
 * {@code real} and {@code imaginary} are the real and imaginary parts of the complex number.
 *
 * @version 0.0.2
 * @since 0.0.0 (The version of ChaosGameEngine application when introduced)
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
   * @param other The complex number to multiply with.
   * @return The result of the multiplication.
   * @throws IllegalArgumentException If the other complex number is null.
   * @since 0.0.0
   */
  public Complex multiply(Complex other) {
    if (other != null) {
      double real = this.getX0() * other.getX0() - this.getX1() * other.getX1();
      double imaginary = this.getX0() * other.getX1() + this.getX1() * other.getX0();

      return new Complex(real, imaginary);
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
    double real = getX0();
    double imag = getX1();
    double magnitude = Math.sqrt(real * real + imag * imag);
    double angle = Math.atan2(imag, real) / 2.0;

    return new Complex(
        Math.sqrt(magnitude) * Math.cos(angle),
        Math.sqrt(magnitude) * Math.sin(angle)
    );
  }
  /*
  public Complex sqrt() {
    double magnitude = Math.sqrt(x0 * x0 + x1 * x1);

    double newReal;
    double newImaginary;

    if (x0 < 0 && x1 == 0) {
      newReal = 0.0;
      newImaginary = Math.sqrt(Math.abs(x0));

    } else {
      newReal = Math.sqrt(0.5 * (magnitude + x0));
      newImaginary = Math.signum(x1) * Math.sqrt(0.5 * (magnitude - x0));

    }
    return new Complex(newReal, newImaginary);
  }*/



  /**
   * Subtracts the given vector from this complex number.
   * If the other vector is also a complex number,
   * the subtraction is performed on the real and imaginary parts separately.
   * If the other vector is not a complex number, the subtraction is performed as in the superclass.
   *
   * @param otherVector The vector to subtract from this complex number.
   * @return A new complex number representing the result of the subtraction.
   * @since 0.0.2
   */
  @Override
  public Vector2D subtract(Vector2D otherVector) {
    if (otherVector instanceof Complex) {
      return new Complex(this.x0 - otherVector.x0, this.x1 - otherVector.x1);
    }
    return super.subtract(otherVector);
  }

}
