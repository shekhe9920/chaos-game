package edu.ntnu.stud.idatg2003.commonutilities.errorutilitie;

/**
 * Custom exception for handling action execution errors.
 * This exception is thrown when an action fails to execute properly.
 *
 * @version 0.0.1
 * @since 0.1.0
 */
public class ActionExecutionException extends RuntimeException {




  /**
   * Constructs a new {@code ActionExecutionException} with the specified detail message and cause.
   *
   * @param message The detail message.
   * @param cause The cause of the exception.
   * @since 0.1.0
   */
  public ActionExecutionException(String message, Throwable cause) {
    super(message, cause);
  }



}
