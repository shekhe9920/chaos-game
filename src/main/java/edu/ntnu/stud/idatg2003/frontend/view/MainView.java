package edu.ntnu.stud.idatg2003.frontend.view;

import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.ActionHandlerUtil.setButtonAction;

import edu.ntnu.stud.idatg2003.frontend.controllers.MainViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


/**
 * The {@code MainView} class represents the main view of the application.
 * It provides buttons to navigate to different views and to exit the application.
 *
 * @version 0.0.4
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class MainView extends BorderPane {


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
    // Button for affine transformations
    Button affineTransformButton = new Button("Affine Transformations");
    // Button for Julia set
    Button juliaSetButton = new Button("Julia Set");
    // Button to exit the application
    Button exitButton = new Button("Exit");


    // Styling buttons
    styleButton(affineTransformButton);
    styleButton(juliaSetButton);
    styleButton(exitButton);


    // Setting button actions using the util class
    setButtonAction(affineTransformButton, controller::switchToAffineTransformationView);
    setButtonAction(juliaSetButton, controller::switchToJuliaSetView);
    setButtonAction(exitButton, controller::handleExit);


    // Button arrangements in a vertical box:
    VBox buttonBox = new VBox(10); // 10 is the spacing between buttons
    buttonBox.getChildren().addAll(affineTransformButton, juliaSetButton, exitButton);
    buttonBox.setAlignment(Pos.CENTER); // aligning the buttons to the center

    buttonBox.setStyle("-fx-padding: 10;");

    this.setCenter(buttonBox);
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



}
