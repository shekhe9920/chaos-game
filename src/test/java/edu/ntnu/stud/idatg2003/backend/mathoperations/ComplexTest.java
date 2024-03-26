package edu.ntnu.stud.idatg2003.backend.mathoperations;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@code ComplexTest} class contains unit tests for the {@code Complex} class,
 * which represents a complex number with real and imaginary parts. The tests verify that the
 * multiplication and square root of complex numbers are performed correctly.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
class ComplexTest {




  @Test
  void createComplex_WithValidCoordinates_ShouldSucceed() {
    assertDoesNotThrow(() -> new Complex(1.0, 2.0),
        "Creating Complex with valid real and imaginary parts should not throw an exception.");
  }




  @Test
  void createComplex_WithInvalidCoordinates_ShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new Complex(Double.NaN, 2.0),
        "Creating Complex with NaN as a real part should throw IllegalArgumentException.");
    assertThrows(IllegalArgumentException.class, () -> new Complex(1.0, Double.NEGATIVE_INFINITY),
        "Creating Complex with an infinite imaginary part should throw IllegalArgumentException.");
  }




  @Test
  void constructor_ValidInput_ShouldCreateComplexNumber() {
    Complex complex = new Complex(1.0, 2.0);
    Assertions.assertEquals(1.0, complex.getX0(), "Real part should match constructor input.");
    Assertions.assertEquals(2.0, complex.getX1(), "Imaginary part should match constructor input.");
  }




  @Test
  void multiply_TwoComplexNumbers_ShouldReturnCorrectResult() {
    Complex c1 = new Complex(1.0, 2.0); // 1 + 2i
    Complex c2 = new Complex(3.0, 4.0); // 3 + 4i
    Complex result = c1.multiply(c2); // Expected: -5 + 10i

    Assertions.assertEquals(-5.0, result.getX0(), "Real part of result is incorrect.");
    Assertions.assertEquals(10.0, result.getX1(), "Imaginary part of result is incorrect.");
  }




  @Test
  void multiply_WithNull_ShouldThrowIllegalArgumentException() {
    Complex c1 = new Complex(1.0, 2.0);

    assertThrows(IllegalArgumentException.class, () -> c1.multiply(null),
        "Multiplying with null should throw IllegalArgumentException.");
  }




  @Test
  void sqrt_PositiveRealNumber_ShouldReturnCorrectResult() {
    Complex c = new Complex(4.0, 0.0); // 4
    Complex result = c.sqrt(); // Expected: 2 + 0i or -2 + 0i

    Assertions.assertEquals(0, Double.compare(2.0, result.getX0()),
        "Real part of the square root is incorrect.");
  }




  @Test
  void sqrt_NegativeRealNumber_ShouldReturnPrincipalRoot() {
    Complex c = new Complex(-4.0, 0.0); // -4
    Complex result = c.sqrt(); // Expected: 0 + 2i

    final double DELTA = 1e-15; // Define a small tolerance
    Assertions.assertEquals(0.0, result.getX0(), DELTA, "Real part of the square root is incorrect.");
    Assertions.assertEquals(2.0, result.getX1(), DELTA, "Imaginary part of the square root is incorrect.");
  }




  @Test
  void subtract_ComplexNumbers_ShouldReturnCorrectResult() {
    Complex c1 = new Complex(5.0, 6.0);
    Complex c2 = new Complex(3.0, 2.0);
    Vector2D result = c1.subtract(c2); // Expected: 2 + 4i

    Assertions.assertEquals(2.0, result.getX0(), "Real part of subtraction result is incorrect.");
    Assertions.assertEquals(4.0, result.getX1(), "Imaginary part of subtraction result is incorrect.");
  }

}