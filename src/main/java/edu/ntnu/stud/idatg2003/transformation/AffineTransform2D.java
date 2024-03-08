package edu.ntnu.stud.idatg2003.transformation;

import edu.ntnu.stud.idatg2003.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;

/**
 * The {@code AffineTransform2D} class represents an Affine transformation in the form of
 * x -> Ax + b, where A is a 2x2 matrix, and b is a 2-dimensional vector.
 * It implements the {@code Transform2D} interface, and has a single method, {@code transform}.
 *
 * @version 0.0.1
 * @since 0.0.0
 */
public class AffineTransform2D implements Transform2D {

  private Matrix2x2 matrix; // a 2x2 matrix, representing the linear transformation.
  private Vector2D vector; // a 2-dimensional vector, representing the translation.

  /**
   * The constructor for the {@code AffineTransform2D} class. It takes a 2x2 matrix and
   * a 2-dimensional vector as input and constructs a new {@code AffineTransform2D} object.
   *
   * @param matrix The 2x2 matrix.
   * @param vector The 2-dimensional vector.
   * @since 0.0.0
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) {
    this.matrix = matrix;
    this.vector = vector;
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

  public Matrix2x2 getMatrix() {
    return matrix;
  }

  public Vector2D getVector() {
    return vector;
  }
}
