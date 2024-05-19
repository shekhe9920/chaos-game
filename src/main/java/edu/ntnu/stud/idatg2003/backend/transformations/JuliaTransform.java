package edu.ntnu.stud.idatg2003.backend.transformations;

import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToDouble;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;

/**
 * The {@code JuliaTransform} class represents a complex transformation of the form
 * z -> ±(z - c)^(1/n), where z is a complex number, and c is a complex constant.
 * This transformation is used in generating Julia sets, a type of fractal.
 *
 * @version 0.0.3
 * @since 0.0.0 (The version of Chaos-Game application when introduced)
 */
public class JuliaTransform implements Transform2D {


  private Complex point; // Represents the complex constant 'c'
  private int sign;      // Represents the sign of the transformation, which must be either 1 or -1
  private int order;     // Represents the order of the transformation
  private boolean isMultiOrder; // Indicates if the transformation is a multi-order Julia set



  /**
   * Constructs a new {@code JuliaTransform} object with the given complex constant 'c' and sign.
   *
   * @param point The complex constant 'c'.
   * @param sign The sign of the transformation, which must be either 1 or -1.
   * @param order The order of the transformation (e.g., 2 for sqrt, 3 for cube root).
   * @param isMultiOrder Indicates if the transformation is a multi-order Julia set.
   * @throws IllegalArgumentException If the sign is not 1 or -1
   * @since 0.0.3
   */
  public JuliaTransform(Complex point, int sign, int order, boolean isMultiOrder) {
    setPoint(point);
    setSign(sign);
    setOrder(order);
    this.isMultiOrder = isMultiOrder;
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
   * @param sign The sign to set, which must be either 1 or -1.
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
   * Sets the order of the transformation vector.
   *
   * @param order The order to set, which must be a positive integer.
   * @throws IllegalArgumentException If the order is not a positive integer.
   * @since 0.0.3
   */
  private void setOrder(int order) {
    if (order > 0) {
      this.order = order;
    } else {
      throw new IllegalArgumentException("Order must be a positive integer");
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
   * Gets the point of the Julia transformation vector.
   *
   * @return The point.
   * @since 0.0.2
   */
  public Complex getPoint() {
    return point;
  }




  /**
   * Gets the order of the Julia transformation vector.
   *
   * @return The order.
   * @since 0.0.3
   */
  public int getOrder() {
    return order;
  }




  /**
   * Checks if the Julia transformation is a multi-order transformation.
   *
   * @return {@code true} if it is a multi-order transformation, {@code false} otherwise.
   * @since 0.0.3
   */
  public boolean isMultiOrder() {
    return isMultiOrder;
  }



  /**
   * Transforms a given complex number {@code z} according to the transformation z -> ±(z - c)^(1/n).
   *
   * <p>
   * This method calculates the nth root of the difference between the input complex number and
   * the complex constant 'c'. The sign of the root is determined by the {@code sign} field
   * of this {@code JuliaTransform} object.
   *
   * The transformation is given by:
   * <pre>
   * z' = ±(z - c)^(1/n)
   * </pre>
   * where:
   * <ul>
   * <li>z is the input complex number</li>
   * <li>c is the complex constant</li>
   * <li>± indicates that the sign can be either positive or negative</li>
   * </ul>
   * </p>
   *
   * @param z The complex number to transform.
   * @return The transformed complex number.
   * @since 0.0.0
   */
  @Override
  public Vector2D transform(Vector2D z) {
    Complex complexZ = new Complex(z.getX0(), z.getX1());
    Complex zMinusC = complexZ.subtract(point); // Calculating z - c

    Complex newComplex;

    if (isMultiOrder) {
      double magnitude = Math.pow(zMinusC.magnitude(), 1.0 / order);
      double angle = zMinusC.angle() / order;
      newComplex = new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    } else {
      newComplex = zMinusC.pow(1.0 / order); // Calculating the nth root of z - c
    }

    double newReal = newComplex.getX0();  // The real part of the root
    double newImaginary = newComplex.getX1();  // The imaginary part of the root

    // Checking if both newReal and newImaginary are zero:
    if (newReal == 0 && newImaginary == 0) {
      return new Vector2D(0, 0);
    } else if (newReal == 0) { // Checking if newReal is zero to avoid "-0.0" output when debugging
      return new Vector2D(0, sign * newImaginary);
    }

    // Return: The transformed vector
    return new Vector2D(sign * newReal, sign * newImaginary);
  }




  /**
   * Calculates the number of iterations for a given point in the Julia set.
   *
   * <p>
   * This method calculates how many iterations a complex number goes through before escaping
   * a predefined radius (escape radius). The iteration process involves applying the transformation
   * defined by the Julia set formula repeatedly until the magnitude of the resulting complex number
   * exceeds the escape radius or the maximum number of iterations is reached.
   * </p>
   *
   * <p>
   * The transformation applied is:
   * <pre>
   * Z(n+1) = (Z(n) - c)^(1/order) + c
   * </pre>
   * where:
   * <ul>
   * <li>Z is the complex number (zx, zy)</li>
   * <li>c is the constant complex number (cRe, cIm)</li>
   * <li>order is the order of the transformation</li>
   * </ul>
   * </p>
   *
   * @param zx The real part of the complex number.
   * @param zy The imaginary part of the complex number.
   * @param order The order of the transformation.
   * @param cRe The real part of the constant complex number.
   * @param cIm The imaginary part of the constant complex number.
   * @return The number of iterations before the point escapes the Julia set boundary.
   * @since 0.0.4
   */
  public int calculateJuliaSetPoint(double zx, double zy, int order, double cRe, double cIm) {
    Complex c = new Complex(cRe, cIm);  // The constant complex number
    int maxIteration = 1000;        // The maximum number of iterations
    Complex z = new Complex(zx, zy);    // The complex number to iterate
    int iteration = 0;            // starting iteration
    double escapeRadius = 4.0;    // The escape radius

    while (iteration < maxIteration && z.norm() < escapeRadius) {
      z = iterate(z, c, order);  // Applying the transformation
      iteration++;          // Incrementing the iteration
    }

    return iteration;
  }





  /**
   * Calculates the number of iterations for a given point in the Mandelbrot set.
   *
   * <p>
   * This method calculates how many iterations a complex number goes through before escaping
   * a predefined radius (escape radius). The iteration process involves applying the transformation
   * defined by the Mandelbrot set formula repeatedly until the magnitude of the resulting complex number
   * exceeds the escape radius or the maximum number of iterations is reached.
   * </p>
   *
   * <p>
   * The transformation applied is:
   * <pre>
   * Z(n+1) = Z(n)^order + c
   * </pre>
   * where:
   * <ul>
   * <li>Z is the complex number (initially (0, 0))</li>
   * <li>c is the constant complex number (cx, cy)</li>
   * <li>order is the order of the transformation</li>
   * </ul>
   * </p>
   *
   * @param cx The real part of the constant complex number.
   * @param cy The imaginary part of the constant complex number.
   * @param order The order of the transformation.
   * @return The number of iterations before the point escapes the Mandelbrot set boundary.
   * @since 0.0.3
   */
  public int calculateMandelbrotSetPoint(double cx, double cy, int order) {
    Complex c = new Complex(cx, cy);  // The constant complex number
    int maxIteration = 1000;    // The maximum number of iterations
    Complex z = new Complex(0, 0);  // The complex number to iterate
    int iteration = 0;      // starting iteration
    double escapeRadius = 4.0;  // The escape radius

    while (iteration < maxIteration && z.norm() < escapeRadius) {
      z = iterate(z, c, order);  // Applying the transformation
      iteration++;     // Incrementing the iteration
    }

    return iteration;
  }






  /**
   * Performs one iteration of the transformation for both Julia and Mandelbrot sets.
   *
   * <p>
   * This method calculates the next value of the complex number z in the iteration process
   * by applying the transformation formula based on the specified order.
   * </p>
   *
   * <p>
   * The transformation for order 2 is:
   * <pre>
   * Z(n+1) = Z(n)^2 + c
   * </pre>
   * The general transformation for higher orders is:
   * <pre>
   * Z(n+1) = Z(n)^order + c
   * </pre>
   * where:
   * <ul>
   * <li>Z is the complex number being iterated</li>
   * <li>c is the constant complex number</li>
   * <li>order is the order of the transformation</li>
   * </ul>
   * </p>
   *
   * @param z The current complex number in the iteration process.
   * @param c The constant complex number.
   * @param order The order of the transformation.
   * @return The next complex number after applying the transformation.
   * @since 0.0.3
   */
  private Complex iterate(Complex z, Complex c, int order) {
    Complex result;  // The result of the transformation

    if (order == 2) {  // Checking if the order is 2
      result = (Complex) z.multiply(z).add(c); // result = z^2 + c
      return result;

    } else { // For orders greater than 2
      // Calculating the magnitude of z to the power of order:
      double magnitude = Math.pow(z.magnitude(), order);
      double angle = z.angle() * order; // angle of z to the power of order
      result =
          (Complex) new Complex( // result = z^order
              magnitude * Math.cos(angle), magnitude * Math.sin(angle)).add(c);
      return result;
    }
  }



}
