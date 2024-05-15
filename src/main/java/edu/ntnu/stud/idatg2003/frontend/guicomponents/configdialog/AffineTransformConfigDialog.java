package edu.ntnu.stud.idatg2003.frontend.guicomponents.configdialog;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * The {@code AffineTransformConfigDialog} class represents a dialog for configuring
 * affine transformations. Users can edit existing transformations or add new ones.
 *
 * @version 0.0.2
 * @since 0.0.4 (The version of Chaos-Game application when introduced)
 */
public class AffineTransformConfigDialog {


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

    transformationsContainer.setPadding(new Insets(10));

    ScrollPane scrollPane = new ScrollPane(transformationsContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefWidth(600);

    initialTransformations.forEach(this::addTransformationFields);

    Button addButton = new Button("+ Add New Transformation");
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

    dialog.setResultConverter(dialogButton -> {
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
    GridPane grid = createGridPane();

    GridPane matrixGrid = createMatrixGrid(transform);
    GridPane vectorGrid = createVectorGrid(transform);

    Label lblA = new Label("A =");
    lblA.getStyleClass().add("matrix-label");
    Label lblB = new Label("b =");
    lblB.getStyleClass().add("vector-label");

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

    TextField fieldA00 = createTextField(transform != null ? String.valueOf(transform.getMatrix().getA00()) : "0", "a00");
    TextField fieldA01 = createTextField(transform != null ? String.valueOf(transform.getMatrix().getA01()) : "0", "a01");
    TextField fieldA10 = createTextField(transform != null ? String.valueOf(transform.getMatrix().getA10()) : "0", "a10");
    TextField fieldA11 = createTextField(transform != null ? String.valueOf(transform.getMatrix().getA11()) : "0", "a11");

    matrixGrid.addRow(0, new Label("a00:"), fieldA00, new Label("a01:"), fieldA01);
    matrixGrid.addRow(1, new Label("a10:"), fieldA10, new Label("a11:"), fieldA11);

    return matrixGrid;
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

    TextField fieldB0 = createTextField(transform != null ? String.valueOf(transform.getVector().getX0()) : "0", "b0");
    TextField fieldB1 = createTextField(transform != null ? String.valueOf(transform.getVector().getX1()) : "0", "b1");

    vectorGrid.addRow(0, new Label("b0:"), fieldB0);
    vectorGrid.addRow(1, new Label("b1:"), fieldB1);

    return vectorGrid;
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
      if (node instanceof GridPane) {
        GridPane grid = (GridPane) node;

        TextField fieldA00 = (TextField) grid.lookup("#a00");
        TextField fieldA01 = (TextField) grid.lookup("#a01");
        TextField fieldA10 = (TextField) grid.lookup("#a10");
        TextField fieldA11 = (TextField) grid.lookup("#a11");
        TextField fieldB0 = (TextField) grid.lookup("#b0");
        TextField fieldB1 = (TextField) grid.lookup("#b1");

        if (fieldA00 == null || fieldA01 == null || fieldA10 == null || fieldA11 == null || fieldB0 == null || fieldB1 == null) {
          throw new IllegalStateException("One or more fields are missing.");
        }

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

        transformations.add(new AffineTransform2D(matrix, vector));
      }
    }
    return transformations;
  }

  /**
   * Shows the dialog and waits for the user to close it.
   *
   * @return The list of affine transformations, or null if the dialog was cancelled.
   * @since 0.0.1
   */
  public List<AffineTransform2D> showDialog() {
    return dialog.showAndWait().orElse(null);
  }
}
