package edu.ntnu.stud.idatg2003.frontend;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Utility class for creating transformation dialogs.
 * Provides methods to create UI components for editing affine transformations.
 *
 * @version 0.0.2
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class TransformDialogs {
  private static List<HBox> transformationBoxes = new ArrayList<>();

  /**
   * Creates an HBox containing the matrix and vector representation of an affine transformation.
   *
   * @param transform the AffineTransform2D object representing the transformation
   * @return an HBox containing UI elements for the transformation.
   * @since 0.0.1
   */
  public HBox createTransformationBox(AffineTransform2D transform) {
    Label matrixLabel = new Label("A =");
    VBox matrixBox = createMatrixBox(transform);

    Separator separator = new Separator();
    separator.setOrientation(Orientation.VERTICAL);
    separator.setPrefHeight(50);
    separator.setValignment(VPos.CENTER);

    Label vectorLabel = new Label(", b =");
    VBox vectorBox = createVectorBox(transform);

    // Create a remove button to allow users to remove this transformation
    Button removeButton = new Button("Remove");
    removeButton.setOnAction(event -> {
      transformationBoxes.remove(removeButton.getParent());
      ((VBox) removeButton.getParent().getParent()).getChildren().remove(removeButton.getParent());
    });

    // Create an HBox to contain the matrix and vector UI elements along with the remove button
    HBox transformationBox = new HBox(10, matrixLabel, matrixBox, separator, vectorLabel, vectorBox, removeButton);
    transformationBox.setAlignment(Pos.CENTER_LEFT);

    return transformationBox;
  }

  /**
   * Creates a VBox containing the matrix entries for an affine transformation.
   *
   * @param transform the AffineTransform2D object representing the transformation
   * @return a VBox containing UI elements for the matrix entries
   * @since 0.0.2
   */
  public VBox createMatrixBox(AffineTransform2D transform) {
    HBox row1 = new HBox(10,
        createMatrixEntry("a00", 50, transform != null ? transform.getMatrix().getA00() : 0),
        createMatrixEntry("a01", 50, transform != null ? transform.getMatrix().getA01() : 0));
    HBox row2 = new HBox(10,
        createMatrixEntry("a10", 50, transform != null ? transform.getMatrix().getA10() : 0),
        createMatrixEntry("a11", 50, transform != null ? transform.getMatrix().getA11() : 0));
    VBox matrixBox = new VBox(5, row1, row2);

    // Style the VBox to visually separate the matrix entries
    matrixBox.setStyle("-fx-padding: 10; -fx-border-style: solid;"
        + " -fx-border-width: 2; -fx-border-insets: 5;"
        + " -fx-border-radius: 5; -fx-border-color: black;");
    return matrixBox;
  }

  /**
   * Creates an HBox containing a label and text field for a matrix entry.
   *
   * @param label        the label for the matrix entry
   * @param prefWidth    the preferred width of the text field
   * @param initialValue the initial value of the matrix entry
   * @return an HBox containing the label and text field
   * @since 0.0.2
   */
  private HBox createMatrixEntry(String label, double prefWidth, double initialValue) {
    Label matrixLabel = new Label(label);
    TextField matrixField = new TextField(String.valueOf(initialValue));
    matrixField.setPrefWidth(prefWidth);
    HBox hbox = new HBox(5, matrixLabel, matrixField);
    return hbox;
  }

  /**
   * Creates a VBox containing the vector entries for an affine transformation.
   *
   * @param transform the AffineTransform2D object representing the transformation
   * @return a VBox containing UI elements for the vector entries
   * @since 0.0.2
   */
  public VBox createVectorBox(AffineTransform2D transform) {
    VBox vectorBox = new VBox(5,
        createMatrixEntry("b1", 50, transform != null ? transform.getVector().getX0() : 0),
        createMatrixEntry("b2", 50, transform != null ? transform.getVector().getX1() : 0));

    // Style the VBox to visually separate the vector entries
    vectorBox.setStyle("-fx-padding: 10; -fx-border-style: solid;"
        + " -fx-border-width: 2; -fx-border-insets: 5;"
        + " -fx-border-radius: 5; -fx-border-color: black;");
    return vectorBox;
  }
}
