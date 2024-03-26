package edu.ntnu.stud.idatg2003.backend.transformation;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * The AffineTransform2DTest class contains JUnit tests for the AffineTransform2D class,
 * specifically testing the transform method for positive and negative cases.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
class AffineTransform2DTest {



  @Test
  void constructor_NullMatrix_ShouldThrowException() {
    Vector2D vector = new Vector2D(1, 1);
    Assertions.assertThrows(IllegalArgumentException.class, () -> new AffineTransform2D(null, vector),
        "Constructor should throw IllegalArgumentException if the matrix is null.");
  }




  @Test
  void constructor_NullVector_ShouldThrowException() {
    Matrix2x2 matrix = new Matrix2x2(1, 0, 0, 1);
    Assertions.assertThrows(IllegalArgumentException.class, () -> new AffineTransform2D(matrix, null),
        "Constructor should throw IllegalArgumentException if the vector is null.");
  }




  @Test
  void transform_ValidPoint_ShouldReturnCorrectResult() {
    // Setup a transformation that scales by 2 and translates by (1, 1):
    Matrix2x2 matrix = new Matrix2x2(2, 0, 0, 2);
    Vector2D vector = new Vector2D(1, 1);
    AffineTransform2D transform = new AffineTransform2D(matrix, vector);

    Vector2D point = new Vector2D(1, 1);
    Vector2D expected = new Vector2D(3, 3); // Scaled by 2, then translated by (1, 1)

    Vector2D result = transform.transform(point);

    Assertions.assertEquals(expected.getX0(), result.getX0(), 0.001, "X component of the transformed vector is incorrect.");
    Assertions.assertEquals(expected.getX1(), result.getX1(), 0.001, "Y component of the transformed vector is incorrect.");
  }




  @Test
  void getMatrix_ShouldReturnCorrectMatrix() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
    Vector2D vector = new Vector2D(5, 6);
    AffineTransform2D transform = new AffineTransform2D(matrix, vector);

    Matrix2x2 resultMatrix = transform.getMatrix();

    Assertions.assertNotNull(resultMatrix, "getMatrix should not return null.");
    Assertions.assertEquals(matrix.getA00(), resultMatrix.getA00(), "Matrix elements should match.");
    Assertions.assertEquals(matrix.getA01(), resultMatrix.getA01(), "Matrix elements should match.");
    Assertions.assertEquals(matrix.getA10(), resultMatrix.getA10(), "Matrix elements should match.");
    Assertions.assertEquals(matrix.getA11(), resultMatrix.getA11(), "Matrix elements should match.");
  }




  @Test
  void getVector_ShouldReturnCorrectVector() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
    Vector2D vector = new Vector2D(5, 6);
    AffineTransform2D transform = new AffineTransform2D(matrix, vector);

    Vector2D resultVector = transform.getVector();

    Assertions.assertNotNull(resultVector, "getVector should not return null.");
    Assertions.assertEquals(vector.getX0(), resultVector.getX0(), "Vector components should match.");
    Assertions.assertEquals(vector.getX1(), resultVector.getX1(), "Vector components should match.");
  }

}
