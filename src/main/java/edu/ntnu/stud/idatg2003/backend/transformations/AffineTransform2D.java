package edu.ntnu.stud.idatg2003.backend.transformations;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;

/**
 * The {@code AffineTransform2D} record class represents an Affine transformation in the form
 * of 'x -> Ax + b', where A is a 2x2 matrix, and b is a 2-dimensional vector.
 * It implements the {@code Transform2D} interface.
 *
 * <p>
 * An affine transformation can be represented as:
 * <pre>
 * x' = Ax + b
 * </pre>
 * where x is the original vector, x' is the transformed vector, A is a 2x2 matrix representing
 * linear transformation, and b is a 2D vector representing translation.
 * </p>
 *
 * @param matrix a 2x2 matrix, representing the linear transformation.
 * @param vector a 2-dimensional vector, representing the translation.
 * @version 0.0.3
 * @since 0.0.0 (The version of Chaos-Game application when introduced)
 */
public record AffineTransform2D(Matrix2x2 matrix, Vector2D vector) implements Transform2D {


  /**
   * The constructor for the {@code AffineTransform2D} class.
   * It takes a 2x2 matrix and a 2-dimensional vector as input
   * and constructs a new {@code AffineTransform2D} object.
   * If either the matrix or vector is null, an exception is thrown.
   *
   * @param matrix The 2x2 matrix.
   * @param vector The 2-dimensional vector.
   * @throws IllegalArgumentException If either the matrix or vector is null.
   * @since 0.0.0
   */
  public AffineTransform2D {
    if (matrix == null || vector == null) {
      throw new IllegalArgumentException("The matrix and vector cannot be null");
    }
  }


  /**
   * Transforms the given vector.
   *
   * @param point The vector to transform.
   * @return The transformed vector.
   * @since 0.0.0
   */
  @Override
  public Vector2D transform(Vector2D point) {
    return matrix.multiply(point).add(vector);
  }


}
