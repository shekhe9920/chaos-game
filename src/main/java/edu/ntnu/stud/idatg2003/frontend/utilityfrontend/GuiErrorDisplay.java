package edu.ntnu.stud.idatg2003.frontend.utilityfrontend;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Provides utility functions for displaying error messages in a GUI using alert dialog boxes.
 * This class simplifies the process of showing errors to the user in a standardized format
 * across an application.
 *
 * @version 0.0.1
 * @since 0.1.0
 */
public class GuiErrorDisplay {


  GuiErrorDisplay() {
    // private constructor to prevent instantiation
  }

  /**
   * Displays an error message in a GUI dialog box.
   * This method ensures that the alert is displayed on the JavaFX application thread.
   *
   * @param message The error message to be displayed to the user.
   * @since 0.1.0
   */
  public static void showError(String message) {

    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText(null);  // no header text
      alert.setContentText(message);  // the actual error message
      alert.showAndWait();  // blocks execution until the dialog is dismissed
    });


  }



}