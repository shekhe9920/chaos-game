package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ChaosGameDescriptionFactory}.
 * This class contains tests for verifying the functionality of the ChaosGameDescriptionFactory class.
 */
class ChaosGameDescriptionFactoryTest {

  /**
   * Tests the creation of the Sierpinski Triangle description.
   */
  @Test
  void testCreateSierpinskiTriangle() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createSierpinskiTriangle();
    assertNotNull(description, "The description should not be null.");
    assertEquals(new Vector2D(0, 0), description.getMinCoords(), "MinCoords should be (0, 0).");
    assertEquals(new Vector2D(1, 1), description.getMaxCoords(), "MaxCoords should be (1, 1).");

    List<Transform2D> transformations = description.getTransformations();
    assertEquals(3, transformations.size(), "There should be 3 transformations.");
    assertInstanceOf(AffineTransform2D.class, transformations.getFirst(),
        "Transformations should be of type AffineTransform2D.");
  }

  /**
   * Tests the creation of the Barnsley Fern description.
   */
  @Test
  void testCreateBarnsleyFern() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createBarnsleyFern();
    assertNotNull(description, "The description should not be null.");
    assertEquals(new Vector2D(-2.65, 0), description.getMinCoords(), "MinCoords should be (-2.65, 0).");
    assertEquals(new Vector2D(2.65, 10), description.getMaxCoords(), "MaxCoords should be (2.65, 10).");

    List<Transform2D> transformations = description.getTransformations();
    assertEquals(4, transformations.size(), "There should be 4 transformations.");
    assertInstanceOf(AffineTransform2D.class, transformations.getFirst(),
        "Transformations should be of type AffineTransform2D.");
  }

  /**
   * Tests the creation of the Dragon Curve description.
   */
  @Test
  void testCreateDragonCurve() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createDragonCurve();
    assertNotNull(description, "The description should not be null.");
    assertEquals(new Vector2D(-6.8, -2.5), description.getMinCoords(), "MinCoords should be (-6.8, -2.5).");
    assertEquals(new Vector2D(2.6, 8), description.getMaxCoords(), "MaxCoords should be (2.6, 8).");

    List<Transform2D> transformations = description.getTransformations();
    assertEquals(2, transformations.size(), "There should be 2 transformations.");
    assertInstanceOf(AffineTransform2D.class, transformations.getFirst(),
        "Transformations should be of type AffineTransform2D.");
  }

  /**
   * Tests the creation of the Maple Leaf description.
   */
  @Test
  void testCreateMapleLeaf() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createMapleLeaf();
    assertNotNull(description, "The description should not be null.");
    assertEquals(new Vector2D(-4, -4), description.getMinCoords(), "MinCoords should be (-4, -4).");
    assertEquals(new Vector2D(5, 4), description.getMaxCoords(), "MaxCoords should be (5, 4).");

    List<Transform2D> transformations = description.getTransformations();
    assertEquals(4, transformations.size(), "There should be 4 transformations.");
    assertInstanceOf(AffineTransform2D.class, transformations.getFirst(),
        "Transformations should be of type AffineTransform2D.");
  }

  /**
   * Tests the creation of the Spiral description.
   */
  @Test
  void testCreateSpiral() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createSpiral();
    assertNotNull(description, "The description should not be null.");
    assertEquals(new Vector2D(-20, -10), description.getMinCoords(), "MinCoords should be (-20, -10).");
    assertEquals(new Vector2D(20, 5), description.getMaxCoords(), "MaxCoords should be (20, 5).");

    List<Transform2D> transformations = description.getTransformations();
    assertEquals(2, transformations.size(), "There should be 2 transformations.");
    assertInstanceOf(AffineTransform2D.class, transformations.getFirst(),
        "Transformations should be of type AffineTransform2D.");
  }

  /**
   * Tests the creation of a Julia Set description.
   */
  @Test
  void testCreateJuliaSetDescription() {
    Complex c = new Complex(0.355, 0.355);
    ChaosGameDescription description = ChaosGameDescriptionFactory.createJuliaSetDescription(c, 2);
    assertNotNull(description, "The description should not be null.");
    assertEquals(new Vector2D(-1.5, -1.5), description.getMinCoords(), "MinCoords should be (-1.5, -1.5).");
    assertEquals(new Vector2D(1.5, 1.5), description.getMaxCoords(), "MaxCoords should be (1.5, 1.5).");

    List<Transform2D> transformations = description.getTransformations();
    assertEquals(2, transformations.size(), "There should be 2 transformations.");
  }

  /**
   * Tests the creation of a Mandelbrot Set description.
   */
  @Test
  void testCreateMandelbrotSetDescription() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createMandelbrotSetDescription();
    assertNotNull(description, "The description should not be null.");
    assertEquals(new Vector2D(-2.5, -2), description.getMinCoords(), "MinCoords should be (-2.5, -2).");
    assertEquals(new Vector2D(1.5, 2), description.getMaxCoords(), "MaxCoords should be (1.5, 2).");

    List<Transform2D> transformations = description.getTransformations();
    assertEquals(1, transformations.size(), "There should be 1 transformation.");
  }
}
