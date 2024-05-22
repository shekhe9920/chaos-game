package edu.ntnu.stud.idatg2003.commonutilities.errorutilitie;


/**
 * Provides a collection of custom exceptions to handle various types of errors
 * throughout the application.
 * This utility class defines specialized exceptions for I/O operations,
 * database access, and configuration issues.
 *
 * @version 0.0.1
 * @since 0.1.0 (The version of Chaos-Game application when introduced)
 */
public class CustomExceptionUtil {



  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  CustomExceptionUtil() {
      // This constructor is intentionally empty. Nothing special is needed here.
  }


  /**
   * Static inner class for handling custom I/O exceptions.
   *
   * @since 0.0.1
   */
  public static class CustomIoException extends RuntimeException {




    /**
     * Custom unchecked exception for handling I/O related errors.
     * This exception is thrown when an I/O operation fails,
     * indicating that the error should be handled at runtime.
     *
     * @param message Detailed message about the error cause.
     * @param cause The underlying cause of the exception.
     * @since 0.0.1
     */
    public CustomIoException(String message, Throwable cause) {
      super(message, cause);
    }


  }




}
