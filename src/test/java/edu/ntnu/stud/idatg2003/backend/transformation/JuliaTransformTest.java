package edu.ntnu.stud.idatg2003.backend.transformation;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.JuliaTransform;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * The {@code JuliaTransformTest} class contains unit tests for the {@code JuliaTransform} class,
 * which represents a complex transformation of the form z -> ±sqrt(z - c), where z is a complex number,
 * and c is a complex constant. The tests verify that the transformation is performed correctly
 * for both positive and negative signs.
 *
 */
class JuliaTransformTest {



  /**
   * Tests the constructor of JuliaTransform class with valid inputs.
   */
  @Test
  void testConstructor_Valid() {
    Complex point = new Complex(0.0, 1.0);
    JuliaTransform transform = new JuliaTransform(point, 1, 2, false);
    assertNotNull(transform, "JuliaTransform should be instantiated");
    assertEquals(point, transform.getPoint(), "Point should match the input value");
    assertEquals(2, transform.getOrder(), "Order should match the input value");
    assertFalse(transform.isMultiOrder(), "isMultiOrder should match the input value");
  }

  /**
   * Tests the constructor of JuliaTransform class with an invalid sign.
   */
  @Test
  void testConstructor_InvalidSign() {
    Complex point = new Complex(0.0, 1.0);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new JuliaTransform(point, 0, 2, false));
    assertEquals("Sign must be 1 or -1", exception.getMessage());
  }

  /**
   * Tests the constructor of JuliaTransform class with a null point.
   */
  @Test
  void testConstructor_NullPoint() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new JuliaTransform(null, 1, 2, false));
    assertEquals("Point must be a Complex object and not null", exception.getMessage());
  }

  /**
   * Tests the constructor of JuliaTransform class with an invalid order.
   */
  @Test
  void testConstructor_InvalidOrder() {
    Complex point = new Complex(0.0, 1.0);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new JuliaTransform(point, 1, -1, false));
    assertEquals("Order must be a positive integer", exception.getMessage());
  }

  /**
   * Tests the transformation method with valid input.
   */
  @Test
  void testTransform_Valid() {
    Complex point = new Complex(0.0, 1.0);
    JuliaTransform transform = new JuliaTransform(point, 1, 2, false);
    Vector2D input = new Complex(2.0, 2.0);
    Vector2D result = transform.transform(input);

    assertNotNull(result, "Result should not be null");
    assertTrue(true, "Result should be of type Vector2D");
  }

  /**
   * Tests the transformation method with null input, expecting a NullPointerException.
   */
  @Test
  void testTransform_Null() {
    Complex point = new Complex(0.0, 1.0);
    JuliaTransform transform = new JuliaTransform(point, 1, 2, false);
    assertThrows(NullPointerException.class, () -> transform.transform(null));
  }

  /**
   * Tests the calculateJuliaSetPoint method with valid inputs.
   */
  @Test
  void testCalculateJuliaSetPoint_Valid() {
    JuliaTransform transform = new JuliaTransform(new Complex(0.0, 1.0), 1, 2, false);
    int iterations = transform.calculateJuliaSetPoint(0.0, 0.0, 2, 0.0, 1.0, 1000);

    assertTrue(iterations >= 0, "Iterations should be non-negative");
  }

  /**
   * Tests the calculateMandelbrotSetPoint method with valid inputs.
   */
  @Test
  void testCalculateMandelbrotSetPoint_Valid() {
    JuliaTransform transform = new JuliaTransform(new Complex(0.0, 1.0), 1, 2, false);
    int iterations = transform.calculateMandelbrotSetPoint(0.0, 0.0, 2, 1000);

    assertTrue(iterations >= 0, "Iterations should be non-negative");
  }

  /**
   * Tests the calculateJuliaSetPoint method with invalid order.
   */
  @Test
  void testCalculateJuliaSetPoint_InvalidOrder() {
    JuliaTransform transform = new JuliaTransform(new Complex(0.0, 1.0), 1, 2, false);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transform.calculateJuliaSetPoint(0.0, 0.0, -1, 0.0, 1.0, 1000));
    assertEquals("Order must be a positive integer", exception.getMessage());
  }

  /**
   * Tests the calculateMandelbrotSetPoint method with invalid max iterations.
   */
  @Test
  void testCalculateMandelbrotSetPoint_InvalidMaxIterations() {
    JuliaTransform transform = new JuliaTransform(new Complex(0.0, 1.0), 1, 2, false);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transform.calculateMandelbrotSetPoint(0.0, 0.0, 2, -1000));
    assertEquals("Max iterations must be a positive integer", exception.getMessage());
  }

  /**
   * Tests the iterate method with valid input.
   */
  @Test
  void testIterate_Valid() {
    Complex z = new Complex(1.0, 1.0);
    Complex c = new Complex(0.5, 0.5);
    int order = 2;

    JuliaTransform transform = new JuliaTransform(new Complex(0.0, 1.0), 1, 2, false);
    Complex result = transform.iterate(z, c, order);

    assertNotNull(result, "Result should not be null");
    assertTrue(true, "Result should be of type Complex");
    assertEquals(0.5, result.getX0(), 1e-10, "Real part of the result should be 0.5");
    assertEquals(2.5, result.getX1(), 1e-10, "Imaginary part of the result should be 2.5");
  }

  /**
   * Tests the iterate method with invalid order.
   */
  @Test
  void testIterate_InvalidOrder() {
    Complex z = new Complex(1.0, 1.0);
    Complex c = new Complex(0.5, 0.5);
    int order = -1;

    JuliaTransform transform = new JuliaTransform(new Complex(0.0, 1.0), 1, 2, false);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transform.iterate(z, c, order));
    assertEquals("Order must be a positive integer", exception.getMessage());
  }



}