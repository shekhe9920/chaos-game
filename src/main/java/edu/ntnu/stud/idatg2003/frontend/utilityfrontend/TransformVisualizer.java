package edu.ntnu.stud.idatg2003.frontend.utilityfrontend;

import static edu.ntnu.stud.idatg2003.frontend.utilityfrontend.ActionHandlerUtil.setButtonAction;

import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
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
 * Utility class for creating transformation visualization components.
 * Provides methods to create UI components for displaying affine transformations.
 *
 * @version 0.0.3
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class TransformVisualizer {



  /**
   * Creates an HBox containing the matrix and vector representation of an affine transformation.
   *
   * @param transform the AffineTransform2D object representing the transformation
   * @return an HBox containing UI elements for the transformation.
   * @since 0.0.1
   */
  public HBox createTransformationBox(AffineTransform2D transform) {
    Separator separator = new Separator();
    separator.setOrientation(Orientation.VERTICAL);
    separator.setPrefHeight(50);
    separator.setValignment(VPos.CENTER);

    // Create a remove button to allow users to remove this transformation
    Button removeButton = new Button("Remove");
    setButtonAction(removeButton, () -> {
      HBox parentBox = (HBox) removeButton.getParent();
      ((VBox) parentBox.getParent()).getChildren().remove(parentBox);
    });

    // labels for the matrix and vector components:
    Label matrixLabel = new Label("A =");
    Label vectorLabel = new Label(", b =");
    VBox vectorBox = createVectorBox(transform);
    VBox matrixBox = createMatrixBox(transform);

    // Create an HBox to contain the matrix and vector UI elements along with the remove button
    HBox transformationBox =
        new HBox(10, matrixLabel, matrixBox, separator, vectorLabel, vectorBox, removeButton);
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
  private VBox createMatrixBox(AffineTransform2D transform) {
    HBox row1 = new HBox(10,
        createMatrixEntry("a00", transform != null ? transform.matrix().a00() : 0),
        createMatrixEntry("a01", transform != null ? transform.matrix().a01() : 0));
    HBox row2 = new HBox(10,
        createMatrixEntry("a10", transform != null ? transform.matrix().a10() : 0),
        createMatrixEntry("a11", transform != null ? transform.matrix().a11() : 0));
    VBox matrixBox = new VBox(5, row1, row2);

    // styling to visually separate the matrix entries
    matrixBox.setStyle("-fx-padding: 10; -fx-border-style: solid;"
        + " -fx-border-width: 2; -fx-border-insets: 5;"
        + " -fx-border-radius: 5; -fx-border-color: black;");
    return matrixBox;
  }





  /**
   * Creates an HBox containing a label and a read-only text field for a matrix entry.
   *
   * @param label        the label for the matrix entry
   * @param initialValue the initial value of the matrix entry
   * @return an HBox containing the label and read-only text field
   * @since 0.0.2
   */
  private HBox createMatrixEntry(String label, double initialValue) {
    Label matrixLabel = new Label(label);
    TextField matrixField = new TextField(String.valueOf(initialValue));
    matrixField.setPrefWidth(50);
    matrixField.setEditable(false); // read-only
    HBox hbox;
    hbox = new HBox(5, matrixLabel, matrixField);
    return hbox;
  }




  /**
   * Creates a VBox containing the vector entries for an affine transformation.
   *
   * @param transform the AffineTransform2D object representing the transformation
   * @return a VBox containing UI elements for the vector entries
   * @since 0.0.2
   */
  private VBox createVectorBox(AffineTransform2D transform) {
    VBox vectorBox = new VBox(5,
        createMatrixEntry("b1", transform != null ? transform.vector().getX0() : 0),
        createMatrixEntry("b2", transform != null ? transform.vector().getX1() : 0));

    // style the VBox to visually separate the vector entries
    vectorBox.setStyle("-fx-padding: 10; -fx-border-style: solid;"
        + " -fx-border-width: 2; -fx-border-insets: 5;"
        + " -fx-border-radius: 5; -fx-border-color: black;");


    return vectorBox;
  }


}
