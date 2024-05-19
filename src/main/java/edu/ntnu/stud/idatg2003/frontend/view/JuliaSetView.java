package edu.ntnu.stud.idatg2003.frontend.view;


import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToDouble;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.parseTextFieldToInt;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.showOpenFileDialog;
import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.FractalViewUtility.showSaveFileDialog;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescriptionFactory;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.JuliaTransform;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import edu.ntnu.stud.idatg2003.frontend.controllers.ChaosGameController;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * View class for displaying and interacting with Julia and Mandelbrot set fractals.
 *
 * @version 0.0.5
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class JuliaSetView extends FractalView {


  private static final String JULIA_SET = "Julia Set Fractal";
  private static final String MANDELBROT_SET = "Mandelbrot Set Fractal";

  private TextField cReField;
  private TextField cImField;
  private ComboBox<Integer> orderComboBox;
  private ComboBox<String> fractalTypeComboBox;




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
    setupUI(); // setting up the UI
    drawInitialFractal(width, height); // the initial fractal is drawn
  }




  /**
   * Initializes the coordinate fields for the Julia Set fractal.
   *
   * @since 0.0.1
   */
  @Override
  protected void initializeCoordinateFields() {
    cReField = new TextField("-0.74543");
    cImField = new TextField("0.11301");
    minXField = new TextField("-1.6");
    minYField = new TextField("-1.0");
    maxXField = new TextField("1.6");
    maxYField = new TextField("1.0");
    stepsField = new TextField("1000");

    orderComboBox = new ComboBox<>();   // ComboBox for selecting the order of the Julia set
    orderComboBox.getItems().addAll(2, 3, 4);
    orderComboBox.setValue(2);     // default order value for transformations
    fractalTypeComboBox = new ComboBox<>();  // ComboBox for selecting a fractal type
    fractalTypeComboBox.getItems().addAll(JULIA_SET, MANDELBROT_SET);
    fractalTypeComboBox.setValue(JULIA_SET);   // default value
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
    if (cReField == null || cImField == null) {
      throw new IllegalStateException("UI elements are not initialized");
    }

    // creating a Julia set description with the default complex number and order
    ChaosGameDescription description = ChaosGameDescriptionFactory.createJuliaSetDescription(
        new Complex(-0.74543, 0.11301), 2
    );

    // initializing the game and drawing the fractal:
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
    List<Pair<String, Complex>> predefinedSets = createPredefinedSets();

    ChoiceDialog<Pair<String, Complex>> dialog = // a ChoiceDialog for predefined Julia sets
        new ChoiceDialog<>(predefinedSets.getFirst(), predefinedSets);

    dialog.setTitle("Predefined Julia Sets");
    dialog.setHeaderText("Select a predefined Julia Set fractal:");
    dialog.setContentText("Available Julia Sets:");

    configureDialogCellFactory(dialog);  // configuring the cell factory for the dialog

    Optional<Pair<String, Complex>> result = dialog.showAndWait();
    result.ifPresent(this::handleSelectedFractal);
    updateFractal();
  }



  /**
   * Creates a list of predefined Julia Set fractals.
   *
   * @return List of pairs containing descriptions and complex parameters.
   * @since 0.0.5
   */
  private List<Pair<String, Complex>> createPredefinedSets() {
    return Arrays.asList(            // creating a list of predefined Julia Set fractals:
        new Pair<>("Standard Julia Set (2nd Order)", new Complex(-0.4, 0.6)),

        new Pair<>("Spider Web (2nd Order)", new Complex(0.355, 0.355)),

        new Pair<>("Tricorn Julia Set (3rd Order)", new Complex(-0.5, 0.0)),

        new Pair<>("Clover Leaf (3rd Order)", new Complex(-0.54, 0.54)),

        new Pair<>("Mandel bar Julia Set (4th Order)", new Complex(0.45, 0.1428)),

        new Pair<>("Cross Fractal (4th Order)", new Complex(-0.70176, -0.3842))
    );
  }




  /**
   * Configures the cell factory for the ChoiceDialog to display only the description of the fractal.
   *
   * @param dialog The ChoiceDialog to configure.
   * @since 0.0.5
   */
  private void configureDialogCellFactory(ChoiceDialog<Pair<String, Complex>> dialog) {
    ComboBox<Pair<String, Complex>> comboBox =
        (ComboBox<Pair<String, Complex>>) dialog.getDialogPane().lookup(".combo-box");
    comboBox.setCellFactory(createListCellFactory());
    comboBox.setButtonCell(createListCell());
  }




  /**
   * Creates a ListCell factory to display the description of the fractal.
   *
   * @return Callback for the ListCell factory.
   * @since 0.0.5
   */
  private Callback<ListView<Pair<String, Complex>>, ListCell<Pair<String, Complex>>> createListCellFactory() {
    return param -> new ListCell<>() {
      @Override
      protected void updateItem(Pair<String, Complex> item, boolean empty) {
        super.updateItem(item, empty);
        updateListCell(this, item, empty);
      }
    };
  }



  /**
   * Creates a ListCell to display the description of the fractal.
   *
   * @return ListCell to display the description.
   * @since 0.0.5
   */
  private ListCell<Pair<String, Complex>> createListCell() {
    return new ListCell<>() {
      @Override
      protected void updateItem(Pair<String, Complex> item, boolean empty) {
        super.updateItem(item, empty);
        updateListCell(this, item, empty);
      }
    };
  }



  /**
   * Updates the text of the ListCell based on the given item and empty state.
   *
   * @param cell  The ListCell to update.
   * @param item  The item to display in the ListCell.
   * @param empty Whether this cell represents data.
   * @since 0.0.5
   */
  private void updateListCell(ListCell<Pair<String, Complex>> cell, Pair<String, Complex> item, boolean empty) {
    if (item == null || empty) {
      cell.setText(null);
    } else {
      cell.setText(item.getKey());
    }
  }




  /**
   * Handles the selected fractal from the ChoiceDialog.
   *
   * @param pair The selected pair containing description and complex parameter.
   * @since 0.0.5
   */
  private void handleSelectedFractal(Pair<String, Complex> pair) {
    int order = determineOrder(pair.getKey()); // determining the order of the fractal
    ChaosGameDescription description =
        ChaosGameDescriptionFactory.createJuliaSetDescription(pair.getValue(), order);

    // initializing the game with the description and the canvas dimensions
    controller.initializeGame(description, getCanvasWidth(), getCanvasHeight(), DEFAULT_STEPS);
    loadTransformationsToUI(description);  // loading the transformations to the UI
    loadCoordinatesToUI(description);      // loading the coordinates to the UI

    updateFractal();
  }





  /**
   * Determines the order of the Julia set fractal based on the key string.
   *
   * @param key The key string.
   * @return The order of the Julia set fractal.
   * @since 0.0.5
   */
  private int determineOrder(String key) {
    if (key.contains("4th Order")) return 4;   // checking the key string for the order
    if (key.contains("3rd Order")) return 3;
    return 2; // default order
  }





  /**
   * Loads transformations to the UI from the provided description.
   *
   * @param description The ChaosGameDescription containing the transformations.
   * @since 0.0.5
   */
  protected void loadTransformationsToUI(ChaosGameDescription description) {
    // iterating through the transformations in the description:
    for (Transform2D transform : description.getTransformations()) {
      // checking if the transformation is a JuliaTransform:
      if (transform instanceof JuliaTransform julia) {
        Complex c = julia.getPoint();          // getting the complex number from the JuliaTransform
        cReField.setText(String.valueOf(c.getX0()));      // setting the complex number fields
        cImField.setText(String.valueOf(c.getX1()));
        orderComboBox.setValue(julia.getOrder());         // setting the order of the Julia set
      }

    }
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

    // setting the action for the menu item:
    openConfig.setOnAction(e -> {
      File file = showOpenFileDialog(getScene().getWindow()); // showing the file dialog
      if (file != null) {
        controller.loadFractal(file.getPath());
        ChaosGameDescription description = controller.getCurrentGameDescription();
        System.out.println("Current game description after loading: " + description);

        if (description != null) {
          updateUIWithDescription(description); // updating the UI with the description
          loadTransformationsToUI(description);
          loadCoordinatesToUI(description);
          // initializing the game with the description:
          controller.initializeGame(description, getCanvasWidth(), getCanvasHeight(), DEFAULT_STEPS);
          drawFractal(canvas); // drawing the fractal
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
    // setting the action for the menu item:
    saveConfig.setOnAction(e -> {
      File file = showSaveFileDialog(getScene().getWindow());
      if (file != null) {

        try { // saving the fractal to the file
          ChaosGameDescription currentDescription = controller.getCurrentGameDescription();

          currentDescription.setMinCoords(
              new Vector2D(getCanvasCoords("minX"), getCanvasCoords("minY")));

          currentDescription.setMaxCoords(
              new Vector2D(getCanvasCoords("maxX"), getCanvasCoords("maxY")));

          controller.saveFractal(file.getPath(), "Julia");
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
    settingsPane = createFractalOptionsPane();             // creating the fractal options pane
    ScrollPane scrollPane = new ScrollPane(settingsPane);  // creating a scroll pane for the settings
    scrollPane.setPrefHeight(settingsPane.getHeight());    // preferred height of the scroll pane
    scrollPane.prefWidth(settingsPane.getWidth());         // preferred width of the scroll pane
    setRight(scrollPane);                      // setting the scroll pane to the right side
  }


  /**
   * Creates the fractal options pane with settings for Julia sets.
   *
   * @return the fractal options pane
   * @since 0.0.2
   */
  private TitledPane createFractalOptionsPane() {
    VBox settingsBox = new VBox(10);  // VBox for the settings
    settingsBox.setPadding(new Insets(10));  // padding for the VBox

    // checking if the UI elements are initialized
    checkNotNull(cReField, cImField, minXField, minYField,
        maxXField, maxYField, stepsField, orderComboBox, fractalTypeComboBox
    );

    // creating the UI elements for the fractal settings
    HBox cBox = new HBox(5, new Label("c (Re):"), cReField,
        new Label("c (Im):"), cImField);

    HBox minCoordsBox = new HBox(5, new Label("Min X:"), minXField,
        new Label("Min Y:"), minYField);

    HBox maxCoordsBox = new HBox(5, new Label("Max X:"), maxXField,
        new Label("Max Y:"), maxYField);

    HBox stepsBox = new HBox(5, new Label("Steps:"), stepsField);
    HBox orderBox = new HBox(5, new Label("Order:"), orderComboBox);
    HBox fractalTypeBox = new HBox(5, new Label("Fractal Type:"), fractalTypeComboBox);

    // creating buttons for updating the fractal and showing predefined Julia sets:
    Button updateFractalButton = new Button("Update Fractal");
    updateFractalButton.setOnAction(e -> {
      System.out.println("Update button pressed");
      updateFractal();
    });


    Button showPredefinedJuliaSetsButton = new Button("Show Predefined Julia Sets");
    showPredefinedJuliaSetsButton.setOnAction(e -> showPredefinedFractalDialog());

    // adding the UI elements to the settings box
    settingsBox.getChildren().addAll(
        cBox,
        orderBox,
        fractalTypeBox,
        minCoordsBox,
        maxCoordsBox,
        stepsBox,
        updateFractalButton,
        showPredefinedJuliaSetsButton
    );

    settingsPane = new TitledPane("Fractal Controller", settingsBox); // creating the TitledPane
    settingsPane.setCollapsible(true);  // setting the TitledPane to be collapsible
    return settingsPane; // returning the TitledPane
  }





  /**
   * Updates the UI with the provided fractal description.
   *
   * @param description the fractal description
   * @since 0.0.1
   */
  @Override
  protected void updateUIWithDescription(ChaosGameDescription description) {
    // placeholder for future implementation
  }




  /**
   * Updates the fractal with the current settings.
   *
   * @since 0.0.1
   */
  @Override
  public void updateFractal() {
    try {
      double realPart = parseTextFieldToDouble(cReField);    // parsing the text fields to double
      double imaginaryPart = parseTextFieldToDouble(cImField);
      int steps = parseTextFieldToInt(stepsField);

      int order = getSelectedOrder();    // getting the selected order of the fractal
      String fractalType = getSelectedFractalType();  // getting the selected fractal type

      // creating a complex number with the real and imaginary parts, and min and max coordinates
      Complex c = new Complex(realPart, imaginaryPart);
      Vector2D minCoords = new Vector2D(parseTextFieldToDouble(minXField), parseTextFieldToDouble(minYField));
      Vector2D maxCoords = new Vector2D(parseTextFieldToDouble(maxXField), parseTextFieldToDouble(maxYField));
      ChaosGameDescription description; // creating a ChaosGameDescription object

      if (fractalType.equals(JULIA_SET)) { // creating a Julia set description
        description = ChaosGameDescriptionFactory.createJuliaSetDescription(c, order);
        description.setMinCoords(minCoords);            // setting the min and max coordinates-
        description.setMaxCoords(maxCoords);            // for the Julia set description

        controller.updateJuliaSetGame(steps, minCoords, maxCoords, c, order); // updating the game

        // initializing the game with the description and the canvas dimensions
        controller.initializeGame(description, getCanvasWidth(), getCanvasHeight(), steps);

      } else {  // creating a Mandelbrot set description
        description = ChaosGameDescriptionFactory.createMandelbrotSetDescription(order);
        controller.initializeGame(description, getCanvasWidth(), getCanvasHeight(), steps);
      }

      drawFractal(canvas);
    } catch (NumberFormatException e) {
      System.err.println("Error parsing input: " + e.getMessage());
    }
  }












  /**
   * Draws the fractal on the canvas.
   *
   * @param canvas the canvas to draw on
   * @since 0.0.5
   */
  protected void drawFractal(Canvas canvas) {
    int width = (int) canvas.getWidth();  // getting the width and height of the canvas
    int height = (int) canvas.getHeight();
    int order = getSelectedOrder();  // getting the selected order
    int maxIteration = 1000;  // maximum number of iterations for the fractal calculation

    WritableImage image = new WritableImage(width, height); // WritableImage for drawing pixels

    PixelWriter pixelWriter = image.getPixelWriter(); // PixelWriter for setting pixel colors

    ExecutorService executor =    // ExecutorService for parallelizing row drawing
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // submitting tasks for drawing each row of the fractal
    for (int y = 0; y < height; y++) {
      int finalY1 = y;
      executor.submit(() ->
          drawRow(finalY1, width, height, order, maxIteration, pixelWriter));
    }

    shutdownExecutorAndAwaitTermination(executor); // shutting down the executor

    GraphicsContext gc = canvas.getGraphicsContext2D(); // getting the GraphicsContext
    gc.drawImage(image, 0, 0); // drawing the image on the canvas
  }





  /**
   * Draws a single row of the fractal image.
   *
   * @param y             The y-coordinate of the row to draw.
   * @param width         The width of the canvas.
   * @param height        The height of the canvas.
   * @param pixelWriter   The PixelWriter to set the color of pixels in the WritableImage.
   * @since 0.0.5
   */
  private void drawRow(int y, int width, int height, int order, int maxIteration, PixelWriter pixelWriter) {
    double minX = parseTextFieldToDouble(minXField); // parsing the text fields to double
    double minY = parseTextFieldToDouble(minYField);
    double maxX = parseTextFieldToDouble(maxXField);
    double maxY = parseTextFieldToDouble(maxYField);
    String fractalType = getSelectedFractalType();   // getting the selected fractal type


    // iterating through the width of the canvas
    for (int x = 0; x < width; x++) {

      double zx = minX + x * (maxX - minX) / width;     // calculating the x and y coordinates
      double zy = minY + y * (maxY - minY) / height;   // in the fractal coordinate system
      // calculating the iteration and smooth iteration.
      int iteration = calculateIteration(zx, zy, fractalType, order);
      double smoothIteration = calculateSmoothIteration(iteration, zx, zy, maxIteration);
      Color color = calculateColor(smoothIteration, iteration, maxIteration);

      pixelWriter.setColor(x, y, color); // setting the color of the pixel
    }
  }




  /**
   * Calculates the number of iterations for a given point in the fractal.
   * For the Julia set, the iteration is calculated using the formula:
   * <pre>
   * Z(n+1) = Z(n)^order + C
   * </pre>
   * where Z is a complex number (zx, zy) and C is a constant complex number.
   * For the Mandelbrot set, the iteration is calculated using the formula:
   * <pre>
   * Z(n+1) = Z(n)^order + Z(0)
   * </pre>
   * where Z(0) is the initial complex number (zx, zy).
   *
   * @param zx           The x-coordinate value in the fractal coordinate system.
   * @param zy           The y-coordinate value in the fractal coordinate system.
   * @param fractalType  The type of the fractal ("Julia Set" or "Mandelbrot Set").
   * @param order        The order of the fractal.
   * @return             The number of iterations before the point escapes the fractal boundary.
   */
  private int calculateIteration(double zx, double zy, String fractalType, int order) {
    if (fractalType.equals(JULIA_SET)) {  // calculating the fractal point based on the fractal type
      double cRe = parseTextFieldToDouble(cReField);
      double cIm = parseTextFieldToDouble(cImField);
      return controller.calculateFractalPoint(JULIA_SET, zx, zy, order, cRe, cIm);

    } else { // calculating the fractal point for the Mandelbrot set
      return controller.calculateFractalPoint(MANDELBROT_SET, zx, zy, order, 0, 0);
    }
  }





  /**
   * Calculates the smooth iteration count for color smoothing, based on the raw iteration count.
   * The smooth iteration count is calculated as:
   * <pre>
   * iteration + 1 - log(log(absZ)) / log(2)
   * </pre>
   * where absZ is the size of the complex number z = zx + i * zy.
   * The maximum number of iterations is used to determine if the point is in the fractal set.
   * If the point is in the set, the raw iteration count is returned.
   * Otherwise, the smooth iteration count is returned.
   *
   * @param iteration    The raw iteration count.
   * @param zx           The x-coordinate value in the fractal coordinate system.
   * @param zy           The y-coordinate value in the fractal coordinate system.
   * @param maxIteration The maximum number of iterations.
   * @return             The smooth iteration count for color smoothing.
   */
  private double calculateSmoothIteration(int iteration, double zx, double zy, int maxIteration) {

    if (iteration < maxIteration) {
      double absZ = Math.sqrt(zx * zx + zy * zy); // |z| = sqrt(x^2 + y^2)

      if (absZ > 0) {  // |z| > 0
        double logAbsZ = Math.log(absZ);  // log(|z|) = log(sqrt(x^2 + y^2))

        if (logAbsZ > 0) {  // log(|z|) > 0

          double logLogAbsZ = Math.log(logAbsZ);  // log(log(|z|))
          if (logLogAbsZ > 0) {    // log(log(|z|)) > 0

            // iteration + 1 - log(log(|z|)) / log(2)
            return iteration + 1 - logLogAbsZ / Math.log(2);
          }
        }
      }
    }
    return iteration;
  }




  /**
   * Calculates the color of a pixel based on the smooth iteration count.
   * The color is calculated using the HSB (Hue, Saturation, Brightness) color model.
   * The hue is calculated as:
   * <pre>
   * hue = 360.0 * (smoothIteration % 256) / 256.0
   * </pre>
   * The saturation is set to 1.0.
   * The brightness is calculated as:
   * <pre>
   * brightness = iteration < maxIteration ? 1.0 - Math.sqrt(iteration / (double) maxIteration) : 0.0
   * </pre>
   *
   * @param smoothIteration The smooth iteration count for color smoothing.
   * @param iteration       The raw iteration count.
   * @param maxIteration    The maximum number of iterations.
   * @return                The Color of the pixel.
   */
  private Color calculateColor(double smoothIteration, int iteration, int maxIteration) {
    double hue = 360.0 * (smoothIteration % 256) / 256.0;    // hue calculation
    double saturation = 1.0;   // saturation is set to 1.0

    double brightness =        // brightness calculation
        iteration < maxIteration ? 1.0 - Math.sqrt(iteration / (double) maxIteration) : 0.0;

    return Color.hsb(hue, saturation, brightness);  // returning the color
  }




  /**
   * Shuts down the ExecutorService and waits for all tasks to complete.
   * This method ensures that all threads complete their execution before the application continues.
   *
   * @param executor The ExecutorService to shut down.
   */
  private void shutdownExecutorAndAwaitTermination(ExecutorService executor) {
    executor.shutdown();
    try {
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }




  /**
   * Shows a dialog for editing the transformation weights.
   *
   * @since 0.0.5
   */
  private int getSelectedOrder() {
    return orderComboBox.getValue();
  }



  /**
   * Shows a dialog for editing the transformation weights.
   *
   * @since 0.0.5
   */
  private String getSelectedFractalType() {
    return fractalTypeComboBox.getValue();
  }


}