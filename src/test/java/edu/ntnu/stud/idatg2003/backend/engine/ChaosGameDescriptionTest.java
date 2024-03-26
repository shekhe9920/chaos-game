package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChaosGameDescriptionTest {

  @Test
  void constructor_ValidInput_ShouldCreateDescription() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);
    List<Transform2D> transformations = List.of(new DummyTransform());

    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);

    Assertions.assertEquals(minCoords, description.getMinCoords(), "Min coords do not match.");
    Assertions.assertEquals(maxCoords, description.getMaxCoords(), "Max coords do not match.");
    Assertions.assertEquals(1, description.getTransformations().size(), "Transformations size should be 1.");
  }

  // DummyTransform for testing purposes:
  static class DummyTransform implements Transform2D {
    @Override
    public Vector2D transform(Vector2D z) {
      return new Vector2D(0.5, 0.5); // Returning a simple, fixed transformation
    }
  }
}
