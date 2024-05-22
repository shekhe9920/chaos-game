package edu.ntnu.stud.idatg2003.frontend.view;

import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.ActionHandlerUtil.setMenuItemAction;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToDouble;

import edu.ntnu.stud.idatg2003.backend.ChaosGameObserver;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.state.AppState;
import edu.ntnu.stud.idatg2003.backend.utilitiesbackend.ConfigManager;
import edu.ntnu.stud.idatg2003.commonutilities.errorutilitie.ErrorHandler;
import edu.ntnu.stud.idatg2003.frontend.controllers.ChaosGameController;
import edu.ntnu.stud.idatg2003.frontend.controllers.MainViewController;
import edu.ntnu.stud.idatg2003.frontend.utilityfrontend.GuiErrorDisplay;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * Abstract base class for all fractal views in the application.
 * Provides common functionality for handling canvas, menus, and settings.
 *
 * @version 0.0.7
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public abstract class FractalView extends BorderPane implements ChaosGameObserver {

  private static final Logger LOGGER = Logger.getLogger(FractalView.class.getName());

  protected TitledPane settingsPane;            // The settings pane
  protected ChaosGameController controller;     // The controller for handling fractal logic
  protected Canvas canvas;                      // The canvas for drawing the fractal
  protected MenuBar menuBar;                    // The menu bar for the application

  protected TextField minXField;                // The text field for the minimum x coordinate
  protected TextField minYField;                // The text field for the minimum y coordinate
  protected TextField maxXField;                // The text field for the maximum x coordinate
  protected TextField maxYField;                // The text field for the maximum y coordinate
  protected TextField stepsField;       // The text field for the number of steps

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

      ErrorHandler.handleException(new IllegalArgumentException(
          "Invalid canvas coordinate value: " + element), LOGGER.getName());
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

    Menu homeMenu = createHomeMenu();
    Menu fileMenu = createFileMenu();
    Menu settingsMenu = createSettingsMenu();

    menuBar.getMenus().addAll(homeMenu, fileMenu, settingsMenu);
    setTop(menuBar);
  }


  /**
   * Creates the home menu with options to go back home and switch fractals.
   *
   * @return the home menu
   * @since 0.0.7
   */
  private Menu createHomeMenu() {
    Menu homeMenu = new Menu("Home");

    MenuItem goBackHomeItem = new MenuItem("Go back home");
    MenuItem switchFractalItem =
        new MenuItem(MainViewController.getInstance().getCurrentViewType());

    setMenuItemActions(goBackHomeItem, switchFractalItem);

    homeMenu.getItems().addAll(goBackHomeItem, switchFractalItem);
    return homeMenu;
  }


  /**
   * Creates the file menu with options to open and save configuration files.
   *
   * @return the file menu
   * @since 0.0.7
   */
  private Menu createFileMenu() {
    Menu fileMenu = new Menu("File");
    fileMenu.getItems().addAll(createOpenConfigMenuItem(), createSaveConfigMenuItem());
    return fileMenu;
  }



  /**
   * Creates the settings menu with options to resize the canvas.
   *
   * @return the settings menu
   * @since 0.0.7
   */
  private Menu createSettingsMenu() {
    Menu settingsMenu = new Menu("Settings");
    MenuItem resizeCanvasItem = new MenuItem("Resize Canvas");

    setMenuItemAction(resizeCanvasItem, this::showResizeCanvasDialog);

    settingsMenu.getItems().add(resizeCanvasItem);
    return settingsMenu;
  }



  /**
   * Sets the actions for the menu items.
   *
   * @param goBackHomeItem    the menu item to go back home
   * @param switchFractalItem the menu item to switch fractals
   * @since 0.0.7
   */
  private void setMenuItemActions(MenuItem goBackHomeItem, MenuItem switchFractalItem) {

    // setting the actions for switching fractals and going back home:
    setMenuItemAction(goBackHomeItem, MainViewController.getInstance()::switchToHomeView);

    setMenuItemAction(switchFractalItem, () -> {
      MainViewController.getInstance().openOtherFractalWindow(); // opening the other fractal window
      switchFractalItem.setText(// updating the menu item text
          MainViewController.getInstance().getCurrentViewType());
    });
  }


  /**
   * Draws the fractal on the canvas based on the current hit counts.
   *
   * @param canvas the canvas to draw on
   * @since 0.0.1
   */
  protected abstract void drawFractal(Canvas canvas);

  /**
   * Sets the default chaos game description.
   *
   * @since 0.0.7
   */
  protected abstract ChaosGameDescription getDefaultDescription();

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
    stepsField = new TextField(String.valueOf(DEFAULT_STEPS));
  }





  /**
   * Sets up the step field in the settings box.
   *
   * @param settingsBox the settings box
   * @since 0.0.1
   */
  protected void setupStepsField(VBox settingsBox) {
    stepsField = new TextField(String.valueOf(DEFAULT_STEPS)); // default steps value
    HBox stepsBox = new HBox(new Label("Steps:"), stepsField);
    stepsBox.setAlignment(Pos.CENTER_LEFT);  // aligning the step box to the left

    // adding listener to update the game when step value changes
    stepsField.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        int steps = Integer.parseInt(newValue); // parsing the new value to integer

        // updating the game with the new steps value
        controller.updateGame(steps,
            new Vector2D(parseTextFieldToDouble(minXField), parseTextFieldToDouble(minYField)),
            new Vector2D(parseTextFieldToDouble(maxXField), parseTextFieldToDouble(maxYField)),
            controller.getCurrentGameDescription().getTransformations());

      } catch (NumberFormatException e) {
        ErrorHandler.handleException(e, LOGGER.getName());
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
    settingsBox.setStyle(
        "-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1;");

    Label coordinatesTitle = new Label("Fractal Canvas Coordinates");
    coordinatesTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10;");

    VBox coordinatesContainer = new VBox();
    coordinatesContainer.setSpacing(10);
    coordinatesContainer.setStyle(
        "-fx-padding: 10; -fx-border-color: #ccc;"
            + " -fx-border-width: 1; -fx-background-color: #f8f8f8;"
    );

    // setting up the coordinate fields, default values:
    minXField = new TextField("0.0");
    minYField = new TextField("0.0");
    maxXField = new TextField("1.0");
    maxYField = new TextField("1.0");

    HBox minCoordsBox = new HBox(10);
    HBox maxCoordsBox = new HBox(10);
    minCoordsBox.getChildren().addAll(new Label("Min X:"),
        minXField, new Label("Min Y:"), minYField);
    maxCoordsBox.getChildren().addAll(new Label("Max X:"),
        maxXField, new Label("Max Y:"), maxYField);

    // adding listeners to update the fractal when coordinate values change
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
  private void updateCanvasSize(int width, int height) {
    try {
      canvas.setWidth(width);
      canvas.setHeight(height);
      setupCanvas();
      updateFractal();

      AppState newState = ConfigManager.loadConfig();
      newState.setCanvasWidth(width);
      newState.setCanvasHeight(height);
      ConfigManager.saveConfig(newState);
    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
    }
  }




  /**
   * Shows a dialog for resizing the canvas.
   *
   * @since 0.0.6
   */
  public void showResizeCanvasDialog() {
    Dialog<Pair<String, String>> dialog = new Dialog<>(); // creating a dialog
    dialog.setTitle("Resize Canvas");
    dialog.setHeaderText("Enter new dimensions for the canvas:");

    // setting the dialog buttons
    ButtonType resizeButtonType = new ButtonType("Resize", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(resizeButtonType, ButtonType.CANCEL);

    // the grid layout for the dialog
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));


    // text fields for width and height
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
    result.ifPresent(widthHeight -> {  // updating the canvas size based on the new width and height
      try {
        int width = Integer.parseInt(widthHeight.getKey()); // parsing the width to integer
        int height = Integer.parseInt(widthHeight.getValue()); // parsing the height to integer
        updateCanvasSize(width, height); // updating the canvas size

      } catch (NumberFormatException e) {
        GuiErrorDisplay.showError("Invalid canvas dimensions: " + e.getMessage());
        ErrorHandler.handleException(e, LOGGER.getName());
      }
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

    } else { // logging a warning if the description is null
      GuiErrorDisplay.showError("An error occurred while loading coordinates from description.");
      LOGGER.warning("Chaos game description is null");
    }
  }


  /**
   * Clears the canvas by resetting the game with an empty transformation list.
   *
   * @since 0.0.7
   */
  protected void clearCanvas() {
    ChaosGameDescription emptyDescription =
        new ChaosGameDescription(new Vector2D(0, 0), new Vector2D(1, 1), new ArrayList<>());
    controller.initializeGame(emptyDescription, getCanvasWidth(), getCanvasHeight(), 0);
    controller.clearCanvas();
    drawFractal(canvas);
  }

}
