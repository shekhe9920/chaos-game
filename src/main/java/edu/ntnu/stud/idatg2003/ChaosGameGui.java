package edu.ntnu.stud.idatg2003;

import edu.ntnu.stud.idatg2003.frontend.controllers.MainViewController;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main class for launching the Chaos Game GUI application.
 * This class extends JavaFX's Application class and sets up the primary stage and event handlers.
 *
 * @version 0.0.5
 * @since 0.0.2 (The version of Chaos-Game application when introduced)
 */
public class ChaosGameGui extends Application {



  /**
   * Handles the exit attempt when the user tries to close the application.
   * Displays a confirmation dialog to prevent accidental closure.
   *
   * @param event the WindowEvent triggered when attempting to close the window
   * @since 0.0.3
   */
  private void handleExitAttempt(WindowEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm Exit");
    alert.setHeaderText("Are you sure you want to close ChaosGame?");
    alert.setContentText("Any unsaved changes will be lost.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() != ButtonType.OK) {
      event.consume(); // Prevent the window from closing if the user cancels
    } else {
      Platform.exit(); // Closes the application if the user confirms
    }
  }




  /**
   * The main entry point for the JavaFX application.
   * Initializes the primary stage and sets up the main view controller.
   *
   * @param primaryStage the primary stage for this application
   * @since 0.0.1
   */
  @Override
  public void start(Stage primaryStage) {
    // Initiate MainViewController and set up the primary stage
    MainViewController mainViewController = MainViewController.getInstance(); // Singleton instance
    mainViewController.init(primaryStage);

    // Set the on-close request handler to confirm exit attempts
    primaryStage.setOnCloseRequest(this::handleExitAttempt);
    primaryStage.show(); // Show the primary stage
  }




  /**
   * The main method to launch the JavaFX application.
   *
   * @param args the command line arguments
   * @since 0.0.1
   */
  public static void main(String[] args) {
    launch(args); // Launch the JavaFX application
  }
}
