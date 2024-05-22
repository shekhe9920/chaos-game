package edu.ntnu.stud.idatg2003.commonutilities.errorutilitie;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


/**
 * This class contains tests that ensure the ErrorHandler class correctly logs and handles exceptions.
 * It verifies that ErrorHandler distinguishes between different types of exceptions and treats them
 * appropriately, logging them and displaying error messages via a GUI.
 */
class ErrorHandlerTest {


  @BeforeAll
  public static void setup() {
    new JFXPanel();  // JavaFX toolkit initialization
  }




  /**
   * This test ensures that when a CustomIoException is passed to the ErrorHandler,
   * it logs the exception with an appropriate level of severity and a specific message format.
   * The behavior is verified by mocking the Logger and confirming that it is called correctly.
   */
  @Test
  void testHandleExceptionWithCustomIoException() {
    try (MockedStatic<Logger> mocked = Mockito.mockStatic(Logger.class)) {
      Logger mockLogger = mock(Logger.class);

      mocked.when(() -> Logger.getLogger(anyString())).thenReturn(mockLogger); // mock the Logger.getLogger() method to return the mockLogger
      when(mockLogger.isLoggable(Level.SEVERE)).thenReturn(true); // Logger is set to log severe messages

      Exception ex =   // Custom I/O exception with a specific message
          new CustomExceptionUtil.CustomIoException("Failed to read file", new IOException("Disk error"));
      ErrorHandler.handleException(ex, "testLogger");

      // verifying that the logger was called with the expected parameters
      verify(mockLogger).log(eq(Level.SEVERE), contains("I/O Error: Failed to read file"), eq(ex));
    }
  }



  /**
   * This test verifies that general exceptions are also logged appropriately,
   * with a clear and informative message. This ensures that unexpected errors are
   * not only logged but are done so in a way that aids debugging and user feedback.
   */
  @Test
  void testHandleExceptionWithGeneralException() {
    String loggerName = "testLogger";
    Exception ex = new Exception("General failure");

    // Mock the Logger class and verify that the log method is called with the expected parameters
    try (MockedStatic<Logger> mockedLogger = Mockito.mockStatic(Logger.class)) {
      Logger mockLogger = mock(Logger.class);

      mockedLogger.when(() -> Logger.getLogger(loggerName)).thenReturn(mockLogger);
      when(mockLogger.isLoggable(Level.SEVERE)).thenReturn(true);

      ErrorHandler.handleException(ex, loggerName);  // calling the method under test

      verify(mockLogger).log(eq(Level.SEVERE), contains("Unexpected Error: General failure"), eq(ex));
    }
  }

}