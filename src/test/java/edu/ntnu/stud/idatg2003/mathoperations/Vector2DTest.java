package edu.ntnu.stud.idatg2003.mathoperations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The {@code Vector2DTest} class contains unit tests for the {@code Vector2D} class,
 * which represents a 2D vector with x0 and x1 coordinates. The tests verify that the
 * addition and subtraction of two vectors are performed correctly.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
class Vector2DTest {

  private Complex complexTest0;
  private Complex complexTest1;
  private Complex complexTest2;
  private Vector2D vectorTest0;
  private Vector2D vectorTest1;
  private Vector2D vectorTest2;

  @BeforeEach
  void setUp() {
    complexTest0 = new Complex(1, 1);
    complexTest1 = new Complex(2, 2);
    complexTest2 = null;

    vectorTest0 = new Vector2D(1, 2);
    vectorTest1 = new Vector2D(2, 3);
    vectorTest2 = null;
  }



  /**
   * Test the positive case of adding two complex numbers.
   */
  @Test
  void addingTwoComplexNumberPositiveTest() {
    // Act: (1 + 1i) + (2 + 2i) = (1 + 2) + (1 + 2)i = (3 + 3i)
    Complex result = (Complex) complexTest0.add(complexTest1);

    double expectedX0 = 3;  // expected real part
    double expectedX1 = 3;  // expected imaginary part

    double actualX0 = result.getX0();  // actual real part
    double actualX1 = result.getX1();  // actual imaginary part

    // Assert:
    assertEquals("(" + expectedX0 + "," + expectedX1 + ")",
        "(" + actualX0 + "," + actualX1 + ")",
        "CALCULATION ERROR: The result of the addition is not as expected");

    System.out.println("The result of the addition is as expected: "
        + "(" + actualX0 + ", " + actualX1 + ")");
  }

  /**
   * Test the positive case of adding two vectors.
   */
  @Test
  void addingTwoVectorsPositiveTest() {
    // Act: [1, 2] + [2, 3] = [1 + 2] + [2 + 3] = [3, 5]
    Vector2D result = vectorTest0.add(vectorTest1);

    double expectedX0 = 3;  // expected x0 coordinate
    double expectedX1 = 5;  // expected x1 coordinate

    double actualX0 = result.getX0();  // actual x0 coordinate
    double actualX1 = result.getX1();  // actual x1 coordinate

    // Assert:
    assertEquals("(" + expectedX0 + "," + expectedX1 + ")",
        "(" + actualX0 + "," + actualX1 + ")",
        "CALCULATION ERROR: The result of the addition is not as expected");


    System.out.println("The result of the addition is as expected: "
        + "(" + actualX0 + ", " + actualX1 + ")");
  }



  /**
   * Test the positive case of subtracting two vectors.
   */
  @Test
  void subtractingTwoComplexNumbersPositiveTest() {
    // Act: (1 + 1i) - (2 + 2i) = (1 - 2) + (1 - 2)i = (-1 - 1i)
    Vector2D result = complexTest0.subtract(complexTest1);

    double expectedX0 = -1;
    double expectedX1 = -1;

    double actualX0 = result.getX0();
    double actualX1 = result.getX1();

    assertEquals("(" + expectedX0 + "," + expectedX1 + ")",
        "(" + actualX0 + "," + actualX1 + ")",
        "CALCULATION ERROR: The result of the addition is not as expected");

    System.out.println("The result of the addition is as expected: "
        + "(" + actualX0 + ", " + actualX1 + ")");
  }




  /**
   * Test the case of subtracting and adding two vectors when the other vector is null (negative test).
   * Verifies that the method correctly throws an IllegalArgumentException
   * when trying to subtract or add a vector with null value.
   */
  @Test
  void subtractingAndAddingTwoVectorsWithNullValuesNegativeTest() {

    // Act and Assert: Expecting IllegalArgumentException to be thrown because vectorTest2 is null.
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      vectorTest0.subtract(vectorTest2);});  // vectorTest2 is null

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      vectorTest0.add(vectorTest2);});       // vectorTest2 is null

    System.out.println("IllegalArgumentException thrown, cannot subtract/add "
        + "when the other vector is null");
  }

  /**
   * Test the case of subtracting and adding two complex numbers
   * when the other vector is null (negative test).
   * Verifies that the method correctly throws an IllegalArgumentException
   * when trying to subtract or add a complex number with null value.
   */
  @Test
  void subtractingAndAddingTwoComplexNumbersWithNullValuesNegativeTest() {

    // Act and Assert: Expecting IllegalArgumentException to be thrown because complexTest2 is null.
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      complexTest0.subtract(complexTest2);}); // complexTest2 is null

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      vectorTest0.add(complexTest2);});      // complexTest2 is null

    System.out.println("IllegalArgumentException thrown, cannot subtract/add "
        + "when the other complex number is null");
  }
}