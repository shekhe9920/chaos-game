package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.ChaosGameObserver;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import edu.ntnu.stud.idatg2003.backend.utilitiesbackend.WeightedRandomSampler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ChaosGame}.
 * This class contains tests for verifying the functionality of the ChaosGame class,
 * including both positive and negative tests.
 */
class ChaosGameTest {

  private ChaosGame chaosGame;
  private ChaosGameDescription description;

  @BeforeEach
  public void setUp() {
    description = ChaosGameDescriptionFactory.createSierpinskiTriangle();
    int canvasHeight = 600;
    int canvasWidth = 800;
    chaosGame = new ChaosGame(description, canvasWidth, canvasHeight);
  }

  /**
   * Tests if the ChaosGame initializes correctly with given parameters.
   */
  @Test
  void testInitialization() {
    assertNotNull(chaosGame.getCanvas(), "Canvas should not be null after initialization.");
    assertEquals(new Vector2D(0, 0), chaosGame.getCurrentPoint(), "Initial current point should be (0, 0).");
    assertEquals(description, chaosGame.getDescription(), "Description should match the one provided in initialization.");
  }

  /**
   * Tests setting a valid starting point.
   */
  @Test
  void testSetStartingPoint_Valid() {
    Vector2D newStartPoint = new Vector2D(2, 2);
    chaosGame.setStartingPoint(newStartPoint);
    assertEquals(newStartPoint, chaosGame.getCurrentPoint(), "Current point should be updated to the new starting point.");
  }

  /**
   * Tests setting a null starting point, expecting an IllegalArgumentException.
   */
  @Test
  void testSetStartingPoint_Null() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chaosGame.setStartingPoint(null));
    assertEquals("Starting point cannot be null", exception.getMessage());
  }

  /**
   * Tests setting valid transformation weights.
   */
  @Test
  void testSetTransformWeights_Valid() {
    List<Double> weights = List.of(0.5, 0.5);
    chaosGame.setTransformWeights(weights);
    assertEquals(weights, chaosGame.getWeights(), "Weights should be updated to the new values.");
  }

  /**
   * Tests setting null transformation weights, expecting an IllegalArgumentException.
   */
  @Test
  void testSetTransformWeights_Null() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chaosGame.setTransformWeights(null));
    assertEquals("Weights cannot be null or empty", exception.getMessage());
  }

  /**
   * Tests running the ChaosGame for a valid number of steps.
   */
  @Test
  void testRunSteps_Valid() {
    List<Double> weights = List.of(0.33, 0.33, 0.33);
    chaosGame.setTransformWeights(weights);
    chaosGame.runSteps(10);
    assertEquals(10, chaosGame.getSteps(), "The number of steps should be updated to 10.");
  }

  /**
   * Tests running the ChaosGame without setting weights, expecting an IllegalStateException.
   */
  @Test
  void testRunSteps_NoWeightsSet() {
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> chaosGame.runSteps(10));
    assertEquals("Weights are not set.", exception.getMessage());
  }

  /**
   * Tests updating the game description and notifying observers.
   */
  @Test
  void testUpdateDescription() {
    ChaosGameObserver observer = new ChaosGameObserver() {
      @Override
      public void onChaosDescriptionChanged(ChaosGameDescription newDescription) {
        assertEquals(description, newDescription, "Observer should be notified with the correct new description.");
      }

      @Override
      public void onChaosGameUpdated() {
        // Not used in this test
      }
    };

    chaosGame.addObserver(observer);
    chaosGame.updateDescription(description);
  }

  /**
   * Tests removing an observer and ensuring it no longer receives updates.
   */
  @Test
  void testRemoveObserver() {
    ChaosGameObserver observer = new ChaosGameObserver() {
      @Override
      public void onChaosDescriptionChanged(ChaosGameDescription newDescription) {
        fail("Observer should not receive updates after being removed.");
      }

      @Override
      public void onChaosGameUpdated() {
        // Not used in this test
      }
    };

    chaosGame.addObserver(observer);
    chaosGame.removeObserver(observer);
    chaosGame.updateDescription(description);  // Should not trigger the observer
  }

}
