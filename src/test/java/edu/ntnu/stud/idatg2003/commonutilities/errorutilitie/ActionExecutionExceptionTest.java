package edu.ntnu.stud.idatg2003.commonutilities.errorutilitie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ActionExecutionException}.
 * This class contains tests for verifying the functionality of the ActionExecutionException class,
 * including both the message and cause.
 */
class ActionExecutionExceptionTest {



  /**
   * Tests the constructor of ActionExecutionException with a specified message and cause.
   */
  @Test
  void testConstructor_MessageAndCause() {
    String errorMessage = "An error occurred during action execution.";
    Throwable cause = new Throwable("Root cause of the error");

    ActionExecutionException exception = new ActionExecutionException(errorMessage, cause);

    assertNotNull(exception, "Exception should be instantiated");
    assertEquals(errorMessage, exception.getMessage(), "Error message should match the input message");
    assertEquals(cause, exception.getCause(), "Cause should match the input cause");
  }





  /**
   * Tests the constructor of ActionExecutionException with a null cause.
   */
  @Test
  void testConstructor_NullCause() {
    String errorMessage = "An error occurred during action execution.";

    ActionExecutionException exception = new ActionExecutionException(errorMessage, null);

    assertNotNull(exception, "Exception should be instantiated");
    assertEquals(errorMessage, exception.getMessage(), "Error message should match the input message");
    assertNull(exception.getCause(), "Cause should be null");
  }





  /**
   * Tests the constructor of ActionExecutionException with a null message and null cause.
   */
  @Test
  void testConstructor_NullMessageAndCause() {
    ActionExecutionException exception = new ActionExecutionException(null, null);

    assertNotNull(exception, "Exception should be instantiated");
    assertNull(exception.getMessage(), "Error message should be null");
    assertNull(exception.getCause(), "Cause should be null");
  }


}
