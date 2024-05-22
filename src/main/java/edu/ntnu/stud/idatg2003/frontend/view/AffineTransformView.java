package edu.ntnu.stud.idatg2003.frontend.view;

import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.ActionHandlerUtil.setButtonAction;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToDouble;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.showOpenFileDialog;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.showSaveFileDialog;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescriptionFactory;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import edu.ntnu.stud.idatg2003.commonutilities.errorutilitie.ErrorHandler;
import edu.ntnu.stud.idatg2003.frontend.utilityfrontend.TransformVisualizer;
import edu.ntnu.stud.idatg2003.frontend.controllers.ChaosGameController;
import edu.ntnu.stud.idatg2003.frontend.guicomponents.configdialog.AffineTransformConfigDialog;
import edu.ntnu.stud.idatg2003.frontend.utilityfrontend.GuiErrorDisplay;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * View class for displaying and interacting with affine transformation fractals.
 *
 * @version 0.0.7
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class AffineTransformView extends FractalView {

  // Logger for the AffineTransformView class:
  private static final Logger LOGGER = Logger.getLogger(AffineTransformView.class.getName());


  // predefined affine transformation fractals:
  private static final String SIERPINSKI_TRIANGLE = "Sierpinski Triangle";
  private static final String BARNSLEY_FERN = "Barnsley Fern";
  private static final String DRAGON_CURVE = "Dragon Curve";
  private static final String MAPLE_LEAF = "Maple Leaf";
  private static final String SPIRAL = "Spiral";


  private VBox transformationsBox; // the box for displaying transformations


  // default settings for the affine transformation fractal:
  private static final ChaosGameDescription DEFAULT_DESCRIPTION
      = ChaosGameDescriptionFactory.createSierpinskiTriangle();

  // default number of steps for the affine transformation fractal:
  private final AffineTransformConfigDialog configDialog;





  /**
   * Constructor for AffineTransformView.
   *
   * @param controller the controller to handle fractal logic
   * @param width      the width of the canvas
   * @param height     the height of the canvas
   * @since 0.0.1
   */
  public AffineTransformView(ChaosGameController controller, int width, int height) {
    super(controller, width, height);     // calling the constructor of the superclass
    setupUI();                            // setting up the UI
    drawInitialFractal(width, height);    // drawing the initial fractal

    // loading transformations to UI:
    loadTransformationsToUserInterface(controller.getCurrentGameDescription());
    this.configDialog = new AffineTransformConfigDialog(controller.getCurrentTransformations());
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

    // buttons for updating the fractal, showing predefined fractals, and editing transformations:
    Button updateFractalButton = new Button("Update Fractal");
    setButtonAction(updateFractalButton, this::updateFractal);

    Button showPredefinedAffineTransformsButton = new Button("Show Predefined Affine Fractals");
    setButtonAction(showPredefinedAffineTransformsButton, this::showPredefinedFractalDialog);

    Button addTransformationButton = new Button("Edit Transformation Matrix and Vector");
    setButtonAction(addTransformationButton, this::editTransformation);

    Button editWeightsButton = new Button("Edit Transformation Weight Probabilities");
    setButtonAction(editWeightsButton, this::showEditWeightsDialog);

    // adding the components to the settings box:
    settingsBox.getChildren().addAll(
        new Label("Transformations:"),
        transformationsBox,
        updateFractalButton,
        showPredefinedAffineTransformsButton,
        addTransformationButton,
        editWeightsButton
    );

    // creating the titled pane for the settings:
    settingsPane = new TitledPane("Fractal Controller", settingsBox);
    settingsPane.setCollapsible(true);
    return settingsPane;
  }




  /**
   * Shows a dialog to edit weights.
   *
   * @since 0.0.7
   */
  private void showEditWeightsDialog() {
    try {
      // validating the transformations before editing weights:
      configDialog.validateTransformations(
          controller.getCurrentGameDescription().getTransformations()
      );

      // showing the dialog to edit weights:
      List<Double> newWeights =
          configDialog.showEditWeightsDialog(controller.getGame().getWeights());

      if (newWeights != null) {
        controller.setTransformWeights(newWeights);
        updateFractal();
      }
    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
      GuiErrorDisplay.showError("Error while editing weights: " + e.getMessage());
    }
  }




  /**
   * Opens a dialog to edit the transformation matrix and vector.
   *
   * @since 0.0.2
   */
  private void editTransformation() {
    try {
      List<AffineTransform2D> currentTransformations = controller.getCurrentTransformations();
      AffineTransformConfigDialog dialog = new AffineTransformConfigDialog(currentTransformations);
      List<AffineTransform2D> results = dialog.showDialog();

      // Applying the new transformations to the game, if not null:
      if (results != null) {
        if (results.isEmpty()) {
          // if no transformations are provided/removed, the canvas is cleared:
          clearCanvas();
        } else {
          applyNewTransformations(results);
          updateFractal();
        }
      }

    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
      GuiErrorDisplay.showError("Error: " + e.getMessage());
    }
  }




  /**
   * Applies the new transformations to the current game description.
   *
   * @param transformations the new transformations to apply
   * @since 0.0.1
   */
  private void applyNewTransformations(List<AffineTransform2D> transformations) {
    if (transformations.isEmpty()) {
      clearCanvas();
    } else {
      ChaosGameDescription description = controller.getCurrentGameDescription();
      description.setTransformations(new ArrayList<>(transformations));

      // re-initializing the game with new transformations
      controller.initializeGame(description, getCanvasWidth(), getCanvasHeight(), DEFAULT_STEPS);
      loadTransformationsToUserInterface(description);
      loadCoordinatesToUI(description);
    }
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
    controller.initializeGame(DEFAULT_DESCRIPTION, width, height, DEFAULT_STEPS);
    drawFractal(canvas);
  }



  /**
   * Returns the default chaos game description for affine transformation fractals.
   *
   * @return the default chaos game description
   * @since 0.0.7
   */
  @Override
  protected ChaosGameDescription getDefaultDescription() {
    return DEFAULT_DESCRIPTION;
  }





  /**
   * Displays a dialog to select a predefined affine transformation fractal.
   *
   * @since 0.0.1
   */
  @Override
  protected void showPredefinedFractalDialog() {
    List<String> predefinedSets =
        List.of(SIERPINSKI_TRIANGLE, BARNSLEY_FERN, DRAGON_CURVE, MAPLE_LEAF, SPIRAL);

    ChoiceDialog<String> dialog = new ChoiceDialog<>(predefinedSets.getFirst(), predefinedSets);
    dialog.setTitle("Predefined Affine Transformations");
    dialog.setHeaderText("Select a predefined affine transformation:");
    dialog.setContentText("Available transformations:");

    dialog.showAndWait().ifPresent(this::applyPredefinedTransformation);
  }





  /**
   * Applies the selected predefined transformation.
   *
   * @param name the name of the predefined transformation
   * @since 0.0.1
   */
  private void applyPredefinedTransformation(String name) {
    try {
      ChaosGameDescription description;
      List<Double> weights = switch (name) {
        case SIERPINSKI_TRIANGLE -> {
          description = ChaosGameDescriptionFactory.createSierpinskiTriangle();
          yield List.of(0.33, 0.33, 0.34);
        }
        case BARNSLEY_FERN -> {
          description = ChaosGameDescriptionFactory.createBarnsleyFern();
          yield List.of(0.01, 0.85, 0.07, 0.07);
        }
        case DRAGON_CURVE -> {
          description = ChaosGameDescriptionFactory.createDragonCurve();
          yield List.of(0.80, 0.20);
        }
        case MAPLE_LEAF -> {
          description = ChaosGameDescriptionFactory.createMapleLeaf();
          yield List.of(0.10, 0.20, 0.20, 0.50);
        }
        case SPIRAL -> {
          description = ChaosGameDescriptionFactory.createSpiral();
          yield List.of(0.787879, 0.212121);
        }
        default -> throw new IllegalArgumentException("Unknown transformation name: " + name);
      };

      controller.setTransformWeights(weights);

      // Re-initializing the game with new transformations:
      controller.initializeGame(description, getCanvasWidth(), getCanvasHeight(), DEFAULT_STEPS);
      controller.updateGameWithWeights(description, DEFAULT_STEPS, weights);
      loadTransformationsToUserInterface(description);
      loadCoordinatesToUI(description);
      updateFractal();

    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
      GuiErrorDisplay.showError("Selected transformation could not be applied: " + e.getMessage());
    }
  }





  /**
   * Loads the transformations from the given description into the UI.
   *
   * @param description the chaos game description.
   * @since 0.0.1
   */
  private void loadTransformationsToUserInterface(ChaosGameDescription description) {
    try {
      if (description == null) {
        throw new IllegalStateException("Game description is not initialized.");
      }
      transformationsBox.getChildren().clear();
      for (Transform2D transform : description.getTransformations()) {
        if (transform instanceof AffineTransform2D) {
          Matrix2x2 matrix = ((AffineTransform2D) transform).matrix();
          Vector2D vector = ((AffineTransform2D) transform).vector();

          AffineTransform2D entry = new AffineTransform2D(matrix, vector);
          TransformVisualizer visualizer = new TransformVisualizer();
          Node transformationNode = visualizer.createTransformationBox(entry);
          transformationsBox.getChildren().add(transformationNode);
        }
      }
    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
      GuiErrorDisplay.showError("Could not load transformations: " + e.getMessage());
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

      // updating the affine transformation game:
      List<Transform2D> currentTransformations =
          controller.getCurrentGameDescription().getTransformations();

      controller.updateAffineTransformationGame(
          steps, minCoords, maxCoords, currentTransformations);

      controller.initializeGame(
          controller.getCurrentGameDescription(), getCanvasWidth(), getCanvasHeight(), steps
      );

      controller.updateGameWithWeights(
          controller.getCurrentGameDescription(), steps, currentWeights
      );

      drawFractal(canvas);
    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
      GuiErrorDisplay.showError("Failed to update fractal: " + e.getMessage());
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
      try {
        File file = showOpenFileDialog(getScene().getWindow());


        if (file != null) {  // if the file is not null,
          controller.loadFractal(file.getPath());
          ChaosGameDescription description = controller.getCurrentGameDescription();
          List<Double> weights = controller.getGame().getWeights();
          int width = getCanvasWidth();
          int height = getCanvasHeight();

          if (description != null) {  // if the description is not null,
            updateUIWithDescription(description); // update the UI with the description
            loadTransformationsToUserInterface(description); // load the transformations to the UI
            loadCoordinatesToUI(description);     // load the coordinates to the UI
            // re-initializing the game with the new description:
            controller.initializeGame(description, width, height, DEFAULT_STEPS);
            controller.updateGameWithWeights(description, DEFAULT_STEPS, weights);
            drawFractal(canvas);
          }


        }


      } catch (Exception ex) {
        ErrorHandler.handleException(ex, LOGGER.getName());
        GuiErrorDisplay.showError("Could not load fractal: " + ex.getMessage());
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
      try {
        File file = showSaveFileDialog(getScene().getWindow());
        if (file != null) {
          double minX = parseTextFieldToDouble(minXField);
          double minY = parseTextFieldToDouble(minYField);
          double maxX = parseTextFieldToDouble(maxXField);
          double maxY = parseTextFieldToDouble(maxYField);
          ChaosGameDescription currentDescription = controller.getCurrentGameDescription();
          currentDescription.setMinCoords(new Vector2D(minX, minY));
          currentDescription.setMaxCoords(new Vector2D(maxX, maxY));
          controller.setTransformWeights(controller.getGame().getWeights());

          controller.saveFractal(file.getPath(), "Affine2D");
          updateFractal();
        }
      } catch (Exception ex) {
        ErrorHandler.handleException(ex, LOGGER.getName());
        GuiErrorDisplay.showError("Could not save fractal: " + ex.getMessage());
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
    // Placeholder for future implementation
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




  /**
   * Draws the fractal on the canvas.
   *
   * @param canvas the canvas to draw on
   * @since 0.0.7
   */
  @Override
  protected void drawFractal(Canvas canvas) {
    try {
      GraphicsContext gc = canvas.getGraphicsContext2D();
      if (gc == null) {
        throw new IllegalStateException("GraphicsContext not available");
      }

      int[][] hitCounts = controller.getGame().getCanvas().getHitCounts();
      int maxHits = getMaxHits(hitCounts);

      // filling the canvas with white color
      Color backgroundColor = Color.WHITE; // The background color of the canvas
      gc.setFill(backgroundColor);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

      // drawing the fractal based on the hit counts
      for (int i = 0; i < hitCounts.length; i++) {
        for (int j = 0; j < hitCounts[i].length; j++) {
          if (hitCounts[i][j] > 0) {
            double intensity = (double) hitCounts[i][j] / maxHits;
            gc.setFill(getColorForIntensity(intensity));
            gc.fillRect(j, canvas.getHeight() - i, 1, 1);
          }
        }
      }
    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
      GuiErrorDisplay.showError("Failed to draw fractal: " + e.getMessage());
    }
  }




  /**
   * Returns the maximum hit count for the pixels.
   * This is used to calculate the intensity of the color.
   *
   * @param hitCounts the hit counts for each pixel
   * @return the color for the intensity
   * @since 0.0.7
   */
  private int getMaxHits(int[][] hitCounts) {
    int maxHits = 1;   // The maximum number of hits

    for (int[] row : hitCounts) {    // for each row in the hit counts
      for (int hitCount : row) {     // for each hit count in the row
        if (hitCount > maxHits) {    // if the hit count is greater than the maximum hits
          maxHits = hitCount;        // setting the maximum hits to the hit count
        }
      }
    }

    return maxHits; // the maximum hits for the pixels
  }





  /**
   * Gets a color based on the intensity value.
   *
   * @param intensity the intensity value
   * @return the color for the intensity
   * @since 0.0.7
   */
  private Color getColorForIntensity(double intensity) {
    // using HSV to RGB conversion for a smoother transition from blue to red
    return Color.hsb(240 * (1 - intensity), 1.0, 1.0);
  }
}
