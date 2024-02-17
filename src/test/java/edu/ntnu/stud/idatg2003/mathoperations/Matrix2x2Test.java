package edu.ntnu.stud.idatg2003.mathoperations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * The Matrix2x2Test class contains JUnit tests for the Matrix2x2 class,
 * specifically testing the multiply method for positive and negative cases.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
class Matrix2x2Test {

  /**
   * Positive test for the multiply method.
   * It validates that the Matrix2x2 multiplication with a Vector2D produces
   * the expected result.
   */
  @Test
  void multiply_PositiveTest() {
    // Arrange
    double[][] matrixArray = {{2, 1}, {1, 3}};
    Matrix2x2 matrix = new Matrix2x2(matrixArray);
    Vector2D vector = new Vector2D(1, 2);

    // Act
    Vector2D result = matrix.multiply(vector);

    // Assert
    assertEquals(4, result.getX0());
    assertEquals(7, result.getX1());
  }

  /**
   * Negative test for the multiply method.
   * It verifies that the Matrix2x2 multiplication with a Vector2D does not
   * produce certain incorrect results.
   */
  @Test
  void multiply_NegativeTest() {
    // Arrange
    double[][] matrixArray = {{2, 1}, {1, 3}};
    Matrix2x2 matrix = new Matrix2x2(matrixArray);
    Vector2D vector = new Vector2D(1, 2);

    // Act
    Vector2D result = matrix.multiply(vector);

    // Assert
    assertNotEquals(3, result.getX0());
    assertNotEquals(5, result.getX1());
  }
}