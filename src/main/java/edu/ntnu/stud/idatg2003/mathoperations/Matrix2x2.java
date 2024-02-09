package edu.ntnu.stud.idatg2003.mathoperations;

/**
 * {@code Matrix2x2} is a class representing a 2x2 matrix.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
public class Matrix2x2 {

  private final double[][] matrix; // 2x2 matrix, represented as 2 dimensional array.



  /**
   * Construct a new {@code Matrix2x2} object with the given matrix.
   *
   * @param matrix The matrix.
   * @since 0.0.0
   */
  public Matrix2x2(double [][] matrix) {
    this.matrix = matrix;
  }



  /**
   * Multiplies the given vector with the matrix and returns the result.
   *
   * @param vector The vector to multiply with the matrix.
   * @return The result of the multiplication.
   * @since 0.0.0
   */
  public Vector2D multiply(Vector2D vector) {
    double resultX0 = matrix[0][0] * vector.getX0() + matrix[0][1] * vector.getX1();
    double resultX1 = matrix[1][0] * vector.getX0() + matrix[1][1] * vector.getX1();
    return new Vector2D(resultX0, resultX1);
  }
}
