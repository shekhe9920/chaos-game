package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChaosGameTest {

  private ChaosGame game;

  @BeforeEach
  void setUp() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);
    List<Transform2D> transformations = List.of(new DummyTransform());

    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);
    game = new ChaosGame(description, 100, 100);
  }

  @Test
  void runSteps_ShouldUpdateCurrentPoint() {
    game.runSteps(1);

    Vector2D expectedPoint = new Vector2D(0.5, 0.5);
    Vector2D actualPoint = game.getCurrentPoint();

    Assertions.assertEquals(expectedPoint.getX0(), actualPoint.getX0(), "X coordinate did not match after 1 step.");
    Assertions.assertEquals(expectedPoint.getX1(), actualPoint.getX1(), "Y coordinate did not match after 1 step.");
  }

  // DummyTransform for testing purposes:
  static class DummyTransform implements Transform2D {
    @Override
    public Vector2D transform(Vector2D z) {
      return new Vector2D(0.5, 0.5); // Returning a simple, fixed transformation
    }
  }
}

