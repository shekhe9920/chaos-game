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
 * @version 0.0.0
 * @since 0.0.0
 */
class JuliaTransformTest {


  private static final double TOLERANCE = 0.000000000001;


  //================================================================================================
  // A list of the expected values for the test vectors when the sign is positive (1 * sqrt(z-c)).
  private static Stream<TestData> provideTestVectorsForPositiveSign() {
    return Stream.of(
        new TestData(
            new Complex(6, 4), // test vectors for transformation
            new Complex(2.27872385417085, 0.438842116902255), 1),

        new TestData(
            new Complex(-2, 3), // test vectors for transformation
            new Complex(0.284848784593141, 1.755317301824428), 1),

        new TestData(
            new Complex(1, 2), // test vectors for transformation
            new Complex(0.0, 0.0), 1),

        new TestData(
            new Complex(0.5, 0.5),
            new Complex(0.735234258615643, -1.020083043208784), 1),

        new TestData(
            new Complex(-6, 2),
            new Complex(0.0, 2.645751311064591), 1)
    );
  }


  // A list of the expected values for the test vectors when the sign is negative (-1 * sqrt(z-c)).
  private static Stream<TestData> provideTestVectorsForNegativeSign() {
    return Stream.of(
        new TestData(
            new Complex(6, 4),
            new Complex(-2.27872385417085, -0.438842116902255), -1),

        new TestData(
            new Complex(-2, 3),
            new Complex(-0.284848784593141, -1.755317301824428), -1),

        new TestData(
            new Complex(1, 2),
            new Complex(0.0, 0.0), -1),

        new TestData(
            new Complex(0.5, 0.5),
            new Complex(-0.735234258615643, 1.020083043208784), -1),

        new TestData(
            new Complex(-6, 2),
            new Complex(0.0, -2.645751311064591), -1)
    );
  }



  @ParameterizedTest
  @MethodSource("provideTestVectorsForPositiveSign")
  void transformTestSignPositive(TestData data) {
    performTest(data);
  }

  @ParameterizedTest
  @MethodSource("provideTestVectorsForNegativeSign")
  void transformTestSignNegative(TestData data) {
    performTest(data);
  }

  private void performTest(TestData data) {
    JuliaTransform juliaTransform = new JuliaTransform(new Complex(1, 2), data.sign);
    Vector2D transformedVector = juliaTransform.transform(data.input);


    assertEquals(data.expected.getX0(), transformedVector.getX0(), TOLERANCE,
        "Real part of the transformation is not as expected.");

    assertEquals(data.expected.getX1(), transformedVector.getX1(), TOLERANCE,
        "Imaginary part of the transformation is not as expected.");
  }


  static class TestData {
    Vector2D input;
    Vector2D expected;
    int sign;

    TestData(Vector2D input, Vector2D expected, int sign) {
      this.input = input;
      this.expected = expected;
      this.sign = sign;
    }
  }


  @Test
  void setSign_InvalidSign_ShouldThrowIllegalArgumentException() {
    Complex point = new Complex(0.0, 0.0);

    Assertions.assertThrows(IllegalArgumentException.class, () -> new JuliaTransform(point, 0),
        "Constructor should throw IllegalArgumentException for invalid sign values.");
  }
}