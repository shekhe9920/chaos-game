package edu.ntnu.stud.idatg2003.frontend.utilityfrontend;

import java.io.File;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;


/**
 * Utility class for common functions used in FractalView.
 *
 * @version 0.0.2
 * @since 0.0.4 (The version of Chaos-Game application when introduced)
 */
public class FractalViewUtility {



  private FractalViewUtility() {
    // Private constructor to hide the implicit public one
  }




  /**
   * Parses the text in the given text field to a double.
   *
   * @param textField the text field to parse
   * @return the parsed double value
   */
  public static double parseTextFieldToDouble(TextField textField) {
    String text = textField.getText().replace(",", ".");
    try {
      return Double.parseDouble(text);
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }





  /**
   * Parses the text in the given text field to an integer.
   *
   * @param textField the text field to parse
   * @return the parsed integer value
   */
  public static int parseTextFieldToInt(TextField textField) {
    String text = textField.getText().replace(",", ".");
    try {
      System.out.println("Steps: " + textField.getText());  // Debugging
      return Integer.parseInt(text);
    } catch (NumberFormatException e) {
      return 0;
    }
  }





  /**
   * Shows a file open dialog.
   *
   * @param owner the owner window
   * @return the selected file
   */
  public static File showOpenFileDialog(Window owner) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Fractal File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("TXT Files", "*.txt"));

    return fileChooser.showOpenDialog(owner);
  }




  /**
   * Shows a file save dialog.
   *
   * @param owner the owner window
   * @return the selected file
   */
  public static File showSaveFileDialog(Window owner) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Fractal File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
    return fileChooser.showSaveDialog(owner);
  }


}
