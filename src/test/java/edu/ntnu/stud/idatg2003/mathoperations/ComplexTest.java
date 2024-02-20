package edu.ntnu.stud.idatg2003.mathoperations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The {@code ComplexTest} class contains unit tests for the {@code Complex} class,
 * which represents a complex number with real and imaginary parts. The tests verify that the
 * multiplication and square root of complex numbers are performed correctly.
 *
 * @version 0.0.0
 * @since 0.0.0
 */
class ComplexTest {

  private Complex complexTest1;
  private Complex complexTest2;
  private Vector2D vectorTest;


  @BeforeEach
  void setUp() {
    complexTest1 = new Complex(1, 1);
    complexTest2 = new Complex(2, 2);
    vectorTest = new Vector2D(1, 2);
  }




  /**
   * Test the positive case of turning a Vector2D object into a Complex object.
   */
  @Test
  void VectorToComplexPositiveTest() {

    // Arrange
    Complex complexTest = new Complex(0.0, 0.0);

    // Act
    Complex vectorToComplex = complexTest.toComplex(vectorTest);

    // Assert
    assertEquals(Complex.class, vectorToComplex.getClass());

  }

  /**
   * Test the negative case of turning a Vector2D object into a Complex object.
   */
  @Test
  void VectorToComplexNegativeTest() {

    // Arrange
     Complex complexTest = new Complex(0.0, 0.0);

    // Act
    Complex vectorToComplex = complexTest.toComplex(complexTest2);

    // Assert
    assertEquals(Complex.class, vectorToComplex.getClass());
  }






  /**
   * Test the positive case of calculating the multiplication of two complex numbers.
   * Verifies that the method correctly returns the result of the multiplication.
   */
  @Test
  void multiplyPositiveTest() {

    // (1+1i) * (2+2i) = (1 * 2 - 1 * 2) + (1 * 2 + 1 * 2) * i = 0 + 4i = 4i
    Complex newComplexTest = complexTest1.multiply(complexTest2);

    assertEquals(0, newComplexTest.getX0());
    assertEquals(4, newComplexTest.getX1());

    System.out.println("The result of the multiplication is: "
        + newComplexTest.getX0() + " ± " + newComplexTest.getX1() + "i");
  }


  /**
   * Test the negative case of calculating the multiplication of two complex numbers.
   * Verifies that the method correctly returns the result of the multiplication.
   */
  @Test
  void multiplyNegativeTest() {

    Complex complex = new Complex(1, 1);

    assertThrows(IllegalArgumentException.class, () -> {
      complex.multiply(null);});
    System.out.println("IllegalArgumentException thrown, other complex number cannot be null");

  }





  /**
   * Test the positive case of calculating the square root of a complex number.
   * Verifies that the method correctly returns the square root of a complex number.
   */
  @Test
  void squareRootCalculationTest() {
    Complex sqrtComplexTest = complexTest1.sqrt();
    double expectedRealPart = 1.09868411346781;
    double expectedImaginaryPart = 0.4550898605622274;

    double actualRealPart = sqrtComplexTest.getX0();
    double actualImaginaryPart = sqrtComplexTest.getX1();

    // The square root of (1 + i) is approximately: 1.09868411346781 + 0.4550898605622274i
    assertEquals(expectedRealPart + "+" + expectedImaginaryPart,
        actualRealPart + "+" +  actualImaginaryPart,
        "CALCULATION ERROR: The result of the square root is not as expected");

    System.out.println("The result of the square root is: "
        + sqrtComplexTest.getX0() + " ± " + sqrtComplexTest.getX1() + "i");
  }
}