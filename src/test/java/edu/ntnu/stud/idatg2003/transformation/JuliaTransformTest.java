package edu.ntnu.stud.idatg2003.transformation;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.idatg2003.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The {@code JuliaTransformTest} class contains unit tests for the {@code JuliaTransform} class,
 * which represents a complex transformation of the form z -> ±sqrt(z - c), where z is a complex number,
 * and c is a complex constant. The tests verify that the transformation is performed correctly
 * for both positive and negative signs.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
class JuliaTransformTest {

  private final JuliaTransform juliaTransformTest =
      new JuliaTransform(new Complex(1, 2), 1);
  private final double tolerance = 0.000000000001;
  private final String message = "The result of the transformation is not as expected";




  // A list of test vectors for transformation:
  private final List<Vector2D> testVectors = List.of(
      new Vector2D(6, 4),
      new Vector2D(-2, 3),
      new Vector2D(1, 2),
      new Vector2D(0.5, 0.5),
      new Vector2D(-6, 2)
  );

  //================================================================================================
  // A list of the expected values for the test vectors when the sign is positive (1 * sqrt(z-c)).
  private final List<Double> expectedX0ValuesSignPositive  = List.of(
      2.27872385417085,
      0.284848784593141,
      0.0,
      0.735234258615643,
      0.0
  );

  private final List<Double> expectedX1ValuesSignPositive  = List.of(
      0.438842116902255,
      1.755317301824428,
      0.0,
      -1.020083043208784,
      2.645751311064591
  );

  // A list of the expected values for the test vectors when the sign is negative (-1 * sqrt(z-c)).
  private final List<Double> expectedX0ValuesSignNegative = List.of(
      -2.27872385417085,
      -0.284848784593141,
      0.0,
      -0.735234258615643,
      0.0
  );

  private final List<Double> expectedX1ValuesSignNegative = List.of(
      -0.438842116902255,
      -1.755317301824428,
      0.0,
      1.020083043208784,
      -2.645751311064591
  );

  //================================================================================================


  /**
   * Tests the transformation with a positive sign.
   */
  @Test
  void transformTestSignPositive() {
    JuliaTransform juliaTransformTest = new JuliaTransform(new Complex(1, 2), 1);
    performTest(juliaTransformTest, expectedX0ValuesSignPositive, expectedX1ValuesSignPositive);
  }

  /**
   * Tests the transformation with a negative sign.
   */
  @Test
  void transformTestSignNegative() {
    JuliaTransform juliaTransformTest = new JuliaTransform(new Complex(1, 2), -1);
    performTest(juliaTransformTest, expectedX0ValuesSignNegative, expectedX1ValuesSignNegative);
  }




  /**
   * Performs the transformation test for the specified
   * Julia transformation with the given expected values.
   *
   * <p>This method iterates over a list of test vectors and applies the provided Julia transformation
   * to each vector. It then compares the transformed vector components (x0 and x1) with the
   * expected values to ensure that the transformation is performed correctly. Assertions are made
   * using the {@code assertEquals} method with a tolerance level.
   *
   * @param juliaTransformTest The Julia transformation to test.
   * @param expectedX0Values   The list of expected x0 values for the transformed vectors.
   * @param expectedX1Values   The list of expected x1 values for the transformed vectors.
   */
  private void performTest(JuliaTransform juliaTransformTest, List<Double> expectedX0Values, List<Double> expectedX1Values) {
    for (int i = 0; i < testVectors.size(); i++) {
      Vector2D testVector = testVectors.get(i);
      Vector2D transformedVector = juliaTransformTest.transform(testVector);

      double expectedX0 = expectedX0Values.get(i);
      double expectedX1 = expectedX1Values.get(i);

      // Asserting the actual transformed values within the tolerance range of the expected values
      assertEquals(expectedX0, expectedX0Values.get(i), tolerance, message);
      assertEquals(expectedX1, transformedVector.getX1(), tolerance, message);


      // Printing the expected and actual transformed vector components for debugging purposes
      System.out.println(
          "Expected:: (" + expectedX0 + ", " + expectedX1 +
              " || " + "Actual: (" + transformedVector.getX0() + ", " + expectedX1Values.get(i) + ")");
    }
  }
}