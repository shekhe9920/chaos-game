package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ChaosGameDescription}.
 * This class contains tests for verifying the functionality of the ChaosGameDescription class,
 * including both positive and negative tests.
 */
class ChaosGameDescriptionTest {

  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transformations;

  @BeforeEach
  void setUp() {
    minCoords = new Vector2D(-1, -1);
    maxCoords = new Vector2D(1, 1);
    transformations = new ArrayList<>();
    Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5);
    Vector2D vector = new Vector2D(1, 1);
    transformations.add(new AffineTransform2D(matrix, vector));
  }

  /**
   * Tests the initialization of ChaosGameDescription with valid parameters.
   */
  @Test
  void testInitialization_Valid() {
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);
    assertEquals(minCoords, description.getMinCoords(), "MinCoords should match the initialized value.");
    assertEquals(maxCoords, description.getMaxCoords(), "MaxCoords should match the initialized value.");
    assertEquals(transformations, description.getTransformations(), "Transformations should match the initialized list.");
  }

  /**
   * Tests setting the transformations with a valid list.
   */
  @Test
  void testSetTransformations_Valid() {
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);
    Matrix2x2 newMatrix = new Matrix2x2(0.3, 0, 0, 0.3);
    Vector2D newVector = new Vector2D(2, 2);
    List<Transform2D> newTransformations = List.of(new AffineTransform2D(newMatrix, newVector));
    description.setTransformations(newTransformations);
    assertEquals(newTransformations, description.getTransformations(), "Transformations should be updated to the new list.");
  }

  /**
   * Tests setting the transformations with a null list, expecting an IllegalArgumentException.
   */
  @Test
  void testSetTransformations_Null() {
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> description.setTransformations(null));
    assertEquals("Transformations cannot be null", exception.getMessage());
  }

  /**
   * Tests setting the minimum coordinates with a valid Vector2D.
   */
  @Test
  void testSetMinCoords_Valid() {
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);
    Vector2D newMinCoords = new Vector2D(-2, -2);
    description.setMinCoords(newMinCoords);
    assertEquals(newMinCoords, description.getMinCoords(), "MinCoords should be updated to the new value.");
  }

  /**
   * Tests setting the minimum coordinates with a null value, expecting an IllegalArgumentException.
   */
  @Test
  void testSetMinCoords_Null() {
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> description.setMinCoords(null));
    assertEquals("MinCoords cannot be null", exception.getMessage());
  }

  /**
   * Tests setting the maximum coordinates with a valid Vector2D.
   */
  @Test
  void testSetMaxCoords_Valid() {
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);
    Vector2D newMaxCoords = new Vector2D(2, 2);
    description.setMaxCoords(newMaxCoords);
    assertEquals(newMaxCoords, description.getMaxCoords(), "MaxCoords should be updated to the new value.");
  }

  /**
   * Tests setting the maximum coordinates with a null value, expecting an IllegalArgumentException.
   */
  @Test
  void testSetMaxCoords_Null() {
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> description.setMaxCoords(null));
    assertEquals("MaxCoords cannot be null", exception.getMessage());
  }
}
