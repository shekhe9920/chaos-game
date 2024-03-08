package edu.ntnu.stud.idatg2003.mathoperations;

/**
 * {@code Matrix2x2} is a class representing a 2x2 matrix.
 *
 * @version 0.0.1
 * @since 0.0.0
 */
public class Matrix2x2 {

  private final double[][] matrix; // 2x2 matrix, represented as 2-dimensional array.

  /**
   * Constructs a new {@code Matrix2x2} object with the given matrix.
   *
   * @param matrix The matrix.
   * @throws IllegalArgumentException if the matrix is null, not a 2x2 matrix, or contains invalid elements.
   * @since 0.0.0
   */
  public Matrix2x2(double[][] matrix) {
    // Check for null-matrix
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix cannot be null");
    }

    // Check for valid 2x2 matrix
    if (matrix.length != 2 || matrix[0].length != 2 || matrix[1].length != 2) {
      throw new IllegalArgumentException("Matrix must be a 2x2 matrix");
    }

    // TODO: kansje fornkle denne kode delen:
    // Check for NaN or infinite values
    for (double[] row : matrix) {
      for (double element : row) {
        if (Double.isNaN(element) || Double.isInfinite(element)) {
          throw new IllegalArgumentException("Matrix contains invalid elements");
        }
      }
    }

    this.matrix = matrix;
  }

  // TODO: det samme metoden er i Complex klassen
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


  public double[][] getMatrix() {
    return matrix;
  }


  public double getElement(int row, int column) {
    return matrix[row][column];
  }
}
