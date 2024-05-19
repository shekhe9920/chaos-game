package edu.ntnu.stud.idatg2003.frontend.view;

import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToDouble;

import edu.ntnu.stud.idatg2003.backend.ChaosGameObserver;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.frontend.controllers.ChaosGameController;
import edu.ntnu.stud.idatg2003.frontend.controllers.MainViewController;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Optional;



/**
 * Abstract base class for all fractal views in the application.
 * Provides common functionality for handling canvas, menus, and settings.
 *
 * @version 0.0.6
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public abstract class FractalView extends BorderPane implements ChaosGameObserver {

  protected TitledPane settingsPane;            // The settings pane
  protected ChaosGameController controller;     // The controller for handling fractal logic
  protected Canvas canvas;                      // The canvas for drawing the fractal
  protected MenuBar menuBar;                    // The menu bar for the application

  protected TextField minXField;                // The text field for the minimum x coordinate
  protected TextField minYField;                // The text field for the minimum y coordinate
  protected TextField maxXField;                // The text field for the maximum x coordinate
  protected TextField maxYField;                // The text field for the maximum y coordinate
  protected TextField stepsField;               // The text field for the number of steps

  protected static final int DEFAULT_STEPS = 100000;  // The default number of steps for the fractal





  /**
   * Constructor for FractalView.
   *
   * @param controller the controller to handle fractal logic
   * @param width      the width of the canvas
   * @param height     the height of the canvas
   * @since 0.0.1
   */
  protected FractalView(ChaosGameController controller, int width, int height) {
    this.controller = controller;   // setting the controller
    controller.addObserver(this);   // adding this view as an observer
    this.canvas = new Canvas(width, height);    // setting the canvas
    setupCanvas();  // setting up the canvas
    initializeCoordinateFields();  // initializing the coordinate fields
    setupUI();  // setting up the user interface
  }




  /**
   * Sets up the user interface including the menu bar and settings pane.
   *
   * @since 0.0.1
   */
  protected void setupUI() {
    setupMenuBar();
    setupSettingsPane();
  }





  /**
   * Sets up the canvas for drawing the fractal.
   *
   * @since 0.0.1
   */
  protected void setupCanvas() {
    setCenter(canvas);
  }


  /**
   * Gets the canvas width.
   *
   * @return the width of the canvas
   * @since 0.0.6
   */
  protected int getCanvasWidth() {
    return (int) canvas.getWidth();
  }


  /**
   * Gets the canvas height.
   *
   * @return the height of the canvas
   * @since 0.0.6
   */
  protected int getCanvasHeight() {
    return (int) canvas.getHeight();
  }






  /**
   * Gets the canvas coordinates based on the given element.
   *
   * @param element the element to get the canvas coordinates. (minX, minY, maxX, maxY)
   * @return the canvas coordinates for the element
   * @since 0.0.6
   */
  protected double getCanvasCoords(String element) {
    try {
      return switch (element) {
        case "minX" -> parseTextFieldToDouble(minXField);  // parsing the text field to double
        case "minY" -> parseTextFieldToDouble(minYField);
        case "maxX" -> parseTextFieldToDouble(maxXField);
        case "maxY" -> parseTextFieldToDouble(maxYField);
        default -> throw new IllegalArgumentException("Invalid canvas indices: " + element);
      };
    } catch (NumberFormatException e) {
      System.err.println("Invalid canvas coordinate value: " + element);
      return 0.0; // default value
    }
  }




  /**
   * Draws the initial fractal on the canvas.
   *
   * @param width  the width of the canvas
   * @param height the height of the canvas
   * @since 0.0.1
   */
  protected abstract void drawInitialFractal(int width, int height);




  /**
   * Sets up the menu bar with home, file, and settings menus.
   *
   * @since 0.0.1
   */
  private void setupMenuBar() {
    menuBar = new MenuBar();
    Menu homeMenu = new Menu("Home");

    MenuItem goBackHomeItem = new MenuItem("Go back home");
    goBackHomeItem.setOnAction(e -> MainViewController.getInstance().switchToHomeView());

    // Initialize the switch fractal item with dynamic text
    MenuItem switchFractalItem =
        new MenuItem(MainViewController.getInstance().getCurrentViewType());

    switchFractalItem.setOnAction(e -> {
      MainViewController.getInstance().openOtherFractalWindow();
      switchFractalItem.setText(
          MainViewController.getInstance().getCurrentViewType());  // Update text after switch
    });

    Menu fileMenu = new Menu("File");

    Menu settingsMenu = new Menu("Settings");
    MenuItem resizeCanvasItem = new MenuItem("Resize Canvas");
    resizeCanvasItem.setOnAction(e -> showResizeCanvasDialog());

    settingsMenu.getItems().add(resizeCanvasItem);
    homeMenu.getItems().addAll(goBackHomeItem, switchFractalItem);
    fileMenu.getItems().addAll(createOpenConfigMenuItem(), createSaveConfigMenuItem());

    menuBar.getMenus().addAll(homeMenu, fileMenu, settingsMenu);
    setTop(menuBar);
  }




  /**
   * Draws the fractal on the canvas based on the current hit counts.
   *
   * @param canvas the canvas to draw on
   * @since 0.0.1
   */
  protected abstract void drawFractal(Canvas canvas);





  /**
   * Gets a color based on the intensity value.
   *
   * @param intensity the intensity value
   * @return the color for the intensity
   * @since 0.0.6
   */
  protected Color getColorForIntensity(double intensity) {
    // Using HSV to RGB conversion for a smooth transition from blue to red
    return Color.hsb(240 * (1 - intensity), 1.0, 1.0);
  }




  /**
   * Updates the UI elements based on the given chaos game description.
   *
   * @param description the chaos game description
   * @since 0.0.1
   */
  protected abstract void updateUIWithDescription(ChaosGameDescription description);




  /**
   * Creates a menu item for opening a configuration file.
   *
   * @return the menu item for opening a configuration file
   * @since 0.0.1
   */
  protected abstract MenuItem createOpenConfigMenuItem();




  /**
   * Creates a menu item for saving a configuration file.
   *
   * @return the menu item for saving a configuration file
   * @since 0.0.1
   */
  protected abstract MenuItem createSaveConfigMenuItem();

  /**
   * Sets up the settings pane with fractal options.
   *
   * @since 0.0.1
   */
  protected abstract void setupSettingsPane();

  /**
   * Shows a dialog for selecting predefined fractals.
   *
   * @since 0.0.1
   */
  protected abstract void showPredefinedFractalDialog();

  /**
   * Updates the fractal based on the current settings.
   *
   * @since 0.0.1
   */
  protected abstract void updateFractal();




  /**
   * When the chaos game is updated, redraw the fractal on the canvas.
   *
   * @since 0.0.1
   */
  @Override
  public void onChaosGameUpdated() {
    Platform.runLater(() -> drawFractal(canvas));
  }




  /**
   * When the chaos game description is changed, update the UI with the new description.
   *
   * @param newDescription the new chaos game description
   * @since 0.0.1
   */
  @Override
  public void onChaosDescriptionChanged(ChaosGameDescription newDescription) {
    Platform.runLater(() -> updateUIWithDescription(newDescription));
  }




  /**
   * Initializes the coordinate fields with default values.
   *
   * @since 0.0.1
   */
  protected void initializeCoordinateFields() {
    minXField = new TextField("0.0");
    minYField = new TextField("0.0");
    maxXField = new TextField("1.0");
    maxYField = new TextField("1.0");
    stepsField = new TextField("100000");
  }





  /**
   * Sets up the steps field in the settings box.
   *
   * @param settingsBox the settings box
   * @since 0.0.1
   */
  protected void setupStepsField(VBox settingsBox) {
    stepsField = new TextField("100000"); // default steps value
    HBox stepsBox = new HBox(new Label("Steps:"), stepsField);
    stepsBox.setAlignment(Pos.CENTER_LEFT);

    // adding listener to update the game when step value changes
    stepsField.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        int steps = Integer.parseInt(newValue);
        controller.updateGame(steps,
            new Vector2D(parseTextFieldToDouble(minXField), parseTextFieldToDouble(minYField)),
            new Vector2D(parseTextFieldToDouble(maxXField), parseTextFieldToDouble(maxYField)),
            controller.getCurrentGameDescription().getTransformations());
        System.out.println("Steps updated to: " + steps);
      } catch (NumberFormatException e) {
        System.err.println("Invalid steps value: " + newValue);
      }
    });

    settingsBox.getChildren().add(stepsBox);
  }




  /**
   * Sets up the coordinate fields in the settings box.
   *
   * @param settingsBox the settings box
   * @since 0.0.3
   */
  protected void setupCoordinateFields(VBox settingsBox) {
    settingsBox
        .setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1;");

    Label coordinatesTitle = new Label("Fractal Canvas Coordinates");
    coordinatesTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10;");

    VBox coordinatesContainer = new VBox();
    coordinatesContainer.setSpacing(10);
    coordinatesContainer
        .setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1; -fx-background-color: #f8f8f8;");

    minXField = new TextField("0.0");
    minYField = new TextField("0.0");
    maxXField = new TextField("1.0");
    maxYField = new TextField("1.0");

    HBox minCoordsBox = new HBox(10);
    HBox maxCoordsBox = new HBox(10);
    minCoordsBox.getChildren().addAll(new Label("Min X:"), minXField, new Label("Min Y:"), minYField);
    maxCoordsBox.getChildren().addAll(new Label("Max X:"), maxXField, new Label("Max Y:"), maxYField);

    minXField.textProperty().addListener((observable, oldValue, newValue) -> updateFractal());
    minYField.textProperty().addListener((observable, oldValue, newValue) -> updateFractal());
    maxXField.textProperty().addListener((observable, oldValue, newValue) -> updateFractal());
    maxYField.textProperty().addListener((observable, oldValue, newValue) -> updateFractal());

    coordinatesContainer.getChildren().addAll(minCoordsBox, maxCoordsBox);
    settingsBox.getChildren().addAll(coordinatesTitle, coordinatesContainer);
  }




  /**
   * Updates the size of the canvas.
   *
   * @param width  the new width of the canvas
   * @param height the new height of the canvas
   * @since 0.0.6
   */
  public void updateCanvasSize(int width, int height) {
    canvas.setWidth(width);
    canvas.setHeight(height);
    setupCanvas();
    updateFractal();
  }




  /**
   * Shows a dialog for resizing the canvas.
   *
   * @since 0.0.6
   */
  public void showResizeCanvasDialog() {
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Resize Canvas");
    dialog.setHeaderText("Enter new dimensions for the canvas:");

    ButtonType resizeButtonType = new ButtonType("Resize", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(resizeButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField widthField = new TextField(String.valueOf((int) canvas.getWidth()));
    TextField heightField = new TextField(String.valueOf((int) canvas.getHeight()));
    grid.add(new Label("Width:"), 0, 0);
    grid.add(widthField, 1, 0);
    grid.add(new Label("Height:"), 0, 1);
    grid.add(heightField, 1, 1);

    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == resizeButtonType) {
        return new Pair<>(widthField.getText(), heightField.getText());
      }
      return null;
    });

    Optional<Pair<String, String>> result = dialog.showAndWait();
    result.ifPresent(widthHeight -> {
      int width = Integer.parseInt(widthHeight.getKey());
      int height = Integer.parseInt(widthHeight.getValue());
      updateCanvasSize(width, height);
    });
  }




  /**
   * Loads the coordinates from the given description into the UI fields.
   *
   * @param description the chaos game description
   * @since 0.0.4
   */
  protected void loadCoordinatesToUI(ChaosGameDescription description) {
    if (description != null) {
      minXField.setText(String.valueOf(description.getMinCoords().getX0()));
      minYField.setText(String.valueOf(description.getMinCoords().getX1()));
      maxXField.setText(String.valueOf(description.getMaxCoords().getX0()));
      maxYField.setText(String.valueOf(description.getMaxCoords().getX1()));
    }
  }




  /**
   * Shows a dialog for editing the transformation selection probability.
   *
   * @since 0.0.6
   */
  protected void showEditWeightsDialog() {
    if (controller.getCurrentGameDescription().getTransformations().isEmpty() ||
        !(controller.getCurrentGameDescription().getTransformations().getFirst() instanceof AffineTransform2D)) {
      System.err.println("Weights are only applicable to affine transformations.");
      return;
    }

    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Edit Transformation Selection Probability");
    dialog.setHeaderText("Edit the weights for each transformation:");

    ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

    VBox weightFieldsBox = new VBox(10);
    List<TextField> weightFields = new ArrayList<>();
    List<Double> weights = controller.getGame().getWeights();

    if (weights == null || weights.isEmpty()) {
      System.err.println("Failed to set weights: Weights cannot be null or empty");
      return;
    }

    for (int i = 0; i < weights.size(); i++) {
      TextField weightField = new TextField(String.format("%.2f", weights.get(i) * 100));
      weightFields.add(weightField);
      HBox hBox = new HBox(10, new Label("Weight " + (i + 1) + ":"), weightField);
      weightFieldsBox.getChildren().add(hBox);
    }

    dialog.getDialogPane().setContent(weightFieldsBox);

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == applyButtonType) {
        for (int i = 0; i < weightFields.size(); i++) {
          try {
            double weight = Double.parseDouble(weightFields.get(i).getText()) / 100.0;
            weights.set(i, weight);
          } catch (NumberFormatException e) {
            System.err.println("Invalid weight value: " + weightFields.get(i).getText());
          }
        }
        normalizeWeights(weights);
        controller.setTransformWeights(weights); // saving the new weights
        updateFractal();
        System.out.println("Updated weights: " + weights);
      }
      return null;
    });

    dialog.showAndWait();
  }



  /**
   * Normalizes the weights to sum up to 1.
   *
   * @param weights the list of weights
   * @since 0.0.6
   */
  private void normalizeWeights(List<Double> weights) {
    double total = weights.stream().mapToDouble(Double::doubleValue).sum();
    weights.replaceAll(aDouble -> aDouble / total);
  }


  /**
   * Checks if the UI elements are initialized.
   *
   * @param objects the objects to check
   * @since 0.0.6
   */
  protected void checkNotNull(Object... objects) {
    for (Object obj : objects) {
      if (obj == null) {
        throw new IllegalStateException("UI elements are not initialized");
      }
    }
  }

}
