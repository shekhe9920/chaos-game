package edu.ntnu.stud.idatg2003.commonutilities.errorutilitie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for the CustomExceptionUtility class.
 * This class contains tests that verify the correct behavior of custom exceptions defined within
 * the CustomExceptionUtility class. Specifically, it ensures that the CustomIoException behaves as
 * expected when thrown, focusing on its message and cause propagation functionalities.
 */
class CustomExceptionUtilTest {

  /**
   * This test confirms that the exception message and cause are properly initialized and can be
   * retrieved using getMessage() and getCause() methods, respectively. This is important for
   * ensuring that error handling mechanisms that depend on these properties function correctly.
   */
  @Test
  void testCustomIoExceptionMessage() {
    Exception cause = new RuntimeException("Cause");

    CustomExceptionUtil.CustomIoException exception
        = new CustomExceptionUtil.CustomIoException("Test message", cause);

    assertEquals("Test message", exception.getMessage(),
        "The message should match the constructor input.");

    assertEquals(cause, exception.getCause(),
        "The cause should be the one passed to the constructor.");
  }

}