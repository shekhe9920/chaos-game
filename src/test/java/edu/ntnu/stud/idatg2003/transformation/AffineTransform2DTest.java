package edu.ntnu.stud.idatg2003.transformation;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.idatg2003.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import org.junit.jupiter.api.Test;
/**
 * The AffineTransform2DTest class contains JUnit tests for the AffineTransform2D class,
 * specifically testing the transform method for positive and negative cases.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
class AffineTransform2DTest {

  /**
   * Positive test for the transform method.
   * It validates that the AffineTransform2D transformation produces
   * the expected result when applied to a Vector2D.
   */
  @Test
  void transform_Positive() {
    // Arrange
    Matrix2x2 matrix = new Matrix2x2(new double[][] {{2, 0}, {0, 2}});
    Vector2D vector = new Vector2D(1, 1);
    AffineTransform2D transform = new AffineTransform2D(matrix, vector);

    // Act
    Vector2D result = transform.transform(new Vector2D(2, 2));

    // Assert
    assertEquals(5, result.getX0(), 1e-6); // 2 * 2 + 1 = 5
    assertEquals(5, result.getX1(), 1e-6); // 2 * 2 + 1 = 5
  }

  /**
   * Negative test for the transform method.
   * It verifies that the AffineTransform2D transformation does not
   * produce certain incorrect results when applied to a Vector2D.
   */
  @Test
  void transform_Negative() {
    // Arrange
    Matrix2x2 matrix = new Matrix2x2(new double[][] {{2, 0}, {0, 2}});
    Vector2D vector = new Vector2D(1, 1);
    AffineTransform2D transform = new AffineTransform2D(matrix, vector);

    // Act
    Vector2D result = transform.transform(new Vector2D(2, 2));

    // Assert
    assertNotEquals(4, result.getX0(), 1e-6); // 2 * 2 + 1 = 5, not 4
    assertNotEquals(4, result.getX1(), 1e-6); // 2 * 2 + 1 = 5, not 4
  }
}
