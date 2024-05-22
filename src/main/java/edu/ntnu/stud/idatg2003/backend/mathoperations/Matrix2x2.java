package edu.ntnu.stud.idatg2003.backend.mathoperations;

/**
 * {@code Matrix2x2} is a record class representing a 2x2 matrix.
 *
 * <p>
 * A 2x2 matrix is represented as:
 * <pre>
 * | a00 a01 |
 * | a10 a11 |
 * </pre>
 * </p>
 *
 * @param a00 The matrix elements:
 * @version 0.0.3
 * @since 0.0.0 (The version of Chaos-Game application when introduced)
 */
public record Matrix2x2(double a00, double a01, double a10, double a11) {

  /**
   * Constructs a new {@code Matrix2x2} object with the given components.
   *
   * @param a00 The value at row 0, column 0.
   * @param a01 The value at row 0, column 1.
   * @param a10 The value at row 1, column 0.
   * @param a11 The value at row 1, column 1.
   * @throws IllegalArgumentException if any of the values are not valid numbers.
   * @since 0.0.0
   */
  public Matrix2x2 {
    validateElement(a00);
    validateElement(a01);
    validateElement(a10);
    validateElement(a11);

  }


  /**
   * Validates that an element is a valid number.
   *
   * @param element The matrix element to validate.
   * @throws IllegalArgumentException if the element is NaN or infinite.
   * @since 0.0.2
   */
  private void validateElement(double element) {
    if (Double.isNaN(element) || Double.isInfinite(element)) {
      throw new IllegalArgumentException("Matrix element must be a valid number");
    }
  }



  /**
   * Multiplies this matrix by a given {@code Vector2D}.
   *
   * <p>
   * The multiplication of a 2x2 matrix with a vector [x, y]^T (T = transposed) is given by:
   * <pre>
   * | a00 a01 |   | x |   | a00*x + a01*y |
   * | a10 a11 | * | y | = | a10*x + a11*y |
   * </pre>
   * </p>
   *
   * @param vector The vector to multiply.
   * @return The resulting {@code Vector2D}.
   * @since 0.0.0
   */
  public Vector2D multiply(Vector2D vector) {
    return new Vector2D(
        a00 * vector.getX0() + a01 * vector.getX1(),
        a10 * vector.getX0() + a11 * vector.getX1()
    );
  }


  /**
   * Gets the matrix element at the specified row and column.
   *
   * @param row    The row index.
   * @param column The column index.
   * @return The matrix element.
   * @throws IllegalArgumentException if the row or column is neither zero nor one.
   * @since 0.0.0
   */
  public double getElement(int row, int column) {
    if (row < 0 || row > 1 || column < 0 || column > 1) {
      throw new IllegalArgumentException("Row and column must be 0 or 1 for a 2x2 matrix.");
    }
    if (row == 0) {
      return (column == 0) ? a00 : a01;
    } else {
      return (column == 0) ? a10 : a11;
    }
  }


  /**
   * Returns a 2D array representation of the matrix.
   *
   * @return The 2D array representing the matrix.
   * @since 0.0.0
   */
  public double[][] toArray() {
    return new double[][]{{a00, a01}, {a10, a11}};
  }


}

