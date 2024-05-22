package edu.ntnu.stud.idatg2003.commonutilities.errorutilitie;

import edu.ntnu.stud.idatg2003.commonutilities.errorutilitie.CustomExceptionUtil.CustomIoException;
import edu.ntnu.stud.idatg2003.frontend.utilityfrontend.GuiErrorDisplay;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a centralized method to handle exceptions by logging them and
 * displaying an error message on the GUI.
 * This class differentiates between exception types to provide appropriate error messages tailored
 * to the issue.
 *
 * @version 0.0.1
 * @since 0.1.0 (The version of Chaos-Game application when introduced)
 */
public class ErrorHandler {

  public static final String GAME_NOT_INITIALIZED = "Chaos game has not been initialized yet.";




  ErrorHandler() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }




  /**
   * Handles exceptions by logging them and displaying an
   * error message in the graphical user interface.
   * Differentiates handling based on the type of exception to provide
   * contextual feedback to the user.
   *
   * @param e The exception to handle.
   * @param loggerName The name of the logger for logging the exception.
   * @since 0.1.0
   */
  public static void handleException(Exception e, String loggerName) {
    Logger logger = Logger.getLogger(loggerName);
    if (logger.isLoggable(Level.SEVERE)) {
      String message = constructLogMessage(e);
      logger.log(Level.SEVERE, message, e);
      GuiErrorDisplay.showError(message);
    }
  }




  /**
   * Constructs a detailed log message based on the type of exception.
   * Uses conditional checks to differentiate between custom exceptions and
   * provide specific error descriptions.
   *
   * @param e The exception from which to construct the message.
   * @return String The constructed error message appropriate for logging.
   * @since 0.1.0
   */
  private static String constructLogMessage(Exception e) {
    if (e == null) {
      return "Error: Exception was null";

    } else if (e instanceof CustomIoException) {
      return "I/O Error: " + e.getMessage();

    } else {
      return "Unexpected Error: " + e.getMessage();
    }


  }

}
