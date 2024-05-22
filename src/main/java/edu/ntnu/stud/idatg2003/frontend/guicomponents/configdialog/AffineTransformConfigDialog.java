package edu.ntnu.stud.idatg2003.frontend.guicomponents.configdialog;

import static edu.ntnu.stud.idatg2003.commonutilities.validation.ValidationUtils.checkNotNull;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import edu.ntnu.stud.idatg2003.commonutilities.errorutilitie.ErrorHandler;
import edu.ntnu.stud.idatg2003.frontend.utilityfrontend.GuiErrorDisplay;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * The {@code AffineTransformConfigDialog} class represents a dialog for configuring
 * affine transformations. Users can edit existing transformations or add new ones.
 *
 * @version 0.0.3
 * @since 0.0.4 (The version of Chaos-Game application when introduced)
 */
public class AffineTransformConfigDialog {

  // Logger for the class:
  private static final Logger LOGGER =
      Logger.getLogger(AffineTransformConfigDialog.class.getName());


  // the dialog for the affine transformations
  private final Dialog<List<AffineTransform2D>> dialog;
  private final VBox transformationsContainer; // Container for all transformations



  /**
   * Constructs a new {@code AffineTransformConfigDialog} with initial transformations.
   *
   * @param initialTransformations The initial list of affine transformations.
   * @since 0.0.1
   */
  public AffineTransformConfigDialog(List<AffineTransform2D> initialTransformations) {
    dialog = new Dialog<>();
    transformationsContainer = new VBox(10);
    setupDialog(initialTransformations);
  }





  /**
   * Sets up the dialog with the given initial transformations.
   *
   * @param initialTransformations The initial list of affine transformations.
   * @since 0.0.1
   */
  private void setupDialog(List<AffineTransform2D> initialTransformations) {
    dialog.setTitle("Configure Affine Transformations");
    dialog.setHeaderText("Edit affine transformations:");

    transformationsContainer.setPadding(new Insets(10)); // padding around the container

    // Scroll pane for the transformation container
    ScrollPane scrollPane = new ScrollPane(transformationsContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefWidth(600);

    initialTransformations.forEach(this::addTransformationFields); // for initial transformations

    Button addButton = new Button("+ Add New Transformation");  // 'Add new transformation' button
    addButton.setOnAction(e -> addTransformationFields(null));

    ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

    VBox mainLayout = new VBox(10, transformationsContainer, addButton);
    mainLayout.setPadding(new Insets(10));
    scrollPane.setContent(mainLayout);

    dialog.getDialogPane().setContent(scrollPane);
    dialog.getDialogPane().setPrefWidth(600);

    dialog.getDialogPane().getStylesheets().add(
        Objects.requireNonNull(getClass().getResource(
        "/css/affinetransform-config-dialog-style.css")).toExternalForm()
    );

    dialog.setResultConverter(dialogButton -> {   // result converter for the dialog
      if (dialogButton == saveButtonType) {
        return collectTransformations();
      }
      return null;
    });
  }





  /**
   * Adds transformation fields to the dialog.
   *
   * @param transform The affine transformation to add fields for, or null for a new transformation.
   * @since 0.0.1
   */
  private void addTransformationFields(AffineTransform2D transform) {
    Label lblA = new Label("A =");
    lblA.getStyleClass().add("matrix-label");
    Label lblB = new Label("b =");
    lblB.getStyleClass().add("vector-label");

    GridPane grid = createGridPane();
    GridPane matrixGrid = createMatrixGrid(transform);
    GridPane vectorGrid = createVectorGrid(transform);

    grid.add(lblA, 0, 0);
    grid.add(matrixGrid, 1, 0);
    grid.add(lblB, 2, 0);
    grid.add(vectorGrid, 3, 0);

    Button removeButton = createRemoveButton(grid);
    grid.add(removeButton, 4, 0, 1, 2);  // Span over two rows

    transformationsContainer.getChildren().add(grid);
  }




  /**
   * Creates a new GridPane with standard settings.
   *
   * @return A new GridPane.
   * @since 0.0.1
   */
  private GridPane createGridPane() {
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(5);
    grid.setPadding(new Insets(10));
    return grid;
  }




  /**
   * Creates a grid for matrix fields.
   *
   * @param transform The affine transformation to add fields for, or null for a new transformation.
   * @return A GridPane containing matrix fields.
   * @since 0.0.1
   */
  private GridPane createMatrixGrid(AffineTransform2D transform) {
    GridPane matrixGrid = new GridPane();
    matrixGrid.getStyleClass().add("matrix-grid");
    matrixGrid.setHgap(5);
    matrixGrid.setVgap(5);

    // text fields for the matrix, each with a default value of 0 if the transformation is null
    TextField fieldA00 = createTextField(transform != null
        ? String.valueOf(transform.matrix().a00()) : "0", "a00");    // a00 field
    TextField fieldA01 = createTextField(transform != null
        ? String.valueOf(transform.matrix().a01()) : "0", "a01");    // a01 field
    TextField fieldA10 = createTextField(transform != null
        ? String.valueOf(transform.matrix().a10()) : "0", "a10");    // a10 field
    TextField fieldA11 = createTextField(transform != null
        ? String.valueOf(transform.matrix().a11()) : "0", "a11");    // a11 field

    // adding the fields to the grid
    matrixGrid.addRow(0, new Label("a00:"), fieldA00, new Label("a01:"), fieldA01);
    matrixGrid.addRow(1, new Label("a10:"), fieldA10, new Label("a11:"), fieldA11);

    return matrixGrid;  // the matrix grid
  }





  /**
   * Creates a grid for vector fields.
   *
   * @param transform The affine transformation to add fields for, or null for a new transformation.
   * @return A GridPane containing vector fields.
   * @since 0.0.1
   */
  private GridPane createVectorGrid(AffineTransform2D transform) {
    GridPane vectorGrid = new GridPane();
    vectorGrid.getStyleClass().add("vector-grid");
    vectorGrid.setHgap(5);
    vectorGrid.setVgap(5);

    // text fields for the vector, each with a default value of 0 if the transformation is null
    TextField fieldB0 = createTextField(transform != null
        ? String.valueOf(transform.vector().getX0()) : "0", "b0");   // b0 field
    TextField fieldB1 = createTextField(transform != null
        ? String.valueOf(transform.vector().getX1()) : "0", "b1");   // b1 field

    // adding the fields to the grid
    vectorGrid.addRow(0, new Label("b0:"), fieldB0);
    vectorGrid.addRow(1, new Label("b1:"), fieldB1);

    return vectorGrid;  // the vector grid
  }





  /**
   * Creates a text field with the given initial text and ID.
   *
   * @param text The initial text for the text field.
   * @param id   The ID for the text field.
   * @return The created text field.
   * @since 0.0.1
   */
  private TextField createTextField(String text, String id) {
    TextField textField = new TextField(text);
    textField.getStyleClass().add("text-field-small");
    textField.setId(id);
    textField.setMinWidth(50);
    return textField;
  }





  /**
   * Creates a remove button for the transformation grid.
   *
   * @param grid The grid to remove when the button is clicked.
   * @return The created remove button.
   * @since 0.0.1
   */
  private Button createRemoveButton(GridPane grid) {
    Button removeButton = new Button("Remove");
    removeButton.getStyleClass().add("button-remove");
    // remove action for the button
    removeButton.setOnAction(e -> transformationsContainer.getChildren().remove(grid));
    return removeButton;
  }





  /**
   * Collects all affine transformations from the dialog.
   *
   * @return A list of affine transformations.
   * @since 0.0.1
   */
  private List<AffineTransform2D> collectTransformations() {
    ObservableList<AffineTransform2D> transformations = FXCollections.observableArrayList();

    for (Node node : transformationsContainer.getChildren()) {
      if (!(node instanceof GridPane grid)) {  // check if the node is a grid pane

        throw new IllegalStateException("Unexpected node type in transformations container.");

      } else {

        TextField fieldA00 = (TextField) grid.lookup("#a00");
        TextField fieldA01 = (TextField) grid.lookup("#a01");
        TextField fieldA10 = (TextField) grid.lookup("#a10");
        TextField fieldA11 = (TextField) grid.lookup("#a11");
        TextField fieldB0 = (TextField) grid.lookup("#b0");
        TextField fieldB1 = (TextField) grid.lookup("#b1");

        // validation for the fields:
        checkNotNull(fieldA00, fieldA01, fieldA10, fieldA11, fieldB0, fieldB1);

        Matrix2x2 matrix = new Matrix2x2(
            Double.parseDouble(fieldA00.getText()),
            Double.parseDouble(fieldA01.getText()),
            Double.parseDouble(fieldA10.getText()),
            Double.parseDouble(fieldA11.getText())
        );

        Vector2D vector = new Vector2D(
            Double.parseDouble(fieldB0.getText()),
            Double.parseDouble(fieldB1.getText())
        );

        //the affine transformation:
        transformations.add(new AffineTransform2D(matrix, vector));
      }
    }
    return transformations;
  }

  /**
   * Shows the dialog and waits for the user to close it.
   *
   * @return The list of affine transformations, or null if the dialog was canceled.
   * @since 0.0.1
   */
  public List<AffineTransform2D> showDialog() {
    return dialog.showAndWait().orElse(null);
  }






  /**
   * Validates if the transformations contain affine transformations.
   *
   * @since 0.1.0
   */
  public void validateTransformations(List<Transform2D> transformations) {
    if (transformations.isEmpty() || !(transformations.getFirst() instanceof AffineTransform2D)) {
      throw new IllegalStateException("Weights are only applicable to affine transformations.");
    }
  }




  /**
   * Shows a dialog to edit weights.
   *
   * @param weights List of weights to edit.
   * @return List of edited weights.
   * @since 0.1.0
   */
  public List<Double> showEditWeightsDialog(List<Double> weights) {
    Dialog<List<Double>> weightDialog = new Dialog<>();   // dialog for the weights
    weightDialog.setTitle("Edit Transformation Selection Probability");
    weightDialog.setHeaderText("Edit the weights for each transformation:");

    ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
    weightDialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

    VBox weightFieldsBox = new VBox(10);
    List<TextField> weightFields = new ArrayList<>();

    if (weights == null || weights.isEmpty()) {
      throw new IllegalArgumentException("Weights cannot be null or empty");
    }

    // for each weight, a text field is created and added to the dialog
    for (int i = 0; i < weights.size(); i++) {
      // '2f' is used to format the weight to two decimal places
      TextField weightField = new TextField(String.format("%.2f", weights.get(i) * 100));
      weightFields.add(weightField);

      // (i + 1) is used to display the weight number starting from 1 (not 0)
      HBox hBox = new HBox(10, new Label("Weight " + (i + 1) + ":"), weightField);
      weightFieldsBox.getChildren().add(hBox);
    }

    weightDialog.getDialogPane().setContent(weightFieldsBox);

    weightDialog.setResultConverter(dialogButton -> {
      if (dialogButton == applyButtonType) {
        try {
          List<Double> newWeights = parseWeights(weightFields);
          double totalWeight = newWeights.stream().mapToDouble(Double::doubleValue).sum();

          if (totalWeight <= 0) {
            throw new IllegalArgumentException("Total weight must be greater than 0.");
          }

          normalizeWeights(newWeights, totalWeight);
          return newWeights;
        } catch (IllegalArgumentException e) {
          ErrorHandler.handleException(e, LOGGER.getName());
          GuiErrorDisplay.showError(e.getMessage());
          return null;
        }
      }
      return null;
    });

    return weightDialog.showAndWait().orElse(null);
  }



  /**
   * Parses the weights from the text fields.
   *
   * @param weightFields The list of text fields containing the weights.
   * @return The list of parsed weights.
   * @since 0.1.0
   */
  private List<Double> parseWeights(List<TextField> weightFields) {
    List<Double> weights = new ArrayList<>(); // list containing the weights
    for (TextField weightField : weightFields) {   // for each weight field in the list
      double weight = Double.parseDouble(weightField.getText()) / 100.0;
      weights.add(weight);
    }
    return weights;
  }



  /**
   * Normalizes the weights to sum to 1.
   *
   * @param weights     The list of weights to normalize.
   * @param totalWeight The total weight of all transformations.
   * @since 0.1.0
   */
  private void normalizeWeights(List<Double> weights, double totalWeight) {
    weights.replaceAll(aDouble -> aDouble / totalWeight);  // normalizing the weights
  }


}
