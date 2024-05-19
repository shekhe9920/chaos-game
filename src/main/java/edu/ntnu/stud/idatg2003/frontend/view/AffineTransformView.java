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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 * View class for displaying and interacting with affine transformation fractals.
 *
 * @version 0.0.7
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class AffineTransformView extends FractalView {


  // predefined affine transformation fractals:
  private static final String SIERPINSKI_TRIANGLE = "Sierpinski Triangle";
  private static final String BARNSLEY_FERN = "Barnsley Fern";
  private static final String DRAGON_CURVE = "Dragon Curve";
  private static final String MAPLE_LEAF = "Maple Leaf";
  private static final String SPIRAL = "Spiral";


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
    super(controller, width, height);     // calling the constructor of the superclass
    setupUI();                            // setting up the UI
    drawInitialFractal(width, height);    // drawing the initial fractal
    loadTransformationsToUI(controller.getCurrentGameDescription());// loading transformations to UI
  }




//  @Override
//  protected void initializeCoordinateFields() {
//    minXField = new TextField("0.0");
//    minYField = new TextField("0.0");
//    maxXField = new TextField("1.0");
//    maxYField = new TextField("1.0");
//    stepsField = new TextField("100000");
//  }




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
    updateFractalButton.setOnAction(e -> updateFractal());

    Button showPredefinedAffineTransformsButton = new Button("Show Predefined Affine Fractals");
    showPredefinedAffineTransformsButton.setOnAction(e -> showPredefinedFractalDialog());

    Button addTransformationButton = new Button("Edit Transformation Matrix and Vector");
    addTransformationButton.setOnAction(e -> editTransformation());

    Button editWeightsButton = new Button("Edit Transformation Weight Probabilities");
    editWeightsButton.setOnAction(e -> showEditWeightsDialog());

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

    // re-initializing the game with new transformations
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
//    drawFractal(canvas);
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
    updateFractal();
  }



  /**
   * Applies the selected predefined transformation.
   *
   * @param name the name of the predefined transformation
   * @since 0.0.1
   */
  private void applyPredefinedTransformation(String name) {
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
    controller.initializeGame(
        description, (int) canvas.getWidth(), (int) canvas.getHeight(), DEFAULT_STEPS
    );
    controller.updateGameWithWeights(description, DEFAULT_STEPS, weights);
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

        controller.loadFractal(file.getPath());
        ChaosGameDescription description = controller.getCurrentGameDescription();
        List<Double> weights = controller.getGame().getWeights();
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();


        if (description != null) {

          updateUIWithDescription(description);
          loadTransformationsToUI(description);
          loadCoordinatesToUI(description);
          controller.initializeGame(description, width, height, DEFAULT_STEPS);
          controller.updateGameWithWeights(description, DEFAULT_STEPS, weights);
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
          controller.setTransformWeights(controller.getGame().getWeights());

          controller.saveFractal(file.getPath(), "Affine2D");
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
}