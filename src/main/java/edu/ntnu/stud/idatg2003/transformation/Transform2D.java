package edu.ntnu.stud.idatg2003.transformation;

import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;

/**
 * The {@code Transform2D} interface represents a 2-dimensional transformation.
 * It has a single method, {@code transform}, which takes a 2-dimensional vector
 * as an input and returns a new 2-dimensional vector as an output.
 * The transformation is defined by the implementing classes.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
public interface Transform2D {

  /**
   * Transforms the given vector using the transformation.
   * The method takes a 2-dimensional vector
   * as an input and returns a new 2-dimensional vector as an output.
   * The transformation is defined by the implementing class.
   *
   * @param vector The vector to transform.
   * @return The transformed vector.
   * @since 0.0.0
   */
  Vector2D transform(Vector2D vector);

}
