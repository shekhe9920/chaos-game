package edu.ntnu.stud.idatg2003.frontend.view;

import edu.ntnu.stud.idatg2003.frontend.controllers.MainViewController;
import edu.ntnu.stud.idatg2003.frontend.utilityfrontend.Action;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

/**
 * The {@code MainView} class represents the main view of the application.
 * It provides buttons to navigate to different views and to exit the application.
 *
 * @version 0.0.4
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class MainView extends BorderPane {


  private final Button affineTransformButton;  // Button for affine transformations
  private final Button juliaSetButton;         // Button for Julia set
  private final Button exitButton;             // Button to exit the application




  /**
   * Constructs a new {@code MainView} object and initializes the UI components.
   *
   * @param controller The main view controller for handling actions.
   * @since 0.0.1
   */
  public MainView(MainViewController controller) {
    // Style for the root pane:
    BorderPane root = new BorderPane();
    root.setStyle("-fx-background-color: #333;"); // Set a dark background
    this.setStyle("-fx-background-color: #333;");

    // Buttons Initializations:
    affineTransformButton = new Button("Affine Transformations");
    juliaSetButton = new Button("Julia Set");
    exitButton = new Button("Exit");

    // Styling buttons
    styleButton(affineTransformButton);
    styleButton(juliaSetButton);
    styleButton(exitButton);

    // Setting button actions using the buttonAction method
    buttonAction(affineTransformButton, controller::switchToAffineTransformationView);
    buttonAction(juliaSetButton, controller::switchToJuliaSetView);
    buttonAction(exitButton, controller::handleExit);

    // Button arrangements in a vertical box:
    VBox buttonBox = new VBox(10); // 10 is the spacing between buttons
    buttonBox.getChildren().addAll(affineTransformButton, juliaSetButton, exitButton);
    buttonBox.setAlignment(Pos.CENTER); // aligning the buttons to the center

    buttonBox.setStyle("-fx-padding: 10;");

    this.setCenter(buttonBox);
  }




  /**
   * Sets the enabled/disabled state of all buttons.
   *
   * @param disabled True to disable the buttons, false to enable them.
   * @since 0.0.1
   */
  public void setButtonsDisabled(boolean disabled) {
    affineTransformButton.setDisable(disabled);
    juliaSetButton.setDisable(disabled);
    exitButton.setDisable(disabled);
  }




  /**
   * Styles the given button with a consistent look and feel.
   *
   * @param button The button to style.
   * @since 0.0.3
   */
  private void styleButton(Button button) {
    button.setStyle(
        "-fx-background-color: #666; -fx-text-fill: #eee; -fx-font-size: 16px; -fx-padding: 10 20;"
    );

    button.setMinWidth(150); // the minimum width of the button
  }




  /**
   * Sets the action for the button.
   *
   * @param button The button to set the action for.
   * @param action The action to perform when the button is pressed.
   * @since 0.0.4
   */
  private void buttonAction(Button button, Action action) {
    button.setOnAction(e -> {
      try {
        action.perform();
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    });
  }
}
