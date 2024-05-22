package edu.ntnu.stud.idatg2003.backend.mathoperations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Complex}.
 * This class contains tests for verifying the functionality of the Complex class,
 * including both positive and negative tests.
 */
class ComplexTest {

  /**
   * Tests the constructor of Complex class.
   */
  @Test
  void testConstructor() {
    Complex complex = new Complex(1.0, 2.0);
    assertEquals(1.0, complex.getX0(), "Real part should be 1.0");
    assertEquals(2.0, complex.getX1(), "Imaginary part should be 2.0");
  }


  /**
   * Tests the multiplication of two complex numbers.
   */
  @Test
  void testMultiply_Valid() {
    Complex c1 = new Complex(1.0, 2.0);
    Complex c2 = new Complex(3.0, 4.0);
    Complex result = c1.multiply(c2);

    assertEquals(-5.0, result.getX0(), "Real part of the product should be -5.0");
    assertEquals(10.0, result.getX1(), "Imaginary part of the product should be 10.0");
  }

  /**
   * Tests multiplication with null, expecting an IllegalArgumentException.
   */
  @Test
  void testMultiply_Null() {
    Complex c1 = new Complex(1.0, 2.0);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> c1.multiply(null));
    assertEquals("Other complex number cannot be null", exception.getMessage());
  }

  /**
   * Tests the square root of a complex number.
   */
  @Test
  void testSqrt() {
    Complex complex = new Complex(3.0, 4.0);
    Complex result = complex.sqrt();

    assertEquals(2.0, result.getX0(), 1e-10, "Real part of the square root should be 2.0");
    assertEquals(1.0, result.getX1(), 1e-10, "Imaginary part of the square root should be 1.0");
  }

  /**
   * Tests the subtraction of two complex numbers.
   */
  @Test
  void testSubtract_Valid() {
    Complex c1 = new Complex(5.0, 6.0);
    Vector2D c2 = new Complex(3.0, 4.0);
    Complex result = c1.subtract(c2);

    assertEquals(2.0, result.getX0(), "Real part of the subtraction result should be 2.0");
    assertEquals(2.0, result.getX1(), "Imaginary part of the subtraction result should be 2.0");
  }

  /**
   * Tests subtraction with null, expecting an IllegalArgumentException.
   */
  @Test
  void testSubtract_Null() {
    Complex c1 = new Complex(5.0, 6.0);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> c1.subtract(null));
    assertEquals("Other vector cannot be null", exception.getMessage());
  }

  /**
   * Tests raising a complex number to a power.
   */
  @Test
  void testPow_Valid() {
    Complex complex = new Complex(1.0, 1.0);
    Complex result = complex.pow(2);

    assertEquals(0.0, result.getX0(), 1e-10, "Real part of the result should be 0.0");
    assertEquals(2.0, result.getX1(), 1e-10, "Imaginary part of the result should be 2.0");
  }

  /**
   * Tests raising a complex number to a negative power.
   */
  @Test
  void testPow_Negative() {
    Complex complex = new Complex(1.0, 1.0);
    Complex result = complex.pow(-1);

    assertEquals(0.5, result.getX0(), 1e-10, "Real part of the result should be 0.5");
    assertEquals(-0.5, result.getX1(), 1e-10, "Imaginary part of the result should be -0.5");
  }

  /**
   * Tests the norm of a complex number.
   */
  @Test
  void testNorm() {
    Complex complex = new Complex(3.0, 4.0);
    double result = complex.norm();

    assertEquals(5.0, result, "Norm should be 5.0");
  }

  /**
   * Tests the magnitude of a complex number.
   */
  @Test
  void testMagnitude() {
    Complex complex = new Complex(3.0, 4.0);
    double result = complex.magnitude();

    assertEquals(5.0, result, "Magnitude should be 5.0");
  }

  /**
   * Tests the angle of a complex number.
   */
  @Test
  void testAngle() {
    Complex complex = new Complex(1.0, 1.0);
    double result = complex.angle();

    assertEquals(Math.PI / 4, result, 1e-10, "Angle should be pi/4 radians");
  }
}
