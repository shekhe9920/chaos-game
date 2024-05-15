package edu.ntnu.stud.idatg2003.frontend.view;

import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToDouble;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToInt;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.showOpenFileDialog;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.showSaveFileDialog;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescriptionFactory;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.frontend.controllers.ChaosGameController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View class for displaying and interacting with Julia set fractals.
 *
 * @version 0.0.4
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class JuliaSetView extends FractalView {

  private TextField cReField;
  private TextField cImField;




  /**
   * Constructor for JuliaSetView.
   *
   * @param controller the controller to handle fractal logic
   * @param width      the width of the canvas
   * @param height     the height of the canvas
   * @since 0.0.1
   */
  public JuliaSetView(ChaosGameController controller, int width, int height) {
    super(controller, width, height);
    setupUI();
  }




  /**
   * Draws the initial fractal on the canvas.
   *
   * @param width  the width of the canvas
   * @param height the height of the canvas
   * @since 0.0.1
   */
  @Override
  protected void drawInitialFractal(int width, int height) {
    ChaosGameDescription description =
        ChaosGameDescriptionFactory.createJuliaSetDescription(
            new Complex(-0.74543, 0.11301)
        );

    controller.initializeGame(description, width, height, DEFAULT_STEPS);
    drawFractal(canvas);
  }




  /**
   * Displays a dialog to select a predefined Julia set fractal.
   *
   * @since 0.0.1
   */
  @Override
  protected void showPredefinedFractalDialog() {
    List<Pair<String, Complex>> predefinedSets = Arrays.asList(
        new Pair<>("Fractal Name 1", new Complex(-0.4, 0.6)),
        new Pair<>("Fractal Name 2", new Complex(-0.285, 0.01)),
        new Pair<>("Fractal Name 3", new Complex(-0.67, 0.01)),
        new Pair<>("Fractal Name 4", new Complex(-0.89, 0))
    );

    List<String> setNames = predefinedSets.stream().map(Pair::getKey).collect(Collectors.toList());
    ChoiceDialog<String> dialog = new ChoiceDialog<>(setNames.getFirst(), setNames);
    dialog.setTitle("Predefined Julia Sets");
    dialog.setHeaderText("Select a predefined Julia Set fractal:");
    dialog.setContentText("Available Julia Sets:");

    dialog.showAndWait().ifPresent(name -> {
      Complex c = predefinedSets.stream()
          .filter(pair -> pair.getKey().equals(name))
          .findFirst()
          .map(Pair::getValue)
          .orElseThrow(() -> new IllegalArgumentException("Set not found"));
      loadJuliaSetFractal(c);
    });
  }




  /**
   * Loads the Julia set fractal with the given complex number.
   *
   * @param c the complex number defining the Julia set
   * @since 0.0.1
   */
  private void loadJuliaSetFractal(Complex c) {
    cReField.setText(Double.toString(c.getX0()));
    cImField.setText(Double.toString(c.getX1()));
    updateFractal();
  }




  /**
   * Creates a menu item for opening a fractal configuration from a file.
   *
   * @return the menu item
   * @since 0.0.1
   */
  @Override
  protected MenuItem createOpenConfigMenuItem() {
    MenuItem openConfig = new MenuItem("Load Fractal From File");
    openConfig.setOnAction(e -> {
      File file = showOpenFileDialog(getScene().getWindow());
      if (file != null) {
        ChaosGameDescription description = controller.loadFractal(file.getPath());
        if (description != null) {
          updateUIWithDescription(description);
          controller.initializeGame(
              description, (int) canvas.getWidth(), (int) canvas.getHeight(), DEFAULT_STEPS);
          drawFractal(canvas);
        }
      }
    });
    return openConfig;
  }




  /**
   * Creates a menu item for saving the current fractal configuration to a file.
   *
   * @return the menu item
   * @since 0.0.2
   */
  @Override
  protected MenuItem createSaveConfigMenuItem() {
    MenuItem saveConfig = new MenuItem("Save Fractal To File");
    saveConfig.setOnAction(e -> {
      File file = showSaveFileDialog(getScene().getWindow());
      if (file != null) {
        try {
          double minX = parseTextFieldToDouble(minXField);
          double minY = parseTextFieldToDouble(minYField);
          double maxX = parseTextFieldToDouble(maxXField);
          double maxY = parseTextFieldToDouble(maxYField);
          ChaosGameDescription currentDescription = controller.getCurrentGameDescription();
          currentDescription.setMinCoords(new Vector2D(minX, minY));
          currentDescription.setMaxCoords(new Vector2D(maxX, maxY));

          controller.saveFractal(currentDescription, file.getPath(), "Julia");
          updateFractal();
        } catch (NumberFormatException ex) {
          System.err.println("Error parsing number: " + ex.getMessage());
        }
      }
    });
    return saveConfig;
  }




  /**
   * Sets up the settings pane for the Julia set fractal.
   *
   * @since 0.0.1
   */
  @Override
  protected void setupSettingsPane() {
    settingsPane = createFractalOptionsPane();
    ScrollPane scrollPane = new ScrollPane(settingsPane);
    scrollPane.setPrefHeight(settingsPane.getHeight());
    scrollPane.prefWidth(settingsPane.getWidth());
    setRight(scrollPane);
  }




  /**
   * Creates the fractal options pane with settings for Julia sets.
   *
   * @return the fractal options pane
   * @since 0.0.2
   */
  private TitledPane createFractalOptionsPane() {
    VBox settingsBox = new VBox(10);
    settingsBox.setPadding(new Insets(10));

    // Initialize text fields with default values for Julia set parameters
    cReField = new TextField("-0.74543");
    cImField = new TextField("0.11301");
    minXField = new TextField("-1.6");
    minYField = new TextField("-1.0");
    maxXField = new TextField("1.6");
    maxYField = new TextField("1.0");
    stepsField = new TextField("100000");

    HBox cBox = new HBox(5, new Label("c (Re):"), cReField, new Label("c (Im):"), cImField);
    HBox minCoordsBox = new HBox(5, new Label("Min X:"), minXField, new Label("Min Y:"), minYField);
    HBox maxCoordsBox = new HBox(5, new Label("Max X:"), maxXField, new Label("Max Y:"), maxYField);
    HBox stepsBox = new HBox(5, new Label("Steps:"), stepsField);

    Button updateFractalButton = new Button("Update Fractal");
    updateFractalButton.setOnAction(e -> updateFractal());
    Button showPredefinedJuliaSetsButton = new Button("Show Predefined Julia Sets");
    showPredefinedJuliaSetsButton.setOnAction(e -> showPredefinedFractalDialog());

    Button editWeightsButton = new Button("Edit Transformation Weight Probabilities");
    editWeightsButton.setOnAction(e -> showEditWeightsDialog());

    settingsBox.getChildren().addAll(
        cBox,
        minCoordsBox,
        maxCoordsBox,
        stepsBox,
        updateFractalButton,
        showPredefinedJuliaSetsButton,
        editWeightsButton
    );

    settingsPane = new TitledPane("Fractal Controller", settingsBox);
    settingsPane.setCollapsible(true);
    return settingsPane;
  }




  /**
   * Updates the UI with the provided fractal description.
   *
   * @param description the fractal description
   * @since 0.0.1
   */
  @Override
  protected void updateUIWithDescription(ChaosGameDescription description) {
    // Implement specific UI update if needed for Julia Set
  }




  /**
   * Updates the fractal with the current settings.
   *
   * @since 0.0.1
   */
  @Override
  public void updateFractal() {
    double realPart = parseTextFieldToDouble(cReField);
    double imaginaryPart = parseTextFieldToDouble(cImField);
    Complex c = new Complex(realPart, imaginaryPart);
    List<Double> currentWeights = controller.getGame().getWeights();

    int steps = parseTextFieldToInt(stepsField);

    Vector2D minCoords = new Vector2D(parseTextFieldToDouble(minXField),
        parseTextFieldToDouble(minYField)
    );
    Vector2D maxCoords = new Vector2D(parseTextFieldToDouble(maxXField),
        parseTextFieldToDouble(maxYField)
    );

    controller.updateJuliaSetGame(steps, minCoords, maxCoords, c);
    controller.initializeGame(controller.getCurrentGameDescription(),
        (int) canvas.getWidth(), (int) canvas.getHeight(), steps);
    controller.updateGameWithWeights(controller.getCurrentGameDescription(), steps, currentWeights);

    drawFractal(canvas);
  }
}
