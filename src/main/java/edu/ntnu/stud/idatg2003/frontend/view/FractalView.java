package edu.ntnu.stud.idatg2003.frontend.view;

import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToDouble;

import edu.ntnu.stud.idatg2003.backend.ChaosGameObserver;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.frontend.controllers.ChaosGameController;
import edu.ntnu.stud.idatg2003.frontend.controllers.MainViewController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

  protected TitledPane settingsPane;
  protected ChaosGameController controller;
  protected Canvas canvas;
  protected MenuBar menuBar;

  protected TextField minXField;
  protected TextField minYField;
  protected TextField maxXField;
  protected TextField maxYField;
  protected TextField stepsField;

  protected static final int DEFAULT_STEPS = 100000;

  /**
   * Constructor for FractalView.
   *
   * @param controller the controller to handle fractal logic
   * @param width      the width of the canvas
   * @param height     the height of the canvas
   * @since 0.0.1
   */
  protected FractalView(ChaosGameController controller, int width, int height) {
    this.controller = controller;
    controller.addObserver(this);
    this.canvas = new Canvas(width, height);
    setupCanvas(width, height);
    setupUI();
    initializeCoordinateFields();
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
   * @param width  the width of the canvas
   * @param height the height of the canvas
   * @since 0.0.1
   */
  private void setupCanvas(int width, int height) {
    setCenter(canvas);
    drawInitialFractal(width, height);
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
    MenuItem switchFractalItem = new MenuItem(MainViewController.getInstance().getCurrentViewType());
    switchFractalItem.setOnAction(e -> {
      MainViewController.getInstance().openOtherFractalWindow();
      switchFractalItem.setText(MainViewController.getInstance().getCurrentViewType());  // Update text after switch
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
  protected void drawFractal(Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    if (gc == null) {
      throw new IllegalStateException("GraphicsContext not available");
    }

    int[][] hitCounts = controller.getGame().getCanvas().getHitCounts();
    int maxHits = getMaxHits(hitCounts);

    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    for (int i = 0; i < hitCounts.length; i++) {
      for (int j = 0; j < hitCounts[i].length; j++) {
        if (hitCounts[i][j] > 0) {
          double intensity = (double) hitCounts[i][j] / maxHits;
          gc.setFill(Color.gray(intensity));
          gc.fillRect(j, canvas.getHeight() - i, 1, 1);
        }
      }
    }
  }



  /**
   * Finds the maximum hit count in the hit count array.
   *
   * @param hitCounts the array of hit counts
   * @return the maximum hit count
   * @since 0.0.3
   */
  public static int getMaxHits(int[][] hitCounts) {
    int maxHits = 1;
    for (int[] row : hitCounts) {
      for (int hitCount : row) {
        if (hitCount > maxHits) {
          maxHits = hitCount;
        }
      }
    }
    return maxHits;
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
  private void initializeCoordinateFields() {
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

    // Legg til lytter på tekstfeltet for å oppdatere fraktalen
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
    settingsBox.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1;");

    Label coordinatesTitle = new Label("Fractal Canvas Coordinates");
    coordinatesTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10;");

    VBox coordinatesContainer = new VBox();
    coordinatesContainer.setSpacing(10);
    coordinatesContainer.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1; -fx-background-color: #f8f8f8;");

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
    setupCanvas(width, height);
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
}
