package edu.ntnu.stud.idatg2003.backend.mathoperations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@code Vector2DTest} class contains unit tests for the {@code Vector2D} class,
 * which represents a 2D vector with x0 and x1 coordinates. The tests verify that the
 * addition and subtraction of two vectors are performed correctly.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
class Vector2DTest {




  @Test
  void createVector2D_WithValidCoordinates_ShouldSucceed() {
    assertDoesNotThrow(() -> new Vector2D(1.0, 2.0),
        "Creating Vector2D with valid coordinates should not throw an exception.");
  }




  @Test
  void createVector2D_WithInvalidCoordinates_ShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new Vector2D(Double.NaN, 1.0),
        "Creating Vector2D with NaN as a coordinate should throw IllegalArgumentException.");
    assertThrows(IllegalArgumentException.class, () -> new Vector2D(1.0, Double.POSITIVE_INFINITY),
        "Creating Vector2D with an infinite value should throw IllegalArgumentException.");
  }




  @Test
  void constructor_ShouldCreateVectorWithGivenCoordinates() {
    Vector2D vector = new Vector2D(1.0, 2.0);
    Assertions.assertEquals(1.0, vector.getX0(), "X0 coordinate does not match.");
    Assertions.assertEquals(2.0, vector.getX1(), "X1 coordinate does not match.");
  }




  @Test
  void add_TwoVectors_ShouldReturnCorrectSum() {
    Vector2D v1 = new Vector2D(1, 2);
    Vector2D v2 = new Vector2D(3, 4);
    Vector2D result = v1.add(v2);

    Assertions.assertEquals(4.0, result.getX0(), "Sum of X0 coordinates is incorrect.");
    Assertions.assertEquals(6.0, result.getX1(), "Sum of X1 coordinates is incorrect.");
  }




  @Test
  void add_NullVector_ShouldThrowException() {
    Vector2D v1 = new Vector2D(1, 2);

    Assertions.assertThrows(IllegalArgumentException.class, () -> v1.add(null),
        "Adding a null vector should throw IllegalArgumentException.");
  }




  @Test
  void subtract_TwoVectors_ShouldReturnCorrectDifference() {
    Vector2D v1 = new Vector2D(5, 5);
    Vector2D v2 = new Vector2D(2, 3);
    Vector2D result = v1.subtract(v2);

    Assertions.assertEquals(3.0, result.getX0(), "Difference of X0 coordinates is incorrect.");
    Assertions.assertEquals(2.0, result.getX1(), "Difference of X1 coordinates is incorrect.");
  }




  // Test that adding a Vector2D to a Complex returns a Complex
  @Test
  void add_VectorToComplex_ShouldReturnComplex() {
    Vector2D v1 = new Vector2D(1, 2);
    Complex c1 = new Complex(3, 4);
    Vector2D result = v1.add(c1);

    assertInstanceOf(Complex.class, result, "Result should be an instance of Complex.");
    Assertions.assertEquals(4.0, result.getX0(), "Real part of result is incorrect.");
    Assertions.assertEquals(6.0, result.getX1(), "Imaginary part of result is incorrect.");
  }
}