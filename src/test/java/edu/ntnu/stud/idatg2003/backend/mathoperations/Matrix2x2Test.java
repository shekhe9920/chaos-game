package edu.ntnu.stud.idatg2003.backend.mathoperations;

import org.junit.jupiter.api.Assertions;
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
   * Tests the construction of a 2x2 matrix with valid elements.
   */
  @Test
  void constructMatrix_ValidElements_ShouldPass() {
    Assertions.assertDoesNotThrow(() -> new Matrix2x2(1.0, 2.0, 3.0, 4.0),
        "Construction with valid elements should not throw an exception.");
  }


  /**
   * Tests the construction of a 2x2 matrix with invalid elements.
   */
  @Test
  void constructMatrix_InvalidElements_ShouldThrowException() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> new Matrix2x2(Double.NaN, 2.0, 3.0, 4.0),
        "Construction with NaN should throw IllegalArgumentException.");
  }


  /**
   * Tests the multiplication of a 2x2 matrix with a valid vector.
   */
  @Test
  void multiply_WithValidVector_ShouldReturnCorrectVector() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
    Vector2D vector = new Vector2D(1, 1);
    Vector2D result = matrix.multiply(vector);

    Assertions.assertEquals(3.0, result.getX0(), "The x component of the result is incorrect.");
    Assertions.assertEquals(7.0, result.getX1(), "The y component of the result is incorrect.");
  }


  /**
   * Tests the retrieval of elements from a 2x2 matrix.
   */
  @Test
  void getElement_ValidRowAndColumn_ShouldReturnCorrectElement() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
    Assertions.assertEquals(1.0, matrix.getElement(0, 0), "Element at (0, 0) should be 1.");
    Assertions.assertEquals(4.0, matrix.getElement(1, 1), "Element at (1, 1) should be 4.");
  }


  /**
   * Tests the retrieval of elements from a 2x2 matrix with an invalid row.
   */
  @Test
  void getElement_InvalidRow_ShouldThrowException() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);

    Assertions.assertThrows(IllegalArgumentException.class, () -> matrix.getElement(2, 0),
        "Accessing element with an invalid row should throw IllegalArgumentException.");
  }


  /**
   * Tests the retrieval of elements from a 2x2 matrix with an invalid column.
   */
  @Test
  void getElement_InvalidColumn_ShouldThrowException() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);

    Assertions.assertThrows(IllegalArgumentException.class, () -> matrix.getElement(0, 2),
        "Accessing element with an invalid column should throw IllegalArgumentException.");
  }


  /**
   * Tests the conversion of a 2x2 matrix to a 2D array.
   */
  @Test
  void toArray_ShouldReturnEquivalent2DArray() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
    double[][] arrayRepresentation = matrix.toArray();

    Assertions.assertArrayEquals(new double[][]{{1, 2}, {3, 4}}, arrayRepresentation,
        "The 2D array representation of the matrix is incorrect.");
  }

}