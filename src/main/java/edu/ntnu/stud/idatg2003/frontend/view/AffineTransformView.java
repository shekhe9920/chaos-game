package edu.ntnu.stud.idatg2003.frontend.view;

import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToDouble;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.showOpenFileDialog;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.showSaveFileDialog;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescriptionFactory;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import edu.ntnu.stud.idatg2003.frontend.TransformDialogs;
import edu.ntnu.stud.idatg2003.frontend.controllers.ChaosGameController;
import edu.ntnu.stud.idatg2003.frontend.guicomponents.configdialog.AffineTransformConfigDialog;
import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * View class for displaying and interacting with affine transformation fractals.
 *
 * @version 0.0.6
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class AffineTransformView extends FractalView {


  private static final String SIERPINSKI_TRIANGLE = "Sierpinski Triangle";
  private static final String BARNSLEYFERN = "Barnsley Fern";

  private VBox transformationsBox;




  /**
   * Constructor for AffineTransformView.
   *
   * @param controller the controller to handle fractal logic
   * @param width      the width of the canvas
   * @param height     the height of the canvas
   * @since 0.0.1
   */
  public AffineTransformView(ChaosGameController controller, int width, int height) {
    super(controller, width, height);
    setupUI();
    initializeTransformationFields();
    setupTransformationBindings();
    loadTransformationsToUI(controller.getCurrentGameDescription());
  }




  /**
   * Initializes the fields for affine transformations.
   * Currently, a placeholder for future implementation.
   *
   * @since 0.0.5
   */
  private void initializeTransformationFields() {
    // Placeholder for initializing transformation fields if needed in the future
  }




  /**
   * Sets up the settings pane for the affine transformation fractal.
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
   * Creates the fractal options pane with settings for affine transformations.
   *
   * @return the fractal options pane
   * @since 0.0.2
   */
  private TitledPane createFractalOptionsPane() {
    VBox settingsBox = new VBox(10);
    settingsBox.setPadding(new Insets(10));

    // fields for fractal coordinates and steps
    setupCoordinateFields(settingsBox);
    setupStepsField(settingsBox);

    transformationsBox = new VBox(10);

    Button updateFractalButton = new Button("Update Fractal");
    updateFractalButton.setOnAction(e -> updateFractal());

    Button showPredefinedAffineTransformsButton = new Button("Show Predefined Affine Fractals");
    showPredefinedAffineTransformsButton.setOnAction(e -> showPredefinedFractalDialog());

    Button addTransformationButton = new Button("Edit Transformation Matrix and Vector");
    addTransformationButton.setOnAction(e -> editTransformation());

    Button editWeightsButton = new Button("Edit Transformation Weight Probabilities");
    editWeightsButton.setOnAction(e -> showEditWeightsDialog());

    settingsBox.getChildren().addAll(
        new Label("Transformations:"),
        transformationsBox,
        updateFractalButton,
        showPredefinedAffineTransformsButton,
        addTransformationButton,
        editWeightsButton
    );

    settingsPane = new TitledPane("Fractal Controller", settingsBox);
    settingsPane.setCollapsible(true);
    return settingsPane;
  }




  /**
   * Opens a dialog to edit the transformation matrix and vector.
   *
   * @since 0.0.2
   */
  private void editTransformation() {
    List<AffineTransform2D> currentTransformations = controller.getCurrentTransformations();
    AffineTransformConfigDialog dialog = new AffineTransformConfigDialog(currentTransformations);
    List<AffineTransform2D> results = dialog.showDialog();
    if (results != null) {
      applyNewTransformations(results);
      updateFractal();
    }
  }




  /**
   * Applies the new transformations to the current game description.
   *
   * @param transformations the new transformations to apply
   * @since 0.0.1
   */
  private void applyNewTransformations(List<AffineTransform2D> transformations) {
    ChaosGameDescription description = controller.getCurrentGameDescription();
    description.setTransformations(new ArrayList<>(transformations));

    // Re-initialize the game with new transformations
    controller.initializeGame(
        description, (int) canvas.getWidth(), (int) canvas.getHeight(), DEFAULT_STEPS
    );
    loadTransformationsToUI(description);
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
    ChaosGameDescription description = ChaosGameDescriptionFactory.createSierpinskiTriangle();
    controller.initializeGame(description, width, height, DEFAULT_STEPS);
    drawFractal(canvas);
  }




  /**
   * Displays a dialog to select a predefined affine transformation fractal.
   *
   * @since 0.0.1
   */
  @Override
  protected void showPredefinedFractalDialog() {
    List<String> predefinedSets = List.of(SIERPINSKI_TRIANGLE, BARNSLEYFERN);

    ChoiceDialog<String> dialog = new ChoiceDialog<>(predefinedSets.getFirst(), predefinedSets);
    dialog.setTitle("Predefined Affine Transformations");
    dialog.setHeaderText("Select a predefined affine transformation:");
    dialog.setContentText("Available transformations:");

    dialog.showAndWait().ifPresent(this::applyPredefinedTransformation);
    updateFractal();
  }




  /**
   * Applies the selected predefined transformation.
   *
   * @param name the name of the predefined transformation
   * @since 0.0.1
   */
  private void applyPredefinedTransformation(String name) {
    ChaosGameDescription description = switch (name) {
      case SIERPINSKI_TRIANGLE -> ChaosGameDescriptionFactory.createSierpinskiTriangle();
      case BARNSLEYFERN -> ChaosGameDescriptionFactory.createBarnsleyFern();
      default -> throw new IllegalArgumentException("Unknown transformation name: " + name);
    };

    // Re-initialize the game with the selected predefined transformation
    controller.initializeGame(
        description, (int) canvas.getWidth(), (int) canvas.getHeight(), DEFAULT_STEPS
    );
    loadTransformationsToUI(description);
    loadCoordinatesToUI(description);
    updateFractal();
  }




  /**
   * Loads the transformations from the given description into the UI.
   *
   * @param description the chaos game description.
   * @since 0.0.1
   */
  private void loadTransformationsToUI(ChaosGameDescription description) {
    if (description == null) {
      System.err.println("Game description is not initialized.");
      return;
    }
    transformationsBox.getChildren().clear();
    for (Transform2D transform : description.getTransformations()) {
      if (transform instanceof AffineTransform2D) {
        Matrix2x2 matrix = ((AffineTransform2D) transform).getMatrix();
        Vector2D vector = ((AffineTransform2D) transform).getVector();

        AffineTransform2D entry = new AffineTransform2D(matrix, vector);
        TransformDialogs transformDialogs = new TransformDialogs();
        Node transformationNode = transformDialogs.createTransformationBox(entry);
        transformationsBox.getChildren().add(transformationNode);
      }
    }
  }




  /**
   * Updates the fractal with the current settings.
   *
   * @since 0.0.1
   */
  @Override
  protected void updateFractal() {
    try {
      int steps = Integer.parseInt(stepsField.getText());
      List<Double> currentWeights = controller.getGame().getWeights(); // the current weights

      Vector2D minCoords = new Vector2D(parseTextFieldToDouble(minXField),
          parseTextFieldToDouble(minYField)
      );
      Vector2D maxCoords = new Vector2D(parseTextFieldToDouble(maxXField),
          parseTextFieldToDouble(maxYField)
      );

      System.out.println("Updating fractal with steps: " + steps);

      List<Transform2D> currentTransformations = controller.getCurrentGameDescription().getTransformations();
      controller.updateAffineTransformationGame(steps, minCoords, maxCoords, currentTransformations);
      controller.initializeGame(controller.getCurrentGameDescription(), (int) canvas.getWidth(), (int) canvas.getHeight(), steps);
      controller.updateGameWithWeights(controller.getCurrentGameDescription(), steps, currentWeights);

      drawFractal(canvas);
    } catch (NumberFormatException e) {
      System.err.println("Error parsing input: " + e.getMessage());
    }
  }




  /**
   * Creates a menu item for opening a fractal configuration from a file.
   *
   * @return the menu item
   * @since 0.0.2
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

          controller.saveFractal(currentDescription, file.getPath(), "Affine2D");
          updateFractal();
        } catch (NumberFormatException ex) {
          System.err.println("Error parsing number: " + ex.getMessage());
        }
      }
    });
    return saveConfig;
  }




  /**
   * Updates the UI with the provided fractal description.
   *
   * @param description the fractal description
   * @since 0.0.2
   */
  @Override
  protected void updateUIWithDescription(ChaosGameDescription description) {
    // Implement if specific UI update is needed for Affine Transformations
  }




  /**
   * Sets up bindings for transformation fields to update the model on change.
   * Currently, a placeholder for future implementation.
   *
   * @since 0.0.5
   */
  private void setupTransformationBindings() {
    // Placeholder for setting up bindings if needed in the future
  }
}