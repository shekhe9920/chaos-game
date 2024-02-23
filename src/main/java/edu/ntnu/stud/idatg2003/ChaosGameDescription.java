package edu.ntnu.stud.idatg2003;

import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.transformation.Transform2D;
import java.util.List;

public class ChaosGameDescription {

  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transformations;

  public ChaosGameDescription(List<Transform2D> transformations, Vector2D minCoords, Vector2D maxCoords) {
    this.transformations = transformations;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
  }

  /**
   *
   *
   * @return
   */
  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  /**
   *
   * @return
   */
  public Vector2D getMinCoords() {
    return minCoords;
  }

  public List<Transform2D> getTransformations() {
    return transformations;
  }
}
