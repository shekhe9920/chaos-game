package edu.ntnu.stud.idatg2003.mathoperations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplexTest {

  Complex complexTest1 = new Complex(1, 1);
  Complex complexTest2 = new Complex(2, 2);
  @BeforeEach
  void setUp() {

  }

  @AfterEach
  void tearDown() {
  }







  /**
   * Test the positive case of turning a Vector2D object into a Complex object.
   */
  @Test
  void VectorToComplexPositiveTest() {

    // Arrange
    Complex complexTest = new Complex(0.0, 0.0);
    Vector2D vectorTest1 = new Vector2D(1, 2);

    // Act
    Complex vectorToComplex = complexTest.toComplex(vectorTest1);

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
     Complex complexTest2 = new Complex(1, 2);

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

  }


  /**
   * Test the positive case of calculating the square root of a complex number.
   * Verifies that the method correctly returns the square root of a complex number.
   */
  @Test
  void sqrt() {
    Complex sqrtComplexTest = complexTest1.sqrt();

    // The square root of (1 + i) is approximately: 1.09868411346781 + 0.4550898605622274i
    assertEquals(1.09868411346781, sqrtComplexTest.getX0());
    assertEquals(0.4550898605622274, sqrtComplexTest.getX1());

    assertEquals(1.09868411346781 + "+" + 0.4550898605622274,
        sqrtComplexTest.getX0() + "+" +  sqrtComplexTest.getX1(),
        "The result of the square root is not as expected");

    System.out.println("The result of the square root is: "
        + sqrtComplexTest.getX0() + " ± " + sqrtComplexTest.getX1() + "i");
  }
}